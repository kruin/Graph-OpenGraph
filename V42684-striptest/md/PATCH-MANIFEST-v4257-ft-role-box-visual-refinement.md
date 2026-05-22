# Patch Manifest v4.25.7 — FT role-box visual refinement

## Doel

Functional Tree (`FT`) role-box visueel verfijnen zonder de Language Tree (`LT`) layout te wijzigen.

## Wijzigingen

- `FT` blijft `role_box` gebruiken.
- `LT` blijft `projection_box` gebruiken.
- Role-box plaatsing gebruikt nu aparte frontiers per rolrichting:
  - `pred` / center op de functionele spine;
  - `agens` links;
  - `patiens` rechts;
  - `recipiens` rechts, dieper/volgend in de rechter frontier;
  - `instrument`, `locatief`, `tijd` in een down-stack.
- Globale list-stacking van alle rollen is vervangen door side-frontier stacking.
- Box-validatie blijft actief: als een role-box botst, schuift alleen die complete subtree verder omlaag.
- Rolvolgorde/rank wordt niet herschreven door de visuele plaatsing.

## Configuratie

Bestaande properties uit v4.25.6 blijven geldig:

```properties
functional.layout.role.<role>.rank=<number>
functional.layout.role.<role>.side=center|left|right|down
functional.layout.role.<role>.corridor=auto|outer|inner|stack
```

## Verwachte visuele default

```text
pred       center
agens      left
patiens    right
recipiens  right / lager
instrument down
locatief   down
tijd       down
```

## Niet gewijzigd

- Geen GUI-config toegevoegd.
- Geen wijziging in LT projectie-box layout bedoeld.
- Geen SEM/LOG-layout toegevoegd.
