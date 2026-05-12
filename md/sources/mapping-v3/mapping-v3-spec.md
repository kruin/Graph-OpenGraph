# Mapping V3 Spec

## Principes

- Data-driven: gedrag komt uit `.opn`, niet uit hardcoded volgorde.
- Meerdere placement-opties zijn toegestaan.
- Ranking kan via placement rules worden vastgelegd.
- Mapping wordt gelezen als modeldata en niet als graph-nodes getekend.

## Rollen

- Agens
- Patiens
- RECIPIENT
- THEME
- TIME
- PLACE
- NEG
- V-AUX
- V-PART

## Constraints

- V-AUX < NEG < Patiens
- RECIPIENT < THEME
- THEME < V-PART

## Doel

```text
Mapping → constraints → validatie → generatie
```

## Compatibiliteit

- MAPPING_V3 is leidend.
- MAPPING_V1 en MAPPING_V2 bestaan alleen nog voor compatibiliteit.
