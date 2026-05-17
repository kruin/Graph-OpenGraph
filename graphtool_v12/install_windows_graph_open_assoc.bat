@echo off
setlocal EnableExtensions

REM Dubbelklik/Open-koppeling voor .graph en .opn naar Graph Viewer.
REM Dit activeert GEEN Windows Preview Pane. Daarvoor is een Shell Preview Handler nodig.

set "SCRIPT_DIR=%~dp0"
set "VIEWER=%SCRIPT_DIR%view_graph.bat"

if not exist "%VIEWER%" (
  echo FOUT: view_graph.bat niet gevonden naast deze batch.
  pause
  exit /b 1
)

for %%E in (.graph .opn) do (
  reg add "HKCU\Software\Classes\%%E" /ve /d "GraphTools.GraphFile" /f >nul
)
reg add "HKCU\Software\Classes\GraphTools.GraphFile" /ve /d "Graph/OPN bestand" /f >nul
reg add "HKCU\Software\Classes\GraphTools.GraphFile\shell\open\command" /ve /d "\"%VIEWER%\" \"%%1\"" /f >nul

echo .graph en .opn zijn gekoppeld aan Graph Viewer voor deze gebruiker.
echo Windows Preview Pane is hiermee niet geactiveerd.
pause
