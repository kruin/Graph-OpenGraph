# PATCH MANIFEST v4.25.9 — FT role-box tiered stacking

## Doel

Corrigeer FT-role-box zodat functionele rollen niet terugvallen naar een vlakke horizontale waaier.

## Wijziging

- `FT` blijft `role_box`.
- `LT` blijft `projection_box`.
- Role-box gebruikt nu visuele tiers:
  - `pred` / center: predicate spine, tier 2.
  - `agens`: links, tier 3.
  - `patiens` / `recipiens`: rechts, tier 3+.
  - `instrument`, `locatief`, `tijd`: down-stack onder de reeds geplaatste role-boxes.
- Down-stack gebruikt nu `roleBoxMaxBottom(placed) + 2`, zodat adjunctrollen niet meer in dezelfde horizontale band als kernrollen terechtkomen.
- Box-validatie en whole-subtree shifting blijven actief.

## Niet gewijzigd

- Geen GUI-config toegevoegd.
- Role-rank blijft leidend.
- Projectielijnen blijven actief zoals in v4.25.8.
