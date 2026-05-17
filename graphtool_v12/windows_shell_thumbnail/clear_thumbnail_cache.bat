@echo off
setlocal
REM Clears Windows thumbnail cache. This restarts Explorer.

echo Dit sluit Verkenner tijdelijk af en wist de thumbnail-cache.
choice /M "Doorgaan"
if errorlevel 2 exit /b 0

taskkill /f /im explorer.exe >nul 2>nul
timeout /t 2 /nobreak >nul

del /f /q "%LocalAppData%\Microsoft\Windows\Explorer\thumbcache_*.db" >nul 2>nul

echo Explorer opnieuw starten...
start explorer.exe
exit /b 0
