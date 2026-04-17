@echo off
setlocal
cd /d "%~dp0"

set "LAUNCHER=%CD%\OpenGraphEd.bat"
set "SHORTCUT_NAME=OpenGraphEd (Java, via DOS .batfile).lnk"
set "DESKTOP=%USERPROFILE%\Desktop\%SHORTCUT_NAME%"

if not exist "%LAUNCHER%" (
    echo Bestand niet gevonden: "%LAUNCHER%"
    exit /b 1
)

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$WshShell = New-Object -ComObject WScript.Shell;" ^
  "$Shortcut = $WshShell.CreateShortcut('%DESKTOP%');" ^
  "$Shortcut.TargetPath = '%LAUNCHER%';" ^
  "$Shortcut.WorkingDirectory = '%CD%';" ^
  "$Shortcut.Description = 'OpenGraphEd (Java, via DOS .batfile)';" ^
  "$Shortcut.Save();"

if errorlevel 1 (
    echo Kon de bureaublad-snelkoppeling niet maken.
    exit /b 1
)

echo.
echo Bureaublad-snelkoppeling gemaakt:
echo %DESKTOP%
exit /b 0
