# Clean render v9

Deze versie maakt `.graph`-miniaturen rustiger en leesbaarder.

## Belangrijkste verschil

V8 gebruikte veelal het hele grid als tekenvlak. Bij sommige bestanden, zoals spiralen, gaf dat veel leeg of druk vlak. V9 gebruikt standaard de feitelijke inhoud:

```text
crop = content
style = clean
labels = auto/no
```

## Instellingen in de batch

In `maak_graph_png_svg.bat`:

```bat
set "STYLE=clean"
set "CROP=content"
set "SHOW_LABELS=auto"
```

Voor oude stijl:

```bat
set "STYLE=classic"
set "CROP=grid"
set "SHOW_LABELS=yes"
```

## Total Commander F3

Gebruik:

```text
Command/Program: C:\GRAPH\graphtool_v9\tc_f3_graph_lister_png.bat
Parameters:      "%P%N"
Start path:      %P
```

De wrapper maakt een tijdelijke cleane PNG en opent die in TC Lister.

## Windows Explorer thumbnails

Installeer opnieuw:

```bat
install_windows_thumbnail_provider.bat
```

Cache wissen:

```bat
windows_shell_thumbnail\clear_thumbnail_cache.bat
```

Daarna Explorer opnieuw starten.
