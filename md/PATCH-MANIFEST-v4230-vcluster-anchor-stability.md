# PATCH MANIFEST v4.23.0 — Language Tree V-cluster anchor stability

## Issue

Switching the Language Tree V-cluster order between `pv-VD` and `VD-pv` still caused a visible one-grid-column shift in the rendered drawing. The right-side SYNT projection stayed stable, but the grid/content fit recentering changed the left footprint and moved the DS tree.

## Fix

For the V-cluster buttons only, the redraw now preserves the current OpenGraph root position:

- capture root position before reloading/redrawing from the original `.graph`;
- redraw the Language Tree with the new V-cluster order;
- translate the resulting graph so the root is back at exactly the captured position;
- skip the normal center-visible-content pass for this specific V-cluster toggle.

This keeps the tree/grid visually anchored while only `heeft` and `gebeten` switch local positions.

## Scope

- Applies to Language Tree V-cluster toggle only.
- Zinstype buttons keep their existing redraw/centering behavior.
- Projectie/SYNT rendering unchanged.
- User-facing spelling remains `pv`, not `PV`.

## Files touched

- `userInterface/OpenGraphActions.java`
- `userInterface/OpenGraphActions.class`
- `out/userInterface/OpenGraphActions.class`
- `OpenGraphEd.jar`
- `dist/OpenGraphEd.jar`
