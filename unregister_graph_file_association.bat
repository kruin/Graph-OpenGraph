@echo off
setlocal

reg delete "HKCU\Software\Classes\.graph" /f >nul 2>nul
reg delete "HKCU\Software\Classes\OpenGraphEd.graph" /f >nul 2>nul
reg delete "HKCU\Software\Classes\Applications\OpenGraphEd.bat" /f >nul 2>nul

echo.
echo Koppeling voor .graph bestanden verwijderd uit HKCU.
exit /b 0
