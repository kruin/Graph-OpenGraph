# PATCH MANIFEST v4.25.4 — Functional Tree type skeleton

## Doel

Voeg **FT = Functional Tree** toe als apart OpenGraph type naast Simple, LT, Anafoor en Frame.

## Functioneel

- Nieuw structure type:
  - `OpenGraphDialog.STRUCTURE_FUNCTIONAL_TREE = 5`
- UI:
  - OpenGraphDraw dialoog: nieuwe keuze `FT - Functional Tree`
  - Header type-combo: nieuwe keuze `FT`
- FT gebruikt een eigen projectieprofiel:
  - left: `LEX`
  - right: `ROLE`
  - top: `FT`
  - bottom: `SEM` default uit
- FT activeert intern:
  - `projection.profile.functional.layout.strategy=role_box`
  - `functional.layout.strategy=role_box`
- LT blijft standaard:
  - `projection_box`

## Niet gedaan

- Geen volledige FG-layout-afwerking.
- Geen nieuwe gebruikersconfiguratie voor role-ranks in de GUI.
- Geen wijziging bedoeld voor bestaande LT-testgraphs.

## Invariant

```text
LT = projectiegestuurde taalboom
FT = rolgestuurde functionele boom
```

Projecties blijven doel; de box-layout bepaalt alleen vrije plaatsing.

## Checks

- Java compile OK
- Fresh jar OK
- `java --dry-run -cp out:. OpenGraphEdFrame` OK
- `java --dry-run -jar dist/OpenGraphEd.jar` OK
