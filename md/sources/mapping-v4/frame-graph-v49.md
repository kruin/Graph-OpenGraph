# Mapping V4.9 — FRAME.graph minimal slot validator

Status:

```text
MAPPING_V4_9_FRAME_GRAPH_MINIMAL_SLOT_VALIDATOR
```

## Purpose

V4.9 implements the first minimal runtime validator for `FRAME_GRAPH` metadata.

It keeps the V4.7/V4.8 architecture boundary:

```text
STRUCTURE = view
MAPPING_V4 = explicit lexical-axis mapping
FRAME_GRAPH = semantic/frame metadata plus validation diagnostics
no graph mutation
no tree transformation / vooropplaatsing
generated output remains derived from MAPPING_V4 placement rules
```

## Base

```text
Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope.zip
```

Preserved base behavior:

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
FRAME_GRAPH metadata read/count
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Implemented behavior

V4.9 validates `FRAME_GRAPH` rows of this shape:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:
```

The Info window now reports both counts and validation:

```text
OPN Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
```

## First-slice slot roles

The first runtime validator accepts these frame slot roles:

```text
Agens
Patiens
RECIPIENT
THEME
```

Rows with other `slot:<role>` values are reported as frame-validation failures.

## Validation checks

Implemented checks:

1. required frame slots must be filled by explicit `MAPPING_V4` lexical roles;
2. frame slot roles must be known first-slice roles;
3. checked lexical semantic roles must be licensed by the frame;
4. malformed `FRAME_GRAPH` rows are reported as metadata diagnostics.

Checked lexical semantic roles are:

```text
Agens
Patiens
RECIPIENT
THEME
TIME
PLACE
```

Verb-domain and clause-mechanics roles remain outside slot licensing:

```text
V
V-AUX
V-PART
DET
WH
NEG
```

## Generated output policy

Frame-validation failures do not suppress generated output in V4.9.

Invalid mapping placement still suppresses generated output as before:

```text
generated: none (invalid mapping)
```

Frame diagnostics are reported separately in the `OPN Frame graph` line.

## Added examples

Valid examples remain under:

```text
examples/opn/mapping-v4-frame/
```

Invalid frame examples are added under:

```text
examples/opn/mapping-v4-frame-invalid/
```

Invalid examples cover:

```text
missing required frame slot
unknown frame slot role
lexical role not licensed by frame
malformed FRAME_GRAPH row
```

## Checker

The V4 checker now covers:

- V3 core valid + invalid: 13 checks
- V4.1 NEG/TIME/PLACE valid + invalid: 6 checks
- V4.3 WH valid + invalid: 5 checks
- V4.5 DET valid + invalid: 7 checks
- V4.9 FRAME.graph slot validation valid + invalid: 6 checks

Expected result:

```text
Mapping V4.9 FRAME.graph minimal slot validator regression checker
summary: 37 pass, 0 fail
```

## Scope boundary

Not included:

```text
FRAME.graph generation behavior
generated-output suppression on frame-validation failure
graph rendering
view toggles
graph mutation
role inference
lexicon lookup
automatic frame selection
verb-to-frame lexicon binding
adjective placement
complex NP structure
relative clauses
```
