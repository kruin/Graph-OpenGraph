# PATCH MANIFEST v4.22.7 — Language Tree settings button placement

## Wijziging

De Language Tree settingsknop is verplaatst naar een vaste zichtbare positie direct naast:

```text
Structure type: [Language Tree / Phrase] [LT Settings...]
```

De knop staat nu vóór de brede `Zinstype`, `V-cluster` en projectieprofielgroepen, zodat hij op smallere schermen niet achter andere header-items verdwijnt.

## Gedrag

- Alleen zichtbaar bij `Structure type = Language Tree / Phrase`.
- Opent `Language Tree settings` voor V-cluster en projectieprofiel.
- Structure type blijft alleen keuze; settings klikken tekent niet automatisch.
