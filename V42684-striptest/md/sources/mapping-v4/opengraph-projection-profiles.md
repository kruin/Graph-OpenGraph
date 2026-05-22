# OpenGraph projection profiles

Projection captions and enabled sides are structure-type specific.

## Profile keys

Use this pattern in `config/opengraph_defaults.properties` or `config/opengraph_user.properties`:

```properties
projection.profile.<profile>.show=true
projection.profile.<profile>.left.enabled=true
projection.profile.<profile>.right.enabled=true
projection.profile.<profile>.top.enabled=false
projection.profile.<profile>.bottom.enabled=false
projection.profile.<profile>.left.caption=LEX
projection.profile.<profile>.right.caption=PROJ
projection.profile.<profile>.top.caption=pm
projection.profile.<profile>.bottom.caption=LF
```

Supported profiles:

| Profile | Used by |
|---|---|
| `simple` | Simple tree |
| `language` | Language Tree / Phrase |
| `frame` | Frame / roles-functions |
| `anaphor` | Anafoor |
| `default` | fallback for later structure types |

## Current defaults

| Structure type | Projection sides |
|---|---|
| Simple | no projections |
| Language Tree / Phrase | left `LEX`, right `SYNT`, top `pm`, bottom `LF` |
| Frame | left `LEX`, right `ROLE`, top `FRAME`; bottom `SEM` exists but is off |
| Anafoor | left `LEX`, right `ANA`, top `pm`; bottom `LF` exists but is off |
| Default/future | left `LEX`, right `PROJ`; top/bottom off |

## Notes

The previous `LEX / SYNT / pm / LF` setting is now explicitly the Dutch Language Tree profile, not the global model for every structure type.

Legacy keys such as `projection.left.caption=LEX` still work as fallback, but profile keys override them.


## v4.22.3 — UI-bediening

De projectieprofielen zijn nu ook direct zichtbaar in de OpenGraph-header. Voor projectie-capable structure types verschijnt:

```text
Projecties: [aan] [L] <caption> [R] <caption> [Boven] <caption> [Onder] <caption> [Save profile]
```

Gedrag:

- wijzigen van een checkbox of caption werkt direct op de huidige projectie-overlay;
- `Save profile` schrijft de instellingen weg naar het actieve profiel in `config/opengraph_user.properties`;
- `Simple` toont deze UI niet, omdat Simple geen projectieprofiel gebruikt;
- `Language Tree / Phrase`, `Frame` en `Anafoor` tonen elk hun eigen profiel.
