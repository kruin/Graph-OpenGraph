@echo off
setlocal
cd /d "%~dp0"

if "%~1"=="" (
    echo Usage: open_graph_file.bat ^<path-to-graph-file^>
    exit /b 1
)

set "GRAPH_FILE=%~f1"
call "%~dp0OpenGraphEd.bat" "%GRAPH_FILE%"
exit /b %errorlevel%
