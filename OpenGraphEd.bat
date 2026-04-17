@echo off
setlocal
cd /d "%~dp0"

if exist out\OpenGraphEdFrame.class (
    start "" javaw -cp "%~dp0out" OpenGraphEdFrame %*
    exit /b 0
)

if exist dist\OpenGraphEd.jar (
    start "" javaw -jar "%~dp0dist\OpenGraphEd.jar" %*
    exit /b 0
)

if exist OpenGraphEd.jar (
    start "" javaw -jar "%~dp0OpenGraphEd.jar" %*
    exit /b 0
)

if exist OpenGraphEd.new.jar (
    start "" javaw -jar "%~dp0OpenGraphEd.new.jar" %*
    exit /b 0
)

echo No compiled classes or jar found. Run build.bat first.
exit /b 1
