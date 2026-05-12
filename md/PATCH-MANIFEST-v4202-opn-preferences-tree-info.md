# Patch manifest — V4.20.2 OPN preferences and tree info

## Version

`v4.20.2`

## Changed Java files

- `graphStructure/Graph.java`
- `userInterface/GraphController.java`
- `userInterface/GraphControllerOptions.java`
- `userInterface/GraphControllerOptionsIO.java`
- `userInterface/GraphEditorInfoSupport.java`
- `userInterface/GraphEditorLogSupport.java`
- `userInterface/GraphEditorLogWindow.java`
- `userInterface/GraphFileActions.java`
- `userInterface/OpenGraphEdAppInfo.java`
- `userInterface/fileUtils/GraphOnlyFilter.java`

## Changed build/manifest files

- `build.bat`
- `manifest.mf`
- `META-INF/MANIFEST.MF`

## New/updated behavior

- Separate persisted preferred OPN folder.
- OPN and Graph file filters selectable in open dialogs.
- Info and Log show OpenGraphEd jar version.
- Info and Log show language tree type metadata: S-tree recursive or V-tree growing.

## Regression status

- Language Tree: `3 pass, 0 fail`
- Mapping V4: `53 pass, 0 fail`
- Mapping V3: `13 pass, 0 fail`
- MD folder: `PASS`
