# projection-layout-implementation-plan

## Doel

Dit document zet de vastgelegde projectiespecificatie om in een concreet technisch implementatieplan voor OpenGraphEd.

Het plan dekt:

- classlijst
- Java-skeletten
- opslagvoorstel voor `.graph` / `.ots`
- implementatiefases 1 t/m 6

Dit document sluit aan op:
- `projectie-master-spec.md`
- de secties over **Projectielayout en gridgedrag**
- de sectie **Concrete app-config keys voor projectielayout**

---

## 1. Ontwerpdoelen

### 1.1 Functionele doelen
- LEX-projecties kunnen genereren
- LOG-projecties kunnen genereren
- later SYN-projecties kunnen toevoegen
- projectieknooppunten zichtbaar tonen
- projectielabels richting-specifiek tonen
- projecties kunnen opslaan en opnieuw laden

### 1.2 Layoutdoelen
- het structuurgrid blijft vast
- projectieknopen vergroten het structuurgrid niet
- projectielabels vergroten het structuurgrid niet
- projecties gebruiken aparte render-/margeruimte
- per richting moet labelplaatsing configureerbaar zijn

### 1.3 Technische doelen
- projecties moeten undo/redo ondersteunen
- projecties moeten export/import ondersteunen
- projecties moeten later uitbreidbaar zijn naar SYN
- model, layout en rendering moeten gescheiden blijven

---

## 2. Architectuur in drie lagen

### 2.1 Structuurlaag
Bevat de feitelijke projectie-entiteiten in het graphmodel:
- projectieknoop
- projectielink
- projectietype
- bronknoop

### 2.2 Layoutlaag
Berekeningslaag:
- selecteert projectiebronnen
- bepaalt richting en offset
- bepaalt positie van projectieknooppunten
- bepaalt positie van projectielabels
- berekent render bounds zonder structuurgrid te wijzigen

### 2.3 Weergavelaag
Tekenlaag:
- tekent projectielijnen
- tekent projectieknooppunten
- tekent projectielabels
- respecteert tekstoriëntatie en labelanker

---

## 3. Classlijst

### 3.1 Enumtypes

```java
public enum ProjectionType {
    LEX, LOG, SYN, PM
}

public enum ProjectionDirection {
    LEFT, RIGHT, UP, DOWN
}

public enum LabelTransferMode {
    COPY, MOVE, COMPUTED
}

public enum ProjectionAttachRule {
    TERMINAL_NODE,
    BRANCHING_NODE,
    COMPUTED
}

public enum LabelAnchor {
    GRID_OUTWARD
}

public enum TextOrientation {
    HORIZONTAL,
    UPWARD,
    DOWNWARD
}

public enum GridResizeMode {
    FIXED,
    EXPAND_FOR_PROJECTIONS
}
```

### 3.2 Config- en specsclasses
- `ProjectionLayoutConfig`
- `ProjectionSpec`
- `DirectionSpec`

### 3.3 Modelclasses
- `ProjectionLink`
- `ProjectionNodeData`
- `ProjectedLabel`
- `ProjectionLayoutResult`

### 3.4 Selectie- en layoutclasses
- `ProjectionSourceSelector`
- `LexSourceSelector`
- `LogSourceSelector`
- `SynSourceSelector`
- `ProjectionLayoutEngine`

### 3.5 Opslagclasses
- `ProjectionFileCodec`
- of uitbreiding van bestaande graph file read/write classes

### 3.6 Undo/redo
- `AddProjectionMemento`
- `RemoveProjectionMemento`
- `RebuildProjectionMemento`
- `UpdateProjectionConfigMemento`

---

## 4. Java-skeletten

### 4.1 ProjectionLayoutConfig

```java
public final class ProjectionLayoutConfig {
    public GridResizeMode gridResizeMode = GridResizeMode.FIXED;

    public boolean includeSourceStructureInBounds = true;
    public boolean includeProjectionNodesInBounds = false;
    public boolean includeProjectionLabelsInBounds = false;

    public boolean renderMarginEnabled = true;
    public boolean renderMarginIncludeProjectionNodes = true;
    public boolean renderMarginIncludeProjectionLabels = true;

    public ProjectionSpec lex = new ProjectionSpec();
    public ProjectionSpec log = new ProjectionSpec();
    public ProjectionSpec syn = new ProjectionSpec();

    public DirectionSpec left = new DirectionSpec();
    public DirectionSpec right = new DirectionSpec();
    public DirectionSpec up = new DirectionSpec();
    public DirectionSpec down = new DirectionSpec();

    public int projectionOffsetInGridUnits = 1;
    public int labelGapFromProjectionNodeInGridUnits = 0;
}
```

