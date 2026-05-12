# 2026-04-29 — Mapping V4.2 WH / clause mode scope

## Status

```text
MAPPING_V4_2_WH_SCOPE
```

## Summary

V4.2 is a documentation and manifest checkpoint.

It prepares WH questions by defining:

- WH as a lexical item.
- WH target roles.
- clause mode as mapping metadata.
- expected valid and invalid outputs for a later V4.3 behavior phase.

## Base

This phase starts from:

```text
MAPPING_V4_1_NEG_TIME_PLACE
```

Base expectations remain:

```text
Mapping V3 regression: 13 pass, 0 fail
Mapping V4.1 regression: 19 pass, 0 fail
MD folder check: PASS
```

## Added

- `md/sources/mapping-v4/wh-v42.md`
- `md/meta-inf/2026-04-29-mapping-v42-wh-scope.md`
- `md/examples/opn/mapping-v4-wh-expected-output-manifest.md`
- `md/sources-md-zip/Mapping_V4-26-04-29--v42-wh-scope-md-only.zip`
- `md/sources-md-zip/Mapping_V4-26-04-29--v42-wh-scope-addendum-bundel.md`

## Decisions

### No transformations

WH is not implemented as fronting or tree movement.

The utterance is generated from mapping data and placement rules on the lexical axis.

### Clause mode

V4.2 reserves:

```text
CLAUSE_MODE: interrogative_wh
```

This is documented for the next behavior phase. It is not a V4.2 runtime change.

### DET remains outside scope

`de hond` and comparable noun phrases may be one lexical item in V4.2/V4.3.

A later DET phase may split these into separate DET and N items.

## Preserved

- V3 core roles remain stable.
- V4.1 NEG / TIME / PLACE behavior remains the current behavior baseline.
- No graph mutation is introduced.
- No UI/rendering behavior is changed.
- Markdown remains under `md/`.

## Scope boundary

V4.2 does not implement:

- WH generation.
- WH validation.
- WH parser logic.
- DET splitting.
- FRAME.graph.
- lexicon.
- UI/rendering/view-options.
- Java source changes.
- class or jar rebuilds.

## Expected checks

Because V4.2 is documentation-only:

```text
Mapping V3 regression: unchanged
Mapping V4.1 regression: unchanged
MD folder check: PASS
```

## Next phase

```text
V4.3 — minimal WH generator and validator
```

Recommended first implementation scope:

- subject-WH: `wie heeft de hond gebeten`
- object-WH: `wat heeft vrouw gebreid`
- invalid diagnostics for missing WH target, unknown WH target and duplicate WH.
