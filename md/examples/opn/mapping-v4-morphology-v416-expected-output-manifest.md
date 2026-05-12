# Mapping V4.16 expected-output manifest — minimal morphology metadata validator

## Valid examples

Directory:

```text
examples/opn/mapping-v4-morphology/
```

Expected:

```text
01-morphology-bijten-present-3sg.opn
expected mapping: Mapping v4: 3 lexical items, 1 verb domains, 2 placement rules
expected validation: validation: 2 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 1 ok, 0 fail

02-morphology-nominal-number-gender.opn
expected mapping: Mapping v4: 3 lexical items, 1 verb domains, 2 placement rules
expected validation: validation: 2 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 1 ok, 0 fail

03-morphology-det-number-gender.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt de hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 1 ok, 0 fail
```

## Invalid examples

Directory:

```text
examples/opn/mapping-v4-morphology-invalid/
```

Expected:

```text
01-morphology-unknown-feature.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 0 ok, 1 fail (file: 01-morphology-unknown-feature.opn; unknown morphology feature degree at lexicon entry bijten)

02-morphology-missing-value.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 0 ok, 1 fail (file: 02-morphology-missing-value.opn; missing morphology feature value for tense at lexicon entry bijten)

03-morphology-duplicate-feature.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 0 ok, 1 fail (file: 03-morphology-duplicate-feature.opn; duplicate morphology feature number at lexicon entry hond)

04-morphology-incompatible-pos.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 0 ok, 1 fail (file: 04-morphology-incompatible-pos.opn; morphology feature tense incompatible with pos:N at lexicon entry hond)

05-morphology-unknown-value.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 0 ok, 1 fail (file: 05-morphology-unknown-value.opn; unknown morphology feature value dual for number at lexicon entry hond)
```

## Pass condition

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
```

## Scope boundary

Morphology diagnostics are informational. They do not suppress generated output and do not alter explicit `form:<surface>` values.
