# PATCH-MANIFEST v4.21.7 — Hide moved base projection labels

## Scope

Language Tree projection display only.

## Change

When a zinstype rule places an item on a surface slot, the old lexical projection text on the base axis is suppressed:

- Topicalisatie: suppress the base projection text for the moved categorial phrase and its lexical terminals.
- Stellend: suppress the base projection text for the default subject/topic phrase and its lexical terminals.
- V2 profiles (`Stellend`, `Topicalisatie`, `WH`, `Ja/nee`): suppress the base projection text for the finite verb terminal.

The projection line and axis marker remain visible; only the old base text is removed. The moved text is shown on the slot/V2 row.

## Files touched

- `userInterface/OpenGraphProjectionSupport.java`
