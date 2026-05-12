# Mapping V3.5 Expected Output Manifest

Status: manual regression manifest.

## Scope

This manifest covers the current Mapping V3 core phase:

- Agens
- Patiens
- RECIPIENT
- THEME
- V
- V-AUX
- V-PART
- split VP: `heeft ... gebreid` / `heeft ... gegeven`

Out of scope:

- WH / vraagzinnen
- NEG
- TIME / PLACE / bijwoorden
- DET splitting
- FRAME.graph integration
- lexicon / automatic role inference
- UI rendering / rotate / layout

## Valid examples

Directory:

```text
examples/opn/mapping-v3-core/
```

Expected file:

```text
examples/opn/mapping-v3-core/EXPECTED.txt
```

| File | Expected best | Expected validation |
|---|---|---|
| `01-vrouw-breit-trui.opn` | `vrouw breit trui` | `3 ok, 0 fail` |
| `02-vrouw-heeft-trui-gebreid.opn` | `vrouw heeft trui gebreid` | `4 ok, 0 fail` |
| `03-vrouw-geeft-man-boek.opn` | `vrouw geeft man boek` | `4 ok, 0 fail` |
| `04-vrouw-heeft-man-boek-gegeven.opn` | `vrouw heeft man boek gegeven` | `5 ok, 0 fail` |
| `05-vrouw-heeft-man-een-boek-gegeven.opn` | `vrouw heeft man een boek gegeven` | `5 ok, 0 fail` |

## Invalid examples

Directory:

```text
examples/opn/mapping-v3-core-invalid/
```

Expected file:

```text
examples/opn/mapping-v3-core-invalid/EXPECTED-FAIL.txt
```

| File | Expected failure class |
|---|---|
| `01-missing-role.opn` | missing lexical role |
| `02-unknown-role.opn` | unknown role |
| `03-missing-theme-target.opn` | missing THEME target |
| `04-missing-vpart-target.opn` | missing V-PART target |
| `05-recipient-theme-cycle.opn` | ordering cycle |
| `06-verb-domain-missing-id.opn` | broken verb-domain reference |
| `07-duplicate-lexical-id.opn` | duplicate lexical id |
| `08-missing-verb-anchor.opn` | missing V-anchor |

## Pass criteria

Valid examples:

```text
validation: ... 0 fail
expected generated best matches exactly
```

Invalid examples:

```text
validation: ... fail
loaded file name appears in validation details
generated: none (invalid mapping)
```

The invalid mapping Info behavior is intentionally info-only: no modal error dialog is expected.
