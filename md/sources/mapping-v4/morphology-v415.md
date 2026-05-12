# Mapping V4.15 — morphology metadata validator target

Status:

```text
MAPPING_V4_15_MORPHOLOGY_METADATA_VALIDATOR_TARGET
```

## Purpose

V4.15 is a documentation-only implementation target after V4.14.

V4.14 reserved the morphology boundary. V4.15 fixes the exact target behavior for a later Java behavior slice, but does not implement runtime parsing or validation in this package.

## Base

This package is rebuilt from the full V4.12 slim runtime package and carries forward the V4.13 and V4.14 documentation-only phases.

Runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## V4.15 runtime change

```text
none
```

## Target behavior for the next Java slice

A later behavior package may add a morphology metadata validator that runs after the current Lexicon validator.

The validator should inspect morphology fields only on `LEXICON` rows:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
END_LEXICON:
```

It should report a separate Info-window diagnostic layer, for example:

```text
Lexicon: 4 entries; lexicon validation: 4 ok, 0 fail; morphology validation: 4 ok, 0 fail
```

## Accepted morphology metadata keys

The first validator target recognizes only these keys:

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

## Target value domain

The first target validator may use this minimal value domain:

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

The value domain is intentionally small. It is a metadata validator, not a grammar or inflection engine.

## Target diagnostics

The next Java behavior slice may report:

```text
unknown morphology feature
missing morphology feature value
duplicate morphology feature key
morphology feature incompatible with pos
unknown morphology feature value
```

Diagnostics must be informational in the first behavior slice.

## Minimal compatibility rules

The first target validator should keep compatibility rules deliberately conservative:

```text
finite only applies to pos:V
person only applies to pos:V or pos:PRON
case only applies to pos:N, pos:PRON, pos:DET or pos:ADJ
tense, mood and aspect only apply to pos:V
gender and number may apply to pos:N, pos:PRON, pos:DET or pos:ADJ
```

A missing `pos` value should not itself be a morphology failure unless a morphology feature requires `pos` to determine compatibility. Basic Lexicon row validity remains the responsibility of the V4.12 Lexicon validator.

## Explicit form remains authoritative

The surface form remains explicit:

```text
form:<surface>
```

The morphology validator must not replace, infer or generate this surface form.

For example, this remains a metadata diagnostic case only:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:past|number:sg|person:3|finite:true
```

The first validator may report a suspicious value only if the value is outside the allowed domain. It must not know that `bijt` is not past tense.

## Generated output boundary

Generated output remains derived from explicit `MAPPING_V4` lexical-axis placement rules.

The `LEXICON` section and morphology features do not:

```text
insert lexical items
select frames
select forms
inflect words
change placement rules
suppress generated output
mutate the graph
```

## Explicitly not included

```text
runtime morphology validation in this package
automatic inflection
surface-form generation
lemma-to-form lookup
role inference
automatic lexical insertion
automatic frame selection
generation from LEXICON
generation from FRAME_GRAPH
Lexicon rendering
graph mutation
```

## Preserved

```text
Java source unchanged
class files unchanged
jar files unchanged
Mapping V4 parser unchanged
Mapping V4 generator unchanged
Mapping V4 placement validator unchanged
FRAME.graph metadata/slot validation unchanged
Lexicon validator behavior unchanged
OPN example semantics unchanged
graph rendering unchanged
graph mutation unchanged
```

## Check status

Because V4.15 is documentation-only, runtime checks remain the V4.12 status:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
