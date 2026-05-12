# 2026-05-02 — Mapping V4.12 Lexicon validator

Status:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-02--v411-lexicon-validation-scope-FIXED-slim.zip
```

## Added

- Runtime Lexicon validation diagnostics.
- Invalid Lexicon examples under `examples/opn/mapping-v4-lexicon-invalid/`.
- V4.12 source document.
- V4.12 expected-output manifest.
- V4.12 patch manifest.

## Changed

- `graphStructure/Graph.java`
- `userInterface/GraphFileActions.java`
- `tools/MappingV4RegressionChecker.java`
- `examples/opn/mapping-v4-lexicon/EXPECTED.txt`
- compiled class files and runtime jars

## Preserved

- Generated output remains based on explicit `MAPPING_V4` placement rules.
- Lexicon validation failures do not suppress generated output.
- No role inference.
- No automatic lexical insertion.
- No automatic frame selection.
- No morphology / inflection.
- No Lexicon rendering.
- No graph mutation.

## Actual checks

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
