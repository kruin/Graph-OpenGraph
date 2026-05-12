# Patch manifest — Mapping V4.12 Lexicon validator

Patch name:

```text
Mapping_V4-26-05-02--v412-lexicon-validator
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-02--v411-lexicon-validation-scope-FIXED-slim.zip
```

## Adds

```text
md/sources/mapping-v4/lexicon-validation-v412.md
md/meta-inf/2026-05-02-mapping-v412-lexicon-validator.md
md/examples/opn/mapping-v4-lexicon-v412-expected-output-manifest.md
md/PATCH-MANIFEST-v412-lexicon-validator.md
examples/opn/mapping-v4-lexicon-invalid/
md/sources-md-zip/Mapping_V4-26-05-02--v412-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v412-lexicon-validator-md-only.zip
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
examples/opn/mapping-v4-lexicon/EXPECTED.txt
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
generated utterance rules
Mapping V4 placement validation semantics
FRAME.graph slot validation semantics
graph rendering
graph mutation
tree transformation boundary
role inference
automatic lexical insertion
automatic frame selection
morphology / inflection
lexicon rendering
```

## Expected status after applying patch

```text
MAPPING_V4_12_LEXICON_VALIDATOR
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
