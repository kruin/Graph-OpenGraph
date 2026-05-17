@echo off
setlocal EnableExtensions
REM Build native C++ thumbnail provider for .graph files.
REM Requires Visual Studio Build Tools with Desktop development with C++.
REM v8: separate cl /c and link.exe steps; avoids /OUT path being parsed as /O... compiler options.

set "HERE=%~dp0"
set "SRC=%HERE%src\GraphThumbnailProvider.cpp"
set "DEF=%HERE%src\GraphThumbnailProvider.def"
set "OUTDIR=%HERE%bin\x64"
set "OUTDLL=%OUTDIR%\GraphThumbnailProvider.dll"
set "OBJ=%OUTDIR%\GraphThumbnailProvider.obj"
set "IMPLIB=%OUTDIR%\GraphThumbnailProvider.lib"

if not exist "%OUTDIR%" mkdir "%OUTDIR%"

if not exist "%SRC%" (
  echo.
  echo ERROR: sourcebestand niet gevonden:
  echo   "%SRC%"
  echo Controleer of de map windows_shell_thumbnail\src aanwezig is.
  exit /b 1
)

if not exist "%DEF%" (
  echo.
  echo ERROR: def-bestand niet gevonden:
  echo   "%DEF%"
  exit /b 1
)

where cl.exe >nul 2>nul
if errorlevel 1 (
  call :setup_msvc
)

where cl.exe >nul 2>nul
if errorlevel 1 (
  echo.
  echo ERROR: cl.exe niet gevonden.
  echo Installeer Visual Studio Build Tools en kies:
  echo   Desktop development with C++
  echo Of start deze batch vanuit "x64 Native Tools Command Prompt for VS".
  echo.
  exit /b 1
)

where link.exe >nul 2>nul
if errorlevel 1 (
  echo.
  echo ERROR: link.exe niet gevonden.
  exit /b 1
)

echo Building GraphThumbnailProvider.dll x64...
echo Source:
echo   "%SRC%"
echo Output:
echo   "%OUTDLL%"

if exist "%OBJ%" del /q "%OBJ%" >nul 2>nul
if exist "%OUTDLL%" del /q "%OUTDLL%" >nul 2>nul

cl.exe /nologo /EHsc /O2 /std:c++17 /DUNICODE /D_UNICODE /c /Fo"%OBJ%" "%SRC%"
if errorlevel 1 (
  echo.
  echo COMPILE FAILED.
  exit /b 1
)

link.exe /nologo /DLL /DEF:"%DEF%" /OUT:"%OUTDLL%" /IMPLIB:"%IMPLIB%" "%OBJ%" gdiplus.lib shlwapi.lib ole32.lib oleaut32.lib uuid.lib advapi32.lib shell32.lib user32.lib gdi32.lib
if errorlevel 1 (
  echo.
  echo LINK FAILED.
  exit /b 1
)

if not exist "%OUTDLL%" (
  echo.
  echo BUILD FAILED: DLL niet aangemaakt:
  echo   "%OUTDLL%"
  exit /b 1
)

echo.
echo BUILD OK:
echo   %OUTDLL%
exit /b 0

:setup_msvc
set "VSWHERE=%ProgramFiles(x86)%\Microsoft Visual Studio\Installer\vswhere.exe"
if not exist "%VSWHERE%" exit /b 0

for /f "usebackq tokens=*" %%I in (`"%VSWHERE%" -latest -products * -requires Microsoft.VisualStudio.Component.VC.Tools.x86.x64 -property installationPath`) do set "VSINSTALL=%%I"
if not defined VSINSTALL exit /b 0

if exist "%VSINSTALL%\VC\Auxiliary\Build\vcvars64.bat" (
  call "%VSINSTALL%\VC\Auxiliary\Build\vcvars64.bat"
)
exit /b 0
