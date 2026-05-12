# Mapping V4.10 — Lexicon metadata read / count

Status:

```text
MAPPING_V4_10_LEXICON_METADATA
```

## Purpose

V4.10 adds the first runtime-visible lexicon layer.

The lexicon layer is metadata only in this phase. It lets an `.opn` file carry lexical entries next to `MAPPING_V4` and `FRAME_GRAPH`, without changing generated output, role validation, graph rendering or graph structure.

## Architecture boundary

```text
STRUCTURE = drawn view
MAPPING_V4 = explicit lexical-axis mapping and placement rules
FRAME_GRAPH = semantic/frame metadata plus frame-slot validation diagnostics
LEXICON = lexical metadata read/count only
```

V4.10 does not infer roles from the lexicon. The generator still uses explicit lexical items and explicit placement rules from `MAPPING_V4`.

## Recognized section

V4.10 recognizes this metadata section:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|vrouw|lemma:vrouw|form:vrouw|role:Agens|pos:N
END_LEXICON:
```

Rows are counted when they are pipe-delimited rows inside `LEXICON:` / `END_LEXICON:`. Field contents are not validated in this phase.

## Info-window behavior

The Info window reports the lexicon count as a separate line:

```text
OPN Lexicon: 4 entries, metadata only
```

If the same `.opn` also has Mapping and FRAME.graph metadata, the Info window reports all layers separately:

```text
OPN Mapping: ...
OPN Frame graph: ...
OPN Lexicon: ...
```

## Added examples

Valid examples are added under:

```text
examples/opn/mapping-v4-lexicon/
```

They preserve generated output from the explicit `MAPPING_V4` placement rules while adding a `LEXICON` metadata section.

## Preserved behavior

V4.10 preserves:

```text
generated output from MAPPING_V4
Mapping V4 placement validation
FRAME.graph slot validation
graph rendering
graph mutation boundary
tree transformation boundary
```

## Out of scope

Not included:

```text
lexicon validation
role inference from lexical entries
automatic frame selection
verb-to-frame binding enforcement
morphology or inflection
language-wide lexicon lookup
generated-output changes
generated-output suppression on lexicon diagnostics
UI rendering of lexical entries
graph mutation
```

## Checker

The V4 checker now covers:

- V3 core valid + invalid: 13 checks
- V4.1 NEG/TIME/PLACE valid + invalid: 6 checks
- V4.3 WH valid + invalid: 5 checks
- V4.5 DET valid + invalid: 7 checks
- V4.9 FRAME.graph slot validation valid + invalid: 6 checks
- V4.10 Lexicon metadata valid: 2 checks

Expected result:

```text
Mapping V4.10 lexicon metadata regression checker
summary: 39 pass, 0 fail
```
