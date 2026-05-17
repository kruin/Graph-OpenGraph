# Quick start

## Purpose

This folder contains tools for viewing and rendering `.graph` and `.opn` files.

## Requirements

For the Python tools:

- Windows 10/11;
- Python available as `py`;
- optional Pillow package for PNG export.

Install Pillow if needed:

```bat
py -m pip install pillow
```

For Windows Explorer thumbnails:

- Windows 10/11 64-bit;
- Visual Studio Build Tools 2022;
- workload: **Desktop development with C++**.

## Test with an example file

```bat
view_graph.bat examples\spiral.graph
```

## Create PNG and SVG

```bat
maak_graph_png_svg.bat examples\spiral.graph
```

Or render all `.graph` and `.opn` files in the current folder:

```bat
maak_graph_png_svg.bat
```

## Clean render

V9 and later use a quieter render style by default:

- content crop;
- labels off in thumbnails;
- subtle or hidden grid lines;
- anti-aliased lines and dots;
- white outline around dots.

Classic render is still available:

```bat
py graph_to_images.py file.graph --mode both --style classic --crop grid --labels yes --grid yes
```
