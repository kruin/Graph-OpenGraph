# Mapping V4.5.2 — stable handoff checkpoint

Status:

```text
MAPPING_V4_5_2_STABLE_HANDOFF
```

## Purpose

V4.5.2 is a documentation-only checkpoint on top of V4.5.1.

It records V4.5.1 as the accepted stable base before opening the next larger Mapping V4 phase.

## Base

```text
Mapping_V4-26-04-30--v451-info-label.zip
```

Base runtime behavior:

```text
MAPPING_V4_5_1_INFO_LABEL
```

## Changed

Documentation/package state only:

- added this V4.5.2 handoff document
- added the V4.5.2 phase manifest
- updated current-state, phasing, README, INDEX and CHANGELOG documentation
- refreshed the md-only source zip and all-md source bundle

## Preserved

No runtime behavior is changed.

Not changed:

```text
Java source
.class files
jar files
parser
generator
validator
checker
mapping rules
graph rendering
graph mutation behavior
example semantics
```

## Stable scope handed off

The stable behavior handed off from V4.5.1 includes:

- V3 core roles and split VP
- V4.1 NEG / TIME / PLACE placement behavior
- V4.3 minimal WH behavior
- V4.5 minimal DET behavior
- V4.5.1 Info-window label clarification

## Actual checks

Run against the V4.5.2 package state:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next intended phase

```text
V4.6 — FRAME.graph scope
```

V4.6 should start as a separate scope/documentation phase unless explicitly decided otherwise.
