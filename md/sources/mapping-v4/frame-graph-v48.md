# Mapping V4.8 — FRAME.graph slot validation scope

Status:

```text
MAPPING_V4_8_FRAME_GRAPH_SLOT_VALIDATION_SCOPE
```

## Purpose

V4.8 defines the next FRAME.graph behavior slice before implementation: minimal slot validation.

This phase is documentation and expected-output scope only. It does not change Java source, class files, jars, parser behavior, generator behavior, validator behavior, checker behavior, UI, rendering, graph mutation or example semantics.

## Base

```text
Mapping_V4-26-05-01--v47-frame-graph-metadata.zip
```

Preserved base behavior:

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
FRAME_GRAPH metadata read/count
Info summary for frame and slot counts
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Core decision

V4.8 does not implement frame-slot validation.

It only defines the smallest later validation behavior that may be implemented in V4.9:

```text
FRAME_GRAPH declares required semantic slots.
MAPPING_V4 lexical items explicitly fill roles.
A later validator checks compatibility between declared frame slots and explicit lexical roles.
```

The generator remains unchanged:

```text
generated best comes from MAPPING_V4 placement rules
not from FRAME_GRAPH
```

## Validation model reserved for V4.9

A later minimal validator may check:

1. required frame slots are filled by explicit `MAPPING_V4` lexical roles;
2. frame slot roles are known Mapping roles;
3. lexical semantic roles are licensed by the selected frame;
4. malformed `FRAME_GRAPH` metadata is reported as metadata diagnostics;
5. invalid FRAME.graph validation suppresses generated output only if the later phase explicitly decides that frame validation is part of the mapping validity gate.

V4.8 does not decide item 5 as runtime behavior. It records the decision point.

## Known frame slot roles for first validator slice

The first validation slice should be conservative and reuse existing Mapping roles:

```text
Agens
Patiens
RECIPIENT
THEME
```

Later roles remain outside the first validator slice unless explicitly added:

```text
TIME
PLACE
NEG
WH
DET
V
V-AUX
V-PART
```

Reason: V4.8 keeps FRAME.graph semantic-slot validation separate from clause-mode, adverbial, determiner and verb-domain mechanics.

## Proposed valid examples for later V4.9

### BIJTEN with required Agens and Patiens

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:

MAPPING_V4:
x1|vrouw|role:Agens
x2|bijt|role:V
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
...
END_MAPPING_V4:
```

Expected later validator result:

```text
frame validation: 2 ok, 0 fail
```

Expected generated output remains:

```text
generated best: vrouw bijt de hond
```

### GEVEN with Agens, RECIPIENT and THEME

```text
FRAME_GRAPH:
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

Expected later validator result:

```text
frame validation: 3 ok, 0 fail
```

Expected generated output remains:

```text
generated best: vrouw heeft man boek gegeven
```

## Proposed invalid examples for later V4.9

### Missing required slot

If `FRAME_GRAPH` declares:

```text
frame|BIJTEN|slot:Patiens|required
```

but no explicit `MAPPING_V4` lexical item has `role:Patiens`, the later diagnostic should contain:

```text
missing required frame slot Patiens for frame BIJTEN
```

### Unknown frame slot role

If `FRAME_GRAPH` declares:

```text
frame|BIJTEN|slot:LOC|required
```

and `LOC` is not in the known first-slice frame slot roles, the later diagnostic should contain:

```text
unknown frame slot role LOC for frame BIJTEN
```

### Lexical role not licensed by frame

If the explicit lexical mapping contains a semantic role not declared by the frame:

```text
x5|daar|role:PLACE
```

while the selected first-slice frame only declares `Agens` and `Patiens`, the later diagnostic should contain:

```text
lexical role PLACE is not licensed by frame BIJTEN
```

For V4.8 this remains a target rule, not runtime behavior.

### Malformed frame metadata

If a `FRAME_GRAPH` row lacks a frame name or slot field, the later diagnostic should contain:

```text
malformed FRAME_GRAPH row
```

## Explicitly out of scope for V4.8

```text
Java source changes
.class rebuilds
jar rebuilds
FRAME.graph runtime validation
checker expansion
new invalid .opn examples
FRAME.graph generation behavior
role inference
verb-to-frame lexicon lookup
automatic frame selection
FRAME.graph rendering
graph mutation
tree transformation / vooropplaatsing
UI view toggles
adjective placement
complex NP structure
relative clauses
multiple DETs targeting the same nominal role
```

## Boundary retained from V4.7

V4.7 already reads and counts `FRAME_GRAPH` metadata.

V4.8 does not retract that behavior. It only defines the next validation scope.

Current runtime-visible FRAME.graph behavior remains:

```text
OPN Frame graph: <n> frames, <m> slots, metadata only
```

## Expected checks

Because V4.8 is documentation-only:

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next candidate phase

```text
V4.9 — FRAME.graph minimal slot validator
```

Recommended V4.9 scope:

```text
required slot present / absent
unknown slot role
metadata diagnostics in Info
no generated-output changes unless explicitly decided
no graph mutation
no rendering
```
