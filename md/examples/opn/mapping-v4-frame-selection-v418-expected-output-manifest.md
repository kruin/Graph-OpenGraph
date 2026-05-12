# Mapping V4.18 expected-output manifest — explicit frame-selection validator target

Status:

```text
documentation-only implementation target
runtime remains V4.16
```

## Purpose

This manifest fixes the target output for the next Java behavior slice: minimal explicit selected-frame validation.

V4.18 itself does not add runtime examples or checker cases.

## Future valid examples

Future directory:

```text
examples/opn/mapping-v4-frame-selection/
```

### 01-frame-selection-bijten-from-multiframe-inventory.opn

Purpose:

```text
explicitly select BIJTEN from a FRAME_GRAPH inventory that also contains GEVEN
```

Lexicon selector:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
```

Target frame inventory:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

Expected mapping:

```text
Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
```

Expected Mapping V4 validation:

```text
validation: 3 ok, 0 fail (best placement rules satisfied)
```

Expected generated output:

```text
generated: best: vrouw bijt de hond
```

Expected frame graph output:

```text
Frame graph: 2 frames, 5 slots; frame selection: 1 selected, 0 fail; selected frame validation: 2 ok, 0 fail (selected frame slots satisfied)
```

### 02-frame-selection-geven-vpart-from-multiframe-inventory.opn

Purpose:

```text
explicitly select GEVEN from a V-PART Lexicon row
```

Lexicon selector:

```text
lex|geven|lemma:geven|form:gegeven|role:V-PART|frame:GEVEN|pos:VPP
```

Expected mapping:

```text
Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
```

Expected Mapping V4 validation:

```text
validation: 5 ok, 0 fail (best placement rules satisfied)
```

Expected generated output:

```text
generated: best: vrouw heeft man boek gegeven
```

Expected frame graph output:

```text
Frame graph: 2 frames, 5 slots; frame selection: 1 selected, 0 fail; selected frame validation: 3 ok, 0 fail (selected frame slots satisfied)
```

## Future invalid frame-selection examples

Future directory:

```text
examples/opn/mapping-v4-frame-selection-invalid/
```

These examples must keep Mapping V4 placement validation valid. Frame-selection diagnostics are separate and informational; generated output remains present.

### 01-frame-selection-missing-selected-frame.opn

Target condition:

```text
FRAME_GRAPH contains multiple frames, but no verbal Lexicon row contains frame:<name>
```

Expected Mapping V4 validation starts with:

```text
validation: 3 ok, 0 fail
```

Expected generated output:

```text
generated: best: vrouw bijt de hond
```

Expected frame graph output:

```text
Frame graph: 2 frames, 5 slots; frame selection: 0 selected, 1 fail (file: 01-frame-selection-missing-selected-frame.opn; missing selected frame for multi-frame FRAME_GRAPH)
```

### 02-frame-selection-non-verbal-selector.opn

Target condition:

```text
frame:<name> appears on a non-verbal Lexicon row
```

Expected diagnostic:

```text
selected frame BIJTEN is attached to non-verbal lexicon entry hond
```

Expected frame graph output starts with:

```text
Frame graph: 1 frames, 2 slots; frame selection: 0 selected, 1 fail
```

### 03-frame-selection-multiple-selected-frames.opn

Target condition:

```text
more than one verbal Lexicon row selects a different frame
```

Expected diagnostic:

```text
multiple selected frames: BIJTEN, GEVEN
```

Expected frame graph output starts with:

```text
Frame graph: 2 frames, 5 slots; frame selection: 2 selected, 1 fail
```

### 04-frame-selection-role-not-licensed.opn

Target condition:

```text
selected frame BIJTEN, but explicit MAPPING_V4 contains a semantic role not licensed by BIJTEN
```

Expected Mapping V4 validation starts with:

```text
validation: 3 ok, 0 fail
```

Expected generated output:

```text
generated: best: vrouw bijt boek
```

Expected frame graph output:

```text
Frame graph: 2 frames, 5 slots; frame selection: 1 selected, 0 fail; selected frame validation: 2 ok, 1 fail (file: 04-frame-selection-role-not-licensed.opn; lexical role THEME is not licensed by selected frame BIJTEN)
```

### 05-frame-selection-selected-frame-not-in-frame-graph.opn

Target condition:

```text
verbal Lexicon row selects a frame that is absent from FRAME_GRAPH
```

Expected diagnostic:

```text
selected frame ZIEN is not present in FRAME_GRAPH
```

Expected frame graph output starts with:

```text
Frame graph: 2 frames, 5 slots; frame selection: 1 selected, 1 fail
```

## Future pass condition

When implemented in the next Java behavior slice, the checker should include unchanged V3 core, V4.1 NEG/TIME/PLACE, V4.3 WH, V4.5 DET, V4.9 FRAME.graph, V4.12 Lexicon, V4.16 morphology and the new frame-selection examples.

Proposed future result:

```text
Mapping V4.19 explicit frame-selection validator regression checker: pass
```

Exact pass/fail counts should be fixed only when the `.opn` examples and checker cases are added.

## Current V4.18 pass condition

Because V4.18 is documentation-only, actual checks remain:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
