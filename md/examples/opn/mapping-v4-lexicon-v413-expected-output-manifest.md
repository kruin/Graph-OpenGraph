# Mapping V4.13 expected-output manifest — Lexicon / morphology / frame-selection scope

Status:

```text
documentation-only target manifest
runtime remains V4.12
```

## Current behavior preserved

V4.13 does not change current valid Lexicon examples.

Directory:

```text
examples/opn/mapping-v4-lexicon/
```

Expected runtime behavior remains the V4.12 expectation:

```text
Lexicon: <n> entries; lexicon validation: <ok> ok, 0 fail
```

Invalid Lexicon examples also remain V4.12 informational diagnostics only:

```text
examples/opn/mapping-v4-lexicon-invalid/
```

Lexicon validation failures do not suppress generated output.

## Reserved target behavior for later morphology phases

Candidate future examples may use fields such as:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3
```

Potential future diagnostics, not implemented in V4.13:

```text
unknown morphology feature
missing morphology feature
morphology feature incompatible with pos
duplicate morphology feature key
```

## Reserved target behavior for later frame-selection phases

Candidate future examples may use explicit frame-selection metadata. V4.13 does not define a runtime syntax beyond the existing `frame:<name>` Lexicon field.

Potential future diagnostics, not implemented in V4.13:

```text
ambiguous lexicon frame selection
lexicon frame incompatible with explicit Mapping V4 verb domain
selected frame not present in FRAME_GRAPH
selected frame does not license explicit lexical role
```

## V4.13 pass condition

Because V4.13 is documentation-only, the actual pass condition remains:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Out of scope

```text
runtime morphology validation
automatic inflection
generation from LEXICON
generation from FRAME_GRAPH
role inference
automatic lexical insertion
automatic frame selection
UI rendering of Lexicon entries
graph mutation
```
