# 2026-04-30 — Mapping V4.4 DET scope

Status:

```text
MAPPING_V4_4_DET_SCOPE
```

## Type

Documentation and expected-output manifest only.

## Base

```text
Mapping_V4-26-04-29--v43-wh-minimal.zip
```

## Added

```text
md/sources/mapping-v4/det-v44.md
md/meta-inf/2026-04-30-mapping-v44-det-scope.md
md/examples/opn/mapping-v4-det-v44-expected-output-manifest.md
```

## Updated

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Scope

Defines future DET handling as a separate lexical-axis mapping slice.

In scope for this documentation phase:

- DET role design.
- `det-target` relation.
- proposed valid examples.
- proposed invalid examples.
- boundary against FRAME.graph, lexicon and UI/rendering.

Not changed:

```text
Java source
.class files
jar files
parser
generator
validator
checker
UI
rendering
runtime example semantics
```

## Expected status after applying patch

```text
MAPPING_V4_4_DET_SCOPE
Runtime behavior still: MAPPING_V4_3_WH_MINIMAL
Mapping V4.3 regression checker: 24 pass, 0 fail
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.5 — minimal DET generator and validator
```
