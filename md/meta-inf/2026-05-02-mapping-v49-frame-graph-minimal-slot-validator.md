# 2026-05-02 — Mapping V4.9 FRAME.graph minimal slot validator

## Status

```text
MAPPING_V4_9_FRAME_GRAPH_MINIMAL_SLOT_VALIDATOR
```

## Summary

V4.9 implements the minimal `FRAME_GRAPH` slot validator defined by the V4.8 scope phase.

It validates required frame slots against explicit `MAPPING_V4` lexical roles and reports diagnostics in the Info window and regression checker.

## Base

```text
Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope.zip
```

## Added

```text
md/sources/mapping-v4/frame-graph-v49.md
md/meta-inf/2026-05-02-mapping-v49-frame-graph-minimal-slot-validator.md
md/examples/opn/mapping-v4-frame-v49-expected-output-manifest.md
md/PATCH-MANIFEST-v49-frame-graph-minimal-slot-validator.md
examples/opn/mapping-v4-frame-invalid/
```

## Changed

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
examples/opn/mapping-v4-frame/EXPECTED.txt
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Behavior

Info now reports:

```text
OPN Frame graph: <n> frames, <m> slots; frame validation: <ok> ok, <fail> fail (...)
```

Frame-validation failures do not suppress generated output. Generated output remains based on explicit `MAPPING_V4` placement rules.

## Actual checks

```text
Mapping V4.9 FRAME.graph minimal slot validator regression checker: 37 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
