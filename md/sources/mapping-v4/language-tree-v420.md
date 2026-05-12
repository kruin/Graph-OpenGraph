# Mapping V4.20 — Language Tree OPN first test slice

## Status

```text
MAPPING_V4_20_LANGUAGE_TREE_OPN_TEST
```

## Goal

Make real language-tree `.opn` files directly testable in OpenGraphEd.

## Behavior

When an `.opn` file contains:

```text
STRUCTURE_TYPE: LANGUAGE_TREE
```

or equivalent `LANGUAGE_TREE`/`L1` metadata, Open OPN marks the graph as a language tree and opens it with the projection context active:

```text
LEX left
SYN right
```

The structure itself still comes from explicit `STRUCTURE_NODES` / `STRUCTURE_EDGES`. Metadata is not drawn as graph content.

## Examples

```text
examples/opn/language-tree-v420/01-ltree-wie-heeft-de-hond-gebeten.opn
examples/opn/language-tree-v420/02-ltree-dat-de-vrouw-de-hond-heeft-gebeten.opn
examples/opn/language-tree-v420/03-ltree-vrouw-heeft-man-boek-gegeven.opn
```

## Scope

No tree transformations. No automatic lexical insertion. No graph mutation. The generated utterance remains derived from explicit `MAPPING_V4` placement rules.
