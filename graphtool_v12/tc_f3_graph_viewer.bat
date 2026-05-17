@echo off

setlocal EnableExtensions

chcp 65001 >nul



REM ============================================================

REM Total Commander F3-wrapper voor .graph/.opn-bestanden

REM ------------------------------------------------------------

REM Stel deze batch in als Viewer/F3-programma in Total Commander.

REM

REM Gedrag:

REM   - F3 op *.graph/*.opn  -> render direct in Graph Viewer

REM   - F3 op anders   -> val terug naar Total Commander Lister

REM                      via %COMMANDER_EXE% /S=L

REM

REM Gebruik in Total Commander:

REM   Command/Program:  C:\pad\naar\tc_f3_graph_viewer.bat

REM   Parameters:       "%P%N"

REM   Start path:       %P

REM ============================================================



set "SCRIPT_DIR=%~dp0"

set "FILE=%~1"



if "%FILE%"=="" goto NOFILE



REM Alleen .graph/.opn-bestanden doorsturen naar de renderer.

if /I "%~x1"==".graph" goto VIEW_GRAPH
if /I "%~x1"==".opn" goto VIEW_GRAPH



REM Voor alle andere bestanden: gebruik TC's eigen Lister.

goto FALLBACK_LISTER



:VIEW_GRAPH

if not exist "%FILE%" goto NOTFOUND

call "%SCRIPT_DIR%view_graph.bat" "%FILE%"

exit /b %ERRORLEVEL%



:FALLBACK_LISTER

set "TCEXE="



REM Total Commander zet normaliter COMMANDER_EXE voor programma's die vanuit TC worden gestart.

if defined COMMANDER_EXE (

    if exist "%COMMANDER_EXE%" set "TCEXE=%COMMANDER_EXE%"

)



REM Fallback via COMMANDER_PATH.

if not defined TCEXE if defined COMMANDER_PATH (

    if exist "%COMMANDER_PATH%\TOTALCMD64.EXE" set "TCEXE=%COMMANDER_PATH%\TOTALCMD64.EXE"

    if not defined TCEXE if exist "%COMMANDER_PATH%\TOTALCMD.EXE" set "TCEXE=%COMMANDER_PATH%\TOTALCMD.EXE"

)



REM Fallback via veelgebruikte installatiemappen.

if not defined TCEXE if exist "%ProgramFiles%\totalcmd\TOTALCMD64.EXE" set "TCEXE=%ProgramFiles%\totalcmd\TOTALCMD64.EXE"

if not defined TCEXE if exist "%ProgramFiles%\totalcmd\TOTALCMD.EXE" set "TCEXE=%ProgramFiles%\totalcmd\TOTALCMD.EXE"

if not defined TCEXE if exist "%ProgramFiles(x86)%\totalcmd\TOTALCMD64.EXE" set "TCEXE=%ProgramFiles(x86)%\totalcmd\TOTALCMD64.EXE"

if not defined TCEXE if exist "%ProgramFiles(x86)%\totalcmd\TOTALCMD.EXE" set "TCEXE=%ProgramFiles(x86)%\totalcmd\TOTALCMD.EXE"



if defined TCEXE (

    start "" "%TCEXE%" /S=L "%FILE%"

    exit /b 0

)



REM Laatste fallback: standaard Windows-openactie.

start "" "%FILE%"

exit /b 0



:NOFILE

call "%SCRIPT_DIR%view_graph.bat"

exit /b %ERRORLEVEL%



:NOTFOUND

echo FOUT: bestand niet gevonden:

echo "%FILE%"

pause

exit /b 1

