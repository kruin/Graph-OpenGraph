# PATCH MANIFEST v4.23.6 — Language Tree n-binair compact LR

## Problem

The v4.23.5 D-lite renderer made unary and binary Language Tree combines freer, but nodes with three or more children still fell back to the older n-ary rule. That rule was deterministic, but not the intended Language Tree strategy: it behaved like a generic open-tree stack instead of a compact n-binary / n-ary layout.

## Decision

Use the recursive renderer, but add a real Language Tree strategy value:

```properties
language.layout.strategy=nary_compact_lr
projection.profile.language.layout.strategy=nary_compact_lr
```

The strategy means:

- **n-binair / n-ary**: do not first force 3+ children into artificial binary boxes;
- **compact**: use occupied-cell validation and the first free local row;
- **LR**: place child boxes in source order by alternating left, right, left, right.

## Implementation

### `operation/OpenGraphTreeDrawOperation.java`

Added:

- `LAYOUT_NARY_COMPACT_LR` and `LAYOUT_FREE_D_LITE` strategy constants;
- config-compatible strategy normalization;
- a new overload that receives `languageTreeLayoutStrategy`;
- `nAryCompactLRRule(...)` for 3+ Language Tree children;
- `candidateNAryPlacementIsValid(...)` and `ShiftedLayoutBox` for occupied-cell validation against previously placed child boxes.

The existing unary and binary D-lite rules remain in use for one-child and two-child nodes.

### `userInterface/OpenGraphProjectionSettings.java`

Added a persistent Language Tree layout strategy field, getter/setter, display name, and normalization aliases:

```text
nary_compact_lr
n_ary_compact_lr
n-binair
nbinair
compact
compact-lr
LR
```

### Dialog/settings plumbing

Updated:

- `OpenGraphDialogSettingsState.java`
- `OpenGraphDialogSettingsSupport.java`
- `OpenGraphDialog.java`
- `OpenGraphProjectionSupport.java`
- `OpenGraphActions.java`

The strategy now flows from config/defaults into the active projection settings and then into the tree renderer.

## Notes

- Projecties remain derived from final node positions.
- No global solver is introduced.
- The fallback strategy `free_d_lite` remains recognized.
