# Mapping V4.12 expected-output manifest — Lexicon validator

## Valid examples

Directory:

```text
examples/opn/mapping-v4-lexicon/
```

Expected:

```text
01-lexicon-bijten-explicit-mapping.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt de hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 4 entries; lexicon validation: 4 ok, 0 fail

02-lexicon-geven-explicit-mapping.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw heeft man boek gegeven
expected frame graph: Frame graph: 1 frames, 3 slots; frame validation: 3 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 5 entries; lexicon validation: 5 ok, 0 fail
```

## Invalid Lexicon examples

Directory:

```text
examples/opn/mapping-v4-lexicon-invalid/
```

These examples keep Mapping V4 placement validation valid. Lexicon diagnostics are reported separately, and generated output is not suppressed.

Expected:

```text
01-lexicon-malformed-row.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 01-lexicon-malformed-row.opn; malformed LEXICON row)

02-lexicon-missing-key.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 02-lexicon-missing-key.opn; missing lexical key)

03-lexicon-duplicate-key.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 2 entries; lexicon validation: 1 ok, 1 fail (file: 03-lexicon-duplicate-key.opn; duplicate lexical key vrouw)

04-lexicon-unknown-role.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 04-lexicon-unknown-role.opn; unknown lexical role LOC for lexicon key loc)

05-lexicon-role-not-in-mapping.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 05-lexicon-role-not-in-mapping.opn; lexical role PLACE is not present in explicit MAPPING_V4 items)

06-lexicon-frame-not-in-frame-graph.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 06-lexicon-frame-not-in-frame-graph.opn; lexicon frame LOPEN is not present in FRAME_GRAPH)
```

## Pass condition

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
```
