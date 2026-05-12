# Mapping V4.12 — Lexicon validator

Status:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Purpose

V4.12 implements the first minimal `LEXICON` validation slice defined in V4.11.

The validator is diagnostic only. It checks Lexicon metadata against explicit `MAPPING_V4` lexical items and, when present, `FRAME_GRAPH` frame names. It does not generate from Lexicon data.

## Runtime behavior

A Lexicon section is still written as pipe-delimited rows:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|vrouw|lemma:vrouw|form:vrouw|role:Agens|pos:N
END_LEXICON:
```

The Info window now reports validation:

```text
OPN Lexicon: 4 entries; lexicon validation: 4 ok, 0 fail
```

For invalid Lexicon metadata, the Info window keeps the Mapping output intact and reports compact diagnostics on the Lexicon line, for example:

```text
OPN Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 04-lexicon-unknown-role.opn; unknown lexical role LOC for lexicon key loc)
```

## Validator scope

V4.12 validates:

```text
malformed LEXICON row
missing lexical key
missing lemma
missing form
duplicate lexical key
unknown lexical role
lexical role not present in explicit MAPPING_V4 items
lexicon frame reference not present in FRAME_GRAPH, only when FRAME_GRAPH is present
```

A lexicon row passes only when its key, lemma, form, role and optional frame reference are valid for this first slice.

## Coupling rule

Lexicon coupling is informational only.

Allowed:

```text
Info diagnostics
checker diagnostics
valid and invalid Lexicon examples
```

Not allowed:

```text
generated-output changes
role inference
automatic lexical insertion
automatic frame selection
morphology / inflection
graph mutation
rendering of lexical entries
suppression of generated output because of Lexicon diagnostics
```

## Examples

Valid examples remain under:

```text
examples/opn/mapping-v4-lexicon/
```

Invalid examples are under:

```text
examples/opn/mapping-v4-lexicon-invalid/
```

The invalid examples keep Mapping V4 placement validation valid. Lexicon diagnostics are checked separately and generated output is not suppressed.

## Checks

Actual checks:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
