# OpenGraph projection config

Projection configuration is file-based and lives in:

```text
config/opengraph_defaults.properties
config/opengraph_user.properties
```

## Default projection axes

```properties
projection.left.caption=LEX
projection.right.caption=SYNT
projection.top.caption=pm
projection.bottom.caption=LF
```

## Default enabled sides

```properties
projections.show=true
projection.left.enabled=true
projection.right.enabled=true
projection.top.enabled=true
projection.bottom.enabled=true
```

## Structure-type convention

| Structure type | Projection sides |
|---|---|
| Simple | none |
| Language Tree / Phrase | LEX, SYNT, pm, LF |
| Frame | LEX, SYNT, pm, LF |
| Anafoor | LEX, SYNT, pm, LF |

## Direction convention

| Side | Caption | Function |
|---|---|---|
| left | LEX | lexical items / lexical axis |
| right | SYNT | syntactic projection |
| top | pm | reserved upper projection |
| bottom | LF | logical form |

## Test graph directory

The project-level default graph directory is now:

```properties
graph.default.dir=examples/graph
```

This prevents test/demo files such as `test.graph` from being selected from `config/` while test features are enabled.
