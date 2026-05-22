# PATCH MANIFEST v4.25.5 — Functional Tree first role-box layout

## Doel

Activeer de eerste echte **FT role-box** plaatsing voor n-ary functionele structuren.

## Functioneel

- `FT - Functional Tree` gebruikt nu een dedicated n-ary role-box combiner wanneer de strategie `role_box` actief is.
- n-ary kinderen worden eerst op rolrank geordend:
  - `pred` rank 0
  - `agens` rank 10
  - `patiens` rank 20
  - `recipiens` rank 30
  - `instrument` rank 40
  - `locatief` rank 50
  - `tijd` rank 60
- Daarna wordt elk kind als complete subtree-box geplaatst.
- De rol bepaalt de voorkeurscorridor:
  - `pred`: center
  - `agens`: left
  - `patiens`: right
  - `recipiens`: right / inner-deeper
  - `instrument`, `locatief`, `tijd`: down stack

## Invariant

```text
role/order first
local subtree box second
whole-box shift third
```

De role-box layout mag rolvolgorde niet herschrijven. Zij verplaatst alleen complete subtree-boxes.

## Testbestand

Toegevoegd:

- `examples/ft-test-geven-jan-boek-marie-gisteren.graph`

Te openen met `FT - Functional Tree`.

## Niet gewijzigd

- LT blijft `projection_box`.
- De drie LT-testgraphs blijven bedoeld ongewijzigd correct.
- Geen GUI-configuratie voor role-ranks.

## Checks

- Java compile OK
- Fresh jar OK
- `java --dry-run -cp out:. OpenGraphEdFrame` OK
- `java --dry-run -jar dist/OpenGraphEd.jar` OK
