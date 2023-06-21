package main

import (
	"bufio"
	"encoding/hex"
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"math/rand"
	"os"
	"os/exec"
	"os/signal"
	"path/filepath"
	"regexp"
	"runtime"
	"strconv"
	"strings"
	"syscall"
	"time"
)

const (
	alternate_java_home                = "gauge_java_home"
	gaugeDebugOptsEnv                  = "GAUGE_DEBUG_OPTS"
	gaugeDebugConnectionTimeoutEnv     = "GAUGE_DEBUG_CONNECTION_TIMEOUT"
	gaugeDefaultDebugConnectionTimeout = "25000"
	javaHome                           = "JAVA_HOME"
	additionalLibsEnv                  = "gauge_additional_libs"
	customBuildPathEnv                 = "gauge_custom_build_path"
	customCompileDirEnv                = "gauge_custom_compile_dir"
	customClasspathEnv                 = "gauge_custom_classpath"
	jvmArgsEnv                         = "gauge_jvm_args"
	defaultBuildDir                    = "gauge_bin"
	mainClassName                      = "com.thoughtworks.gauge.GaugeRuntime"
	JavaDebugOptsTemplate              = "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=%s,timeout=%s"
	java                               = "java"
	javaExt                            = ".java"
	defaultSrcDir                      = "src"
	windows                            = "windows"
	mavenPomFile                       = "pom.xml"
	mavenCommand                       = "mvn"
	gradleBuildFile                    = "build.gradle"
	gradleCommadUnix                   = "gradlew"
	gradleCommadWindows                = "gradlew.bat"
)

var propertiesToPrint = []string{
	"PATH",
	"JAVA_HOME",
	"gauge_project_root",
	"gauge_parallel_streams_count",
	"gauge_reports_dir",
	"overwrite_reports",
	"logs_directory",
	"enable_multithreading",
	"gauge_specs_dir",
	"csv_delimiter",
	"gauge_java_home",
	"gauge_custom_build_path",
	"gauge_additional_libs",
	"gauge_jvm_args",
	"gauge_custom_compile_dir",
	"gauge_clear_state_level",
}
var pluginDir = ""
var projectRoot = ""

func isVersionEqual(vs1 string, vs2 string) bool {
	va1 := strings.Split(strings.TrimSpace(vs1), ".")
	va2 := strings.Split(strings.TrimSpace(vs2), ".")
	if len(va1) != len(va2) {
		return false
	}
	for i, _v := range va1 {
		k1, e := strconv.Atoi(_v)
		if e != nil {
			logMessage("debug", fmt.Sprintf("failed to compare version '%s' and '%s'. %s", vs1, vs2, e.Error()))
			return false
		}
		k2, e := strconv.Atoi(va2[i])
		if e != nil {
			logMessage("debug", fmt.Sprintf("failed to compare version '%s' and '%s'. %s", vs1, vs2, e.Error()))
			return false
		}
		if k1 != k2 {
			return false
		}
	}
	return true
}

func main() {
	logRelevantEnv()
	setPluginAndProjectRoots()
	startJava()
}

