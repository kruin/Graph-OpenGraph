# PATCH MANIFEST v4.22.4 — Language Tree V-cluster UI

## Scope

This patch adds a Language Tree-only V-cluster control for comparing:

- `PV-VD` = `heeft gebeten`
- `VD-PV` = `gebeten heeft`

The change is intentionally implemented as a local V-cluster branching/order choice. The projection mechanism remains unchanged: terminal nodes still project horizontally to the lexical axis.

## Files touched

- `userInterface/OpenGraphProjectionSettings.java`
  - Added `languageTreeVerbClusterOrder`.
  - Added constants `VERB_CLUSTER_PV_VD` and `VERB_CLUSTER_VD_PV`.
  - Added normalisation and display helpers.

- `userInterface/GraphEditorWindow.java`
  - Added Language Tree header UI group:
    - `V-cluster: [PV-VD] [VD-PV]`
  - The group is visible only when `Structure type = Language Tree / Phrase`.
  - Button choice immediately redraws from the original `.graph`.

- `userInterface/GraphController.java`
  - Added forwarding methods for `applyLanguageTreeVerbClusterOrder`.

- `userInterface/OpenGraphActions.java`
  - Added `applyLanguageTreeVerbClusterOrder`.
  - Passes the chosen order to the OpenGraph draw operation.

- `operation/OpenGraphTreeDrawOperation.java`
  - Added Language Tree V-cluster ordering.
  - Detects local `V` clusters with PV/finite and VD/participle children.
  - Orders those children as `PV-VD` or `VD-PV` during layout only.
  - Does not mutate graph labels or graph source structure.

- `userInterface/OpenGraphProjectionSupport.java`
  - Loads/saves the Language Tree V-cluster setting from config.
  - Shows active V-cluster order in the compact Language Tree overlay.

- `config/opengraph_defaults.properties`
- `config/opengraph_user.properties`
  - Added:
    - `language.verbcluster.order=pv_vd`
    - `projection.profile.language.verbcluster.order=pv_vd`

## Design decision

The V-cluster switch is not a projection rule. It is a local drawing/order rule inside the Language Tree V-cluster. This keeps the LEX projection mechanism stable.

## Expected visual effect

For a V-cluster with terminals `heeft` and `gebeten`:

- `PV-VD`: short/first branch = `heeft`, long/second branch = `gebeten`.
- `VD-PV`: short/first branch = `gebeten`, long/second branch = `heeft`.

This supports comparison of:

```text
dat de hond de man heeft gebeten
dat de hond de man gebeten heeft
```

