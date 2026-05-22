# v4.26.3 — FT role-box adjunct-stack refinement

## Purpose

Compact Functional Tree adjunct placement after v4.26.2 made down-stack roles too wide.

## Changes

- `functional.layout.downStack.mode=adjunct_stack` is the default.
- Down-stack roles are anchored near `pred` using `functional.layout.downStack.xOffset`.
- Vertical spacing is controlled by `functional.layout.downStack.verticalGap`.
- Legacy wide lane remains available with `functional.layout.downStack.mode=edge_cone`.
- `functional.layout.debug=false` by default.

## Compatibility

- LT remains `projection_box`.
- FT remains `role_box`.
