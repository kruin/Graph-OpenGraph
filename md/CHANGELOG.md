
## v4.21.8 — Projection defaults restored

- Restored default projection configuration: left `LEX`, right `SYNT`, bottom `LF`, top `pm`.
- Projection-capable structures now enable all four projection sides by default.
- Simple tree still suppresses projections.
- Side captions remain visible when enabled, also for placeholder sides such as top `pm`.

## 2026-05-03 — Mapping V4.20.1 Language Tree OPN open-grid fix

### Fixed
- Pipe-style `STRUCTURE_NODES` now load on the 20 px OpenGraphGrid instead of using the old 25 px coordinate scaling.
- Pipe-style OPN grid sizing now expands to contain the largest source grid coordinate.
- The three Language Tree OPN examples no longer place multiple source nodes on the same horizontal grid row.
- Language Tree regression checker now verifies OpenGraphGrid alignment, unique source horizontal rows and source nodes inside the grid display window.

### Preserved
- No generated utterance rule changes.
- No projection rendering algorithm changes.
- No FRAME.graph, Lexicon or morphology validation changes.
- No graph mutation behavior changes.

### Actual checks
- Mapping V4.20.1 Language Tree OPN regression checker: `3 pass, 0 fail`.
- Mapping V4.16 morphology metadata validator regression checker: `53 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-03 — Mapping V4.20 Language Tree OPN test slice

### Added
- Added first testable Language Tree OPN examples.
- Added Language Tree OPN auto-open projection defaults for `STRUCTURE_TYPE: LANGUAGE_TREE`.
- Added Language Tree regression checker and Windows runner.

### Changed
- `graphStructure/Graph.java` stores whether an OPN should open as a language tree.
- `userInterface/GraphFileActions.java` activates LEX-left/SYN-right projection context on Language Tree OPN open.
- Mapping V4 checker accepts complementizer role `C` for the subordinate-clause language-tree example.

### Actual checks
- Mapping V4.20 Language Tree OPN regression checker: `3 pass, 0 fail`.
- Mapping V4.16 morphology metadata validator regression checker: `53 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-03 — Mapping V4.18 explicit frame-selection validator target

### Added
- Added V4.18 explicit frame-selection validator target document.
- Added V4.18 documentation-only expected-output target manifest for the next frame-selection validator slice.
- Added V4.18 phase manifest and patch manifest.

### Scope
- Documentation-only implementation target.
- Fixes verbal `LEXICON` `frame:<name>` rows as selected-frame source for the next behavior slice.
- Defines selected-frame validation against selected frames, not every frame listed in `FRAME_GRAPH`.
- Defines exact first-slice diagnostic strings and future Info-line summary shape.
- Keeps multi-frame `FRAME_GRAPH` as inventory/context unless explicitly selected.

### Preserved
- Runtime behavior remains V4.16.
- No Java source, class, jar, parser, generator, runtime validator, checker, UI, rendering, graph-mutation or example-semantics changes.
- No automatic frame selection, role inference, automatic lexical insertion, generation from `LEXICON` or generation from `FRAME_GRAPH`.

### Actual checks
- Mapping V4.16 morphology metadata validator regression checker: `53 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-03 — Mapping V4.17 explicit frame-selection scope

### Added
- Added V4.17 explicit frame-selection scope document.
- Added V4.17 documentation-only expected-output target manifest for later frame-selection validation.
- Added V4.17 phase manifest and patch manifest.

### Scope
- Documentation-only phase.
- Defines `frame:<name>` on verbal `LEXICON` rows as the proposed explicit selector source for later frame-selection validation.
- Reserves multi-frame `FRAME_GRAPH` inventory handling for a later behavior slice.
- Defines that selected-frame validation should validate against selected frames, not every frame listed in `FRAME_GRAPH`.

### Preserved
- Runtime behavior remains V4.16.
- No Java source, class, jar, parser, generator, validator, checker, UI, rendering, graph-mutation or example-semantics changes.
- No automatic frame selection, role inference, automatic lexical insertion, generation from `LEXICON` or generation from `FRAME_GRAPH`.

### Actual checks
- Mapping V4.16 morphology metadata validator regression checker: `53 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-02 — Mapping V4.16 morphology metadata validator

### Added
- Added runtime morphology metadata validation for explicit morphology fields on `LEXICON` rows.
- Added valid morphology examples under `examples/opn/mapping-v4-morphology/`.
- Added invalid morphology examples under `examples/opn/mapping-v4-morphology-invalid/`.
- Added V4.16 expected-output manifest, phase manifest and patch manifest.

### Changed
- `graphStructure/Graph.java` now stores/appends morphology validation summary details on the Lexicon Info line.
- `userInterface/GraphFileActions.java` validates morphology metadata during OPN open.
- `tools/MappingV4RegressionChecker.java` now checks V4.16 morphology examples.

### Scope
- Diagnostics for unknown morphology feature, missing value, duplicate feature, feature/`pos` incompatibility and unknown value.
- Morphology diagnostics are informational only.
- Generated output remains based on explicit `MAPPING_V4` placement rules.

### Preserved
- No automatic inflection, surface-form generation, role inference, automatic lexical insertion, automatic frame selection, Lexicon rendering, graph mutation or generated-output changes.

### Actual checks
- Mapping V4.16 morphology metadata validator regression checker: `53 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-02 — Mapping V4.15 morphology metadata validator target

### Added
- Added V4.15 morphology metadata validator target document.
- Added V4.15 phase manifest.
- Added V4.15 expected-output target manifest for a later Java morphology validator.
- Added V4.15 patch manifest.
- Rebuilt package on the full V4.12 slim runtime zip and carried forward V4.13/V4.14 documentation-only phases.

### Scope
- Documentation-only implementation target.
- Fixes target morphology keys, value domains, compatibility boundaries and diagnostics for a later behavior slice.
- Keeps explicit `MAPPING_V4` lexical-axis mapping and explicit `form:<surface>` authoritative.
- Keeps morphology diagnostics informational in the intended next behavior slice.

### Preserved
- Runtime behavior remains V4.12.
- No Java source, class, jar, parser, generator, validator, checker, UI, rendering, graph-mutation or example-semantics changes.

### Actual checks
- Mapping V4.12 lexicon validation regression checker: `45 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-02 — Mapping V4.14 morphology metadata validation scope

