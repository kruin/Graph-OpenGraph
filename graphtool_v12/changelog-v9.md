# Changelog v9 — clean render

Datum: 2026-05-15
Project: Mapping V4 / graph tools

## Aanleiding

De Windows Explorer thumbnails werkten, maar de weergave was visueel te druk en slecht leesbaar op kleine formaten.

## Wijzigingen

- Windows thumbnail-provider tekent nu in **clean render style**:
  - content-crop in plaats van volledig grid-crop;
  - geen labels in kleine thumbnails;
  - minder gridruis;
  - subtiele achtergrond en rand;
  - anti-aliased lijnen en dots;
  - wit dot-randje voor leesbaarheid op lichte en donkere achtergronden.
- Windows Preview Pane handler gebruikt dezelfde cleanere interpretatie.
- `graph_to_images.py` uitgebreid met:
  - `--style clean|classic`
  - `--crop content|grid`
  - `--labels auto|yes|no`
- `maak_graph_previews.bat` maakt standaard cleane previews zonder grid/labels.
- `tc_f3_graph_lister_png.bat` maakt tijdelijk een cleane PNG voor F3/Lister.

## Compatibiliteit

Klassieke uitvoer blijft beschikbaar:

```bat
py graph_to_images.py bestand.graph --mode both --style classic --crop grid --labels yes --grid yes
```

## Na installeren

Voor Explorer-thumbnails:

```bat
install_windows_thumbnail_provider.bat
windows_shell_thumbnail\clear_thumbnail_cache.bat
```

Daarna Explorer sluiten en opnieuw openen.
