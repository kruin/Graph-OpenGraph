# Mapping v3.5 — Expected output manifest

## Doel

Vastleggen wat de core Mapping V3 regressieset moet opleveren.

## Toegevoegd

- `examples/opn/mapping-v3-core/EXPECTED.txt`
- `examples/opn/mapping-v3-core-invalid/EXPECTED-FAIL.txt`
- `examples/opn/mapping-v3-expected-output-manifest.md`

## Gedrag

Geen Java-code gewijzigd.

Deze stap legt alleen de verwachte testuitkomsten vast:

- geldige core `.opn`-bestanden: `0 fail` en exacte `generated: best`
- ongeldige core `.opn`-bestanden: fail zichtbaar in Info, loadfile genoemd, geen generated best

## Scope

In scope:

- Agens, Patiens, RECIPIENT, THEME
- V, V-AUX, V-PART
- gesplitste VP
- expected-output en expected-fail manifesten

Buiten scope:

- WH/vraagzinnen
- NEG
- TIME/PLACE/bijwoorden
- DET-splitsing
- FRAME.graph
- lexicon
- UI/rendering