### 4.2 ProjectionSpec

```java
public final class ProjectionSpec {
    public boolean enabled;
    public ProjectionDirection direction;
    public ProjectionAttachRule attachTo;
    public LabelTransferMode labelTransfer;
    public String labelSource;
}
```

### 4.3 DirectionSpec

```java
public final class DirectionSpec {
    public LabelAnchor labelAnchor = LabelAnchor.GRID_OUTWARD;
    public TextOrientation textOrientation = TextOrientation.HORIZONTAL;
    public boolean nodeOutsideGrid = true;
}
```

### 4.4 ProjectionLink

```java
public final class ProjectionLink {
    public Node sourceNode;
    public Node projectionNode;
    public ProjectionType projectionType;
}
```

### 4.5 ProjectionNodeData

```java
public final class ProjectionNodeData {
    public ProjectionType projectionType;
    public Node sourceNode;
    public ProjectionDirection direction;
    public String projectedLabel;
    public boolean generated = true;
}
```

### 4.6 ProjectedLabel

```java
public final class ProjectedLabel {
    public String text;
    public LabelTransferMode mode;
    public String sourceKind;
}
```

### 4.7 ProjectionLayoutResult

```java
public final class ProjectionLayoutResult {
    public Rectangle sourceBounds;
    public Rectangle renderBounds;
}
```

### 4.8 ProjectionSourceSelector

```java
public interface ProjectionSourceSelector {
    boolean matches(Node node, Graph graph);
}
```

### 4.9 LexSourceSelector

```java
public final class LexSourceSelector implements ProjectionSourceSelector {
    @Override
    public boolean matches(Node node, Graph graph) {
        return node != null && node.getDegree() <= 1;
    }
}
```

### 4.10 LogSourceSelector

```java
public final class LogSourceSelector implements ProjectionSourceSelector {
    @Override
    public boolean matches(Node node, Graph graph) {
        return node != null && node.getDegree() >= 2;
    }
}
```

### 4.11 SynSourceSelector

```java
public final class SynSourceSelector implements ProjectionSourceSelector {
    @Override
    public boolean matches(Node node, Graph graph) {
        return false; // later ingevuld via berekende projectieregel
    }
}
```

### 4.12 ProjectionLayoutEngine

```java
public final class ProjectionLayoutEngine {

    public ProjectionLayoutResult computeLayout(Graph graph, ProjectionLayoutConfig config) {
        ProjectionLayoutResult result = new ProjectionLayoutResult();
        result.sourceBounds = computeSourceBounds(graph, config);
        result.renderBounds = computeRenderBounds(graph, config, result.sourceBounds);
        return result;
    }

    protected Rectangle computeSourceBounds(Graph graph, ProjectionLayoutConfig config) {
        return new Rectangle();
    }

    protected Rectangle computeRenderBounds(
            Graph graph,
            ProjectionLayoutConfig config,
            Rectangle sourceBounds) {
        return new Rectangle(sourceBounds);
    }
}
```

---

## 5. Uitbreidingen aan Graph

### 5.1 Nieuwe velden in Graph
Voorstel:

```java
private ProjectionLayoutConfig projectionLayoutConfig;
private List<ProjectionLink> projectionLinks;
```

### 5.2 Nieuwe methods in Graph

```java
public ProjectionLayoutConfig getProjectionLayoutConfig();
public void setProjectionLayoutConfig(ProjectionLayoutConfig config);

public List<ProjectionLink> getProjectionLinks();

public void clearProjections();
public void rebuildProjections();
public void buildProjection(ProjectionType type);

public List<Node> getProjectionSources(ProjectionType type);
```

### 5.3 Doel van echte modelobjecten
Projecties moeten niet alleen visueel bestaan, maar ook:
- selecteerbaar zijn
- opslaan/laadbaar zijn
- undo/redo ondersteunen
- herberekend kunnen worden
- later per projectietype beheerd kunnen worden

---

## 6. Selectieregels per projectie

