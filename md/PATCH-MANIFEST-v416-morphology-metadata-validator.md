# Patch manifest — Mapping V4.16 morphology metadata validator

Patch name:

```text
Mapping_V4-26-05-02--v416-morphology-metadata-validator
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-02--v415-morphology-metadata-validator-target-full-from-v412-slim.zip
```

## Adds

```text
md/sources/mapping-v4/morphology-v416.md
md/meta-inf/2026-05-02-mapping-v416-morphology-metadata-validator.md
md/examples/opn/mapping-v4-morphology-v416-expected-output-manifest.md
md/PATCH-MANIFEST-v416-morphology-metadata-validator.md
examples/opn/mapping-v4-morphology/
examples/opn/mapping-v4-morphology-invalid/
md/sources-md-zip/Mapping_V4-26-05-02--v416-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v416-morphology-metadata-validator-md-only.zip
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
examples/opn/mapping-v4-checker/LAST-RUN.txt
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
FRAME.graph metadata or validation
Lexicon basic validation behavior
graph rendering
graph mutation
tree transformation boundary
role inference
automatic lexical insertion
automatic frame selection
automatic inflection
surface-form generation
lexicon rendering
```

## Expected status after applying patch

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
