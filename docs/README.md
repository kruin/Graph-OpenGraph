# JAN Open Notation Viewer — PWA v4355

Mobiele/desktop PWA-viewer voor **JAN — Just Another Notation** binnen OpenGraph.


## Nieuw in v4355

- Config prominenter bovenaan zichtbaar.
- Contextuele `?`-uitleg bij de belangrijkste opties.
- Uitleg maakt onderscheid tussen visuele opties, constraints en browser-generatie.
- `Config & uitleg` staat standaard open zodat nieuwe gebruikers meteen zien wat keuzes doen.

## Scheiding

```text
viewer   = één JSON/grow-weergave afspelen, controleren en beperkt hergenereren
carousel = uitlegbeelden en didactische taalboomvoorbeelden tonen
```

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

Carousel:

```text
http://localhost:8088/carousel/index.html
```

## Testpunten

1. `v4355` staat in de kop.
2. `Config ▾` opent/sluit het configuratiepaneel bovenaan.
3. `Genereer` of `Genereer Greedy-layout` springt naar het eindbeeld en toont feedback.
4. `Meer → Config` werkt op mobiel.
5. `JAN-carousel` opent de didactische uitleglaag.

## Concept

JAN staat hier voor **Just Another Notation**: geen vervanging van taalkundige analyse, maar een open kijknotatie. De viewer toont vrije HOR/VER-plaatsing en groeilijnen; de carousel legt stap voor stap uit waarom deze open notatie ruimte maakt voor projecties zoals LEX, SYNT en LOG.
