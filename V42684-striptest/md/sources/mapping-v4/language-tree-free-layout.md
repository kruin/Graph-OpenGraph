# Language Tree — free layout strategy

## Decision

Language Tree keeps the bottom-up recursive drawing approach. The change is not from recursive to global layout. Instead, each recursive step now returns and recomputes a richer local layout box.

The current implementation is **D-lite**:

```text
layout(node):
  layout(children) recursively
  combine child boxes
  validate occupied cells and free-edge constraints
  return occupied-cell box
```

## Why D-lite

The older Simple/OpenGraph box model only knew a coarse bounding box:

```text
boundX, boundWidth, boundHeight
```

That is sufficient for many graph drawings, but too weak for Dutch Language Tree. Language Tree needs an additional visual constraint: real tree edges should not produce unnecessary vertical parent-child stacks. These vertical stacks are treated as **non-free nodes**.

D-lite adds enough information to make the first structural correction without replacing the whole renderer:

```text
occupied cells
left contour per row
right contour per row
recursive recomputed box
binary placement validation
general unary fan-out
```

## Current rules

### Unary

For Language Tree, unary parent-child edges fan out by one grid column.

Bad:

```text
X
|
Y
```

Preferred:

```text
X
 \
  Y
```

or, when forced locally by the V-cluster side:

```text
  X
 /
Y
```

### Binary

The existing compact/stacked OpenGraph placement remains the first candidate. The candidate is accepted only if:

```text
left and right occupied cells do not overlap
left child root is not vertically below parent
right child root is not vertically below parent
parent cell is not occupied by a child box
```

If the first candidate is invalid, the right subtree is moved down until the local placement is valid.

### V-cluster

The existing stable V-cluster box remains. `pv-VD` and `VD-pv` still swap local visual order without changing the surrounding root anchor. The local `pv -> heeft` and `VD -> gebeten` unary edges are forced away from the V-axis.

## Future strategy/config design

The config now reserves:

```properties
language.layout.strategy=free_d_lite
projection.profile.language.layout.strategy=free_d_lite
```

Planned future values:

```text
compact      minimal width/height first
left_first   prefer leftward fan-out and left-contour compactness
right_first  prefer rightward fan-out and right-contour compactness
free         most readable educational layout; no vertical real tree edges
```

The intended order remains:

```text
1. draw DS tree recursively
2. enforce free-layout constraints
3. compute projecties from final node positions
4. preserve root anchor during redraws
```

Projecties are not used to solve the tree layout. They remain derived from the final DS node positions.

## v4.23.6: n-binair compact LR

The active Language Tree strategy is now:

```properties
language.layout.strategy=nary_compact_lr
projection.profile.language.layout.strategy=nary_compact_lr
```

This keeps the recursive bottom-up renderer, but changes the Language Tree case for nodes with three or more children. Such nodes are no longer handled by the generic n-ary stack rule. They use a compact LR combiner:

```text
child 0 -> left
child 1 -> right
child 2 -> left
child 3 -> right
...
```

Each child box is placed on the first free local row on its side. Occupied cells are checked against earlier child boxes and against the parent cell. The result is n-binair/n-ary, compact, and still free of direct vertical parent-child placement.

The older `free_d_lite` value is still accepted as a fallback.

## v4.23.7: side-aware LR/RL recursion

The active strategy remains:

```properties
language.layout.strategy=nary_compact_lr
projection.profile.language.layout.strategy=nary_compact_lr
```

The strategy is now side-aware. The root starts in normal LR mode. A subtree that is placed on the right of its parent switches to RL mode: the first structural child is placed outward on the right and the second child inward on the left. A subtree that is placed on the left keeps LR mode.

Unary preterminals inherit the same side. This prevents right-growing V/VP material such as `pv -> heeft` from folding back to the left of the higher VP axis. V-cluster placement keeps its fixed reserved box, but assigns the ordered first child to the inherited outer side.
