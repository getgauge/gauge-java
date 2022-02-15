package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"io"
	"io/ioutil"
	"log"
	"os"
	"os/exec"
	"path/filepath"
	"runtime"
)

const (
	CGO_ENABLED = "CGO_ENABLED"
)

const (
	dotGauge          = ".gauge"
	plugins           = "plugins"
	GOARCH            = "GOARCH"
	GOOS              = "GOOS"
	X86               = "386"
	ARM64             = "arm64"
	X86_64            = "amd64"
	DARWIN            = "darwin"
	LINUX             = "linux"
	WINDOWS           = "windows"
	bin               = "bin"
	newDirPermissions = 0755
	gauge             = "gauge"
	gaugeJava         = "gauge-java"
	deploy            = "deploy"
	targetDir         = "target"
	libsDir           = "libs"
	jarExt            = ".jar"
)

var deployDir = filepath.Join(deploy, gaugeJava)

func isExecMode(mode os.FileMode) bool {
	return (mode & 0111) != 0
}

func mirrorFile(src, dst string) error {
	sfi, err := os.Stat(src)
	if err != nil {
		return err
	}
	if sfi.Mode()&os.ModeType != 0 {
		log.Fatalf("mirrorFile can't deal with non-regular file %s", src)
	}
	dfi, err := os.Stat(dst)
	if err == nil &&
		isExecMode(sfi.Mode()) == isExecMode(dfi.Mode()) &&
		(dfi.Mode()&os.ModeType == 0) &&
		dfi.Size() == sfi.Size() &&
		dfi.ModTime().Unix() == sfi.ModTime().Unix() {
		// Seems to not be modified.
		return nil
	}

	dstDir := filepath.Dir(dst)
	if err := os.MkdirAll(dstDir, newDirPermissions); err != nil {
		return err
	}

	df, err := os.Create(dst)
	if err != nil {
		return err
	}
	sf, err := os.Open(src)
	if err != nil {
		return err
	}
	defer sf.Close()

	n, err := io.Copy(df, sf)
	if err == nil && n != sfi.Size() {
		err = fmt.Errorf("copied wrong size for %s -> %s: copied %d; want %d", src, dst, n, sfi.Size())
	}
	cerr := df.Close()
	if err == nil {
		err = cerr
	}
	if err == nil {
		err = os.Chmod(dst, sfi.Mode())
	}
	if err == nil {
		err = os.Chtimes(dst, sfi.ModTime(), sfi.ModTime())
	}
	return err
}

func mirrorDir(src, dst string) error {
	err := filepath.Walk(src, func(path string, fi os.FileInfo, err error) error {
		if err != nil {
			return err
		}
		if fi.IsDir() {
			return nil
		}
		suffix, err := filepath.Rel(src, path)
		if err != nil {
			return fmt.Errorf("Failed to find Rel(%q, %q): %v", src, path, err)
		}
		return mirrorFile(path, filepath.Join(dst, suffix))
	})
	return err
}

func runProcess(command string, workingDir string, arg ...string) {
	cmd := exec.Command(command, arg...)
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	cmd.Dir = workingDir
	log.Printf("Execute %v\n", cmd.Args)
	err := cmd.Run()
	if err != nil {
		panic(err)
	}
}

func compileGoPackage() {
	destDir := filepath.Join(bin, fmt.Sprintf("%s_%s", getGOOS(), getGOARCH()))
	err := os.MkdirAll(destDir, newDirPermissions)
	if err != nil {
		panic(err)
	}
	runProcess("go", ".", "build", "-ldflags=-s -w", "-o", destDir)
}

// key will be the source file and value will be the target
func copyFiles(files map[string]string, installDir string) {
	for src, dst := range files {
		base := filepath.Base(src)
		installDst := filepath.Join(installDir, dst)
		log.Printf("Copying %s -> %s\n", src, installDst)
		stat, err := os.Stat(src)
		if err != nil {
			panic(err)
		}
		if stat.IsDir() {
			err = mirrorDir(src, installDst)
		} else {
			err = mirrorFile(src, filepath.Join(installDst, base))
		}
		if err != nil {
			panic(err)
		}
	}
}

func copyGaugeJavaFiles(destDir string) {
	files := make(map[string]string)
	if getGOOS() == "windows" {
		files[filepath.Join(getBinDir(), "gauge-java.exe")] = ""
	} else {
		files[filepath.Join(getBinDir(), gaugeJava)] = ""
	}
	files[filepath.Join("libs")] = filepath.Join("libs")
	files[filepath.Join("java.json")] = ""
	files[filepath.Join(targetDir, gaugeJava+"-"+getGaugeJavaVersion()+jarExt)] = "libs"
	copyFiles(files, destDir)
}

func getGaugeJavaVersion() string {
	javaRunnerProperties, err := getPluginProperties("java.json")
	if err != nil {
		panic(fmt.Sprintf("Failed to get gauge java properties file. %s", err))
	}
	return javaRunnerProperties["version"].(string)
}

func getBinDir() string {
	if *binDir == "" {
		return filepath.Join(bin, fmt.Sprintf("%s_%s", getGOOS(), getGOARCH()))
	}
	return filepath.Join(bin, *binDir)
}

func setEnv(envVariables map[string]string) {
	for k, v := range envVariables {
		os.Setenv(k, v)
	}
}

var install = flag.Bool("install", false, "Install to the specified prefix")
var pluginInstallPrefix = flag.String("plugin-prefix", "", "Specifies the prefix where gauge plugins will be installed")
var distro = flag.Bool("distro", false, "Creates distributables for gauge java")
var test = flag.Bool("test", false, "Runs tests")
var allPlatforms = flag.Bool("all-platforms", false, "Compiles or creates distributables for all platforms windows, linux, darwin both x86 and x86_64")
var binDir = flag.String("bin-dir", "", "Specifies OS_PLATFORM specific binaries to install when cross compiling")
var profile = flag.String("profile", "", "Specify profile to use while building")

