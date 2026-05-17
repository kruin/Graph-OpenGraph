@echo off
setlocal

echo.
echo === .graph/.opn thumbnail registration check ===

reg query "HKCU\Software\Classes\.graph\ShellEx\{E357FCCD-A995-4576-B01F-234630154E96}" /ve
if errorlevel 1 echo MISSING: .graph ShellEx thumbnail handler

echo.
reg query "HKCU\Software\Classes\.opn\ShellEx\{E357FCCD-A995-4576-B01F-234630154E96}" /ve
if errorlevel 1 echo MISSING: .opn ShellEx thumbnail handler

echo.
reg query "HKCU\Software\Classes\GraphFile\ShellEx\{E357FCCD-A995-4576-B01F-234630154E96}" /ve
if errorlevel 1 echo MISSING: GraphFile ShellEx thumbnail handler

echo.
reg query "HKCU\Software\Classes\CLSID\{B86C773A-62BD-4F47-85D4-132380F52AE3}\InProcServer32" /ve
if errorlevel 1 echo MISSING: COM InProcServer32

echo.
reg query "HKCU\Software\Classes\CLSID\{B86C773A-62BD-4F47-85D4-132380F52AE3}\InProcServer32" /v ThreadingModel
if errorlevel 1 echo MISSING: ThreadingModel

echo.
reg query "HKCU\Software\Microsoft\Windows\CurrentVersion\Shell Extensions\Approved" /v "{B86C773A-62BD-4F47-85D4-132380F52AE3}"
if errorlevel 1 echo MISSING: Approved shell extension entry

echo.
echo === end check ===
exit /b 0
