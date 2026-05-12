# Mapping V4.14 — morphology metadata validation scope

Status:

```text
MAPPING_V4_14_MORPHOLOGY_METADATA_VALIDATION_SCOPE
```

## Purpose

V4.14 is a documentation-only scope phase after V4.13.

It narrows the next morphology step to metadata validation only. It does not implement runtime morphology parsing or validation.

## Base

```text
MAPPING_V4_13_LEXICON_MORPHOLOGY_FRAME_SELECTION_SCOPE
```

Base runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## V4.14 runtime change

```text
none
```

## Scope decision

The first morphology behavior slice should validate only explicit metadata fields that are already present in `LEXICON` rows.

It must not infer or generate a surface form.

Accepted future morphology metadata keys are reserved as:

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

The future validator may check these fields only as metadata attached to a Lexicon entry, for example:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
```

## Reserved future diagnostics

The first future validator may report:

```text
unknown morphology feature
missing morphology feature value
duplicate morphology feature key
morphology feature incompatible with pos
unknown morphology feature value
```

These diagnostics must remain informational in the first behavior slice.

## Explicit form remains authoritative

The `form:<surface>` field remains the surface form used by explicit `MAPPING_V4` lexical items.

Future morphology validation must not silently replace:

```text
form:bijt
```

with a generated or inferred alternative.

## Compatibility with current Lexicon validator

The current V4.12 Lexicon validator validates basic Lexicon row structure and role/frame consistency.

V4.14 does not change that validator.

A later morphology validator should be layered after basic Lexicon validation and should not change generated output.

## Explicitly not included

```text
runtime morphology validation
automatic inflection
surface-form generation
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

Because V4.14 is documentation-only, runtime checks remain the V4.12 status:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
