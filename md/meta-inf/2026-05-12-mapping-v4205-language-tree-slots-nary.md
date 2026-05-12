# 2026-05-12 — Mapping v4.20.5 Language Tree slots and n-ary draw

## Decision

Language Tree rendering moves toward one DS tree plus lexical-axis placement.

`slot0` and `slot1` are virtual lexical-axis positions. They are not inserted into the DS graph.

## Implemented

- OpenGraph draw accepts n-ary trees.
- Existing binary layout remains the two-child rule.
- Three-or-more-child nodes use a deterministic n-ary rule with vertical stacking and slight horizontal spread.
- Language Tree draw reserves an additional top row for `slot0`.
- Projection overlay renders `slot0` above the top node and `slot1` at top-node height on the left lexical axis.
- Type summary now describes S/V top trees as n-ary allowed / lexical-axis placement.

## Not implemented yet

- Actual placement-rule execution on the lexical axis.
- COMP assignment to `slot0`.
- TOPIC/WH assignment to `slot1`.
- FIN/PV rule for V2 and yes/no questions.
- Visual traces/copies on the lexical axis.

## Regression

- Language Tree: 3 pass, 0 fail.
- Mapping V4: 53 pass, 0 fail.
- Mapping V3: 13 pass, 0 fail.
- MD folder check: PASS.
