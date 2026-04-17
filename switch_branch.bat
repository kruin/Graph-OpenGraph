@echo off
setlocal

cd /d "%~dp0"

echo.
echo ==============================
echo Wissel branch
echo ==============================
echo.

git branch
echo.

set /p TARGET=Branch om naar te schakelen: 

if "%TARGET%"=="" (
    echo.
    echo Geen branch opgegeven. Afgebroken.
    exit /b 1
)

git checkout %TARGET%
if errorlevel 1 (
    echo.
    echo Overschakelen mislukt.
    exit /b 1
)

echo.
echo Actieve branch:
git branch --show-current
echo.

endlocal
