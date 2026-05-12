# 2026-05-05 — Mapping V4.20.3 OpenGraph actions menu and local buttons

## Baseline

Started from V4.20.2.

## Added

- A dedicated top-level `OpenGraph` menu groups the OpenGraph user actions in one place:
  - Draw / Redraw
  - Grid Settings
  - Toggle Projections
  - Save OPN
- A compact action bar above each graph editor provides direct buttons near the graph/OPN view:
  - Draw
  - Grid
  - Toggle Proj.
  - Save OPN

## Coordinated action behavior

- `Draw` creates or replaces the current OpenGraph result and can therefore clear the previous OpenGraph save state.
- `Grid` changes the OpenGraphGrid settings and reapplies the grid without clearing the current OPN result state.
- `Toggle Projections` only changes projection visibility and does not refit or move the source structure grid.
- `Save OPN` remains available from the OpenGraph menu and local action bar when an editor is open.

## Packaging decision

- No separate md-only zip is generated for V4.20.3.
- All markdown remains under `md/` in the project zip.

## Preserved

- No mapping-rule changes.
- No generator changes.
- No language-tree coordinate-rule changes.
- No checker expectation changes.
- V4.20.2 OPN-directory preference and Info/Log tree-type reporting are preserved.

## Validation

- `tools.LanguageTreeRegressionChecker`: `3 pass, 0 fail`
- `tools.MappingV4RegressionChecker`: `53 pass, 0 fail`
- `tools.MappingV3RegressionChecker`: `13 pass, 0 fail`
- `tools.CheckMdFolder`: `PASS`
