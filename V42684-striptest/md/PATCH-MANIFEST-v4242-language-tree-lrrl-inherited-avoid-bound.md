# PATCH MANIFEST v4.24.2 — Language Tree LR/RL inherited avoid-bound

## Goal

Repair the v4.24.1 Language Tree layout where the V subtree could fold back into the higher VP reserved zone.

## Changes

- Kept the requested LR/RL recursion model.
- Corrected binary recursive side propagation:
  - first child gets the current outer side;
  - second child gets the opposite side.
- Corrected V-cluster side propagation so pv/VD order determines which child gets the outer side.
- Added `enforceSiblingBoxAvoid(...)` to `OpenGraphTreeDrawOperation`.
- The binary combiner now moves the complete second subtree box beyond the first subtree box boundary before row-searching downward.
- The legacy v4.24.0 same-side RR/LL rule is still present but remains inactive.

## Files changed

- `operation/OpenGraphTreeDrawOperation.java`
- `build.bat`
- `manifest.mf`
- launcher comments in `*.bat`
- `md/CHANGELOG.md`

## Validation

- Java compile: expected OK.
- Jar rebuild: expected OK.
- GUI visual test: not performed in this environment.
