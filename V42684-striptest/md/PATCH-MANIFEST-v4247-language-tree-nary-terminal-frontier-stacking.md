# PATCH MANIFEST v4.24.7 — Language Tree n-ary terminal frontier stacking

## Goal

Correct n-ary Language Tree placement after v4.24.6. The previous n-ary compact LR rule used separate left and right vertical frontiers. That allowed terminal siblings on opposite sides to share the same row.

## Main change

`nAryCompactLRRule(...)` now uses one accumulated vertical frontier in source order. Each next child is placed below the complete previous child subtree box using the same terminal-aware `stackedBelowShiftY(...)` rule as binary placement.

## Files changed

- `operation/OpenGraphTreeDrawOperation.java`
- `build.bat`
- `md/CHANGELOG.md`
- `md/INDEX.md`

## Validation

- Java compile OK
- Fresh jar OK
- Dry-run classpath OK
- Dry-run jar OK
- Zip integrity OK
