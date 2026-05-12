# PATCH MANIFEST v4.22.6 — Language Tree user settings button

## Doel

Maak de Language Tree-instellingen voor de gebruiker direct vindbaar in het hoofdscherm.

## Wijzigingen

- Toegevoegd in de OpenGraph-header, alleen zichtbaar bij `Language Tree / Phrase`:

  ```text
  Settings...
  ```

- De knop opent een expliciet user-settings venster:

  ```text
  Language Tree settings
  - V-cluster: PV-VD heeft gebeten / VD-PV gebeten heeft
  - Projecties: toon projecties
  - LEX-as links: aan/uit + caption
  - SYNT-as rechts: aan/uit + caption
  - boven: aan/uit + caption
  - onder: aan/uit + caption
  ```

- `OK` past de instellingen toe en slaat het Language Tree-profiel op in:

  ```text
  config/opengraph_user.properties
  ```

## Gedrag

- De settings-knop tekent niet zelf opnieuw.
- De gekozen instellingen worden gebruikt bij de volgende Language Tree-draw, bijvoorbeeld via `Bijzin`, `Basis`, `Stellend`, enzovoort.
- De bestaande headerknoppen `PV-VD` en `VD-PV` blijven bestaan voor snelle directe redraw.

## Scope

Alleen `Language Tree / Phrase`.
