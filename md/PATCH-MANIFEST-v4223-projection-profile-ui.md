# PATCH MANIFEST v4.22.3 — Projection profiles in UI

## Doel

De projectieconfiguratie per structure type is niet alleen meer een `.properties`-configuratie, maar is ook direct zichtbaar en wijzigbaar in het hoofdscherm.

## UI

In de OpenGraph-header staat bij projectie-capable structure types een nieuwe groep:

```text
Projecties: [aan] [L] LEX [R] SYNT [Boven] pm [Onder] LF [Save profile]
```

De groep is zichtbaar voor:

- Language Tree / Phrase
- Frame
- Anafoor

De groep is verborgen voor:

- Simple

## Werking

- `aan` toont/verbergt projecties.
- `L`, `R`, `Boven`, `Onder` schakelen de projectiezijde aan/uit.
- De tekstvelden wijzigen de captions.
- `Save profile` schrijft de instellingen weg naar `config/opengraph_user.properties` onder het actieve structure-typeprofiel.

## Configkeys

Voorbeeld Language Tree:

```properties
projection.profile.language.show=true
projection.profile.language.left.enabled=true
projection.profile.language.left.caption=LEX
projection.profile.language.right.enabled=true
projection.profile.language.right.caption=SYNT
projection.profile.language.top.enabled=true
projection.profile.language.top.caption=pm
projection.profile.language.bottom.enabled=true
projection.profile.language.bottom.caption=LF
```

Frame en Anafoor gebruiken hun eigen `projection.profile.frame.*` en `projection.profile.anaphor.*` keys.

## Aangepaste bestanden

- `userInterface/GraphEditorWindow.java`
- `md/CHANGELOG.md`
- `md/INDEX.md`
- `md/sources/mapping-v4/opengraph-projection-profiles.md`
