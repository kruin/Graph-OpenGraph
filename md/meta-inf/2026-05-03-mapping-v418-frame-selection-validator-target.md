# Phase manifest — Mapping V4.18 explicit frame-selection validator target

Status:

```text
MAPPING_V4_18_FRAME_SELECTION_VALIDATOR_TARGET
```

Date:

```text
2026-05-03
```

## Type

Documentation-only implementation target.

## Base

```text
Mapping_V4-26-05-03--v417-frame-selection-scope.zip
```

## Runtime behavior

Unchanged:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

## Added documents

```text
md/sources/mapping-v4/frame-selection-v418.md
md/meta-inf/2026-05-03-mapping-v418-frame-selection-validator-target.md
md/examples/opn/mapping-v4-frame-selection-v418-expected-output-manifest.md
md/PATCH-MANIFEST-v418-frame-selection-validator-target.md
```

## Scope

V4.18 fixes the target behavior for the next explicit frame-selection validator.

It defines:

```text
verbal Lexicon frame:<name> as selected-frame source
selected-frame validation against selected frame(s), not all inventory frames
multi-frame FRAME_GRAPH inventory handling
exact first-slice diagnostic strings
future Info-line summary shape
```

## Preserved

```text
no Java source changes
no class-file changes
no jar changes
no parser changes
no generator changes
no runtime validator changes
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
