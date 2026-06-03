@echo off
setlocal
cd /d "%~dp0"

set "OUTDIR=out"
set "DISTDIR=dist"
set "TMPDIR=.build-tmp"
set "ROOT_JAR=OpenGraphEd.jar"
set "FALLBACK_JAR=OpenGraphEd.new.jar"
set "DIST_JAR=%DISTDIR%\OpenGraphEd.jar"
set "TEMP_JAR=%TMPDIR%\OpenGraphEd.build.jar"
set "SOURCES_FILE=%TMPDIR%\OpenGraphEd_sources.txt"
set "MANIFEST_FILE=%TMPDIR%\OpenGraphEd_manifest.mf"

rem v4336: build cleanly so removed classes such as GreedyGrowthWindow do not remain in out/.
if exist "%OUTDIR%" rmdir /S /Q "%OUTDIR%" >nul 2>nul

if not exist "%OUTDIR%" mkdir "%OUTDIR%"
if errorlevel 1 ( echo. & echo BUILD FAILED & echo Could not create %OUTDIR%\ & exit /b 1 )
if not exist "%DISTDIR%" mkdir "%DISTDIR%"
if errorlevel 1 ( echo. & echo BUILD FAILED & echo Could not create %DISTDIR%\ & exit /b 1 )
if not exist "%TMPDIR%" mkdir "%TMPDIR%"
if errorlevel 1 ( echo. & echo BUILD FAILED & echo Could not create local build temp directory: %TMPDIR%\ & exit /b 1 )

if exist "%ROOT_JAR%\NUL" ( echo. & echo BUILD FAILED & echo %ROOT_JAR% exists as a DIRECTORY, not as a file. & exit /b 1 )
if exist "%FALLBACK_JAR%\NUL" ( echo. & echo BUILD FAILED & echo %FALLBACK_JAR% exists as a DIRECTORY, not as a file. & exit /b 1 )
if exist "%DIST_JAR%\NUL" ( echo. & echo BUILD FAILED & echo %DIST_JAR% exists as a DIRECTORY, not as a file. & exit /b 1 )

if exist "%TEMP_JAR%" del /q "%TEMP_JAR%" >nul 2>nul
if exist "%DIST_JAR%" del /q "%DIST_JAR%" >nul 2>nul
if exist "%SOURCES_FILE%" del /q "%SOURCES_FILE%" >nul 2>nul
if exist "%MANIFEST_FILE%" del /q "%MANIFEST_FILE%" >nul 2>nul

rem Build a javac @argfile with relative paths and forward slashes.
rem Do NOT write quoted absolute Windows paths here: javac treats backslashes
rem in @argfiles as escapes, which turns C:\dir\File.java into C:dirFile.java.
type nul > "%SOURCES_FILE%"
if errorlevel 1 ( echo. & echo BUILD FAILED & echo Could not create source list in %SOURCES_FILE%. & exit /b 1 )

for %%F in (*.java) do call :append_source "%%F"
if exist dataStructure for /r dataStructure %%F in (*.java) do call :append_source "%%F"
if exist graphException for /r graphException %%F in (*.java) do call :append_source "%%F"
if exist graphStructure for /r graphStructure %%F in (*.java) do call :append_source "%%F"
if exist operation for /r operation %%F in (*.java) do call :append_source "%%F"
if exist tools for /r tools %%F in (*.java) do call :append_source "%%F"
if exist userInterface for /r userInterface %%F in (*.java) do call :append_source "%%F"

if not exist "%SOURCES_FILE%" ( echo. & echo BUILD FAILED & echo Could not create source list in %SOURCES_FILE%. & exit /b 1 )
for %%A in ("%SOURCES_FILE%") do if %%~zA EQU 0 ( echo. & echo BUILD FAILED & echo Source list is empty. & exit /b 1 )

where javac >nul 2>nul
if errorlevel 1 (
    echo.
    echo BUILD FAILED
    echo javac was not found. Install a JDK and make sure javac is on PATH.
    echo A JRE is not enough for build.bat.
    exit /b 1
)

javac -Xmaxerrs 500 -encoding UTF-8 -d "%OUTDIR%" @"%SOURCES_FILE%"
if errorlevel 1 goto :build_failed

if exist images xcopy images "%OUTDIR%\images" /E /I /Y >nul
if exist help xcopy help "%OUTDIR%\help" /E /I /Y >nul
if exist config xcopy config "%OUTDIR%\config" /E /I /Y >nul

> "%MANIFEST_FILE%" echo Manifest-Version: 1.0
>> "%MANIFEST_FILE%" echo Main-Class: OpenGraphEdFrame
>> "%MANIFEST_FILE%" echo Implementation-Title: OpenGraphEd
>> "%MANIFEST_FILE%" echo Implementation-Version: v4.33.9-greedy-grow-json-export
>> "%MANIFEST_FILE%" echo.

where jar >nul 2>nul
if errorlevel 1 ( echo. & echo JAR BUILD FAILED & echo jar was not found. Install a JDK and make sure jar is on PATH. & exit /b 1 )

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
        echo BUILD OK. Use %DIST_JAR%.
        goto :cleanup_success
    )
    echo Project-root fallback jar created: %FALLBACK_JAR%
    goto :cleanup_success
)
echo Project-root jar refreshed: %ROOT_JAR%
goto :cleanup_success

:append_source
rem Convert each source path to a relative path with forward slashes for javac @argfile.
rem Delayed expansion is required here. Plain %%P:%%CD%% substitution can be
rem parsed incorrectly by cmd.exe and may leak tokens such as CD into the argfile.
setlocal EnableDelayedExpansion
set "P=%~1"
set "ROOT=%CD%\"
set "P=!P:%ROOT%=!"
set "P=!P:\=/!"
>> "%SOURCES_FILE%" echo "!P!"
endlocal
exit /b 0

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
if exist "%TMPDIR%" rmdir "%TMPDIR%" >nul 2>nul
echo.
echo Note: OpenGraphEd.bat prefers freshly compiled classes in %OUTDIR%\
echo and falls back to dist\OpenGraphEd.jar or OpenGraphEd.jar.
endlocal
exit /b 0

:cleanup_error
if exist "%TEMP_JAR%" del /q "%TEMP_JAR%" >nul 2>nul
if exist "%SOURCES_FILE%" del /q "%SOURCES_FILE%" >nul 2>nul
if exist "%MANIFEST_FILE%" del /q "%MANIFEST_FILE%" >nul 2>nul
if exist "%TMPDIR%" rmdir "%TMPDIR%" >nul 2>nul
endlocal
exit /b 1