### Added
- Added V4.14 morphology metadata validation scope document.
- Added V4.14 documentation-only expected-output target manifest.
- Added V4.14 phase manifest.
- Added V4.14 patch manifest.

### Scope
- Documentation-only phase.
- Reserves the first later morphology step as metadata validation only.
- Reserved future morphology keys: `tense`, `number`, `person`, `gender`, `case`, `mood`, `aspect`, `finite`.
- Future diagnostics may cover unknown feature, missing value, duplicate key, incompatible feature for `pos`, and unknown feature value.

### Preserved
- Runtime behavior remains V4.12.
- No Java source, class, jar, parser, generator, validator, checker, UI, rendering, graph-mutation or example-semantics changes.
- No automatic inflection, surface-form generation, role inference, automatic lexical insertion or automatic frame selection.

### Check status
- Mapping V4.12 lexicon validation regression checker: `45 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-02 — Mapping V4.13 Lexicon / morphology / frame-selection scope

### Added
- Added V4.13 Lexicon / morphology / frame-selection scope document.
- Added V4.13 phase manifest.
- Added V4.13 expected-output target manifest for later morphology and frame-selection phases.
- Added V4.13 patch manifest.

### Scope
- Documentation-only phase.
- Defines that morphology metadata and frame-selection behavior are reserved for later explicit behavior slices.
- Keeps explicit `MAPPING_V4` lexical-axis mapping as authoritative for generated output.
- Keeps Lexicon and FRAME.graph as diagnostic/context layers only.

### Preserved
- Runtime behavior remains V4.12.
- No Java source, class, jar, parser, generator, validator, checker, UI, rendering, graph-mutation or example-semantics changes.

### Actual checks
- Mapping V4.12 lexicon validation regression checker: `45 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-02 — Mapping V4.12 Lexicon validator

### Added
- Added runtime Lexicon validation diagnostics.
- Added invalid Lexicon examples under `examples/opn/mapping-v4-lexicon-invalid/`.
- Added V4.12 expected-output manifest and phase manifest.
- Added V4.12 patch manifest.

### Changed
- `graphStructure/Graph.java` now stores Lexicon validation summary details.
- `userInterface/GraphFileActions.java` validates Lexicon metadata during OPN open.
- `tools/MappingV4RegressionChecker.java` now checks V4.12 Lexicon validation examples.
- Valid Lexicon example expectations now report `lexicon validation: ...` instead of metadata-only.

### Scope
- Diagnostics for malformed row, missing key, missing lemma, missing form, duplicate lexical key, unknown lexical role, role absent from explicit `MAPPING_V4`, and missing `FRAME_GRAPH` frame reference when `FRAME_GRAPH` is present.
- Lexicon diagnostics are informational only.

### Preserved
- Generated output remains based on explicit `MAPPING_V4` placement rules.
- Lexicon validation failures do not suppress generated output.
- No role inference, automatic lexical insertion, automatic frame selection, morphology/inflection, lexicon rendering or graph mutation.

### Actual checks
- Mapping V4.12 lexicon validation regression checker: `45 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-02 — Mapping V4.11 Lexicon validation / coupling scope

### Added
- Added V4.11 lexicon validation/coupling scope document.
- Added V4.11 phase manifest.
- Added V4.11 expected-output target manifest for the later lexicon validator implementation.
- Added V4.11 patch manifest.

### Scope
- Documentation-only phase.
- Defines first minimal lexicon diagnostics for a later behavior phase: malformed rows, missing key/lemma/form, duplicate lexical key, unknown lexical role, role not present in explicit `MAPPING_V4`, and optional frame-reference mismatch when `FRAME_GRAPH` is present.
- Coupling remains diagnostic-only in the intended next behavior phase.

### Preserved
- Runtime behavior remains V4.10.1.
- No Java source, class, jar, parser, generator, validator, checker, UI, rendering, graph-mutation or example-semantics changes.
- Lexicon at runtime remains metadata read/count only.

### Actual checks
- Mapping V4.10 lexicon metadata regression checker: `39 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-02 — Mapping V4.10.1 straight-edge midpoint cleanup

### Fixed
- Ordinary straight undirected edges no longer draw a visible center/midpoint marker in normal rendering.
- This removes the visible blue midpoint on simple structure edges such as `S — V`.

### Preserved
- Curved and orthogonal undirected edges still draw their center/bend marker.
- Directed edge arrows are unchanged.
- No mapping, generator, validator, FRAME.graph, Lexicon metadata, graph-mutation or OPN semantics changes.

### Actual checks
- Mapping V4.10 lexicon metadata regression checker: `39 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-02 — Mapping V4.10 Lexicon metadata read / count

### Added
- Added runtime `LEXICON` metadata read/count behavior.
- Added Info-window summary for lexicon metadata.
- Added valid lexicon metadata examples under `examples/opn/mapping-v4-lexicon/`.
- Added `md/sources/mapping-v4/lexicon-v410.md`.
- Added `md/meta-inf/2026-05-02-mapping-v410-lexicon-metadata.md`.
- Added `md/examples/opn/mapping-v4-lexicon-v410-expected-output-manifest.md`.
- Added `md/PATCH-MANIFEST-v410-lexicon-metadata.md`.

### Changed
- `graphStructure/Graph.java` now stores Lexicon metadata summary details.
- `userInterface/GraphFileActions.java` reads `LEXICON` metadata during OPN open.
- `userInterface/GraphEditorInfoSupport.java` displays Lexicon metadata in Info.
- `tools/MappingV4RegressionChecker.java` now checks V4.10 lexicon metadata examples.

### Scope
- `LEXICON:` / `END_LEXICON:` metadata read/count only.
- Counts pipe-delimited lexical entries.
- Reports metadata in Info as `Lexicon: ... metadata only`.
- Generated output remains based on explicit `MAPPING_V4` placement rules.

### Preserved
- No graph mutation.
- No lexicon validation.
- No automatic role inference.
- No generated-output rule changes.
- No FRAME.graph validation behavior changes.
- No lexicon rendering.

### Actual checks
- Mapping V4.10 lexicon metadata regression checker: `39 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-02 — Mapping V4.9 FRAME.graph minimal slot validator

