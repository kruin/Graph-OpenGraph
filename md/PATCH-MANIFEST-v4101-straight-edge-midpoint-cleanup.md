# Patch manifest — Mapping V4.10.1 straight-edge midpoint cleanup

Patch name:

```text
Mapping_V4-26-05-02--v4101-straight-edge-midpoint-cleanup
```

## Type

Small rendering cleanup patch.

## Base

```text
Mapping_V4-26-05-02--v410-lexicon-metadata.zip
```

## Adds

```text
md/sources/mapping-v4/midpoint-v4101.md
md/meta-inf/2026-05-02-mapping-v4101-straight-edge-midpoint-cleanup.md
md/PATCH-MANIFEST-v4101-straight-edge-midpoint-cleanup.md
md/sources-md-zip/Mapping_V4-26-05-02--v4101-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v4101-straight-edge-midpoint-cleanup-md-only.zip
```

## Changes

```text
graphStructure/Edge.java
graphStructure/Edge.class
out/graphStructure/Edge.class
OpenGraphEd.jar
dist/OpenGraphEd.jar
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
Mapping V4 parsing/generation/validation
FRAME.graph metadata or validation
Lexicon metadata read/count
OPN example semantics
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_10_1_STRAIGHT_EDGE_MIDPOINT_CLEANUP
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
