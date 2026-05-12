# Mapping V4.18 — explicit frame-selection validator target

Status:

```text
MAPPING_V4_18_FRAME_SELECTION_VALIDATOR_TARGET
```

## Purpose

V4.18 is a documentation-only implementation target after V4.17.

It fixes the exact target boundary for the next Java behavior slice: a minimal explicit frame-selection validator that uses existing `LEXICON` metadata and validates selected frames against `FRAME_GRAPH`.

V4.18 does not change runtime behavior.

## Base

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
MAPPING_V4_17_FRAME_SELECTION_SCOPE
```

V4.17 established the scope rule:

```text
FRAME_GRAPH = available frame inventory / semantic context
LEXICON frame:<name> = explicit selected-frame source
MAPPING_V4 = authoritative lexical-axis mapping and generated-output source
```

## V4.18 runtime change

```text
none
```

Runtime behavior remains:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

## Target selector source for V4.19

The next Java slice should read selected frames from explicit Lexicon metadata:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|geven|lemma:geven|form:gegeven|role:V-PART|frame:GEVEN|pos:VPP
```

Accepted first-slice selector roles:

```text
V
V-AUX
V-PART
```

A `frame:<name>` field on these roles is an explicit selected-frame declaration. It is not inferred from word form, lemma, surface position or `FRAME_GRAPH` contents.

## Target frame inventory rule

`FRAME_GRAPH` may contain one or more frame definitions:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

When an explicit selected frame is present, selected-frame validation should validate only the selected frame or selected frames.

Unselected frames remain inventory/context. They must not by themselves trigger missing-slot or role-licensing failures.

## Target validation outputs

The next Java slice should preserve the existing frame graph count and add two selected-frame summaries to the Frame graph Info line.

Target form:

```text
Frame graph: <frames> frames, <slots> slots; frame selection: <selected> selected, <fail> fail; selected frame validation: <ok> ok, <fail> fail (<details>)
```

The existing V4.9 form remains acceptable only for examples without a selected-frame condition:

```text
Frame graph: <frames> frames, <slots> slots; frame validation: <ok> ok, <fail> fail (<details>)
```

## Target selection algorithm

For the first behavior slice:

1. Read all `LEXICON` rows.
2. Collect `frame:<name>` values from rows whose canonical role is `V`, `V-AUX` or `V-PART`.
3. Count distinct selected frames.
4. Report selection diagnostics before selected-frame slot validation.
5. If selection has one or more fatal selection failures, report selected-frame validation as not run or `0 ok, 0 fail` according to the checker target fixed in the implementation slice.
6. If no frame is selected and `FRAME_GRAPH` contains exactly one frame, preserve the current V4.9 one-frame validation behavior.
7. If no frame is selected and `FRAME_GRAPH` contains multiple frames, report a missing selected-frame diagnostic.

## Target selected-frame slot validation

For each valid selected frame:

- required selected-frame slots must be present as explicit `MAPPING_V4` lexical roles
- lexical semantic roles in explicit `MAPPING_V4` must be licensed by the selected frame
- validation uses selected frame slots only
- unselected frame slots are ignored for slot satisfaction and licensing

First-slice semantic slot roles remain:

```text
Agens
Patiens
RECIPIENT
THEME
```

Lexical roles checked for selected-frame licensing remain:

```text
Agens
Patiens
RECIPIENT
THEME
TIME
PLACE
```

`TIME` and `PLACE` can still produce not-licensed diagnostics when the selected frame does not license them.

## Target diagnostics for V4.19

Exact diagnostic strings for the next behavior slice should be:

```text
selected frame <frame> is not present in FRAME_GRAPH
missing selected frame for multi-frame FRAME_GRAPH
multiple selected frames: <frames>
selected frame <frame> is attached to non-verbal lexicon entry <key>
missing required selected-frame slot <role> for frame <frame>
lexical role <role> is not licensed by selected frame <frame>
```

The `<frames>` list should be stable and comma-space separated in lexical-row encounter order after de-duplication:

```text
BIJTEN, GEVEN
```

## Informational-only boundary

In the first Java behavior slice, frame-selection diagnostics should remain informational like FRAME.graph, Lexicon and morphology diagnostics.

They must not suppress generated output when the `MAPPING_V4` placement validation itself is valid.

Generated output remains derived from explicit `MAPPING_V4` placement rules.

## Preserved boundaries

V4.18 and the intended V4.19 behavior do not add:

```text
automatic frame selection
role inference
automatic lexical insertion
generation from LEXICON
generation from FRAME_GRAPH
surface-form generation
automatic inflection
graph mutation
FRAME.graph rendering
STRUCTURE mutation
MAPPING_V4 mutation
placement-rule changes
```

## Target examples

The target expected-output manifest is:

```text
md/examples/opn/mapping-v4-frame-selection-v418-expected-output-manifest.md
```

V4.18 does not add runtime `.opn` examples. It fixes the expected-output target for the next behavior slice.

## Actual checks

Because V4.18 is documentation-only, runtime checks remain the V4.16 checks:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
