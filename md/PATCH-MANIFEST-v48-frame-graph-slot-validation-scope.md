# Patch manifest — Mapping V4.8 FRAME.graph slot validation scope

Patch name:

```text
Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope
```

## Type

Documentation and expected-output manifest only.

## Base

```text
Mapping_V4-26-05-01--v47-frame-graph-metadata.zip
```

## Adds

```text
md/sources/mapping-v4/frame-graph-v48.md
md/meta-inf/2026-05-01-mapping-v48-frame-graph-slot-validation-scope.md
md/examples/opn/mapping-v4-frame-v48-expected-output-manifest.md
md/PATCH-MANIFEST-v48-frame-graph-slot-validation-scope.md
md/sources-md-zip/Mapping_V4-26-05-01--v48-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope-md-only.zip
```

## Updates

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
.class files
jar files
parser
generator
validator
checker
placement rules
UI
graph rendering
graph mutation
runtime example semantics
```

## Expected status after applying patch

```text
MAPPING_V4_8_FRAME_GRAPH_SLOT_VALIDATION_SCOPE
Runtime behavior still: MAPPING_V4_7_FRAME_GRAPH_METADATA
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.9 — FRAME.graph minimal slot validator
```
