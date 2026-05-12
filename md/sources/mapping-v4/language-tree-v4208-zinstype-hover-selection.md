# Language Tree V4.20.8 — zinstype selection and hover preview

## UI behaviour

The `Zinstype` group now uses toggle buttons:

```text
[Basis] [Bijzin] [Stellend] [Ja/nee] [WH] [Topicalisatie]
```

The chosen button remains marked after selection.

## Rule preview

The detailed rule preview is no longer drawn over the tree.  It appears when hovering over a zinstype button.

The drawing overlay remains compact:

```text
LANGUAGE TREE
Zinstype: <active profile>
```

For topicalisation with a selected node, the overlay can show:

```text
Zinstype: Topicalisatie — NP(de man)
```

## Topicalisation convention

Displacement rules attach to categorial nodes, not to loose lexical leaves.

Recommended workflow:

1. Click/select the categorial node, e.g. `NP` or `DP`.
2. Click `Topicalisatie`.
3. The preview uses the selected category and groups its lexical descendants, e.g. `NP(de man)`.

This is a preview/selection convention.  Physical placement of lexical items on `slot1` remains the next implementation step.
