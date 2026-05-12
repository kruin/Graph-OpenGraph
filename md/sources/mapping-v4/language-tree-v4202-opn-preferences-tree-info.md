# Mapping V4.20.2 — OPN preferences, tree type info, jar version info

## Scope

This patch is applied on top of the V4.20.1 revert baseline.

## Runtime behavior

### Preferred OPN directory

- OpenGraphEd now keeps a separate preferred directory for OPN files.
- The OPN open dialog starts in the saved OPN directory when enabled.
- Loading an `.opn` file updates `config/opengraphed_user.properties` with:
  - `opn.load.from.default.dir`
  - `opn.default.dir`
- The graph directory preference remains separate.

### File chooser filters

- Graph/OPN open dialogs expose selectable filters for:
  - `OPN files (*.opn)`
  - `GRAPH / OPN files`
  - `GRAPH files (*.graph)`
- The normal graph loader can open both `.graph` and `.opn` files.
- The OPN loader can still focus on OPN files but can also switch to `.graph`/mixed filters.

### Language tree type info

Language-tree OPN files now get a tree-type summary in the model and in UI diagnostics.

- Top node `S` is reported as: `S-tree; binary; recursive`.
- Top node `V` is reported as: `V-tree; non-binary; growing`.
- Other top labels are reported as language-tree with unspecified type.

This is metadata/reporting only; it does not change drawing coordinates, projections, mapping generation or validators.

### Info and Log version line

The Info and Log windows now show the OpenGraphEd jar version:

```text
OpenGraphEd.jar: v4.20.2
```

The jar manifest now includes:

```text
Implementation-Title: OpenGraphEd
Implementation-Version: v4.20.2
```

## Regression status

Last checked in container:

- Language Tree V4.20.1 checker: `3 pass, 0 fail`
- Mapping V4 checker: `53 pass, 0 fail`
- Mapping V3 checker: `13 pass, 0 fail`
- MD folder check: `PASS`
