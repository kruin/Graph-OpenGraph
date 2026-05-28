@echo off
setlocal EnableDelayedExpansion
cd /d "%~dp0"
set "ROOT=%CD%\"
set "MDROOT=%CD%\md\"
set /a COUNT=0
set /a FAIL=0

echo Markdown folder check
echo Root: %CD%

for /r %%F in (*.md) do (
  set /a COUNT+=1
  set "FULL=%%~fF"
  set "REL=!FULL:%ROOT%=!"
  if /I "!FULL:%MDROOT%=!"=="!FULL!" (
    echo FAIL: !REL!
    set /a FAIL+=1
  )
)

echo Markdown files: !COUNT!
if !FAIL! EQU 0 (
  echo PASS: all .md files are under md/
  exit /b 0
)

echo FAIL: !FAIL! .md file(s) outside md/
exit /b 1