### Added
- Added runtime `FRAME_GRAPH` minimal slot validation.
- Added valid frame-validation expected outputs to existing FRAME.graph examples.
- Added invalid FRAME.graph examples under `examples/opn/mapping-v4-frame-invalid/`.
- Added `md/sources/mapping-v4/frame-graph-v49.md`.
- Added `md/meta-inf/2026-05-02-mapping-v49-frame-graph-minimal-slot-validator.md`.
- Added `md/examples/opn/mapping-v4-frame-v49-expected-output-manifest.md`.
- Added `md/PATCH-MANIFEST-v49-frame-graph-minimal-slot-validator.md`.

### Changed
- `graphStructure/Graph.java` now stores FRAME.graph validation summary details.
- `userInterface/GraphFileActions.java` validates FRAME.graph slots during OPN open.
- `tools/MappingV4RegressionChecker.java` now checks V4.9 frame-validation valid and invalid examples.
- Existing FRAME.graph expected output now reports `frame validation: ...`.

### Scope
- Required slot present / absent.
- Unknown frame slot role.
- Lexical semantic role not licensed by frame.
- Malformed `FRAME_GRAPH` row diagnostics.
- Generated output remains based on explicit `MAPPING_V4` placement rules.
- FRAME.graph validation failures do not suppress generated output in this phase.

### Preserved
- No graph mutation.
- No FRAME.graph rendering.
- No tree transformation / vooropplaatsing.
- No lexicon or automatic role inference.
- No generated-output rule changes.

### Actual checks
- Mapping V4.9 FRAME.graph minimal slot validator regression checker: `37 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-01 — Mapping V4.8 FRAME.graph slot validation scope

### Added
- Added `md/sources/mapping-v4/frame-graph-v48.md` as the V4.8 FRAME.graph slot-validation scope document.
- Added `md/meta-inf/2026-05-01-mapping-v48-frame-graph-slot-validation-scope.md` phase manifest.
- Added `md/examples/opn/mapping-v4-frame-v48-expected-output-manifest.md` as a documentation-only target manifest for later slot validation.
- Added `md/PATCH-MANIFEST-v48-frame-graph-slot-validation-scope.md`.
- Refreshed md-only source zip and all-md source bundle.

### Scope
- Documentation and expected-output manifest only.
- Defines the future minimal FRAME.graph slot validator boundary.
- First-slice semantic slot roles are limited to `Agens`, `Patiens`, `RECIPIENT` and `THEME`.
- Runtime behavior remains `MAPPING_V4_7_FRAME_GRAPH_METADATA`.

### Preserved
- No Java source, class, jar, parser, generator, validator, checker, mapping-rule, UI, rendering, graph-mutation or example-semantics changes.
- FRAME_GRAPH remains metadata read/count only at runtime.
- Generated utterances remain based on explicit `MAPPING_V4` lexical-axis placement rules.

### Actual checks
- Mapping V4.7 FRAME.graph metadata regression checker: `33 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-01 — Mapping V4.7 FRAME.graph metadata read / count

### Added
- Added `FRAME_GRAPH:` / `END_FRAME_GRAPH:` metadata read/count behavior.
- Added Info-window summary for FRAME.graph metadata.
- Added valid FRAME.graph metadata examples under `examples/opn/mapping-v4-frame/`.
- Added `md/sources/mapping-v4/frame-graph-v47.md`.
- Added `md/meta-inf/2026-05-01-mapping-v47-frame-graph-metadata.md`.
- Added `md/examples/opn/mapping-v4-frame-v47-expected-output-manifest.md`.
- Added `md/PATCH-MANIFEST-v47-frame-graph-metadata.md`.
- Refreshed md-only source zip and all-md source bundle.

### Changed
- `graphStructure/Graph.java`
- `userInterface/GraphFileActions.java`
- `userInterface/GraphEditorInfoSupport.java`
- `tools/MappingV4RegressionChecker.java`
- compiled class files and runtime jars

### Scope
- FRAME.graph metadata read/count only.
- Counts distinct frames and `slot:<role>` entries.
- Reports metadata in Info as `Frame graph: ... metadata only`.

### Preserved
- Generated utterances remain based on explicit `MAPPING_V4` lexical-axis placement rules.
- No frame-slot validation.
- No automatic role inference.
- No lexicon behavior.
- No graph mutation.
- No FRAME.graph rendering.
- No tree transformation / vooropplaatsing.

### Actual checks
- Mapping V4.7 FRAME.graph metadata regression checker: `33 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-01 — Mapping V4.6 FRAME.graph scope

### Added
- Added `md/sources/mapping-v4/frame-graph-v46.md` as the V4.6 FRAME.graph scope document.
- Added `md/meta-inf/2026-05-01-mapping-v46-frame-graph-scope.md` phase manifest.
- Added `md/examples/opn/mapping-v4-frame-v46-expected-output-manifest.md` as a documentation-only target manifest for later FRAME.graph implementation.
- Added `md/PATCH-MANIFEST-v46-frame-graph-scope.md`.
- Refreshed md-only source zip and all-md source bundle.

### Scope
- Documentation and expected-output manifest only.
- Defines `FRAME.graph` as a future semantic/frame context layer.
- `FRAME.graph` is not the generator.
- Existing `MAPPING_V4` lexical-axis mapping remains explicit.
- `STRUCTURE` remains a drawn view.

### Preserved
- No Java source, class, jar, parser, generator, validator, checker, mapping-rule, UI, rendering, graph-mutation or example-semantics changes.
- Runtime behavior remains the V4.5.1 Info label behavior.

### Actual checks
- Mapping V4.5 DET regression checker: `31 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-05-01 — Mapping V4.5.2 stable handoff

