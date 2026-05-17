# Windows Verkenner

## Dubbelklik / Open

Installeer de normale `.graph`-koppeling:

```bat
install_windows_graph_open_assoc.bat
```

Daarna opent dubbelklik op `.graph` de Graph Viewer.

## Preview Pane / Alt+P

Installeer de Windows Preview Handler:

```bat
install_windows_preview_handler.bat
```

Daarna:

1. Sluit Verkenner.
2. Open Verkenner opnieuw.
3. Zet het voorbeeldvenster aan met `Alt+P`.
4. Selecteer een `.graph`/`.opn`-bestand.

Verwijderen:

```bat
uninstall_windows_preview_handler.bat
```

## Thumbnails / miniaturen

Installeer de native thumbnail-provider:

```bat
install_windows_thumbnail_provider.bat
```

Daarna cache wissen:

```bat
windows_shell_thumbnail\clear_thumbnail_cache.bat
```

Zet Verkenner op:

```text
Grote pictogrammen
```

of:

```text
Extra grote pictogrammen
```

## Belangrijk over de DLL

De thumbnail-provider is een Windows Shell Extension. Daarom wordt de DLL lokaal gebouwd en geregistreerd. De Python-renderer is hier getest; de Shell DLL moet op jouw Windows-machine worden gebouwd met de meegeleverde batch.
