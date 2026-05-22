# PATCH MANIFEST v4.25.3 — Role detection diagnostics

## Doel

Voorbereiding voor Functional Grammar role-box layout, zonder de stabiele `projection_box` standaardlayout te veranderen.

## Wijzigingen

- Herkenning van FG-rolnamen in labels:
  - `pred`, `predicate`, `predicaat`
  - `agens`, `agent`, `actor`
  - `patiens`, `patient`, `theme`, `thema`
  - `recipiens`, `recipient`, `beneficiens`, `beneficiary`
  - `instrument`, `middel`
  - `locatief`, `locative`, `plaats`, `location`
  - `tijd`, `time`, `tempus`
- Role diagnostics worden geschreven naar:
  - operation log entry;
  - console, alleen als bekende FG-rollen aanwezig zijn.
- Default blijft `projection_box`.
- `role_box` blijft experimenteel/hidden.
- Geen GUI-instelling toegevoegd.
- Geen bedoelde wijziging in standaard Language Tree rendering.

## Verwachte test

De drie v4.25.1 testgraphs moeten visueel ongewijzigd OK blijven.

## Build checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK
