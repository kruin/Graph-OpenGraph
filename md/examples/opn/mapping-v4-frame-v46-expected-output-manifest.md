# Mapping V4.6 expected-output manifest — FRAME.graph scope

Status: documentation-only expected-output manifest.

This manifest defines target behavior for later FRAME.graph implementation phases. V4.6 does not implement or run these checks yet.

## Directory proposal

Future valid examples:

```text
examples/opn/mapping-v4-frame/
```

Future invalid examples:

```text
examples/opn/mapping-v4-frame-invalid/
```

## Valid example targets

### 01-frame-bijten-explicit-mapping.opn

Purpose:

```text
FRAME.graph metadata plus unchanged explicit Mapping V4 lexical-axis mapping
```

Proposed future frame section:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:
```

Existing explicit Mapping V4 lexical interpretation:

```text
x1|vrouw|role:Agens
x2|bijt|role:V
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
```

Expected generated best remains:

```text
generated best: vrouw bijt de hond
```

Expected future frame summary, if a later metadata-read phase implements it:

```text
Frame graph: 1 frames, 2 slots, metadata only
```

### 02-frame-geven-explicit-mapping.opn

Purpose:

```text
FRAME.graph metadata for a ditransitive frame plus unchanged explicit Mapping V4 lexical-axis mapping
```

Proposed future frame section:

```text
FRAME_GRAPH:
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

Existing explicit Mapping V4 lexical interpretation:

```text
x1|vrouw|role:Agens
x2|heeft|role:V-AUX
x3|man|role:RECIPIENT
x4|boek|role:THEME
x5|gegeven|role:V-PART
```

Expected generated best remains:

```text
generated best: vrouw heeft man boek gegeven
```

Expected future frame summary, if a later metadata-read phase implements it:

```text
Frame graph: 1 frames, 3 slots, metadata only
```

## Future invalid target examples

These are not V4.6 runtime checks. They are reserved for a later validator phase.

### 01-frame-missing-required-slot.opn

Future expected validation detail:

```text
missing required frame slot Patiens for frame BIJTEN
```

### 02-frame-unknown-role.opn

Future expected validation detail:

```text
unknown frame slot role LOC for frame BIJTEN
```

### 03-frame-role-not-licensed.opn

Future expected validation detail:

```text
lexical role PLACE is not licensed by frame BIJTEN
```

### 04-frame-section-drawn-as-structure.opn

Future expected validation detail:

```text
FRAME_GRAPH metadata must not be drawn as STRUCTURE content
```

## V4.6 pass condition

Because V4.6 is documentation-only, the actual pass condition remains unchanged:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
