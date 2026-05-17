@echo off

setlocal EnableExtensions

rem Build the OpenGraph .graph/.opn Windows Explorer Preview Handler.

rem This uses the .NET Framework C# compiler that is normally present on Windows 10/11.



set "ROOT=%~dp0"

set "SRC=%ROOT%src\GraphShellExtension.cs"

set "OUTDIR=%ROOT%bin"

set "OUTDLL=%OUTDIR%\GraphShellExtension.dll"



if not exist "%OUTDIR%" mkdir "%OUTDIR%"



set "CSC=%WINDIR%\Microsoft.NET\Framework64\v4.0.30319\csc.exe"

if not exist "%CSC%" set "CSC=%WINDIR%\Microsoft.NET\Framework\v4.0.30319\csc.exe"



if not exist "%CSC%" (

  echo ERROR: csc.exe niet gevonden.

  echo Installeer .NET Framework 4.x Developer Pack of Visual Studio Build Tools.

  pause

  exit /b 1

)



"%CSC%" /nologo /target:library /platform:anycpu /optimize+ ^

  /out:"%OUTDLL%" ^

  /reference:System.dll ^

  /reference:System.Core.dll ^

  /reference:System.Drawing.dll ^

  /reference:System.Windows.Forms.dll ^

  "%SRC%"



if errorlevel 1 (

  echo.

  echo BUILD FAILED.

  pause

  exit /b 1

)



echo.

echo BUILD OK:

echo "%OUTDLL%"

exit /b 0