func logRelevantEnv() {
	logMessage("debug", "***** Printing Properties/Env Values *****")
	for _, e := range propertiesToPrint {
		logMessage("debug", fmt.Sprintf("%s: %s", e, os.Getenv(e)))
	}
	logMessage("debug", "***** END *****")
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

func fileExists(file string) bool {
	_, err := os.Stat(file)
	return err == nil
}

func getGaugeJavaDepFromMavenPom() (string, string, error) {
	args := []string{"dependency:tree", "-Dincludes=com.thoughtworks.gauge:gauge-java"}
	cmd := exec.Command(mavenCommand, args...)
	cmd.Stderr = os.Stderr
	cmd.Dir = projectRoot
	out, err := cmd.Output()
	if err != nil {
		return "", mavenPomFile, err
	}

	re, err := regexp.Compile(`.*com\.thoughtworks\.gauge:gauge-java:jar:(.*):.*`)
	if err != nil {
		return "", mavenPomFile, fmt.Errorf("failed to compile regex. %w", err)
	}
	matches := re.FindStringSubmatch(string(out))
	if len(matches) < 2 {
		return "", mavenPomFile, fmt.Errorf("failed to get gauge-java dep version")
	}
	return matches[1], mavenPomFile, nil
}

func getGradleCommand() string {
	windowsGradleW := filepath.Join(projectRoot, gradleCommadWindows)
	unixGradleW := filepath.Join(projectRoot, gradleCommadUnix)
	if runtime.GOOS == "windows" && fileExists(windowsGradleW) {
		return windowsGradleW
	} else if fileExists(unixGradleW) {
		return unixGradleW
	}
	return "gradle"
}

func getGaugeJavaDepFromGradleBuild() (string, string, error) {
	args := []string{"-q", "dependencyInsight", "--dependency", "com.thoughtworks.gauge", "--configuration", "testCompileClasspath"}
	cmd := exec.Command(getGradleCommand(), args...)
	cmd.Stderr = os.Stderr
	cmd.Dir = projectRoot
	out, err := cmd.Output()
	if err != nil {
		return "", gradleBuildFile, err
	}
	re, err := regexp.Compile(`.*com\.thoughtworks\.gauge:gauge-java:([^\s]+)`)
	if err != nil {
		return "", gradleBuildFile, fmt.Errorf("failed to compile regex. %w", err)
	}
	matches := re.FindStringSubmatch(string(out))
	if len(matches) < 2 {
		return "", mavenPomFile, fmt.Errorf("failed to get gauge-java dep version.")
	}
	return matches[1], gradleBuildFile, nil
}

func getDepVersionFromBuildFile() (string, string, error) {
	if fileExists(filepath.Join(projectRoot, mavenPomFile)) {
		return getGaugeJavaDepFromMavenPom()
	} else if fileExists(filepath.Join(projectRoot, gradleBuildFile)) {
		return getGaugeJavaDepFromGradleBuild()
	} else {
		return "", "", nil
	}
}

func getInstalledGaugeJavaVersion() (string, error) {
	exPath, err := os.Executable()
	if err != nil {
		return "", fmt.Errorf("failed to get executable path. %w", err)
	}
	j, err := ioutil.ReadFile(filepath.Join(filepath.Dir(exPath), "java.json"))
	if err != nil {
		return "", fmt.Errorf("Unable to read java.json. %w", err)
	}
	var v struct {
		Version string `json:"version"`
	}
	err = json.Unmarshal(j, &v)
	if err != nil {
		return "", fmt.Errorf("Unable to unmarshal java.json. %w", err)
	}
	return v.Version, nil
}

func validateGaugeJavaVersion() {
	depVersion, file, err := getDepVersionFromBuildFile()
	if err != nil {
		logMessage("error", err.Error())
	}
	if file == "" {
		return
	}
	installVersion, err := getInstalledGaugeJavaVersion()
	if err != nil {
		logMessage("error", err.Error())
	}
	if !isVersionEqual(depVersion, installVersion) {
		t := "Installed version of gauge-java (%s) does not match with dependency gauge-java (%s) specified in %s file"
		logMessage("error", fmt.Sprintf(t, installVersion, depVersion, file))
	}
}

func startJava() {
	validateGaugeJavaVersion()
	err := os.Chdir(projectRoot)
	if err != nil {
		logMessage("fatal", "failed to set gauge project root. "+err.Error())
	}
	cp := customClasspath()
	if cp == "" {
		cp = createClasspath()
	}
	logMessage("debug", fmt.Sprintf("classpath set to %s", cp))
	javaPath := getExecPathFrom(javaHome, alternate_java_home, execName(java))
	logMessage("debug", fmt.Sprintf("found java executable in %s", javaPath))
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
	javaDebugPort := os.Getenv(gaugeDebugOptsEnv)
	debugTimeout := os.Getenv(gaugeDebugConnectionTimeoutEnv)
	if debugTimeout == "" {
		debugTimeout = gaugeDefaultDebugConnectionTimeout
	}
	if javaDebugPort != "" {
		logMessage("info", "\nRunner Ready for Debugging")
		value := fmt.Sprintf(JavaDebugOptsTemplate, javaDebugPort, debugTimeout)
		args = append(args, value)
	}
	if os.Getenv(jvmArgsEnv) != "" {
		jvmArgs := splitByComma(os.Getenv(jvmArgsEnv))
		args = append(args, jvmArgs...)
	}
	args = append(args, "-Dfile.encoding=UTF-8")
	args = append(args, mainClassName)
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
	logMessage("debug", fmt.Sprintf("running - %s %s", cmdName, args))
	outFile := filepath.Join(os.Getenv("logs_directory"), fmt.Sprintf("%d-%s.out", os.Getpid(), filepath.Base(cmdName)))
	logMessage("debug", fmt.Sprintf("stdout/stderr is logged to %s", outFile))
	var stdout io.Writer
	var stderr io.Writer
	f, err := os.Create(outFile)
	if err != nil {
		logMessage("info", fmt.Sprintf("unable to create %s to log process output. Execution will continue without this log. %s", outFile, err.Error()))
		stdout = os.Stdout
		stderr = os.Stderr
	} else {
		stdout = io.MultiWriter(os.Stdout, f)
		stderr = io.MultiWriter(os.Stderr, f)
	}
	cmd.Stdout = stdout
	cmd.Stderr = stderr
	cmd.Stdin = os.Stdin
	os.Setenv("CLASSPATH", classpath)
	err = cmd.Start()
	if err != nil {
		logMessage("fatal", fmt.Sprintf("Failed to start %s. %s\n", cmd.Path, err.Error()))
		os.Exit(1)
	}
	return cmd
}

func customClasspath() string {
	return os.Getenv(customClasspathEnv)
}

func createClasspath() string {
	cp := ""
	appendClasspath(&cp, filepath.Join(pluginDir, "*"))
	appendClasspath(&cp, filepath.Join(pluginDir, "libs", "*"))

	additionalLibs := getClassPathForVariable(additionalLibsEnv)
	appendClasspath(&cp, additionalLibs)

	// If user has specified classpath, that will be taken. If not search for IntelliJ and Eclipse out directories before giving up
	userSpecifiedClasspath := getClassPathForVariable(customBuildPathEnv)
	if userSpecifiedClasspath != "" {
		appendClasspath(&cp, userSpecifiedClasspath)
	} else {
		if os.Getenv("SHOULD_BUILD_PROJECT") != "false" {
			build(defaultBuildDir, cp)
		}
		appendClasspath(&cp, defaultBuildDir)
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

	value := os.Getenv(customCompileDirEnv)
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
	javac := getExecPathFrom(javaHome, alternate_java_home, execName("javac"))

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
