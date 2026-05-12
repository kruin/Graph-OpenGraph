# Mapping V4.9 expected-output manifest — FRAME.graph minimal slot validator

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
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)

02-frame-geven-explicit-mapping.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw heeft man boek gegeven
expected frame graph: Frame graph: 1 frames, 3 slots; frame validation: 3 ok, 0 fail (frame slots satisfied)
```

## Invalid FRAME.graph examples

Directory:

```text
examples/opn/mapping-v4-frame-invalid/
```

These examples keep Mapping V4 placement validation valid. Frame diagnostics are reported separately, and generated output is not suppressed.

Expected:

```text
01-frame-missing-required-slot.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 1 ok, 1 fail (file: 01-frame-missing-required-slot.opn; missing required frame slot Patiens for frame BIJTEN)

02-frame-unknown-slot-role.opn
expected validation starts with: validation: 1 ok, 0 fail
expected generated: generated: best: bijt
expected frame graph: Frame graph: 1 frames, 1 slots; frame validation: 0 ok, 1 fail (file: 02-frame-unknown-slot-role.opn; unknown frame slot role LOC for frame BIJTEN)

03-frame-role-not-licensed.opn
expected validation starts with: validation: 3 ok, 0 fail
expected generated: generated: best: vrouw bijt hond daar
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 1 fail (file: 03-frame-role-not-licensed.opn; lexical role PLACE is not licensed by frame BIJTEN)

04-frame-malformed-row.opn
expected validation starts with: validation: 1 ok, 0 fail
expected generated: generated: best: bijt
expected frame graph: Frame graph: 1 frames, 0 slots; frame validation: 0 ok, 1 fail (file: 04-frame-malformed-row.opn; malformed FRAME_GRAPH row)
```

## Pass condition

```text
Mapping V4.9 FRAME.graph minimal slot validator regression checker: 37 pass, 0 fail
```
