# PATCH MANIFEST v4.24.4 — Language Tree unary-aware n-ary subtree boxes

## Goal

Repair the remaining VP/V-box intrusion where a unary descendant such as `pv -> heeft` could escape the avoid-bound that was computed for the higher `VP` placement. Also make the same accumulated-box logic explicit for true n-ary nodes with 3, 4, 5, ... children.

## Changes

- `OpenGraphTreeDrawOperation.java`
  - `LayoutBox.fromSubtree(...)` now accumulates local child coordinates while collecting subtree cells.
  - Unary chains now count fully in the real subtree box.
  - `forceUnaryFanOut(...)` now uses local child offsets (`-1` / `+1`) instead of parent absolute x-coordinates.
  - n-ary compact LR placement now uses complete accumulated child boxes, not just direct child/root extents.
  - n-ary collision validation checks full bounding boxes plus occupied cells.

## Intended effect

- `VP -> NP V` remains structural LR.
- `V -> pv VD` remains `pv-VD` or `VD-pv` according to the toolbar setting.
- `pv -> heeft` and comparable unary terminal chains may no longer leak into the higher `VP` box.
- True n-ary nodes are handled directly; they are not forced into artificial binary chains.

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- MD folder check: PASS
