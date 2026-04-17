@echo off
setlocal

cd /d "%~dp0"
cd
echo.
echo ==============================
echo Git add / commit / push
echo ==============================
echo.

set /p COMMITMSG=Geef commit message: 

if "%COMMITMSG%"=="" (
    echo.
    echo Geen commit message opgegeven. Afgebroken.
    exit /b 1
)

echo.
git status
echo.

git add .
if errorlevel 1 (
    echo.
    echo git add mislukt.
    exit /b 1
)

git commit -m "%COMMITMSG%"
if errorlevel 1 (
    echo.
    echo git commit mislukt of er was niets te committen.
    exit /b 1
)

for /f "delims=" %%b in ('git branch --show-current') do set BRANCH=%%b

if "%BRANCH%"=="" (
    echo.
    echo Kon actieve branch niet bepalen.
    exit /b 1
)

echo.
echo Push naar origin/%BRANCH%
git push origin %BRANCH%
if errorlevel 1 (
    echo.
    echo git push mislukt.
    exit /b 1
)

echo.
echo Klaar.
echo Branch: %BRANCH%
echo Commit : %COMMITMSG%
echo.

endlocal