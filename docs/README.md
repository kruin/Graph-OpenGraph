# OpenGraph Lite Viewer — PWA v4394

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

## v4394-correctie functioneel

`OPN · functionele structuur` tekent nu zichtbaar `CLAUSE > AGENS/PRED/PATIENS`. `BIJT` is alleen de leaf onder `PRED` en mag dus niet meer als centrale root van een driehoek verschijnen.

## v4394-noot

De header/subtitel is bewust gelijk gehouden: **Redesign: eerst syntax-tree, daarna LEX-projectie, daarna lokale LEX-regel.**  
Om te voorkomen dat de browser alleen `index.html` vernieuwt maar een oude `viewer.js` houdt, laadt `index.html` nu `viewer.js?v4394` en `styles.css?v4394`.


## v4394 — OPN-slot voor vooropplaatsing

De OPN-bronnen reserveren nu expliciet een plaats voor vooropplaatsing/topicalisatie:

- in de OPN-syntaxboom: tussen `S` en de bovenste boomlaag;
- in de OPN-functionele structuur: tussen `CLAUSE` en de bovenste role-boxen;
- in de LEX-projectie: `slot 1 · vooropplaatsing` projecteert horizontaal mee met de OPN-bron;
- `slot 0 · Comp/(om)dat` blijft het hogere lokale LEX-slot voor bijzinnen.

De functionele structuur heeft daarmee, net als de syntaxboom, een eigen LEX-projectie. De functionele structuur blijft n-ary en gebruikt de config `left-first` / `right-first` voor de vrije role-boxplaatsing.


## v4394 — voorbeeldzinnen als HTML-input

De actieve voorbeeldzinnen staan nu ook in `examples-input.html`.

- `<strong>` markeert het gekozen subject.
- `<em>` markeert het gekozen object.
- De viewer leest deze HTML-input bij opstarten wanneer hij via de lokale server draait.
- De knop **Wissel S/O** wisselt de lexicale vulling van subject en object. De grammaticale markering blijft gelijk: vet blijft subject, cursief blijft object.

Actieve beginvoorbeelden:

```html
<strong>HOND</strong> BIJT <em>MAN</em>
OMDAT DE <strong>HOND</strong> DE <em>MAN</em> BIJT
```

## v4394 — voorbeeldzinnen-editor

Toegevoegd:

```text
examples-editor.html
```

Functie:

- voorbeelden toevoegen;
- voorbeelden verwijderen;
- voorbeeld kopiëren;
- tokens bewerken;
- subject/object markeren;
- subject/object-vulling wisselen;
- LEX-regel kiezen;
- source/slot per token instellen;
- `examples-input.html` downloaden of via Chrome/Edge opslaan als HTML.

Conventie blijft:

```html
<strong>SUBJECT</strong>
<em>OBJECT</em>
```

De viewer leest `examples-input.html` bij start. Na wijziging via de editor: download de nieuwe `examples-input.html`, vervang het bestand in de viewer-map en herlaad de viewer hard met `Ctrl+F5`.


## v4394 — structure-config als eerste stap

De werkwijze is aangescherpt:

1. Bewerk eerst `structure-config.html` via `structure-editor.html`.
2. De syntax-config bevat **geen lexicale woorden** meer. Zij bevat alleen abstracte structurele posities/projectiebronnen.
3. Lexicale woorden zoals `hond`, `man`, `vrouw`, `trui`, `bijt` en `breit` staan uitsluitend in `lexicon-config.html` en in de voorbeeldzinnen.

Correct basispatroon:

```text
s:S [cat=S] -> np-subj vp
np-subj:NP [cat=NP] -> subj
subj:{subject} [leaf role=subject source=subject cat=N]
vp:VP [cat=VP] -> np-obj v
np-obj:NP [cat=NP] -> obj
obj:{object} [leaf role=object source=object cat=N]
v:V [cat=V] -> pred
pred:{predicate} [leaf role=predicate source=predicate cat=V]
```

Functioneel idem: n-ary role-boxes met dezelfde abstracte sources `subject`, `predicate` en `object`.

De knop `Voorbeeld VP: NP-VP / VP: pv-VDW` maakt een nieuwe VP-regelset met structurele posities `pv` en `vdw`; dat zijn dus geen lexicale items.

## v4394 — bescheiden lexicon

Er is nu een aparte lexiconbron toegevoegd:

```text
lexicon-config.html
```

De voorbeeldzinnen-editor gebruikt dit lexicon voor de zichtbare woordvulling. De structure-config blijft verantwoordelijk voor de boomstructuur en de projectiebronnen.

Startlexicon:

- NP/N: `man`, `hond`, `kat`, `vrouw`, `trui`
- V: `bijt(en)`, `brei(en)`
- AUX: `heeft`, `hebben`, `is`
- DET: `de`, `het`, `een`
- COMP: `omdat`, `dat`

De editor houdt daarom twee lagen uit elkaar:

```text
lexeme = zichtbaar woord
source  = structurele projectiebron
```

Voorbeeld: `VROUW` kan als subject zichtbaar zijn, maar blijft projecteren naar de structurele subjectbron.

## v4394 — perfectumregel

Toegevoegd aan de structure-laag:

```text
vp:VP [cat=VP] -> np-obj vp-perfectum
vp-perfectum:VP [cat=VP] -> pv vdw
pv:{pv} [leaf role=aux source=pv cat=AUX]
vdw:{vdw} [leaf role=participle source=vdw cat=V]
```

Deze regel beschrijft het perfectum `heeft gebeten` structureel als twee projectiebronnen:

- `pv` = persoonsvorm / hulpwerkwoord, bijvoorbeeld `HEEFT`
- `vdw` = voltooid deelwoord, bijvoorbeeld `GEBETEN`

De lexicale woorden blijven in `lexicon-config.html`; de structure-config bevat alleen posities en bronnen. De voorbeeldzinnen-editor heeft een nieuw patroon `perfectum: Det S HEEFT Det O VDW`.
