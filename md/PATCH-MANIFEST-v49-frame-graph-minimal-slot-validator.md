# Patch manifest — Mapping V4.9 FRAME.graph minimal slot validator

Patch name:

```text
Mapping_V4-26-05-02--v49-frame-graph-minimal-slot-validator
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope.zip
```

## Adds

```text
md/sources/mapping-v4/frame-graph-v49.md
md/meta-inf/2026-05-02-mapping-v49-frame-graph-minimal-slot-validator.md
md/examples/opn/mapping-v4-frame-v49-expected-output-manifest.md
md/PATCH-MANIFEST-v49-frame-graph-minimal-slot-validator.md
examples/opn/mapping-v4-frame-invalid/
md/sources-md-zip/Mapping_V4-26-05-02--v49-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v49-frame-graph-minimal-slot-validator-md-only.zip
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
examples/opn/mapping-v4-frame/EXPECTED.txt
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
MAPPING_V4_9_FRAME_GRAPH_MINIMAL_SLOT_VALIDATOR
Mapping V4.9 FRAME.graph minimal slot validator regression checker: 37 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
