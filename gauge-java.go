package main

import (
	"github.com/getgauge/common"
	"flag"
	"fmt"
	"os"
	"os/exec"
	"path"
	"path/filepath"
	"strings"
)

const (
	alternate_java_home       = "gauge_java_home"
	java_home                 = "JAVA_HOME"
	additional_libs_env_name  = "gauge_additional_libs"
	custom_build_path         = "gauge_custom_build_path"
	jvm_args_env_name         = "gauge_jvm_args"
	default_build_dir         = "gauge_bin"
	main_class_name           = "com.thoughtworks.gauge.GaugeRuntime"
	step_implementation_class = "StepImplementation.java"
)

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

var projectRoot = ""
var start = flag.Bool("start", false, "Start the java runner")
var initialize = flag.Bool("init", false, "Initialize the java runner")

func getProjectRoot() string {
	pwd, err := common.GetProjectRoot()
	if err != nil {
		panic(err)
	}

	return pwd
}

func getIntelliJClasspath() string {
	intellijOutDir := path.Join("out", "production")
	if !common.DirExists(intellijOutDir) {
		return ""
	}

	cp := ""
	walker := func(path string, info os.FileInfo, err error) error {
		if path == intellijOutDir {
			return nil
		}
		if info.IsDir() {
			appendClasspath(&cp, path)
			// we need only top-level directories. Don't walk nested
			return filepath.SkipDir
		}
		return nil
	}
	filepath.Walk(intellijOutDir, walker)
	return cp
}

func getEclipseClasspath() string {
	eclipseOutDir := path.Join("bin")
	if !common.DirExists(eclipseOutDir) {
		return ""
	}

	return eclipseOutDir
}

// User set classpath & additional libs will be comma separated
// it could be relative path, but JVM needs full path to be specified
// so this function splits the path, convert them to absolute path forms a classpath
func getClassPathForVariable(envVariableName string) string {
	value := os.Getenv(envVariableName)
	cp := ""
	if len(value) > 0 {
		paths := strings.Split(value, ",")
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

func showMessage(action, filename string) {
	fmt.Printf(" %s  %s\n", action, filename)
}

func createSrcDirectory() {
	createDirectory(path.Join("src", "test", "java"))
}

func createLibsDirectory() {
	createDirectory("libs")
}

func createDirectory(filePath string) {
	showMessage("create", filePath)
	if !common.DirExists(filePath) {
		err := os.MkdirAll(filePath, 0755)
		if err != nil {
			fmt.Printf("Failed to make directory. %s\n", err.Error())
		}
	} else {
		showMessage("skip", filePath)
	}
}

func createStepImplementationClass() {
	javaSrc := path.Join("src", "test", "java")
	destFile := path.Join(javaSrc, step_implementation_class)
	showMessage("create", destFile)
	if common.FileExists(destFile) {
		showMessage("skip", destFile)
	} else {
		srcFile, err := common.GetSkeletonFilePath(path.Join("java", step_implementation_class))
		if err != nil {
			showMessage("error", fmt.Sprintf("Failed to find %s. %s", step_implementation_class, err.Error()))
			return
		}
		err = common.CopyFile(srcFile, destFile)
		if err != nil {
			showMessage("error", fmt.Sprintf("Failed to copy %s. %s", srcFile, err.Error()))
		}
	}
}

func createJavaPropertiesFile() {
	destFile := path.Join("env", "default", "java.properties")
	showMessage("create", destFile)
	if common.FileExists(destFile) {
		showMessage("skip", destFile)
	} else {
		srcFile, err := common.GetSkeletonFilePath(path.Join("env", "java.properties"))
		if err != nil {
			showMessage("error", fmt.Sprintf("Failed to find env/java.properties. %s", err.Error()))
			return
		}
		err = common.CopyFile(srcFile, destFile)
		if err != nil {
			showMessage("error", fmt.Sprintf("Failed to copy %s. %s", srcFile, err.Error()))
		}
	}
}

func getInstallationPath() (string, error) {
	libsPath, err := common.GetLibsPath()
	if (err != nil) {
		return "", err
	}
	return path.Join(libsPath, "java"), nil
}

func printUsage() {
	flag.PrintDefaults()
	os.Exit(2)
}

func runCommand(cmdName string, args []string) {
	cmd := exec.Command(cmdName, args...)
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	//TODO: move to logs
	//fmt.Println(cmd.Args)
	var err error
	err = cmd.Start()
	if err != nil {
		fmt.Printf("Failed to start %s. %s\n", cmd.Path, err.Error())
		os.Exit(1)
	}
	err = cmd.Wait()
	if err != nil {
		fmt.Printf("process %s with pid %d quit unexpectedly. %s\n", cmd.Path, cmd.Process.Pid, err.Error())
		os.Exit(1)
	}
}

func main() {
	flag.Parse()
	if *start {
		os.Chdir(getProjectRoot())
		cp := ""
		javaInstallationPath,err := getInstallationPath()
		if err != nil {
			fmt.Println("Could not get installation directory, exiting...")
			os.Exit(1)
		}
		appendClasspath(&cp, path.Join(javaInstallationPath, "*"))
		appendClasspath(&cp, path.Join(javaInstallationPath, "libs", "*"))

		additionalLibs := getClassPathForVariable(additional_libs_env_name)
		appendClasspath(&cp, additionalLibs)

		// If user has specified classpath, that will be taken. If not search for IntelliJ and Eclipse out directories before giving up
		userSpecifiedClasspath := getClassPathForVariable(custom_build_path)
		if userSpecifiedClasspath != "" {
			appendClasspath(&cp, userSpecifiedClasspath)
		} else {
			if icp := getIntelliJClasspath(); icp != "" {
				appendClasspath(&cp, icp)
			} else if ecp := getEclipseClasspath(); ecp != "" {
				appendClasspath(&cp, ecp)
			} else {
				//TODO: Move to log
				//fmt.Println("Failed to detect project build path")
				//fmt.Printf("Building to %s directory \n", default_build_dir)
				build(default_build_dir, cp)
				appendClasspath(&cp, default_build_dir)
			}
		}

		javaPath := getExecPathFrom(java_home, alternate_java_home, "java")
		args := []string{"-classpath", cp}
		if os.Getenv(jvm_args_env_name) != "" {
			args = append(args, os.Getenv(jvm_args_env_name))
		}
		args = append(args, main_class_name)
		runCommand(javaPath, args)
	} else if *initialize {
		funcs := []initializerFunc{createSrcDirectory, createLibsDirectory, createStepImplementationClass, createJavaPropertiesFile}
		for _, f := range funcs {
			f()
		}
	} else {
		printUsage()
	}
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
	args := []string{"-encoding", "UTF-8", "-d", destination, "-cp", classpath}
	javaFiles := make([]string, 0)
	srcDir := path.Join("src", "test", "java")
	filepath.Walk(srcDir, func(currentPath string, info os.FileInfo, err error) error {
		if strings.Contains(currentPath, ".java") {
			javaFiles = append(javaFiles, currentPath)
		}
		return nil
	})

	args = append(args, javaFiles...)
	javac := getExecPathFrom(java_home, alternate_java_home, "javac")
	//TODO: should move to logs
	//fmt.Println(fmt.Sprintf("Building files in %s directory to %s", "src", destination))
	runCommand(javac, args)
}