### Added
- Added V4.5.2 stable handoff checkpoint documentation.
- Added V4.5.2 phase manifest.
- Refreshed md-only source zip and all-md source bundle.

### Preserved
- No Java source, class, jar, parser, generator, validator, checker, mapping-rule, UI, rendering, graph-mutation or example-semantics changes.
- Runtime behavior remains the V4.5.1 Info label behavior.

### Actual checks
- Mapping V4.5 DET regression checker: `31 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-04-30 — Mapping V4.5.1 Info label clarification

### Changed
- Changed Info-window display from `generated: best: ...` to `generated best: ...`.
- Added V4.5.1 documentation and phase manifest.

### Preserved
- No generator, validator, parser, checker, mapping-rule or graph-rendering changes.
- Invalid mapping output remains `generated: none (invalid mapping)`.

### Expected checks
- Mapping V4.5 DET regression checker: `31 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-04-30 — Mapping V4.5 minimal DET generator / validator

### Added
- Added `DET` as a known Mapping role.
- Added parsing for `det-target` and `det_target` on lexical items.
- Added DET target validation for missing target, unknown target and absent lexical target.
- Added valid DET examples under `examples/opn/mapping-v4-det/`.
- Added invalid DET examples under `examples/opn/mapping-v4-det-invalid/`.
- Added `md/sources/mapping-v4/det-v45.md` as the V4.5 behavior document.
- Added `md/meta-inf/2026-04-30-mapping-v45-det-minimal.md` phase manifest.
- Added `md/examples/opn/mapping-v4-det-v45-expected-output-manifest.md`.

### Changed
- Updated Mapping V4 regression checker to include V4.5 DET examples.
- Updated current-state, phasing, README, INDEX and CHANGELOG documentation.

### Scope
- Minimal DET generator / validator only.
- DET remains a lexical-axis item.
- Generation still uses placement rules; no graph mutation and no tree transformation.
- FRAME.graph, lexicon, adjectives, complex NP structure, relative clauses, UI/rendering and multiple DET-per-target behavior remain outside scope.

### Expected checks
- Mapping V4.5 regression checker: `31 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-04-30 — Mapping V4.4 DET scope

### Added
- Added `md/sources/mapping-v4/det-v44.md` as the V4.4 DET scope document.
- Added `md/meta-inf/2026-04-30-mapping-v44-det-scope.md` phase manifest.
- Added `md/examples/opn/mapping-v4-det-v44-expected-output-manifest.md` as a documentation-only target manifest for later V4.5 implementation.

### Scope
- Documentation and expected-output manifest only.
- No Java source changes.
- No `.class` rebuilds.
- No jar rebuilds.
- No parser, generator, validator, checker, UI, rendering or runtime example-semantics changes.
- Runtime behavior remains `MAPPING_V4_3_WH_MINIMAL`.

### Expected checks
- Mapping V4.3 regression: `24 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-04-29 — Mapping V4.0 scope freeze

### Added
- Added `md/sources/mapping-v4/` as the V4 documentation source area.
- Added V4.0 current-state, phasing and scope-freeze documents.
- Added `md/meta-inf/2026-04-29-mapping-v40-start.md`.
- Added `md/sources-md-zip/` for the embedded md-only sources zip.

### Packaging
- Project zips now include an md-only sources zip containing all Markdown files in the package, preserving relative paths.
- The md-only zip is intended for manual upload to Project Sources and source-state verification.

### Scope
- Documentation/package-only phase.
- No Java code changes.
- No `.class` rebuilds.
- No jar rebuilds.
- No generator, validator, parser, UI, rendering or example-semantics changes.
- Mapping V3 core remains the regression baseline.

### Expected checks
- Mapping V3 regression: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-04-27 — Mapping V3.7 future phases placeholder

### Added
- extended `meta-inf/frame_md.tmp` with later phases for utterance type / clause mode
- added placeholders for declarative, interrogative, imperative and exclamative utterances
- added placeholders for full vs elliptic utterances
- clarified that WH, negation, DET, TIME/PLACE and FRAME.graph remain outside the current core checker scope

### Scope
- documentation only
- no Java changes
- no mapping-rule changes
- no checker changes

## 2026-04-18 — Move-mode edge midpoint cleanup

### Fixed
- ordinary undirected edge midpoint markers are now hidden while the editor is in **Move** mode
- the suppression applies both to the buffered graph image and to temporary redraw overlays during transforms

### Scope
- directed edge arrows are unchanged
- projection rendering is unchanged
- midpoint markers still remain available outside Move mode

## 2026-04-18 — First projection slice follow-up

### Included
- Sources-led markdown files restored into the package root
- first projection slice refined for phrase trees
- rebuilt `OpenGraphEd.jar` and `dist/OpenGraphEd.jar`

### Changed
- default phrase-tree projections now enable **LEX left** and **SYN right**
- right-side projection labels are now computed from ordered child labels
- simple JAN convention for this slice: first child uppercase, following children lowercase, joined with `, `
- bottom logical projection rendering is limited to source nodes that already carry explicit logical labels
- phrase-tree UI copy updated to describe the current first slice

### Layout
- fitted OpenGraph grid no longer expands the structure grid for projection bands or projection labels
- projection visibility is handled through render/visible bounds rather than structure-grid growth

- 2026-04-17: Moved `Set Default Graph Dir` into the Load Graph dialog accessory and removed the now-redundant File menu item.
- 2026-04-17: Removed the duplicate File menu checkbox for starting Load Graph in the Graph directory. The option remains available in the Load Graph dialog; the separate File menu item was redundant.
# Changelog

## v4.21.4 — Zinstype zichtbaar in toolbar

- `Zinstype` direct naast `Structure type` gezet zodat het niet achter latere OpenGraph-knoppen verdwijnt.
- Header-controls worden na het zetten van de graph opnieuw gesynchroniseerd.
- `Language Tree / Phrase`: zinstype zichtbaar, Draw verborgen.
- `Simple`/`Anafoor`/`Frame`: Draw zichtbaar, zinstype verborgen.

