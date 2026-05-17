@echo off
setlocal EnableExtensions
call "%~dp0windows_shell_thumbnail\build_thumbnail_provider_x64_debug.bat"
if errorlevel 1 exit /b 1
echo.
echo Registering thumbnail provider DEBUG build for current user...
"%SystemRoot%\System32\regsvr32.exe" "%~dp0windows_shell_thumbnail\bin\x64\GraphThumbnailProvider.dll"
if errorlevel 1 exit /b 1
call "%~dp0windows_shell_thumbnail\check_thumbnail_provider_registration.bat"
echo.
echo DEBUG INSTALL OK.
exit /b 0
