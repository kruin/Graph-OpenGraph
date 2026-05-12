# PATCH MANIFEST — v4.20.7 Language Tree zinstype profile preview

## Purpose

Implement Alternative C for Language Tree placement-rule control: zinstype profile buttons plus visible rule preview.

## User-visible changes

- New `Zinstype:` group in the graph editor OpenGraph action bar.
- Buttons: `Basis`, `Bijzin`, `Stellend`, `Ja/nee`, `WH`, `Topicalisatie`.
- Active Language Tree overlay shows the selected profile and rule preview near the lexical axis.
- Overlay still explicitly shows `LANGUAGE TREE`.

## Behavioral constraints

- DS tree is not modified by zinstype buttons.
- `slot0` and `slot1` remain virtual lexical-axis positions.
- Rule preview is declarative in this slice; physical lexical-axis reordering is a later step.
- Clicks on Language Tree virtual overlay labels are consumed to avoid accidental node creation.

## Test status

- `java -cp out tools.LanguageTreeRegressionChecker` → `3 pass, 0 fail`
- `java -cp out tools.MappingV4RegressionChecker` → `53 pass, 0 fail`
- `java -cp out tools.MappingV3RegressionChecker` → `13 pass, 0 fail`
- `bash tools/check-md-folder.sh` → `PASS`
