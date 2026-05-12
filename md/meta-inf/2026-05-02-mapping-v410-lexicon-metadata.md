# 2026-05-02 — Mapping V4.10 Lexicon metadata read / count

## Status

```text
MAPPING_V4_10_LEXICON_METADATA
```

## Summary

V4.10 implements the first minimal runtime-visible lexicon layer.

It reads a `LEXICON:` / `END_LEXICON:` metadata section, counts pipe-delimited lexical entries and reports the count in the Info window.

## Base

```text
Mapping_V4-26-05-02--v49-frame-graph-minimal-slot-validator.zip
```

## Added

```text
md/sources/mapping-v4/lexicon-v410.md
md/meta-inf/2026-05-02-mapping-v410-lexicon-metadata.md
md/examples/opn/mapping-v4-lexicon-v410-expected-output-manifest.md
md/PATCH-MANIFEST-v410-lexicon-metadata.md
examples/opn/mapping-v4-lexicon/
```

## Changed

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
userInterface/GraphEditorInfoSupport.java
tools/MappingV4RegressionChecker.java
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Behavior

Recognized metadata section:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
END_LEXICON:
```

Info summary:

```text
OPN Lexicon: <n> entries, metadata only
```

## Preserved

```text
generated utterance rules
Mapping V4 placement validation semantics
FRAME.graph slot validation semantics
graph rendering
graph mutation
tree transformation boundary
automatic role inference boundary
```

## Actual checks

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
