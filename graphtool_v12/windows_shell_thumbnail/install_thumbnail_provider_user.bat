@echo off

setlocal EnableExtensions

REM Build and install the native Windows Explorer thumbnail provider for .graph/.opn files.

REM User-level COM registration: no administrator rights intended.

REM v8: DLL no longer starts GDI+ from DllMain; this avoids regsvr32 hangs.



set "HERE=%~dp0"

set "DLL=%HERE%bin\x64\GraphThumbnailProvider.dll"



call "%HERE%build_thumbnail_provider_x64.bat"

if errorlevel 1 exit /b 1



if not exist "%DLL%" (

  echo ERROR: DLL niet gevonden: "%DLL%"

  exit /b 1

)



echo.

echo Registering thumbnail provider for current user...

echo   "%DLL%"

echo.

"%SystemRoot%\System32\regsvr32.exe" /s "%DLL%"

if errorlevel 1 (

  echo ERROR: regsvr32 registratie mislukt.

  echo Controleer of dit de x64 DLL is en of je 64-bit Windows gebruikt.

  exit /b 1

)



call "%HERE%check_thumbnail_provider_registration.bat"



echo.

echo INSTALL OK.

echo Herstart Verkenner of log opnieuw in als thumbnails niet direct verschijnen.

echo Zie ook: clear_thumbnail_cache.bat

exit /b 0