var (
	platformEnvs = []map[string]string{
		{GOARCH: ARM64, GOOS: DARWIN, CGO_ENABLED: "0"},
		{GOARCH: X86_64, GOOS: DARWIN, CGO_ENABLED: "0"},
		{GOARCH: X86, GOOS: LINUX, CGO_ENABLED: "0"},
		{GOARCH: X86_64, GOOS: LINUX, CGO_ENABLED: "0"},
		{GOARCH: ARM64, GOOS: LINUX, CGO_ENABLED: "0"},
		{GOARCH: X86, GOOS: WINDOWS, CGO_ENABLED: "0"},
		{GOARCH: X86_64, GOOS: WINDOWS, CGO_ENABLED: "0"},
	}
)

func getPluginProperties(jsonPropertiesFile string) (map[string]interface{}, error) {
	pluginPropertiesJson, err := ioutil.ReadFile(jsonPropertiesFile)
	if err != nil {
		fmt.Printf("Could not read %s: %s\n", filepath.Base(jsonPropertiesFile), err)
		return nil, err
	}
	var pluginJson interface{}
	if err = json.Unmarshal([]byte(pluginPropertiesJson), &pluginJson); err != nil {
		fmt.Printf("Could not read %s: %s\n", filepath.Base(jsonPropertiesFile), err)
		return nil, err
	}
	return pluginJson.(map[string]interface{}), nil
}

func main() {
	flag.Parse()

	if *install {
		updatePluginInstallPrefix()
		installGaugeJava(*pluginInstallPrefix)
	} else if *distro {
		createGaugeDistro(*allPlatforms)
	} else if *test {
		compileGoPackage()
		runMavenTests()
	} else {
		compileGaugeJava()
	}
}

func runMavenTests() {
	runCommand("mvn", "test")
}

func compileGaugeJava() {
	buildGaugeJavaJar()
	if *allPlatforms {
		compileGaugeJavaAcrossPlatforms()
	} else {
		compileGoPackage()
	}
}

func createGaugeDistro(forAllPlatforms bool) {
	os.RemoveAll(deploy)
	if forAllPlatforms {
		for _, platformEnv := range platformEnvs {
			setEnv(platformEnv)
			fmt.Printf("Creating distro for platform => OS:%s ARCH:%s \n", platformEnv[GOOS], platformEnv[GOARCH])
			createDistro()
		}
	} else {
		createDistro()
	}
}

func createDistro() {
	packageName := fmt.Sprintf("%s-%s-%s.%s", gaugeJava, getGaugeJavaVersion(), getGOOS(), getArch())
	distroDir := filepath.Join(deploy, packageName)
	copyGaugeJavaFiles(distroDir)
	createZipFromUtil(deploy, packageName)
	os.RemoveAll(distroDir)
}

func runCommand(command string, arg ...string) {
	cmd := exec.Command(command, arg...)
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	log.Printf("Execute %v\n", cmd.Args)
	err := cmd.Run()
	if err != nil {
		panic(err)
	}
}

func createZipFromUtil(dir, name string) {
	wd, _ := os.Getwd()
	os.Chdir(filepath.Join(dir, name))
	runCommand("zip", "-r", filepath.Join("..", name+".zip"), ".")
	os.Chdir(wd)
}

func compileGaugeJavaAcrossPlatforms() {
	for _, platformEnv := range platformEnvs {
		setEnv(platformEnv)
		fmt.Printf("Compiling for platform => OS:%s ARCH:%s \n", platformEnv[GOOS], platformEnv[GOARCH])
		compileGoPackage()
	}
}

func buildGaugeJavaJar() {
	os.RemoveAll(targetDir)
	os.RemoveAll(libsDir)
	if *profile != "" {
		runCommand("mvn", "-q", "package", "-P", *profile)
	} else {
		runCommand("mvn", "-q", "package")
	}
}

func installGaugeJava(installPrefix string) {
	os.RemoveAll(deployDir)
	copyGaugeJavaFiles(deployDir)
	javaRunnerInstallPath := filepath.Join(installPrefix, "java", getGaugeJavaVersion())
	log.Printf("Copying %s -> %s\n", deployDir, javaRunnerInstallPath)
	mirrorDir(deployDir, javaRunnerInstallPath)
}

func updatePluginInstallPrefix() {
	if *pluginInstallPrefix == "" {
		if runtime.GOOS == "windows" {
			*pluginInstallPrefix = os.Getenv("APPDATA")
			if *pluginInstallPrefix == "" {
				panic(fmt.Errorf("Failed to find AppData directory"))
			}
			*pluginInstallPrefix = filepath.Join(*pluginInstallPrefix, gauge, plugins)
		} else {
			userHome := getUserHome()
			if userHome == "" {
				panic(fmt.Errorf("Failed to find User Home directory"))
			}
			*pluginInstallPrefix = filepath.Join(userHome, dotGauge, plugins)
		}
	}
}

func getUserHome() string {
	return os.Getenv("HOME")
}

func getArch() string {
	arch := getGOARCH()
	if arch == X86 {
		return "x86"
	}
	return "x86_64"
}

func getGOARCH() string {
	goArch := os.Getenv(GOARCH)
	if goArch == "" {
		return runtime.GOARCH

	}
	return goArch
}

func getGOOS() string {
	os := os.Getenv(GOOS)
	if os == "" {
		return runtime.GOOS
	}
	return os
}
