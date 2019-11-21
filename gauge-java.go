// Copyright 2019 ThoughtWorks, Inc.

// This file is part of Gauge-Java.

// This program is free software.
//
// It is dual-licensed under:
// 1) the GNU General Public License as published by the Free Software Foundation,
// either version 3 of the License, or (at your option) any later version;
// or
// 2) the Eclipse Public License v1.0.
//
// You can redistribute it and/or modify it under the terms of either license.
// We would then provide copied of each license in a separate .txt file with the name of the license as the title of the file.

package main

import (
	"bufio"
	"encoding/json"
	"flag"
	"fmt"
	"io/ioutil"
	"os"
	"os/exec"
	"os/signal"
	"path"
	"path/filepath"
	"runtime"
	"strings"
	"syscall"
	"time"

	"github.com/getgauge/common"
)

const (
	alternate_java_home       = "gauge_java_home"
	java_home                 = "JAVA_HOME"
	additional_libs_env_name  = "gauge_additional_libs"
	custom_build_path         = "gauge_custom_build_path"
	custom_compile_dir        = "gauge_custom_compile_dir"
	custom_classpath          = "gauge_custom_classpath"
	jvm_args_env_name         = "gauge_jvm_args"
	default_build_dir         = "gauge_bin"
	main_class_name           = "com.thoughtworks.gauge.GaugeRuntime"
	step_implementation_class = "StepImplementation.java"
	skelDir                   = "skel"
	envDir                    = "env"
	JavaDebugOptsTemplate     = "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=%s,timeout=25000"
	java                      = "java"
	javaExt                   = ".java"
	defaultSrcDir             = "src"
	windows                   = "windows"
)

var pluginDir = ""
var projectRoot = ""
var start = flag.Bool("start", false, "Start the java runner")
var initialize = flag.Bool("init", false, "Initialize the java runner")

func main() {
	flag.Parse()
	setPluginAndProjectRoots()
	if *start {
		startJava()
	} else if *initialize {
		initializeProject()
	} else {
		printUsage()
	}
}

type logger struct {
	LogLevel string `json:"logLevel"`
	Message  string `json:"message"`
}

func logMessage(level, text string) {
	c := &logger{LogLevel: level, Message: text}
	if b, err := json.Marshal(c); err != nil {
		fmt.Println(text)
	} else {
		fmt.Println(string(b))
	}
}

func initializeProject() {
	os.Chdir(projectRoot)
	funcs := []initializerFunc{createSrcDirectory, createLibsDirectory, createStepImplementationClass, createEnvDir, createJavaPropertiesFile, createOrAppendGitignore}
	for _, f := range funcs {
		f()
	}
}

func startJava() {
	os.Chdir(projectRoot)
	cp := customClasspath()
	if cp == "" {
		cp = createClasspath()
	}
	logMessage("debug", fmt.Sprintf("classpath set to %s", cp))
	javaPath := getExecPathFrom(java_home, alternate_java_home, execName(java))
	args := createCommandArgs()
	cmd := runJavaCommandAsync(javaPath, args, cp)
	listenForKillSignal(cmd)
	go killIfGaugeIsDead(cmd) // Kills gauge-java.go process if gauge process i.e. parent process is already dead.

	err := cmd.Wait()
	if err != nil {
		logMessage("fatal", fmt.Sprintf("process %s with pid %d quit unexpectedly. %s\n", cmd.Path, cmd.Process.Pid, err.Error()))
		os.Exit(1)
	}
}

func listenForKillSignal(cmd *exec.Cmd) {
	sigc := make(chan os.Signal, 2)
	signal.Notify(sigc, syscall.SIGTERM)
	go func() {
		<-sigc
		cmd.Process.Kill()
	}()
}

func killIfGaugeIsDead(cmd *exec.Cmd) {
	parentProcessID := os.Getppid()
	for {
		if !isProcessRunning(parentProcessID) {
			err := cmd.Process.Kill()
			if err != nil {
				fmt.Printf("Failed to kill process with pid %d. %s\n", cmd.Process.Pid, err.Error())
			}
			os.Exit(0)
		}
		time.Sleep(100 * time.Millisecond)
	}
}

func isProcessRunning(pid int) bool {
	process, err := os.FindProcess(pid)
	if err != nil {
		return false
	}

	if runtime.GOOS != windows {
		return process.Signal(syscall.Signal(0)) == nil
	}

	processState, err := process.Wait()
	if err != nil {
		return false
	}
	if processState.Exited() {
		return false
	}

	return true
}

func createCommandArgs() []string {
	args := []string{}
	javaDebugPort := os.Getenv(common.GaugeDebugOptsEnv)
	if javaDebugPort != "" {
		logMessage("info", fmt.Sprintf("\nRunner Ready for Debugging"))
		value := fmt.Sprintf(JavaDebugOptsTemplate, javaDebugPort)
		args = append(args, value)
	}
	if os.Getenv(jvm_args_env_name) != "" {
		jvmArgs := splitByComma(os.Getenv(jvm_args_env_name))
		args = append(args, jvmArgs...)
	}
	args = append(args, encoding())
	args = append(args, main_class_name)
	return args
}

