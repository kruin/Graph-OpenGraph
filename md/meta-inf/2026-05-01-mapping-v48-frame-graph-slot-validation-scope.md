# 2026-05-01 — Mapping V4.8 FRAME.graph slot validation scope

## Status

```text
MAPPING_V4_8_FRAME_GRAPH_SLOT_VALIDATION_SCOPE
```

## Summary

V4.8 is a documentation-only scope phase on top of V4.7.

It defines the intended boundary for a later minimal FRAME.graph slot validator. It does not implement that validator.

## Base

```text
Mapping_V4-26-05-01--v47-frame-graph-metadata.zip
```

Base behavior remains:

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
FRAME_GRAPH metadata read/count
Info summary for frame and slot counts
```

## Added

```text
md/sources/mapping-v4/frame-graph-v48.md
md/meta-inf/2026-05-01-mapping-v48-frame-graph-slot-validation-scope.md
md/examples/opn/mapping-v4-frame-v48-expected-output-manifest.md
md/PATCH-MANIFEST-v48-frame-graph-slot-validation-scope.md
```

## Updated

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
md/sources-md-zip/Mapping_V4-26-05-01--v48-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope-md-only.zip
```

## Scope

Documentation/package checkpoint only.

Not changed:

```text
Java source
.class files
jar files
parser
generator
validator
checker
placement rules
UI behavior
graph rendering
graph mutation behavior
example semantics
```

## Decisions

- FRAME.graph slot validation is not implemented in V4.8.
- V4.8 reserves the first validator slice for required semantic slots.
- First-slice slot roles are limited to `Agens`, `Patiens`, `RECIPIENT` and `THEME`.
- `FRAME_GRAPH` remains metadata only at runtime in this phase.
- Generated output remains derived from `MAPPING_V4` placement rules.

## Actual checks

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended phase

```text
Mapping V4.9 — FRAME.graph minimal slot validator
```
