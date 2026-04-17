# projectie-master-spec

## Doel
Dit is het centrale, duurzame specificatiebestand voor het project **Projectie**.

Doel van dit bestand:
- de inhoudelijke afspraken centraal bewaren
- de actuele werktermen en structuurtypes vastleggen
- een brug vormen tussen chat-afspraken en projectbestanden
- bij toekomstige projectzips steeds meegeleverd worden

---

## 1. Kernmapping

| Richting | Label | Betekenis | Status |
|---|---|---|---|
| W / west | **LEX** | lexicale projectie | actief |
| O / oost | **SYN** | syntactische projectie | actief |
| Z / zuid | **LOG** | logische projectie (`log`, `LF`) | actief |
| N / noord | **PM** | nog te bepalen | open |

### Compacte interne notatie
```text
W = LEX
O = SYN
Z = LOG
N = PM
```

---

## 2. Deelname per projectie

### 2.1 LEX
**Omschrijving**
- language tree / lexicale projectie

**Deelnameregel**
- alleen **eindknopen** doen mee

**Open preciseringen**
- formele definitie van `eindknoop`
- behandeling van leestekens, lege knopen, samengestelde woorden

### 2.2 SYN
**Omschrijving**
- syntactische projectie

**Deelnameregel**
- alleen **categorieknooppunten** doen mee

**Voorlopige categorieën**
- `S`
- `NP`
- `VP`
- `Det`

**Open preciseringen**
- formele definitie van `catknoop`
- uitbreiding van de categorielijst
- status van tussenknopen / hulpknopen

### 2.3 LOG
**Omschrijving**
- logische projectie
- `log` / `LF`

**Deelnameregel**
- alleen **logische categorieën** doen mee

**Voorlopige categorieën**
- `V = Verb`
- `S = Subject`
- `O = Object`

**Open preciseringen**
- uitbreiding van de logische categorielijst
- relatie tussen syntactische en logische labels
- status van adjuncten en operatoren

### 2.4 N / PM
**Omschrijving**
- nog te bepalen

**Status**
- open werkpunt

**Mogelijke richtingen**
- morfologische projectie
- semantische projectie
- pragmatische projectie
- informatiestructurele projectie

---

## 3. Structuurtype

### 3.1 Overzicht

| Type | Naam | Korte omschrijving | Status |
|---|---|---|---|
| D | **demo** | handmatig getekende boomstructuur | actief |
| L | **language tree** | taalboom met projecties LEX-SYN-LOG | actief |
| R | **rest** | groeiende structuur volgens plaatsingsregels | voorlopig / open |

### 3.2 Type D = demo
**Omschrijving**
- eenvoudige, handmatig getekende boomstructuur
- eerdere naam: `Type D simple`
- werknaam voortaan: **type demo**

**Input**
- een door de gebruiker getekende structuur (zeg: boom)

**Verwerking**
- de app zet deze om naar **opentree**
- projecties zijn beschikbaar via **toggle**

**Open punten**
- exacte definitie van de omzetting naar opentree
- precieze rol van projectietoggle bij type D

### 3.3 Type L = language tree
**Omschrijving**
- taalboom
- heeft drie projecties:
  - **LEX**
  - **SYN**
  - **LOG**

**Subtype L1: getekende boom met labels**
- input is een getekende boom
- knooplabels zijn ingevuld
- ombouw gebeurt via de app
- richting: **bottom-up**, recursief
- referentie: bestaande code

**Subtype L2: groeiende boom**
- dit is expliciet een **tree / boomstructuur**
- de boom wordt opgebouwd tijdens het proces
- het bepalende kenmerk is **groei**, niet recursie
- regels bepalen **top-down**:
  - welke tak wordt geschreven
  - welke knoop wordt geschreven
  - waar dit in de boomstructuur gebeurt

**Open punten**
- formele definitie van L1-ombouw
- formele definitie van L2-groeiregels
- relatie tussen L1 en L2: zelfde doelformaat of niet?

