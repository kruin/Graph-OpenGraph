# Projectielayout en gridgedrag

## Doel
Deze sectie legt vast hoe projectieknopen en projectielabels worden geplaatst ten opzichte van de bronstructuur en het grid.

## Hoofdregel
Het **grid blijft onveranderd** wanneer projecties worden toegevoegd.

Dat betekent:
- projectieknopen vergroten het grid niet
- projectielabels vergroten het grid niet
- de bronstructuur bepaalt de gridgrenzen
- extra ruimte voor projectieknopen en projectielabels komt uit **render-/margeruimte**, niet uit gridvergroting

## Scheiding tussen grid en rendering
Er wordt onderscheid gemaakt tussen:

### 1. Structuurgrid
- bedoeld voor de bronstructuur
- bepaalt de vaste gridafmetingen

### 2. Renderruimte
- extra tekenruimte buiten het grid
- bedoeld voor projectieknopen en projectielabels
- mag automatisch meegroeien zonder wijziging van het grid zelf

## Huidige projecties

### LEX
- standaardrichting: **links**
- grijpt aan op: **eindknopen**
- labelbron: label van de bronknoop
- huidige transfermodus: **copy**

### LOG
- standaardrichting: **onder**
- grijpt aan op: **vertakkende knopen / P-knooppunten**
- labelbron: label van de bronknoop
- huidige transfermodus: **copy**

### SYN
- later uit te werken
- labelinhoud wordt **berekend**
- labelbron: **namen van de vertakkende knopen**

## Richtingen
Voor alle projecties moet configuratie mogelijk zijn voor vier richtingen:
- links
- rechts
- boven
- onder

## Labelregels per richting

### Links
- label staat **outwards**
- het label sluit aan op de **linker gridrand**
- tekst blijft voorlopig horizontaal

### Rechts
- label staat **outwards**
- het label sluit aan op de **rechter gridrand**
- tekst blijft voorlopig horizontaal

### Boven
- label staat **outwards**
- het label sluit aan op de **bovenste gridrand**
- tekstoriëntatie is later configureerbaar

### Onder
- label staat **outwards**
- het label sluit aan op de **onderste gridrand**
- labeltekst loopt **naar beneden**, dus niet horizontaal

## Projectieknooppunten
- projectieknooppunten zijn zichtbaar
- projectieknooppunten mogen buiten het bron-grid liggen
- projectieknooppunten worden via een projectielijn verbonden met hun bronknoop
- projectieknooppunten veranderen de gridgrootte niet

## Labeltransfer
Per projectie moet configureerbaar zijn:
- **copy**
- **move**
- later eventueel: **computed**

Huidige default:
- LEX: `copy`
- LOG: `copy`

## Reserve voor latere uitbreiding
Later moet ondersteuning mogelijk zijn voor:
- berekende SYN-projectie
- labelmaat-gevoelige render margins
- verschillende offsets per projectie
- verschillende offsets per richting
- eventuele grid-aanpassing als afzonderlijke, expliciet instelbare modus
