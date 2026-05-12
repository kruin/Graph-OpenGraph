# Phase manifest — Mapping V4.16 morphology metadata validator

Status:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

Date:

```text
2026-05-02
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-02--v415-morphology-metadata-validator-target-full-from-v412-slim.zip
```

## Runtime behavior

V4.16 adds diagnostic-only morphology metadata validation for explicit fields on `LEXICON` rows.

The Info summary may now include:

```text
Lexicon: <n> entries; lexicon validation: <ok> ok, <fail> fail; morphology validation: <ok> ok, <fail> fail
```

## Added examples

```text
examples/opn/mapping-v4-morphology/
examples/opn/mapping-v4-morphology-invalid/
```

## Added documents

```text
md/sources/mapping-v4/morphology-v416.md
md/meta-inf/2026-05-02-mapping-v416-morphology-metadata-validator.md
md/examples/opn/mapping-v4-morphology-v416-expected-output-manifest.md
md/PATCH-MANIFEST-v416-morphology-metadata-validator.md
```

## Changed Java

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
```

## Preserved

```text
generated utterance rules
Mapping V4 placement validation semantics
FRAME.graph metadata or validation
Lexicon basic validation behavior
OPN structure rendering
graph mutation behavior
automatic inflection
surface-form generation
role inference
automatic lexical insertion
automatic frame selection
```

## Actual checks

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
