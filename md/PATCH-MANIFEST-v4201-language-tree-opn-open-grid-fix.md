# Patch manifest — Mapping V4.20.1 Language Tree OPN open-grid fix

Patch name:

```text
Mapping_V4-26-05-03--v4201-language-tree-opn-open-grid-fix
```

## Type

Small corrective behavior/test-data slice.

## Base

```text
Mapping_V4-26-05-03--v420-language-tree-opn-test.zip
```

## Adds

```text
md/sources/mapping-v4/language-tree-v4201.md
md/meta-inf/2026-05-03-mapping-v4201-language-tree-opn-open-grid-fix.md
md/PATCH-MANIFEST-v4201-language-tree-opn-open-grid-fix.md
```

## Changes

```text
userInterface/GraphFileActions.java
tools/LanguageTreeRegressionChecker.java
compiled class files
OpenGraphEd.jar
dist/OpenGraphEd.jar
examples/opn/language-tree-v420/01-ltree-wie-heeft-de-hond-gebeten.opn
examples/opn/language-tree-v420/02-ltree-dat-de-vrouw-de-hond-heeft-gebeten.opn
examples/opn/language-tree-v420/03-ltree-vrouw-heeft-man-boek-gegeven.opn
examples/opn/language-tree-v420/LAST-RUN.txt
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
generated utterance rules
projection rendering algorithm
FRAME.graph validation semantics
Lexicon validation semantics
Morphology validation semantics
graph mutation behavior
```

## Actual checks

```text
Mapping V4.20.1 Language Tree OPN regression checker: 3 pass, 0 fail
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
