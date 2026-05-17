# Chatlog update — graph tools

User request: add documentation in English and Dutch.

Applied changes:

- Added bilingual root README.
- Added `README.NL.md` and `README.EN.md`.
- Added Dutch documentation under `docs/nl/`.
- Added English documentation under `docs/en/`.
- Added `changelog-v10.md`.
- Rebuilt `sources-md-only.zip`.

No source-code behavior was changed in this version.

---

# Changelog

## 2026-05-15T20-42 — v4 preview

- Toegevoegd: `tc_f3_graph_lister_png.bat` voor Total Commander F3: `.graph` wordt tijdelijk naar PNG gerenderd en geopend in TC Lister.
- Toegevoegd: debugvariant voor F3/Lister-preview.
- Toegevoegd: `maak_graph_previews.bat` voor sidecar previews `*.preview.png` en `*.preview.svg`.
- Toegevoegd: Windows Open/dubbelklik-associatie via `install_windows_graph_open_assoc.bat`.
- Gedocumenteerd onderscheid tussen TC F3, TC Ctrl+Q Quick View en Windows Preview Pane.
- `graph_to_images.py` uitgebreid met `--suffix` voor previewbestandsnamen.

# Changelog

## 2026-05-14 14:18 Amsterdam — graph tools v2

Aanleiding:

- Total Commander meldde: `Toegang geweigerd tot bestand ... "Toepassing is niet gevonden"`.
- Graph Viewer meldde: `regel 8 is geen geheel getal: '319.0'` bij `spiral.graph`.

Wijzigingen:

- Parser accepteert floatcoördinaten voor dots en edges.
- Parser leest het `.graph`/`.opn`-bestand regelgebaseerd, niet meer als losse integer-tokens.
- Per dot wordt de zesde regel met adjacency/metadata correct overgeslagen.
- Lege labels zijn toegestaan.
- Edge-sectie wordt gelezen en meegetekend.
- Viewer en renderer delen dezelfde parser.
- Nieuwe install-batch voor herstel van `.graph`-associatie.
- Rendererbatch blijft current-dir-gebaseerd en heeft configuratie bovenin de `.bat`.
- Voorbeelden toegevoegd: `space3_eerste_vrije_plek.graph` en `spiral.graph` inclusief gegenereerde SVG/PNG.

## 2026-05-14 15:02 — v3 TC F3 direct

- Toegevoegd: `tc_f3_graph_viewer.bat` als Total Commander F3-wrapper.
- Toegevoegd: fallback naar Total Commander Lister voor niet-`.graph`- en `.opn`-bestanden via `%COMMANDER_EXE% /S=L`.
- Toegevoegd: `tc_f3_graph_viewer_debug.bat` voor diagnose van TC-parameters.
- Toegevoegd: documentatie `docs/Total_Commander_F3_direct.md`.


## 2026-05-15 — v6 native thumbnails

Gebruiker vroeg na de Preview Pane-oplossing om de ontbrekende echte Explorer-thumbnail-provider ook te maken. Toegevoegd: native C++ Shell Extension voor `.graph`-miniaturen, build/install/uninstall/check/cache scripts, documentatie en hoofdmap-wrappers.


## 2026-05-15 — v7 buildfix

Gebruiker meldde bij `install_windows_thumbnail_provider.bat`:

```text
cl : Command line error D8003 : missing source filename
BUILD FAILED.
```

Herstel: thumbnail buildbatch vervangen door response-file aanpak en extra bestandscontroles.


## v8 — thumbnail-provider register/build fix

- Gebruiker meldde dat `install_windows_thumbnail_provider.bat` na `Registering thumbnail provider for current user...` bleef staan.
- Build gaf daarnaast veel waarschuwingen doordat `/OUT:C:\...` als `/O...` opties werd gelezen.
- Herstel: GDI+ niet meer in `DllMain`; build gesplitst in compile + link.

## 2026-05-15 — v9 clean render

Gebruiker: thumbnails werken, maar zien er niet uit.

Aanpassing:
- clean renderstijl toegevoegd aan Windows thumbnail-provider;
- Preview Pane handler en Python renderer afgestemd op dezelfde stijl;
- content-crop als standaard;
- labels/grid voor previews teruggebracht;
- F3/Total Commander wrapper gebruikt nu tijdelijke cleane PNG.

## 2026-05-17 — graph tools v11

- Added `.opn`/`.OPN` support alongside `.graph`.
- Updated renderer, viewer, Total Commander wrappers, Windows Open association, Windows Preview Pane registration, and Windows thumbnail-provider registration.
- Added example `.opn` files and bilingual documentation updates.
