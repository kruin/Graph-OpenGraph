# PATCH MANIFEST v4.26.2 — FT role-box edge-cone clearance

## Doel

v4.26.1 liet zien dat de FT-nodes zelf niet botsten, maar dat de directe edges vanuit `CLAUSE` nog in dezelfde visuele cone vielen:

```text
CLAUSE -> pred
CLAUSE -> locatief
CLAUSE -> tijd
```

De fout zat dus niet in node-box collision, maar in edge-corridor/corner clearance.

## Wijziging

- `functional.layout.downStack.minClearanceX` verhoogd van `4` naar `8`.
- Nieuwe property:

```properties
functional.layout.downStack.edgeConeClearance=3
```

- `roleBoxDownOffset()` gebruikt nu een echte adjunct-lane:

```text
ordinal 0 -> +base
ordinal 1 -> +base + cone
ordinal 2 -> -base
ordinal 3 -> +base + cone + 1
```

- Debug trace toont nu:

```text
downStack.minClearanceX=...
downStack.edgeConeClearance=...
```

## Behouden

- FT blijft `role_box`.
- LT blijft `projection_box`.
- Role-rank blijft leidend.
- Down-stack blijft onder eerdere role-boxes geplaatst.
