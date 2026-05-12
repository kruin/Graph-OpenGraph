# Patch manifest — Mapping V4.13 Lexicon / morphology / frame-selection scope

Patch name:

```text
Mapping_V4-26-05-02--v413-lexicon-morphology-frame-selection-scope
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v412-lexicon-validator.zip
```

## Adds

```text
md/sources/mapping-v4/lexicon-morphology-frame-selection-v413.md
md/meta-inf/2026-05-02-mapping-v413-lexicon-morphology-frame-selection-scope.md
md/examples/opn/mapping-v4-lexicon-v413-expected-output-manifest.md
md/PATCH-MANIFEST-v413-lexicon-morphology-frame-selection-scope.md
md/sources-md-zip/Mapping_V4-26-05-02--v413-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v413-lexicon-morphology-frame-selection-scope-md-only.zip
```

## Changes

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
Java source
class files
jar files
Mapping V4 parsing/generation/validation
FRAME.graph metadata or validation
Lexicon validator behavior
OPN example semantics
graph rendering
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_13_LEXICON_MORPHOLOGY_FRAME_SELECTION_SCOPE
Runtime behavior remains MAPPING_V4_12_LEXICON_VALIDATOR
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
