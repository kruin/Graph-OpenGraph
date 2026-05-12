# 2026-05-12 — Mapping v4.20.6 Language Tree slot click guard

## Problem

After OpenDraw in Language Tree mode, clicking the virtual lexical-axis marker for `slot0` could be interpreted by Edit/Grid mode as a click in empty graph space. In single-click node creation mode this could create a real DS node on the marker. The Language Tree overlay then used that accidental top node as its anchor, making `slot0` and `slot1` appear one grid row too high.

## Fix

- `OpenGraphProjectionSupport.isLanguageTreeVirtualSlotHit(...)` detects clicks on virtual `slot0`/`slot1` markers and labels.
- `EditListener.mouseClicked(...)` consumes those clicks before the ordinary node-creation path.
- Slot anchoring now prefers real structure roots: nodes with children below them, or labels such as `S`, `CP`, `VP`, `V`, `DS`. Accidental isolated top nodes are ignored.
- The overlay adds a visible `LANGUAGE TREE` caption when Language Tree projection context is active.

## Test file

`examples/graph/lextest.graph` is a native GRAPH file. Its graph label and filename contain the Language Tree hint, so opening it activates Language Tree projection defaults.

## Placement-rule alternatives for Zinstype

See `md/sources/mapping-v4/language-tree-zinstype-placement-rules.md`.