### 3.4 Type R = rest
**Omschrijving**
- restcategorie / nog nader te bepalen
- `R` kan een **tree** zijn, maar ook een **andere structuur**
- `R` kan zowel **groeiend** als **getekend** zijn

**Huidige werkdefinitie**
- `R` is voorlopig een verzameltype voor structuren die niet netjes onder `D` of `L` vallen
- de precieze afbakening is nog open

**Voorlopige subtypen**
- **R1** = tree, getekend
- **R2** = tree, groeiend
- **R3** = niet-tree, getekend
- **R4** = niet-tree, groeiend

**Belangrijk onderscheid t.o.v. L2**
- **L2** is altijd een **boom / tree**
- bij **L2** is **groei** het bepalende kenmerk
- **R** hoeft geen boom te zijn
- **R** kan zowel een groeiende als een getekende structuur zijn
- **R2** is een groeiende tree binnen restcategorie `R`, niet automatisch een language tree

**Open punten**
- precieze formele definitie van `R`
- of `R` werkelijk een resttype blijft of later wordt opgesplitst
- welke niet-boomstructuren hieronder vallen
- welke groeiregels of omzetregels voor `R` gelden

---

## 4. Terminologie

### Werktermen
- **eindknoop**: terminale knoop in de boomstructuur; exacte definitie nog vast te leggen
- **catknoop**: knoop die een syntactische categorie draagt; exacte definitie nog vast te leggen
- **logische categorie**: knoop of label op logisch niveau; exacte definitie nog vast te leggen

---

## 5. Projectieregels

### Algemene regel
Elke projectie selecteert alleen de knopen die volgens haar eigen deelnameregel zijn toegestaan.

### Huidige stand
- **LEX** selecteert eindknopen
- **SYN** selecteert categorieknooppunten
- **LOG** selecteert logische categorieën
- **N** nog niet vastgelegd

---

## 6. Projectielayout en gridgedrag

### 6.1 Doel
Deze sectie legt vast hoe projectieknopen en projectielabels worden geplaatst ten opzichte van de bronstructuur en het grid.

### 6.2 Hoofdregel
Het **grid blijft onveranderd** wanneer projecties worden toegevoegd.

Dat betekent:
- projectieknopen vergroten het grid niet
- projectielabels vergroten het grid niet
- de bronstructuur bepaalt de gridgrenzen
- extra ruimte voor projectieknopen en projectielabels komt uit **render-/margeruimte**, niet uit gridvergroting

### 6.3 Scheiding tussen grid en rendering
Er wordt onderscheid gemaakt tussen:

#### 1. Structuurgrid
- bedoeld voor de bronstructuur
- bepaalt de vaste gridafmetingen

#### 2. Renderruimte
- extra tekenruimte buiten het grid
- bedoeld voor projectieknopen en projectielabels
- mag automatisch meegroeien zonder wijziging van het grid zelf

### 6.4 Huidige projecties in layout
#### LEX
- standaardrichting: **links**
- grijpt aan op: **eindknopen**
- labelbron: label van de bronknoop
- huidige transfermodus: **copy**

#### LOG
- standaardrichting: **onder**
- grijpt aan op: **vertakkende knopen / P-knooppunten**
- labelbron: label van de bronknoop
- huidige transfermodus: **copy**

#### SYN
- later uit te werken
- labelinhoud wordt **berekend**
- labelbron: **namen van de vertakkende knopen**

### 6.5 Richtingen
Voor alle projecties moet configuratie mogelijk zijn voor vier richtingen:
- links
- rechts
- boven
- onder

### 6.6 Labelregels per richting
#### Links
- label staat **outwards**
- het label sluit aan op de **linker gridrand**
- tekst blijft voorlopig horizontaal

#### Rechts
- label staat **outwards**
- het label sluit aan op de **rechter gridrand**
- tekst blijft voorlopig horizontaal

#### Boven
- label staat **outwards**
- het label sluit aan op de **bovenste gridrand**
- tekstoriëntatie is later configureerbaar

