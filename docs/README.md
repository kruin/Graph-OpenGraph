# JAN Open Notation Viewer — PWA v4352

Mobiele/desktop PWA-viewer voor **JAN/Open Notation**.

## Nieuw in v4352

- Desktop-toolbar is gesplitst: navigatie links, bestand/Greedy-acties rechts.
- Mobile gebruikt een vaste bottom bar: Vorige, Volgende, Genereer, Meer.
- Het mobiele Meer-menu geeft toegang tot eerste/laatste stap, play, passend maken, herstel, download, config en JSON laden.
- De statusregel meldt expliciet hoeveel knopen zijn gegenereerd, met style/rule/diagonal_free.


De viewer bevat nu een eerste **browser-Greedy-generator** naast de bestaande afspeelviewer.

```text
Genereer layout      = bereken nieuwe HOR/VER-vrije plaatsing in de browser
Herstel JSON-layout  = keer terug naar de geladen bronplaatsing
Download JSON        = bewaar de actuele viewer-JSON
```

De browser-generator ondersteunt:

```text
style: near0 / maxturn / quadrant / ring
rule: none / extension / collinear / angle
diagonal_free: none / slash / backslash / both
```

De generator wijzigt de zichtbare/gelimiteerde knopen. De viewer is nog geen volledige graph-editor: knopen slepen, labels bewerken en edges tekenen zitten nog in de desktopversie.

## Scheiding vanaf v4348

```text
viewer   = één JSON/grow-weergave afspelen, controleren en beperkt hergenereren
carousel = uitlegbeelden en didactische voorbeelden tonen
```

De viewer bevat daarom geen voorbeeldkeuzelijst meer.

## Start lokaal

Dubbelklik in de projectroot op:

```bat
START-GREEDY-GROW-VIEWER.bat
```

Of start in deze map:

```bat
start-local-viewer.bat
```

Open daarna:

```text
http://localhost:8088
```

Open de carousel:

```text
http://localhost:8088/carousel/index.html
```

Voor telefoon op hetzelfde netwerk:

```text
http://<pc-ip>:8088
```

## Knoppen en velden

- `Greedy stijl`: kiest de sorteervolgorde van kandidaatpunten.
- `Regel`: verbiedt bepaalde groeilijnen.
- `Minimumhoek`: gebruikt bij `rule=angle`.
- `Diagonal-free controle`: kiest welke extra diagonal constraints bovenop HOR/VER worden gecontroleerd en gegenereerd.
- `Genereer layout`: berekent een nieuwe plaatsing in de browser.
- `Herstel JSON-layout`: zet de geladen JSON-plaatsing terug.
- `Download JSON`: downloadt de actuele viewer-JSON.
- `Conflicten tonen`: markeert zichtbare conflicterende knopen.
- `Passend maken`: herberekent de zichtbare tekening en past de SVG-viewBox opnieuw aan de zichtbare knopen aan. Dit verandert geen stappen.
- `JSON laden`: opent een eigen `*_grow_demo.json`, bijvoorbeeld uit OpenGraphEd via `Greedy → Export Greedy Grow JSON`.
- `Lijnen tonen`: toont echte JSON-edges, of — als die ontbreken — afgeleide groeilijnen tussen opeenvolgende stappen.
- `JAN-carousel openen`: opent de aparte uitleglaag met beelden en teksten.
