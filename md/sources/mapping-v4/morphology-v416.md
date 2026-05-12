# Mapping V4.16 — minimal morphology metadata validator

Status:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

## Purpose

V4.16 implements the first runtime morphology metadata validator that was fixed as the V4.15 target.

The validator is diagnostic only. It inspects explicit morphology fields on `LEXICON` rows and appends a morphology summary to the existing Lexicon Info line. It does not generate, infer, select or mutate anything.

## Base

```text
Mapping_V4-26-05-02--v415-morphology-metadata-validator-target-full-from-v412-slim.zip
```

## Runtime behavior

A Lexicon row may contain morphology metadata:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
END_LEXICON:
```

The Info window reports morphology validation after Lexicon validation when morphology metadata is present:

```text
OPN Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 1 ok, 0 fail
```

If no morphology fields or morphology diagnostics are present, the V4.12 Lexicon summary remains unchanged.

## Accepted morphology metadata keys

```text
tense
number
person
gender
case
mood
aspect
finite
```

## Accepted value domain

```text
tense: present, past
number: sg, pl
gender: common, neuter, masc, fem
case: nom, acc, dat, gen
mood: indicative, imperative, subjunctive
aspect: simple, perfect, progressive
finite: true, false
person: 1, 2, 3
```

## Compatibility rules

The validator checks a conservative first-slice compatibility boundary with `pos`:

```text
finite applies to verbal pos values
person applies to verbal pos values or pos:PRON
case applies to pos:N, pos:PRON, pos:DET or pos:ADJ
tense, mood and aspect apply to verbal pos values
gender applies to pos:N, pos:PRON, pos:DET or pos:ADJ
number applies to verbal pos values and to pos:N, pos:PRON, pos:DET or pos:ADJ
```

Verbal `pos` values are accepted when they start with `V`, so `pos:V`, `pos:VFIN` and `pos:VPP` are treated as verbal for this metadata check.

A missing `pos` is only a morphology failure when a morphology feature needs `pos` for compatibility.

## Diagnostics

V4.16 reports these morphology diagnostics:

```text
unknown morphology feature <feature> at lexicon entry <key>
missing morphology feature value for <feature> at lexicon entry <key>
duplicate morphology feature <feature> at lexicon entry <key>
morphology feature <feature> incompatible with pos:<pos> at lexicon entry <key>
unknown morphology feature value <value> for <feature> at lexicon entry <key>
```

Diagnostics are informational and do not suppress generated output.

## Counting rule

Morphology validation counts Lexicon entries that contain morphology metadata.

```text
morphology validation: <entries-with-valid-morphology> ok, <entries-with-invalid-morphology> fail
```

Lexicon rows without morphology fields are not counted as morphology passes or failures.

## Generated output boundary

Generated output remains derived from explicit `MAPPING_V4` lexical-axis placement rules.

The morphology validator does not:

```text
replace form:<surface>
generate surface forms
inflect words
infer roles
insert lexical items
select frames
change placement rules
suppress generated output
mutate the graph
render Lexicon entries
```

## Examples

Valid examples are under:

```text
examples/opn/mapping-v4-morphology/
```

Invalid examples are under:

```text
examples/opn/mapping-v4-morphology-invalid/
```

The invalid examples keep Mapping V4 placement validation valid. Morphology diagnostics are checked separately and generated output remains available.

## Checks

Actual checks:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