func encoding() string {
	return "-Dfile.encoding=UTF-8"
}

func execName(name string) string {
	if runtime.GOOS == windows {
		return fmt.Sprintf("%s.exe", name)
	}
	return name
}

func setPluginAndProjectRoots() {
	var err error
	pluginDir, err = os.Getwd()
	if err != nil {
		logMessage("fatal", fmt.Sprintf("Failed to find current working directory: %s \n", err))
		os.Exit(1)
	}
	projectRoot = os.Getenv(common.GaugeProjectRootEnv)
	if projectRoot == "" {
		logMessage("fatal", fmt.Sprintf("Could not find %s env. Java Runner exiting...", common.GaugeProjectRootEnv))
		os.Exit(1)
	}
}

func appendClasspath(source *string, classpath string) {
	if len(classpath) == 0 {
		return
	}
	if len(*source) == 0 {
		*source = classpath
	} else {
		*source = fmt.Sprintf("%s%c%s", *source, os.PathListSeparator, classpath)
	}
}

// User set classpath & additional libs will be comma separated
// it could be relative path, but JVM needs full path to be specified
// so this function splits the path, convert them to absolute path forms a classpath
func getClassPathForVariable(envVariableName string) string {
	value := os.Getenv(envVariableName)
	cp := ""
	if len(value) > 0 {
		paths := splitByComma(value)
		for _, p := range paths {
			abs, err := filepath.Abs(p)
			if err == nil {
				appendClasspath(&cp, abs)
			} else {
				appendClasspath(&cp, p)
			}
		}
	}
	return cp
}

type initializerFunc func()

func showMessage(action, filename string) string {
	return fmt.Sprintf(" %s  %s\n", action, filename)
}

func createSrcDirectory() {
	createDirectory(filepath.Join(defaultSrcDir, "test", java))
}

func createLibsDirectory() {
	createDirectory("libs")
}

func createEnvDir() {
	createDirectory(path.Join(projectRoot, "env", "default"))
}

func createDirectory(filePath string) {
	logMessage("info", showMessage("create", filePath))
	if !common.DirExists(filePath) {
		err := os.MkdirAll(filePath, 0755)
		if err != nil {
			logMessage("error", fmt.Sprintf("Failed to make directory. %s\n", err.Error()))
		}
	} else {
		logMessage("info", showMessage("skip", filePath))
	}
}

func createStepImplementationClass() {
	javaSrc := filepath.Join(defaultSrcDir, "test", java)
	destFile := filepath.Join(javaSrc, step_implementation_class)
	logMessage("info", showMessage("create", destFile))
	if common.FileExists(destFile) {
		logMessage("info", showMessage("skip", destFile))
	} else {
		srcFile := filepath.Join(pluginDir, skelDir, step_implementation_class)
		if !common.FileExists(srcFile) {
			logMessage("error", showMessage("error", fmt.Sprintf("%s Does not exist.\n", step_implementation_class)))
			return
		}
		err := common.CopyFile(srcFile, destFile)
		if err != nil {
			logMessage("error", showMessage("error", fmt.Sprintf("Failed to copy %s. %s \n", srcFile, err.Error())))
		}
	}
}

func createJavaPropertiesFile() {
	destFile := filepath.Join(envDir, "default", "java.properties")
	logMessage("info", showMessage("create", destFile))
	if common.FileExists(destFile) {
		logMessage("info", showMessage("skip", destFile))
	} else {
		srcFile := filepath.Join(pluginDir, skelDir, envDir, "java.properties")
		if !common.FileExists(srcFile) {
			logMessage("error", showMessage("error", fmt.Sprintf("java.properties does not exist at %s. \n", srcFile)))
			return
		}
		err := common.CopyFile(srcFile, destFile)
		if err != nil {
			logMessage("error", showMessage("error", fmt.Sprintf("Failed to copy %s. %s \n", srcFile, err.Error())))
		}
	}
}

func createOrAppendGitignore() {
	destFile := filepath.Join(projectRoot, ".gitignore")
	srcFile := filepath.Join(pluginDir, skelDir, ".gitignore")
	logMessage("info", showMessage("create", destFile))
	if err := common.AppendToFile(srcFile, destFile); err != nil {
		logMessage("error", showMessage("error", err.Error()))
	}
}

func printUsage() {
	flag.PrintDefaults()
	os.Exit(2)
}

func runJavaCommand(cmdName string, args []string, classpath string, printErrorsOnFailure bool) {
	cmd := runJavaCommandAsync(cmdName, args, classpath)
	if err := cmd.Wait(); err != nil {
		os.Exit(1)
	}
}

