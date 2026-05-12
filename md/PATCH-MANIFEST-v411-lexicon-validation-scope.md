# Patch manifest — Mapping V4.11 lexicon validation / coupling scope

Patch name:

```text
Mapping_V4-26-05-02--v411-lexicon-validation-scope
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v4101-straight-edge-midpoint-cleanup.zip
```

## Adds

```text
md/sources/mapping-v4/lexicon-validation-v411.md
md/meta-inf/2026-05-02-mapping-v411-lexicon-validation-scope.md
md/examples/opn/mapping-v4-lexicon-v411-expected-output-manifest.md
md/PATCH-MANIFEST-v411-lexicon-validation-scope.md
md/sources-md-zip/Mapping_V4-26-05-02--v411-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v411-lexicon-validation-scope-md-only.zip
```

## Changes

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
Java source
class files
jar files
Mapping V4 parsing/generation/validation
FRAME.graph metadata or validation
Lexicon metadata read/count
straight-edge midpoint rendering behavior
OPN example semantics
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_11_LEXICON_VALIDATION_SCOPE
Runtime behavior remains MAPPING_V4_10_1_STRAIGHT_EDGE_MIDPOINT_CLEANUP
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
