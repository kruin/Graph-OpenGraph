# PATCH MANIFEST v4.24.3 — Language Tree structural LR + avoid-bound

## Goal

Repair v4.24.2 where the inherited right-side context could reverse structural VP order, producing `VP -> V NP` visually where the grammar/order expects `VP -> NP V`.

## Changes

- Restored structural binary placement for Language Tree:
  - first structural child is placed left;
  - second structural child is placed right.
- Kept the inherited avoid-bound in the binary combiner:
  - the complete right/second subtree box is shifted outside the left/first subtree box;
  - downward row-search remains active after the horizontal shift.
- Stopped using inherited side as a child-order flip.
- V-cluster order is now visual/structural again:
  - `pv-VD` means pv left, VD right;
  - `VD-pv` means VD left, pv right.
- Removed the visible Java 21 `new Integer(...)` removal warnings in the touched classes by using `Integer.valueOf(...)`.

## Files changed

- `operation/OpenGraphTreeDrawOperation.java`
- `userInterface/GraphFileActions.java`
- `build.bat`
- `md/CHANGELOG.md`

## Validation

- Java compile: OK.
- Jar rebuild: OK.
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK.
- `out/images/RotateCursor.gif`: present.
- GUI visual test: not performed in this environment.
