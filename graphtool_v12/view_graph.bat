@echo off

setlocal EnableExtensions

chcp 65001 >nul



set "SCRIPT_DIR=%~dp0"

set "VIEWER=%SCRIPT_DIR%graph_viewer.py"



if not exist "%VIEWER%" (

    echo FOUT: graph_viewer.py niet gevonden naast deze batch.

    echo Pad: "%VIEWER%"

    pause

    exit /b 1

)



where py >nul 2>nul

if %ERRORLEVEL%==0 (

    py -3 "%VIEWER%" %*

    exit /b %ERRORLEVEL%

)



where python >nul 2>nul

if %ERRORLEVEL%==0 (

    python "%VIEWER%" %*

    exit /b %ERRORLEVEL%

)



echo FOUT: Python niet gevonden. Installeer Python of voeg python/py toe aan PATH.

pause

exit /b 1

