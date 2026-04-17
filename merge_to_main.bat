@echo off
setlocal

cd /d "%~dp0"

echo.
echo ==============================
echo Branch mergen naar main
echo ==============================
echo.

set /p SOURCEBRANCH=Branch om naar main te mergen: 

if "%SOURCEBRANCH%"=="" (
    echo.
    echo Geen branch opgegeven. Afgebroken.
    exit /b 1
)

echo.
echo Naar main...
git checkout main
if errorlevel 1 (
    echo Kon niet naar main schakelen.
    exit /b 1
)

echo.
echo Laatste main ophalen...
git pull origin main
if errorlevel 1 (
    echo git pull origin main mislukt.
    exit /b 1
)

echo.
echo Merge van %SOURCEBRANCH% naar main...
git merge %SOURCEBRANCH%
if errorlevel 1 (
    echo.
    echo Conflict of mergefout.
    echo Los conflicten op, doe git add ., daarna git commit.
    exit /b 1
)

echo.
echo Push main...
git push origin main
if errorlevel 1 (
    echo git push origin main mislukt.
    exit /b 1
)

echo.
echo Klaar.
echo %SOURCEBRANCH% is gemerged naar main.
echo.

endlocal
