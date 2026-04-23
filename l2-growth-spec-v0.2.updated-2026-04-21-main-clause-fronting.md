# L2 Growth Specification v0.2

## 1. Doel

Deze specificatie definieert een verder aangescherpt werkmodel voor **L2 (groeiende taalboom)**.

Deze versie bouwt voort op **L2 Growth Specification v0** en voegt expliciet toe:
- lexicaal gesplitste predicaten
- onderscheid tussen **LEX**, **SYN** en **LOG** binnen L2
- constituentframes als hogere groeieenheden
- regels voor afleiding van **eenduidige SYN/LOG** uit een groeiende boom
- een praktische positie t.o.v. **binaire** en **n-aire** representatie

L2 verschilt principieel van L1:

- **L1**: boom bestaat vooraf; wordt recursief opgebouwd of getekend
- **L2**: boom bestaat niet vooraf; ontstaat stap voor stap tijdens invoer

De invoer in **LEX-volgorde** stuurt de groei van de structuur.

---

## 2. Kernidee

De boom groeit via iteratieve plaatsing van tokens:

1. neem volgend token uit lineaire invoer
2. zoek de **eerste open plek**
3. plaats token op die plek
4. update structuur en open plekken

Formeel:

```text
for token in input:
    slot = first_open_slot(token)
    place(token, slot)
    update_state()
```

Deze groeiregel blijft ongewijzigd.

Nieuw in v0.2:
- losse lexicale invulling gebeurt op **slots**
- hogere syntactische/logische eenheden worden bewaakt via **frames**
- projecties worden idealiter pas afgeleid **na voltooiing van de groeifase**

---

## 3. Basisbegrippen

### 3.1 GrowthState

Bevat de huidige partiële structuur.

Velden:
- `root`
- `openSlots` (geordende lijst)
- `history` (optioneel)
- `constituentFrames` (nieuw in v0.1)

---

### 3.2 OpenSlot

Een open plek waar een element geplaatst kan worden.

Velden:
- `hostNode`
- `slotType`
- `priority` (lage waarde = eerder)
- `occupied` (boolean)

---

### 3.3 SlotType (v0/v0.1/v0.2)

Minimale set voor eerste prototype:

- `LEX_LEFT_EDGE`
- `VERB_FINITE`
- `DET_SLOT`
- `NOUN_SLOT`
- `VERB_PARTICIPLE`

Deze lijst blijft voorlopig bewust klein en hardcoded.

---

### 3.4 ConstituentFrame (nieuw in v0.1, behouden in v0.2)

Een `ConstituentFrame` groepeert meerdere slots tot één hogere constituent.

Doel:
- lexicale invulling en hogere constituentstructuur van elkaar scheiden
- lexicaal gesplitste realisatie toelaten zonder SYN/LOG te versplinteren
- SYN en LOG uit een **stabiele hogere eenheid** laten afleiden

Minimale velden:
- `hostNode`
- `category` (bijv. `VP`)
- `memberSlots`
- `complete`
- `logicalKind` (bijv. `PRED`)

Indicatief skelet:

```java
public final class ConstituentFrame {
    public Node hostNode;
    public String category;
    public List<OpenSlot> memberSlots = new ArrayList<>();
    public boolean complete;
    public String logicalKind;
}
```

---

## 4. Selectieregel

Een token wordt geplaatst op:

> de eerste open slot waarvoor de constraints gelden

In v0/v0.1 zijn constraints nog impliciet of hardcoded per `slotType`.

Aanvulling in v0.1:
- een token vult **een slot**
- een constituent ontstaat pas als het relevante **frame** voldoende gevuld is

---

## 5. Meerlagigheid: LEX, SYN en LOG

### 5.1 Hoofdregel

Binnen L2 moeten drie niveaus worden onderscheiden:

- **LEX** = lineaire woordinvoer en lexicale invulling van slots
- **SYN** = hogere constituentstructuur
- **LOG** = logische/predicatieve rolstructuur

### 5.2 Gevolg

Lexicale segmentatie hoeft **niet** identiek te zijn aan SYN of LOG.

Dus:
- iets kan lexicaal gesplitst zijn
- terwijl het syntactisch één constituent blijft
- en logisch één eenheid blijft

### 5.3 Principiële regel

**LEX vult slots; SYN en LOG lezen voltooide frames.**

---

## 6. Lexicaal gesplitst predicaat

### 6.1 Kerngeval

In de zin:

```text
wie heeft de hond gebeten
```

is de predicaatketen lexicaal gesplitst:
- `heeft`
- `gebeten`

Maar op hogere niveaus hoeft die splitsing niet te worden overgenomen:
- **SYN** kan één `VP` blijven
- **LOG** kan één predicatiekern blijven

### 6.2 Besluit

