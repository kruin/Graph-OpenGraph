# Language Tree V4.20.7 — Zinstype profile + rule preview

## Goal

Alternative C is implemented: a one-click `Zinstype` profile selector plus a visible placement-rule preview near the lexical axis.

## UI

Each graph editor now has a local `Zinstype:` button group in the OpenGraph action bar:

- `Basis`
- `Bijzin`
- `Stellend`
- `Ja/nee`
- `WH`
- `Topicalisatie`

The buttons do not modify DS nodes and do not create graph nodes. They only set the active Language Tree zinstype profile in the projection settings.

## Overlay

When a Language Tree projection context is active, the overlay shows:

- `LANGUAGE TREE`
- virtual `slot0` and `slot1` on the lexical axis
- `Zinstype: <profile>`
- rule preview lines for that profile

Example:

```text
Zinstype: Bijzin
- slot0 = C
- slot1 = leeg
- PV blijft in basispositie
```

## Placement-rule model

The rule preview is declarative in this slice. It shows the profile and rule sequence but does not yet physically reorder lexical items on the axis.

The intended next step is to connect the visible profiles to real lexical-axis placement actions:

- `slot0` assignment for complementizers
- `slot1` assignment for topic/WH/subject
- `PV` placement as initial or immediately after `slot1`
- `rest` as DS-basisvolgorde minus moved items

## Click safety

Language Tree overlay labels and preview text are virtual. Clicks on these overlay items are consumed so Edit/Grid mode does not accidentally create real DS nodes on them.