## v4.21.3 — Language Tree UI source reset

- Structure type is selector-only; it does not draw instantly.
- Simple shows Draw and hides Zinstype.
- Language Tree / Phrase keeps direct Zinstype draw.
- Anafoor restored in the structure selector.
- Frame remains projection-capable/open-tree projection.
- Direct draw reloads the original `.graph`; missing source gives an explicit message.
- Placement labels are visible on the lexical axis.

## v4.21.2 — Language Tree selectie null-safe + DOS error logging

- Fixed: directe zinstype-draw kon crashen wanneer `getSpecialNodeSelections()` nog `null` was.
- `slot`/rootselectie is nu robuust wanneer nog geen speciale selectie is gestart.
- OpenGraph drawing display fouten worden nu ook naar DOS/console (`System.err`) geschreven, inclusief stacktrace.
- Dialogmelding blijft zichtbaar voor de gebruiker.

## v4.21.1 — Language Tree zinstype direct draw + structure type in editor

- Zinstype buttons now immediately execute OpenGraph Draw/Redraw for Language Tree / Phrase.
- The local editor bar no longer has a Draw button; draw is triggered by Zinstype or Structure type selection.
- Added `Structure type` selector directly in the graph editor window: Simple, Language Tree / Phrase, Frame.
- Changing Structure type immediately redraws with that structure profile.
- Zinstype choices force Structure type to Language Tree / Phrase and keep the selected button marked.

## 2026-05-02 — Mapping V4.16 morphology metadata validator

### Added
- Added runtime morphology metadata validation for explicit morphology fields on `LEXICON` rows.
- Added valid morphology examples under `examples/opn/mapping-v4-morphology/`.
- Added invalid morphology examples under `examples/opn/mapping-v4-morphology-invalid/`.
- Added V4.16 expected-output manifest, phase manifest and patch manifest.

### Changed
- `graphStructure/Graph.java` now stores/appends morphology validation summary details on the Lexicon Info line.
- `userInterface/GraphFileActions.java` validates morphology metadata during OPN open.
- `tools/MappingV4RegressionChecker.java` now checks V4.16 morphology examples.

### Scope
- Diagnostics for unknown morphology feature, missing value, duplicate feature, feature/`pos` incompatibility and unknown value.
- Morphology diagnostics are informational only.
- Generated output remains based on explicit `MAPPING_V4` placement rules.

### Preserved
- No automatic inflection, surface-form generation, role inference, automatic lexical insertion, automatic frame selection, Lexicon rendering, graph mutation or generated-output changes.

### Actual checks
- Mapping V4.16 morphology metadata validator regression checker: `53 pass, 0 fail`.
- Mapping V3 regression checker: `13 pass, 0 fail`.
- MD folder check: `PASS`.

## 2026-04-02 — Stable refactor baseline

Stable baseline:

**OpenGraphEd_refactor_phase33_pq_lifecycle_support_2026-04-02**

### Included

#### Naming and packaging
- project naming standardized to **OpenGraphEd**
- runtime jar standardized as `OpenGraphEd.jar`

#### Controller and UI refactor
- controller responsibilities reduced and split into focused helpers
- file actions isolated
- window/dialog coordination isolated
- menu and toolbar heavily reduced and decomposed
- help/info/log/preferences windows simplified

#### Graph editor refactor
- layout/grid support extracted
- render/transform support extracted
- mode/listener support extracted
- overlay support extracted

#### Graph model refactor
- `Graph` decomposed into focused support classes for:
  - median helpers
  - selection helpers
  - copy helpers
  - bounds helpers
  - grid helpers
  - stats helpers
  - lookup helpers
  - transform helpers
  - persistence helpers
  - extender helpers
  - edge mutation helpers
  - appearance helpers
  - undo helpers
  - log helpers
  - property mutation helpers
  - topology helpers
  - structure mutation helpers

#### Node / Edge refactor
- `Node` split into:
  - incident support
  - geometry support
  - persistence support
- `Edge` split into:
  - cycle support
  - geometry support
  - persistence support

#### Operation refactor
- biconnectivity support extracted
- Chan tree draw support extracted
- embed support extracted
- Schnyder embedding support extracted

#### PQ and file utility partial cleanup
- debug support extracted from `PQNode`
- debug support extracted from `PQTree`
- lifecycle/state support extracted from PQ structures
- GIF palette/color conversion support extracted

### Fixed

#### Biconnectivity display regression
- fixed a regression introduced during refactoring of biconnected component support
- cause: temporary `newEdges` collection was not cleared per component
- result: component display/state corruption
- fix applied and included in stable baseline

### Deliberately not continued
- deep PQ reduction core not further refactored
- PQ template logic not further split
- high-risk algorithmic core intentionally left in place for stability

### Validation
The stable baseline was kept only after repeated successful checks of:

- compile
- jar build
- out folder generation
- undo / redo sanity
- render-after-undo behavior
- display flows
- editor interaction flows

## 2026-04-13

### Menu / file loading
- added `File -> Set Default Graph Dir` to choose the default folder for loading `.graph` files
- `File -> Graph Dir` now uses the chosen default graph directory instead of only the built-in `GRAPH` folder
- default graph directory is persisted in `config/opengraphed_user.properties`
- `Load Graph` accessory now shows the active default graph directory

## 2026-04-16

### Graph file click / direct open
- OpenGraphEd can now start with one or more `.graph` file paths as command-line arguments
- added shared path-based graph loading so startup and file chooser use the same open logic
- updated `run.bat` so `run.bat "bestand.graph"` opens that graph directly
- added `open_graph_file.bat` as a dedicated launcher for a specific `.graph` file
- added `register_graph_file_association.bat` and `unregister_graph_file_association.bat` for Windows double-click association of `.graph` files

