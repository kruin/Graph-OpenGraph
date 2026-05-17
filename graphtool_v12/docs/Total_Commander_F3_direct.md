# Total Commander: F3 direct rendered `.graph`

Doel: in Total Commander op een `.graph`/`.opn`-bestand drukken op **F3** en meteen de gerenderde graph-viewer krijgen.

## Belangrijk

Windows-bestandskoppeling is hiervoor niet genoeg. F3 gebruikt in Total Commander de viewer/Lister-route. Daarom is er een aparte F3-wrapper:

```text
tc_f3_graph_viewer.bat
```

Deze wrapper doet twee dingen:

1. `.graph` → openen met `Graph Viewer`.
2. andere bestanden → terugvallen naar de normale Total Commander Lister via `%COMMANDER_EXE% /S=L`.

## Instellen

In Total Commander:

1. Open **Configuration** / **Options**.
2. Ga naar **Edit/View** of **Operation → Edit/View**, afhankelijk van TC-versie/taal.
3. Zet bij **Viewer for F3** / **F3-viewer** de externe viewer op:

```text
C:\pad
aar	c_f3_graph_viewer.bat
```

4. Parameters:

```text
"%P%N"
```

5. Start path:

```text
%P
```

Daarna:

```text
F3 op bestand.graph  -> Graph Viewer met rendered graph
F3 op bestand.txt    -> normale TC Lister
```

## Test

Gebruik het meegeleverde bestand:

```text
examples\spiral.graph
```

Druk in Total Commander op **F3**. De viewer moet het gerenderde spiral-beeld tonen, niet de ruwe tekst.

## Debug

Als er niets gebeurt of als TC een oude koppeling gebruikt, stel tijdelijk deze batch in als F3-viewer:

```text
tc_f3_graph_viewer_debug.bat
```

Dan blijft het consolevenster open en zie je onder meer:

- het bestand dat TC doorgeeft;
- de extensie;
- `COMMANDER_EXE`;
- `COMMANDER_PATH`.

## Niet gebruiken voor F3

Deze scripts zijn nuttig, maar lossen F3 niet volledig op:

```text
install_graph_viewer_assoc.bat
view_graph.bat
```

`install_graph_viewer_assoc.bat` regelt Windows-dubbelklik/rechtsklik. Total Commander F3 volgt die Windows-koppeling niet betrouwbaar; gebruik daarom de F3-wrapper.
