@echo off
setlocal

cd /d "%~dp0"

echo.
echo ==============================
echo Nieuwe branch maken
echo ==============================
echo.
echo Kies type:
echo   1 = feature
echo   2 = repair
echo   3 = experiment
echo   4 = cleanup
echo.

set /p TYPECHOICE=Type nummer: 
set /p NAME=Branch naam zonder prefix (bijv OpenGraphGrid): 

if "%NAME%"=="" (
    echo.
    echo Geen naam opgegeven. Afgebroken.
    exit /b 1
)

set PREFIX=
if "%TYPECHOICE%"=="1" set PREFIX=feature
if "%TYPECHOICE%"=="2" set PREFIX=repair
if "%TYPECHOICE%"=="3" set PREFIX=experiment
if "%TYPECHOICE%"=="4" set PREFIX=cleanup

if "%PREFIX%"=="" (
    echo.
    echo Ongeldige keuze. Afgebroken.
    exit /b 1
)

set BRANCH=%PREFIX%/%NAME%

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
echo Nieuwe branch maken: %BRANCH%
git checkout -b %BRANCH%
if errorlevel 1 (
    echo Branch maken mislukt. Bestaat deze al?
    exit /b 1
)

echo.
echo Klaar.
echo Actieve branch: %BRANCH%
echo.

endlocal
