@echo off
setlocal
cd /d "%~dp0"

set "OUTDIR=out"
set "DISTDIR=dist"
set "ROOT_JAR=OpenGraphEd.jar"
set "FALLBACK_JAR=OpenGraphEd.new.jar"
set "DIST_JAR=%DISTDIR%\OpenGraphEd.jar"
set "TEMP_JAR=%DISTDIR%\OpenGraphEd.build.jar"
set "SOURCES_FILE=%TEMP%\OpenGraphEd_sources_%RANDOM%%RANDOM%.txt"
set "MANIFEST_FILE=%TEMP%\OpenGraphEd_manifest_%RANDOM%%RANDOM%.txt"

if not exist "%OUTDIR%" mkdir "%OUTDIR%"
if errorlevel 1 (
    echo.
    echo BUILD FAILED
    echo Could not create %OUTDIR%\
    exit /b 1
)

if not exist "%DISTDIR%" mkdir "%DISTDIR%"
if errorlevel 1 (
    echo.
    echo BUILD FAILED
    echo Could not create %DISTDIR%\
    exit /b 1
)

if exist "%ROOT_JAR%\NUL" (
    echo.
    echo BUILD FAILED
    echo %ROOT_JAR% exists as a DIRECTORY, not as a file.
    echo Rename or remove that directory first.
    exit /b 1
)

if exist "%FALLBACK_JAR%\NUL" (
    echo.
    echo BUILD FAILED
    echo %FALLBACK_JAR% exists as a DIRECTORY, not as a file.
    echo Rename or remove that directory first.
    exit /b 1
)

if exist "%DIST_JAR%\NUL" (
    echo.
    echo BUILD FAILED
    echo %DIST_JAR% exists as a DIRECTORY, not as a file.
    echo Rename or remove that directory first.
    exit /b 1
)

if exist "%TEMP_JAR%" del /q "%TEMP_JAR%" >nul 2>nul
if exist "%DIST_JAR%" del /q "%DIST_JAR%" >nul 2>nul
if exist "%SOURCES_FILE%" del /q "%SOURCES_FILE%" >nul 2>nul
if exist "%MANIFEST_FILE%" del /q "%MANIFEST_FILE%" >nul 2>nul

(
  for %%F in (*.java) do @echo %%F
  if exist dataStructure for /r dataStructure %%F in (*.java) do @echo %%F
  if exist graphException for /r graphException %%F in (*.java) do @echo %%F
  if exist graphStructure for /r graphStructure %%F in (*.java) do @echo %%F
  if exist operation for /r operation %%F in (*.java) do @echo %%F
  if exist userInterface for /r userInterface %%F in (*.java) do @echo %%F
) > "%SOURCES_FILE%"

if not exist "%SOURCES_FILE%" (
    echo.
    echo BUILD FAILED
    echo Could not create source list.
    exit /b 1
)

javac -Xmaxerrs 500 -encoding UTF-8 -d "%OUTDIR%" @"%SOURCES_FILE%"
if errorlevel 1 goto :build_failed

if exist images xcopy images "%OUTDIR%\images" /E /I /Y >nul
if exist help xcopy help "%OUTDIR%\help" /E /I /Y >nul
if exist config xcopy config "%OUTDIR%\config" /E /I /Y >nul

> "%MANIFEST_FILE%" echo Main-Class: OpenGraphEdFrame
>> "%MANIFEST_FILE%" echo.

jar cfm "%TEMP_JAR%" "%MANIFEST_FILE%" -C "%OUTDIR%" .
if errorlevel 1 goto :jar_failed

call :safe_replace "%TEMP_JAR%" "%DIST_JAR%"
if errorlevel 1 goto :dist_failed

echo.
echo BUILD OK
echo Classes and resources are in %OUTDIR%\
echo Fresh jar created: %DIST_JAR%

echo.
echo Trying to copy jar to project root...
call :try_copy_with_retry "%DIST_JAR%" "%ROOT_JAR%"
if errorlevel 1 (
    echo Could not refresh %ROOT_JAR% in the project root.
    call :try_copy_with_retry "%DIST_JAR%" "%FALLBACK_JAR%"
    if errorlevel 1 (
        echo Could not create %FALLBACK_JAR% in the project root either.
        echo.
        echo BUILD OK
        echo Use the app normally via OpenGraphEd.bat or run.bat.
        echo For packaging/distribution, use this jar:
        echo   %DIST_JAR%
        goto :cleanup_success
    )
    echo Project-root fallback jar created: %FALLBACK_JAR%
    echo.
    echo BUILD OK
    echo Use the app normally via OpenGraphEd.bat or run.bat.
    echo For packaging/distribution, use this jar:
    echo   %DIST_JAR%
    goto :cleanup_success
)

echo Project-root jar refreshed: %ROOT_JAR%

goto :cleanup_success

:safe_replace
set "SRC=%~1"
set "DST=%~2"
if exist "%DST%" del /q "%DST%" >nul 2>nul
copy /Y "%SRC%" "%DST%" >nul 2>nul
if errorlevel 1 exit /b 1
exit /b 0

:try_copy_with_retry
set "SRC=%~1"
set "DST=%~2"
set "TRY_COUNT=0"
:try_copy_loop
set /a TRY_COUNT+=1 >nul
copy /Y "%SRC%" "%DST%" >nul 2>nul
if not errorlevel 1 exit /b 0
if %TRY_COUNT% GEQ 5 exit /b 1
timeout /t 1 /nobreak >nul
 goto :try_copy_loop

:build_failed
echo.
echo BUILD FAILED
echo Java compilation failed.
goto :cleanup_error

:jar_failed
echo.
echo JAR BUILD FAILED
echo Could not create the jar in %DISTDIR%\
goto :cleanup_error

:dist_failed
echo.
echo JAR BUILD FAILED
echo Could not place the fresh jar at %DIST_JAR%
goto :cleanup_error

:cleanup_success
if exist "%TEMP_JAR%" del /q "%TEMP_JAR%" >nul 2>nul
if exist "%SOURCES_FILE%" del /q "%SOURCES_FILE%" >nul 2>nul
if exist "%MANIFEST_FILE%" del /q "%MANIFEST_FILE%" >nul 2>nul

echo.
echo Note: OpenGraphEd.bat prefers freshly compiled classes in %OUTDIR%\
echo and does not depend on %ROOT_JAR% for normal local use.

endlocal
exit /b 0

:cleanup_error
if exist "%TEMP_JAR%" del /q "%TEMP_JAR%" >nul 2>nul
if exist "%SOURCES_FILE%" del /q "%SOURCES_FILE%" >nul 2>nul
if exist "%MANIFEST_FILE%" del /q "%MANIFEST_FILE%" >nul 2>nul
endlocal
exit /b 1
