# PATCH MANIFEST v4.23.9 — Windows launcher classpath quoting fix

## Doel

Deze patch herstelt de Windows-launchers na v4.23.8. De vorige classpath gebruikte:

```bat
-cp "%~dp0out;%~dp0" OpenGraphEdFrame
```

Op Windows kan de trailing backslash van `%~dp0` vlak vóór de sluitquote door de Java launcher worden opgevat als quote-escape. Daardoor krijgt Java wel `-cp`, maar geen losse main class meer. Resultaat: alleen de algemene `java Usage:`-tekst.

## Wijziging

Alle directe launchers gebruiken nu, na `cd /d "%~dp0"`, een relatieve classpath:

```bat
-cp "out;." OpenGraphEdFrame
```

Aangepaste bestanden:

- `run.bat`
- `OpenGraphEd.bat`
- `OpenGraphed.bat`
- `Opengraphed.bat`
- `OpenGraphEd-console.bat`
- `START-OpenGraphEd.bat`

## Behouden uit v4.23.8

- `out\images` blijft aanwezig in de distributie.
- `GraphEditor` blijft tolerant voor een ontbrekende `/images/RotateCursor.gif`.
- Jar-fallbacks blijven bestaan: eerst `dist\OpenGraphEd.jar`, dan `OpenGraphEd.jar`.

## Verwachte test

Vanaf de uitgepakte map:

```bat
run
```

moet nu niet meer de algemene Java usage-tekst tonen, maar de GUI starten.
