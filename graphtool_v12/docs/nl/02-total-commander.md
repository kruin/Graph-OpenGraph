# Total Commander

## F3: direct gerenderde weergave

Gebruik deze wrapper:

```text
tc_f3_graph_lister_png.bat
```

Stel in Total Commander de F3-viewer in op:

```text
Command/Program: C:\GRAPH\graphtool_v11\tc_f3_graph_lister_png.bat
Parameters:      "%P%N"
Start path:      %P
```

Daarna geldt:

```text
F3 op spiral.graph -> gerenderde PNG in Total Commander Lister
```

## Debug

Als F3 niets toont, gebruik tijdelijk:

```text
tc_f3_graph_lister_png_debug.bat
```

Met dezelfde parameters:

```text
"%P%N"
```

## Geselecteerde bestanden naar PNG/SVG renderen

Voor een knop of menu-item in Total Commander:

```text
Command:    C:\GRAPH\graphtool_v11\maak_graph_png_svg.bat
Parameters: "%L"
Start path: %P
```

`%L` is een tijdelijk lijstbestand met de geselecteerde bestanden. De batch herkent dit automatisch.

## Alle `.graph`- en `.opn`-bestanden in actuele map renderen

```text
Command:    C:\GRAPH\graphtool_v11\maak_graph_png_svg.bat
Parameters:
Start path: %P
```

## Ctrl+Q

Total Commander `Ctrl+Q` gebruikt de interne Lister-route. De praktische route in dit pakket is F3 via `tc_f3_graph_lister_png.bat`. Voor echte ingebedde `Ctrl+Q`-preview is later een WLX Lister-plugin nodig.
