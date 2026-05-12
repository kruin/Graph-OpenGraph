# Phase manifest — Mapping V4.17 explicit frame-selection scope

Status:

```text
MAPPING_V4_17_FRAME_SELECTION_SCOPE
```

Date:

```text
2026-05-03
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v416-morphology-metadata-validator.zip
```

## Runtime behavior

Unchanged:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

## Added documents

```text
md/sources/mapping-v4/frame-selection-v417.md
md/meta-inf/2026-05-03-mapping-v417-frame-selection-scope.md
md/examples/opn/mapping-v4-frame-selection-v417-expected-output-manifest.md
md/PATCH-MANIFEST-v417-frame-selection-scope.md
```

## Scope

V4.17 defines the boundary for later explicit frame-selection validation.

It reserves:

```text
frame:<name> on verbal Lexicon rows as an explicit selector candidate
multi-frame FRAME_GRAPH inventory handling
selected-frame slot validation
selected-frame licensing diagnostics
```

## Preserved

```text
no Java source changes
no class-file changes
no jar changes
no parser changes
no generator changes
no validator changes
no checker changes
no UI/rendering changes
no graph mutation
no OPN example semantics changes
```

## Expected unchanged checks

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
