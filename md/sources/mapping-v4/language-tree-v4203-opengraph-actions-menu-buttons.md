# Mapping V4.20.3 — OpenGraph actions menu and local buttons

## User-facing change

OpenGraph actions are now coordinated as a single workflow.

### Menu

A new top-level menu `OpenGraph` contains the OpenGraph workflow actions:

1. `Draw / Redraw`
2. `Grid Settings`
3. `Toggle Projections`
4. `Save OPN`

The general `Display` menu no longer contains the OpenGraph draw/toggle commands. The general `Modes` menu no longer contains OpenGraphGrid; ordinary Grid remains in Modes.

### Local buttons near the graph

Each graph editor window now has a compact OpenGraph action bar above the graph canvas:

```text
OpenGraph:  Draw  Grid  Toggle Proj.  Save OPN
```

This is intended as the primary user path while testing or editing OPN/language-tree drawings.

## Action coordination

### Draw

`Draw` opens the OpenGraph Draw dialog and creates or replaces the current OpenGraph drawing result.

### Grid

`Grid` opens OpenGraphGrid settings and reapplies the grid. It does not clear the current OPN result state. This means the user can draw first, adjust the grid, toggle projections, and then save OPN.

### Toggle Proj.

`Toggle Proj.` shows or hides projections. It does not refit the grid and does not move the source structure.

### Save OPN

`Save OPN` is part of the OpenGraph workflow menu and the local action bar. The old md-only packaging path is not used for this release.

## Version

```text
OpenGraphEd.jar: v4.20.3
```

Jar manifest:

```text
Implementation-Version: v4.20.3
```

## Source packaging

For V4.20.3 there is no md-only zip. The project zip contains the complete `md/` folder.

If the project zip is uploaded as a Project Source, the markdown is present inside it. In environments that index zip contents, those md files can be read from the zip; in environments that treat a zip as one opaque file, upload the relevant md files separately. The delivered project zip itself remains sufficient for code transfer and local build/test.
