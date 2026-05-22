# PATCH MANIFEST v4.24.9 — Language Tree n-ary terminal diagonal clearance

## Problem

In n-ary NP fan-out, the first left and right lexical children were only one grid column away from the parent. This made edges such as `NP-de` and `NP-grote` visually/vertically unfree even after same-side terminal corridors were separated.

## Changes

- Added terminal-specific diagonal clearance for n-ary children.
- Terminal children in true n-ary fan-out now start one extra column away from the parent axis.
- Repeated same-side children still use separate corridors: `L1, R1, L2, R2, ...`.
- Source-order vertical stacking remains unchanged.

## Kept

- direct n-ary placement for 3, 4, 5, ... children
- unary-aware subtree boxes
- terminal-aware stacking
- structural `VP -> NP V`
- toolbar-controlled `pv-VD` / `VD-pv`
- no RR/LL cascade

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK
