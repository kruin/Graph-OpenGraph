# Mapping V4.14 expected-output manifest — morphology metadata validation scope

Status:

```text
documentation-only target manifest
runtime remains V4.12
```

## Current behavior preserved

V4.14 does not change current valid or invalid Lexicon examples.

Current Lexicon validation remains V4.12 behavior:

```text
Lexicon: <n> entries; lexicon validation: <ok> ok, <fail> fail
```

Lexicon validation failures remain informational and do not suppress generated output.

## Reserved target behavior for a later morphology validator

Candidate future valid Lexicon row:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
```

Candidate future summary:

```text
morphology validation: <ok> ok, 0 fail
```

Candidate future invalid examples:

```text
examples/opn/mapping-v4-morphology-invalid/01-morphology-unknown-feature.opn
examples/opn/mapping-v4-morphology-invalid/02-morphology-missing-feature-value.opn
examples/opn/mapping-v4-morphology-invalid/03-morphology-duplicate-feature-key.opn
examples/opn/mapping-v4-morphology-invalid/04-morphology-feature-incompatible-with-pos.opn
examples/opn/mapping-v4-morphology-invalid/05-morphology-unknown-feature-value.opn
```

Candidate future diagnostics:

```text
unknown morphology feature
missing morphology feature value
duplicate morphology feature key
morphology feature incompatible with pos
unknown morphology feature value
```

## V4.14 pass condition

Because V4.14 is documentation-only, the check status remains:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Out of scope

```text
runtime morphology validation
automatic inflection
surface-form generation
generation from LEXICON
generation from FRAME_GRAPH
role inference
automatic lexical insertion
automatic frame selection
Lexicon rendering
graph mutation
```
