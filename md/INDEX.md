# MD index

Alle markdown-documentatie in deze zip staat onder deze map.

## Hoofdmap

- `README.md` — oorspronkelijke project-README
- `CHANGELOG.md` — wijziglog

## Mapping-bronnen

- `sources/mapping-v3/` — actuele project-/mappingbronnen

## Meta-notities

- `meta-inf/` — fase-notities en tijdelijke placeholders
- `meta-inf/frame_md.tmp` — placeholder voor latere Frame/WH/DET/NEG/uitingstype-fasen

## Voorbeelden

- `examples/opn/mapping-v3-expected-output-manifest.md` — expected-output manifest

## Afspraak

Deze map is documentatie/config-context. De runtime-code gebruikt de `.opn`, `.java`, `.class`, `.bat` en voorbeeldbestanden buiten deze map.

## Mapping V40

- `md/meta-inf/2026-04-27-mapping-v40-md-folder-check.md`
- `run-md-folder-check.bat`
- `tools/check-md-folder.sh`
- `tools/CheckMdFolder.java`

## Mapping v3.10 stable checkpoint

- `md/meta-inf/2026-04-27-mapping-v310-core-stable-checkpoint.md`
- `md/sources/mapping-v3/current-state.md` — bijgewerkt naar `MAPPING_V3_CORE_STABLE`

## Mapping V4.0 scope freeze

- `md/sources/mapping-v4/README.md` — V4 startnotitie en basisafspraken
- `md/sources/mapping-v4/current-state.md` — actuele V4.0-status
- `md/sources/mapping-v4/v4-phasing.md` — voorgestelde V4-fasering
- `md/sources/mapping-v4/scope-freeze.md` — V4.0 scope-freeze regels
- `md/meta-inf/2026-04-29-mapping-v40-start.md` — V4.0 fase-manifest

## Mapping V4.3 minimal WH

- `md/sources/mapping-v4/wh-v43.md` — V4.3 minimal WH behavior
- `md/meta-inf/2026-04-29-mapping-v43-wh-minimal.md` — V4.3 phase manifest
- `md/examples/opn/mapping-v4-wh-v43-expected-output-manifest.md` — V4.3 expected-output manifest
- `examples/opn/mapping-v4-wh/` — valid V4.3 WH examples
- `examples/opn/mapping-v4-wh-invalid/` — invalid V4.3 WH examples
- `tools/MappingV4RegressionChecker.java` — V4 checker for V3 core, V4.1 NEG/TIME/PLACE and V4.3 WH
- `run-mapping-v4-checker.bat` — V4 checker entry point

## Mapping V4.4 DET scope

- `md/sources/mapping-v4/det-v44.md` — V4.4 DET scope and design boundary
- `md/meta-inf/2026-04-30-mapping-v44-det-scope.md` — V4.4 phase manifest
- `md/examples/opn/mapping-v4-det-v44-expected-output-manifest.md` — documentation-only expected-output manifest for later V4.5 DET implementation
- Runtime behavior remains `MAPPING_V4_3_WH_MINIMAL`; no Java, generator, validator, checker, UI or rendering changes.

## Mapping V4.5 minimal DET

- `md/sources/mapping-v4/det-v45.md` — V4.5 minimal DET generator / validator behavior
- `md/meta-inf/2026-04-30-mapping-v45-det-minimal.md` — V4.5 phase manifest
- `md/examples/opn/mapping-v4-det-v45-expected-output-manifest.md` — V4.5 expected-output manifest
- `examples/opn/mapping-v4-det/` — valid V4.5 DET examples
- `examples/opn/mapping-v4-det-invalid/` — invalid V4.5 DET examples
- `tools/MappingV4RegressionChecker.java` — V4 checker expanded through V4.5 DET
- `run-mapping-v4-checker.bat` — V4.5 checker entry point

## Mapping V4.5.1 Info label clarification

- `md/sources/mapping-v4/info-label-v451.md` — V4.5.1 Info-window label clarification
- `md/meta-inf/2026-04-30-mapping-v451-info-label.md` — V4.5.1 phase manifest
- Runtime label changed from `generated: best: ...` to `generated best: ...`
- No generator, validator, parser, checker, mapping-rule or graph-rendering change.
- `md/sources-md-zip/Mapping_V4-26-04-30--v451-all-md-sources-bundel.md` — V4.5.1 all-md source bundle

