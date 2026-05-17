# Graph tools v12 — Nederlands

Graph tools is een Windows-pakket voor `.graph`- en `.opn`-bestanden. v12 ondersteunt nu ook **structured OPN**: bestanden met namen zoals `.structure.opn` en inhoud in `structure.nodes/structure.edges` of `STRUCTURE_NODES/STRUCTURE_EDGES`.

## Wat zit erin

| Onderdeel | Doel |
|---|---|
| Python-renderer | `.graph`, `.opn` en `.structure.opn` omzetten naar PNG en SVG |
| Python/Tkinter-viewer | bestanden direct bekijken in een eigen venster |
| Total Commander F3-wrapper | F3 toont direct een gerenderde afbeelding |
| Windows Preview Handler | `Alt+P` in Verkenner toont preview |
| Windows Thumbnail Provider | Verkenner toont miniaturen bij grote pictogrammen |

## Supported formats

1. Klassiek JGraphEd/OpenGraph formaat:
   - `.graph`
   - `.opn` met hetzelfde regelgebaseerde graph-formaat

2. Structured OPN:
   - YAML-achtig:
     - `structure:`
     - `nodes:` met `id`, `label`, `kind`, `x`, `y`
     - `edges:` met `from`, `to`
   - sectieformaat:
     - `STRUCTURE_NODES:` met `id | label | x | y | kind`
     - `STRUCTURE_EDGES:` met `from | to`

## Belangrijk

De Python-renderer is getest met:

- `examples\spiral.graph`
- `examples\spiral.opn`
- `examples\space3_eerste_vrije_plek.graph`
- `examples\space3_eerste_vrije_plek.opn`
- `examples\onbezield-bovenboom.structure.opn`
- `examples\voorbeeldzin-vrouw-heeft-trui-gebreid.structure.opn`

De Windows Shell DLL's voor Preview Pane en thumbnails moeten op jouw Windows-machine opnieuw worden gebouwd en geïnstalleerd.

```bat
install_windows_preview_handler.bat
install_windows_thumbnail_provider.bat
windows_shell_thumbnail\clear_thumbnail_cache.bat
```

Daarna Verkenner sluiten en opnieuw openen.

## Snel starten

### Graph openen

```bat
view_graph.bat examples\onbezield-bovenboom.structure.opn
```

### PNG/SVG maken

```bat
maak_graph_png_svg.bat examples\voorbeeldzin-vrouw-heeft-trui-gebreid.structure.opn
```

### Total Commander F3

```text
Command/Program: C:\GRAPH\graphtool_v12\tc_f3_graph_lister_png.bat
Parameters:      "%P%N"
Start path:      %P
```

Pas het pad aan naar jouw installatiemap.
