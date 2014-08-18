package main

import (
	"flag"
	"fmt"
	"github.com/getgauge/common"
	"os"
	"os/exec"
	"path"
	"path/filepath"
	"strings"
	"syscall"
	"os/signal"
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
	skelDir                   = "skel"
	envDir                    = "env"
	JavaDebugOptsTemplate     = "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=%s"
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

var pluginDir = ""
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
			p = strings.TrimSpace(p)
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
		srcFile := path.Join(pluginDir, skelDir, step_implementation_class)
		if !common.FileExists(srcFile) {
			showMessage("error", fmt.Sprintf("%s Does not exist.\n", step_implementation_class))
			return
		}
		err := common.CopyFile(srcFile, destFile)
		if err != nil {
			showMessage("error", fmt.Sprintf("Failed to copy %s. %s \n", srcFile, err.Error()))
		}
	}
}

func createJavaPropertiesFile() {
	destFile := path.Join(envDir, "default", "java.properties")
	showMessage("create", destFile)
	if common.FileExists(destFile) {
		showMessage("skip", destFile)
	} else {
		srcFile := path.Join(pluginDir, skelDir, envDir, "java.properties")
		if !common.FileExists(srcFile) {
			showMessage("error", fmt.Sprintf("java.properties does not exist at %s. \n", srcFile))
			return
		}
		err := common.CopyFile(srcFile, destFile)
		if err != nil {
			showMessage("error", fmt.Sprintf("Failed to copy %s. %s \n", srcFile, err.Error()))
		}
	}
}

func printUsage() {
	flag.PrintDefaults()
	os.Exit(2)
}

func runCommand(cmdName string, args []string) {
	cmd := runCommandAsync(cmdName, args)
	err := cmd.Wait()
	if err != nil {
		fmt.Printf("process %s with pid %d quit unexpectedly. %s\n", cmd.Path, cmd.Process.Pid, err.Error())
		os.Exit(1)
	}
}

func runCommandAsync(cmdName string, args []string) *exec.Cmd {
	cmd := exec.Command(cmdName, args...)
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	//TODO: move to logs
	/*fmt.Println(cmd.Args)*/
	var err error
	err = cmd.Start()
	if err != nil {
		fmt.Printf("Failed to start %s. %s\n", cmd.Path, err.Error())
		os.Exit(1)
	}
	return cmd
}

func main() {
	flag.Parse()
	var err error
	pluginDir, err = os.Getwd()
	if err != nil {
		fmt.Printf("Failed to find current working directory: %s \n", err)
		os.Exit(1)
	}
	projectRoot = os.Getenv("GAUGE_PROJECT_ROOT")
	if projectRoot == "" {
		fmt.Println("Could not find GAUGE_PROJECT_ROOT env. Java Runner exiting...")
		os.Exit(1)
	}

	if *start {
		os.Chdir(projectRoot)
		cp := ""
		appendClasspath(&cp, path.Join(pluginDir, "*"))
		appendClasspath(&cp, path.Join(pluginDir, "libs", "*"))

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
		args := []string{}
		javaDebugPort := os.Getenv(common.GaugeDebugOptsEnv)
		if javaDebugPort != "" {
			value := fmt.Sprintf(JavaDebugOptsTemplate, javaDebugPort)
			fmt.Println(value)
			args = append(args, value)
		}
		args = append(args, "-classpath", cp)
		if os.Getenv(jvm_args_env_name) != "" {
			args = append(args, os.Getenv(jvm_args_env_name))
		}
		args = append(args, main_class_name)
		sigc := make(chan os.Signal, 2)
		signal.Notify(sigc, syscall.SIGTERM)
		cmd := runCommandAsync(javaPath, args)

		go func() {
			<-sigc
			cmd.Process.Kill()
		}()

		err := cmd.Wait()
		if (err != nil) {
			fmt.Printf("process %s with pid %d quit unexpectedly. %s\n", cmd.Path, cmd.Process.Pid, err.Error())
			os.Exit(1)
		}

	} else if *initialize {
		os.Chdir(projectRoot)
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
	srcDir := path.Join("src")
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
