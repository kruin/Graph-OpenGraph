@echo off

setlocal

REM Uninstall current-user .graph/.opn thumbnail provider.



set "HERE=%~dp0"

set "DLL=%HERE%bin\x64\GraphThumbnailProvider.dll"



if exist "%DLL%" (

  "%SystemRoot%\System32\regsvr32.exe" /u /s "%DLL%"

)



reg delete "HKCU\Software\Classes\CLSID\{B86C773A-62BD-4F47-85D4-132380F52AE3}" /f >nul 2>nul

reg delete "HKCU\Software\Classes\.graph\ShellEx\{E357FCCD-A995-4576-B01F-234630154E96}" /f >nul 2>nul
reg delete "HKCU\Software\Classes\.opn\ShellEx\{E357FCCD-A995-4576-B01F-234630154E96}" /f >nul 2>nul

reg delete "HKCU\Software\Classes\GraphFile\ShellEx\{E357FCCD-A995-4576-B01F-234630154E96}" /f >nul 2>nul

reg delete "HKCU\Software\Microsoft\Windows\CurrentVersion\Shell Extensions\Approved" /v "{B86C773A-62BD-4F47-85D4-132380F52AE3}" /f >nul 2>nul



echo Uninstall uitgevoerd. Herstart Verkenner indien nodig.

exit /b 0

