# Mapping V4.4 — DET scope

Status:

```text
MAPPING_V4_4_DET_SCOPE
```

## Purpose

V4.4 defines the next small Mapping V4 slice after V4.3 minimal WH: controlled handling of determiners (`DET`) as separate lexical-axis items.

This phase is documentation and expected-output scope only. It does not implement the DET generator or validator yet.

## Base

```text
Mapping_V4-26-04-29--v43-wh-minimal.zip
```

Preserved runtime behavior:

```text
MAPPING_V4_3_WH_MINIMAL
Mapping V4.3 regression checker: 24 pass, 0 fail
```

## Scope

In scope for the future DET behavior:

- `DET` as a known lexical role.
- DET represented as a normal lexical item on the lexical axis.
- A `det-target` relation from DET to its nominal host role.
- Minimal NP-internal placement: DET immediately before its nominal target.
- DET combined with already-stable V4.3 WH examples.
- Invalid examples for missing target, unknown target and ordering cycle.

Out of scope for V4.4:

- Java implementation.
- `.class` rebuilds.
- jar rebuilds.
- parser/generator/validator changes.
- graph mutation.
- FRAME.graph integration.
- lexicon / automatic role inference.
- adjective placement.
- complex NP structure.
- relative clauses.
- UI/rendering changes.

## Proposed DET representation

A determiner is represented as a separate lexical item:

```text
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
```

The initial implementation should normalize both spellings:

```text
det-target
det_target
```

The preferred project spelling is:

```text
det-target
```

## Minimal DET rule pattern

For a plain declarative transitive clause:

```text
Agens|left_of|V|core
Patiens|right_of|V|core
DET|before|Patiens|core
```

This should produce:

```text
vrouw bijt de hond
```

For split VP:

```text
Agens|before|V-AUX|core
Patiens|after|V-AUX|core
DET|before|Patiens|core
Patiens|before|V-PART|core
V-PART|clause_end|core
```

This should produce:

```text
vrouw heeft de trui gebreid
```

For WH + DET, WH remains a lexical-axis item and DET remains a lexical-axis item:

```text
WH|before_clause|core
V-AUX|after|WH|core
Patiens|after|V-AUX|core
DET|before|Patiens|core
Patiens|before|V-PART|core
V-PART|clause_end|core
```

This should produce:

```text
wie heeft de hond gebeten
```

## Validation target for later V4.5 implementation

The later implementation should reject:

- DET without `det-target`.
- DET with an unknown target role.
- DET target that is absent from the lexical items.
- DET ordering that creates a cycle in the best placement rules.
- Multiple DET items targeting the same nominal role unless explicitly allowed in a later phase.

## Architecture rule

```text
STRUCTURE = view
MAPPING_V4 = logica
WH = lexical item on the lexical axis
DET = lexical item on the lexical axis
no graph mutation
no tree transformation
output via generator
validation and generation through placement constraints
```

## Next proposed phase

```text
V4.5 — minimal DET generator / validator
```
