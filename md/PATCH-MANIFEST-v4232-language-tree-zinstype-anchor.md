# PATCH MANIFEST v4.23.2 — Language Tree zinstype anchor stability

## Problem

Switching the Language Tree zinstype to `Bijzin` could shift the displayed tree one grid column to the right. The cause was the redraw/fit pass using the active projection text, where `slot0` becomes `(om)dat` in `Bijzin`. The longer left projection text changed the rendered bounds and therefore the recentering result.

## Fix

For Language Tree zinstype redraws, the DS root position is now preserved when the current editor already contains an OpenGraph draw:

- capture the current root, normally `S`;
- reload the original `.graph` as before;
- redraw the requested zinstype;
- translate the new result so the root returns to the same grid coordinate;
- skip the ordinary center-visible-content pass for this redraw.

This makes `Basis`, `Bijzin`, `Stell.`, `Ja/nee`, `WH`, and `Topic` compare in place. Only the zinstype-specific projection/placement text changes.

## Scope

- Applies only to Language Tree zinstype redraws after an OpenGraph drawing is already present.
- First draw from a raw `.graph` still uses normal centering.
- V-cluster toggles use the same guarded anchor behaviour: preserve root only after a current OpenGraph draw exists.

## Files touched

- `userInterface/OpenGraphActions.java`
- `OpenGraphEd.jar`
- `dist/OpenGraphEd.jar`