## Mapping V4.5.2 stable handoff

- `md/sources/mapping-v4/stable-handoff-v452.md` — V4.5.2 stable handoff checkpoint
- `md/meta-inf/2026-05-01-mapping-v452-stable-handoff.md` — V4.5.2 phase manifest
- Runtime behavior remains `MAPPING_V4_5_1_INFO_LABEL`; this phase is documentation/package only.
- `md/sources-md-zip/Mapping_V4-26-05-01--v452-all-md-sources-bundel.md` — V4.5.2 all-md source bundle
## Mapping V4.6 FRAME.graph scope

- `md/sources/mapping-v4/frame-graph-v46.md` — V4.6 FRAME.graph scope and architecture boundary
- `md/meta-inf/2026-05-01-mapping-v46-frame-graph-scope.md` — V4.6 phase manifest
- `md/examples/opn/mapping-v4-frame-v46-expected-output-manifest.md` — documentation-only expected-output manifest for later FRAME.graph implementation
- `md/PATCH-MANIFEST-v46-frame-graph-scope.md` — V4.6 patch manifest
- Runtime behavior remains `MAPPING_V4_5_1_INFO_LABEL`; no Java, generator, validator, checker, UI or rendering changes.
- `md/sources-md-zip/Mapping_V4-26-05-01--v46-all-md-sources-bundel.md` — V4.6 all-md source bundle

## Mapping V4.7 FRAME.graph metadata read / count

- `md/sources/mapping-v4/frame-graph-v47.md` — V4.7 FRAME.graph metadata read/count behavior
- `md/meta-inf/2026-05-01-mapping-v47-frame-graph-metadata.md` — V4.7 phase manifest
- `md/examples/opn/mapping-v4-frame-v47-expected-output-manifest.md` — V4.7 expected-output manifest
- `md/PATCH-MANIFEST-v47-frame-graph-metadata.md` — V4.7 patch manifest
- `examples/opn/mapping-v4-frame/` — valid V4.7 FRAME.graph metadata examples
- Runtime reads `FRAME_GRAPH` metadata, counts frames/slots and reports it in Info.
- Generation, placement validation, rendering and graph mutation remain unchanged.
- `md/sources-md-zip/Mapping_V4-26-05-01--v47-all-md-sources-bundel.md` — V4.7 all-md source bundle

## Mapping V4.8 FRAME.graph slot validation scope

- `md/sources/mapping-v4/frame-graph-v48.md` — V4.8 FRAME.graph slot-validation scope and validator boundary
- `md/meta-inf/2026-05-01-mapping-v48-frame-graph-slot-validation-scope.md` — V4.8 phase manifest
- `md/examples/opn/mapping-v4-frame-v48-expected-output-manifest.md` — documentation-only expected-output manifest for later slot validation
- `md/PATCH-MANIFEST-v48-frame-graph-slot-validation-scope.md` — V4.8 patch manifest
- Runtime behavior remains `MAPPING_V4_7_FRAME_GRAPH_METADATA`; no Java, generator, validator, checker, UI or rendering changes.
- `md/sources-md-zip/Mapping_V4-26-05-01--v48-all-md-sources-bundel.md` — V4.8 all-md source bundle
## Mapping V4.9 FRAME.graph minimal slot validator

- `md/sources/mapping-v4/frame-graph-v49.md` — V4.9 FRAME.graph minimal slot validator behavior
- `md/meta-inf/2026-05-02-mapping-v49-frame-graph-minimal-slot-validator.md` — V4.9 phase manifest
- `md/examples/opn/mapping-v4-frame-v49-expected-output-manifest.md` — V4.9 expected-output manifest
- `md/PATCH-MANIFEST-v49-frame-graph-minimal-slot-validator.md` — V4.9 patch manifest
- `examples/opn/mapping-v4-frame-invalid/` — invalid V4.9 FRAME.graph validator examples
- Runtime now reports `Frame graph: ...; frame validation: ...`.
- Generated output remains based on explicit `MAPPING_V4` placement rules and is not suppressed by FRAME.graph validation failures.


## Mapping V4.10 Lexicon metadata read / count

