@echo off
setlocal
cd /d "%~dp0"

echo.
echo Current folder: %CD%
echo.

echo ==== Matching names ====
dir /a /b OpenGraphEd.jar OpenGraphEd.new.jar dist\OpenGraphEd.jar 2>nul

echo.
echo ==== Directory conflicts ====
if exist "OpenGraphEd.jar\NUL" echo OpenGraphEd.jar is a DIRECTORY
if exist "OpenGraphEd.new.jar\NUL" echo OpenGraphEd.new.jar is a DIRECTORY
if exist "dist\OpenGraphEd.jar\NUL" echo dist\OpenGraphEd.jar is a DIRECTORY

echo.
echo ==== Dist folder ====
dir /a dist 2>nul

echo.
echo ==== Write test in project root ====
echo test> __og_write_test.tmp 2>nul
if exist __og_write_test.tmp (
  echo Project root is writable.
  del /q __og_write_test.tmp >nul 2>nul
) else (
  echo Could not create a temporary file in the project root.
)

echo.
echo ==== Write test in dist ====
if not exist dist mkdir dist >nul 2>nul
echo test> dist\__og_write_test.tmp 2>nul
if exist dist\__og_write_test.tmp (
  echo dist\ is writable.
  del /q dist\__og_write_test.tmp >nul 2>nul
) else (
  echo Could not create a temporary file in dist\
)

echo.
pause
