# Total Commander

## F3: direct rendered view

Use this wrapper:

```text
tc_f3_graph_lister_png.bat
```

Configure the F3 viewer in Total Commander as follows:

```text
Command/Program: C:\GRAPH\graphtool_v11\tc_f3_graph_lister_png.bat
Parameters:      "%P%N"
Start path:      %P
```

Result:

```text
F3 on spiral.graph -> rendered PNG in Total Commander Lister
```

## Debug

If F3 shows nothing, temporarily use:

```text
tc_f3_graph_lister_png_debug.bat
```

With the same parameters:

```text
"%P%N"
```

## Render selected files to PNG/SVG

For a Total Commander button or menu item:

```text
Command:    C:\GRAPH\graphtool_v11\maak_graph_png_svg.bat
Parameters: "%L"
Start path: %P
```

`%L` is a temporary list file containing the selected files. The batch detects it automatically.

## Render all `.graph` and `.opn` files in the current folder

```text
Command:    C:\GRAPH\graphtool_v11\maak_graph_png_svg.bat
Parameters:
Start path: %P
```

## Ctrl+Q

Total Commander `Ctrl+Q` uses its internal Lister route. The practical route in this package is F3 through `tc_f3_graph_lister_png.bat`. A true embedded `Ctrl+Q` preview would require a WLX Lister plugin later.
