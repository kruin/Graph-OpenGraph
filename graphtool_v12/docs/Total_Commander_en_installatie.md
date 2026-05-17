# Total Commander en installatie

## Probleem: “Toepassing is niet gevonden”

Deze melding betekent meestal dat Windows of Total Commander nog een oude of kapotte `.graph`-koppeling gebruikt. Dat is geen parserfout in het `.graph`/`.opn`-bestand.

Herstel:

```bat
install_graph_viewer_assoc.bat
```

Sluit daarna Total Commander en open hem opnieuw.

## Viewer instellen in Total Commander

Gebruik als externe viewer/F3-programma:

```text
Command:    C:\pad\naar\view_graph.bat
Parameters: "%P%N"
Start path: %P
```

Als de viewer in PATH staat of vanuit dezelfde map bereikbaar is:

```text
Command:    view_graph.bat
Parameters: "%P%N"
Start path: %P
```

## Batch-render in Total Commander

Alle `.graph`- en `.opn`-bestanden in de actieve map:

```text
Command:    maak_graph_png_svg.bat
Parameters:
Start path: %P
```

Alleen geselecteerde bestanden:

```text
Command:    maak_graph_png_svg.bat
Parameters: "%L"
Start path: %P
```

`%L` is een door Total Commander gemaakt tijdelijk lijstbestand met de geselecteerde bestanden. De batch herkent dat automatisch.

## PATH

Toevoegen aan PATH is voldoende voor:

- starten vanuit `cmd`;
- Total Commander-knoppen/commands met alleen `maak_graph_png_svg.bat`.

PATH is niet voldoende voor:

- dubbelklik op `.graph`;
- Windows-bestandskoppeling;
- herstel van een kapotte oude associatie.

Daarvoor is `install_graph_viewer_assoc.bat` nodig.

## F3 direct gerenderd in Total Commander

Gebruik niet alleen de Windows-`.graph`-koppeling. Voor **F3** moet Total Commander naar de F3-wrapper wijzen:

```text
Command/Program: C:\pad\naar\tc_f3_graph_viewer.bat
Parameters:      "%P%N"
Start path:      %P
```

De wrapper opent `.graph` in de Graph Viewer en stuurt andere bestanden terug naar de normale TC Lister.

Zie ook: `Total_Commander_F3_direct.md`.
