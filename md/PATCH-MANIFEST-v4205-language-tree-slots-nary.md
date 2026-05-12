# PATCH MANIFEST v4.20.5 — Language Tree slots and n-ary OpenGraph draw

Date: 2026-05-12
Base: v4.20.4

## Scope

This patch starts the next Language Tree phase: one DS tree with lexical-axis placement support.

## Main behavior

- `OpenGraphTreeDrawOperation` no longer rejects non-binary trees for the OpenGraph draw path.
- Binary trees keep the existing two-child layout rule.
- Nodes with three or more children use a new n-ary open-tree rule:
  - children keep deterministic structural order,
  - child boxes are stacked on successive grid rows,
  - a small horizontal spread keeps multi-edges readable.
- `correctGridCoordinates` now recurses over all children instead of only left/right.
- Language Tree draw reserves one extra grid row above the root so `slot0` can sit one grid line above `S` / the top node.
- Language Tree projection rendering adds virtual lexical-axis markers:
  - `slot0` one grid row above the top structure node,
  - `slot1` on the same row as the top structure node.
- `slot0` and `slot1` are rendered as overlay/projection markers, not as DS graph nodes.

## Main files touched

- `operation/OpenGraphTreeDrawOperation.java`
- `userInterface/OpenGraphActions.java`
- `userInterface/OpenGraphProjectionSupport.java`
- `graphStructure/Graph.java`
- `userInterface/OpenGraphEdAppInfo.java`
- `build.bat`

## Validation

- `javac -Xmaxerrs 500 -encoding UTF-8 -d out @sources.txt`: OK, warnings only.
- `tools.LanguageTreeRegressionChecker`: `3 pass, 0 fail`.
- `tools.MappingV4RegressionChecker`: `53 pass, 0 fail`.
- `tools.MappingV3RegressionChecker`: `13 pass, 0 fail`.
- `tools.CheckMdFolder`: `PASS`.

## Notes

This patch does not yet implement full lexical-axis placement rules for V2, COMP filling, WH/topicalization, or FIN/PV movement. It creates the render/layout basis: virtual slots plus n-ary DS rendering.