#### Onder
- label staat **outwards**
- het label sluit aan op de **onderste gridrand**
- labeltekst loopt **naar beneden**, dus niet horizontaal

### 6.7 Projectieknooppunten
- projectieknooppunten zijn zichtbaar
- projectieknooppunten mogen buiten het bron-grid liggen
- projectieknooppunten worden via een projectielijn verbonden met hun bronknoop
- projectieknooppunten veranderen de gridgrootte niet

### 6.8 Labeltransfer
Per projectie moet configureerbaar zijn:
- **copy**
- **move**
- later eventueel: **computed**

Huidige default:
- LEX: `copy`
- LOG: `copy`

### 6.9 Reserve voor latere uitbreiding
Later moet ondersteuning mogelijk zijn voor:
- berekende SYN-projectie
- labelmaat-gevoelige render margins
- verschillende offsets per projectie
- verschillende offsets per richting
- eventuele grid-aanpassing als afzonderlijke, expliciet instelbare modus

---

## 7. Concrete app-config keys voor projectielayout

Deze sectie zet de projectieregels om in concrete app-config keys, aansluitend op de bestaande projectiespecificatie en de vastgelegde regels voor projectielayout en gridgedrag.

### 7.1 YAML-config
```yaml
projection_layout:
  grid:
    resize_mode: fixed
    include_source_structure_in_bounds: true
    include_projection_nodes_in_bounds: false
    include_projection_labels_in_bounds: false

  render_margin:
    enabled: true
    mode: auto
    include_projection_nodes: true
    include_projection_labels: true

  projections:
    lex:
      enabled: true
      direction: left
      attach_to: terminal_node
      label_transfer: copy
      label_source: source_node_label

    log:
      enabled: true
      direction: down
      attach_to: branching_node
      label_transfer: copy
      label_source: source_node_label

    syn:
      enabled: false
      direction: right
      attach_to: computed
      label_transfer: computed
      label_source: branching_node_name

  directions:
    left:
      axis: horizontal
      sign: negative
      node_side: outside_grid
      label_anchor: grid_outward
      text_orientation: horizontal

    right:
      axis: horizontal
      sign: positive
      node_side: outside_grid
      label_anchor: grid_outward
      text_orientation: horizontal

    up:
      axis: vertical
      sign: negative
      node_side: outside_grid
      label_anchor: grid_outward
      text_orientation: upward

    down:
      axis: vertical
      sign: positive
      node_side: outside_grid
      label_anchor: grid_outward
      text_orientation: downward

  nodes:
    visible: true
    use_grid_alignment: true
    allow_outside_source_grid: true
    affect_grid_resize: false

  edges:
    draw_projection_edge: true
    projection_edge_style: straight
    projection_edge_direction: source_to_projection

  labels:
    source:
      keep_visible_when_transfer_is_copy: true
      keep_visible_when_transfer_is_move: false

    projection:
      enabled: true
      affect_grid_resize: false
      may_extend_outside_grid: true

    left:
      anchor_rule: touches_left_grid_edge_outward
      alignment_rule: label_box_right_touches_grid

    right:
      anchor_rule: touches_right_grid_edge_outward
      alignment_rule: label_box_left_touches_grid

    up:
      anchor_rule: touches_top_grid_edge_outward
      alignment_rule: label_box_bottom_touches_grid

    down:
      anchor_rule: touches_bottom_grid_edge_outward
      alignment_rule: label_box_top_touches_grid

  spacing:
    projection_offset_in_grid_units: 1
    label_gap_from_projection_node_in_grid_units: 0
    reserve_for_future_label_metrics: true
```

### 7.2 Korte toelichting per blok

#### `projection_layout.grid`
Regelt wat wel en niet meetelt voor de gridgrenzen. De hoofdregel blijft dat het grid niet automatisch meegroeit door projectieknopen of projectielabels.

#### `projection_layout.render_margin`
Regelt extra tekenspatie buiten het grid. Deze ruimte mag meegroeien zonder wijziging van het structuurgrid.

