@echo off
setlocal EnableExtensions
call "%~dp0windows_shell_preview\uninstall_windows_preview_handler_user.bat"
exit /b %errorlevel%
