@echo off
setlocal EnableExtensions
chcp 65001 >nul

REM ============================================================
REM Graph/OPN renderer batch - current-dir model
REM ------------------------------------------------------------
REM Usage:
REM   maak_graph_png_svg.bat
REM       render all .graph/.opn files in the current directory
REM
REM   maak_graph_png_svg.bat bestand.graph
REM   maak_graph_png_svg.bat bestand.opn
REM       render the specified file
REM
REM   maak_graph_png_svg.bat "%L"
REM       Total Commander: render selected files from list file
REM
REM   maak_graph_png_svg.bat install path
REM   maak_graph_png_svg.bat install menu
REM   maak_graph_png_svg.bat install all
REM ============================================================

REM ========================= CONFIG ===========================
set "MODE=both"
set "PNG_SCALE=1"
set "MARGIN=28"
set "DOT_RADIUS=6"
set "SHOW_GRID=yes"
set "SHOW_LABELS=auto"
set "SHOW_EDGES=yes"
set "STYLE=clean"
set "CROP=content"
set "OUTPUT_DIR="
set "PAUSE_AT_END=yes"
REM ============================================================

set "SCRIPT_DIR=%~dp0"
set "PY_SCRIPT=%SCRIPT_DIR%graph_to_images.py"

if /I "%~1"=="install" goto INSTALL
if /I "%~1"=="uninstall" goto UNINSTALL
if /I "%~1"=="/?" goto HELP
if /I "%~1"=="-h" goto HELP
if /I "%~1"=="--help" goto HELP

if not exist "%PY_SCRIPT%" (
    echo FOUT: graph_to_images.py niet gevonden naast deze batch.
    echo Pad: "%PY_SCRIPT%"
    goto END_ERROR
)

call :FIND_PYTHON
if errorlevel 1 goto END_ERROR

if "%~1"=="" (
    call :RUN --current-dir
    goto END_WITH_CODE
)

REM If one existing non-.graph/.opn file is given, treat it as a Total Commander %L list file.
if exist "%~1" if /I not "%~x1"==".graph" if /I not "%~x1"==".opn" if not exist "%~1\" (
    call :RUN --list "%~1"
    goto END_WITH_CODE
)

call :RUN %*
goto END_WITH_CODE

:RUN
if defined OUTPUT_DIR (
    %PYTHON_CMD% "%PY_SCRIPT%" --mode "%MODE%" --scale "%PNG_SCALE%" --margin "%MARGIN%" --dot-radius "%DOT_RADIUS%" --grid "%SHOW_GRID%" --labels "%SHOW_LABELS%" --edges "%SHOW_EDGES%" --style "%STYLE%" --crop "%CROP%" --outdir "%OUTPUT_DIR%" %*
) else (
    %PYTHON_CMD% "%PY_SCRIPT%" --mode "%MODE%" --scale "%PNG_SCALE%" --margin "%MARGIN%" --dot-radius "%DOT_RADIUS%" --grid "%SHOW_GRID%" --labels "%SHOW_LABELS%" --edges "%SHOW_EDGES%" --style "%STYLE%" --crop "%CROP%" %*
)
set "LAST_CODE=%ERRORLEVEL%"
exit /b %LAST_CODE%

:FIND_PYTHON
set "PYTHON_CMD="
where py >nul 2>nul
if %ERRORLEVEL%==0 (
    set "PYTHON_CMD=py -3"
    exit /b 0
)
where python >nul 2>nul
if %ERRORLEVEL%==0 (
    set "PYTHON_CMD=python"
    exit /b 0
)
echo FOUT: Python niet gevonden. Installeer Python of voeg python/py toe aan PATH.
exit /b 1

:INSTALL
if /I "%~2"=="path" goto INSTALL_PATH
if /I "%~2"=="menu" goto INSTALL_MENU
if /I "%~2"=="all" goto INSTALL_ALL
echo Gebruik: %~nx0 install path ^| menu ^| all
goto END_ERROR

:INSTALL_ALL
call :INSTALL_PATH_DO
if errorlevel 1 goto END_ERROR
call :INSTALL_MENU_DO
if errorlevel 1 goto END_ERROR
goto END_OK

:INSTALL_PATH
call :INSTALL_PATH_DO
goto END_WITH_CODE

:INSTALL_MENU
call :INSTALL_MENU_DO
goto END_WITH_CODE

