# Patch manifest — Mapping V4.20 Language Tree OPN test slice

Patch name:

```text
Mapping_V4-26-05-03--v420-language-tree-opn-test
```

## Type

Small UI/open-behavior + examples/checker slice.

## Base

```text
Mapping_V4-26-05-03--v418-frame-selection-validator-target.zip
```

## Adds

```text
examples/opn/language-tree-v420/
tools/LanguageTreeRegressionChecker.java
run-language-tree-checker.bat
md/sources/mapping-v4/language-tree-v420.md
md/meta-inf/2026-05-03-mapping-v420-language-tree-opn-test.md
md/examples/opn/mapping-v4-language-tree-v420-expected-output-manifest.md
md/PATCH-MANIFEST-v420-language-tree-opn-test.md
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
compiled class files
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Does not change

```text
graph mutation
projection rendering algorithm
generated utterance rules except accepting role C in examples
FRAME.graph validation semantics
Lexicon validation semantics
Morphology validation semantics
```
