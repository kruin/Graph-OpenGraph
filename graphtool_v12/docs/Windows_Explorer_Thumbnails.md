# Windows Explorer thumbnails activeren

Deze v6 voegt een native C++ thumbnail-provider toe voor `.graph`.

## Installatie

```bat
install_windows_thumbnail_provider.bat
```

## Gebruik

Open daarna een map met `.graph`- en `.opn`-bestanden in Windows Explorer en kies:

```text
View → Large icons
```

of:

```text
View → Extra large icons
```

## Cache wissen

Als Explorer nog oude witte documenticonen toont:

```bat
windows_shell_thumbnail\clear_thumbnail_cache.bat
```

Deze batch herstart Explorer.

## Verschil met eerdere onderdelen

| Onderdeel | Functie |
|---|---|
| `graph_viewer.py` | Eigen viewer-venster |
| `tc_f3_graph_lister_png.bat` | F3 in Total Commander via PNG/Lister |
| Windows Preview Handler | Explorer Preview Pane / Alt+P |
| Native Thumbnail Provider | Miniatuur/pictogram-preview in Explorer-mapweergave |

## Niet hetzelfde als Total Commander Ctrl+Q

Deze thumbnail-provider is voor Windows Explorer thumbnails. Total Commander gebruikt eigen Lister/plug-inroutes. Voor TC blijft de praktische route:

```text
F3 → tc_f3_graph_lister_png.bat
```


## v7 buildfix

Als v6 meldde:

```text
cl : Command line error D8003 : missing source filename
```

gebruik dan v7. De build gebruikt nu een response-file en controleert eerst expliciet of `GraphThumbnailProvider.cpp` en `GraphThumbnailProvider.def` bestaan.