:INSTALL_PATH_DO
echo Voeg batchmap toe aan User PATH...
powershell -NoProfile -ExecutionPolicy Bypass -Command "$dir=[IO.Path]::GetFullPath('%SCRIPT_DIR%').TrimEnd('\'); $key='HKCU:\Environment'; $path=(Get-ItemProperty -Path $key -Name Path -ErrorAction SilentlyContinue).Path; if([string]::IsNullOrWhiteSpace($path)){$items=@()}else{$items=$path -split ';' | Where-Object { $_ -ne '' }}; if($items -notcontains $dir){$items += $dir; New-ItemProperty -Path $key -Name Path -Value ($items -join ';') -PropertyType ExpandString -Force | Out-Null; Write-Host 'Toegevoegd:' $dir}else{Write-Host 'Stond al in PATH:' $dir}; $sig='[DllImport("user32.dll",SetLastError=true,CharSet=CharSet.Auto)] public static extern IntPtr SendMessageTimeout(IntPtr hWnd, uint Msg, UIntPtr wParam, string lParam, uint fuFlags, uint uTimeout, out UIntPtr lpdwResult);'; Add-Type -MemberDefinition $sig -Name NativeMethods -Namespace Win32; $result=[UIntPtr]::Zero; [Win32.NativeMethods]::SendMessageTimeout([IntPtr]0xffff,0x1A,[UIntPtr]::Zero,'Environment',2,5000,[ref]$result) | Out-Null"
exit /b %ERRORLEVEL%

:INSTALL_MENU_DO
echo Voeg rechtsklikmenu toe voor .graph/.opn renderen...
for %%E in (.graph .opn) do (
    reg add "HKCU\Software\Classes\%%E\shell\render_graph_images" /ve /t REG_SZ /d "Maak PNG/SVG" /f >nul
    reg add "HKCU\Software\Classes\%%E\shell\render_graph_images\command" /ve /t REG_SZ /d "\"%~f0\" \"%%1\"" /f >nul
)
reg add "HKCU\Software\Classes\OpenGraph.GraphFile\shell\render_graph_images" /ve /t REG_SZ /d "Maak PNG/SVG" /f >nul
reg add "HKCU\Software\Classes\OpenGraph.GraphFile\shell\render_graph_images\command" /ve /t REG_SZ /d "\"%~f0\" \"%%1\"" /f >nul
exit /b %ERRORLEVEL%

:UNINSTALL
if /I "%~2"=="path" goto UNINSTALL_PATH
if /I "%~2"=="menu" goto UNINSTALL_MENU
if /I "%~2"=="all" goto UNINSTALL_ALL
echo Gebruik: %~nx0 uninstall path ^| menu ^| all
goto END_ERROR

:UNINSTALL_ALL
call :UNINSTALL_MENU_DO
call :UNINSTALL_PATH_DO
goto END_WITH_CODE

:UNINSTALL_PATH
call :UNINSTALL_PATH_DO
goto END_WITH_CODE

:UNINSTALL_MENU
call :UNINSTALL_MENU_DO
goto END_WITH_CODE

:UNINSTALL_PATH_DO
echo Verwijder batchmap uit User PATH...
powershell -NoProfile -ExecutionPolicy Bypass -Command "$dir=[IO.Path]::GetFullPath('%SCRIPT_DIR%').TrimEnd('\'); $key='HKCU:\Environment'; $path=(Get-ItemProperty -Path $key -Name Path -ErrorAction SilentlyContinue).Path; if(-not [string]::IsNullOrWhiteSpace($path)){ $items=$path -split ';' | Where-Object { $_ -ne '' -and $_.TrimEnd('\') -ne $dir }; New-ItemProperty -Path $key -Name Path -Value ($items -join ';') -PropertyType ExpandString -Force | Out-Null; Write-Host 'Verwijderd indien aanwezig:' $dir }; $sig='[DllImport("user32.dll",SetLastError=true,CharSet=CharSet.Auto)] public static extern IntPtr SendMessageTimeout(IntPtr hWnd, uint Msg, UIntPtr wParam, string lParam, uint fuFlags, uint uTimeout, out UIntPtr lpdwResult);'; Add-Type -MemberDefinition $sig -Name NativeMethods -Namespace Win32; $result=[UIntPtr]::Zero; [Win32.NativeMethods]::SendMessageTimeout([IntPtr]0xffff,0x1A,[UIntPtr]::Zero,'Environment',2,5000,[ref]$result) | Out-Null"
exit /b %ERRORLEVEL%

:UNINSTALL_MENU_DO
for %%E in (.graph .opn) do (
    reg delete "HKCU\Software\Classes\%%E\shell\render_graph_images" /f >nul 2>nul
)
reg delete "HKCU\Software\Classes\OpenGraph.GraphFile\shell\render_graph_images" /f >nul 2>nul
exit /b 0

:HELP
echo Gebruik:
echo   %~nx0
echo   %~nx0 bestand.graph^|bestand.opn
echo   %~nx0 "%%L"      ^(Total Commander selectie-lijst^)
echo   %~nx0 install path ^| menu ^| all
echo   %~nx0 uninstall path ^| menu ^| all
echo.
echo Config staat bovenin deze batch.
goto END_OK

:END_WITH_CODE
set "EXIT_CODE=%LAST_CODE%"
if not defined EXIT_CODE set "EXIT_CODE=%ERRORLEVEL%"
if /I "%PAUSE_AT_END%"=="yes" pause
exit /b %EXIT_CODE%

:END_OK
if /I "%PAUSE_AT_END%"=="yes" pause
exit /b 0

:END_ERROR
if /I "%PAUSE_AT_END%"=="yes" pause
exit /b 1
