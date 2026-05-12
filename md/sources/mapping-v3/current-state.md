# Current State (Mapping V3)

Status:
- MAPPING_V3_CORE_STABLE
- Open OPN: OK
- Save OPN: OK (force .opn)
- Roundtrip: OK
- Mapping v3: OK
- Validator: OK
- Generator: OK
- Expected-output checker: OK (13 pass, 0 fail)
- MD folder check: OK

Core ondersteund en gestabiliseerd:
- Agens
- Patiens
- RECIPIENT
- THEME
- V
- V-AUX
- V-PART
- gesplitste VP: V-AUX ... V-PART

Aanwezig maar voorlopig buiten core-stable scope:
- NEG
- TIME, PLACE
- WH/vraagzinnen
- DET/lidwoorden
- FRAME.graph
- lexicon
- UI/rendering/view-opties

Architectuur:
- STRUCTURE = view
- MAPPING_V3 = logica
- Geen graph-mutatie
- Output via generator
- Validatie en generatie lopen via de lexicale as

Core regressiestatus:
- Geldige corevoorbeelden: expected-output manifest aanwezig
- Ongeldige corevoorbeelden: expected-fail manifest aanwezig
- Checker: 13 pass, 0 fail

Volgende inhoudelijke fase pas kiezen na dit checkpoint:
- WH / vraagzinnen
- DET / lidwoorden
- FRAME.graph
- lexicon
- view-optie: verticale lexicale as
