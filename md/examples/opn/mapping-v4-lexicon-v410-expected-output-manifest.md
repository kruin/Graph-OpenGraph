# Mapping V4.10 expected-output manifest — Lexicon metadata read / count

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
expected lexicon: Lexicon: 4 entries, metadata only

02-lexicon-geven-explicit-mapping.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw heeft man boek gegeven
expected frame graph: Frame graph: 1 frames, 3 slots; frame validation: 3 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 5 entries, metadata only
```

## Pass condition

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
```

## Scope boundary

The Lexicon examples check metadata read/count only.

They do not introduce:

```text
lexicon validation
role inference
generation from LEXICON
automatic frame selection
morphology
lexicon rendering
graph mutation
```