### Internal OpenGraph rename
- renamed the internal `Kruin*` module/class/config layer to `OpenGraph*`
- kept the GitHub owner/account name `kruin` unchanged
- renamed OpenGraph settings/config files to `config/opengraph_defaults.properties` and `config/opengraph_user.properties`
- updated source lists, tests, UI labels, dialog titles, grid/projection helpers, and draw operations to the OpenGraph naming line
- rebuilt the project so `out/` and `OpenGraphEd.jar` match the renamed sources

## 2026-04-16 — graph click single-instance fix

- added single-instance forwarding for launches from the same app folder
- later `.graph` double-clicks now open in the existing OpenGraphEd window instead of starting a second app instance
- added `OpenGraphEd.bat` as the named launcher
- updated Windows `.graph` association to use the launcher name `OpenGraphEd (Java, via DOS .batfile)`
- added `create_desktop_shortcut.bat` to create a desktop shortcut with that display name


- 2026-04-17: build.bat made resilient on Windows when OpenGraphEd.jar is locked by a running app; build now keeps refreshed classes in out\ and writes OpenGraphEd.new.jar as fallback instead of failing the whole build.

- 2026-04-17: Move mode in OpenGraph projection context now shifts the visible grid display window together with the graph so projection bands and markers stay attached during drag.

- 2026-04-17: OpenGraph projection labels phase 1 added visible side captions (LEX/SYN/LF/PM) and copied source labels on projection targets; bottom labels now render downward.

- 2026-04-18: projection caption orientation updated: LEX vertical on the left; LF horizontal below projected labels.

## 2026-04-26 — OPN structure loader fix

- Fixed `Open OPN` so metadata is not drawn as graph nodes.
- Current supported demo/test OPN formats:
  - YAML-like `structure.nodes` / `structure.edges`
  - older pipe-delimited `STRUCTURE_NODES` / `STRUCTURE_EDGES`
- `meta`, `notes`, `OPN_VERSION`, and `STRUCTURE_TYPE` are not graph content.
- Included example OPN files for ONBEZIELD bovenboom and the V-centered example sentence.

## 2026-04-26 - OPN mapping v1 visibility
- Added non-drawing support for `MAPPING_V1` sections in pipe-safe OPN files.
- `Open OPN` still draws only `STRUCTURE_NODES` / `STRUCTURE_EDGES`.
- Mapping sections are counted and reported in the Info window as OPN Mapping v1 metadata.
- Added mapping-v1 example OPN files under `examples/opn/mapping-v1/`.

## 2026-04-26 — OPN placement rules v2
- Generalized generator: generated utterance is now derived from `PLACEMENT_RULES` ordering constraints instead of hardcoded role sequence.
- Supported `MAPPING_V2:` / `END_MAPPING_V2:` markers while preserving `MAPPING_V1:` compatibility.
- Current v2 rule relations: `left_of V`, `right_of V`, `realizes_before`, `realizes_after`, plus aliases `before` and `after`.
- Added `examples/opn/mapping-v2/` with the same three views using the v2 markers.

## 2026-04-26 - adverbs-v2 example
- Added mapping-v2 adverb example with TIME, NEG and PLACE lexical items.
- Existing data-driven placement-rule engine is used; graph nodes are not mutated.

2026-04-26: mapping v3 NEG constraint upgraded to after_aux_before_object (V-AUX < NEG < Patiens).

## 2026-04-27 — Mapping V3.1 ranking en alternatieven

- V3-generator bouwt nu expliciete alternatieve kandidaten op basis van ranked placement rules.
- Alternatieven worden stabiel gerangschikt, gededupliceerd en begrensd tot maximaal 3 in Info.
- Niet-oplosbare ordering-constraints worden niet meer als fallback-volgorde gegenereerd; zulke kandidaten vallen weg.
- Toegevoegd: beperkte combinatie van ranked opties voor meerdere argumenten.
- Toegevoegd: ondersteuning voor `before_clause` en `after_clause` als placement-relaties.
- Scope: geen UI- of graph-renderingwijzigingen.


## 2026-04-27 — Mapping V3.2 argumenten en placement-resolutie

- V3-placementregels ondersteunen nu compacte spec-spelling:
  - `left_of_V` / `right_of_V`
  - `before_Theme` / `before_V_PART`
  - `before_clause` / `after_clause`
  - `anchor` / `clause_end`
- Generator/validator normaliseren role-targets robuuster, onder meer `Theme` ↔ `THEME` en `V_PART` ↔ `V-PART`.
- `after_aux_before_object` heeft nu een veilige default naar `Patiens` wanneer geen target is opgegeven, maar kan ook expliciet op `THEME` worden toegepast.
- `clause_end` wordt generatorisch behandeld zonder conflict met `after_clause`-rollen zoals PLACE.
- Toegevoegd: compact V3.2 voorbeeld met TIME, NEG, RECIPIENT, THEME, V-PART en PLACE.
- Scope: geen UI- of graph-renderingwijzigingen.

## 2026-04-27 — Mapping V3.3 core regressie

- Toegevoegd: kern-regressieset onder `examples/opn/mapping-v3-core/`.
- V3.3-scope aangescherpt tot declaratieve kernmapping: Agens, Patiens, RECIPIENT, THEME, V, V-AUX en V-PART.
- Gesplitste VP blijft in scope: `heeft ... gebreid/gegeven`.
- Vraagzinnen/WH, negatie, TIME/PLACE-bijwoorden, DET-splitsing en FRAME.graph zijn expliciet on hold gezet.
- Toegevoegd: `meta-inf/frame_md.tmp` als placeholder voor latere fasen.
- Scope: geen Java-, UI- of graph-renderingwijzigingen.

## 2026-04-27 — Mapping v3.4 core invalid diagnostics

- Added invalid Mapping V3 regression examples under `examples/opn/mapping-v3-core-invalid/`.
- Open OPN now reports Mapping V3 validation diagnostics in the Info window.
- Invalid Mapping V3 summaries include the loaded file name in the validation details.
- Generation is suppressed for invalid mappings: `generated: none (invalid mapping)`.
- Diagnostics added for missing role, unknown role, missing targets, ordering cycles, broken verb-domain references, duplicate lexical ids, and missing V anchors.

