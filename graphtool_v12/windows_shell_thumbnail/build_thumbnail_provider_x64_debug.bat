@echo off
setlocal EnableExtensions
REM Debug build for native .graph thumbnail provider.

set "HERE=%~dp0"
set "SRC=%HERE%src\GraphThumbnailProvider.cpp"
set "DEF=%HERE%src\GraphThumbnailProvider.def"
set "OUTDIR=%HERE%bin\x64"
set "OUTDLL=%OUTDIR%\GraphThumbnailProvider.dll"
set "OBJ=%OUTDIR%\GraphThumbnailProvider.obj"
set "IMPLIB=%OUTDIR%\GraphThumbnailProvider.lib"
set "PDB=%OUTDIR%\GraphThumbnailProvider.pdb"

if not exist "%OUTDIR%" mkdir "%OUTDIR%"

where cl.exe >nul 2>nul
if errorlevel 1 call :setup_msvc
where cl.exe >nul 2>nul
if errorlevel 1 (
  echo ERROR: cl.exe niet gevonden.
  exit /b 1
)

echo Debug building GraphThumbnailProvider.dll x64...
cl.exe /nologo /EHsc /Zi /Od /std:c++17 /DUNICODE /D_UNICODE /c /Fo"%OBJ%" "%SRC%"
if errorlevel 1 exit /b 1

link.exe /nologo /DEBUG /DLL /DEF:"%DEF%" /OUT:"%OUTDLL%" /IMPLIB:"%IMPLIB%" /PDB:"%PDB%" "%OBJ%" gdiplus.lib shlwapi.lib ole32.lib oleaut32.lib uuid.lib advapi32.lib shell32.lib user32.lib gdi32.lib
if errorlevel 1 exit /b 1

echo.
echo DEBUG BUILD OK:
echo   %OUTDLL%
exit /b 0

:setup_msvc
set "VSWHERE=%ProgramFiles(x86)%\Microsoft Visual Studio\Installer\vswhere.exe"
if not exist "%VSWHERE%" exit /b 0
for /f "usebackq tokens=*" %%I in (`"%VSWHERE%" -latest -products * -requires Microsoft.VisualStudio.Component.VC.Tools.x86.x64 -property installationPath`) do set "VSINSTALL=%%I"
if not defined VSINSTALL exit /b 0
if exist "%VSINSTALL%\VC\Auxiliary\Build\vcvars64.bat" call "%VSINSTALL%\VC\Auxiliary\Build\vcvars64.bat"
exit /b 0