### 6.1 LEX
Huidige regel:
- aangrijpen op eindknopen

Voorlopige technische interpretatie:
- knoop met graad `<= 1`

Opmerking:
- later verfijnen als de formele definitie van `eindknoop` preciezer wordt

### 6.2 LOG
Huidige regel:
- aangrijpen op vertakkende knopen / P-knooppunten

Voorlopige technische interpretatie:
- knoop met graad `>= 2`

Opmerking:
- later verfijnen als phrase-/vertakkingsknoop semantisch anders wordt gedefinieerd

### 6.3 SYN
Nog niet actief.
Wel nu al API klaarzetten voor:
- berekende selectie
- berekend label
- aparte attach-regel

---

## 7. Labeltransfer

### 7.1 COPY
- bronlabel blijft zichtbaar op de bronknoop
- projectieknoop krijgt een kopie van de labeltekst

### 7.2 MOVE
- bronlabel wordt niet meer bij de bron getoond
- label wordt getoond bij de projectieknoop

### 7.3 COMPUTED
- label ontstaat uit berekeningslogica
- nodig voor latere SYN-projectie

### 7.4 Reden om dit expliciet te modelleren
`copy` en `move` zijn geen puur visuele varianten, maar semantische modi die effect hebben op:
- rendering
- opslag
- undo/redo
- toekomstig UI-gedrag

---

## 8. Layoutregels

### 8.1 Structuurbounds versus renderbounds
De layoutlaag moet twee grenzen onderscheiden:

#### sourceBounds
Bevat alleen:
- bronstructuur
- bestaande structurele bounds

#### renderBounds
Bevat:
- bronstructuur
- projectieknopen
- projectielabels
- extra render margins

### 8.2 Hoofdregel
Niet:
```java
graphBounds = everythingVisible;
```

Wel:
```java
sourceBounds = computeSourceBounds();
renderBounds = expandForProjectionRendering(sourceBounds);
```

### 8.3 Richtingsregels

#### LEFT
- projectieknoop links van bronknoop
- label sluit outwards aan op linker gridrand
- tekst voorlopig horizontaal

#### RIGHT
- projectieknoop rechts van bronknoop
- label sluit outwards aan op rechter gridrand
- tekst voorlopig horizontaal

#### UP
- projectieknoop boven bronknoop
- label sluit outwards aan op bovenste gridrand
- tekstoriëntatie later verfijnbaar

#### DOWN
- projectieknoop onder bronknoop
- label sluit outwards aan op onderste gridrand
- labeltekst loopt naar beneden

---

## 9. Rendering

### 9.1 Nieuwe renderfasen
Voorstel binnen graph draw pipeline:

1. bronstructuur tekenen
2. projectielijnen tekenen
3. projectieknopen tekenen
4. projectielabels tekenen

### 9.2 Waarom aparte labelfase
Omdat projectielabels:
- een andere tekstoriëntatie kunnen hebben
- buiten het structuurgrid kunnen vallen
- niet mogen terugwerken op de gridberekening

---

## 10. Opslagvoorstel voor `.graph` / `.ots`

### 10.1 Ontwerpeis
Projecties mogen niet alleen impliciet bestaan via:
- gewone labels
- generated edges
- toevallige extra knopen

Projectie-informatie moet expliciet opslaan:
- type
- bronknoop
- projectieknoop
- richting
- transfermodus
- labeltekst

### 10.2 Graph-level configsectie
Voorstel:

```text
<- Projection Layout Config Present ->
<- Grid Resize Mode ->
<- Include Source Structure In Bounds ->
<- Include Projection Nodes In Bounds ->
<- Include Projection Labels In Bounds ->
<- Render Margin Enabled ->
<- Render Margin Include Projection Nodes ->
<- Render Margin Include Projection Labels ->
<- Projection Offset In Grid Units ->
<- Label Gap From Projection Node In Grid Units ->
```

### 10.3 Projection entries
Voorstel:

```text
<- Number Of Projections ->
<- Projection Index ->
<- Projection Type ->
<- Source Node Index ->
<- Projection Node Index ->
<- Direction ->
<- Label Transfer Mode ->
<- Attach Rule ->
<- Projected Label Text ->
```

### 10.4 Waarom expliciete projectiesectie
Voordelen:
- helder formaat
- geen misbruik van gewone edge/node-semantiek
- export/import blijft stabiel
- latere SYN-uitbreiding blijft schoon

