# Mapping V4.2 — WH / clause mode scope

Status:

```text
MAPPING_V4_2_WH_SCOPE
```

## Purpose

V4.2 is a documentation and manifest phase for WH questions.

It prepares the next behavior phase without changing Java code, parser behavior, generator behavior, validator behavior, UI, rendering, `.class` files or jar files.

## Base

V4.2 starts from:

```text
MAPPING_V4_1_NEG_TIME_PLACE
```

The V4.1 baseline must remain true:

```text
Mapping V3 regression: 13 pass, 0 fail
Mapping V4.1 regression: 19 pass, 0 fail
MD folder check: PASS
```

## Core decision

WH is not treated as a transformation.

OpenGraphEd Mapping keeps the existing architecture:

```text
STRUCTURE = view
MAPPING_V4 = logic
no graph mutation
output via generator
validation and generation via the lexical axis
```

Therefore a WH question is specified by lexical items, role labels, clause mode metadata and placement rules.

## Clause mode

V4.2 introduces the scope concept:

```text
CLAUSE_MODE: interrogative_wh
```

This is a mapping-level property, not a graph-rendering property.

For now, clause mode is only documented. It is not yet parsed as behavior in V4.2.

## WH lexical items

A WH item is a lexical item with:

```text
role:WH
wh_target:<semantic role>
```

Proposed target roles for the first behavior phase:

```text
Agens
Patiens
```

Later targets such as RECIPIENT, THEME, PLACE and TIME are out of scope for the first WH behavior phase.

## Minimal placement model

V4.2 records the intended first WH placement behavior:

```text
WH     -> before_clause
V-AUX  -> after_WH
Agens  -> after_V_AUX    when Agens is not the WH target
Patiens -> after_V_AUX   when Patiens is not the WH target
V-PART -> clause_end
```

The exact internal relation names may still be normalized in V4.3.

## DET boundary

DET splitting remains outside V4.2.

Natural noun phrases such as `de hond` and `een boek` may remain one lexical item for this phase:

```text
x3|de hond|role:Patiens
```

That keeps V4.2 focused on WH and clause mode. A later DET phase may split this into separate DET and N lexical items.

## In scope

- Define WH as a lexical-axis mapping problem.
- Define clause mode as mapping metadata.
- Define expected valid WH examples.
- Define expected invalid WH diagnostics.
- Preserve V3 core and V4.1 behavior.
- Keep all Markdown under `md/`.

## Out of scope

- Java changes.
- Parser changes.
- Generator changes.
- Validator changes.
- New checker behavior.
- DET/lidwoorden as separate roles.
- FRAME.graph integration.
- Lexicon or automatic role inference.
- UI/rendering/view-options.
- Graph mutation.
- WH transformations or movement operations on trees.

## Next behavior phase

The next behavior phase should be:

```text
V4.3 — minimal WH generator and validator
```

V4.3 may implement subject-WH and object-WH only, using the expected-output manifest from V4.2.
