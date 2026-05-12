# Phase manifest — Mapping V4.15 morphology metadata validator target

Status:

```text
MAPPING_V4_15_MORPHOLOGY_METADATA_VALIDATOR_TARGET
```

Date:

```text
2026-05-02
```

## Type

Documentation-only implementation target.

## Base

```text
Mapping_V4-26-05-02--v412-lexicon-validator-slim.zip
```

This rebuild carries forward the V4.13 and V4.14 documentation-only phases and adds V4.15.

## Runtime behavior

Unchanged:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Added documents

```text
md/sources/mapping-v4/morphology-v415.md
md/meta-inf/2026-05-02-mapping-v415-morphology-metadata-validator-target.md
md/examples/opn/mapping-v4-morphology-v415-expected-output-manifest.md
md/PATCH-MANIFEST-v415-morphology-metadata-validator-target.md
```

## Scope

V4.15 fixes the target behavior for a later morphology metadata validator.

It defines:

```text
accepted morphology keys
minimal allowed value domain
target valid-output summaries
target invalid diagnostics
compatibility boundary between morphology features and pos
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
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
