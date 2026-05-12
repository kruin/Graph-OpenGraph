# Mapping V3.3 core regression

## Doel

Stabiliseer de kern van Mapping V3 zonder FRAME.graph, bijwoorden, negatie, WH-vragen of DET-splitsing.

## Scope

Wel:
- declaratieve zinnen
- Agens / Patiens
- RECIPIENT / THEME
- V / V-AUX / V-PART
- gesplitste VP (`heeft ... gebreid/gegeven`)
- multiword THEME als één item (`een boek`)

Niet:
- vraagzinnen / WH
- negatie
- TIME / PLACE / bijwoorden
- DET als aparte rol
- FRAME.graph-integratie
- UI/rendering

## Toegevoegd

`examples/opn/mapping-v3-core/`:

1. `01-vrouw-breit-trui.opn`
2. `02-vrouw-heeft-trui-gebreid.opn`
3. `03-vrouw-geeft-man-boek.opn`
4. `04-vrouw-heeft-man-boek-gegeven.opn`
5. `05-vrouw-heeft-man-een-boek-gegeven.opn`

## Placeholder

`meta-inf/frame_md.tmp` reserveert de latere fasen:
- WH/vraagzin
- negatie
- DET/lidwoorden
- FRAME.graph
