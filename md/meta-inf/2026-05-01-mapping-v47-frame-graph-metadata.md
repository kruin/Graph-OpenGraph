# 2026-05-01 — Mapping V4.7 FRAME.graph metadata read / count

## Status

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
```

## Summary

V4.7 implements the first runtime-visible FRAME.graph behavior after the V4.6 scope phase.

It reads `FRAME_GRAPH` metadata, counts frames and slots, and reports that metadata in the Info window. It does not validate frame-slot completeness, infer roles, alter generated output, render frames, or mutate the graph.

## Base

```text
Mapping_V4-26-05-01--v46-frame-graph-scope.zip
```

## Added

```text
md/sources/mapping-v4/frame-graph-v47.md
md/meta-inf/2026-05-01-mapping-v47-frame-graph-metadata.md
md/examples/opn/mapping-v4-frame-v47-expected-output-manifest.md
examples/opn/mapping-v4-frame/
```

## Changed

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
userInterface/GraphEditorInfoSupport.java
tools/MappingV4RegressionChecker.java
```

## Behavior

Recognized metadata section:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:
```

Info summary:

```text
OPN Frame graph: 1 frames, 2 slots, metadata only
```

## Preserved

```text
generator behavior
validator behavior for Mapping V4 placement rules
generated best output
graph rendering
graph mutation behavior
tree transformation boundary
explicit lexical-axis mapping
```

## Actual checks

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended phase

```text
Mapping V4.8 — FRAME.graph slot validation scope
```
