# 2026-05-01 — Mapping V4.6 FRAME.graph scope

## Status

```text
MAPPING_V4_6_FRAME_GRAPH_SCOPE
```

## Summary

V4.6 opens `FRAME.graph` as a documentation-only scope phase on top of V4.5.2.

It defines `FRAME.graph` as a future semantic/frame context layer. It does not implement parsing, validation, generation, rendering or graph mutation.

## Base

```text
Mapping_V4-26-05-01--v452-stable-handoff.zip
```

## Added

```text
md/sources/mapping-v4/frame-graph-v46.md
md/meta-inf/2026-05-01-mapping-v46-frame-graph-scope.md
md/examples/opn/mapping-v4-frame-v46-expected-output-manifest.md
```

## Updated

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
md/sources-md-zip/Mapping_V4-26-05-01--v46-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v46-frame-graph-scope-md-only.zip
```

## Scope

Documentation/package checkpoint only.

Not changed:

```text
Java source
.class files
jar files
parser
generator
validator
checker
placement rules
UI behavior
graph rendering
graph mutation behavior
example semantics
```

## Decisions

- `FRAME.graph` is a future semantic/frame context layer.
- `FRAME.graph` is not the generator.
- Existing `MAPPING_V4` lexical-axis mapping remains explicit.
- `STRUCTURE` remains a drawn view.
- No frame metadata is drawn as graph content.
- No lexical role inference is introduced.

## Actual checks

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended phase

```text
Mapping V4.7 — FRAME.graph metadata read / count
```

Recommended first behavior slice: parse a `FRAME_GRAPH` section as metadata only and report it in Info, without validation, generation, rendering or graph mutation.
