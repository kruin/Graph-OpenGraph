# Snelstart

## Doel

Deze map bevat tools om `.graph`- en `.opn`-bestanden te bekijken en te renderen.

## Vereisten

Voor de Python-tools:

- Windows 10/11;
- Python beschikbaar als `py`;
- optioneel Pillow voor PNG-export.

Installeer Pillow indien nodig:

```bat
py -m pip install pillow
```

Voor Windows Explorer-thumbnails:

- Windows 10/11 64-bit;
- Visual Studio Build Tools 2022;
- workload: **Desktop development with C++**.

## Testen met voorbeeldbestand

```bat
view_graph.bat examples\spiral.graph
```

## PNG en SVG maken

```bat
maak_graph_png_svg.bat examples\spiral.graph
```

Of alle `.graph`- en `.opn`-bestanden in de actuele map:

```bat
maak_graph_png_svg.bat
```

## Clean render

V9 en later gebruiken standaard een rustige renderstijl:

- content-crop;
- labels uit in thumbnails;
- subtiele of geen gridlijnen;
- anti-aliased lijnen en dots;
- witte rand rond dots.

Klassieke render kan nog steeds:

```bat
py graph_to_images.py bestand.graph --mode both --style classic --crop grid --labels yes --grid yes
```
