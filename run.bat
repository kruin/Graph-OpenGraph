@echo off
setlocal
cd /d "%~dp0"

if exist "%~dp0register_graph_file_association.bat" (
    call "%~dp0register_graph_file_association.bat" >nul 2>nul
)

call "%~dp0OpenGraphEd.bat" %*
exit /b %errorlevel%
