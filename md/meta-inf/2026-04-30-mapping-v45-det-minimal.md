# 2026-04-30 — Mapping V4.5 minimal DET generator / validator

## Status

```text
MAPPING_V4_5_DET_MINIMAL
```

## Summary

V4.5 implements minimal DET behavior after the V4.4 documentation-only DET scope phase.

## Base

```text
Mapping_V4-26-04-30--v44-det-scope.zip
```

## Added

- `DET` as a known Mapping role.
- `det-target` and `det_target` parsing on lexical items.
- DET target validation.
- Valid DET examples under `examples/opn/mapping-v4-det/`.
- Invalid DET examples under `examples/opn/mapping-v4-det-invalid/`.
- Expected-output and expected-fail files for DET examples.
- `md/sources/mapping-v4/det-v45.md`.
- `md/examples/opn/mapping-v4-det-v45-expected-output-manifest.md`.
- V4 regression checker expansion to V4.5 DET.

## Changed

- `userInterface/GraphFileActions.java`
- `tools/MappingV4RegressionChecker.java`
- compiled class files for the changed Java sources
- V4 current-state, phasing, README, INDEX and CHANGELOG documentation

## Preserved

- No graph mutation.
- No tree transformation / vooropplaatsing.
- No FRAME.graph behavior.
- No lexicon behavior.
- No UI/rendering behavior changes.
- Existing V3, V4.1 and V4.3 checks remain passing.

## Expected checks

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
MD folder check: PASS
```
