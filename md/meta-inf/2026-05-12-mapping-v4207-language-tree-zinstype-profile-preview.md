# 2026-05-12 — Mapping V4.20.7 Language Tree zinstype profile preview

## Changed files

- `userInterface/GraphEditorWindow.java`
  - adds local `Zinstype:` buttons in every graph editor window.
- `userInterface/GraphController.java`
  - forwards zinstype profile actions to OpenGraph actions.
- `userInterface/OpenGraphActions.java`
  - applies Language Tree zinstype profile settings without changing DS.
- `userInterface/OpenGraphProjectionSettings.java`
  - stores normalized Language Tree zinstype profile and rule-preview text.
- `userInterface/OpenGraphProjectionSupport.java`
  - draws the selected profile and rule preview near the lexical axis.
  - consumes clicks on Language Tree overlay captions/preview labels.
- `userInterface/OpenGraphEdAppInfo.java`
  - version set to `v4.20.7`.
- `manifest.mf`, `META-INF/MANIFEST.MF`, `build.bat`
  - manifest version set to `v4.20.7`.
- `md/sources/mapping-v4/language-tree-v4207-zinstype-profile-preview.md`
  - design and behavior note.

## Validation

Expected regression status:

- Language Tree: `3 pass, 0 fail`
- Mapping V4: `53 pass, 0 fail`
- Mapping V3: `13 pass, 0 fail`
- MD folder: `PASS`
