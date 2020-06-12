package main

import (
	"bufio"
	"encoding/hex"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"math/rand"
	"os"
	"os/exec"
	"os/signal"
	"path/filepath"
	"runtime"
	"strings"
	"syscall"
	"time"
)

const (
	alternate_java_home      = "gauge_java_home"
	java_home                = "JAVA_HOME"
	additional_libs_env_name = "gauge_additional_libs"
	custom_build_path        = "gauge_custom_build_path"
	custom_compile_dir       = "gauge_custom_compile_dir"
	custom_classpath         = "gauge_custom_classpath"
	jvm_args_env_name        = "gauge_jvm_args"
	default_build_dir        = "gauge_bin"
	main_class_name          = "com.thoughtworks.gauge.GaugeRuntime"
	JavaDebugOptsTemplate    = "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=%s,timeout=25000"
	java                     = "java"
	javaExt                  = ".java"
	defaultSrcDir            = "src"
	windows                  = "windows"
)

var pluginDir = ""
var projectRoot = ""

func main() {
	setPluginAndProjectRoots()
	startJava()
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

func startJava() {
	err := os.Chdir(projectRoot)
	if err != nil {
		logMessage("fatal", "failed to set gauge project root. "+err.Error())
	}
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

	err = cmd.Wait()
	if err != nil {
		logMessage("fatal", fmt.Sprintf("process %s with pid %d quit unexpectedly. %s\n", cmd.Path, cmd.Process.Pid, err.Error()))
		os.Exit(1)
	}
}

func listenForKillSignal(cmd *exec.Cmd) {
	sigchan := make(chan os.Signal, 2)
	signal.Notify(sigchan, syscall.SIGTERM)
	go func() {
		<-sigchan
		err := cmd.Process.Kill()
		if err != nil {
			logMessage("fatal", fmt.Sprintf("failed to kill process %d. %s", cmd.Process.Pid, err))
		}
	}()
}

func killIfGaugeIsDead(cmd *exec.Cmd) {
	parentProcessID := os.Getppid()
	for {
		if !isProcessRunning(parentProcessID) {
			err := cmd.Process.Kill()
			if err != nil {
				logMessage("debug", fmt.Sprintf("Failed to kill process with pid %d. %s\n", cmd.Process.Pid, err.Error()))
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
	javaDebugPort := os.Getenv("GAUGE_DEBUG_OPTS")
	if javaDebugPort != "" {
		logMessage("info", fmt.Sprintf("\nRunner Ready for Debugging"))
		value := fmt.Sprintf(JavaDebugOptsTemplate, javaDebugPort)
		args = append(args, value)
	}
	if os.Getenv(jvm_args_env_name) != "" {
		jvmArgs := splitByComma(os.Getenv(jvm_args_env_name))
		args = append(args, jvmArgs...)
	}
	args = append(args, "-Dfile.encoding=UTF-8")
	args = append(args, main_class_name)
	args = append(args, os.Args[1])
	return args
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
	projectRoot = os.Getenv("GAUGE_PROJECT_ROOT")
	if projectRoot == "" {
		logMessage("fatal", fmt.Sprintf("Could not find %s env. Java Runner exiting...", "GAUGE_PROJECT_ROOT"))
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
	err := cmd.Start()
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
	err := filepath.Walk(home, func(currentPath string, info os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if info.Name() == execName {
			execPath = currentPath
		}
		return err
	})
	if err != nil {
		logMessage("debug", fmt.Sprintf("failed to get full path for %s. %s", execName, err.Error()))
	}
	if execPath != "" {
		return execPath
	}
	return execName
}

func build(destination string, classpath string) {
	err := os.RemoveAll(destination)
	if err != nil {
		logMessage("fatal", fmt.Sprintf("failed to remove dir %s. %s", destination, err.Error()))
	}
	err = os.Mkdir(destination, 0755)
	if err != nil {
		logMessage("fatal", fmt.Sprintf("failed to create dir %s. %s", destination, err.Error()))
	}
	args := []string{"-encoding", "UTF-8", "-d", destination}
	javaFiles := make([]string, 0)
	resourceFiles := make(map[string][]string)

	srcDirs := make([]string, 0)

	value := os.Getenv(custom_compile_dir)
	if len(value) > 0 {
		paths := splitByComma(value)
		srcDirs = append(srcDirs, paths...)
	} else {
		srcDirs = append(srcDirs, defaultSrcDir)
	}
	for _, srcDirItem := range srcDirs {
		err = filepath.Walk(srcDirItem, func(currentPath string, info os.FileInfo, err error) error {
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
		if err != nil {
			logMessage("fatal", fmt.Sprintf("failed to get src files from  dir %s. %s", srcDirItem, err.Error()))
		}
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
}

func uniqueFileName() string {
	randBytes := make([]byte, 16)
	rand.Read(randBytes)
	return hex.EncodeToString(randBytes)
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

func splitByComma(text string) []string {
	splits := make([]string, 0)
	values := strings.Split(text, ",")
	for _, val := range values {
		splits = append(splits, strings.TrimSpace(val))
	}
	return splits
}
