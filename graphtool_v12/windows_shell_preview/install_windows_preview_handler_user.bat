@echo off

setlocal EnableExtensions

set "ROOT=%~dp0"

set "DLL=%ROOT%bin\GraphShellExtension.dll"



if not exist "%DLL%" (

  call "%ROOT%build_windows_shell_preview_handler.bat"

  if errorlevel 1 exit /b 1

)



powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%scripts\install_user_preview_handler.ps1" -DllPath "%DLL%"

if errorlevel 1 (

  echo.

  echo INSTALL FAILED.

  pause

  exit /b 1

)



echo.

echo INSTALL OK. Sluit alle Explorer-vensters en open Explorer opnieuw.

echo Zet Preview Pane aan met Alt+P en selecteer een .graph/.opn-bestand.

pause

exit /b 0

