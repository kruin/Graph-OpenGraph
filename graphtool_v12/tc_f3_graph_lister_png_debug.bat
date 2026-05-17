@echo off
setlocal EnableExtensions

echo DEBUG tc_f3_graph_lister_png
echo INPUT=%~1
echo SCRIPT_DIR=%~dp0
echo COMMANDER_EXE=%COMMANDER_EXE%
echo CD=%CD%
echo.
call "%~dp0tc_f3_graph_lister_png.bat" %*
echo.
echo Returncode: %ERRORLEVEL%
pause

