# OpenGraph Lite Viewer — PWA v4385

Browser-native proefversie van **OpenGraphEd Lite** voor **JAN — Open Notation**.

## Doel van deze fase

Deze versie beperkt de test bewust tot drie stappen:

1. maak eerst de centrale boom voor `HOND BIJT MAN` correct;
2. teken daarna de LEX-projectie;
3. regel pas daarna lokaal op de LEX-as het uitingtype `OMDAT DE HOND DE MAN BIJT`.

De centrale boom blijft invariant. `OMDAT` en determinatoren worden dus niet in de centrale syntaxboom ingevoegd.

## Recursieve syntaxboom

De syntaxboom wordt niet top-down met vaste handmatige posities geschreven. De layout gebruikt een bottom-up rekengang:

1. bereken eerst alle leaf-boxes;
2. bereken daarna elke parent-subtree uit de reeds berekende child-boxes;
3. plaats child-subtrees als complete boxen op de eerste vrije HOR/VER-positie;
4. reserveer gebruikte rijen, kolommen en boxruimte;
5. render pas daarna de boxen, lijnen en knopen.

De structurele volgorde blijft left-first, maar de tekenvolgorde is bottom-up:

```text
HOND / MAN / BIJT
→ V(BIJT), NP(MAN), NP(HOND)
→ VP(NP(MAN), V(BIJT))
→ S(NP(HOND), VP(...))
```

De centrale syntaxboom is:

```text
S
├─ NP
│  └─ HOND
└─ VP
   ├─ NP
   │  └─ MAN
   └─ V
      └─ BIJT
```

## Recursieve OPN-functionele structuur

De OPN-functionele structuur gebruikt dezelfde bottom-up gedachte, maar met een eigen **n-ary role-box-layout**. De bron is niet `BIJT` als root; de root is expliciet `CLAUSE`:

```text
CLAUSE
├─ AGENS   → HOND
├─ PRED    → BIJT
└─ PATIENS → MAN
```

De plaatsing heeft een menuconfiguratie:

- `left-first`: AGENS/PRED/PATIENS zoeken om beurten eerst links/rechts/links naar een vrije HOR/VER-corridor;
- `right-first`: dezelfde n-ary structuur, gespiegeld: eerst rechts/links/rechts.

Deze keuze staat in het menu **Functioneel order** en wordt meegenomen in JSON/.OPN-export.

## Projecties

- **Bron**: toont de gekozen OPN-bron: syntaxboom of functionele structuur.
- **LEX**: toont de lokale uitingtype-regel.
- **SYNTAX-projectie**: toont alleen regels, geen rollenboom.
- **LOG/FT**: toont de OPN-functionele structuur.

## Start lokaal

Dubbelklik in deze map op:

```bat
start-local-viewer.bat
```

Open daarna:

```text
http://localhost:8088
```

Gebruik na updates zo nodig `Ctrl+F5` of verwijder de oude service worker.

## Testpunten

1. Open `Bron → OPN · syntaxboom` en controleer de bottom-up vrije boxplaatsing.
2. Open `Bron → OPN · functionele structuur` en wissel `Functioneel order` tussen `left-first` en `right-first`.
3. Controleer dat `AGENS/PRED/PATIENS` niet als binaire boom worden behandeld.
4. Open `Assen` en controleer dat LEX apart blijft.
5. Kies `OMDAT DE HOND DE MAN BIJT` en controleer dat alleen de LEX-as verandert.

## v4385-correctie functioneel

`OPN · functionele structuur` tekent nu zichtbaar `CLAUSE > AGENS/PRED/PATIENS`. `BIJT` is alleen de leaf onder `PRED` en mag dus niet meer als centrale root van een driehoek verschijnen.

## v4385-noot

De header/subtitel is bewust gelijk gehouden: **Redesign: eerst syntax-tree, daarna LEX-projectie, daarna lokale LEX-regel.**  
Om te voorkomen dat de browser alleen `index.html` vernieuwt maar een oude `viewer.js` houdt, laadt `index.html` nu `viewer.js?v4385` en `styles.css?v4385`.
