# 2026-05-05 — Mapping V4.20.2 OPN preferences and tree info

## Baseline

Started from the clean V4.20.1 revert package.

## Added

- Separate persisted OPN default directory in controller options and `config/opengraphed_user.properties`.
- OPN dialog accessory for selecting/changing the default OPN directory.
- File chooser filters that make `.opn`, `.graph`, and combined GRAPH/OPN views selectable.
- Language-tree type summary stored on `Graph`:
  - `S` top node: S-tree, binary, recursive.
  - `V` top node: V-tree, non-binary, growing.
  - Other top nodes: language-tree, type unspecified.
- Info window displays jar version and language-tree type summary.
- Log window displays jar version and language-tree type summary above the operation tree.
- Jar manifest includes `Implementation-Version: v4.20.2`.

## Preserved

- No mapping-rule changes.
- No generator changes.
- No projection-layout changes.
- No checker expectation changes.
- No changes to V4.20.1 language-tree grid placement rules.

## Validation

- `tools.LanguageTreeRegressionChecker`: `3 pass, 0 fail`
- `tools.MappingV4RegressionChecker`: `53 pass, 0 fail`
- `tools.MappingV3RegressionChecker`: `13 pass, 0 fail`
- `tools.CheckMdFolder`: `PASS`
