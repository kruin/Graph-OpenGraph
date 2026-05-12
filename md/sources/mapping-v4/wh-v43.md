# Mapping V4.3 — minimal WH generator / validator

Status:

```text
MAPPING_V4_3_WH_MINIMAL
```

## Purpose

V4.3 is the first behavior phase after the V4.2 WH scope freeze.

It adds minimal, tested WH behavior to the existing ranked Mapping V4 placement layer.

## Scope

In scope:

- `MAPPING_V4:` / `END_MAPPING_V4:` recognition in the OPN loader.
- `WH` as a known lexical role.
- WH as a lexical-axis item, not as a tree transformation.
- `CLAUSE_MODE: interrogative_wh` as mapping metadata in examples.
- Minimal WH examples:
  - subject-WH: `wie heeft de hond gebeten`
  - object-WH: `wat heeft vrouw gebreid`
  - simple finite-V WH: `wie bijt hond`
- Invalid WH examples for missing V-AUX and ordering cycles.
- V4 checker including V3 core, V4.1 NEG/TIME/PLACE and V4.3 WH examples.

Out of scope:

- DET splitting.
- FRAME.graph integration.
- automatic role inference.
- UI/rendering changes.
- graph mutation.
- transformations / vooropplaatsing.

## Minimal WH rule pattern

WH is represented as a normal lexical item:

```text
x1|wie|role:WH|wh-target:Agens|pos:WH
```

For a split-VP WH question, the stable rule pattern is:

```text
WH|before_clause|core
V-AUX|after|WH|core
Patiens|after|V-AUX|core
Patiens|before|V-PART|core
V-PART|clause_end|core
```

This produces:

```text
wie heeft de hond gebeten
```

## DET boundary

DET is still not implemented. For V4.3, multiword items such as `de hond` remain one lexical item.

## Architecture rule

```text
STRUCTURE = view
MAPPING_V4 = logica
WH = lexical item on the lexical axis
no graph mutation
no tree transformation
output via generator
validation and generation through placement constraints
```
