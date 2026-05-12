# v4.22.0 — Dutch Language Tree root branch spacing

## Purpose

The earlier v4.21.9 solution placed FIN/V2 on a half-row below `slot1` to avoid horizontal alignment with the first DS child row, commonly `NP`/`VP` under `S`. That solved the collision visually, but it made FIN a non-grid position.

This patch replaces that temporary convention with a cleaner Dutch Language Tree layout rule: the first branching below the DS root is drawn one grid row longer.

## Layout convention

For `Language Tree / Phrase`:

```text
slot0  = COMP, one grid row above S
slot1  = voorveld / topicalisation, on S-height
FIN    = V2 finite-verb terminal, one normal grid row below slot1
DS     = first DS child row starts one grid row below FIN
```

In effect:

```text
slot0: (om)dat
slot1: (de man)
FIN:   heeft

DS children: NP / VP ...
```

## Implementation

- `OpenGraphTreeDrawOperation` now calls `lengthenFirstRootBranch(rootEx)` when `reserveLanguageSlots` is true.
- `lengthenFirstRootBranch` shifts every immediate root-child subtree one grid row downward and increases the root bounding height by one.
- `OpenGraphProjectionSupport.languageTreeFinAxisY` now returns a full-row FIN position: `slot1Y + rowHeight`.

## Scope

Only `Language Tree / Phrase` uses this extra root-branch spacing. Other structure types keep the existing tree layout.
