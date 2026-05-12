# 2026-04-27 — Mapping V3.1 ranking en alternatieven

Scope:
- Alleen mapping/generator/validator-laag.
- Geen UI-wijzigingen.
- Geen graph rendering-wijzigingen.

Wijzigingen:
- V3-alternatieven worden nu als expliciete kandidaten opgebouwd en daarna stabiel gerangschikt.
- Alternatieven worden gededupliceerd op gegenereerde uiting.
- Info-output blijft begrensd tot maximaal 3 alternatieven.
- Niet-oplosbare ordering-constraints leveren geen fallback-volgorde meer op; zulke kandidaten vallen weg.
- Enkelvoudige rank-varianten en beperkte paren van rank-varianten worden geprobeerd, zodat meerdere argumenten beter zichtbaar worden zonder explosie.
- `before_clause` en `after_clause` worden als placement-relaties ondersteund naast de bestaande `realizes_before` / `realizes_after` regels.

Aangeraakt:
- `userInterface/GraphFileActions.java`

Compile:
- `javac @sources.txt` OK
- 1 bestaande deprecation warning: `new Integer(x)` in `GraphFileActions.java`.

Controle:
- `examples/opn/mapping-v3-neg/voorbeeldzin-met-bijwoorden.lexical-axis-view.mapping-v3-neg-after-aux.opn`
  - best: `gisteren vrouw heeft niet trui gebreid daar`
  - alternatieven: maximaal 3, uniek en stabiel gerangschikt.
