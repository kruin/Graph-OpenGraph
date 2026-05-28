# projectie-master-spec.md

## Status

- Project/chatreeks: **Mapping V4 / OpenGraphEd / Projectie**
- Actuele correctieversie: **v4299**
- Doel van deze versie: herstel van ontbrekende `projectie-master-spec.md` in de projectzip.
- Gedragswijziging in Java: **geen**.

Deze file is de centrale duurzame project-spec. Toekomstige projectzips horen deze file mee te leveren.

## 1. Projectdoel

OpenGraphEd ontwikkelt een notatie- en tekenomgeving waarin een taalkundige structuur niet wordt gereduceerd tot één traditionele boomtekening. De centrale gedachte is:

```text
vrije OpenGraph-bronknopen + projectieruimte = ruimte voor LEX, SYN, LF en latere projecties
```

De editor bewaart en toont een graph als bronnotatie. Tree-weergaven zijn afgeleide presentaties of didactische lagen op basis van die bron.

## 2. Belangrijke naamconventies

- GitHub-account/owner blijft: `kruin`.
- Interne Engelstalige projectnaam: **OpenGraph** / **OpenGraphEd**.
- Voor downloadpakketten geldt: gebruik de chatnaam of afgesproken downloadnaam met datum/tijd.
- Voor deze reeks is de werknaam: **Mapping V4**.

## 3. Tree-menu

De gewenste volgorde in het Tree-menu is:

```text
origineel - OpenGraph - Simple - Language - Functional - Frame - Anaphor
```

### 3.1 origineel

`origineel` toont de actuele editorboom in vrije OpenGraph-notatie.

Belangrijk: dit betekent **niet** automatisch: opnieuw laden vanaf het opgeslagen `.graph`-bestand. Als de gebruiker in de editor iets wijzigt, moet `origineel` die aangepaste versie tonen. De echte terugkeer naar een eerdere toestand gebeurt via de bestaande Back/Undo-functionaliteit.

### 3.2 OpenGraph

`OpenGraph` is een didactische laag vóór `Simple`. Deze laag toont een carrousel met screenshots en toelichtende teksten.

Doel van de carrousel:

1. eenvoudige vertakkingen tonen, eerst één level, daarna varianten;
2. projectie naar links tonen zonder projectienaam: een kale lijn met kopieën van knopen;
3. daarna stapsgewijs de projectieruimte uitbreiden;
4. de taalkundige/student laten zien waarom vrije knopen nuttig zijn.

### 3.3 Simple

`Simple` is de basiscontrole en basisuitleg van de boomlaag.

`Simple` legt voor de taalkundige/student uit waarom OpenGraphEd afwijkt van de traditionele boomtekening. Uitgangspunt:

- In traditionele syntactische bomen staan woorden vaak onderaan.
- Dat roept de vraag op: wat doen die woorden daar onderaan? Is dat een soort zwaartekracht?
- Nee: “onder” is een tekenconventie.
- De traditionele boom reconstrueert de uiting/woordvolgorde descriptief via vertakkingen en eventueel transformaties.

In de oude boomnotatie is dit structureel verschillend:

```text
S → NP VP
S → VP NP
```

De volgorde zit dus in de lokale vertakking ingebakken. In OpenGraphEd hoeft de centrale boom niet alle woordvolgorde te dragen. De LEX-projectie kan, als taklengte en projectieruimte goed verdeeld zijn, dezelfde leesvolgorde tonen als de klassieke boom, zonder dat de woorden letterlijk “onderaan” hoeven te vallen.

Daarmee worden LEX-regels een alternatief voor transformaties: niet de boom hoeft herschreven te worden, maar de lexicale projectie bepaalt de uitingsvolgorde.

### 3.4 Language

`Language` gebruikt de OpenGraph/Simple-basis voor een taalboom van het Nederlands.

Belangrijke projecties:

- **LEX**: lexicale projectie / uitingsvolgorde;
- **SYN**: syntactische categorieprojectie;
- **LF**: logical form / logische projectie.

Language werkt vanuit het principe:

```text
geen transformaties; projectie- en plaatsingsregels bepalen de zichtbare uitingsvolgorde
```

Voor Nederlands zijn onder meer relevant:

- bijzinvolgorde;
- stellende hoofdzin;
- V2 / persoonsvormpositie;
- WH-vraag;
- topicalisatie;
- V-cluster-varianten zoals `pv-VD` en `VD-pv`.

### 3.5 Functional, Frame, Anaphor

