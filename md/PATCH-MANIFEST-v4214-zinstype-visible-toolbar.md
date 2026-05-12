# PATCH MANIFEST — v4.21.4 Zinstype visible toolbar

## Doel

Corrigeert dat `Zinstype` niet zichtbaar leek/was in de OpenGraph-balk bij Language Tree / Phrase.

## Wijzigingen

- `Zinstype` staat direct naast `Structure type`, vóór `Draw`, `Grid`, `Toggle Proj.` en `Save OPN`.
- Bij `Language Tree / Phrase` is `Zinstype` zichtbaar en `Draw` verborgen.
- Bij `Simple`, `Anafoor` en `Frame` is `Draw` zichtbaar en `Zinstype` verborgen.
- Bij openen van een `.graph` met Language Tree-hint wordt de header direct opnieuw gesynchroniseerd.

## Bestanden

- `userInterface/GraphEditorWindow.java`
- `userInterface/OpenGraphEdAppInfo.java`
- manifest/build/version metadata