## 2026-04-27 - Mapping v3.4 invalid dialog

- Invalid `MAPPING_V3` bij open `.opn` toont nu direct een modal melding met OK-knop.
- De melding noemt de loadfile en verwijst naar het Info-scherm.
- Het Info-scherm wordt automatisch geopend/bijgewerkt voor de volledige diagnose.
- Geen wijziging aan mappingregels of rendering.

## 2026-04-27 - Mapping v3.4 invalid info-only

- Removed modal warning dialog for invalid OPN MAPPING_V3 on load.
- Invalid mapping now opens the Info window directly.
- Validation fail summary is ordered first in the OPN Mapping info text.
- Loadfile name remains included in validation details.

## 2026-04-27 - Mapping v3.5 expected output manifest

- Added expected-output manifest for valid core Mapping V3 examples.
- Added expected-fail manifest for invalid core Mapping V3 examples.
- Added central `examples/opn/mapping-v3-expected-output-manifest.md` with pass criteria.
- No Java, UI, rendering or mapping-rule changes.

## 2026-04-27 - Mapping v3.6 expected-output checker

- Added `tools/MappingV3RegressionChecker.java` and compiled class files.
- Added `run-mapping-v3-checker.bat` for Windows use.
- Checker compares valid examples against `EXPECTED.txt` and invalid examples against `EXPECTED-FAIL.txt`.
- Checker verifies generated best output, validation counts, invalid diagnostics, filename presence and suppressed invalid generation.
- Last container run: `summary: 13 pass, 0 fail`.
- Scope remains Mapping V3 core only; WH, NEG, DET, FRAME.graph, lexicon and rendering remain out of scope.

## 2026-04-27 - Mapping v3.8 source MD bundle

- Added `sources/mapping-v3/` with the current project-source `.md` set.
- Added source bundle README and meta note.
- No Java code changes.
- Mapping V3 checker remains unchanged; core checker status remains `13 pass, 0 fail`.

## 2026-04-27 — Mapping v3.9 MD folder cleanup

- Verzamelt alle `.md`-bestanden in één `md/`-map.
- Behoudt de oorspronkelijke subpaden onder `md/`, zoals `md/meta-inf/`, `md/sources/mapping-v3/` en `md/examples/`.
- Verplaatst ook `frame_md.tmp` naar `md/meta-inf/` omdat dit een markdown-achtige placeholder is.
- Geen Java-codewijzigingen.
- Geen wijziging aan mapping, checker, examples, generator of validator.

## 2026-04-27 — Mapping V40: MD Folder Check

- Toegevoegd: `run-md-folder-check.bat` voor Windows.
- Toegevoegd: `tools/check-md-folder.sh` voor POSIX/containercontrole.
- Toegevoegd: `tools/CheckMdFolder.java` als Java-bron voor een latere cross-platform checker.
- Regel technisch afdwingbaar gemaakt: alle `.md`-bestanden moeten onder `md/` staan.
- Geen wijzigingen aan Mapping V3, generator, validator, regression checker of UI.

## 2026-04-27 — Mapping v3.10 core stable checkpoint

- Markeert de core Mapping V3-laag als `MAPPING_V3_CORE_STABLE`.
- Werkt `md/sources/mapping-v3/current-state.md` bij met stable-status.
- Legt vast dat core in scope is: Agens, Patiens, RECIPIENT, THEME, V, V-AUX, V-PART en gesplitste VP.
- Legt vast dat WH, NEG, TIME/PLACE, DET, FRAME.graph, lexicon en UI/rendering buiten dit checkpoint blijven.
- Geen Java-codewijzigingen.
- Mapping checker blijft: `13 pass, 0 fail`.
- MD folder check blijft: `PASS`.

## 2026-04-29 — Mapping V4.3 minimal WH

### Added
- Added `WH` as a known Mapping role.
- Added minimal WH behavior in `MAPPING_V4` examples through lexical-axis placement rules.
- Added valid WH examples for `wie heeft de hond gebeten`, `wat heeft vrouw gebreid`, and `wie bijt hond`.
- Added invalid WH examples for missing `V-AUX` and ordering cycle diagnostics.
- Added `tools/MappingV4RegressionChecker.java` and `run-mapping-v4-checker.bat`.
- Added V4.3 source documentation and expected-output manifest.

### Preserved
- No graph mutation.
- No WH transformation / vooropplaatsing.
- DET, FRAME.graph, lexicon and UI/rendering remain outside scope.

## 2026-05-05 — Mapping V4.20.2 OPN preferences and tree info

- Added a separate persisted preferred OPN directory.
- OPN and graph open dialogs now expose selectable `.opn`, `.graph`, and combined GRAPH/OPN filters.
- Added language-tree type reporting:
  - `S` top node: S-tree, binary, recursive.
  - `V` top node: V-tree, non-binary, growing.
- Info and Log windows now display the OpenGraphEd jar version and language-tree type summary.
- Jar manifest now includes `Implementation-Version: v4.20.2`.
- No mapping-rule, generator, projection-layout or checker expectation changes.
- Regression status: Language Tree `3 pass, 0 fail`; Mapping V4 `53 pass, 0 fail`; Mapping V3 `13 pass, 0 fail`; MD folder `PASS`.
- Added `md/sources-md-zip/Mapping_V4-26-05-05--v4202-all-md-sources-bundel.md` for Project Sources handoff.
- Added md-only zip handoff reference for V4.20.2 Project Sources upload.

## 2026-05-05 — Mapping V4.20.3 OpenGraph actions menu and local buttons

