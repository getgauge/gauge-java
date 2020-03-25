Param(
  [String]$TaskName # The name of the task to run
)

$DefaultBuildDir = "gauge_bin"
$PluginDir = Get-Location
$global:classpath = $env:gauge_custom_classpath
Set-Location $env:GAUGE_PROJECT_ROOT

$javaCommand = "java"
$javacCommand = "javac"

if ("$env:gauge_java_home" -ne "") {
    $javaCommand = "$env:gauge_java_home\bin\$javaCommand"
    $javacCommand = "$env:gauge_java_home\bin\$javacCommand"
}
elseif ("$env:JAVA_HOME" -ne "") {
    $javaCommand = "$env:JAVA_HOME\bin\$javaCommand"
    $javacCommand = "$env:JAVA_HOME\bin\$javacCommand"
}

function AddRunnerInClassPath {
  $global:classpath = $global:classpath + "$PluginDir\*" + ";" + "$PluginDir\libs\*"
}

function GetAdditionalPath() {
  param(
    $DirNames
  )
  if ("$DirNames" -eq "") {
    return ""
  }
  $libs = ""
  foreach ($dir in $DirNames.Split(",")) {
    $lib = Resolve-Path -Path $dir
    $libs = "$lib;$libs"
  }
  return $libs
}

function ListFiles {
  param(
    $targetFile
  )

  $dirs = "src"
  if ("$env:gauge_custom_compile_dir" -ne "") {
    $dirs = $env:gauge_custom_compile_dir
  }
  foreach ($dir in $dirs.Split(",")) {
    Get-ChildItem -Recurse -File -Include "*.java" -Path $dir.Trim() |
    ForEach-Object { Write-Output `"$_`" } |
    ForEach-Object { Write-Output $_.Replace('\', '\\') } |
    Out-File $targetFile -Append -Encoding default
  }
}

function BuildProject() {
  param(
    $classpath
  )
  Remove-Item -Recurse -Force $DefaultBuildDir -ErrorAction SilentlyContinue
  mkdir $DefaultBuildDir > $null
  $random = (Get-Random)
  $targetFile = Join-Path "$env:TEMP" "$random.txt"
  ListFiles $targetFile
  & $javacCommand -cp `"$global:classpath`" -encoding UTF-8 -d $DefaultBuildDir `"@$targetFile`"
  Remove-Item -Force $targetFile
}

function AddClassPathRequiredForExecution() {
  $additional_classpath = GetAdditionalPath "$env:gauge_additional_libs"
  if ("$additional_classpath" -ne "" ) {
    $global:classpath = "$global:classpath;$additional_classpath"
  }

  $user_classpath = GetAdditionalPath $env:gauge_custom_build_path
  if ("$user_classpath" -ne "" ) {
    $global:classpath = $global:classpath + ";" + $user_classpath
  }
  else {
    if ("$env:SHOULD_BUILD_PROJECT" -ne "false") {
      BuildProject
    }
    $buildDir = Resolve-Path -Path $DefaultBuildDir
    $global:classpath = $global:classpath + ";" + $buildDir
  }
}

$tasks = @{ }
$tasks.Add('init', {
    if ("$global:classpath" -eq "" ) { AddRunnerInClassPath }
    $env:CLASSPATH = $global:classpath
    & $javaCommand com.thoughtworks.gauge.GaugeRuntime --init
    exit
  })

$tasks.Add('start', {
    if ("$global:classpath" -eq "") {
      AddRunnerInClassPath
      AddClassPathRequiredForExecution
    }
    $env:CLASSPATH = $global:classpath
    if ("$env:GAUGE_DEBUG_OPTS" -ne "" ) {
      $debugArgs = "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=$env:GAUGE_DEBUG_OPTS,timeout=25000"
      Write-Output "`nRunner Ready for Debugging"
    }
    & $javaCommand `"-Dfile.encoding=UTF-8`" $debugArgs $env:gauge_jvm_args com.thoughtworks.gauge.GaugeRuntime --start
    exit
  })

if ($tasks.ContainsKey($TaskName)) {
  Invoke-Command $tasks.Get_Item($TaskName)
}
else {
  Write-Output "Options: [init | start]"
}