Deze onderdelen blijven voorlopig gereserveerd.

Werkafspraak:

- inhoudelijke uitwerking komt later;
- de uitleg verwijst voorlopig terug naar `Simple` en `OpenGraph`;
- latere uitbreiding mag dezelfde vrije OpenGraph-bron gebruiken en eigen projecties toevoegen.

## 4. Boomgeldigheid

Voor Tree-weergaven geldt de huidige controle:

```text
0 kindtakken  = toegestaan als eindknoop
1 kindtak     = fout
2+ kindtakken = toegestaan
```

Richting is irrelevant. De fout is dus niet “links onder rechts” of “zelfde schuine richting”, maar precies:

```text
een interne knoop mag niet precies één kindtak hebben
```

## 5. Branches-config

Alle Tree-types gebruiken dezelfde branch-configuratie per categoriale knoop:

```text
auto
2
3
4
many
```

Betekenis:

- `2`: binaire vertakking;
- `3`, `4`, `many`: n-air vertakking;
- `auto`: renderer kiest volgens de beschikbare structuur/config.

`Simple` mag dus niet meer hardcoded als uitsluitend binair worden behandeld. Binair gedrag hoort uit `Branches = 2` te komen.

## 6. Compacte layout

Tree-layout moet compact zijn:

- geen overbodige lege horizontale gridlijnen;
- geen overbodige lege verticale gridlijnen;
- recursieve box-aanpak blijft het uitgangspunt;
- elke subboom krijgt een denkbeeldige box;
- kindboxen worden compact geplaatst;
- de ouder komt erboven;
- projecties worden afgeleid uit de uiteindelijk geplaatste knopen.

## 7. Draw en editorstatus

`Draw` moet de actuele graph in de editor gebruiken.

Regel:

```text
editorwijziging → Draw ziet die wijziging
```

Draw mag dus niet stil terugvallen op de opgeslagen `.graph` van schijf. Alleen expliciete import/herlaad-acties mogen een bestand opnieuw laden.

## 8. OpenGraph-carrousel

De carrousel staat lokaal in:

```text
opengraph_carousel/
```

Bestanden worden genummerd:

```text
001-...
002-...
003-...
```

Elke afbeelding heeft een bijbehorend `.txt`-bestand met dezelfde basisnaam.

### 8.1 Functionaliteit

De carrousel ondersteunt:

- navigatie: Eerste, Vorige, Volgende, Laatste, Naar...;
- beheer: Screenshot toevoegen, Verwijder, Tekst opslaan, Ververs;
- volgorde: Plaats <, Plaats >, Verplaats..., Wissel...;
- Import carousel;
- Export carousel;
- Reminder.

Bij verplaatsen/wisselen moet de carrouselmap opnieuw opgebouwd worden, zodat oude posities geen residuen achterlaten.

### 8.2 Workflow-reminder

Na carrouselwijzigingen:

1. Controleer de carrousel in de app.
2. Gebruik **Export carousel**.
3. Upload die exportzip hier mee bij het volgende verzoek.
4. Dan kan de volgende projectzip de actuele carrouselstand meenemen.
5. De git-update blijft handwerk.

Voor git blijft de gebruiker zelf uitvoeren, bijvoorbeeld:

```bat
git status
git add .
git commit -m "Update OpenGraph carousel"
git push
```

## 9. Sources en md-bestanden

Aanbevolen voor projectbronnen/Sources:

- `projectie-master-spec.md`;
- compacte projectsource-notities zoals `Mapping_V4-26-05-23--v4298-projectsource-note.md`;
- inhoudelijke `.md`-bestanden onder `md/sources/`.

Niet aanbevolen als vaste Source:

- volledige projectzips;
- oude build-zips;
- gegenereerde `.class`, `out`, `dist`, `.jar`.

## 10. Packaging-regel voor volgende zips

Elke volgende projectzip in deze reeks moet bevatten:

```text
projectie-master-spec.md
md/projectie-master-spec.md
md-only-sources-vXXXX.zip
chatlog/CHANGELOG-vXXXX.md
```

De md-only zip moet alle relevante Markdown-bronnen bevatten, inclusief deze master-spec.

## 11. Huidige correctie v4299

v4298 miste `projectie-master-spec.md`. Dat was fout.

v4299 corrigeert dit door de master-spec toe te voegen op twee plaatsen:

```text
projectie-master-spec.md
md/projectie-master-spec.md
```

Daarnaast bevat v4299 een vernieuwde md-only sources zip waarin deze file ook is opgenomen.
