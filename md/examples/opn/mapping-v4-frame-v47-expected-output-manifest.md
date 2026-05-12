# Mapping V4.7 expected-output manifest — FRAME.graph metadata read / count

## Valid examples

Directory:

```text
examples/opn/mapping-v4-frame/
```

Expected:

```text
01-frame-bijten-explicit-mapping.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt de hond
expected frame graph: Frame graph: 1 frames, 2 slots, metadata only

02-frame-geven-explicit-mapping.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw heeft man boek gegeven
expected frame graph: Frame graph: 1 frames, 3 slots, metadata only
```

## Pass condition

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
```

The checker total consists of:

- V3 core valid + invalid: 13 checks
- V4.1 NEG/TIME/PLACE valid + invalid: 6 checks
- V4.3 WH valid + invalid: 5 checks
- V4.5 DET valid + invalid: 7 checks
- V4.7 FRAME.graph metadata valid: 2 checks

## Scope boundary

The FRAME.graph examples check metadata read/count only.

They do not introduce:

```text
frame-slot validation
role inference
generation from FRAME_GRAPH
FRAME.graph rendering
graph mutation
```