- Added a dedicated `OpenGraph` menu with `Draw / Redraw`, `Grid Settings`, `Toggle Projections`, and `Save OPN`.
- Added a local OpenGraph action bar above each graph editor: `Draw`, `Grid`, `Toggle Proj.`, `Save OPN`.
- Removed OpenGraphGrid from the general Modes chooser; ordinary Grid remains there.
- Removed OpenGraph draw/toggle entries from the general Display menu.
- Coordinated behavior: `Grid` and `Toggle Projections` no longer clear the current OPN result state; `Draw` remains the operation that starts/replaces that result.
- OpenGraph Draw dialog now uses `Draw` wording instead of `Run` wording.
- Jar manifest now includes `Implementation-Version: v4.20.3`.
- No separate md-only zip is generated; all markdown is under `md/` in the project zip.
- Regression status: Language Tree `3 pass, 0 fail`; Mapping V4 `53 pass, 0 fail`; Mapping V3 `13 pass, 0 fail`; MD folder `PASS`.


## v4.20.4 — build launcher menu fix

- Build no longer depends on Windows `%TEMP%`; local `.build-tmp` is used.
- Launchers call Java directly to avoid batch recursion.
- Fixed OpenGraph menu descriptor comma that caused a null menu item.
- Version metadata set to v4.20.4.

## v4.21.0 — Language Tree tooltip + Phrase default

- Zinstype hover tooltips now appear immediately.
- OpenGraph defaults now select `Language Tree / Phrase` instead of `Frame`.
- Clarified dialog labels: `Language Tree / Phrase` and `Frame (roles/functions)`.
- Updated config defaults/user settings to `structure.type=2`.


## v4.20.9 — Language Tree icon resource startup fix

- Fixed startup crash when run.bat preferred precompiled `out\` classes but `out\images` was absent.
- Menu/toolbar icon loading now tolerates missing classpath resources and falls back to root-level `images\`.
- Packaged `out\images`, `out\help`, and `out\config` in the zip so the preferred `out\` launcher path is self-contained.
- Version metadata set to `v4.20.9`.

## v4.20.8 — Language Tree zinstype hover selection

- Zinstype buttons are now persistent toggle buttons; the chosen profile remains visibly marked.
- Detailed Language Tree placement-rule previews moved to hover tooltips on the zinstype buttons.
- The drawing overlay is compact again: `LANGUAGE TREE` plus active `Zinstype`.
- Topicalisation now reads the standard selected node and can preview a grouped topic such as `NP(de man)`.
- Displacement convention clarified: rules attach to categorial nodes; lexical descendants are grouped for the lexical-axis label.
- Version metadata set to `v4.20.8`.

## v4.20.7 — Language Tree zinstype profile preview

- Implemented Alternative C: `Zinstype` profile buttons plus visible placement-rule preview.
- Added editor-window buttons: `Basis`, `Bijzin`, `Stellend`, `Ja/nee`, `WH`, `Topicalisatie`.
- Language Tree overlay now shows `Zinstype: <profile>` and preview rules near the lexical axis.
- Zinstype buttons update projection settings only; they do not alter DS and do not create nodes.
- Clicks on Language Tree overlay labels/preview are consumed to avoid accidental DS node creation.
- Version metadata set to `v4.20.7`.
- Regression status: Language Tree `3 pass, 0 fail`; Mapping V4 `53 pass, 0 fail`; Mapping V3 `13 pass, 0 fail`; MD folder `PASS`.

## v4.20.6 — Language Tree slot click guard and lextest

- Fixed virtual slot markers: clicking `slot0`/`slot1` no longer creates a real DS node or shifts the virtual slots upward.
- Language Tree slot anchoring now prefers real structure roots and ignores accidental isolated top nodes.
- Added visible `LANGUAGE TREE` overlay caption when Language Tree projection context is active.
- Added `examples/graph/lextest.graph` as a native GRAPH test file that opens with Language Tree projection defaults.
- Added placement-rule design note for the upcoming Zinstype controls.

## v4.20.5 — Language Tree slots and n-ary OpenGraph draw

- OpenGraph draw no longer rejects non-binary trees; nodes with three or more children use a deterministic n-ary layout.
- Binary trees keep the existing two-child open-tree rule.
- Language Tree draw reserves one extra grid row above the top node for `slot0`.
- Projection overlay renders virtual lexical-axis markers `slot0` and `slot1`; these are not DS nodes.
- Language-tree type summary now reports n-ary allowed / lexical-axis placement.
- Version metadata set to v4.20.5.
- Regression status: Language Tree `3 pass, 0 fail`; Mapping V4 `53 pass, 0 fail`; Mapping V3 `13 pass, 0 fail`; MD folder `PASS`.

## v4.21.7 — Hide moved base projection labels

- Language Tree: moved surface items no longer duplicate their lexical text at the old base-axis position.
- Topicalisatie/Stellend: the moved categorial phrase text is shown at the slot, while its original projection text is hidden.
- V2 profiles: the finite verb terminal is shown at the V2 position, while its original projection text is hidden.

## v4.21.6 — Language Tree placement rules revision

- Revised topicalisation rule: topicalisation now requires a categorical selected node and renders its bundled lexical phrase on `slot1`.
- Revised V2 rule: V2 now renders the finite verb terminal, e.g. `heeft`, on the surface axis instead of rendering the category-level label `PV → FIN`.
- Ja/nee questions now show the finite verb as first visible material on the lexical axis.
- Bijzin keeps the finite verb in basis order and renders `(om)dat` in `slot0`.

## v4.21.9 — Language Tree FIN half-row

- Moved the FIN/V2 surface-axis label to a half-row below slot1 instead of a full grid row.
- Prevents FIN from lining up with DS child rows such as the first NP/VP row under S.
- Keeps FIN as an axis position, not as a DS node row.
## v4.22.0 — Dutch Language Tree root branch spacing

- Replaced the temporary FIN half-row convention with a structural spacing convention for Dutch Language Tree / Phrase.
- In Language Tree draw, the first branching below the DS root is now one grid row longer.
- `slot0` remains above `S`; `slot1` remains on `S`; `FIN/V2` now uses the normal full grid row below `slot1`.
- The first real DS child row, for example `NP`/`VP`, starts one grid row lower, leaving room for topicalisation and V2 on the lexical axis.
- Scope is limited to Language Tree draw; Simple, Anafoor, and Frame are not changed.