- `md/sources/mapping-v4/lexicon-v410.md` — V4.10 Lexicon metadata read/count behavior
- `md/meta-inf/2026-05-02-mapping-v410-lexicon-metadata.md` — V4.10 phase manifest
- `md/examples/opn/mapping-v4-lexicon-v410-expected-output-manifest.md` — V4.10 expected-output manifest
- `md/PATCH-MANIFEST-v410-lexicon-metadata.md` — V4.10 patch manifest
- `examples/opn/mapping-v4-lexicon/` — valid V4.10 Lexicon metadata examples
- Runtime now reports `Lexicon: ... entries, metadata only`.
- Generated output remains based on explicit `MAPPING_V4` placement rules and is not affected by `LEXICON`.
## Mapping V4.10.1 straight-edge midpoint cleanup

- `md/sources/mapping-v4/midpoint-v4101.md` — V4.10.1 straight-edge midpoint cleanup behavior
- `md/meta-inf/2026-05-02-mapping-v4101-straight-edge-midpoint-cleanup.md` — V4.10.1 phase manifest
- `md/PATCH-MANIFEST-v4101-straight-edge-midpoint-cleanup.md` — V4.10.1 patch manifest
- Ordinary straight undirected edges no longer draw the visible center/midpoint marker.
- Curved/orthogonal edge markers and directed arrows are unchanged.


## Mapping V4.11 Lexicon validation / coupling scope

- `md/sources/mapping-v4/lexicon-validation-v411.md` — V4.11 Lexicon validation/coupling scope and validator boundary
- `md/meta-inf/2026-05-02-mapping-v411-lexicon-validation-scope.md` — V4.11 phase manifest
- `md/examples/opn/mapping-v4-lexicon-v411-expected-output-manifest.md` — documentation-only expected-output manifest for later Lexicon validation implementation
- `md/PATCH-MANIFEST-v411-lexicon-validation-scope.md` — V4.11 patch manifest
- Runtime behavior remains `MAPPING_V4_10_1_STRAIGHT_EDGE_MIDPOINT_CLEANUP`; no Java, generator, validator, checker, UI or rendering changes.

## Mapping V4.12 Lexicon validator

- `md/sources/mapping-v4/lexicon-validation-v412.md` — V4.12 Lexicon validator behavior
- `md/meta-inf/2026-05-02-mapping-v412-lexicon-validator.md` — V4.12 phase manifest
- `md/examples/opn/mapping-v4-lexicon-v412-expected-output-manifest.md` — V4.12 expected-output manifest
- `md/PATCH-MANIFEST-v412-lexicon-validator.md` — V4.12 patch manifest
- `examples/opn/mapping-v4-lexicon-invalid/` — invalid V4.12 Lexicon validator examples
- Runtime now reports `Lexicon: ...; lexicon validation: ...`.
- Generated output remains based on explicit `MAPPING_V4` placement rules and is not suppressed by Lexicon validation failures.

## Mapping V4.13 Lexicon / morphology / frame-selection scope

- `md/sources/mapping-v4/lexicon-morphology-frame-selection-v413.md` — V4.13 scope for later Lexicon, morphology and frame-selection work
- `md/meta-inf/2026-05-02-mapping-v413-lexicon-morphology-frame-selection-scope.md` — V4.13 phase manifest
- `md/examples/opn/mapping-v4-lexicon-v413-expected-output-manifest.md` — documentation-only expected-output target manifest for later morphology/frame-selection behavior
- `md/PATCH-MANIFEST-v413-lexicon-morphology-frame-selection-scope.md` — V4.13 patch manifest
- Runtime behavior remains `MAPPING_V4_12_LEXICON_VALIDATOR`; no Java, generator, validator, checker, UI or rendering changes.

## Mapping V4.14 morphology metadata validation scope

- `md/sources/mapping-v4/morphology-v414.md` — V4.14 morphology metadata validation scope and validator boundary
- `md/meta-inf/2026-05-02-mapping-v414-morphology-metadata-validation-scope.md` — V4.14 phase manifest
- `md/examples/opn/mapping-v4-morphology-v414-expected-output-manifest.md` — documentation-only expected-output manifest for later morphology validation implementation
- `md/PATCH-MANIFEST-v414-morphology-metadata-validation-scope.md` — V4.14 patch manifest
- Runtime behavior remains `MAPPING_V4_12_LEXICON_VALIDATOR`; no Java, generator, validator, checker, UI or rendering changes.


