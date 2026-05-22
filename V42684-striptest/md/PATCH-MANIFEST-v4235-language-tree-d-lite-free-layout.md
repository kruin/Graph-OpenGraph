# PATCH MANIFEST v4.23.5 — Language Tree D-lite free layout

## Problem

The previous fixes solved individual visible defects, but they were still local patches:

- extra row for `S -> NP VP`;
- stable V-cluster bounding box;
- local V-cluster unary fan-out.

That kept revealing new non-free cases because the old layout box only represented coarse bounds, not actual occupied grid cells.

## Decision

Keep the recursive layout approach. Replace the Language Tree combine step with a D-lite version of the richer box model.

## Implementation

`operation/OpenGraphTreeDrawOperation.java` now adds:

- `LayoutBox` with occupied cells and left/right row contours;
- `freeUnaryRule(...)` for Language Tree unary fan-out;
- `freeBinaryRule(...)` for occupied-cell validation of binary combines;
- `forceUnaryFanOut(...)` so V-cluster preterminals are placed relative to their assigned side, not shifted blindly;
- recomputation of subtree bounds from occupied cells after free-layout operations.

The renderer remains bottom-up recursive.

## Scope

Applies only when the Language Tree root-branch spacing/free layout path is active. Simple/OpenGraph layouts keep their previous behavior.

## Config reservation

Added to defaults and user config:

```properties
language.layout.strategy=free_d_lite
projection.profile.language.layout.strategy=free_d_lite
```

The value is documented as the current strategy. Future values can be wired into UI/config without changing the basic recursive architecture.

## Non-goals

This patch does not implement a full global solver and does not change projection rules.
