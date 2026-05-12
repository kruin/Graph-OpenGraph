# PATCH MANIFEST — v4.21.3 Language Tree UI source reset

## Scope

Refines the direct Language Tree / Phrase workflow.

## Changes

- `Structure type` is now only a selector; changing it no longer performs Draw/Redraw.
- For `Simple`, the local `Draw` button is shown and `Zinstype` controls are hidden.
- For `Language Tree / Phrase`, `Zinstype` controls are shown and still execute Draw/Redraw directly.
- `Anafoor` is restored as a structure type in the main structure selector.
- `Frame` remains projection-capable and is drawn as an open tree with projected nodes.
- Direct OpenGraph/Zinstype draw reloads the original `.graph` before drawing. If no original `.graph` is available, the user receives an explicit error.
- Language Tree placement effects are now visible as slot-placement labels on the lexical axis, for example `slot1 ← NP(de man)` and `PV → FIN`.

## Tests

- Language Tree: `3 pass, 0 fail`
- Mapping V4: `53 pass, 0 fail`
- Mapping V3: `13 pass, 0 fail`
- MD folder: `PASS`
- `javac`: OK, warnings only
