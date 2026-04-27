# OPN Format (v3)

## Structure (visual)

```text
STRUCTURE_NODES:
id|label|x|y

STRUCTURE_EDGES:
id|id

END_STRUCTURE:
```

## Mapping (logic)

```text
MAPPING_V3:
LEXICAL_ITEMS:
x1|woord|role:...|pos:...

VERB_DOMAIN:
V|x2,x4

PLACEMENT_RULES:
ROLE -> constraint

END_MAPPING_V3:
```

## Regels

- STRUCTURE wordt getekend.
- MAPPING wordt niet getekend.
- Pipe-safe parsing is verplicht.
- Save OPN forceert extensie `.opn`.
- Roundtrip moet STRUCTURE en MAPPING_V3 behouden.
