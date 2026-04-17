@echo off
setlocal

cd /d "%~dp0"

echo.
echo =========================================
echo Integration branch maken en features mergen
echo =========================================
echo.

set /p INAME=Naam integration branch zonder prefix (bijv opengraphgrid-projecties): 
set /p BR1=Eerste branch (bijv feature/OpenGraphGrid): 
set /p BR2=Tweede branch (bijv feature/projecties): 

if "%INAME%"=="" (
    echo.
    echo Geen integration naam opgegeven. Afgebroken.
    exit /b 1
)

if "%BR1%"=="" (
    echo.
    echo Eerste branch ontbreekt. Afgebroken.
    exit /b 1
)

if "%BR2%"=="" (
    echo.
    echo Tweede branch ontbreekt. Afgebroken.
    exit /b 1
)

set IBRANCH=integrate/%INAME%

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
echo Nieuwe integration branch maken: %IBRANCH%
git checkout -b %IBRANCH%
if errorlevel 1 (
    echo Branch maken mislukt. Bestaat deze al?
    exit /b 1
)

echo.
echo Merge van %BR1%
git merge %BR1%
if errorlevel 1 (
    echo.
    echo Conflict of mergefout bij %BR1%.
    echo Los conflicten op, doe git add ., daarna git commit.
    exit /b 1
)

echo.
echo Merge van %BR2%
git merge %BR2%
if errorlevel 1 (
    echo.
    echo Conflict of mergefout bij %BR2%.
    echo Los conflicten op, doe git add ., daarna git commit.
    exit /b 1
)

echo.
echo Push integration branch...
git push -u origin %IBRANCH%
if errorlevel 1 (
    echo git push mislukt.
    exit /b 1
)

echo.
echo Klaar.
echo Integration branch: %IBRANCH%
echo Test nu build en run op deze branch.
echo.

endlocal
