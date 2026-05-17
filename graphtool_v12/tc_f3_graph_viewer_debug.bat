@echo off

setlocal EnableExtensions

chcp 65001 >nul



echo TC F3 graph wrapper debug

echo.

echo Argument 1:        [%~1]

echo Extensie:          [%~x1]

echo SCRIPT_DIR:        [%~dp0]

echo COMMANDER_EXE:     [%COMMANDER_EXE%]

echo COMMANDER_PATH:    [%COMMANDER_PATH%]

echo.

call "%~dp0tc_f3_graph_viewer.bat" %*

echo.

echo Exit code: %ERRORLEVEL%

pause

exit /b %ERRORLEVEL%

