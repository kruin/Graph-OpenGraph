@echo off
setlocal EnableExtensions

REM ============================================================
REM maak_graph_previews.bat
REM Maakt previewbestanden voor .graph/.opn: *.preview.png en/of *.preview.svg
REM Werkt vanuit de current directory. Geschikt voor Total Commander.
REM
REM Gebruik:
REM   maak_graph_previews.bat              = alle .graph in current dir
REM   maak_graph_previews.bat "%L"          = TC selectie via lijstbestand
REM   maak_graph_previews.bat file.graph|file.opn   = één bestand
REM ============================================================

REM ===== CONFIG =================================================
set "MODE=both"
set "PNG_SCALE=1"
set "MARGIN=28"
set "DOT_RADIUS=6"
set "SHOW_GRID=no"
set "SHOW_LABELS=no"
set "SHOW_EDGES=yes"
set "STYLE=clean"
set "CROP=content"
set "PREVIEW_SUFFIX=.preview"
REM OUTPUT_DIR leeg = naast de .graph. Voor aparte map bv: __graph_preview
set "OUTPUT_DIR="
set "PAUSE_AT_END=yes"
REM ==============================================================

set "SCRIPT_DIR=%~dp0"
set "PYFILE=%SCRIPT_DIR%graph_to_images.py"

if not exist "%PYFILE%" (
  echo FOUT: graph_to_images.py niet gevonden naast deze batch.
  echo Verwacht: "%PYFILE%"
  if /I "%PAUSE_AT_END%"=="yes" pause
  exit /b 1
)

where py >nul 2>nul
if errorlevel 1 (
  echo FOUT: Python launcher 'py' niet gevonden.
  echo Installeer Python of pas de batch aan naar python.exe.
  if /I "%PAUSE_AT_END%"=="yes" pause
  exit /b 1
)

set "OUTARGS="
if not "%OUTPUT_DIR%"=="" set "OUTARGS=--outdir %OUTPUT_DIR%"

if "%~1"=="" goto currentdir

REM TC %L geeft meestal een tekstbestand met geselecteerde paden.
if exist "%~1" (
  for %%A in ("%~1") do set "EXT=%%~xA"
  if /I not "%EXT%"==".graph" if /I not "%EXT%"==".opn" goto listmode
)

goto directargs

:currentdir
echo Preview maken voor alle .graph/.opn-bestanden in:
echo %CD%
py "%PYFILE%" --current-dir --mode "%MODE%" %OUTARGS% --scale "%PNG_SCALE%" --margin "%MARGIN%" --dot-radius "%DOT_RADIUS%" --grid "%SHOW_GRID%" --labels "%SHOW_LABELS%" --edges "%SHOW_EDGES%" --style "%STYLE%" --crop "%CROP%" --suffix "%PREVIEW_SUFFIX%"
goto done

:listmode
echo Preview maken voor TC-selectie uit lijstbestand:
echo %~1
py "%PYFILE%" --list "%~1" --mode "%MODE%" %OUTARGS% --scale "%PNG_SCALE%" --margin "%MARGIN%" --dot-radius "%DOT_RADIUS%" --grid "%SHOW_GRID%" --labels "%SHOW_LABELS%" --edges "%SHOW_EDGES%" --style "%STYLE%" --crop "%CROP%" --suffix "%PREVIEW_SUFFIX%"
goto done

:directargs
echo Preview maken voor opgegeven bestand(en):
py "%PYFILE%" %* --mode "%MODE%" %OUTARGS% --scale "%PNG_SCALE%" --margin "%MARGIN%" --dot-radius "%DOT_RADIUS%" --grid "%SHOW_GRID%" --labels "%SHOW_LABELS%" --edges "%SHOW_EDGES%" --style "%STYLE%" --crop "%CROP%" --suffix "%PREVIEW_SUFFIX%"
goto done

:done
set "RC=%ERRORLEVEL%"
echo.
echo Klaar. Returncode: %RC%
if /I "%PAUSE_AT_END%"=="yes" pause
exit /b %RC%

