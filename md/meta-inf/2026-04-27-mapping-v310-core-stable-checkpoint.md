# Mapping v3.10 core stable checkpoint

Status: **MAPPING_V3_CORE_STABLE**

## Doel

Dit checkpoint markeert de core Mapping V3-laag als voorlopig stabiel.

## Core in scope

- Agens
- Patiens
- RECIPIENT
- THEME
- V
- V-AUX
- V-PART
- gesplitste VP: `V-AUX ... V-PART`

## Stabiel verklaard

- openen van `.opn` met `MAPPING_V3`
- `.opn` save met verplichte extensie `.opn`
- roundtrip behoud van `STRUCTURE` en `MAPPING_V3`
- validatie van core-placementregels
- generatie van `best` output
- expected-output manifest voor geldige corevoorbeelden
- expected-fail manifest voor invalid corevoorbeelden
- regressiechecker
- technische regel: alle `.md`-bestanden staan onder `md/`

## Checkerstatus

```text
Mapping V3 regression: 13 pass, 0 fail
MD folder check: PASS
```

## Buiten scope voor dit checkpoint

- WH / vraagzinnen
- NEG / negatie
- TIME / PLACE / bijwoorden
- DET / lidwoorden
- FRAME.graph
- lexicon
- UI/rendering/view-opties

Deze onderdelen blijven expliciet latere fasen.

## Geen codewijzigingen

Dit checkpoint wijzigt geen Java-code, generatorlogica, validatorlogica, UI of rendering.
