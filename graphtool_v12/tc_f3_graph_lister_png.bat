@echo off
setlocal EnableExtensions

REM ============================================================
REM tc_f3_graph_lister_png.bat
REM Voor Total Commander: F3 op .graph/.opn -> tijdelijk PNG renderen ->
REM openen in Total Commander's eigen Lister (/S=L:T4).
REM
REM TC instelling:
REM   Command/Program: C:\pad\naar\tc_f3_graph_lister_png.bat
REM   Parameters:      "%P%N"
REM   Start path:      %P
REM ============================================================

set "SCRIPT_DIR=%~dp0"
set "PYFILE=%SCRIPT_DIR%graph_to_images.py"
set "INPUT=%~1"
set "TMPDIR=%TEMP%\graph_tools_preview"
set "MARGIN=18"
set "PNG_SCALE=2"
set "DOT_RADIUS=5"

if "%INPUT%"=="" (
  echo FOUT: geen bestand ontvangen.
  pause
  exit /b 2
)

if /I not "%~x1"==".graph" if /I not "%~x1"==".opn" (
  REM Geen .graph/.opn: laat TC zelf het bestand in Lister openen als mogelijk.
  if defined COMMANDER_EXE (
    "%COMMANDER_EXE%" /S=L "%INPUT%"
    exit /b 0
  )
  start "" "%INPUT%"
  exit /b 0
)

if not exist "%PYFILE%" (
  echo FOUT: graph_to_images.py niet gevonden: "%PYFILE%"
  pause
  exit /b 1
)

if not exist "%TMPDIR%" mkdir "%TMPDIR%" >nul 2>nul

py "%PYFILE%" "%INPUT%" --mode png --outdir "%TMPDIR%" --scale "%PNG_SCALE%" --margin "%MARGIN%" --dot-radius "%DOT_RADIUS%" --grid no --labels no --edges yes --style clean --crop content >nul
if errorlevel 1 (
  echo FOUT: renderen mislukt.
  echo Bestand: "%INPUT%"
  pause
  exit /b 1
)

set "PNGFILE=%TMPDIR%\%~n1.png"
if not exist "%PNGFILE%" (
  echo FOUT: PNG niet aangemaakt: "%PNGFILE%"
  pause
  exit /b 1
)

if defined COMMANDER_EXE (
  "%COMMANDER_EXE%" /S=L:T4 "%PNGFILE%"
  exit /b 0
)

REM Fallback buiten Total Commander: open met Windows-standaardviewer voor PNG.
start "" "%PNGFILE%"
exit /b 0

