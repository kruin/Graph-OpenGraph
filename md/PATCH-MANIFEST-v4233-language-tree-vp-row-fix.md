# PATCH MANIFEST v4.23.3 — Language Tree VP row fix

## Problem

In the Dutch Language Tree, the classic `S -> NP VP` split could place the `VP` category on the same visual row as the deepest terminal inside the subject `NP`. This looked cramped and unintuitive in the bijzin/base display.

## Fix

The open-tree draw rule now inserts one extra vertical grid row between the left `NP` box and the right `VP` box for the specific root-level sentence pattern:

- root label `S`
- left child `NP` or `DP`
- right child `VP`

This keeps the `VP` category visually below the adjacent NP terminal and improves readability without changing unrelated binary trees.

## Scope

- Applies to Language Tree style `S -> NP VP` drawings.
- Does not change projection logic.
- Does not change V-cluster ordering behavior.
