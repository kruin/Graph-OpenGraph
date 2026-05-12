# Language Tree — Zinstype and placement-rule alternatives

## Context

Language Tree now uses one DS tree. Surface differences are not created by tree transformations. They are created by placement rules on the lexical axis.

`slot0` and `slot1` are virtual lexical-axis positions, not DS nodes.

## Preferred UI grouping

Add a `Zinstype` group near the lexical axis controls in the edit window. The group controls the placement profile applied to the lexical axis.

Suggested buttons:

- `Bijzin`
- `Stellend`
- `Ja/nee-vraag`
- `WH-vraag`
- `Topicalisatie`
- `Basis` / `DS-volgorde`

## Alternative A — one-click zinstype profiles

Each button applies a complete profile.

- `Bijzin`: fill/use `slot0` for C; keep PV in base V position.
- `Stellend`: place subject or selected topic in `slot1`; place PV immediately after `slot1`.
- `Ja/nee-vraag`: leave `slot1` empty; place PV in first visible position.
- `WH-vraag`: place WH phrase in `slot1`; place PV immediately after `slot1`.
- `Topicalisatie`: place selected constituent in `slot1`; place PV immediately after `slot1`.
- `Basis`: clear derived placements; show the DS lexical projection order.

Advantage: fastest for classroom/test use.
Risk: less transparent if the user wants to inspect each individual rule.

## Alternative B — rule-step buttons

Keep `Zinstype` as a container but expose individual steps.

- `C → slot0`
- `Topic/WH → slot1`
- `PV → FIN`
- `PV blijft`
- `Reset plaatsing`

Advantage: didactically explicit; good for debugging.
Risk: more clicks; rule order must be guarded.

## Alternative C — profile plus rule preview

Use one-click zinstype buttons, but show the applied rule sequence next to the lexical axis.

Example for `WH-vraag`:

```text
slot1 = WH
PV = after(slot1)
Rest = DS order minus moved items
```

Advantage: combines speed with transparency.
Risk: requires a small read-only rule-preview panel.

## Recommended next implementation

Implement Alternative C.

Minimum model:

```text
ClauseType: BASIS | BIJZIN | STELLEND | YESNO | WH | TOPICALISATIE
Slot0: empty | C
Slot1: empty | selected constituent | WH | subject
FiniteVerbPlacement: BASE | FIRST | AFTER_SLOT1
RestPlacement: DS_ORDER_MINUS_MOVED
```

Important: do not create `slot0` or `slot1` as graph nodes. They remain virtual lexical-axis slots.
