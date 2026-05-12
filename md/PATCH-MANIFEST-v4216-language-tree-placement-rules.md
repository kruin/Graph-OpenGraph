# PATCH-MANIFEST v4.21.6 — Language Tree placement rules revision

## Purpose

Revise Language Tree surface-placement display rules:

- Topicalisation always targets a categorical node (`NP`, `DP`, `VP`, etc.).
- V2 placement targets the finite-verb terminal, not the verbal category node.

## Behaviour

- `Topicalisatie`: selected categorical node is displayed on `slot1` as its lexical phrase, e.g. `(de man)`.
- `Stellend`: default subject/topic phrase is displayed on `slot1`.
- `WH`: `WH` is displayed on `slot1`.
- `Stellend`, `Topicalisatie`, `WH`: the V2 row displays the finite terminal, e.g. `heeft`, instead of `PV → FIN na slot1`.
- `Ja/nee`: the finite terminal is displayed in first visible position.
- `Bijzin`: `slot0` displays `(om)dat`; finite verb stays in the basis order.

## Notes

The DS tree is not transformed. The display is still an axis-level placement overlay.
