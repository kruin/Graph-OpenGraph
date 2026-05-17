@echo off
setlocal EnableExtensions
chcp 65001 >nul

REM ============================================================
REM Registreert .graph en .opn voor de huidige Windows-gebruiker.
REM Geen administratorrechten nodig.
REM Overschrijft oude/missende koppelingen in HKCU.
REM ============================================================

set "VIEWER=%~dp0view_graph.bat"
if not exist "%VIEWER%" (
    echo FOUT: view_graph.bat niet gevonden naast deze batch.
    pause
    exit /b 1
)

echo Herstel .graph/.opn-koppeling voor Graph Viewer...

for %%E in (.graph .opn) do (
    reg delete "HKCU\Software\Microsoft\Windows\CurrentVersion\Explorer\FileExts\%%E\UserChoice" /f >nul 2>nul
    reg add "HKCU\Software\Classes\%%E" /ve /t REG_SZ /d "OpenGraph.GraphFile" /f >nul
    reg add "HKCU\Software\Classes\%%E\shell\view_graph" /ve /t REG_SZ /d "View with Graph Viewer" /f >nul
    reg add "HKCU\Software\Classes\%%E\shell\view_graph\command" /ve /t REG_SZ /d "\"%VIEWER%\" \"%%1\"" /f >nul
)

reg add "HKCU\Software\Classes\OpenGraph.GraphFile" /ve /t REG_SZ /d "OpenGraph Graph/OPN-bestand" /f >nul
reg add "HKCU\Software\Classes\OpenGraph.GraphFile\DefaultIcon" /ve /t REG_SZ /d "%SystemRoot%\System32\imageres.dll,-102" /f >nul
reg add "HKCU\Software\Classes\OpenGraph.GraphFile\shell\open\command" /ve /t REG_SZ /d "\"%VIEWER%\" \"%%1\"" /f >nul
reg add "HKCU\Software\Classes\OpenGraph.GraphFile\shell\view" /ve /t REG_SZ /d "View with Graph Viewer" /f >nul
reg add "HKCU\Software\Classes\OpenGraph.GraphFile\shell\view\command" /ve /t REG_SZ /d "\"%VIEWER%\" \"%%1\"" /f >nul

powershell -NoProfile -ExecutionPolicy Bypass -Command "Add-Type -Namespace Win32 -Name Native -MemberDefinition '[System.Runtime.InteropServices.DllImport(\"shell32.dll\")] public static extern void SHChangeNotify(int wEventId, uint uFlags, System.IntPtr dwItem1, System.IntPtr dwItem2);'; [Win32.Native]::SHChangeNotify(0x08000000, 0, [IntPtr]::Zero, [IntPtr]::Zero)" >nul 2>nul

echo OK: .graph en .opn zijn geregistreerd voor de huidige gebruiker.
echo Viewer: "%VIEWER%"
echo.
pause
exit /b 0
