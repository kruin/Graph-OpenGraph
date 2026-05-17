@echo off
setlocal EnableExtensions
set "ROOT=%~dp0"
powershell -NoProfile -ExecutionPolicy Bypass -File "%ROOT%scripts\uninstall_user_preview_handler.ps1"
echo.
echo UNINSTALL OK. Sluit alle Explorer-vensters en open Explorer opnieuw.
pause
exit /b 0