#### `projection_layout.projections`
Bevat projectiespecifieke defaults:
- `lex`: links, op eindknopen, labeltransfer via `copy`
- `log`: onder, op vertakkende knopen, labeltransfer via `copy`
- `syn`: nog niet actief; later berekend vanuit namen van vertakkende knopen

#### `projection_layout.directions`
Legt richtingseigenschappen vast voor alle vier richtingen:
- as
- tekenrichting
- positie buiten het grid
- labelankering
- tekstoriëntatie

#### `projection_layout.nodes`
Regelt zichtbaarheid en plaatsing van projectieknooppunten.

#### `projection_layout.edges`
Regelt de projectielijn tussen bronknoop en projectieknoop.

#### `projection_layout.labels`
Scheidt bronlabels van projectielabels en bevat de per-richting anker- en uitlijnregels.

#### `projection_layout.spacing`
Bevat elementaire afstandsregels en reserve voor latere labelmetriek.

### 7.3 Directe implementatiedoelen
1. Lees `projection_layout.grid.resize_mode` uit vóór elke herberekening van gridgrenzen.
2. Behandel `render_margin` apart van structurele gridberekening.
3. Laat LEX standaard op eindknopen werken en LOG standaard op vertakkende knopen.
4. Laat labelplaatsing per richting verlopen via `label_anchor` en `text_orientation`.
5. Gebruik `label_transfer` als schakelaar tussen `copy`, `move` en later `computed`.

### 7.4 Opmerking voor latere uitbreiding
Bij invoering van SYN hoeft de key-structuur niet te veranderen. Alleen de berekeningslogica achter:
- `attach_to: computed`
- `label_transfer: computed`
- `label_source: branching_node_name`

moet dan worden ingevuld.

---

## 8. Implementatievragen

Nog uit te werken:
- hoe projecties intern worden opgeslagen
- hoe projecties gelabeld worden in UI
- hoe projecties getoond worden links / rechts / onder / boven
- hoe projecties zich gedragen bij undo/redo
- hoe projecties meegaan in export en import
- hoe projecties omgaan met ontbrekende labels
- hoe `render_margin` en `grid.resize_mode` technisch van elkaar gescheiden blijven in layoutberekening
- hoe richting-specifieke tekstoriëntatie exact wordt gemodelleerd, vooral voor **onder** en later **boven**

---

## 9. Beslispunten

Open:
- invulling van **N / noord**
- formele definitie van `eindknoop`
- formele definitie van `catknoop`
- formele definitie van logische categorie
- definitieve lijst SYN-categorieën
- definitieve lijst LOG-categorieën
- exacte semantiek van `move` versus `copy` bij labeltransfer in de UI
- precieze toekomststatus van SYN als berekende projectie

---

## 10. Wijziglog

- **2026-04-14**: eerste vastlegging van W/O/Z/N en deelnameregels voor LEX, SYN en LOG.
- **2026-04-14**: document herwerkt naar vaste specificatiestructuur met kernmapping, regels, terminologie, implementatievragen en beslispunten.
- **2026-04-14**: structuurtype toegevoegd met types D = demo, L = language tree en R = rest, inclusief invoer- en groeiregels in voorlopige vorm.
- **2026-04-14**: onderscheid aangescherpt tussen L2 en R: L2 is altijd een groeiende boom; bij R is nog open of het een boom of andere structuur is, en of die groeiend of getekend is.
- **2026-04-14**: voorlopige subtype-indeling voor R toegevoegd: R1 tree-getekend, R2 tree-groeiend, R3 niet-tree-getekend, R4 niet-tree-groeiend.
- **2026-04-16**: L2-open-punten opgeschoond; dubbele regels verwijderd.
- **2026-04-17**: sectie **Projectielayout en gridgedrag** toegevoegd aan de master-spec; hoofdregel vastgelegd dat projecties het structuurgrid niet vergroten.
- **2026-04-17**: sectie **Concrete app-config keys voor projectielayout** toegevoegd, inclusief YAML-voorstel, richtingregels en implementatiedoelen.
