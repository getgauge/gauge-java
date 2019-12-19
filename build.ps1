Param(
    [String]$TaskName # The name of the task to run
)

# Define the tasks
$tasks = @{}

$tasks.Add('build', @{
        description = "Compiles java soruce code. Create jars, copy dependencies";
        script      = {
            clean
            Write-Output "Packaging gauge-java..."
            mvn -q package
            Write-Output "Copying  dependencies to libs..."
            mvn dependency:copy-dependencies -DoutputDirectory=libs -DexcludeTransitive=true -DincludeScope=runtime
            Copy-Item -Recurse target/gauge-java-$(version).jar libs
        }
    })


$tasks.Add('package', @{
        description = "Generate gauge-java plugin zip file";
        script      = {
            Invoke-Command $tasks.Get_Item("build").script
            Remove-Item -Force -Recurse deploy  -ErrorAction SilentlyContinue
            mkdir -p deploy > $null
            Copy-Item bin/launcher.* deploy
            Copy-Item java.json deploy
            Copy-Item -Recurse libs deploy
            Remove-Item -Recurse -Force artifacts  -ErrorAction SilentlyContinue
            mkdir artifacts > $null
            $version = version
            $src = Join-Path -Path (Get-Location).Path -ChildPath "deploy"
            $artifacts = Join-Path -Path  (Get-Location).Path -ChildPath "artifacts"
            $dest = Join-Path -Path $artifacts -ChildPath "gauge-java-$version.zip"
            Add-Type -Assembly "System.IO.Compression.FileSystem" ;
            [System.IO.Compression.ZipFile]::CreateFromDirectory($src, $dest)
        }
    })


$tasks.Add('install', @{
        description = "Install's gauge-java plugin from the files in artifacts dir";
        script      = {
            Invoke-Command $tasks.Get_Item("package").script
            $version = version
            gauge install java -f ".\artifacts\gauge-java-$version.zip"
        }
    })


$tasks.Add('uninstall', @{
        description = "UnInstall gauge-java plugin's current version";
        script      = {
            $version = version
            gauge uninstall java -v $version
        }
    })



$tasks.Add('forceinstall', @{
        description = "Insatall gauge-ts plugin after uninstall the current version";
        script      = {
            Invoke-Command $tasks.Get_Item("uninstall").script
            Invoke-Command $tasks.Get_Item("install").script
        }
    })


# Helper functions
function version {
    $runnerManifest = Get-Content .\java.json | Out-String | ConvertFrom-Json
    $version = $runnerManifest.version
    return $version
}

function clean {
    $dirs = "deploy", "target", "libs", "artifacts"
    foreach ($dir in $dirs) {
        if (Test-Path $dir) {
            Remove-Item -Recurse -Force $dir
        }
    }
}

# Some helpful strings for formatting output
$indent = (" " * 4);
$spacer = ("-" * 40);

function DisplayHelpText {
    $help_text = Get-Help $MyInvocation.ScriptName
    $syn = $help_text.Synopsis
    Write-Output "build.ps1 - runtask TaskName"
    DisplayTaskList
}

function DisplayTaskList {
    Write-Output "`nList of Tasks:`n$spacer"
    foreach ($task in $tasks.GetEnumerator()) {
        Write-Output "$indent$($task.Key)"
        Write-Output "$($indent * 2)$($task.Value.description)"
    }
}

# Now process the given task name
if (-not $taskname) {
    DisplayHelpText
    exit
}
$task = $tasks.Get_Item($taskname)
if ($task) {
    Invoke-Command $task.script
}
else {
    Write-Output "'$taskname' is not a valid task name."
    DisplayTaskList
}