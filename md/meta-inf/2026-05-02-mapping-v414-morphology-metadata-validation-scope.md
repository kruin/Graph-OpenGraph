# 2026-05-02 — Mapping V4.14 morphology metadata validation scope

Status:

```text
MAPPING_V4_14_MORPHOLOGY_METADATA_VALIDATION_SCOPE
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v413-lexicon-morphology-frame-selection-scope.zip
```

Base runtime behavior:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Added

```text
md/sources/mapping-v4/morphology-v414.md
md/meta-inf/2026-05-02-mapping-v414-morphology-metadata-validation-scope.md
md/examples/opn/mapping-v4-morphology-v414-expected-output-manifest.md
md/PATCH-MANIFEST-v414-morphology-metadata-validation-scope.md
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

V4.14 scopes the first later morphology step as metadata validation only.

The reserved morphology metadata keys are:

```text
tense
number
person
gender
case
mood
aspect
finite
```

This phase does not implement the validator.

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

## Check status

Because this phase is documentation-only, checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
