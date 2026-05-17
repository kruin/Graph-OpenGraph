# Graph tools v12 — English

Graph tools is a Windows package for `.graph` and `.opn` files. v12 also supports **structured OPN**: files named like `.structure.opn` and using either `structure.nodes/structure.edges` or `STRUCTURE_NODES/STRUCTURE_EDGES`.

## Included components

| Component | Purpose |
|---|---|
| Python renderer | Convert `.graph`, `.opn`, and `.structure.opn` to PNG/SVG |
| Python/Tkinter viewer | Open files directly in a standalone viewer |
| Total Commander F3 wrapper | Press F3 and see a rendered image |
| Windows Preview Handler | Show previews in Explorer Preview Pane / `Alt+P` |
| Windows Thumbnail Provider | Show thumbnails in Explorer icon view |

## Supported formats

1. Classic JGraphEd/OpenGraph format:
   - `.graph`
   - `.opn` using the same line-based graph format

2. Structured OPN:
   - YAML-like:
     - `structure:`
     - `nodes:` with `id`, `label`, `kind`, `x`, `y`
     - `edges:` with `from`, `to`
   - section format:
     - `STRUCTURE_NODES:` with `id | label | x | y | kind`
     - `STRUCTURE_EDGES:` with `from | to`

## Important

The Python renderer has been tested with:

- `examples\spiral.graph`
- `examples\spiral.opn`
- `examples\space3_eerste_vrije_plek.graph`
- `examples\space3_eerste_vrije_plek.opn`
- `examples\onbezield-bovenboom.structure.opn`
- `examples\voorbeeldzin-vrouw-heeft-trui-gebreid.structure.opn`

The Windows Shell DLLs for Preview Pane and thumbnails must be rebuilt and reinstalled on your own Windows machine.

```bat
install_windows_preview_handler.bat
install_windows_thumbnail_provider.bat
windows_shell_thumbnail\clear_thumbnail_cache.bat
```

Then close and reopen Explorer.

## Quick start

### Open a graph

```bat
view_graph.bat examples\onbezield-bovenboom.structure.opn
```

### Create PNG/SVG

```bat
maak_graph_png_svg.bat examples\voorbeeldzin-vrouw-heeft-trui-gebreid.structure.opn
```

### Total Commander F3

```text
Command/Program: C:\GRAPH\graphtool_v12\tc_f3_graph_lister_png.bat
Parameters:      "%P%N"
Start path:      %P
```

Adjust the path to your own install directory.