Een lexicaal gesplitst predicaat wordt in L2 gemodelleerd als:
- meerdere lexicale slots
- binnen één hoger **predicaatframe / constituentframe**

### 6.3 Praktische regel

Voor dit v0.1-geval:
- `VERB_FINITE` en `VERB_PARTICIPLE` zijn aparte LEX-slots
- beide behoren tot één hoger `VP`-frame
- zodra beide relevante slots gevuld zijn, geldt het frame als `complete`
- dat voltooide frame wordt de bron voor:
  - één SYN-eenheid (`VP`)
  - één LOG-predicatiekern (`PRED` of vergelijkbaar)

---

## 7. SYN in L2

### 7.1 Hoofdregel

Voor L2 mag SYN niet simpelweg uit de directe woordsegmentatie worden afgeleid.

### 7.2 Bron van SYN

De bron voor een SYN-projectie in L2 is bij voorkeur:
1. een **stabiele hogere categorieknoop** / frame
2. met voorrang voor een **expliciete categorie** op die hogere knoop
3. en pas daarna, indien nodig, afleiding uit lagere structuur

### 7.3 Gevolg voor tijdelijke v0-regel

De tijdelijke v0-regel:

> SYN-label = opsomming van directe kindlabels

mag alleen als vereenvoudigde fallback gelden.

Voor lexicaal gesplitste predicaten is die regel inhoudelijk onvoldoende.

### 7.4 Werkregel voor L2

Binnen L2 geldt daarom:

- SYN leest bij voorkeur de **voltooide constituentstructuur**
- niet de losse lexicale slotlabels
- niet rechtstreeks de lineaire woordreeks

---

## 8. LOG in L2

### 8.1 Hoofdregel

LOG volgt evenmin noodzakelijk de lexicale segmentatie.

### 8.2 Gevolg

Wanneer een predicatie lexicaal over meerdere knopen verdeeld is, mag LOG toch één hogere logische eenheid behandelen.

Voor het basisgeval van `heeft ... gebeten` betekent dit:
- `heeft` en `gebeten` zijn twee LEX-instanties
- maar samen vormen zij één hogere logische predicatiekern

### 8.3 Werkregel

Binnen L2 leest LOG, net als SYN, bij voorkeur uit **voltooide frames** en niet uit losse lexicale slots.

---

## 9. Voorbeeld: "wie heeft de hond gebeten"

### 9.1 Input

```text
wie → heeft → de → hond → gebeten
```

### 9.2 Initiële open slots (in volgorde)

1. `LEX_LEFT_EDGE`
2. `VERB_FINITE`
3. `DET_SLOT`
4. `NOUN_SLOT`
5. `VERB_PARTICIPLE`

### 9.3 Nieuw frame in v0.1

Parallel aan deze slots bestaat een `VP`-frame:

```text
VP-frame
- slot A: VERB_FINITE
- slot B: VERB_PARTICIPLE
- SYN-category = VP
- logicalKind = PRED
```

### 9.4 Groeistappen

| stap | token     | slot               |
|------|-----------|--------------------|
| 1    | wie       | `LEX_LEFT_EDGE`    |
| 2    | heeft     | `VERB_FINITE`      |
| 3    | de        | `DET_SLOT`         |
| 4    | hond      | `NOUN_SLOT`        |
| 5    | gebeten   | `VERB_PARTICIPLE`  |

### 9.5 Toestand na stap 5

- alle slots zijn gevuld
- het `VP`-frame is `complete`
- de hogere `VP` kan nu stabiel worden behandeld als één constituent
- de predicatiekern kan logisch als één eenheid worden behandeld

### 9.6 Niveaus

**LEX**:
- `wie`
- `heeft`
- `de`
- `hond`
- `gebeten`

**SYN**:
- `NP`
- `VP`

**LOG**:
- `SUBJ`
- `PRED`
- `OBJ`

---

## 10. Relatie met projecties

### 10.1 Hoofdregel

Projecties beïnvloeden de groeilogica niet.

Volgorde:
1. eerst groeit de L2-structuur
2. daarna worden SYN/LOG/LEX afgeleid uit de voltooide structuur

### 10.2 Consequentie

Tijdens de groeifase is een knoop nog niet noodzakelijk stabiel als:
- eindknoop
- vertakkende knoop
- SYN-bronknoop
- LOG-bronknoop

### 10.3 Besluit

Voor L2 worden projecties idealiter pas afgeleid **na voltooiing van de groeifase** of ten minste na stabilisatie van de relevante frames.

### 10.4 Samenvattende regel

**Groei eerst; projecteer daarna.**

---

## 11. Binair versus n-air

### 11.1 Inhoudelijke positie

De voorbeelden rond lexicaal gesplitste predicaten zijn **geen doorslaggevend argument** voor een volledig binaire of volledig n-aire theorie.

