@echo off
setlocal EnableExtensions
chcp 65001 >nul

for %%E in (.graph .opn) do (
    reg delete "HKCU\Software\Classes\%%E\shell\view_graph" /f >nul 2>nul
    reg delete "HKCU\Software\Classes\%%E" /f >nul 2>nul
    reg delete "HKCU\Software\Microsoft\Windows\CurrentVersion\Explorer\FileExts\%%E\UserChoice" /f >nul 2>nul
)
reg delete "HKCU\Software\Classes\OpenGraph.GraphFile" /f >nul 2>nul

powershell -NoProfile -ExecutionPolicy Bypass -Command "Add-Type -Namespace Win32 -Name Native -MemberDefinition '[System.Runtime.InteropServices.DllImport(\"shell32.dll\")] public static extern void SHChangeNotify(int wEventId, uint uFlags, System.IntPtr dwItem1, System.IntPtr dwItem2);'; [Win32.Native]::SHChangeNotify(0x08000000, 0, [IntPtr]::Zero, [IntPtr]::Zero)" >nul 2>nul

echo OK: .graph/.opn-koppeling voor Graph Viewer verwijderd uit HKCU.
pause
exit /b 0
