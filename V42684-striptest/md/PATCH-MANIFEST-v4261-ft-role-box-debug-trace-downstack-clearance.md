# PATCH MANIFEST v4.26.1 — FT role-box debug trace + down-stack clearance

## Doel

Functional Tree (`FT`) gebruikt `role_box`. v4.26.0 corrigeerde de exacte verticale spine, maar `pred` en de eerste down-stack rollen konden nog te dicht bij dezelfde diagonale zone liggen. Deze patch maakt de FT role-box traceerbaar en vergroot de diagonale vrijheidsruimte voor down-stack rollen.

## Wijzigingen

- Extra FT role-box debug trace toegevoegd.
- Debug trace is tijdelijk aangezet via user config:
  - `functional.layout.debug=true`
- Trace toont per FT draw:
  - root
  - role count
  - label
  - herkende rol
  - rank
  - side
  - corridor
  - lokale offset
  - finale offset
  - aantal collision-shift attempts
  - subtree-box
  - finale root-subtree-box
- `pred/center` gebruikt nu offset `+2` in plaats van `+1`.
- down-stack gebruikt ruimere offsets:
  - `+4, +5, -5, -6, ...`
- Configvoorbereiding toegevoegd:
  - `functional.layout.downStack.minClearanceX=4`
  - `functional.layout.downStack.startBelowFullBox=true`

## Verwacht effect

Voor `ft-test-04-locatief-tijd`:

- geen verticale kolom `CLAUSE -> pred -> locatief`;
- `locatief` en `tijd` blijven down-stack, maar liggen verder uit de predicate-spine;
- console toont nu genoeg gegevens voor verdere analyse.

## Niet gewijzigd

- `LT` blijft `projection_box`.
- `FT` blijft `role_box`.
- Geen GUI-instelling toegevoegd.

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK
