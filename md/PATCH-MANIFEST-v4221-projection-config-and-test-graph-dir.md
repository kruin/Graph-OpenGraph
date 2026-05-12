# PATCH MANIFEST v4.22.1 — projection config and test graph directory

## Purpose

Restore explicit OpenGraph projection configuration and prevent test/demo graph loading from drifting into `config/`.

## Projection configuration

The OpenGraph projection axes are now explicit in `config/opengraph_defaults.properties` and `config/opengraph_user.properties`.

Default axis captions:

```properties
projection.left.caption=LEX
projection.right.caption=SYNT
projection.top.caption=pm
projection.bottom.caption=LF
```

Default enabled sides:

```properties
projections.show=true
projection.left.enabled=true
projection.right.enabled=true
projection.top.enabled=true
projection.bottom.enabled=true
```

Interpretation:

| Side | Caption | Meaning |
|---|---|---|
| left | LEX | lexical axis |
| right | SYNT | syntactic projection |
| top | pm | placeholder / later upper projection |
| bottom | LF | logical form projection |

For `Simple`, all projection sides remain disabled.
For `Language Tree / Phrase`, `Frame`, and `Anafoor`, all four sides are enabled by default.

## UI

`OpenGraphDraw settings → Projections` now displays both:

- which projection sides are enabled;
- which caption is assigned to each side.

## Language Tree graph hints

Opening a graph with `LANGUAGE_TREE`, `LANGUAGE TREE`, or `LEXTEST` no longer hard-resets top/bottom projections off. It now loads the projection configuration from the OpenGraph config files and only forces:

```text
structure type = Language Tree / Phrase
show projections = true
```

## Test / demo graph directory

Added `config/opengraphed_user.properties` with:

```properties
graph.load.from.default.dir=true
graph.default.dir=examples/graph
opn.load.from.default.dir=true
opn.default.dir=examples/opn
```

When test features are enabled, the graph chooser will not persist `config/` as the graph directory if a test/demo graph was accidentally opened there.
