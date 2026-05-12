# PATCH MANIFEST v4.22.2 — Projection profiles per structure type

## Scope

This slice separates projection configuration by OpenGraph structure type. The Language Tree projection profile is no longer treated as the universal default for Frame, Anafoor, or future structure types.

## Configuration

Projection settings now support profile-specific keys in `config/opengraph_defaults.properties` and `config/opengraph_user.properties`:

```properties
projection.profile.<profile>.show=true|false
projection.profile.<profile>.left.enabled=true|false
projection.profile.<profile>.right.enabled=true|false
projection.profile.<profile>.top.enabled=true|false
projection.profile.<profile>.bottom.enabled=true|false
projection.profile.<profile>.left.caption=...
projection.profile.<profile>.right.caption=...
projection.profile.<profile>.top.caption=...
projection.profile.<profile>.bottom.caption=...
projection.profile.<profile>.<side>.position=...
```

Profiles:

- `simple`
- `language`
- `frame`
- `anaphor`
- `default`

Legacy global keys such as `projection.left.caption` remain as fallback only.

## Defaults

| Structure type | Left | Right | Top | Bottom |
|---|---|---|---|---|
| Simple | off | off | off | off |
| Language Tree / Phrase | `LEX` on | `SYNT` on | `pm` on | `LF` on |
| Frame | `LEX` on | `ROLE` on | `FRAME` on | `SEM` off |
| Anafoor | `LEX` on | `ANA` on | `pm` on | `LF` off |
| Default/future | `LEX` on | `PROJ` on | `pm` off | `LF` off |

## Runtime behavior

When the structure type changes, OpenGraph now loads the matching projection profile. Language Tree graphs still load the `language` profile explicitly.

## Files touched

- `config/opengraph_defaults.properties`
- `config/opengraph_user.properties`
- `userInterface/OpenGraphDialogSettingsSupport.java`
- `userInterface/OpenGraphProjectionSupport.java`
- `userInterface/OpenGraphActions.java`
- `userInterface/GraphEditorWindow.java`
- `userInterface/GraphFileActions.java`
