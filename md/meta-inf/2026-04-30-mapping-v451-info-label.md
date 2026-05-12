# 2026-04-30 — Mapping V4.5.1 Info label clarification

## Status

```text
MAPPING_V4_5_1_INFO_LABEL
```

## Summary

V4.5.1 is a small UI text clarification patch on top of V4.5.

The Info window now shows the top generated output as:

```text
generated best: <utterance>
```

instead of:

```text
generated: best: <utterance>
```

## Changed

- `userInterface/GraphEditorInfoSupport.java`
- `userInterface/GraphEditorInfoSupport.class`
- `out/userInterface/GraphEditorInfoSupport.class`
- `OpenGraphEd.jar`
- `dist/OpenGraphEd.jar`
- documentation current-state, phasing, README, INDEX and CHANGELOG

## Preserved

- No generator logic changes.
- No validator logic changes.
- No parser changes.
- No checker changes.
- No mapping-rule changes.
- No graph mutation.
- Invalid output remains:

```text
generated: none (invalid mapping)
```

## Expected checks

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Meaning

```text
generated best = the highest-ranked output candidate derived from the placement rules
```
