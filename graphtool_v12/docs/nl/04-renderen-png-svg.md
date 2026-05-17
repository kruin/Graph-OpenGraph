# PNG/SVG renderen

## Eén bestand renderen

```bat
maak_graph_png_svg.bat spiral.graph
```

## Alle `.graph`- en `.opn`-bestanden in de huidige map

```bat
maak_graph_png_svg.bat
```

## Configuratie in de batch

Open `maak_graph_png_svg.bat`. Bovenin staan instellingen zoals:

```bat
set "MODE=both"
set "PNG_SCALE=1"
set "MARGIN=28"
set "OUTPUT_DIR="
set "PAUSE_AT_END=yes"
```

Betekenis:

| Instelling | Betekenis |
|---|---|
| `MODE=both` | maak PNG en SVG |
| `PNG_SCALE=1` | schaalfactor voor PNG |
| `MARGIN=28` | marge rond content |
| `OUTPUT_DIR=` | leeg betekent: naast het bronbestand |
| `PAUSE_AT_END=yes` | venster blijft open na afloop |

## Direct via Python

```bat
py graph_to_images.py spiral.graph --mode both
```

Clean render is standaard. Klassieke render:

```bat
py graph_to_images.py spiral.graph --mode both --style classic --crop grid --labels yes --grid yes
```

## Preview-sidecars maken

```bat
maak_graph_previews.bat
```

Dit maakt bestanden zoals:

```text
spiral.preview.png
spiral.preview.svg
```
