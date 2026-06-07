@echo off
setlocal
cd /d "%~dp0"
echo.
echo OpenGraph Lite Viewer v4388
echo.
echo Start lokale server op http://localhost:8088
echo Gebruik 8088. Herlaad hard als een oude PWA-cache nog actief is.
echo.
where py >nul 2>nul
if %errorlevel%==0 (
  py -m http.server 8088 --bind 0.0.0.0
  goto :eof
)
where python >nul 2>nul
if %errorlevel%==0 (
  python -m http.server 8088 --bind 0.0.0.0
  goto :eof
)
echo FOUT: Python niet gevonden. Installeer Python of publiceer deze map via GitHub Pages.
pause
