# Windows Explorer Preview Pane activeren voor `.graph`

## Installatie

```bat
install_windows_preview_handler.bat
```

Daarna:

1. Sluit alle Verkenner-vensters.
2. Open Verkenner opnieuw.
3. Gebruik `Alt+P` om het voorbeeldvenster aan te zetten.
4. Selecteer een `.graph`/`.opn`-bestand.

## Wat deze versie doet

Deze versie installeert een echte Windows Shell Preview Handler voor `.graph`.
Het `.graph`/`.opn`-bestand wordt dus niet eerst geopend in de losse Graph Viewer; Explorer host de preview zelf in het Preview Pane.

## Wat deze versie niet doet

Deze versie installeert nog geen thumbnail provider voor pictogramweergave in de bestandslijst. Voor thumbnails is later beter een native C++ shell extension te maken.

## Foutopsporing

Gebruik:

```bat
windows_shell_preview\check_windows_preview_handler_registration.bat
```

Als Explorer nog oude informatie gebruikt:

```bat
taskkill /im prevhost.exe /f
```

of sluit alle Explorer-vensters en open Explorer opnieuw.

## Total Commander

Voor Total Commander blijft deze praktische route het meest direct:

```text
Command/Program: C:\Graph\graphview\tc_f3_graph_lister_png.bat
Parameters:      "%P%N"
Start path:      %P
```

De Windows Preview Handler is bedoeld voor Windows Verkenner. Total Commander Quick View/Ctrl+Q gebruikt eigen Lister-mechanismen; volledige ingebouwde TC-preview vraagt later om een WLX-plugin.
