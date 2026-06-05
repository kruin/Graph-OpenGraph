# JAN Open Notation Viewer — PWA v4348

Mobiele/desktop PWA-viewer voor **JAN/Open Notation**.

## Scheiding vanaf v4348

```text
viewer   = één JSON/grow-weergave afspelen en testen
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

## Knoppen

- `Passend maken`: herberekent de zichtbare tekening en past de SVG-viewBox opnieuw aan de zichtbare knopen aan. Dit verandert geen JSON en geen stappen.
- `JSON laden`: opent een eigen `*_grow_demo.json`, bijvoorbeeld uit OpenGraphEd via `Greedy → Export Greedy Grow JSON`.
- `Lijnen tonen`: toont echte JSON-edges, of — als die ontbreken — afgeleide groeilijnen tussen opeenvolgende stappen.
- `JAN-carousel openen`: opent de aparte uitleglaag met beelden en teksten.

## Carousel

De web-carousel gebruikt de bestaande OpenGraph-carouselbeelden en teksten:

```text
mobile/greedy-grow-viewer/carousel/
opengraph_carousel/
```