Ze tonen vooral:
- dat LEX, SYN en LOG van elkaar moeten worden onderscheiden
- dat hogere eenheden niet simpelweg uit directe woordsegmentatie mogen worden afgeleid

### 11.2 Praktisch besluit voor L2

Voor OpenGraphEd wordt aanbevolen:
- **n-aire inhoudelijke structuur** toe te laten waar nodig
- **binaire normalisatie** eventueel alleen als technische tussenstap te gebruiken
- maar die binaire normalisatie niet als enige semantische waarheid te behandelen

### 11.3 Werkregel

Dus:
- inhoudelijk mag het model meerdere bijdragers aan één hogere constituent toelaten
- tijdelijk mag een renderer of backend desnoods een binaire hulpstructuur gebruiken
- SYN en LOG blijven dan toch uit de **oorspronkelijke voltooide structuur** lezen

---

## 12. Minimale uitbreidingen van het model

### 12.1 Aan te vullen in `GrowthState`

Toevoegen:
- `constituentFrames`

Indicatief:

```java
public final class GrowthState {
    public Node root;
    public List<OpenSlot> openSlots = new ArrayList<>();
    public List<String> history = new ArrayList<>();
    public List<ConstituentFrame> constituentFrames = new ArrayList<>();
}
```

### 12.2 Aan te vullen logica in `L2GrowthEngine`

Naast:
- `initialize()`
- `firstOpenSlot(...)`
- `place(...)`
- `updateState(...)`

moet `updateState(...)` in v0.1 ook:
- frame-compleetheid controleren
- zo nodig hogere constituentstatus activeren
- SYN/LOG-bronstatus voorbereiden

### 12.3 Minimale completion-regels

Voor het eerste predicate-frame-geval:
- een `VP`-frame is `complete` wanneer `VERB_FINITE` en `VERB_PARTICIPLE` beide gevuld zijn

Later uitbreidbaar naar:
- andere frame-typen
- alternatieve zinstypen
- optionele of variabele slots

---


## 13. Niet-transformationele hoofdzin en vooropplaatsing

### 13.1 Hoofdregel

Vooropplaatsing in de Nederlandse hoofdzin wordt in L2 **niet** gemodelleerd als:
- eerst een onderliggende structuur bouwen
- daarna één constituent verplaatsen

Maar als:
- de hoofdzin heeft **van meet af aan** een eigen groeiframe
- daarin bestaat een speciale **LEX1**-positie voor precies één vooropgeplaatst element
- daarnaast bestaat een aparte positie voor het **finiete werkwoord**
- SYN en LOG worden afgeleid van het **voltooide constituent- en predicatieframe**, niet van een verplaatsingsstap

### 13.2 Nieuwe slottypes

Voor hoofdzinnen wordt de minimale slotset uitgebreid met:
- `LEX1_FRONTED`
- `VFINITE_MAIN`

Eventueel later uitbreidbaar met:
- `POSTFIELD_SLOT`
- `CLAUSE_FINAL_VERB`
- andere zinstypische posities

### 13.3 MainClauseFrame

Naast `ConstituentFrame` wordt voor hoofdzinnen een specifiek frame aanbevolen:

```java
public final class MainClauseFrame {
    public Node hostNode;
    public String clauseType;      // bv. "MAIN"
    public OpenSlot lex1Fronted;
    public OpenSlot vfiniteMain;
    public List<OpenSlot> remainingSlots = new ArrayList<>();
    public boolean complete;
}
```

Doel:
- lineaire hoofdzinvolgorde expliciet modelleren
- vooropplaatsing zonder transformatie beschrijven
- LEX-oppervlaktevolgorde en SYN/LOG-structuur van elkaar scheiden

### 13.4 Constraint op LEX1

Voor `LEX1_FRONTED` geldt:
- precies **één** constituent mag dit slot vullen
- na vulling is het slot gesloten
- de occupant kan bijvoorbeeld zijn:
  - subject-NP
  - object-NP
  - adverbiale constituent
  - vraagwoord / wh-constituent

Dus: **één element voorop** wordt in L2 een eigenschap van het groeiframe, niet van een latere transformatie.

### 13.5 Constraint op finiet werkwoord

Voor `VFINITE_MAIN` geldt:
- het finiete werkwoord krijgt een eigen hoofdzinpositie
- dit slot is onafhankelijk van `LEX1_FRONTED`
- zo kan de Nederlandse hoofdzinvolgorde worden opgebouwd zonder verplaatsingsanalyse

### 13.6 Relatie tussen LEX, SYN en LOG

Bij hoofdzinvooropplaatsing geldt:
- **LEX** leest de oppervlakteslots (`LEX1_FRONTED`, `VFINITE_MAIN`, overige slots)
- **SYN** leest de voltooide constituentstructuur
- **LOG** leest de voltooide rol- en predicatiestructuur

