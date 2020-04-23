Param(
  [String]$TaskName # The name of the task to run
)

$javaCommand = "java"
$javacCommand = "javac"
$MINIUM_GAUGE_MVN_VERSION="1.4.3"
$MINIUM_GAUGE_GRADLE_VERSION="1.8.1"

if ("$env:gauge_java_home" -ne "") {
  $javaCommand = "$env:gauge_java_home\bin\$javaCommand"
  $javacCommand = "$env:gauge_java_home\bin\$javacCommand"
}
elseif ("$env:JAVA_HOME" -ne "") {
  $javaCommand = "$env:JAVA_HOME\bin\$javaCommand"
  $javacCommand = "$env:JAVA_HOME\bin\$javacCommand"
}

$version = (Get-Command $javaCommand | Select-Object -ExpandProperty Version).Major | Out-String
if ( "$version" -as [int] -lt 9 ) {
  Write-Output "This version of gauge-java plugin does not support Java versions < 1.9"
  Write-Output "Please upgrade your Java version or use a version of gauge-java <= v0.7.4"
  exit 1;
}


$DefaultBuildDir = "gauge_bin"
$PluginDir = Get-Location
Set-Location $env:GAUGE_PROJECT_ROOT

function ExtractGaugePluginVersion() {
  param(
      $PomData,
      $GaugePluginName
  )
  foreach ($line in $PomData) {
      if($line -match "$GaugePluginName"){
          $is_gauge_plugin_version_entry="true"
      }
      elseif ($is_gauge_plugin_version_entry -match "true")  {
          $gaugeplugin_version=$line  -replace "[^0-9.]", ""
          return $gaugeplugin_version
      }
  }
}

function ValidatePluginsVersion() {
  param(
    $BuildToolName,
    $BuildFileName
  )
  $installed_gauge_java_version=((gauge -v -m | ConvertFrom-Json).plugins | Where-Object { $_.name -eq "java" }).version
  if ( $BuildToolName -match "maven" ) {
      $pom_data=mvn help:effective-pom
      $gauge_java_version=(mvn dependency:tree -Dincludes="com.thoughtworks.gauge:gauge-java" | Where-Object { $_ -match "gauge:gauge-java" }) -replace '[a-zA-Z]+.|[^0-9.]', ''
      $gauge_mvn_version=ExtractGaugePluginVersion -PomData $pom_data -GaugePluginName "gauge-maven-plugin"
      if ( $gauge_mvn_version -lt $MINIUM_GAUGE_MVN_VERSION ) {
        Write-Output "Expected gauge-maven-plugin version to be $MINIUM_GAUGE_MVN_VERSION or greater. $gauge_mvn_version"
      }
  } else {
      $gauge_java_version=(./gradlew -q dependencyInsight --dependency com.thoughtworks | Select-Object -First 1) -replace "[a-zA-Z]+.", ""
      $gauge_gradle_version=(Get-Content .\build.gradle | Where-Object { $_  -match ".*org.gauge.*[0-9.]+" } ) -replace '[a-zA-Z]+.|[^0-9.]', ''
      if ( $gauge_gradle_version -lt $MINIUM_GAUGE_GRADLE_VERSION ) {
        Write-Output "Expected gauge-gradle-plugin version to be $MINIUM_GAUGE_GRADLE_VERSION or greater."
      }
  }

  if ( $installed_gauge_java_version -notmatch $gauge_java_version ) {
    Write-Output "Installed version of gauge-java($installed_gauge_java_version) does not match with dependency gauge-java($gauge_java_version) specified in $BuildFileName file."
  }
 }

function IsMavenProject(){
  Test-Path "pom.xml" -PathType Leaf
}

function IsGradleProject(){
  Test-Path "build.gradle" -PathType Leaf
}

function SetClasspath() {
  if (-not (Test-Path env:gauge_custom_classpath)) {
    if (IsMavenProject) {
      $global:classpath = (mvn -q test-compile gauge:classpath)
    } elseif (IsGradleProject) {
      $global:classpath = (./gradlew -q clean classpath)
    }
  } else {
    $global:classpath = $env:gauge_custom_classpath
  }
}

function AddRunnerInClassPath {
  $global:classpath += "$PluginDir\libs\*"
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

function EscapeSpaceInClasspath {
  $classpath = $global:classpath.Split(";")
  $global:classpath = ""
  foreach ($cp in $classpath.Split(";")) {
      if ($cp.Contains(" ") ) {
        $cp = "`"$cp`"" -replace '\\','\\'
      }
      $global:classpath+="$cp;"
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
    if (IsMavenProject) {
      ValidatePluginsVersion -BuildToolName "maven" -BuildFileName "pom.xml"
    } elseif (IsGradleProject) {
      ValidatePluginsVersion -BuildToolName "gradle" -BuildFileName "build.gradle"
    }
    SetClasspath
    if ("$global:classpath" -eq "") {
      AddRunnerInClassPath
      AddClassPathRequiredForExecution
    }
    if ("$env:GAUGE_DEBUG_OPTS" -ne "" ) {
      $debugArgs = "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=$env:GAUGE_DEBUG_OPTS,timeout=25000"
      Write-Output "`nRunner Ready for Debugging"
    }
    EscapeSpaceInClasspath
    $random = (Get-Random)
    $targetFile = Join-Path "$env:TEMP" "$random.txt"
    Write-Output "-cp $global:classpath `"-Dfile.encoding=UTF-8`" $debugArgs $env:gauge_jvm_args com.thoughtworks.gauge.GaugeRuntime --start" | Out-File $targetFile -Append -Encoding default
    & $javaCommand "@$targetFile"
    exit
  })

if ($tasks.ContainsKey($TaskName)) {
  Invoke-Command $tasks.Get_Item($TaskName)
}
else {
  Write-Output "Options: [init | start]"
}