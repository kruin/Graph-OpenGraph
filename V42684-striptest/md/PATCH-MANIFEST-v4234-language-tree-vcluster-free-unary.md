# PATCH MANIFEST v4.23.4 — Language Tree V-cluster free unary nodes

## Problem

The previous VP-row fix made the upper sentence split clearer, but the V-cluster still contained unary preterminal chains such as:

```text
pv -> heeft
VD -> gebeten
```

The generic unary rule draws a child exactly below its parent. That is acceptable for a generic graph, but not for the OpenGraph/Language Tree visual convention: it creates non-free nodes in one vertical column. In the screenshot this showed up around `pv`, `heeft`, `VD`, and `gebeten`.

## Cause

The V-cluster order rule swapped and positioned the two V-cluster sides, but it did not override the already-computed unary layout inside the `pv` and `VD` subtrees. So each preterminal retained a vertical `parent -> terminal` line.

## Fix

Inside the Language Tree V-cluster rule only:

- the left V-cluster side fans its terminal one grid column left;
- the right V-cluster side fans its terminal one grid column right;
- the V-cluster bounding box is recomputed after this fan-out.

The projection mechanism is unchanged. The V-cluster choice still controls only local V-cluster order.

## Scope

- Applies only inside the Language Tree V-cluster rule.
- Does not change generic unary-tree drawing.
- Does not change zinstype placement rules.
