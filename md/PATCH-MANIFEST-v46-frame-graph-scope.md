# Patch manifest — Mapping V4.6 FRAME.graph scope

Patch name:

```text
Mapping_V4-26-05-01--v46-frame-graph-scope
```

## Type

Documentation and expected-output manifest only.

## Base

```text
Mapping_V4-26-05-01--v452-stable-handoff.zip
```

## Adds

```text
md/sources/mapping-v4/frame-graph-v46.md
md/meta-inf/2026-05-01-mapping-v46-frame-graph-scope.md
md/examples/opn/mapping-v4-frame-v46-expected-output-manifest.md
md/PATCH-MANIFEST-v46-frame-graph-scope.md
md/sources-md-zip/Mapping_V4-26-05-01--v46-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v46-frame-graph-scope-md-only.zip
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
UI
rendering
graph mutation
runtime example semantics
```

## Expected status after applying patch

```text
MAPPING_V4_6_FRAME_GRAPH_SCOPE
Runtime behavior still: MAPPING_V4_5_1_INFO_LABEL
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.7 — FRAME.graph metadata read / count
```
