# Patch manifest — Mapping V4.7 FRAME.graph metadata read / count

Patch name:

```text
Mapping_V4-26-05-01--v47-frame-graph-metadata
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-01--v46-frame-graph-scope.zip
```

## Adds

```text
md/sources/mapping-v4/frame-graph-v47.md
md/meta-inf/2026-05-01-mapping-v47-frame-graph-metadata.md
md/examples/opn/mapping-v4-frame-v47-expected-output-manifest.md
md/PATCH-MANIFEST-v47-frame-graph-metadata.md
examples/opn/mapping-v4-frame/
md/sources-md-zip/Mapping_V4-26-05-01--v47-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v47-frame-graph-metadata-md-only.zip
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
userInterface/GraphEditorInfoSupport.java
tools/MappingV4RegressionChecker.java
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Does not change

```text
generated utterance rules
Mapping V4 placement validation semantics
graph rendering
graph mutation
tree transformation boundary
lexicon behavior
automatic role inference
```

## Expected status after applying patch

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.8 — FRAME.graph slot validation scope
```
