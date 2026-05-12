# Patch manifest — Mapping V4.14 morphology metadata validation scope

Patch name:

```text
Mapping_V4-26-05-02--v414-morphology-metadata-validation-scope
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v413-lexicon-morphology-frame-selection-scope.zip
```

## Adds

```text
md/sources/mapping-v4/morphology-v414.md
md/meta-inf/2026-05-02-mapping-v414-morphology-metadata-validation-scope.md
md/examples/opn/mapping-v4-morphology-v414-expected-output-manifest.md
md/PATCH-MANIFEST-v414-morphology-metadata-validation-scope.md
md/sources-md-zip/Mapping_V4-26-05-02--v414-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v414-morphology-metadata-validation-scope-md-only.zip
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
MAPPING_V4_14_MORPHOLOGY_METADATA_VALIDATION_SCOPE
Runtime behavior remains MAPPING_V4_12_LEXICON_VALIDATOR
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
