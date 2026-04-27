# Projectie Master Spec

## Architectuur

```text
Frame   → rollen
Phrase  → structuur
Lex     → lineaire as
```

## Kernregel

```text
Structuur + mapping → uiting
```

## Geen

- transformaties op bomen
- VP als verplichte node
- graph-mutatie door generator

## Wel

- projectie
- constraint-based volgorde
- V-centrische phrase-structuur
- Frame als rol-/projectieregelruimte
- lexicale as als primaire doelruimte van de uiting

## Frame / Phrase scheiding

Wijzigingen in Frame en Phrase gelden niet automatisch voor beide. Alleen expliciet gekoppelde besluiten gelden voor beide.

## OPN

- `.graph` = zichtbare snapshot.
- `.opn` = modelbestand met structure + mapping.
- Save OPN forceert extensie `.opn`.
- Roundtrip moet mapping behouden.
