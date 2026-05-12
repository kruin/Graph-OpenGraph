# 2026-04-27 — Mapping V3.2 argumenten en placement-resolutie

Scope:
- Alleen mapping/generator/validator-laag.
- Geen UI-wijzigingen.
- Geen graph rendering-wijzigingen.

Wijzigingen:
- Compacte placement-regels uit de spec worden genormaliseerd naar interne relaties:
  - `left_of_V` → `left_of | V`
  - `right_of_V` → `right_of | V`
  - `before_Theme` → `before | THEME`
  - `before_V_PART` → `before | V-PART`
- `anchor` wordt als declaratieve verb-domain regel geaccepteerd.
- `clause_end` plaatst de rol na de normale clausale rollen, maar vermijdt een conflict met rollen die `after_clause` hebben.
- Role-target resolutie is robuuster voor hoofdletters en underscore/koppeltekenvarianten.
- `after_aux_before_object` gebruikt `Patiens` als default target als geen target is opgegeven; expliciete targets zoals `THEME` blijven mogelijk.
- Toegevoegd voorbeeld:
  - `examples/opn/mapping-v3-recipient-theme/voorbeeldzin-recipient-theme.mapping-v3-compact.opn`

Controle:
- Reflectietest compact rule `RECIPIENT|before_Theme|core` → `[RECIPIENT, before, THEME, core]`.
- Reflectietest generator best-output:
  - `gisteren vrouw heeft niet man boek gegeven daar`
- `javac @sources.txt` OK.
- Alleen bestaande waarschuwingen:
  - `new Integer(x)` deprecation in `GraphFileActions.java`
  - bestaande deprecation/unchecked meldingen elders.
