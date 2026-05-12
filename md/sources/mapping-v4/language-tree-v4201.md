# Mapping V4.20.1 — Language Tree OPN open-grid fix

## Status

```text
MAPPING_V4_20_1_LANGUAGE_TREE_OPN_OPEN_GRID_FIX
```

## Goal

Repair the first Language Tree OPN test slice so the shipped test files open as OpenGraph-style source trees.

## Fixes

- Pipe-style `STRUCTURE_NODES` coordinates are now loaded as OpenGraph grid coordinates using the active 20 px grid cell.
- The OPN pipe loader sizes the grid display window from the largest source coordinate instead of relying only on the old fixed grid size.
- The three Language Tree OPN examples no longer place multiple source nodes on the same horizontal grid row.
- The Language Tree regression checker now asserts:
  - every source node is on the OpenGraphGrid;
  - no two source nodes share the same horizontal grid row;
  - every source node lies inside the OpenGraphGrid display window.

## Preserved

- No generated utterance rule changes.
- No FRAME.graph, Lexicon or morphology validation changes.
- No projection rendering algorithm changes.
- No graph mutation behavior changes.
- `STRUCTURE_TYPE: LANGUAGE_TREE` still opens with LEX-left and SYN-right projection defaults.

## Actual checks

```text
Mapping V4.20.1 Language Tree OPN regression checker: 3 pass, 0 fail
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