Gevolg:
- een constituent kan in `LEX1` staan
- zonder dat SYN of LOG die constituent als "verplaatst" hoeven te behandelen

### 13.7 Voorbeeld: niet-transformationele hoofdzin

Voor:

```text
Wie heeft de hond gebeten
```

kan een hoofdzinframe er schematisch zo uitzien:

```text
MainClauseFrame
- LEX1_FRONTED      = wie
- VFINITE_MAIN      = heeft
- NP object frame   = de hond
- VP predicate part = gebeten
```

Daarbij geldt:
- **LEX**: `wie` staat in eerste woordpositie
- **SYN**: de relevante hogere constituenten blijven wat zij zijn
- **LOG**: de logische rol van `wie` verandert niet door zijn positie in `LEX1`

### 13.8 Samenvattende ontwerpregel voor hoofdzinnen

De centrale regel luidt:

> **Vooropplaatsing in de Nederlandse hoofdzin wordt in L2 niet transformationeel gemodelleerd, maar via een hoofdzinframe met een speciale `LEX1_FRONTED`-positie en een aparte finiete werkwoordspositie. SYN en LOG worden afgeleid van het voltooide frame, niet van een verplaatsingsstap.**

### 13.9 Praktische aanbeveling

Voor OpenGraphEd wordt aanbevolen:
- hoofdzinvooropplaatsing inhoudelijk als **frame-gedrag** te modelleren
- niet als transformatie
- `LEX1_FRONTED` en `VFINITE_MAIN` vroeg in het model op te nemen
- projecties pas na stabilisatie van het frame af te leiden

## 14. Scope van v0.2

Deze versie is nog steeds beperkt.

### In scope
- voorbeeldzin met lexicaal gesplitst predicaat
- niet-transformationele hoofdzin met vooropplaatsing
- vaste slottypes
- vaste volgorde
- eenvoudige constituentframes
- expliciet basisgeval van lexicaal gesplitst predicaat
- eenduidige afleiding van hogere SYN/LOG-eenheden uit een voltooid frame

### Buiten scope
- volledige grammatica
- algemene semantische interpretatie
- ambiguïteit
- backtracking
- volledige UI-integratie
- definitieve n-aire renderer
- volledige automatische LOG-afleiding voor alle constructietypen

---

## 15. Aanbevolen implementatievolgorde

1. `GrowthState` uitbreiden met `constituentFrames`
2. `ConstituentFrame` toevoegen
3. `updateState(...)` uitbreiden met frame-completion
4. `VP`-frame voor het basisgeval toevoegen
5. LEX-slots los houden van SYN/LOG-afleiding
6. projectie-engine pas laten lezen uit gestabiliseerde frames / voltooide structuur

---

## 16. Samenvattende ontwerpregel

De centrale ontwerpregel van deze versie luidt:

> **Slots vullen woorden; frames vullen constituenten; SYN en LOG lezen frames, niet losse lexicale slots.**

Voor lexicaal gesplitste predicaten betekent dit:

> **meerdere LEX-instanties kunnen samen precies één SYN- en één LOG-eenheid voeden.**

---

## 17. Volgende uitbreidingen

- formele constraints per slot
- formele constraints per frame
- dynamische slotcreatie
- meerdere frame-typen
- meerdere zinstypen
- preciezere LOG-roldefinitie
- expliciete relatie tussen frame-completion en projectiegeneratie
- expliciete omgang met 1, 2, 3 of meer dochters in SYN
- eventuele tijdelijke binaire normalisatie als render-backend

---

## 18. Wijziglog

- **2026-04-21**: v0.2 toegevoegd met niet-transformationele modellering van Nederlandse hoofdzinvooropplaatsing via `MainClauseFrame`, `LEX1_FRONTED` en `VFINITE_MAIN`.
- **2026-04-21**: expliciet vastgelegd dat hoofdzinvooropplaatsing in L2 via frame-gedrag wordt beschreven en niet als transformatie.
- **2026-04-21**: v0.1 toegevoegd met expliciete modellering van `ConstituentFrame`, lexicaal gesplitst predicaat, scheiding tussen LEX/SYN/LOG en de regel dat SYN/LOG uit voltooide frames worden afgeleid.
- **2026-04-21**: inhoudelijke positie toegevoegd dat lexicale splitsing geen directe SYN-splitsing forceert en dat binaire normalisatie hoogstens een technische tussenstap mag zijn.
- **2026-04-21**: voorbeeld `wie heeft de hond gebeten` uitgebreid met `VP`-frame, `VERB_FINITE` + `VERB_PARTICIPLE`, en eenduidige afleiding van hogere SYN/LOG.
- **2026-04-21**: notitie behouden dat de opslaglaag erboven voortaan `.opn` heet; dit verandert niets aan het groeimodel zelf.
- **2026-04-21**: eerdere v0-inhoud behouden als basisstructuur.
