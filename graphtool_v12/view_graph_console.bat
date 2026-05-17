@echo off

setlocal EnableExtensions

chcp 65001 >nul



call "%~dp0view_graph.bat" %*



echo.

echo Exitcode: %ERRORLEVEL%

pause

exit /b %ERRORLEVEL%