---

## 11. Undo/redo

### 11.1 Nieuwe mementos
Voorstel:
- `AddProjectionMemento`
- `RemoveProjectionMemento`
- `RebuildProjectionMemento`
- `UpdateProjectionConfigMemento`

### 11.2 Minimale verantwoordelijkheden
- projectieknoop toevoegen/verwijderen
- projectielink toevoegen/verwijderen
- configwijziging herstelbaar maken
- rebuild als één logische operatie kunnen terugdraaien

### 11.3 Reden
Projecties zijn geen tijdelijk tekeneffect, maar modelwijzigingen. Daarom moeten ze op hetzelfde niveau behandeld worden als andere graphbewerkingen.

---

## 12. Implementatiefases 1 t/m 6

### Fase 1 — Config en enums
Doel:
- de basisstructuur compilabel maken

Taken:
- enumtypes toevoegen
- `ProjectionLayoutConfig`
- `ProjectionSpec`
- `DirectionSpec`

Resultaat:
- vaste representatie van projectie-instellingen

### Fase 2 — Selectie van bronknopen
Doel:
- LEX- en LOG-bronnen automatisch kunnen bepalen

Taken:
- `ProjectionSourceSelector`
- `LexSourceSelector`
- `LogSourceSelector`
- `SynSourceSelector` placeholder

Resultaat:
- lijst van bronknopen per projectietype

### Fase 3 — Modelobjecten en rebuild
Doel:
- projecties als echte objecten in het graphmodel opnemen

Taken:
- `ProjectionLink`
- `ProjectionNodeData`
- `ProjectedLabel`
- graphvelden en graphmethods
- `clearProjections()`
- `rebuildProjections()`
- `buildProjection(ProjectionType type)`

Resultaat:
- projecties bestaan echt in het model

### Fase 4 — Layout en rendering
Doel:
- projecties zichtbaar maken zonder gridvergroting

Taken:
- `ProjectionLayoutEngine`
- `sourceBounds` / `renderBounds`
- projectielijnen tekenen
- projectieknopen tekenen
- labelplaatsing links en onder
- outwards anchor
- downward text

Resultaat:
- eerste bruikbare visuele projecties

### Fase 5 — Opslag en undo/redo
Doel:
- projecties persistent en herstelbaar maken

Taken:
- fileformaat uitbreiden
- lezen en schrijven van config + projecties
- mementos toevoegen
- rebuild als undo/redo-eenheid modelleren

Resultaat:
- projecties blijven bestaan na opslaan/laden
- projecties zijn editorwaardig

### Fase 6 — SYN-voorbereiding en verfijning
Doel:
- architectuur klaarzetten voor berekende projecties

Taken:
- computed attach rule verder invullen
- computed label source
- richting-specifieke verfijning boven/rechts
- latere UI-koppeling

Resultaat:
- architectuur klaar voor SYN

---

## 13. Aanbevolen bouwvolgorde

1. Fase 1
2. Fase 2
3. Fase 3
4. Fase 4
5. Fase 5
6. Fase 6

Belangrijk:
- niet eerst UI
- niet eerst losse labelrendering
- eerst model en opslag stabiel maken

---

## 14. Eerste concrete ontwikkeltaak

De eerstvolgende praktische ontwikkelstap is:

### Stap A
Voeg de enumtypes en configclasses toe.

### Stap B
Voeg aan `Graph` de velden en methods toe voor:
- `ProjectionLayoutConfig`
- `List<ProjectionLink>`
- `clearProjections()`
- `rebuildProjections()`

### Stap C
Implementeer voorlopig:
- `LexSourceSelector`
- `LogSourceSelector`

### Stap D
Laat `rebuildProjections()` eerst alleen:
- bestaande projecties wissen
- LEX-projecties opbouwen
- projectieknopen links plaatsen op vaste grid-offset

Dat geeft snel een eerste testbare verticale doorsnede.

---

## 15. Besluit

De technisch juiste route is nu:

- **eerst model**
- dan **layout**
- dan **rendering**
- dan **opslag**
- pas daarna verdere UI-verfijning

Zo blijft de implementatie in lijn met:
- de bestaande JGraphEd/OpenGraphEd-architectuur
- de nieuwe master-spec
- de eis dat projecties niet het structuurgrid vergroten
