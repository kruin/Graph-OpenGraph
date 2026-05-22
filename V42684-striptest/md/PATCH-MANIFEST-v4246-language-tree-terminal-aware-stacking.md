# PATCH MANIFEST v4.24.6 — Language Tree terminal-aware stacking

## Purpose

Fix remaining row-collision cases where a following category node can land on the same grid row as a preceding lexical/end terminal.

Observed cases:

- `man` and `V` on the same row inside `VP -> NP V`;
- `heeft` and `VD` on the same row inside `V -> pv VD`.

## Change

`LayoutBox` now records whether the bottom row of a subtree box is occupied by an end terminal.

`stackedBelowShiftY(...)` is now terminal-aware:

```text
if upper sibling box ends in a terminal:
    reserve one additional vertical row before placing the next subtree
```

This treats terminal rows as lexical positions, not merely as ordinary occupied node cells. A following category root must not share that terminal row.

## Preserved

- Structural LR order, including `VP -> NP V`.
- `pv-VD` / `VD-pv` toolbar order.
- Unary-aware subtree boxes from v4.24.4.
- n-ary direct placement for 3, 4, 5, ... children.
- Box-bottom stacking from v4.24.5.

## Files touched

- `operation/OpenGraphTreeDrawOperation.java`
- `build.bat`
- `md/CHANGELOG.md`
- `md/INDEX.md`
