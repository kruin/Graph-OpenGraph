# Patch manifest — V4.20.3 OpenGraph actions menu and local buttons

## Version

`v4.20.3`

## Baseline

Applied on top of `v4.20.2`.

## Changed Java files

- `userInterface/GraphController.java`
- `userInterface/GraphEditorWindow.java`
- `userInterface/OpenGraphActions.java`
- `userInterface/OpenGraphDialog.java`
- `userInterface/OpenGraphEdAppInfo.java`
- `userInterface/menuAndToolBar/MenuAndToolBar.java`
- `userInterface/menuAndToolBar/MenuAndToolBarControlCatalog.java`
- `userInterface/menuAndToolBar/MenuAndToolBarWindowSupport.java`

## Changed build/manifest files

- `build.bat`
- `manifest.mf`
- `META-INF/MANIFEST.MF`

## New/updated behavior

- Added a single `OpenGraph` menu containing:
  - `Draw / Redraw`
  - `Grid Settings`
  - `Toggle Projections`
  - `Save OPN`
- Removed OpenGraph drawing/toggle commands from the general Display menu.
- Removed OpenGraphGrid from the general Modes chooser; ordinary Grid remains there.
- Added a local OpenGraph action bar above every graph editor:
  - `Draw`
  - `Grid`
  - `Toggle Proj.`
  - `Save OPN`
- `Grid Settings` no longer clears the current OPN result state.
- `Toggle Projections` keeps the source grid fixed and only changes projection visibility.
- `Draw` remains the operation that starts/replaces the current OpenGraph result.
- The OpenGraph Draw dialog now uses `Draw` language instead of `Run` language.
- Package version is now `v4.20.3`.
- No md-only zip is produced for this release; all markdown is inside the project zip under `md/`.

## Regression status

- Language Tree: `3 pass, 0 fail`
- Mapping V4: `53 pass, 0 fail`
- Mapping V3: `13 pass, 0 fail`
- MD folder: `PASS`
