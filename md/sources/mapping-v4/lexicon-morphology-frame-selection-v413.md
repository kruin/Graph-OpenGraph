# Mapping V4.13 — Lexicon / morphology / frame-selection scope

Status:

```text
MAPPING_V4_13_LEXICON_MORPHOLOGY_FRAME_SELECTION_SCOPE
```

## Purpose

V4.13 is a documentation-only scope phase after V4.12.

It records the boundary for later Lexicon, morphology and frame-selection work without changing runtime behavior.

## Base

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

V4.12 already provides:

- `LEXICON:` metadata read/count
- Lexicon validation diagnostics
- optional validation of `frame:<name>` references against `FRAME_GRAPH`
- generated output still based on explicit `MAPPING_V4` placement rules
- informational Lexicon diagnostics only

## V4.13 runtime change

```text
none
```

Runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Scope decisions

### Lexicon remains metadata/context

`LEXICON` is still not the generator.

It may later support checks or suggestions, but it does not currently:

- infer roles
- insert missing lexical items
- select frames automatically
- inflect words
- render entries as graph nodes
- mutate `STRUCTURE`
- mutate `MAPPING_V4`

### Explicit lexical-axis mapping remains authoritative

The generator continues to use explicit `MAPPING_V4` lexical items and placement rules.

Lexicon entries may describe possible words, forms, roles and frames, but they do not override the explicit lexical-axis sequence.

### Morphology is reserved, not implemented

V4.13 reserves future morphology metadata fields, for example:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3
```

Reserved future checks may include:

- known morphology feature names
- duplicate feature keys
- feature compatibility with `pos`
- required feature presence for a later morphology slice

V4.13 does not implement these checks.

### Form remains explicit

The field `form:<surface>` remains the visible lexical form supplied by the OPN source.

Future morphology work must not silently replace it with an inferred form unless a later behavior phase explicitly defines that rule.

### Frame selection is reserved, not implemented

`frame:<name>` in a Lexicon row is currently only validated against `FRAME_GRAPH` when `FRAME_GRAPH` is present.

Future frame-selection work may define how a lexical entry can be associated with a selected frame, but V4.13 does not add:

- automatic frame selection
- frame-driven role inference
- generation from `FRAME_GRAPH`
- generation from `LEXICON`
- fallback frame selection
- semantic disambiguation

## Future behavior candidates

Later phases may be split into small slices such as:

1. morphology metadata validation
2. Lexicon-to-Mapping consistency checks beyond role presence
3. explicit frame-selection metadata validation
4. optional user-visible diagnostics for ambiguous Lexicon/frame combinations

These candidates are not part of V4.13 runtime behavior.

## Preserved

```text
Java source unchanged
class files unchanged
jar files unchanged
Mapping V4 parser unchanged
Mapping V4 generator unchanged
Mapping V4 placement validator unchanged
FRAME.graph metadata/slot validation unchanged
Lexicon runtime validator unchanged
OPN example semantics unchanged
graph rendering unchanged
graph mutation unchanged
```

## Actual checks

Because V4.13 is documentation-only, the runtime check status remains the V4.12 status:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