## Mapping V4.15 morphology metadata validator target

- `md/sources/mapping-v4/morphology-v415.md` — V4.15 target for later morphology metadata validator behavior
- `md/meta-inf/2026-05-02-mapping-v415-morphology-metadata-validator-target.md` — V4.15 phase manifest
- `md/examples/opn/mapping-v4-morphology-v415-expected-output-manifest.md` — documentation-only expected-output manifest for later morphology validation
- `md/PATCH-MANIFEST-v415-morphology-metadata-validator-target.md` — V4.15 patch manifest
- Runtime behavior remains `MAPPING_V4_12_LEXICON_VALIDATOR`; no Java, generator, validator, checker, UI or rendering changes.

## Mapping V4.16 morphology metadata validator

- `md/sources/mapping-v4/morphology-v416.md` — V4.16 runtime morphology metadata validator behavior
- `md/meta-inf/2026-05-02-mapping-v416-morphology-metadata-validator.md` — V4.16 phase manifest
- `md/examples/opn/mapping-v4-morphology-v416-expected-output-manifest.md` — V4.16 expected-output manifest
- `md/PATCH-MANIFEST-v416-morphology-metadata-validator.md` — V4.16 patch manifest
- `examples/opn/mapping-v4-morphology/` — valid V4.16 morphology examples
- `examples/opn/mapping-v4-morphology-invalid/` — invalid V4.16 morphology examples
- Runtime now reports `morphology validation: ...` on the Lexicon Info line when morphology metadata is present.
- Generated output remains based on explicit `MAPPING_V4` placement rules and is not suppressed by morphology validation failures.


## Mapping V4.17 explicit frame-selection scope

- `md/sources/mapping-v4/frame-selection-v417.md` — V4.17 explicit frame-selection scope and validator boundary
- `md/meta-inf/2026-05-03-mapping-v417-frame-selection-scope.md` — V4.17 phase manifest
- `md/examples/opn/mapping-v4-frame-selection-v417-expected-output-manifest.md` — documentation-only expected-output manifest for later frame-selection validation
- `md/PATCH-MANIFEST-v417-frame-selection-scope.md` — V4.17 patch manifest
- Runtime behavior remains `MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR`; no Java, generator, validator, checker, UI or rendering changes.

## Mapping V4.18 explicit frame-selection validator target

- `md/sources/mapping-v4/frame-selection-v418.md` — V4.18 explicit frame-selection validator target and diagnostic boundary
- `md/meta-inf/2026-05-03-mapping-v418-frame-selection-validator-target.md` — V4.18 phase manifest
- `md/examples/opn/mapping-v4-frame-selection-v418-expected-output-manifest.md` — documentation-only expected-output target manifest for the next Java frame-selection validator
- `md/PATCH-MANIFEST-v418-frame-selection-validator-target.md` — V4.18 patch manifest
- Runtime behavior remains `MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR`; no Java, generator, runtime validator, checker, UI or rendering changes.

## Mapping V4.20 Language Tree OPN test slice

- `md/sources/mapping-v4/language-tree-v420.md` — first testable language-tree OPN behavior
- `md/meta-inf/2026-05-03-mapping-v420-language-tree-opn-test.md` — V4.20 phase manifest
- `md/examples/opn/mapping-v4-language-tree-v420-expected-output-manifest.md` — V4.20 expected-output manifest
- `examples/opn/language-tree-v420/` — first real language-tree OPN examples
- `tools/LanguageTreeRegressionChecker.java` — language-tree checker
- `run-language-tree-checker.bat` — checker entry point

## Mapping V4.20.1 Language Tree OPN open-grid fix

- `md/sources/mapping-v4/language-tree-v4201.md` — corrective behavior/test-data slice
- `md/meta-inf/2026-05-03-mapping-v4201-language-tree-opn-open-grid-fix.md` — V4.20.1 phase manifest
- `md/PATCH-MANIFEST-v4201-language-tree-opn-open-grid-fix.md` — V4.20.1 patch manifest
- `examples/opn/language-tree-v420/` — revised OPN examples with source nodes on OpenGraphGrid and unique horizontal source rows
- `tools/LanguageTreeRegressionChecker.java` — checker now validates grid alignment, unique rows and grid containment

