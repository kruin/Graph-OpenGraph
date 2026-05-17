# Render PNG/SVG

## Render one file

```bat
maak_graph_png_svg.bat spiral.graph
```

## Render all `.graph` and `.opn` files in the current folder

```bat
maak_graph_png_svg.bat
```

## Batch configuration

Open `maak_graph_png_svg.bat`. The main settings are at the top:

```bat
set "MODE=both"
set "PNG_SCALE=1"
set "MARGIN=28"
set "OUTPUT_DIR="
set "PAUSE_AT_END=yes"
```

Meaning:

| Setting | Meaning |
|---|---|
| `MODE=both` | create PNG and SVG |
| `PNG_SCALE=1` | PNG scale factor |
| `MARGIN=28` | margin around content |
| `OUTPUT_DIR=` | empty means: next to the source file |
| `PAUSE_AT_END=yes` | keep window open after completion |

## Direct Python use

```bat
py graph_to_images.py spiral.graph --mode both
```

Clean render is the default. Classic render:

```bat
py graph_to_images.py spiral.graph --mode both --style classic --crop grid --labels yes --grid yes
```

## Create preview sidecars

```bat
maak_graph_previews.bat
```

This creates files such as:

```text
spiral.preview.png
spiral.preview.svg
```
