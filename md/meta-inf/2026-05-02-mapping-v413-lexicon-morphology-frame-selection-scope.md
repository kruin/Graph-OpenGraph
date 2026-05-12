# 2026-05-02 — Mapping V4.13 Lexicon / morphology / frame-selection scope

Status:

```text
MAPPING_V4_13_LEXICON_MORPHOLOGY_FRAME_SELECTION_SCOPE
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v412-lexicon-validator.zip
```

Base runtime behavior:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Added

```text
md/sources/mapping-v4/lexicon-morphology-frame-selection-v413.md
md/meta-inf/2026-05-02-mapping-v413-lexicon-morphology-frame-selection-scope.md
md/examples/opn/mapping-v4-lexicon-v413-expected-output-manifest.md
md/PATCH-MANIFEST-v413-lexicon-morphology-frame-selection-scope.md
```

## Changed

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Runtime change

```text
none
```

## Summary

V4.13 defines the later work boundary for Lexicon, morphology metadata and frame-selection behavior.

It keeps V4.12 as the runtime behavior and records that future morphology and frame-selection behavior must be introduced only in later explicit behavior slices.

## Preserved

```text
Java source
class files
jar files
parser
generator
Mapping V4 validator
FRAME.graph metadata and slot validation
Lexicon validator behavior
OPN example semantics
graph rendering
graph mutation
```

## Actual checks

Because this phase is documentation-only, checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