- `md/sources-md-zip/Mapping_V4-26-05-03--v4201-all-md-sources-bundel.md` — V4.20.1 all-md source bundle

## Mapping V4.20.2 OPN preferences and tree info

- `md/sources/mapping-v4/language-tree-v4202-opn-preferences-tree-info.md` — V4.20.2 behavior description
- `md/meta-inf/2026-05-05-mapping-v4202-opn-preferences-tree-info.md` — V4.20.2 phase manifest
- `md/PATCH-MANIFEST-v4202-opn-preferences-tree-info.md` — V4.20.2 patch manifest
- Runtime adds persisted OPN directory, selectable `.opn`/`.graph` filters, jar version line in Info/Log, and S-tree/V-tree type reporting.
- `md/sources-md-zip/Mapping_V4-26-05-05--v4202-all-md-sources-bundel.md` — V4.20.2 all-md source bundle

## Mapping V4.20.3 OpenGraph actions menu and local buttons

- `md/sources/mapping-v4/language-tree-v4203-opengraph-actions-menu-buttons.md` — V4.20.3 behavior description
- `md/meta-inf/2026-05-05-mapping-v4203-opengraph-actions-menu-buttons.md` — V4.20.3 phase manifest
- `md/PATCH-MANIFEST-v4203-opengraph-actions-menu-buttons.md` — V4.20.3 patch manifest


## v4.20.4

- `PATCH-MANIFEST-v4204-build-launcher-menu-fix.md`
- `meta-inf/2026-05-05-mapping-v4204-build-launcher-menu-fix.md`


## v4.21.4

- Patch manifest: `md/PATCH-MANIFEST-v4214-zinstype-visible-toolbar.md`.

## v4.21.3

- Patch manifest: `md/PATCH-MANIFEST-v4213-language-tree-ui-source-reset.md`.
- Structure selector separated from draw; Simple/Frame/Anafoor use Draw, Language Tree uses Zinstype draw.

## v4.21.2

- Null-safe speciale selectie bij Language Tree direct draw.
- OpenGraph foutmeldingen ook naar DOS/console.
- Patch manifest: `md/PATCH-MANIFEST-v4212-language-tree-selection-nullsafe-dos-error.md`.

## v4.21.1

- Zinstypeknoppen voeren direct Draw/Redraw uit.
- Draw-knop verwijderd uit de editorbalk.
- Structure type staat direct in het edit-window: Simple, Language Tree / Phrase, Frame.
- Zie `PATCH-MANIFEST-v4211-language-tree-direct-zinstype-draw.md`.

## v4.21.0

- Hoverteksten direct zichtbaar.
- Default OpenGraph type: Language Tree / Phrase.
- Zie `PATCH-MANIFEST-v4210-language-tree-tooltip-phrase-default.md`.


## v4.20.9

- Startup fix for missing icon resources when launching from precompiled `out\` classes.
- `out\images`, `out\help`, and `out\config` included in clean zip.

## v4.20.8

- Persistent zinstype toggle-button selection.
- Rule preview moved to hover tooltips.
- Compact Language Tree overlay prevents preview overlap with the DS tree.
- Topicalisation preview uses selected categorial nodes and grouped lexical labels such as `NP(de man)`.

## v4.20.7

- `PATCH-MANIFEST-v4207-language-tree-zinstype-profile-preview.md`
- `meta-inf/2026-05-12-mapping-v4207-language-tree-zinstype-profile-preview.md`
- `sources/mapping-v4/language-tree-v4207-zinstype-profile-preview.md`
- Alternative C implemented: editor-window `Zinstype` buttons plus visible Language Tree rule preview near the lexical axis.

## v4.20.6

- Slot click guard for Language Tree virtual axis markers.
- Native `lextest.graph` language-tree test file.
- Initial Zinstype/placement-rule alternatives documented.

## v4.20.5

- `PATCH-MANIFEST-v4205-language-tree-slots-nary.md`
- `meta-inf/2026-05-12-mapping-v4205-language-tree-slots-nary.md`
