# PATCH MANIFEST v4.24.5 — Language Tree box-bottom vertical stacking

## Goal

Repair the box-level row violation where the following subtree root, such as `V`, could be placed on the same visual row as the deepest terminal in the previous subtree, such as `man`.

## Changes

- `OpenGraphTreeDrawOperation.java`
  - Added `stackedBelowShiftY(...)`.
  - Binary placement now uses `LayoutBox.maxY/minY` to place the second subtree below the real bottom of the first subtree.
  - Non-free `domainRule(...)` uses the same box-bottom vertical stacking.
  - V-cluster vertical placement now uses real subtree boxes instead of cached `boundHeight`.
  - n-ary compact LR updates the next left/right row from the child's actual subtree box.
  - Legacy n-ary stacking also advances by `LayoutBox.maxY`.

## Intended effect

- In `VP -> NP V`, the `V` node is below the complete `NP` box, not merely below a cached height.
- A terminal such as `man` cannot share a row with the following `V` node.
- Unary descendants and true n-ary children remain part of the placement box model.

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
