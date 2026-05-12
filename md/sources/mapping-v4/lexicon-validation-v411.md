# Mapping V4.11 — Lexicon validation / coupling scope

Status:

```text
MAPPING_V4_11_LEXICON_VALIDATION_SCOPE
```

## Purpose

V4.11 defines the next lexicon step before implementation.

V4.10 already reads `LEXICON:` metadata and counts entries. V4.11 keeps runtime behavior unchanged and freezes the first minimal validation/coupling boundary for a later behavior phase.

## Current architecture boundary

```text
STRUCTURE = drawn view
MAPPING_V4 = explicit lexical-axis mapping and placement rules
FRAME_GRAPH = semantic/frame metadata plus frame-slot validation diagnostics
LEXICON = lexical metadata layer
```

The generator must still use explicit `MAPPING_V4` lexical items. Lexicon data may be checked against those explicit items, but must not silently create them, move them, infer tree transformations or mutate the graph.

## Candidate syntax already accepted from V4.10

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|vrouw|lemma:vrouw|form:vrouw|role:Agens|pos:N
END_LEXICON:
```

The first minimal validator should continue to treat rows as pipe-delimited lexical entries.

## First validator boundary for the next behavior phase

The intended first behavior phase after this scope may validate only these items:

```text
malformed lexicon row
missing lexical key
missing lemma
missing form
duplicate lexical key
unknown lexical role
lexical role not present in explicit MAPPING_V4 items
lexicon frame reference not present in FRAME_GRAPH, only when FRAME_GRAPH is present
```

## Coupling rule

Coupling is diagnostic only in the first implementation phase.

Allowed:

```text
Info diagnostics
checker diagnostics
expected-fail examples
```

Not allowed:

```text
generated-output changes
role inference
automatic lexical insertion
automatic frame selection
graph mutation
rendering of lexical entries
suppression of generated output because of lexicon diagnostics
```

## Expected Info-line direction

A later implementation can extend the current V4.10 Info line:

```text
OPN Lexicon: 4 entries, metadata only
```

to a validation summary such as:

```text
OPN Lexicon: 4 entries; lexicon validation: 4 ok, 0 fail
```

For invalid files, diagnostics should be compact and not alter the existing generated-output line.

## Example families for the next behavior phase

Valid examples may remain under:

```text
examples/opn/mapping-v4-lexicon/
```

Invalid examples can be added under:

```text
examples/opn/mapping-v4-lexicon-invalid/
```

Expected invalid cases:

```text
01-lexicon-malformed-row.opn
02-lexicon-missing-key.opn
03-lexicon-duplicate-key.opn
04-lexicon-unknown-role.opn
05-lexicon-role-not-in-mapping.opn
06-lexicon-frame-not-in-frame-graph.opn
```

## Preserved in V4.11

V4.11 itself preserves:

```text
V4.10.1 runtime behavior
Mapping V4 generation and validation
FRAME.graph validation
Lexicon read/count only at runtime
graph rendering
graph mutation boundary
```

## Checks

Expected unchanged checks:

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
