# PATCH MANIFEST v4.23.7 — Language Tree side-aware LR/RL recursion

## Doel

Correctie op `nary_compact_lr`: de recursie wordt afhankelijk van de kant waarop de subboom staat.

## Gedrag

- Root: normale LR-start.
- Linkertak: LR-recursie.
- Rechtertak: RL-recursie.
- Unary preterminals erven de kant van de bovenliggende plaatsing.
- V-cluster behoudt een vaste gereserveerde box, maar zet het geordende eerste kind aan de geërfde buitenzijde.

## Config

De actieve sleutel blijft gelijk:

```properties
language.layout.strategy=nary_compact_lr
projection.profile.language.layout.strategy=nary_compact_lr
```

Vrijheden/configuratie voor alternatieven volgen later; deze versie kiest één deterministische regel.

## Aangeraakt

- `operation/OpenGraphTreeDrawOperation.java`
- `userInterface/OpenGraphDialog.java`
- `userInterface/OpenGraphProjectionSettings.java`
- `config/opengraph_defaults.properties`
- `config/opengraph_user.properties`
- `md/CHANGELOG.md`
- `md/INDEX.md`
- `md/sources/mapping-v4/language-tree-free-layout.md`
