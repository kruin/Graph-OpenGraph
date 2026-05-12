# Mapping V4.17 — explicit frame-selection scope

Status:

```text
MAPPING_V4_17_FRAME_SELECTION_SCOPE
```

## Purpose

V4.17 is a documentation-only scope phase after V4.16.

It defines the boundary for the next frame-selection work without changing runtime behavior. The key distinction is:

```text
FRAME_GRAPH = available frame inventory / semantic context
LEXICON frame:<name> = explicit frame selector candidate
MAPPING_V4 = authoritative lexical-axis mapping and generated output source
```

## Base

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

V4.16 already provides:

- `FRAME_GRAPH:` metadata and minimal slot validation
- `LEXICON:` metadata, Lexicon validation and morphology metadata validation
- `frame:<name>` reference checking from Lexicon rows to `FRAME_GRAPH` when `FRAME_GRAPH` is present
- generated output based on explicit `MAPPING_V4` placement rules
- informational diagnostics only for FRAME.graph, Lexicon and morphology layers

## V4.17 runtime change

```text
none
```

Runtime behavior remains:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

## Problem reserved for the next behavior slice

The current FRAME.graph validator is intentionally minimal. It treats `FRAME_GRAPH` as a small context layer and validates required slots against explicit `MAPPING_V4` lexical roles.

That is sufficient for one-frame examples, but it is not yet a true frame-selection model for multi-frame inventories. A later behavior slice may need to validate only the explicitly selected frame or frames, instead of treating every frame in `FRAME_GRAPH` as equally active.

## Proposed explicit selector source

The first frame-selection behavior slice should use existing Lexicon metadata:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|geven|lemma:geven|form:gegeven|role:V-PART|frame:GEVEN|pos:VPP
```

The `frame:<name>` field is explicit source data. It is not inferred.

Candidate frame-bearing lexical roles for the first validator:

```text
V
V-AUX
V-PART
```

The most conservative rule is:

```text
frame:<name> on a verbal Lexicon row selects that frame for frame-slot validation
```

## Target validation boundary for a later behavior phase

A later Java slice may validate:

```text
selected frame exists in FRAME_GRAPH
selected frame has required slots satisfied by explicit MAPPING_V4 lexical roles
explicit lexical semantic roles are licensed by the selected frame, not by every frame in FRAME_GRAPH
multiple selected frames are reported explicitly
selected frame is absent when multiple FRAME_GRAPH frames are present
selected frame is attached to a non-verbal Lexicon row
```

## Diagnostics reserved for a later behavior phase

Candidate diagnostics:

```text
selected frame <frame> is not present in FRAME_GRAPH
missing selected frame for multi-frame FRAME_GRAPH
multiple selected frames: <frames>
selected frame <frame> is attached to non-verbal lexicon entry <key>
missing required selected-frame slot <role> for frame <frame>
lexical role <role> is not licensed by selected frame <frame>
```

Exact wording should be fixed in the next implementation-target phase, not in V4.17.

## Generated output boundary

Frame selection must not become the generator.

Generated output remains derived from explicit `MAPPING_V4` lexical-axis placement rules.

Frame-selection validation must not:

```text
infer lexical roles
insert lexical items
choose words from LEXICON
generate from FRAME_GRAPH
replace form:<surface>
inflect words
change placement rules
suppress generated output in its first diagnostic slice
mutate STRUCTURE
mutate MAPPING_V4
render frames as graph nodes
```

## Multi-frame inventory rule

A future frame-selection validator should allow `FRAME_GRAPH` to contain more than one frame.

For example:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

If the Lexicon explicitly selects `frame:BIJTEN`, the validation target should be `BIJTEN`. The unselected `GEVEN` frame remains available context and should not by itself force missing-slot failures.

## Future behavior candidates

Recommended sequence:

1. V4.18 — frame-selection validator target document.
2. V4.19 — minimal explicit frame-selection validator Java behavior slice.
3. Later — optional diagnostics for ambiguous or competing frame candidates.

## Preserved

```text
Java source unchanged
class files unchanged
jar files unchanged
Mapping V4 parser unchanged
Mapping V4 generator unchanged
Mapping V4 placement validator unchanged
FRAME.graph runtime validator unchanged
Lexicon runtime validator unchanged
Morphology runtime validator unchanged
OPN example semantics unchanged
graph rendering unchanged
graph mutation unchanged
```

## Actual checks

Because V4.17 is documentation-only, the runtime check status remains the V4.16 status:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
