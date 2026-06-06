# OpenGraph Lite Viewer — PWA v4380

Browser-native proefversie van **OpenGraphEd Lite** voor **JAN — Just Another Notation**.

## Nieuw in v4380

- voorbeeld toegevoegd: `WIE HEEFT DE HOND GEBETEN`
- assenweergave aangescherpt: LEX links, SYNTAX rechts, LOGICAL onder
- gridcorrectie: elke SYNTAX-boomknoop heeft een eigen rasterkruispunt

- Hoofdscherm is nu een kleine taalboom-editor in plaats van alleen een Greedy Grow-afspeler.
- Vaste voorbeelden: `HOND BIJT MAN`, `MAN BIJT HOND`, `HOND MAN BIJT`, `WIE HEEFT DE HOND GEBETEN`, `VROUW HEEFT TRUI GEBREID`, `TRUI HEEFT VROUW GEBREID`, `MAN WORDT DOOR HOND GEBETEN`.
- Hoofdbeeld **Assen**: vrije bron in het midden, **LEX links** met eindknopen, **SYNTAX rechts**, **LOGICAL onder**.
- Focusprojecties blijven beschikbaar: **Bron**, **LEX**, **SYNT**, **LOG/FT**.
- Beperkte edits: knopen slepen, toevoegen, dupliceren, verwijderen, label/categorie/rol wijzigen.
- LEX-plaatsingsregels: SVO, SOV, Nederlands perfectum, topicalisatie en passief.
- Relaties toevoegen/verwijderen.
- JSON import/export en compacte `.opn` export/import.

## Start lokaal

Dubbelklik in deze map op:

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

## Afbakening

Deze Lite-versie neemt de voor OpenGraph/JAN relevante Java-editorconcepten over: graph-core, editing, projecties, bron/LEX/SYNT/LOG en import/export.

Niet meegenomen in deze eerste stap: klassieke JGraphEd-algoritmen zoals planarity, Dijkstra, MST, biconnectivity, canonical ordering en algemene graph analysis.

## Testpunten

1. Open `index.html` via de lokale server.
2. Kies elk voorbeeld in de dropdown.
3. Controleer het assenbeeld: LEX links, SYNTAX rechts, LOGICAL onder. Wissel daarna eventueel tussen Bron / LEX / SYNT / LOG/FT.
4. Sleep een vrije bronknoop in Assen of Bron; controleer dat LEX/SYNTAX/LOGICAL als projecties meebewegen.
5. Wijzig label/categorie/rol van een knoop.
6. Pas een LEX-plaatsingsregel toe.
7. Download JSON, laad die opnieuw en controleer of edits bewaard blijven.


## Correctie v4380

- Centrale OPN-modus gebruikt nu expliciete waarden `opn-syntax` en `opn-functional`.
- `SYNTAX-projectie` blijft alleen de rechter projectie; openen van OPN/Assen opent niet meer impliciet `synt`.
- Oude imports met `opn_center: synt` of `opn_center: functional` worden automatisch genormaliseerd.

## Correctie syntaxboom v4380

- Centrale OPN-syntaxboom is nu invariant en gebruikt exact:
  - `S → NP VP`
  - `VP → NP V`
  - `V → pv VDW` wanneer een persoonsvorm + voltooid deelwoord aanwezig zijn
- `Comp/(om)dat`, determinatoren en vooropplaatsing blijven lokale LEX-as-elementen.
- Geen transformaties op de centrale boom; uitingtype-regels werken alleen op de LEX-as.


## Correctie v4380

De centrale OPN-syntaxboom gebruikt nu vrije HOR/VER-boxplaatsing: child-subtrees worden eerst gemeten, daarna zoekt iedere child-box de eerste vrije plek waar de root-rij en root-kolom nog niet bezet zijn. De tweede binaire child start onder de echte ondergrens van de eerste child-box; hij wordt niet meer als naastliggende container-child geplaatst.
