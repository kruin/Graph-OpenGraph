# PATCH MANIFEST v4.23.1 — compact Language Tree header

## Doel

De OpenGraph-header was te breed voor Language Tree-gebruik.

## Wijzigingen

- De vaste tekst `OpenGraph:` is verwijderd uit de header.
- `Structure type:` is vervangen door `Type:`.
- De structure type-keuze gebruikt korte labels:
  - `LT` = `Language Tree / Phrase`
  - `Anaf.` = `Anafoor`
- De combobox heeft hovertekst waarin `LT` volledig wordt uitgelegd.
- `LT Settings...` is verkort tot `Settings...`.
- `Zinstype:` is verkort tot `Zin:`.
- De zichtbare zinstypeknoppen zijn compacter:
  - `Stellend` → `Stell.`
  - `Topicalisatie` → `Topic`
- `V-cluster:` in de header is verkort tot `V:`.
- Tooltips blijven volledig; de UI-tekst is korter.

## Geen functionele wijziging

- Zinstype-draw blijft hetzelfde.
- V-cluster-toggle blijft hetzelfde.
- Projectieprofielen blijven hetzelfde.
- Structure type-keuze tekent nog steeds niet direct.
