# Mapping V4.6 — FRAME.graph scope

Status:

```text
MAPPING_V4_6_FRAME_GRAPH_SCOPE
```

## Purpose

V4.6 defines the role of `FRAME.graph` before any runtime implementation.

This phase is documentation and expected-output scope only. It does not parse, validate, generate from, render or mutate graph content based on `FRAME.graph`.

## Base

```text
Mapping_V4-26-05-01--v452-stable-handoff.zip
```

Preserved stable base:

```text
MAPPING_V4_5_2_STABLE_HANDOFF
Runtime behavior: MAPPING_V4_5_1_INFO_LABEL
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Core decision

`FRAME.graph` is not the generator.

`FRAME.graph` is a later semantic/frame context layer next to the existing explicit lexical-axis mapping.

The Mapping architecture remains:

```text
STRUCTURE = view
MAPPING_V4 = explicit lexical-axis mapping
FRAME.graph = future semantic/frame context
no graph mutation
no tree transformation / vooropplaatsing
output via generator
validation and generation via the lexical axis
```

## Meaning of FRAME.graph

A `FRAME.graph` describes a semantic frame and its slots.

Example frames:

```text
BIJTEN:
  required slots:
    Agens
    Patiens

GEVEN:
  required slots:
    Agens
    RECIPIENT
    THEME
```

In later phases, this can support checks such as:

- whether a frame declares all required slots;
- whether lexical roles used in `MAPPING_V4` are licensed by the selected frame;
- whether required frame slots are filled by lexical items;
- whether a verb-domain can be associated with a frame.

V4.6 does not implement those checks.

## Relationship to STRUCTURE

`STRUCTURE` remains a drawn view.

For V4.6, `FRAME.graph` does not change the drawn structure. It is not projected into the editor, not rendered as new nodes and not used to reposition existing nodes.

## Relationship to MAPPING_V4

`MAPPING_V4` remains explicit.

A V4.5-style mapping remains valid without `FRAME.graph`:

```text
x1|vrouw|role:Agens
x2|bijt|role:V
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens

Agens|left_of|V|core
Patiens|right_of|V|core
DET|before|Patiens|core
```

Expected generated best remains:

```text
vrouw bijt de hond
```

A later `FRAME.graph` layer may say that the verb belongs to frame `BIJTEN` and that `Agens` and `Patiens` are licensed slots, but this phase does not execute that logic.

## Proposed future OPN section shape

Documentation-only proposal:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:
```

Alternative spellings and the final parser form are not fixed in V4.6.

Rules for a later implementation phase:

- keep the section pipe-safe;
- keep it separate from `STRUCTURE_NODES` and `STRUCTURE_EDGES`;
- do not draw frame metadata as graph nodes;
- do not infer lexical roles unless a later lexicon phase explicitly defines that behavior.

## In scope for V4.6

- Define `FRAME.graph` as semantic/frame context.
- Define the boundary between `STRUCTURE`, `MAPPING_V4` and `FRAME.graph`.
- Define proposed future frame-slot examples.
- Define future valid and invalid behavior targets.
- Keep runtime behavior unchanged.
- Keep all Markdown under `md/`.

## Out of scope for V4.6

```text
Java source changes
.class rebuilds
jar rebuilds
FRAME_GRAPH parser
FRAME.graph validator
FRAME.graph generator behavior
FRAME.graph rendering
checker expansion
lexicon / automatic role inference
verb-frame inference
role inference from node labels
graph mutation
tree transformation / vooropplaatsing
UI/view options
adjective placement
complex NP structure
relative clauses
multiple DETs targeting the same nominal role
```

## Later implementation candidates

A later behavior phase may add one small slice at a time:

1. Parse and count a `FRAME_GRAPH` section as metadata only.
2. Report frame metadata in the Info window.
3. Validate that frame slots and lexical roles are compatible.
4. Add expected-output / expected-fail checks for frame-slot diagnostics.
5. Only after that, connect a lexicon layer to frame selection.

These are not V4.6 behavior.

## Expected checks

Because V4.6 is documentation-only:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
