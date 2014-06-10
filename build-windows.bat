@ECHO OFF
setlocal
set programName="gauge"
if "%1" == "install" (
	md "%APPDATA%\%programName%\lib\%programName%\java\libs"
	copy "libs\*" "%APPDATA%\%programName%\lib\%programName%\java\libs"
	copy "build\jar\*" "%APPDATA%\%programName%\lib\%programName%\java"
	md "%APPDATA%\%programName%\bin"
	copy "%programName%-java.exe" "%APPDATA%\%programName%\bin"
	md "%APPDATA%\%programName%\share\%programName%\languages"
	copy "java.json" "%APPDATA%\%programName%\share\%programName%\languages"
	md "%APPDATA%\%programName%\share\%programName%\skel\java"
	md "%APPDATA%\%programName%\share\%programName%\skel\env"
	copy "skel\StepImplementation.java" "%APPDATA%\%programName%\share\%programName%\skel\java"
	copy "skel\java.properties" "%APPDATA%\%programName%\share\%programName%\skel\env"
	echo Installation successful
	GOTO success
) else (
	SET GOPATH="%cd%"
	cd src
	call go build || goto fail
	copy src.exe ..\%programName%-java.exe
	cd ..
	call ant jar || goto fail
	GOTO success
)
:fail
EXIT /b %errorlevel%

:success

endlocal
