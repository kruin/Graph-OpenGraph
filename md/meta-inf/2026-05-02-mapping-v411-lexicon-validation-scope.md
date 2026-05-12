# 2026-05-02 — Mapping V4.11 Lexicon validation / coupling scope

## Status

```text
MAPPING_V4_11_LEXICON_VALIDATION_SCOPE
```

## Summary

V4.11 is a documentation-only scope phase on top of V4.10.1.

It defines the first minimal lexicon validation/coupling boundary for a later behavior phase. Runtime behavior remains V4.10.1.

## Base

```text
Mapping_V4-26-05-02--v4101-straight-edge-midpoint-cleanup.zip
```

## Added

```text
md/sources/mapping-v4/lexicon-validation-v411.md
md/meta-inf/2026-05-02-mapping-v411-lexicon-validation-scope.md
md/examples/opn/mapping-v4-lexicon-v411-expected-output-manifest.md
md/PATCH-MANIFEST-v411-lexicon-validation-scope.md
```

## Runtime change

```text
none
```

## Preserved

```text
Mapping V4 parser/generator/validator
FRAME.graph metadata and validation
Lexicon metadata read/count
straight-edge midpoint cleanup
graph rendering
graph mutation behavior
OPN example semantics
```

## Actual checks

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
