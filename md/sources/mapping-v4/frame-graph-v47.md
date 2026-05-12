# Mapping V4.7 — FRAME.graph metadata read / count

Status:

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
```

## Purpose

V4.7 is the first small behavior slice after the V4.6 FRAME.graph scope phase.

It reads a `FRAME_GRAPH` section as metadata only and reports the number of frames and slots in the Info window.

## Base

```text
Mapping_V4-26-05-01--v46-frame-graph-scope.zip
```

## Implemented

- `FRAME_GRAPH:` / `END_FRAME_GRAPH:` metadata section is recognized.
- Rows of the following form are counted:

```text
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
```

- Distinct frame names are counted as frames.
- `slot:<role>` fields are counted as slots.
- Info window adds a summary such as:

```text
OPN Frame graph: 1 frames, 2 slots, metadata only
```

- Mapping V4 regression checker includes two valid FRAME.graph metadata examples.

## Still explicit

`FRAME.graph` remains metadata only.

Existing `MAPPING_V4` lexical-axis mapping remains explicit:

```text
x1|vrouw|role:Agens
x2|bijt|role:V
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
```

Generation remains derived from `PLACEMENT_RULES`, not from `FRAME_GRAPH`.

## Not implemented

V4.7 does not implement:

```text
frame-slot validation
role licensing diagnostics
automatic role inference
lexicon lookup
generated utterance changes
FRAME.graph rendering
graph mutation
tree transformation / vooropplaatsing
UI view toggles
```

## Expected checks

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next candidate phase

```text
V4.8 — FRAME.graph slot validation scope
```

Recommended first validation scope:

- required slot present / absent
- unknown slot role
- metadata diagnostics only
- still no generation changes
