# Phase manifest — Mapping V4.20.1 Language Tree OPN open-grid fix

Status:

```text
MAPPING_V4_20_1_LANGUAGE_TREE_OPN_OPEN_GRID_FIX
```

Base:

```text
MAPPING_V4_20_LANGUAGE_TREE_OPN_TEST
```

## Type

Small corrective behavior/test-data slice.

## Included

- Pipe OPN coordinate load fixed from 25 px scaling to OpenGraphGrid 20 px scaling.
- Pipe OPN grid size now expands to fit the largest source grid coordinate.
- Three Language Tree OPN examples revised so source nodes occupy unique horizontal grid rows.
- Language Tree checker expanded with grid-alignment, unique-row and in-grid assertions.

## Checks

```text
Mapping V4.20.1 Language Tree OPN regression checker: 3 pass, 0 fail
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
