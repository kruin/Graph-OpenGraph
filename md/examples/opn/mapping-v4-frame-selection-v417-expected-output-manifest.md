# Mapping V4.17 expected-output manifest — explicit frame-selection scope

Status:

```text
documentation-only target manifest
runtime remains V4.16
```

## Purpose

This manifest defines target output for later explicit frame-selection validation.

V4.17 itself does not add runtime examples or checker cases.

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

Lexicon selector target:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
```

Frame inventory target:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

Expected future frame-selection summary:

```text
frame selection: 1 selected, 0 fail
```

Expected future selected-frame validation summary:

```text
selected frame validation: 2 ok, 0 fail
```

Generated best remains derived from `MAPPING_V4` placement rules:

```text
generated: best: vrouw bijt de hond
```

### 02-frame-selection-geven-vpart-from-multiframe-inventory.opn

Purpose:

```text
explicitly select GEVEN from a V-PART Lexicon row
```

Lexicon selector target:

```text
lex|geven|lemma:geven|form:gegeven|role:V-PART|frame:GEVEN|pos:VPP
```

Expected future frame-selection summary:

```text
frame selection: 1 selected, 0 fail
```

Expected future selected-frame validation summary:

```text
selected frame validation: 3 ok, 0 fail
```

Generated best remains:

```text
generated: best: vrouw heeft man boek gegeven
```

## Future invalid examples

Future directory:

```text
examples/opn/mapping-v4-frame-selection-invalid/
```

These examples should keep Mapping V4 placement validation valid. Frame-selection diagnostics should be reported separately, and generated output should not be suppressed in the first diagnostic slice.

### 01-frame-selection-missing-selected-frame.opn

Target condition:

```text
FRAME_GRAPH contains multiple frames, but no verbal Lexicon row contains frame:<name>
```

Expected future diagnostic:

```text
missing selected frame for multi-frame FRAME_GRAPH
```

Expected future summary:

```text
frame selection: 0 selected, 1 fail
```

### 02-frame-selection-non-verbal-selector.opn

Target condition:

```text
frame:<name> appears on a non-verbal Lexicon row
```

Expected future diagnostic:

```text
selected frame BIJTEN is attached to non-verbal lexicon entry hond
```

Expected future summary:

```text
frame selection: 0 selected, 1 fail
```

### 03-frame-selection-multiple-selected-frames.opn

Target condition:

```text
more than one verbal Lexicon row selects a different frame
```

Expected future diagnostic:

```text
multiple selected frames: BIJTEN, GEVEN
```

Expected future summary:

```text
frame selection: 2 selected, 1 fail
```

### 04-frame-selection-role-not-licensed.opn

Target condition:

```text
selected frame BIJTEN, but explicit MAPPING_V4 contains a semantic role not licensed by BIJTEN
```

Expected future diagnostic:

```text
lexical role THEME is not licensed by selected frame BIJTEN
```

Expected future selected-frame validation summary:

```text
selected frame validation: 2 ok, 1 fail
```

## Future pass condition

Exact pass/fail counts should be fixed only when the Java behavior, example files and checker cases are added.

Proposed future result:

```text
Mapping V4.19 explicit frame-selection validator regression checker: pass
```

## Current V4.17 pass condition

Because V4.17 is documentation-only, the actual checks remain:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
