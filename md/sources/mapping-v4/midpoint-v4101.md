# Mapping V4.10.1 — straight-edge midpoint cleanup

Status:

```text
MAPPING_V4_10_1_STRAIGHT_EDGE_MIDPOINT_CLEANUP
```

## Purpose

Remove the visible midpoint marker from ordinary straight undirected edges in normal rendering.

This fixes the unwanted middle dot on simple structure edges, for example the edge between `S` and `V` in the V4.10 lexicon example.

## Behavior

Changed:

```text
straight undirected edge: no center/midpoint marker is drawn
```

Preserved:

```text
curved undirected edge: center marker remains visible
orthogonal undirected edge: bend marker remains visible
directed edge: arrow remains visible
selected edge outline behavior remains unchanged
```

## Scope boundary

Not changed:

```text
MAPPING_V4 parser
MAPPING_V4 generator
MAPPING_V4 validator
FRAME_GRAPH metadata or validation
LEXICON metadata read/count
OPN semantics
graph mutation behavior
file format
```

## Checks

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