func runJavaCommandAsync(cmdName string, args []string, classpath string) *exec.Cmd {
	cmd := exec.Command(cmdName, args...)
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	cmd.Stdin = os.Stdin
	os.Setenv("CLASSPATH", classpath)
	//TODO: move to logs
	/*fmt.Println(cmd.Args)*/
	var err error
	err = cmd.Start()
	if err != nil {
		logMessage("fatal", fmt.Sprintf("Failed to start %s. %s\n", cmd.Path, err.Error()))
		os.Exit(1)
	}
	return cmd
}

func customClasspath() string {
	return os.Getenv(custom_classpath)
}

func createClasspath() string {
	cp := ""
	appendClasspath(&cp, filepath.Join(pluginDir, "*"))
	appendClasspath(&cp, filepath.Join(pluginDir, "libs", "*"))

	additionalLibs := getClassPathForVariable(additional_libs_env_name)
	appendClasspath(&cp, additionalLibs)

	// If user has specified classpath, that will be taken. If not search for IntelliJ and Eclipse out directories before giving up
	userSpecifiedClasspath := getClassPathForVariable(custom_build_path)
	if userSpecifiedClasspath != "" {
		appendClasspath(&cp, userSpecifiedClasspath)
	} else {
		if os.Getenv("SHOULD_BUILD_PROJECT") != "false" {
			build(default_build_dir, cp)
		}
		appendClasspath(&cp, default_build_dir)
	}
	return cp
}

func getExecPathFrom(path string, alternatePath string, execName string) string {
	var execPath string
	home := os.Getenv(alternatePath)
	if home == "" {
		home = os.Getenv(path)
		if home == "" {
			return execName
		}
	}
	filepath.Walk(home, func(currentPath string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if info.Name() == execName {
			execPath = currentPath
		}
		return err
	})
	if execPath != "" {
		return execPath
	}
	return execName
}

func build(destination string, classpath string) {
	os.RemoveAll(destination)
	os.Mkdir(destination, 0755)
	args := []string{"-encoding", "UTF-8", "-d", destination}
	javaFiles := make([]string, 0)
	resourceFiles := make(map[string][]string, 0)

	srcDirs := make([]string, 0)

	value := os.Getenv(custom_compile_dir)
	if len(value) > 0 {
		paths := splitByComma(value)
		for _, src := range paths {
			srcDirs = append(srcDirs, src)
		}
	} else {
		srcDirs = append(srcDirs, defaultSrcDir)
	}
	for _, srcDirItem := range srcDirs {
		filepath.Walk(srcDirItem, func(currentPath string, info os.FileInfo, err error) error {
			if err != nil {
				return err
			}
			if filepath.Ext(currentPath) == javaExt {
				javaFiles = append(javaFiles, currentPath)
			} else if !info.IsDir() {
				if _, ok := resourceFiles[srcDirItem]; !ok {
					resourceFiles[srcDirItem] = make([]string, 0)
				}
				listOfFiles := resourceFiles[srcDirItem]
				listOfFiles = append(listOfFiles, currentPath)
				resourceFiles[srcDirItem] = listOfFiles
			}
			return nil
		})
	}
	if len(javaFiles) == 0 {
		return
	}

	// Writing all java src file names to a file and using it as a @filename parameter to javac. Eg: javac -cp jar1:jar2 @sources.txt
	// This needs to be done because if the number of java files is too high the command length will be more than that permitted by the os.
	tempDir, err := ioutil.TempDir("", "gauge_temp")
	if err != nil {
		logMessage("error", fmt.Sprint(err.Error()))
		return
	}
	defer os.RemoveAll(tempDir)

	sourcesFile := filepath.Join(tempDir, uniqueFileName())
	if err := writeLines(javaFiles, sourcesFile); err != nil {
		panic("Unable to write file: " + err.Error())
	}
	args = append(args, "@"+sourcesFile)
	javac := getExecPathFrom(java_home, alternate_java_home, execName("javac"))

	runJavaCommand(javac, args, classpath, false)
	copyResources(resourceFiles, destination)
}

func uniqueFileName() string {
	return fmt.Sprintf("%d", common.GetUniqueID())
}

func writeLines(lines []string, path string) error {
	file, err := os.Create(path)
	if err != nil {
		return err
	}
	defer file.Close()

	w := bufio.NewWriter(file)
	for _, line := range lines {
		fmt.Fprintln(w, line)
	}
	return w.Flush()
}

func copyResources(srcFilesMap map[string][]string, dest string) {
	for src, files := range srcFilesMap {
		for _, file := range files {
			if src == defaultSrcDir {
				copyResource(filepath.Join(defaultSrcDir, "test", "java"), file, default_build_dir)
			} else {
				copyResource(src, file, default_build_dir)
			}
		}
	}
}

func copyResource(basePath string, resource string, destination string) error {
	rel, err := filepath.Rel(basePath, resource)
	if err != nil {
		return err
	}
	return common.MirrorFile(resource, filepath.Join(destination, rel))
}

func splitByComma(text string) []string {
	splits := make([]string, 0)
	values := strings.Split(text, ",")
	for _, val := range values {
		splits = append(splits, strings.TrimSpace(val))
	}
	return splits
}
