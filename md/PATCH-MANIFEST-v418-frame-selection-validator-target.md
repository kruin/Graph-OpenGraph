# Patch manifest — Mapping V4.18 explicit frame-selection validator target

Patch name:

```text
Mapping_V4-26-05-03--v418-frame-selection-validator-target
```

## Type

Documentation-only implementation target.

## Base

```text
Mapping_V4-26-05-03--v417-frame-selection-scope.zip
```

## Adds

```text
md/sources/mapping-v4/frame-selection-v418.md
md/meta-inf/2026-05-03-mapping-v418-frame-selection-validator-target.md
md/examples/opn/mapping-v4-frame-selection-v418-expected-output-manifest.md
md/PATCH-MANIFEST-v418-frame-selection-validator-target.md
md/sources-md-zip/Mapping_V4-26-05-03--v418-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-03--v418-frame-selection-validator-target-md-only.zip
```

## Changes

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
examples/opn/mapping-v4-checker/LAST-RUN.txt
```

## Does not change

```text
Java source
class files
jar files
Mapping V4 parsing/generation/validation
FRAME.graph runtime validation behavior
Lexicon runtime validation behavior
Morphology runtime validation behavior
OPN example semantics
graph rendering
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_18_FRAME_SELECTION_VALIDATOR_TARGET
Runtime behavior remains MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.19 — minimal explicit frame-selection validator
```
