# 2026-05-03 — Mapping V4.20 Language Tree OPN test slice

## Status

```text
MAPPING_V4_20_LANGUAGE_TREE_OPN_TEST
```

## Added

- Language Tree OPN auto-open projection defaults.
- Three first testable language-tree examples.
- `tools/LanguageTreeRegressionChecker.java`.
- `run-language-tree-checker.bat`.

## Changed

- `graphStructure/Graph.java`
- `userInterface/GraphFileActions.java`
- `tools/MappingV4RegressionChecker.java` accepts role `C` for complementizer examples.

## Actual checks

```text
Mapping V4.20 Language Tree OPN regression checker: 3 pass, 0 fail
Mapping V4.19 baseline checker from this package line remains V4.16/V4.18-based if starting from V4.18.
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
