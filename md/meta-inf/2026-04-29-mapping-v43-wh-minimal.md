# 2026-04-29 — Mapping V4.3 minimal WH

## Status

```text
MAPPING_V4_3_WH_MINIMAL
```

## Summary

V4.3 adds minimal WH behavior after the V4.2 scope freeze.

The implementation keeps the previous architecture intact: mapping logic does not mutate the graph and generated output is computed from placement constraints on the lexical axis.

## Added

- `WH` as a known Mapping role.
- `MAPPING_V4:` / `END_MAPPING_V4:` handling in the OPN mapping path.
- Valid V4.3 WH examples under `examples/opn/mapping-v4-wh/`.
- Invalid V4.3 WH examples under `examples/opn/mapping-v4-wh-invalid/`.
- `tools/MappingV4RegressionChecker.java`.
- `run-mapping-v4-checker.bat`.
- V4.1 NEG/TIME/PLACE examples and expected manifests, because this code package starts from the V4.0 scope-freeze zip.
- V4.3 documentation under `md/sources/mapping-v4/`.

## Preserved

- Mapping V3 core examples and checker source remain present.
- V3 rules are not intentionally changed.
- No UI/rendering/view-option changes.
- No graph mutation behavior.

## Scope boundary

Not included:

- DET splitting
- FRAME.graph
- lexicon / automatic role inference
- UI/rendering/view-options
- transformations / vooropplaatsing

## Expected checks

```text
javac full build: intended
Mapping V3 regression checker: expected 13 pass, 0 fail
Mapping V4.3 regression checker: expected 24 pass, 0 fail
MD folder check: expected PASS
```

## Note

This package was prepared from `Mapping_V4-26-04-29--v40-scope-freeze.zip`. Therefore it includes the V4.1 behavior/example layer plus V4.3 WH behavior in one forward package.
