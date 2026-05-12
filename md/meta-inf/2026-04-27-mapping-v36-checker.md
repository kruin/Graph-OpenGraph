# Mapping V3.6 — expected-output checker

## Status

Added command-line regression checker for the Mapping V3 core manifests.

## Scope

Checks:
- valid core examples under `examples/opn/mapping-v3-core/`
- invalid core examples under `examples/opn/mapping-v3-core-invalid/`
- expected generated best output
- expected validation fail behavior
- filename presence in invalid diagnostics
- `generated: none (invalid mapping)` for invalid mappings

Out of scope:
- WH / vraagzinnen
- NEG
- TIME / PLACE / bijwoorden
- DET splitting
- FRAME.graph
- lexicon
- UI rendering / rotate / layout

## Run

From the OpenGraphEd directory:

```text
java tools.MappingV3RegressionChecker .
```

Windows helper:

```text
run-mapping-v3-checker.bat
```

## Expected result

```text
summary: 13 pass, 0 fail
```
