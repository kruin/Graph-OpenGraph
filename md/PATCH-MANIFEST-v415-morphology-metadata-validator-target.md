# Patch manifest — Mapping V4.15 morphology metadata validator target

Patch name:

```text
Mapping_V4-26-05-02--v415-morphology-metadata-validator-target-full-from-v412
```

## Type

Documentation-only implementation target, rebuilt on the full V4.12 slim runtime package.

## Base

```text
Mapping_V4-26-05-02--v412-lexicon-validator-slim.zip
```

This package carries forward the V4.13 and V4.14 documentation-only phases and adds V4.15.

## Adds

```text
md/sources/mapping-v4/lexicon-morphology-frame-selection-v413.md
md/meta-inf/2026-05-02-mapping-v413-lexicon-morphology-frame-selection-scope.md
md/examples/opn/mapping-v4-lexicon-v413-expected-output-manifest.md
md/PATCH-MANIFEST-v413-lexicon-morphology-frame-selection-scope.md
md/sources/mapping-v4/morphology-v414.md
md/meta-inf/2026-05-02-mapping-v414-morphology-metadata-validation-scope.md
md/examples/opn/mapping-v4-morphology-v414-expected-output-manifest.md
md/PATCH-MANIFEST-v414-morphology-metadata-validation-scope.md
md/sources/mapping-v4/morphology-v415.md
md/meta-inf/2026-05-02-mapping-v415-morphology-metadata-validator-target.md
md/examples/opn/mapping-v4-morphology-v415-expected-output-manifest.md
md/PATCH-MANIFEST-v415-morphology-metadata-validator-target.md
md/sources-md-zip/Mapping_V4-26-05-02--v415-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v415-morphology-metadata-validator-target-md-only.zip
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
Morphology runtime behavior
OPN example semantics
graph rendering
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_15_MORPHOLOGY_METADATA_VALIDATOR_TARGET
Runtime behavior remains MAPPING_V4_12_LEXICON_VALIDATOR
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
