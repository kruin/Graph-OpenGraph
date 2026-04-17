@echo off
setlocal
cd /d "%~dp0"

set "APP_DIR=%CD%"
set "LAUNCHER=%APP_DIR%\OpenGraphEd.bat"
set "FRIENDLY_APP_NAME=OpenGraphEd (Java, via DOS .batfile)"
if not exist "%LAUNCHER%" (
    echo Bestand niet gevonden: "%LAUNCHER%"
    exit /b 1
)

reg add "HKCU\Software\Classes\.graph" /ve /d "OpenGraphEd.graph" /f >nul
reg add "HKCU\Software\Classes\OpenGraphEd.graph" /ve /d "OpenGraphEd Graph File" /f >nul
reg add "HKCU\Software\Classes\OpenGraphEd.graph\shell" /ve /d "open" /f >nul
reg add "HKCU\Software\Classes\OpenGraphEd.graph\shell\open" /ve /d "Open with %FRIENDLY_APP_NAME%" /f >nul
reg add "HKCU\Software\Classes\OpenGraphEd.graph\shell\open\command" /ve /d "\"%LAUNCHER%\" \"%%1\"" /f >nul
reg add "HKCU\Software\Classes\Applications\OpenGraphEd.bat" /v "FriendlyAppName" /d "%FRIENDLY_APP_NAME%" /f >nul
reg add "HKCU\Software\Classes\Applications\OpenGraphEd.bat\shell\open\command" /ve /d "\"%LAUNCHER%\" \"%%1\"" /f >nul
reg add "HKCU\Software\Classes\Applications\OpenGraphEd.bat\SupportedTypes" /v ".graph" /d "" /f >nul

echo.
echo Koppeling gezet voor .graph bestanden.
echo Latere dubbelkliks sturen het bestand nu naar de al geopende app-instantie.
echo De launchernaam in Windows is ingesteld op: %FRIENDLY_APP_NAME%
echo Mogelijk moet Verkenner opnieuw gestart worden voordat de wijziging zichtbaar is.
exit /b 0
