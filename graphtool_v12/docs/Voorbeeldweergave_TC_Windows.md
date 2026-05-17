# Voorbeeldweergave voor `.graph` in Total Commander en Windows

## Kern

Er zijn drie verschillende dingen:

1. **F3 / Lister-venster**: kan met deze ZIP direct werken.
2. **Ctrl+Q / Quick View-paneel in Total Commander**: dit gebruikt TC Lister. Voor echte embedded `.graph`-preview is normaal een native WLX Lister-plugin nodig.
3. **Windows Explorer Preview Pane / thumbnails op het `.graph`/`.opn`-bestand zelf**: daarvoor is een Windows Shell Preview Handler of Thumbnail Provider nodig. Dat kan niet betrouwbaar met alleen batch + Python.

Deze ZIP activeert daarom de praktische route:

- F3 op `.graph` geeft direct een gerenderde afbeelding.
- Je kunt sidecar previews maken: `bestand.preview.png` en `bestand.preview.svg`.
- Windows-dubbelklik kan aan de viewer worden gekoppeld.

## Total Commander: F3 als gerenderde preview in TC Lister

Gebruik deze wrapper:

```text
Command/Program: C:\pad\naar\tc_f3_graph_lister_png.bat
Parameters:      "%P%N"
Start path:      %P
```

Werking:

1. TC geeft het geselecteerde `.graph`/`.opn`-bestand door.
2. De wrapper maakt tijdelijk een PNG in `%TEMP%\graph_tools_preview`.
3. De wrapper opent die PNG in Total Commander's eigen Lister via `/S=L:T4`.

Debugvariant:

```text
Command/Program: C:\pad\naar\tc_f3_graph_lister_png_debug.bat
Parameters:      "%P%N"
Start path:      %P
```

## Total Commander: Quick View-paneel

Quick View zet je in TC zelf aan met:

```text
Ctrl+Q
```

Maar: voor `.graph` embedded in dat paneel is een WLX Lister-plugin nodig. Deze ZIP bevat geen native WLX-DLL. De Python-viewer kan wel een apart venster openen, maar niet in het TC-paneel worden ingebed.

## Sidecar previews maken

Voor alle `.graph`- en `.opn`-bestanden in de actuele map:

```bat
maak_graph_previews.bat
```

Voor geselecteerde bestanden in Total Commander:

```text
Command:    C:\pad\naar\maak_graph_previews.bat
Parameters: "%L"
Start path: %P
```

Output naast de bronbestanden:

```text
spiral.graph
spiral.preview.png
spiral.preview.svg
```

Die PNG/SVG-bestanden tonen wel gewone thumbnails/preview in Total Commander en Windows.

## Windows

Dubbelklik/Open koppelen aan de viewer:

```bat
install_windows_graph_open_assoc.bat
```

Dit activeert niet de Windows Preview Pane. Voor echte Windows-preview op `.graph` zelf is later een aparte Shell Extension nodig.
