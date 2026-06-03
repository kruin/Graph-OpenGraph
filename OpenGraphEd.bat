@echo off
setlocal EnableExtensions
set "APP_DIR=%~dp0"
cd /d "%APP_DIR%"

rem Direct launcher generated for OpenGraphEd v4.24.2.
rem No wrapper calls. No recursion. No %TEMP%. No PowerShell.
rem Uses relative classpath "out;." to avoid a Java launcher quoting bug
rem when %%~dp0 ends in a backslash before a closing quote.

set "JAVA_HOME_CLEAN=%JAVA_HOME:"=%"
set "JAVA_CMD=java.exe"
if defined JAVA_HOME_CLEAN if exist "%JAVA_HOME_CLEAN%\bin\java.exe" set "JAVA_CMD=%JAVA_HOME_CLEAN%\bin\java.exe"

if exist "out\OpenGraphEdFrame.class" (
  "%JAVA_CMD%" -cp "out;." OpenGraphEdFrame %*
  exit /b %errorlevel%
)

if exist "dist\OpenGraphEd.jar" (
  "%JAVA_CMD%" -jar "dist\OpenGraphEd.jar" %*
  exit /b %errorlevel%
)

if exist "OpenGraphEd.jar" (
  "%JAVA_CMD%" -jar "OpenGraphEd.jar" %*
  exit /b %errorlevel%
)

echo No compiled classes or jar found. Run build.bat first.
exit /b 1
