# PATCH MANIFEST v4.24.8 — Language Tree n-ary same-side terminal corridor fix

## Purpose

Fix conflicts in n-ary Language Tree fan-out where child 0 and child 2 were both placed on the same side and same column.  This created lexical corridor conflicts such as:

- `de` / `hond`
- `kleine` / `man`

## Implementation

`OpenGraphTreeDrawOperation.nAryCompactLRRule(...)` now tracks a per-side ordinal.

The horizontal fan-out becomes:

```text
L1, R1, L2, R2, L3, R3, ...
```

instead of repeatedly using the same `L` and `R` columns.

Vertical stacking remains source-order and terminal-aware.

## Files touched

- `operation/OpenGraphTreeDrawOperation.java`
- `md/CHANGELOG.md`
- `md/INDEX.md`
- `build.bat`
