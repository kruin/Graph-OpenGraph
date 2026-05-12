# 2026-05-02 — Mapping V4.10.1 straight-edge midpoint cleanup

## Status

```text
MAPPING_V4_10_1_STRAIGHT_EDGE_MIDPOINT_CLEANUP
```

## Summary

V4.10.1 is a small rendering cleanup on top of V4.10.

Ordinary straight undirected edges no longer draw a visible center/midpoint marker. This removes the unwanted middle dot on simple structure edges such as `S — V`.

## Base

```text
Mapping_V4-26-05-02--v410-lexicon-metadata.zip
```

## Changed

```text
graphStructure/Edge.java
graphStructure/Edge.class
out/graphStructure/Edge.class
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Preserved

```text
curved and orthogonal undirected edge markers
directed edge arrows
Mapping V4 parser/generator/validator
FRAME.graph metadata and validation
Lexicon metadata read/count
OPN examples and semantics
graph mutation behavior
```

## Actual checks

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
