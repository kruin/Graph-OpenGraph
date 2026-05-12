# Mapping V4.8 expected-output manifest — FRAME.graph slot validation scope

Status: documentation-only expected-output manifest.

This manifest defines target behavior for a later FRAME.graph slot-validation implementation. V4.8 does not implement or run these checks.

## Base behavior preserved from V4.7

Current valid FRAME.graph metadata examples remain runtime examples:

```text
examples/opn/mapping-v4-frame/01-frame-bijten-explicit-mapping.opn
examples/opn/mapping-v4-frame/02-frame-geven-explicit-mapping.opn
```

Current V4.7 behavior remains:

```text
FRAME_GRAPH metadata read/count
Info summary: Frame graph: ... metadata only
generated best unchanged
```

## Future valid examples for V4.9

### 01-frame-bijten-slots-valid.opn

Purpose:

```text
BIJTEN frame declares Agens and Patiens; explicit MAPPING_V4 fills both roles.
```

Future expected frame validation:

```text
frame validation: 2 ok, 0 fail
```

Future expected generated best remains:

```text
generated best: vrouw bijt de hond
```

### 02-frame-geven-slots-valid.opn

Purpose:

```text
GEVEN frame declares Agens, RECIPIENT and THEME; explicit MAPPING_V4 fills all three roles.
```

Future expected frame validation:

```text
frame validation: 3 ok, 0 fail
```

Future expected generated best remains:

```text
generated best: vrouw heeft man boek gegeven
```

## Future invalid examples for V4.9

These are documentation-only targets in V4.8.

### 01-frame-missing-required-slot.opn

Expected future diagnostic:

```text
missing required frame slot Patiens for frame BIJTEN
```

### 02-frame-unknown-slot-role.opn

Expected future diagnostic:

```text
unknown frame slot role LOC for frame BIJTEN
```

### 03-frame-role-not-licensed.opn

Expected future diagnostic:

```text
lexical role PLACE is not licensed by frame BIJTEN
```

### 04-frame-malformed-row.opn

Expected future diagnostic:

```text
malformed FRAME_GRAPH row
```

## V4.8 pass condition

Because V4.8 is documentation-only, the actual pass condition remains:

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Scope boundary

V4.8 does not add:

```text
runtime frame-slot validation
invalid frame examples
checker expansion
generated-output changes
graph mutation
FRAME.graph rendering
role inference
lexicon lookup
```
