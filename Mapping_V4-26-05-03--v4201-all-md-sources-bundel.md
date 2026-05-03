# Mapping V4.20.1 — all MD sources bundle

---

## FILE: `md/CHANGELOG.md`
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
---

## FILE: `md/INDEX.md`
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
- `md/sources-md-zip/README.md` — afspraak over md-only sources zip
- `md/sources-md-zip/Mapping_V4-26-04-29--v40-all-md-sources.zip` — md-only sources zip voor handmatige Project Sources upload

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
- `md/sources-md-zip/Mapping_V4-26-04-30--v451-info-label-md-only.zip` — V4.5.1 md-only source zip

## Mapping V4.5.2 stable handoff

- `md/sources/mapping-v4/stable-handoff-v452.md` — V4.5.2 stable handoff checkpoint
- `md/meta-inf/2026-05-01-mapping-v452-stable-handoff.md` — V4.5.2 phase manifest
- Runtime behavior remains `MAPPING_V4_5_1_INFO_LABEL`; this phase is documentation/package only.
- `md/sources-md-zip/Mapping_V4-26-05-01--v452-all-md-sources-bundel.md` — V4.5.2 all-md source bundle
- `md/sources-md-zip/Mapping_V4-26-05-01--v452-stable-handoff-md-only.zip` — V4.5.2 md-only source zip
## Mapping V4.6 FRAME.graph scope

- `md/sources/mapping-v4/frame-graph-v46.md` — V4.6 FRAME.graph scope and architecture boundary
- `md/meta-inf/2026-05-01-mapping-v46-frame-graph-scope.md` — V4.6 phase manifest
- `md/examples/opn/mapping-v4-frame-v46-expected-output-manifest.md` — documentation-only expected-output manifest for later FRAME.graph implementation
- `md/PATCH-MANIFEST-v46-frame-graph-scope.md` — V4.6 patch manifest
- Runtime behavior remains `MAPPING_V4_5_1_INFO_LABEL`; no Java, generator, validator, checker, UI or rendering changes.
- `md/sources-md-zip/Mapping_V4-26-05-01--v46-all-md-sources-bundel.md` — V4.6 all-md source bundle
- `md/sources-md-zip/Mapping_V4-26-05-01--v46-frame-graph-scope-md-only.zip` — V4.6 md-only source zip

## Mapping V4.7 FRAME.graph metadata read / count

- `md/sources/mapping-v4/frame-graph-v47.md` — V4.7 FRAME.graph metadata read/count behavior
- `md/meta-inf/2026-05-01-mapping-v47-frame-graph-metadata.md` — V4.7 phase manifest
- `md/examples/opn/mapping-v4-frame-v47-expected-output-manifest.md` — V4.7 expected-output manifest
- `md/PATCH-MANIFEST-v47-frame-graph-metadata.md` — V4.7 patch manifest
- `examples/opn/mapping-v4-frame/` — valid V4.7 FRAME.graph metadata examples
- Runtime reads `FRAME_GRAPH` metadata, counts frames/slots and reports it in Info.
- Generation, placement validation, rendering and graph mutation remain unchanged.
- `md/sources-md-zip/Mapping_V4-26-05-01--v47-all-md-sources-bundel.md` — V4.7 all-md source bundle
- `md/sources-md-zip/Mapping_V4-26-05-01--v47-frame-graph-metadata-md-only.zip` — V4.7 md-only source zip

## Mapping V4.8 FRAME.graph slot validation scope

- `md/sources/mapping-v4/frame-graph-v48.md` — V4.8 FRAME.graph slot-validation scope and validator boundary
- `md/meta-inf/2026-05-01-mapping-v48-frame-graph-slot-validation-scope.md` — V4.8 phase manifest
- `md/examples/opn/mapping-v4-frame-v48-expected-output-manifest.md` — documentation-only expected-output manifest for later slot validation
- `md/PATCH-MANIFEST-v48-frame-graph-slot-validation-scope.md` — V4.8 patch manifest
- Runtime behavior remains `MAPPING_V4_7_FRAME_GRAPH_METADATA`; no Java, generator, validator, checker, UI or rendering changes.
- `md/sources-md-zip/Mapping_V4-26-05-01--v48-all-md-sources-bundel.md` — V4.8 all-md source bundle
- `md/sources-md-zip/Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope-md-only.zip` — V4.8 md-only source zip
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
---

## FILE: `md/PATCH-MANIFEST-v410-lexicon-metadata.md`
# Patch manifest — Mapping V4.10 Lexicon metadata read / count

Patch name:

```text
Mapping_V4-26-05-02--v410-lexicon-metadata
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-02--v49-frame-graph-minimal-slot-validator.zip
```

## Adds

```text
md/sources/mapping-v4/lexicon-v410.md
md/meta-inf/2026-05-02-mapping-v410-lexicon-metadata.md
md/examples/opn/mapping-v4-lexicon-v410-expected-output-manifest.md
md/PATCH-MANIFEST-v410-lexicon-metadata.md
examples/opn/mapping-v4-lexicon/
md/sources-md-zip/Mapping_V4-26-05-02--v410-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v410-lexicon-metadata-md-only.zip
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
userInterface/GraphEditorInfoSupport.java
tools/MappingV4RegressionChecker.java
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Does not change

```text
generated utterance rules
Mapping V4 placement validation semantics
FRAME.graph slot validation semantics
graph rendering
graph mutation
tree transformation boundary
lexicon lookup
automatic role inference
```

## Expected status after applying patch

```text
MAPPING_V4_10_LEXICON_METADATA
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/PATCH-MANIFEST-v4101-straight-edge-midpoint-cleanup.md`
# Patch manifest — Mapping V4.10.1 straight-edge midpoint cleanup

Patch name:

```text
Mapping_V4-26-05-02--v4101-straight-edge-midpoint-cleanup
```

## Type

Small rendering cleanup patch.

## Base

```text
Mapping_V4-26-05-02--v410-lexicon-metadata.zip
```

## Adds

```text
md/sources/mapping-v4/midpoint-v4101.md
md/meta-inf/2026-05-02-mapping-v4101-straight-edge-midpoint-cleanup.md
md/PATCH-MANIFEST-v4101-straight-edge-midpoint-cleanup.md
md/sources-md-zip/Mapping_V4-26-05-02--v4101-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v4101-straight-edge-midpoint-cleanup-md-only.zip
```

## Changes

```text
graphStructure/Edge.java
graphStructure/Edge.class
out/graphStructure/Edge.class
OpenGraphEd.jar
dist/OpenGraphEd.jar
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
Mapping V4 parsing/generation/validation
FRAME.graph metadata or validation
Lexicon metadata read/count
OPN example semantics
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_10_1_STRAIGHT_EDGE_MIDPOINT_CLEANUP
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/PATCH-MANIFEST-v411-lexicon-validation-scope.md`
# Patch manifest — Mapping V4.11 lexicon validation / coupling scope

Patch name:

```text
Mapping_V4-26-05-02--v411-lexicon-validation-scope
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v4101-straight-edge-midpoint-cleanup.zip
```

## Adds

```text
md/sources/mapping-v4/lexicon-validation-v411.md
md/meta-inf/2026-05-02-mapping-v411-lexicon-validation-scope.md
md/examples/opn/mapping-v4-lexicon-v411-expected-output-manifest.md
md/PATCH-MANIFEST-v411-lexicon-validation-scope.md
md/sources-md-zip/Mapping_V4-26-05-02--v411-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v411-lexicon-validation-scope-md-only.zip
```

## Changes

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
Java source
class files
jar files
Mapping V4 parsing/generation/validation
FRAME.graph metadata or validation
Lexicon metadata read/count
straight-edge midpoint rendering behavior
OPN example semantics
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_11_LEXICON_VALIDATION_SCOPE
Runtime behavior remains MAPPING_V4_10_1_STRAIGHT_EDGE_MIDPOINT_CLEANUP
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/PATCH-MANIFEST-v412-lexicon-validator.md`
# Patch manifest — Mapping V4.12 Lexicon validator

Patch name:

```text
Mapping_V4-26-05-02--v412-lexicon-validator
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-02--v411-lexicon-validation-scope-FIXED-slim.zip
```

## Adds

```text
md/sources/mapping-v4/lexicon-validation-v412.md
md/meta-inf/2026-05-02-mapping-v412-lexicon-validator.md
md/examples/opn/mapping-v4-lexicon-v412-expected-output-manifest.md
md/PATCH-MANIFEST-v412-lexicon-validator.md
examples/opn/mapping-v4-lexicon-invalid/
md/sources-md-zip/Mapping_V4-26-05-02--v412-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v412-lexicon-validator-md-only.zip
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
examples/opn/mapping-v4-lexicon/EXPECTED.txt
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
generated utterance rules
Mapping V4 placement validation semantics
FRAME.graph slot validation semantics
graph rendering
graph mutation
tree transformation boundary
role inference
automatic lexical insertion
automatic frame selection
morphology / inflection
lexicon rendering
```

## Expected status after applying patch

```text
MAPPING_V4_12_LEXICON_VALIDATOR
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/PATCH-MANIFEST-v413-lexicon-morphology-frame-selection-scope.md`
# Patch manifest — Mapping V4.13 Lexicon / morphology / frame-selection scope

Patch name:

```text
Mapping_V4-26-05-02--v413-lexicon-morphology-frame-selection-scope
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v412-lexicon-validator.zip
```

## Adds

```text
md/sources/mapping-v4/lexicon-morphology-frame-selection-v413.md
md/meta-inf/2026-05-02-mapping-v413-lexicon-morphology-frame-selection-scope.md
md/examples/opn/mapping-v4-lexicon-v413-expected-output-manifest.md
md/PATCH-MANIFEST-v413-lexicon-morphology-frame-selection-scope.md
md/sources-md-zip/Mapping_V4-26-05-02--v413-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v413-lexicon-morphology-frame-selection-scope-md-only.zip
```

## Changes

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
Java source
class files
jar files
Mapping V4 parsing/generation/validation
FRAME.graph metadata or validation
Lexicon validator behavior
OPN example semantics
graph rendering
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_13_LEXICON_MORPHOLOGY_FRAME_SELECTION_SCOPE
Runtime behavior remains MAPPING_V4_12_LEXICON_VALIDATOR
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/PATCH-MANIFEST-v414-morphology-metadata-validation-scope.md`
# Patch manifest — Mapping V4.14 morphology metadata validation scope

Patch name:

```text
Mapping_V4-26-05-02--v414-morphology-metadata-validation-scope
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v413-lexicon-morphology-frame-selection-scope.zip
```

## Adds

```text
md/sources/mapping-v4/morphology-v414.md
md/meta-inf/2026-05-02-mapping-v414-morphology-metadata-validation-scope.md
md/examples/opn/mapping-v4-morphology-v414-expected-output-manifest.md
md/PATCH-MANIFEST-v414-morphology-metadata-validation-scope.md
md/sources-md-zip/Mapping_V4-26-05-02--v414-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v414-morphology-metadata-validation-scope-md-only.zip
```

## Changes

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
Java source
class files
jar files
Mapping V4 parsing/generation/validation
FRAME.graph metadata or validation
Lexicon validator behavior
OPN example semantics
graph rendering
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_14_MORPHOLOGY_METADATA_VALIDATION_SCOPE
Runtime behavior remains MAPPING_V4_12_LEXICON_VALIDATOR
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/PATCH-MANIFEST-v415-morphology-metadata-validator-target.md`
# Patch manifest — Mapping V4.15 morphology metadata validator target

Patch name:

```text
Mapping_V4-26-05-02--v415-morphology-metadata-validator-target-full-from-v412
```

## Type

Documentation-only implementation target, rebuilt on the full V4.12 slim runtime package.

## Base

```text
Mapping_V4-26-05-02--v412-lexicon-validator-slim.zip
```

This package carries forward the V4.13 and V4.14 documentation-only phases and adds V4.15.

## Adds

```text
md/sources/mapping-v4/lexicon-morphology-frame-selection-v413.md
md/meta-inf/2026-05-02-mapping-v413-lexicon-morphology-frame-selection-scope.md
md/examples/opn/mapping-v4-lexicon-v413-expected-output-manifest.md
md/PATCH-MANIFEST-v413-lexicon-morphology-frame-selection-scope.md
md/sources/mapping-v4/morphology-v414.md
md/meta-inf/2026-05-02-mapping-v414-morphology-metadata-validation-scope.md
md/examples/opn/mapping-v4-morphology-v414-expected-output-manifest.md
md/PATCH-MANIFEST-v414-morphology-metadata-validation-scope.md
md/sources/mapping-v4/morphology-v415.md
md/meta-inf/2026-05-02-mapping-v415-morphology-metadata-validator-target.md
md/examples/opn/mapping-v4-morphology-v415-expected-output-manifest.md
md/PATCH-MANIFEST-v415-morphology-metadata-validator-target.md
md/sources-md-zip/Mapping_V4-26-05-02--v415-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v415-morphology-metadata-validator-target-md-only.zip
```

## Changes

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
Java source
class files
jar files
Mapping V4 parsing/generation/validation
FRAME.graph metadata or validation
Lexicon validator behavior
Morphology runtime behavior
OPN example semantics
graph rendering
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_15_MORPHOLOGY_METADATA_VALIDATOR_TARGET
Runtime behavior remains MAPPING_V4_12_LEXICON_VALIDATOR
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/PATCH-MANIFEST-v416-morphology-metadata-validator.md`
# Patch manifest — Mapping V4.16 morphology metadata validator

Patch name:

```text
Mapping_V4-26-05-02--v416-morphology-metadata-validator
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-02--v415-morphology-metadata-validator-target-full-from-v412-slim.zip
```

## Adds

```text
md/sources/mapping-v4/morphology-v416.md
md/meta-inf/2026-05-02-mapping-v416-morphology-metadata-validator.md
md/examples/opn/mapping-v4-morphology-v416-expected-output-manifest.md
md/PATCH-MANIFEST-v416-morphology-metadata-validator.md
examples/opn/mapping-v4-morphology/
examples/opn/mapping-v4-morphology-invalid/
md/sources-md-zip/Mapping_V4-26-05-02--v416-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v416-morphology-metadata-validator-md-only.zip
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
examples/opn/mapping-v4-checker/LAST-RUN.txt
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
generated utterance rules
Mapping V4 placement validation semantics
FRAME.graph metadata or validation
Lexicon basic validation behavior
graph rendering
graph mutation
tree transformation boundary
role inference
automatic lexical insertion
automatic frame selection
automatic inflection
surface-form generation
lexicon rendering
```

## Expected status after applying patch

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/PATCH-MANIFEST-v417-frame-selection-scope.md`
# Patch manifest — Mapping V4.17 explicit frame-selection scope

Patch name:

```text
Mapping_V4-26-05-03--v417-frame-selection-scope
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v416-morphology-metadata-validator.zip
```

## Adds

```text
md/sources/mapping-v4/frame-selection-v417.md
md/meta-inf/2026-05-03-mapping-v417-frame-selection-scope.md
md/examples/opn/mapping-v4-frame-selection-v417-expected-output-manifest.md
md/PATCH-MANIFEST-v417-frame-selection-scope.md
md/sources-md-zip/Mapping_V4-26-05-03--v417-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-03--v417-frame-selection-scope-md-only.zip
```

## Changes

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
examples/opn/mapping-v4-checker/LAST-RUN.txt
```

## Does not change

```text
Java source
class files
jar files
Mapping V4 parsing/generation/validation
FRAME.graph runtime validation behavior
Lexicon runtime validation behavior
Morphology runtime validation behavior
OPN example semantics
graph rendering
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_17_FRAME_SELECTION_SCOPE
Runtime behavior remains MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/PATCH-MANIFEST-v418-frame-selection-validator-target.md`
# Patch manifest — Mapping V4.18 explicit frame-selection validator target

Patch name:

```text
Mapping_V4-26-05-03--v418-frame-selection-validator-target
```

## Type

Documentation-only implementation target.

## Base

```text
Mapping_V4-26-05-03--v417-frame-selection-scope.zip
```

## Adds

```text
md/sources/mapping-v4/frame-selection-v418.md
md/meta-inf/2026-05-03-mapping-v418-frame-selection-validator-target.md
md/examples/opn/mapping-v4-frame-selection-v418-expected-output-manifest.md
md/PATCH-MANIFEST-v418-frame-selection-validator-target.md
md/sources-md-zip/Mapping_V4-26-05-03--v418-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-03--v418-frame-selection-validator-target-md-only.zip
```

## Changes

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
examples/opn/mapping-v4-checker/LAST-RUN.txt
```

## Does not change

```text
Java source
class files
jar files
Mapping V4 parsing/generation/validation
FRAME.graph runtime validation behavior
Lexicon runtime validation behavior
Morphology runtime validation behavior
OPN example semantics
graph rendering
graph mutation
file format
```

## Expected status after applying patch

```text
MAPPING_V4_18_FRAME_SELECTION_VALIDATOR_TARGET
Runtime behavior remains MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.19 — minimal explicit frame-selection validator
```
---

## FILE: `md/PATCH-MANIFEST-v42-wh-scope.md`
# Patch manifest — Mapping V4.2 WH scope

Patch name:

```text
Mapping_V4-26-04-29--v42-wh-scope-md-patch
```

## Type

Documentation and expected-output manifest only.

## Base

```text
Mapping_V4-26-04-29--v41-neg-time-place.zip
```

## Adds

```text
md/sources/mapping-v4/wh-v42.md
md/meta-inf/2026-04-29-mapping-v42-wh-scope.md
md/examples/opn/mapping-v4-wh-expected-output-manifest.md
md/sources-md-zip/Mapping_V4-26-04-29--v42-wh-scope-addendum-bundel.md
md/sources-md-zip/Mapping_V4-26-04-29--v42-wh-scope-md-only.zip
```

## Does not change

```text
Java source
.class files
jar files
parser
generator
validator
checker
UI
rendering
example semantics from V4.1
```

## Expected status after applying patch

```text
MAPPING_V4_2_WH_SCOPE
Mapping V3 regression: unchanged
Mapping V4.1 regression: unchanged
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.3 — minimal WH generator and validator
```
---

## FILE: `md/PATCH-MANIFEST-v420-language-tree-opn-test.md`
# Patch manifest — Mapping V4.20 Language Tree OPN test slice

Patch name:

```text
Mapping_V4-26-05-03--v420-language-tree-opn-test
```

## Type

Small UI/open-behavior + examples/checker slice.

## Base

```text
Mapping_V4-26-05-03--v418-frame-selection-validator-target.zip
```

## Adds

```text
examples/opn/language-tree-v420/
tools/LanguageTreeRegressionChecker.java
run-language-tree-checker.bat
md/sources/mapping-v4/language-tree-v420.md
md/meta-inf/2026-05-03-mapping-v420-language-tree-opn-test.md
md/examples/opn/mapping-v4-language-tree-v420-expected-output-manifest.md
md/PATCH-MANIFEST-v420-language-tree-opn-test.md
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
compiled class files
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Does not change

```text
graph mutation
projection rendering algorithm
generated utterance rules except accepting role C in examples
FRAME.graph validation semantics
Lexicon validation semantics
Morphology validation semantics
```
---

## FILE: `md/PATCH-MANIFEST-v4201-language-tree-opn-open-grid-fix.md`
# Patch manifest — Mapping V4.20.1 Language Tree OPN open-grid fix

Patch name:

```text
Mapping_V4-26-05-03--v4201-language-tree-opn-open-grid-fix
```

## Type

Small corrective behavior/test-data slice.

## Base

```text
Mapping_V4-26-05-03--v420-language-tree-opn-test.zip
```

## Adds

```text
md/sources/mapping-v4/language-tree-v4201.md
md/meta-inf/2026-05-03-mapping-v4201-language-tree-opn-open-grid-fix.md
md/PATCH-MANIFEST-v4201-language-tree-opn-open-grid-fix.md
```

## Changes

```text
userInterface/GraphFileActions.java
tools/LanguageTreeRegressionChecker.java
compiled class files
OpenGraphEd.jar
dist/OpenGraphEd.jar
examples/opn/language-tree-v420/01-ltree-wie-heeft-de-hond-gebeten.opn
examples/opn/language-tree-v420/02-ltree-dat-de-vrouw-de-hond-heeft-gebeten.opn
examples/opn/language-tree-v420/03-ltree-vrouw-heeft-man-boek-gegeven.opn
examples/opn/language-tree-v420/LAST-RUN.txt
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
generated utterance rules
projection rendering algorithm
FRAME.graph validation semantics
Lexicon validation semantics
Morphology validation semantics
graph mutation behavior
```

## Actual checks

```text
Mapping V4.20.1 Language Tree OPN regression checker: 3 pass, 0 fail
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/PATCH-MANIFEST-v46-frame-graph-scope.md`
# Patch manifest — Mapping V4.6 FRAME.graph scope

Patch name:

```text
Mapping_V4-26-05-01--v46-frame-graph-scope
```

## Type

Documentation and expected-output manifest only.

## Base

```text
Mapping_V4-26-05-01--v452-stable-handoff.zip
```

## Adds

```text
md/sources/mapping-v4/frame-graph-v46.md
md/meta-inf/2026-05-01-mapping-v46-frame-graph-scope.md
md/examples/opn/mapping-v4-frame-v46-expected-output-manifest.md
md/PATCH-MANIFEST-v46-frame-graph-scope.md
md/sources-md-zip/Mapping_V4-26-05-01--v46-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v46-frame-graph-scope-md-only.zip
```

## Updates

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
Java source
.class files
jar files
parser
generator
validator
checker
UI
rendering
graph mutation
runtime example semantics
```

## Expected status after applying patch

```text
MAPPING_V4_6_FRAME_GRAPH_SCOPE
Runtime behavior still: MAPPING_V4_5_1_INFO_LABEL
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.7 — FRAME.graph metadata read / count
```
---

## FILE: `md/PATCH-MANIFEST-v47-frame-graph-metadata.md`
# Patch manifest — Mapping V4.7 FRAME.graph metadata read / count

Patch name:

```text
Mapping_V4-26-05-01--v47-frame-graph-metadata
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-01--v46-frame-graph-scope.zip
```

## Adds

```text
md/sources/mapping-v4/frame-graph-v47.md
md/meta-inf/2026-05-01-mapping-v47-frame-graph-metadata.md
md/examples/opn/mapping-v4-frame-v47-expected-output-manifest.md
md/PATCH-MANIFEST-v47-frame-graph-metadata.md
examples/opn/mapping-v4-frame/
md/sources-md-zip/Mapping_V4-26-05-01--v47-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v47-frame-graph-metadata-md-only.zip
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
userInterface/GraphEditorInfoSupport.java
tools/MappingV4RegressionChecker.java
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Does not change

```text
generated utterance rules
Mapping V4 placement validation semantics
graph rendering
graph mutation
tree transformation boundary
lexicon behavior
automatic role inference
```

## Expected status after applying patch

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.8 — FRAME.graph slot validation scope
```
---

## FILE: `md/PATCH-MANIFEST-v48-frame-graph-slot-validation-scope.md`
# Patch manifest — Mapping V4.8 FRAME.graph slot validation scope

Patch name:

```text
Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope
```

## Type

Documentation and expected-output manifest only.

## Base

```text
Mapping_V4-26-05-01--v47-frame-graph-metadata.zip
```

## Adds

```text
md/sources/mapping-v4/frame-graph-v48.md
md/meta-inf/2026-05-01-mapping-v48-frame-graph-slot-validation-scope.md
md/examples/opn/mapping-v4-frame-v48-expected-output-manifest.md
md/PATCH-MANIFEST-v48-frame-graph-slot-validation-scope.md
md/sources-md-zip/Mapping_V4-26-05-01--v48-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope-md-only.zip
```

## Updates

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Does not change

```text
Java source
.class files
jar files
parser
generator
validator
checker
placement rules
UI
graph rendering
graph mutation
runtime example semantics
```

## Expected status after applying patch

```text
MAPPING_V4_8_FRAME_GRAPH_SLOT_VALIDATION_SCOPE
Runtime behavior still: MAPPING_V4_7_FRAME_GRAPH_METADATA
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.9 — FRAME.graph minimal slot validator
```
---

## FILE: `md/PATCH-MANIFEST-v49-frame-graph-minimal-slot-validator.md`
# Patch manifest — Mapping V4.9 FRAME.graph minimal slot validator

Patch name:

```text
Mapping_V4-26-05-02--v49-frame-graph-minimal-slot-validator
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope.zip
```

## Adds

```text
md/sources/mapping-v4/frame-graph-v49.md
md/meta-inf/2026-05-02-mapping-v49-frame-graph-minimal-slot-validator.md
md/examples/opn/mapping-v4-frame-v49-expected-output-manifest.md
md/PATCH-MANIFEST-v49-frame-graph-minimal-slot-validator.md
examples/opn/mapping-v4-frame-invalid/
md/sources-md-zip/Mapping_V4-26-05-02--v49-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-02--v49-frame-graph-minimal-slot-validator-md-only.zip
```

## Changes

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
examples/opn/mapping-v4-frame/EXPECTED.txt
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Does not change

```text
generated utterance rules
Mapping V4 placement validation semantics
graph rendering
graph mutation
tree transformation boundary
lexicon behavior
automatic role inference
```

## Expected status after applying patch

```text
MAPPING_V4_9_FRAME_GRAPH_MINIMAL_SLOT_VALIDATOR
Mapping V4.9 FRAME.graph minimal slot validator regression checker: 37 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/README.md`
# OpenGraphEd

Legacy Java graph editor (2004) restored and refactored to run on modern Java.

## Status

This repository is currently based on the stable refactor baseline:

**OpenGraphEd_refactor_phase33_pq_lifecycle_support_2026-04-02**

This version has been verified as the current stable baseline after a long refactor series.

## What this baseline includes

The codebase has been substantially refactored in the following areas:

- application naming migrated to **OpenGraphEd**
- controller layer reduced and split into focused helpers
- window and dialog handling cleaned up
- file actions isolated
- editor support split into:
  - layout/grid support
  - render/transform support
  - mode/listener support
  - overlay support
- menu and toolbar logic reduced and split into helpers
- `Graph`, `Node`, and `Edge` significantly decomposed into focused support classes
- several operation classes refactored into orchestration + support structures
- partial non-critical cleanup of `PQNode` and `PQTree`

## Important note about scope

The deep PQ reduction core was **intentionally not fully refactored further**.

This was a deliberate decision to preserve stability in the most sensitive algorithmic part of the application.

## Regression note

During the refactor process, a display regression was found in the biconnectivity flow.

That issue was traced to the extracted support logic for biconnected component construction and was corrected. The stable baseline in this repository includes that fix.

## Run

You can run the application either from the built jar or by using the batch scripts.

### Run existing build

```bat
OpenGraphEd.bat
```

or

```bat
run.bat
```

or

```bat
java -jar OpenGraphEd.jar
```

### Open a specific `.graph` file directly

These now work:

```bat
OpenGraphEd.bat "C:\pad\naar\bestand.graph"
```

or

```bat
run.bat "C:\pad\naar\bestand.graph"
```

or

```bat
java -jar OpenGraphEd.jar "C:\pad\naar\bestand.graph"
```

At startup, OpenGraphEd opens the supplied `.graph` file directly in an editor window.
If OpenGraphEd is already running from the same folder, later launches forward the file to the existing app window instead of opening a second app instance.

### Windows double-click association for `.graph`

A one-time Windows association script is included:

```bat
register_graph_file_association.bat
```

This registers `.graph` files for the current Windows user so a double-click opens OpenGraphEd and loads that graph.

Files included for this flow:

- `OpenGraphEd.bat`
- `open_graph_file.bat`
- `register_graph_file_association.bat`
- `unregister_graph_file_association.bat`
- `create_desktop_shortcut.bat`

The Windows launcher/display name is set to:

```text
OpenGraphEd (Java, via DOS .batfile)
```

## Build

On Windows:

```bat
build.bat
```

After building, the compiled output is available in:

```text
out\
```

The repository also includes the packaged runtime jar:

```text
OpenGraphEd.jar
```

## Basic structure

```text
dataStructure/                core data structures
graphStructure/               graph model
operation/                    graph algorithms and operations
userInterface/                Swing UI
userInterface/menuAndToolBar/ menus and toolbar helpers
userInterface/modes/          editor interaction modes
images/                       toolbar and UI images
help/                         HTML help pages
config/                       configuration and persisted dialog settings
docs/                         historical PDF material
out/                          compiled classes and copied runtime resources
```

## Notes

The original code used several legacy patterns and obsolete APIs.
The refactor baseline includes many internal cleanups while preserving behavior as much as possible.

Examples of preserved behavior that were explicitly checked during the refactor process:

- undo / redo
- render state after undo
- display flows
- dialog/window interaction
- save/load
- graph editing operations

## Recommended maintenance approach

For future work, prefer:

- small refactor steps
- build after every step
- functional checks after every step
- extra care in PQ-tree and embedding-related code

Recommended areas for safe future cleanup:

- documentation
- changelog / release notes
- small UI helpers
- isolated utility methods

Areas to treat as high risk:

- PQ reduction logic
- PQ templates
- deep embedding core behavior

## License / origin

This repository contains a restored legacy Java graph editor codebase with targeted modernization and refactoring for current Java environments.


## Build note on Windows

If `OpenGraphEd.jar` is currently running, Windows may lock that file. In that case `build.bat` now still refreshes `out\` and writes a fallback jar as `OpenGraphEd.new.jar` instead of failing the whole build.

Build note for Windows:
- Local launching uses freshly compiled classes from `out\` when available.
- Packaging jars are written first to `dist\OpenGraphEd.jar`.
- If Windows blocks copying a jar into the project root, local app use still works.

## Mapping V3 core stable checkpoint

Mapping V3 core is marked as `MAPPING_V3_CORE_STABLE` in `md/sources/mapping-v3/current-state.md`.

The stable core covers:

- Agens
- Patiens
- RECIPIENT
- THEME
- V
- V-AUX
- V-PART
- split VP: `V-AUX ... V-PART`

Current regression status:

```text
Mapping V3 regression: 13 pass, 0 fail
MD folder check: PASS
```

WH, NEG, TIME/PLACE, DET, FRAME.graph, lexicon and UI/rendering are later phases.

## Mapping V4.0 scope freeze

Mapping V4 has started as a documentation-only scope-freeze phase:

```text
MAPPING_V4_SCOPE_FREEZE
```

V4.0 preserves the Mapping V3 core stable checkpoint and adds V4 planning/source documentation only.

No Java code, class files, jar files, generator, validator, parser, UI, rendering, or example semantics are changed in V4.0.

The package now also includes an md-only sources zip under:

```text
md/sources-md-zip/
```

This zip is intended for manual upload to Project Sources and for source-state verification.

## Mapping V4.3 minimal WH

Mapping V4.3 adds minimal WH behavior:

```text
MAPPING_V4_3_WH_MINIMAL
```

WH is represented as a lexical item on the lexical axis, not as a tree transformation.

Current V4.3 examples include:

```text
wie heeft de hond gebeten
wat heeft vrouw gebreid
wie bijt hond
```

DET splitting, FRAME.graph integration, lexicon behavior and UI/rendering changes remain outside this phase.

## Mapping V4.4 DET scope

Mapping V4.4 is a documentation-only DET scope phase:

```text
MAPPING_V4_4_DET_SCOPE
```

It defines controlled future handling of determiners as separate lexical-axis items:

```text
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
```

This phase does not change Java source, class files, jar files, parser, generator, validator, checker, UI, rendering or runtime example semantics. Runtime behavior remains:

```text
MAPPING_V4_3_WH_MINIMAL
```

The next proposed behavior phase is:

```text
V4.5 — minimal DET generator / validator
```

## Mapping V4.5 minimal DET

Mapping V4.5 adds minimal DET behavior:

```text
MAPPING_V4_5_DET_MINIMAL
```

DET is represented as a lexical item on the lexical axis, not as graph mutation or tree transformation.

Current V4.5 examples include:

```text
vrouw bijt de hond
vrouw heeft de trui gebreid
wie heeft de hond gebeten
```

The V4.5 checker covers V3 core, V4.1 NEG/TIME/PLACE, V4.3 WH and V4.5 DET:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
MD folder check: PASS
```

FRAME.graph, lexicon behavior, adjective placement, complex NP structure, relative clauses, UI/rendering changes and multiple DET-per-target behavior remain outside this phase.

## Mapping V4.5.1 Info label clarification

Mapping V4.5.1 is a small Info-window display clarification:

```text
generated: best: ...
```

is now displayed as:

```text
generated best: ...
```

Meaning:

```text
generated best = the highest-ranked output candidate derived from the placement rules
```

This patch does not change the generator, validator, parser, checker, mapping rules, graph rendering or graph mutation behavior.

Expected checks remain:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Mapping V4.5.2 stable handoff

Mapping V4.5.2 is a documentation-only checkpoint:

```text
MAPPING_V4_5_2_STABLE_HANDOFF
```

It records V4.5.1 as the stable base before the next larger Mapping V4 phase. Runtime behavior remains:

```text
MAPPING_V4_5_1_INFO_LABEL
```

No Java source, class files, jar files, parser, generator, validator, checker, mapping rules, UI behavior, graph rendering, graph mutation behavior or example semantics are changed.

Actual checks for this package state:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

Next intended phase:

```text
V4.6 — FRAME.graph scope
```
## Mapping V4.6 FRAME.graph scope

Mapping V4.6 is a documentation-only scope phase:

```text
MAPPING_V4_6_FRAME_GRAPH_SCOPE
```

It defines `FRAME.graph` as a future semantic/frame context layer.

Core decisions:

```text
STRUCTURE = view
MAPPING_V4 = explicit lexical-axis mapping
FRAME.graph = future semantic/frame context
no graph mutation
no tree transformation / vooropplaatsing
output via generator
validation and generation via the lexical axis
```

`FRAME.graph` is not the generator. It does not infer lexical roles, draw nodes, mutate the graph or change generated utterances in this phase.

Runtime behavior remains:

```text
MAPPING_V4_5_1_INFO_LABEL
```

Expected checks remain:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

Next recommended behavior slice:

```text
V4.7 — FRAME.graph metadata read / count
```

## Mapping V4.7 FRAME.graph metadata read / count

Mapping V4.7 is the first behavior slice for FRAME.graph:

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
```

It recognizes a metadata section:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:
```

The Info window reports:

```text
OPN Frame graph: 1 frames, 2 slots, metadata only
```

Generation still comes from explicit `MAPPING_V4` lexical-axis placement rules. `FRAME_GRAPH` does not infer roles, validate slots, render frames, mutate the graph or change generated utterances.

Actual checks:

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

Next recommended phase:

```text
V4.8 — FRAME.graph slot validation scope
```

## Mapping V4.8 FRAME.graph slot validation scope

Mapping V4.8 is a documentation-only scope phase:

```text
MAPPING_V4_8_FRAME_GRAPH_SLOT_VALIDATION_SCOPE
```

It defines the intended first FRAME.graph slot-validation boundary for a later behavior phase. It does not implement runtime frame validation.

Runtime behavior remains:

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
```

V4.8 reserves the first validator slice for required semantic slots only:

```text
Agens
Patiens
RECIPIENT
THEME
```

Generated output still comes from explicit `MAPPING_V4` lexical-axis placement rules, not from `FRAME_GRAPH`.

Actual checks remain:

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

Implemented next phase:

```text
V4.9 — FRAME.graph minimal slot validator
```

## Mapping V4.9 FRAME.graph minimal slot validator

Mapping V4.9 adds the first runtime slot validator for `FRAME_GRAPH`:

```text
MAPPING_V4_9_FRAME_GRAPH_MINIMAL_SLOT_VALIDATOR
```

The Info window now reports frame counts and validation results, for example:

```text
OPN Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
```

The first accepted frame slot roles are:

```text
Agens
Patiens
RECIPIENT
THEME
```

V4.9 reports diagnostics for:

```text
missing required frame slot
unknown frame slot role
lexical semantic role not licensed by frame
malformed FRAME_GRAPH row
```

Generated output remains derived from explicit `MAPPING_V4` placement rules. FRAME.graph validation failures do not suppress generated output in this phase.

Actual checks:

```text
Mapping V4.9 FRAME.graph minimal slot validator regression checker: 37 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Mapping V4.10 Lexicon metadata read / count

Mapping V4.10 adds the first runtime-visible Lexicon metadata layer:

```text
MAPPING_V4_10_LEXICON_METADATA
```

It recognizes a metadata section:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
END_LEXICON:
```

The Info window reports:

```text
OPN Lexicon: 1 entries, metadata only
```

Generation still comes from explicit `MAPPING_V4` lexical-axis placement rules. `LEXICON` does not infer roles, validate lexical entries, select frames, render entries, mutate the graph or change generated utterances.

Actual checks:

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
## Mapping V4.10.1 straight-edge midpoint cleanup

Mapping V4.10.1 is a small rendering cleanup on top of V4.10:

```text
MAPPING_V4_10_1_STRAIGHT_EDGE_MIDPOINT_CLEANUP
```

Ordinary straight undirected edges no longer draw a visible center/midpoint marker. This removes the blue midpoint on simple structure edges such as `S — V`.

Preserved:

```text
curved/orthogonal edge control markers unchanged
directed edge arrows unchanged
no mapping-rule changes
no generator changes
no validator changes
no FRAME.graph changes
no Lexicon metadata changes
no graph mutation
```

Actual checks:

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```



## Mapping V4.11 Lexicon validation / coupling scope

V4.11 is a documentation-only scope phase. Runtime behavior remains V4.10.1.

The next intended behavior phase is a minimal Lexicon validator based on explicit `MAPPING_V4` items and optional `FRAME_GRAPH` references. Lexicon diagnostics are scoped as informational only: no generated-output changes, no role inference, no automatic lexical insertion, no graph mutation and no lexicon rendering.


## Mapping V4.12 Lexicon validator

Mapping V4.12 adds the first runtime Lexicon validator:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

The Info window now reports Lexicon counts and validation results, for example:

```text
OPN Lexicon: 4 entries; lexicon validation: 4 ok, 0 fail
```

V4.12 reports diagnostics for malformed Lexicon rows, missing lexical key, missing lemma, missing form, duplicate lexical key, unknown lexical role, lexical role absent from explicit `MAPPING_V4` lexical items, and `frame:<name>` references absent from `FRAME_GRAPH` when `FRAME_GRAPH` is present.

Generated output remains derived from explicit `MAPPING_V4` placement rules. Lexicon validation failures do not suppress generated output. The Lexicon layer still does not infer roles, insert lexical items, select frames, inflect forms, render entries or mutate the graph.

Actual checks:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```


## Mapping V4.13 Lexicon / morphology / frame-selection scope

Mapping V4.13 is a documentation-only scope phase:

```text
MAPPING_V4_13_LEXICON_MORPHOLOGY_FRAME_SELECTION_SCOPE
```

Runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

V4.13 reserves later work for morphology metadata and explicit frame-selection behavior. It does not implement morphology validation, automatic inflection, role inference, automatic lexical insertion, automatic frame selection, Lexicon rendering, FRAME.graph generation or graph mutation.

Generated output remains derived from explicit `MAPPING_V4` lexical-axis placement rules.

Actual checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```


## Mapping V4.14 morphology metadata validation scope

Mapping V4.14 is a documentation-only scope phase:

```text
MAPPING_V4_14_MORPHOLOGY_METADATA_VALIDATION_SCOPE
```

It reserves the first later morphology step as metadata validation only. Runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

Reserved future morphology keys:

```text
tense
number
person
gender
case
mood
aspect
finite
```

The intended future validator may report unknown morphology features, missing feature values, duplicate feature keys, feature/`pos` incompatibility and unknown feature values. V4.14 does not implement these checks.

The explicit `form:<surface>` field remains authoritative. V4.14 does not add automatic inflection, surface-form generation, role inference, automatic lexical insertion, automatic frame selection, Lexicon rendering or graph mutation.

Check status remains:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```


## Mapping V4.15 morphology metadata validator target

Mapping V4.15 is a documentation-only implementation target:

```text
MAPPING_V4_15_MORPHOLOGY_METADATA_VALIDATOR_TARGET
```

Runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

This rebuild is based on the full V4.12 slim runtime package and carries forward the V4.13 and V4.14 documentation-only phases.

V4.15 fixes the target behavior for a later morphology metadata validator. The target validator is limited to explicit morphology fields on `LEXICON` rows and does not infer or generate surface forms.

Reserved morphology keys:

```text
tense
number
person
gender
case
mood
aspect
finite
```

Target diagnostics for the later Java slice:

```text
unknown morphology feature
missing morphology feature value
duplicate morphology feature key
morphology feature incompatible with pos
unknown morphology feature value
```

Generated output remains derived from explicit `MAPPING_V4` lexical-axis placement rules. `form:<surface>` remains authoritative; V4.15 adds no automatic inflection, lexicon lookup, frame selection, graph mutation or rendering change.

Actual checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Mapping V4.16 morphology metadata validator

Mapping V4.16 adds the first runtime morphology metadata validator:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

The validator checks explicit morphology fields on `LEXICON` rows, for example:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
```

When morphology metadata is present, the Info window appends a morphology validation summary to the Lexicon line:

```text
OPN Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 1 ok, 0 fail
```

V4.16 reports diagnostics for unknown morphology features, missing values, duplicate feature keys, feature/`pos` incompatibility and unknown feature values. Diagnostics remain informational only. Generated output still comes from explicit `MAPPING_V4` lexical-axis placement rules and is not suppressed by morphology failures.

V4.16 does not add automatic inflection, surface-form generation, role inference, automatic lexical insertion, automatic frame selection, Lexicon rendering or graph mutation.

Actual checks:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```


## Mapping V4.17 explicit frame-selection scope

Mapping V4.17 is a documentation-only scope phase:

```text
MAPPING_V4_17_FRAME_SELECTION_SCOPE
```

Runtime behavior remains:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

V4.17 defines the boundary for later explicit frame-selection validation. The proposed selector source is existing Lexicon metadata on verbal entries:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|geven|lemma:geven|form:gegeven|role:V-PART|frame:GEVEN|pos:VPP
```

The future intent is to allow `FRAME_GRAPH` to contain a multi-frame inventory while validating slot satisfaction and lexical-role licensing against the explicitly selected frame, not blindly against every frame in the inventory.

V4.17 does not implement automatic frame selection, role inference, automatic lexical insertion, generation from `LEXICON`, generation from `FRAME_GRAPH`, graph mutation or rendering changes. Generated output remains derived from explicit `MAPPING_V4` placement rules.

Actual checks remain:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```


## Mapping V4.18 explicit frame-selection validator target

Mapping V4.18 is a documentation-only implementation target:

```text
MAPPING_V4_18_FRAME_SELECTION_VALIDATOR_TARGET
```

Runtime behavior remains:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

V4.18 fixes the target for the next Java behavior slice. The selected frame is explicitly declared by `frame:<name>` on verbal Lexicon rows:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|geven|lemma:geven|form:gegeven|role:V-PART|frame:GEVEN|pos:VPP
```

The intended next validator should allow `FRAME_GRAPH` to contain multiple frames while validating required slots and lexical-role licensing against the selected frame(s), not against every inventory frame.

Target diagnostic strings are fixed for the next implementation slice, including:

```text
missing selected frame for multi-frame FRAME_GRAPH
multiple selected frames: BIJTEN, GEVEN
selected frame BIJTEN is attached to non-verbal lexicon entry hond
lexical role THEME is not licensed by selected frame BIJTEN
```

Generated output remains derived from explicit `MAPPING_V4` placement rules. V4.18 adds no automatic frame selection, role inference, automatic lexical insertion, generation from `LEXICON`, generation from `FRAME_GRAPH`, graph mutation or rendering changes.

Actual checks remain:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Mapping V4.20 Language Tree OPN test slice

V4.20 adds first testable Language Tree OPN examples. Files with `STRUCTURE_TYPE: LANGUAGE_TREE` open with the projection context active: LEX left and SYN right.

Run:

```text
run-language-tree-checker.bat
```

Expected:

```text
Mapping V4.20 Language Tree OPN regression checker: 3 pass, 0 fail
```

## Mapping V4.20.1 Language Tree OPN open-grid fix

Mapping V4.20.1 fixes the first Language Tree OPN test slice:

```text
MAPPING_V4_20_1_LANGUAGE_TREE_OPN_OPEN_GRID_FIX
```

The pipe-style OPN loader now maps `STRUCTURE_NODES` coordinates to the 20 px OpenGraphGrid and expands the grid to fit the largest source coordinate. The three Language Tree OPN examples have been revised so no two source nodes occupy the same horizontal grid row.

Run:

```text
run-language-tree-checker.bat
```

Expected:

```text
Mapping V4.20.1 Language Tree OPN regression checker: 3 pass, 0 fail
```
---

## FILE: `md/examples/opn/mapping-v3-expected-output-manifest.md`
# Mapping V3.5 Expected Output Manifest

Status: manual regression manifest.

## Scope

This manifest covers the current Mapping V3 core phase:

- Agens
- Patiens
- RECIPIENT
- THEME
- V
- V-AUX
- V-PART
- split VP: `heeft ... gebreid` / `heeft ... gegeven`

Out of scope:

- WH / vraagzinnen
- NEG
- TIME / PLACE / bijwoorden
- DET splitting
- FRAME.graph integration
- lexicon / automatic role inference
- UI rendering / rotate / layout

## Valid examples

Directory:

```text
examples/opn/mapping-v3-core/
```

Expected file:

```text
examples/opn/mapping-v3-core/EXPECTED.txt
```

| File | Expected best | Expected validation |
|---|---|---|
| `01-vrouw-breit-trui.opn` | `vrouw breit trui` | `3 ok, 0 fail` |
| `02-vrouw-heeft-trui-gebreid.opn` | `vrouw heeft trui gebreid` | `4 ok, 0 fail` |
| `03-vrouw-geeft-man-boek.opn` | `vrouw geeft man boek` | `4 ok, 0 fail` |
| `04-vrouw-heeft-man-boek-gegeven.opn` | `vrouw heeft man boek gegeven` | `5 ok, 0 fail` |
| `05-vrouw-heeft-man-een-boek-gegeven.opn` | `vrouw heeft man een boek gegeven` | `5 ok, 0 fail` |

## Invalid examples

Directory:

```text
examples/opn/mapping-v3-core-invalid/
```

Expected file:

```text
examples/opn/mapping-v3-core-invalid/EXPECTED-FAIL.txt
```

| File | Expected failure class |
|---|---|
| `01-missing-role.opn` | missing lexical role |
| `02-unknown-role.opn` | unknown role |
| `03-missing-theme-target.opn` | missing THEME target |
| `04-missing-vpart-target.opn` | missing V-PART target |
| `05-recipient-theme-cycle.opn` | ordering cycle |
| `06-verb-domain-missing-id.opn` | broken verb-domain reference |
| `07-duplicate-lexical-id.opn` | duplicate lexical id |
| `08-missing-verb-anchor.opn` | missing V-anchor |

## Pass criteria

Valid examples:

```text
validation: ... 0 fail
expected generated best matches exactly
```

Invalid examples:

```text
validation: ... fail
loaded file name appears in validation details
generated: none (invalid mapping)
```

The invalid mapping Info behavior is intentionally info-only: no modal error dialog is expected.
---

## FILE: `md/examples/opn/mapping-v4-det-v44-expected-output-manifest.md`
# Mapping V4.4 expected-output manifest — DET scope

Status: documentation-only expected-output manifest.

This manifest defines target behavior for a later V4.5 implementation. V4.4 does not implement or run these checks yet.

## Directory proposal

Future valid examples:

```text
examples/opn/mapping-v4-det/
```

Future invalid examples:

```text
examples/opn/mapping-v4-det-invalid/
```

## Valid examples

### 01-vrouw-bijt-de-hond.opn

Purpose:

```text
simple DET + Patiens
```

Lexical interpretation:

```text
x1|vrouw|role:Agens
x2|bijt|role:V
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
```

Expected mapping summary:

```text
Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules, DET scope
```

Expected validation:

```text
validation: 3 ok, 0 fail (DET placement rules satisfied)
```

Expected generated:

```text
generated: best: vrouw bijt de hond
```

### 02-vrouw-heeft-de-trui-gebreid.opn

Purpose:

```text
split VP + DET
```

Lexical interpretation:

```text
x1|vrouw|role:Agens
x2|heeft|role:V-AUX
x3|de|role:DET|det-target:Patiens
x4|trui|role:Patiens
x5|gebreid|role:V-PART
```

Expected mapping summary:

```text
Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules, DET scope
```

Expected validation:

```text
validation: 5 ok, 0 fail (DET placement rules satisfied)
```

Expected generated:

```text
generated: best: vrouw heeft de trui gebreid
```

### 03-wie-heeft-de-hond-gebeten.opn

Purpose:

```text
WH + DET
```

Lexical interpretation:

```text
x1|wie|role:WH|wh-target:Agens|pos:WH
x2|heeft|role:V-AUX
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
x5|gebeten|role:V-PART
```

Expected mapping summary:

```text
Mapping v4: 5 lexical items, 1 verb domains, 6 placement rules, WH + DET scope
```

Expected validation:

```text
validation: 6 ok, 0 fail (WH and DET placement rules satisfied)
```

Expected generated:

```text
generated: best: wie heeft de hond gebeten
```

## Invalid examples

### 01-det-missing-target.opn

Expected validation starts with:

```text
validation: ... 1 fail
```

Expected details contain:

```text
file: 01-det-missing-target.opn
missing det-target for role DET at x3
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 02-det-unknown-target.opn

Expected validation starts with:

```text
validation: ... 1 fail
```

Expected details contain:

```text
file: 02-det-unknown-target.opn
unknown det-target LOC for role DET at x3
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 03-det-target-absent.opn

Expected validation starts with:

```text
validation: ... 1 fail
```

Expected details contain:

```text
file: 03-det-target-absent.opn
missing lexical target Patiens for DET at x3
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 04-det-ordering-cycle.opn

Expected validation starts with:

```text
validation: ... 1 fail
```

Expected details contain:

```text
file: 04-det-ordering-cycle.opn
ordering cycle in best placement rules
```

Expected generated:

```text
generated: none (invalid mapping)
```

## Pass condition for future V4.5

When implemented in V4.5, the checker should include the unchanged V3 core, V4.1 NEG/TIME/PLACE, V4.3 WH and these DET cases.

Proposed future result:

```text
Mapping V4.5 DET regression checker: pass
```

Exact pass/fail counts should be fixed only when the example files and checker cases are added.
---

## FILE: `md/examples/opn/mapping-v4-det-v45-expected-output-manifest.md`
# Mapping V4.5 expected-output manifest — minimal DET

## Valid examples

Directory:

```text
examples/opn/mapping-v4-det/
```

Expected:

```text
01-vrouw-bijt-de-hond.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt de hond

02-vrouw-heeft-de-trui-gebreid.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw heeft de trui gebreid

03-wie-heeft-de-hond-gebeten.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 6 placement rules
expected validation: validation: 6 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: wie heeft de hond gebeten
```

## Invalid examples

Directory:

```text
examples/opn/mapping-v4-det-invalid/
```

Expected:

```text
01-det-missing-target.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 01-det-missing-target.opn
expected details contain: missing det-target for role DET at x3
expected generated: generated: none (invalid mapping)

02-det-unknown-target.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 02-det-unknown-target.opn
expected details contain: unknown det-target LOC for role DET at x3
expected generated: generated: none (invalid mapping)

03-det-target-absent.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 03-det-target-absent.opn
expected details contain: missing lexical target Patiens for DET at x3
expected generated: generated: none (invalid mapping)

04-det-ordering-cycle.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 04-det-ordering-cycle.opn
expected details contain: ordering cycle in best placement rules
expected generated: generated: none (invalid mapping)
```

## Pass condition

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
```
---

## FILE: `md/examples/opn/mapping-v4-frame-selection-v417-expected-output-manifest.md`
# Mapping V4.17 expected-output manifest — explicit frame-selection scope

Status:

```text
documentation-only target manifest
runtime remains V4.16
```

## Purpose

This manifest defines target output for later explicit frame-selection validation.

V4.17 itself does not add runtime examples or checker cases.

## Future valid examples

Future directory:

```text
examples/opn/mapping-v4-frame-selection/
```

### 01-frame-selection-bijten-from-multiframe-inventory.opn

Purpose:

```text
explicitly select BIJTEN from a FRAME_GRAPH inventory that also contains GEVEN
```

Lexicon selector target:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
```

Frame inventory target:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

Expected future frame-selection summary:

```text
frame selection: 1 selected, 0 fail
```

Expected future selected-frame validation summary:

```text
selected frame validation: 2 ok, 0 fail
```

Generated best remains derived from `MAPPING_V4` placement rules:

```text
generated: best: vrouw bijt de hond
```

### 02-frame-selection-geven-vpart-from-multiframe-inventory.opn

Purpose:

```text
explicitly select GEVEN from a V-PART Lexicon row
```

Lexicon selector target:

```text
lex|geven|lemma:geven|form:gegeven|role:V-PART|frame:GEVEN|pos:VPP
```

Expected future frame-selection summary:

```text
frame selection: 1 selected, 0 fail
```

Expected future selected-frame validation summary:

```text
selected frame validation: 3 ok, 0 fail
```

Generated best remains:

```text
generated: best: vrouw heeft man boek gegeven
```

## Future invalid examples

Future directory:

```text
examples/opn/mapping-v4-frame-selection-invalid/
```

These examples should keep Mapping V4 placement validation valid. Frame-selection diagnostics should be reported separately, and generated output should not be suppressed in the first diagnostic slice.

### 01-frame-selection-missing-selected-frame.opn

Target condition:

```text
FRAME_GRAPH contains multiple frames, but no verbal Lexicon row contains frame:<name>
```

Expected future diagnostic:

```text
missing selected frame for multi-frame FRAME_GRAPH
```

Expected future summary:

```text
frame selection: 0 selected, 1 fail
```

### 02-frame-selection-non-verbal-selector.opn

Target condition:

```text
frame:<name> appears on a non-verbal Lexicon row
```

Expected future diagnostic:

```text
selected frame BIJTEN is attached to non-verbal lexicon entry hond
```

Expected future summary:

```text
frame selection: 0 selected, 1 fail
```

### 03-frame-selection-multiple-selected-frames.opn

Target condition:

```text
more than one verbal Lexicon row selects a different frame
```

Expected future diagnostic:

```text
multiple selected frames: BIJTEN, GEVEN
```

Expected future summary:

```text
frame selection: 2 selected, 1 fail
```

### 04-frame-selection-role-not-licensed.opn

Target condition:

```text
selected frame BIJTEN, but explicit MAPPING_V4 contains a semantic role not licensed by BIJTEN
```

Expected future diagnostic:

```text
lexical role THEME is not licensed by selected frame BIJTEN
```

Expected future selected-frame validation summary:

```text
selected frame validation: 2 ok, 1 fail
```

## Future pass condition

Exact pass/fail counts should be fixed only when the Java behavior, example files and checker cases are added.

Proposed future result:

```text
Mapping V4.19 explicit frame-selection validator regression checker: pass
```

## Current V4.17 pass condition

Because V4.17 is documentation-only, the actual checks remain:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/examples/opn/mapping-v4-frame-selection-v418-expected-output-manifest.md`
# Mapping V4.18 expected-output manifest — explicit frame-selection validator target

Status:

```text
documentation-only implementation target
runtime remains V4.16
```

## Purpose

This manifest fixes the target output for the next Java behavior slice: minimal explicit selected-frame validation.

V4.18 itself does not add runtime examples or checker cases.

## Future valid examples

Future directory:

```text
examples/opn/mapping-v4-frame-selection/
```

### 01-frame-selection-bijten-from-multiframe-inventory.opn

Purpose:

```text
explicitly select BIJTEN from a FRAME_GRAPH inventory that also contains GEVEN
```

Lexicon selector:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
```

Target frame inventory:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

Expected mapping:

```text
Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
```

Expected Mapping V4 validation:

```text
validation: 3 ok, 0 fail (best placement rules satisfied)
```

Expected generated output:

```text
generated: best: vrouw bijt de hond
```

Expected frame graph output:

```text
Frame graph: 2 frames, 5 slots; frame selection: 1 selected, 0 fail; selected frame validation: 2 ok, 0 fail (selected frame slots satisfied)
```

### 02-frame-selection-geven-vpart-from-multiframe-inventory.opn

Purpose:

```text
explicitly select GEVEN from a V-PART Lexicon row
```

Lexicon selector:

```text
lex|geven|lemma:geven|form:gegeven|role:V-PART|frame:GEVEN|pos:VPP
```

Expected mapping:

```text
Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
```

Expected Mapping V4 validation:

```text
validation: 5 ok, 0 fail (best placement rules satisfied)
```

Expected generated output:

```text
generated: best: vrouw heeft man boek gegeven
```

Expected frame graph output:

```text
Frame graph: 2 frames, 5 slots; frame selection: 1 selected, 0 fail; selected frame validation: 3 ok, 0 fail (selected frame slots satisfied)
```

## Future invalid frame-selection examples

Future directory:

```text
examples/opn/mapping-v4-frame-selection-invalid/
```

These examples must keep Mapping V4 placement validation valid. Frame-selection diagnostics are separate and informational; generated output remains present.

### 01-frame-selection-missing-selected-frame.opn

Target condition:

```text
FRAME_GRAPH contains multiple frames, but no verbal Lexicon row contains frame:<name>
```

Expected Mapping V4 validation starts with:

```text
validation: 3 ok, 0 fail
```

Expected generated output:

```text
generated: best: vrouw bijt de hond
```

Expected frame graph output:

```text
Frame graph: 2 frames, 5 slots; frame selection: 0 selected, 1 fail (file: 01-frame-selection-missing-selected-frame.opn; missing selected frame for multi-frame FRAME_GRAPH)
```

### 02-frame-selection-non-verbal-selector.opn

Target condition:

```text
frame:<name> appears on a non-verbal Lexicon row
```

Expected diagnostic:

```text
selected frame BIJTEN is attached to non-verbal lexicon entry hond
```

Expected frame graph output starts with:

```text
Frame graph: 1 frames, 2 slots; frame selection: 0 selected, 1 fail
```

### 03-frame-selection-multiple-selected-frames.opn

Target condition:

```text
more than one verbal Lexicon row selects a different frame
```

Expected diagnostic:

```text
multiple selected frames: BIJTEN, GEVEN
```

Expected frame graph output starts with:

```text
Frame graph: 2 frames, 5 slots; frame selection: 2 selected, 1 fail
```

### 04-frame-selection-role-not-licensed.opn

Target condition:

```text
selected frame BIJTEN, but explicit MAPPING_V4 contains a semantic role not licensed by BIJTEN
```

Expected Mapping V4 validation starts with:

```text
validation: 3 ok, 0 fail
```

Expected generated output:

```text
generated: best: vrouw bijt boek
```

Expected frame graph output:

```text
Frame graph: 2 frames, 5 slots; frame selection: 1 selected, 0 fail; selected frame validation: 2 ok, 1 fail (file: 04-frame-selection-role-not-licensed.opn; lexical role THEME is not licensed by selected frame BIJTEN)
```

### 05-frame-selection-selected-frame-not-in-frame-graph.opn

Target condition:

```text
verbal Lexicon row selects a frame that is absent from FRAME_GRAPH
```

Expected diagnostic:

```text
selected frame ZIEN is not present in FRAME_GRAPH
```

Expected frame graph output starts with:

```text
Frame graph: 2 frames, 5 slots; frame selection: 1 selected, 1 fail
```

## Future pass condition

When implemented in the next Java behavior slice, the checker should include unchanged V3 core, V4.1 NEG/TIME/PLACE, V4.3 WH, V4.5 DET, V4.9 FRAME.graph, V4.12 Lexicon, V4.16 morphology and the new frame-selection examples.

Proposed future result:

```text
Mapping V4.19 explicit frame-selection validator regression checker: pass
```

Exact pass/fail counts should be fixed only when the `.opn` examples and checker cases are added.

## Current V4.18 pass condition

Because V4.18 is documentation-only, actual checks remain:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/examples/opn/mapping-v4-frame-v46-expected-output-manifest.md`
# Mapping V4.6 expected-output manifest — FRAME.graph scope

Status: documentation-only expected-output manifest.

This manifest defines target behavior for later FRAME.graph implementation phases. V4.6 does not implement or run these checks yet.

## Directory proposal

Future valid examples:

```text
examples/opn/mapping-v4-frame/
```

Future invalid examples:

```text
examples/opn/mapping-v4-frame-invalid/
```

## Valid example targets

### 01-frame-bijten-explicit-mapping.opn

Purpose:

```text
FRAME.graph metadata plus unchanged explicit Mapping V4 lexical-axis mapping
```

Proposed future frame section:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:
```

Existing explicit Mapping V4 lexical interpretation:

```text
x1|vrouw|role:Agens
x2|bijt|role:V
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
```

Expected generated best remains:

```text
generated best: vrouw bijt de hond
```

Expected future frame summary, if a later metadata-read phase implements it:

```text
Frame graph: 1 frames, 2 slots, metadata only
```

### 02-frame-geven-explicit-mapping.opn

Purpose:

```text
FRAME.graph metadata for a ditransitive frame plus unchanged explicit Mapping V4 lexical-axis mapping
```

Proposed future frame section:

```text
FRAME_GRAPH:
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

Existing explicit Mapping V4 lexical interpretation:

```text
x1|vrouw|role:Agens
x2|heeft|role:V-AUX
x3|man|role:RECIPIENT
x4|boek|role:THEME
x5|gegeven|role:V-PART
```

Expected generated best remains:

```text
generated best: vrouw heeft man boek gegeven
```

Expected future frame summary, if a later metadata-read phase implements it:

```text
Frame graph: 1 frames, 3 slots, metadata only
```

## Future invalid target examples

These are not V4.6 runtime checks. They are reserved for a later validator phase.

### 01-frame-missing-required-slot.opn

Future expected validation detail:

```text
missing required frame slot Patiens for frame BIJTEN
```

### 02-frame-unknown-role.opn

Future expected validation detail:

```text
unknown frame slot role LOC for frame BIJTEN
```

### 03-frame-role-not-licensed.opn

Future expected validation detail:

```text
lexical role PLACE is not licensed by frame BIJTEN
```

### 04-frame-section-drawn-as-structure.opn

Future expected validation detail:

```text
FRAME_GRAPH metadata must not be drawn as STRUCTURE content
```

## V4.6 pass condition

Because V4.6 is documentation-only, the actual pass condition remains unchanged:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/examples/opn/mapping-v4-frame-v47-expected-output-manifest.md`
# Mapping V4.7 expected-output manifest — FRAME.graph metadata read / count

## Valid examples

Directory:

```text
examples/opn/mapping-v4-frame/
```

Expected:

```text
01-frame-bijten-explicit-mapping.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt de hond
expected frame graph: Frame graph: 1 frames, 2 slots, metadata only

02-frame-geven-explicit-mapping.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw heeft man boek gegeven
expected frame graph: Frame graph: 1 frames, 3 slots, metadata only
```

## Pass condition

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
```

The checker total consists of:

- V3 core valid + invalid: 13 checks
- V4.1 NEG/TIME/PLACE valid + invalid: 6 checks
- V4.3 WH valid + invalid: 5 checks
- V4.5 DET valid + invalid: 7 checks
- V4.7 FRAME.graph metadata valid: 2 checks

## Scope boundary

The FRAME.graph examples check metadata read/count only.

They do not introduce:

```text
frame-slot validation
role inference
generation from FRAME_GRAPH
FRAME.graph rendering
graph mutation
```
---

## FILE: `md/examples/opn/mapping-v4-frame-v48-expected-output-manifest.md`
# Mapping V4.8 expected-output manifest — FRAME.graph slot validation scope

Status: documentation-only expected-output manifest.

This manifest defines target behavior for a later FRAME.graph slot-validation implementation. V4.8 does not implement or run these checks.

## Base behavior preserved from V4.7

Current valid FRAME.graph metadata examples remain runtime examples:

```text
examples/opn/mapping-v4-frame/01-frame-bijten-explicit-mapping.opn
examples/opn/mapping-v4-frame/02-frame-geven-explicit-mapping.opn
```

Current V4.7 behavior remains:

```text
FRAME_GRAPH metadata read/count
Info summary: Frame graph: ... metadata only
generated best unchanged
```

## Future valid examples for V4.9

### 01-frame-bijten-slots-valid.opn

Purpose:

```text
BIJTEN frame declares Agens and Patiens; explicit MAPPING_V4 fills both roles.
```

Future expected frame validation:

```text
frame validation: 2 ok, 0 fail
```

Future expected generated best remains:

```text
generated best: vrouw bijt de hond
```

### 02-frame-geven-slots-valid.opn

Purpose:

```text
GEVEN frame declares Agens, RECIPIENT and THEME; explicit MAPPING_V4 fills all three roles.
```

Future expected frame validation:

```text
frame validation: 3 ok, 0 fail
```

Future expected generated best remains:

```text
generated best: vrouw heeft man boek gegeven
```

## Future invalid examples for V4.9

These are documentation-only targets in V4.8.

### 01-frame-missing-required-slot.opn

Expected future diagnostic:

```text
missing required frame slot Patiens for frame BIJTEN
```

### 02-frame-unknown-slot-role.opn

Expected future diagnostic:

```text
unknown frame slot role LOC for frame BIJTEN
```

### 03-frame-role-not-licensed.opn

Expected future diagnostic:

```text
lexical role PLACE is not licensed by frame BIJTEN
```

### 04-frame-malformed-row.opn

Expected future diagnostic:

```text
malformed FRAME_GRAPH row
```

## V4.8 pass condition

Because V4.8 is documentation-only, the actual pass condition remains:

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Scope boundary

V4.8 does not add:

```text
runtime frame-slot validation
invalid frame examples
checker expansion
generated-output changes
graph mutation
FRAME.graph rendering
role inference
lexicon lookup
```
---

## FILE: `md/examples/opn/mapping-v4-frame-v49-expected-output-manifest.md`
# Mapping V4.9 expected-output manifest — FRAME.graph minimal slot validator

## Valid examples

Directory:

```text
examples/opn/mapping-v4-frame/
```

Expected:

```text
01-frame-bijten-explicit-mapping.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt de hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)

02-frame-geven-explicit-mapping.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw heeft man boek gegeven
expected frame graph: Frame graph: 1 frames, 3 slots; frame validation: 3 ok, 0 fail (frame slots satisfied)
```

## Invalid FRAME.graph examples

Directory:

```text
examples/opn/mapping-v4-frame-invalid/
```

These examples keep Mapping V4 placement validation valid. Frame diagnostics are reported separately, and generated output is not suppressed.

Expected:

```text
01-frame-missing-required-slot.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 1 ok, 1 fail (file: 01-frame-missing-required-slot.opn; missing required frame slot Patiens for frame BIJTEN)

02-frame-unknown-slot-role.opn
expected validation starts with: validation: 1 ok, 0 fail
expected generated: generated: best: bijt
expected frame graph: Frame graph: 1 frames, 1 slots; frame validation: 0 ok, 1 fail (file: 02-frame-unknown-slot-role.opn; unknown frame slot role LOC for frame BIJTEN)

03-frame-role-not-licensed.opn
expected validation starts with: validation: 3 ok, 0 fail
expected generated: generated: best: vrouw bijt hond daar
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 1 fail (file: 03-frame-role-not-licensed.opn; lexical role PLACE is not licensed by frame BIJTEN)

04-frame-malformed-row.opn
expected validation starts with: validation: 1 ok, 0 fail
expected generated: generated: best: bijt
expected frame graph: Frame graph: 1 frames, 0 slots; frame validation: 0 ok, 1 fail (file: 04-frame-malformed-row.opn; malformed FRAME_GRAPH row)
```

## Pass condition

```text
Mapping V4.9 FRAME.graph minimal slot validator regression checker: 37 pass, 0 fail
```
---

## FILE: `md/examples/opn/mapping-v4-language-tree-v420-expected-output-manifest.md`
# Mapping V4.20 expected-output manifest — Language Tree OPN

Directory:

```text
examples/opn/language-tree-v420/
```

Expected checker result:

```text
Mapping V4.20 Language Tree OPN regression checker
summary: 3 pass, 0 fail
```

Checked per file:

```text
node count
edge count
LEX projection source count
SYN projection source count
language-tree projection preference
generated utterance substring
```
---

## FILE: `md/examples/opn/mapping-v4-lexicon-v410-expected-output-manifest.md`
# Mapping V4.10 expected-output manifest — Lexicon metadata read / count

## Valid examples

Directory:

```text
examples/opn/mapping-v4-lexicon/
```

Expected:

```text
01-lexicon-bijten-explicit-mapping.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt de hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 4 entries, metadata only

02-lexicon-geven-explicit-mapping.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw heeft man boek gegeven
expected frame graph: Frame graph: 1 frames, 3 slots; frame validation: 3 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 5 entries, metadata only
```

## Pass condition

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
```

## Scope boundary

The Lexicon examples check metadata read/count only.

They do not introduce:

```text
lexicon validation
role inference
generation from LEXICON
automatic frame selection
morphology
lexicon rendering
graph mutation
```
---

## FILE: `md/examples/opn/mapping-v4-lexicon-v411-expected-output-manifest.md`
# Mapping V4.11 — Lexicon validation expected-output manifest

Status:

```text
documentation-only target manifest
runtime remains V4.10.1
```

## Purpose

This manifest reserves the expected target behavior for the next lexicon validation implementation phase.

## Current V4.11 behavior

No runtime behavior changes are made in V4.11.

Current valid lexicon examples continue to use the V4.10 metadata-only expectation:

```text
Lexicon: <n> entries, metadata only
```

## Target valid behavior for later implementation

Candidate valid summary:

```text
Lexicon: <n> entries; lexicon validation: <ok> ok, 0 fail
```

Valid cases should not change generated output.

## Target invalid behavior for later implementation

Candidate invalid examples:

```text
examples/opn/mapping-v4-lexicon-invalid/01-lexicon-malformed-row.opn
examples/opn/mapping-v4-lexicon-invalid/02-lexicon-missing-key.opn
examples/opn/mapping-v4-lexicon-invalid/03-lexicon-duplicate-key.opn
examples/opn/mapping-v4-lexicon-invalid/04-lexicon-unknown-role.opn
examples/opn/mapping-v4-lexicon-invalid/05-lexicon-role-not-in-mapping.opn
examples/opn/mapping-v4-lexicon-invalid/06-lexicon-frame-not-in-frame-graph.opn
```

Diagnostics should remain informational. Generated output should not be suppressed by lexicon validation failures in the first validator phase.

## Out of scope

```text
generated-output changes
role inference
automatic lexical insertion
automatic frame selection
morphology / inflection
UI rendering of lexicon entries
graph mutation
```
---

## FILE: `md/examples/opn/mapping-v4-lexicon-v412-expected-output-manifest.md`
# Mapping V4.12 expected-output manifest — Lexicon validator

## Valid examples

Directory:

```text
examples/opn/mapping-v4-lexicon/
```

Expected:

```text
01-lexicon-bijten-explicit-mapping.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt de hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 4 entries; lexicon validation: 4 ok, 0 fail

02-lexicon-geven-explicit-mapping.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw heeft man boek gegeven
expected frame graph: Frame graph: 1 frames, 3 slots; frame validation: 3 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 5 entries; lexicon validation: 5 ok, 0 fail
```

## Invalid Lexicon examples

Directory:

```text
examples/opn/mapping-v4-lexicon-invalid/
```

These examples keep Mapping V4 placement validation valid. Lexicon diagnostics are reported separately, and generated output is not suppressed.

Expected:

```text
01-lexicon-malformed-row.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 01-lexicon-malformed-row.opn; malformed LEXICON row)

02-lexicon-missing-key.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 02-lexicon-missing-key.opn; missing lexical key)

03-lexicon-duplicate-key.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 2 entries; lexicon validation: 1 ok, 1 fail (file: 03-lexicon-duplicate-key.opn; duplicate lexical key vrouw)

04-lexicon-unknown-role.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 04-lexicon-unknown-role.opn; unknown lexical role LOC for lexicon key loc)

05-lexicon-role-not-in-mapping.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 05-lexicon-role-not-in-mapping.opn; lexical role PLACE is not present in explicit MAPPING_V4 items)

06-lexicon-frame-not-in-frame-graph.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected frame graph: Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
expected lexicon: Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 06-lexicon-frame-not-in-frame-graph.opn; lexicon frame LOPEN is not present in FRAME_GRAPH)
```

## Pass condition

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
```
---

## FILE: `md/examples/opn/mapping-v4-lexicon-v413-expected-output-manifest.md`
# Mapping V4.13 expected-output manifest — Lexicon / morphology / frame-selection scope

Status:

```text
documentation-only target manifest
runtime remains V4.12
```

## Current behavior preserved

V4.13 does not change current valid Lexicon examples.

Directory:

```text
examples/opn/mapping-v4-lexicon/
```

Expected runtime behavior remains the V4.12 expectation:

```text
Lexicon: <n> entries; lexicon validation: <ok> ok, 0 fail
```

Invalid Lexicon examples also remain V4.12 informational diagnostics only:

```text
examples/opn/mapping-v4-lexicon-invalid/
```

Lexicon validation failures do not suppress generated output.

## Reserved target behavior for later morphology phases

Candidate future examples may use fields such as:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3
```

Potential future diagnostics, not implemented in V4.13:

```text
unknown morphology feature
missing morphology feature
morphology feature incompatible with pos
duplicate morphology feature key
```

## Reserved target behavior for later frame-selection phases

Candidate future examples may use explicit frame-selection metadata. V4.13 does not define a runtime syntax beyond the existing `frame:<name>` Lexicon field.

Potential future diagnostics, not implemented in V4.13:

```text
ambiguous lexicon frame selection
lexicon frame incompatible with explicit Mapping V4 verb domain
selected frame not present in FRAME_GRAPH
selected frame does not license explicit lexical role
```

## V4.13 pass condition

Because V4.13 is documentation-only, the actual pass condition remains:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Out of scope

```text
runtime morphology validation
automatic inflection
generation from LEXICON
generation from FRAME_GRAPH
role inference
automatic lexical insertion
automatic frame selection
UI rendering of Lexicon entries
graph mutation
```
---

## FILE: `md/examples/opn/mapping-v4-morphology-v414-expected-output-manifest.md`
# Mapping V4.14 expected-output manifest — morphology metadata validation scope

Status:

```text
documentation-only target manifest
runtime remains V4.12
```

## Current behavior preserved

V4.14 does not change current valid or invalid Lexicon examples.

Current Lexicon validation remains V4.12 behavior:

```text
Lexicon: <n> entries; lexicon validation: <ok> ok, <fail> fail
```

Lexicon validation failures remain informational and do not suppress generated output.

## Reserved target behavior for a later morphology validator

Candidate future valid Lexicon row:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
```

Candidate future summary:

```text
morphology validation: <ok> ok, 0 fail
```

Candidate future invalid examples:

```text
examples/opn/mapping-v4-morphology-invalid/01-morphology-unknown-feature.opn
examples/opn/mapping-v4-morphology-invalid/02-morphology-missing-feature-value.opn
examples/opn/mapping-v4-morphology-invalid/03-morphology-duplicate-feature-key.opn
examples/opn/mapping-v4-morphology-invalid/04-morphology-feature-incompatible-with-pos.opn
examples/opn/mapping-v4-morphology-invalid/05-morphology-unknown-feature-value.opn
```

Candidate future diagnostics:

```text
unknown morphology feature
missing morphology feature value
duplicate morphology feature key
morphology feature incompatible with pos
unknown morphology feature value
```

## V4.14 pass condition

Because V4.14 is documentation-only, the check status remains:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Out of scope

```text
runtime morphology validation
automatic inflection
surface-form generation
generation from LEXICON
generation from FRAME_GRAPH
role inference
automatic lexical insertion
automatic frame selection
Lexicon rendering
graph mutation
```
---

## FILE: `md/examples/opn/mapping-v4-morphology-v415-expected-output-manifest.md`
# Mapping V4.15 expected-output manifest — morphology metadata validator target

Status:

```text
documentation-only target manifest
runtime remains V4.12
```

## Purpose

This manifest defines target output for the next Java behavior slice that may implement morphology metadata validation.

V4.15 itself does not add runtime examples or checker cases.

## Future valid examples

Future directory:

```text
examples/opn/mapping-v4-morphology/
```

### 01-morphology-bijten-present-3sg.opn

Purpose:

```text
valid finite verb morphology metadata
```

Lexicon target:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
```

Expected future morphology summary:

```text
morphology validation: 1 ok, 0 fail
```

Generated best remains derived from `MAPPING_V4` placement rules.

### 02-morphology-nominal-number-gender.opn

Purpose:

```text
valid nominal morphology metadata
```

Lexicon target:

```text
lex|hond|lemma:hond|form:hond|role:Patiens|pos:N|number:sg|gender:common
```

Expected future morphology summary:

```text
morphology validation: 1 ok, 0 fail
```

### 03-morphology-det-number-gender.opn

Purpose:

```text
valid determiner morphology metadata
```

Lexicon target:

```text
lex|de|lemma:de|form:de|role:DET|pos:DET|number:sg|gender:common
```

Expected future morphology summary:

```text
morphology validation: 1 ok, 0 fail
```

## Future invalid examples

Future directory:

```text
examples/opn/mapping-v4-morphology-invalid/
```

These examples should keep Mapping V4 placement validation valid. Morphology diagnostics should be reported separately, and generated output should not be suppressed.

### 01-morphology-unknown-feature.opn

Expected future diagnostic:

```text
unknown morphology feature degree at lexicon entry bijten
```

Expected future morphology summary:

```text
morphology validation: 0 ok, 1 fail
```

### 02-morphology-missing-value.opn

Expected future diagnostic:

```text
missing morphology feature value for tense at lexicon entry bijten
```

Expected future morphology summary:

```text
morphology validation: 0 ok, 1 fail
```

### 03-morphology-duplicate-feature.opn

Expected future diagnostic:

```text
duplicate morphology feature number at lexicon entry hond
```

Expected future morphology summary:

```text
morphology validation: 0 ok, 1 fail
```

### 04-morphology-incompatible-pos.opn

Expected future diagnostic:

```text
morphology feature tense incompatible with pos:N at lexicon entry hond
```

Expected future morphology summary:

```text
morphology validation: 0 ok, 1 fail
```

### 05-morphology-unknown-value.opn

Expected future diagnostic:

```text
unknown morphology feature value dual for number at lexicon entry hond
```

Expected future morphology summary:

```text
morphology validation: 0 ok, 1 fail
```

## Future pass condition

Exact pass/fail counts should be fixed only when the Java behavior, example files and checker cases are added.

Proposed future result:

```text
Mapping V4.16 morphology metadata validator regression checker: pass
```

## Current V4.15 pass condition

Because V4.15 is documentation-only, the actual checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/examples/opn/mapping-v4-morphology-v416-expected-output-manifest.md`
# Mapping V4.16 expected-output manifest — minimal morphology metadata validator

## Valid examples

Directory:

```text
examples/opn/mapping-v4-morphology/
```

Expected:

```text
01-morphology-bijten-present-3sg.opn
expected mapping: Mapping v4: 3 lexical items, 1 verb domains, 2 placement rules
expected validation: validation: 2 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 1 ok, 0 fail

02-morphology-nominal-number-gender.opn
expected mapping: Mapping v4: 3 lexical items, 1 verb domains, 2 placement rules
expected validation: validation: 2 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 1 ok, 0 fail

03-morphology-det-number-gender.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt de hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 1 ok, 0 fail
```

## Invalid examples

Directory:

```text
examples/opn/mapping-v4-morphology-invalid/
```

Expected:

```text
01-morphology-unknown-feature.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 0 ok, 1 fail (file: 01-morphology-unknown-feature.opn; unknown morphology feature degree at lexicon entry bijten)

02-morphology-missing-value.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 0 ok, 1 fail (file: 02-morphology-missing-value.opn; missing morphology feature value for tense at lexicon entry bijten)

03-morphology-duplicate-feature.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 0 ok, 1 fail (file: 03-morphology-duplicate-feature.opn; duplicate morphology feature number at lexicon entry hond)

04-morphology-incompatible-pos.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 0 ok, 1 fail (file: 04-morphology-incompatible-pos.opn; morphology feature tense incompatible with pos:N at lexicon entry hond)

05-morphology-unknown-value.opn
expected validation starts with: validation: 2 ok, 0 fail
expected generated: generated: best: vrouw bijt hond
expected lexicon: Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 0 ok, 1 fail (file: 05-morphology-unknown-value.opn; unknown morphology feature value dual for number at lexicon entry hond)
```

## Pass condition

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
```

## Scope boundary

Morphology diagnostics are informational. They do not suppress generated output and do not alter explicit `form:<surface>` values.
---

## FILE: `md/examples/opn/mapping-v4-wh-expected-output-manifest.md`
# Mapping V4.2 WH expected-output manifest

Status: documentation-only expected-output manifest.

This manifest defines the target behavior for a later V4.3 implementation. V4.2 does not implement or run these checks yet.

## Directory proposal

Future valid examples:

```text
examples/opn/mapping-v4-wh/
```

Future invalid examples:

```text
examples/opn/mapping-v4-wh-invalid/
```

## Valid examples

### 01-subject-wh-vchain.opn

Purpose:

```text
subject-WH
```

Lexical interpretation:

```text
x1|wie|role:WH|wh_target:Agens
x2|heeft|role:V-AUX
x3|de hond|role:Patiens
x4|gebeten|role:V-PART
```

Expected mapping summary:

```text
Mapping v4: 4 lexical items, 1 verb domains, WH clause mode
```

Expected validation:

```text
validation: 4 ok, 0 fail (WH placement rules satisfied)
```

Expected generated:

```text
generated: best: wie heeft de hond gebeten
```

### 02-object-wh-vchain.opn

Purpose:

```text
object-WH
```

Lexical interpretation:

```text
x1|wat|role:WH|wh_target:Patiens
x2|vrouw|role:Agens
x3|heeft|role:V-AUX
x4|gebreid|role:V-PART
```

Expected mapping summary:

```text
Mapping v4: 4 lexical items, 1 verb domains, WH clause mode
```

Expected validation:

```text
validation: 4 ok, 0 fail (WH placement rules satisfied)
```

Expected generated:

```text
generated: best: wat heeft vrouw gebreid
```

### 03-subject-wh-with-neg.opn

Purpose:

```text
WH combined with existing NEG placement
```

Lexical interpretation:

```text
x1|wie|role:WH|wh_target:Agens
x2|heeft|role:V-AUX
x3|niet|role:NEG
x4|de hond|role:Patiens
x5|gebeten|role:V-PART
```

Expected mapping summary:

```text
Mapping v4: 5 lexical items, 1 verb domains, WH clause mode
```

Expected validation:

```text
validation: 5 ok, 0 fail (WH and NEG placement rules satisfied)
```

Expected generated:

```text
generated: best: wie heeft niet de hond gebeten
```

## Invalid examples

### 01-wh-missing-target.opn

Expected validation starts with:

```text
validation: 2 ok, 1 fail
```

Expected details contain:

```text
file: 01-wh-missing-target.opn
missing wh_target for role WH at x1
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 02-wh-unknown-target.opn

Expected validation starts with:

```text
validation: 2 ok, 1 fail
```

Expected details contain:

```text
file: 02-wh-unknown-target.opn
unknown wh_target LOC for role WH at x1
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 03-duplicate-wh.opn

Expected validation starts with:

```text
validation: 3 ok, 1 fail
```

Expected details contain:

```text
file: 03-duplicate-wh.opn
duplicate WH lexical item
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 04-wh-missing-vaux.opn

Expected validation starts with:

```text
validation: 2 ok, 1 fail
```

Expected details contain:

```text
file: 04-wh-missing-vaux.opn
missing V-AUX for WH clause mode
```

Expected generated:

```text
generated: none (invalid mapping)
```

## Pass condition for future V4.3

When implemented in V4.3, the checker should include the unchanged V3 and V4.1 baselines plus these WH cases.

Proposed future result:

```text
Mapping V4.3 WH regression checker: pass
```

Exact pass/fail counts should be fixed only when the example files and checker cases are added.
---

## FILE: `md/examples/opn/mapping-v4-wh-v43-expected-output-manifest.md`
# Mapping V4.3 expected-output manifest — minimal WH

## Valid examples

Directory:

```text
examples/opn/mapping-v4-wh/
```

Expected:

```text
01-wie-heeft-de-hond-gebeten.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: wie heeft de hond gebeten

02-wat-heeft-vrouw-gebreid.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: wat heeft vrouw gebreid

03-wie-bijt-hond.opn
expected mapping: Mapping v4: 3 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: wie bijt hond
```

## Invalid examples

Directory:

```text
examples/opn/mapping-v4-wh-invalid/
```

Expected:

```text
01-wh-missing-vaux.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 01-wh-missing-vaux.opn
expected details contain: missing role for rule V-AUX after WH
expected generated: generated: none (invalid mapping)

02-wh-ordering-cycle.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 02-wh-ordering-cycle.opn
expected details contain: ordering cycle in best placement rules
expected generated: generated: none (invalid mapping)
```

## Pass condition

```text
Mapping V4.3 regression checker: 24 pass, 0 fail
```

The checker total consists of:

- V3 core valid + invalid: 13 checks
- V4.1 NEG/TIME/PLACE valid + invalid: 6 checks
- V4.3 WH valid + invalid: 5 checks
---

## FILE: `md/meta-inf/2026-04-26-opn-save-extension.md`
# 2026-04-26 — Save OPN extension enforcement

Wijziging:
- File → Save OPN forceert nu altijd de extensie `.opn`.
- Zonder extensie wordt `.opn` toegevoegd.
- Met een andere extensie, bijvoorbeeld `test.txt`, wordt `test.txt.opn` opgeslagen.
- De Save OPN chooser gebruikt een OPN-filter en verbergt andere extensies.

Compile:
- `javac @sources.txt` OK
- alleen bestaande deprecation warning in `GraphFileActions.java`.
---

## FILE: `md/meta-inf/2026-04-27-mapping-v31-ranking.md`
# 2026-04-27 — Mapping V3.1 ranking en alternatieven

Scope:
- Alleen mapping/generator/validator-laag.
- Geen UI-wijzigingen.
- Geen graph rendering-wijzigingen.

Wijzigingen:
- V3-alternatieven worden nu als expliciete kandidaten opgebouwd en daarna stabiel gerangschikt.
- Alternatieven worden gededupliceerd op gegenereerde uiting.
- Info-output blijft begrensd tot maximaal 3 alternatieven.
- Niet-oplosbare ordering-constraints leveren geen fallback-volgorde meer op; zulke kandidaten vallen weg.
- Enkelvoudige rank-varianten en beperkte paren van rank-varianten worden geprobeerd, zodat meerdere argumenten beter zichtbaar worden zonder explosie.
- `before_clause` en `after_clause` worden als placement-relaties ondersteund naast de bestaande `realizes_before` / `realizes_after` regels.

Aangeraakt:
- `userInterface/GraphFileActions.java`

Compile:
- `javac @sources.txt` OK
- 1 bestaande deprecation warning: `new Integer(x)` in `GraphFileActions.java`.

Controle:
- `examples/opn/mapping-v3-neg/voorbeeldzin-met-bijwoorden.lexical-axis-view.mapping-v3-neg-after-aux.opn`
  - best: `gisteren vrouw heeft niet trui gebreid daar`
  - alternatieven: maximaal 3, uniek en stabiel gerangschikt.
---

## FILE: `md/meta-inf/2026-04-27-mapping-v310-core-stable-checkpoint.md`
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
---

## FILE: `md/meta-inf/2026-04-27-mapping-v32-arguments-placement.md`
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
---

## FILE: `md/meta-inf/2026-04-27-mapping-v33-core-regression.md`
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
---

## FILE: `md/meta-inf/2026-04-27-mapping-v34-invalid-diagnostics.md`
# Mapping v3.4 — Core invalid diagnostics

## Doel

Bij openen van een `.opn` met foute `MAPPING_V3` moet de fout zichtbaar zijn in het Info-venster.
De foutmelding noemt de geladen bestandsnaam, zodat de fout snel te koppelen is aan de testfile.

## Toegevoegd

```text
examples/opn/mapping-v3-core-invalid/
  01-missing-role.opn
  02-unknown-role.opn
  03-missing-theme-target.opn
  04-missing-vpart-target.opn
  05-recipient-theme-cycle.opn
  06-verb-domain-missing-id.opn
  07-duplicate-lexical-id.opn
  08-missing-verb-anchor.opn
  README.txt
```

## Verwacht gedrag

Bij openen van een invalid `.opn`:

```text
validation: ... fail (file: <loadfile>; <diagnostic>)
generated: none (invalid mapping)
```

## Buiten scope

- UI-layout wijzigen
- graph rendering
- WH/vraagzinnen
- NEG
- DET-splitsing
- FRAME.graph
---

## FILE: `md/meta-inf/2026-04-27-mapping-v34-invalid-dialog.md`
# Mapping v3.4 invalid dialog

## Doel

Bij het openen van een `.opn` met ongeldige `MAPPING_V3` moet de gebruiker de fout direct zien.

## Gedrag

- De graph/editor wordt geopend.
- Het Info-scherm wordt geopend/bijgewerkt.
- Er verschijnt direct een modal melding met OK-knop.
- De melding noemt de loadfile.
- De melding verwijst naar het Info-scherm voor de volledige diagnose.

## Voorbeeld

```text
OPN Mapping-fout in loadfile:
05-recipient-theme-cycle.opn

Zie het Info-scherm voor de volledige diagnose.

validation: ... fail (...)
```

## Scope

Wel:
- invalid mapping zichtbaar maken bij open `.opn`
- Info-output behouden als volledige diagnose

Niet:
- UI-layout wijzigen
- graph rendering wijzigen
- mapping/generatorregels inhoudelijk uitbreiden
---

## FILE: `md/meta-inf/2026-04-27-mapping-v34-invalid-info-only.md`
# Mapping v3.4 invalid info-only

Wijziging:
- Bij ongeldige MAPPING_V3 wordt geen modal foutmelding meer getoond.
- Het Info-scherm wordt direct geopend/bijgewerkt.
- De fail-melding staat in het Info-scherm vooraan.
- De loaded file blijft in de validation details staan.

Scope:
- Alleen invalid-mapping diagnostics.
- Geen generator-, UI-rendering- of graph-mutatie-wijziging.
---

## FILE: `md/meta-inf/2026-04-27-mapping-v35-expected-output-manifest.md`
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
---

## FILE: `md/meta-inf/2026-04-27-mapping-v36-checker.md`
# Mapping V3.6 — expected-output checker

## Status

Added command-line regression checker for the Mapping V3 core manifests.

## Scope

Checks:
- valid core examples under `examples/opn/mapping-v3-core/`
- invalid core examples under `examples/opn/mapping-v3-core-invalid/`
- expected generated best output
- expected validation fail behavior
- filename presence in invalid diagnostics
- `generated: none (invalid mapping)` for invalid mappings

Out of scope:
- WH / vraagzinnen
- NEG
- TIME / PLACE / bijwoorden
- DET splitting
- FRAME.graph
- lexicon
- UI rendering / rotate / layout

## Run

From the OpenGraphEd directory:

```text
java tools.MappingV3RegressionChecker .
```

Windows helper:

```text
run-mapping-v3-checker.bat
```

## Expected result

```text
summary: 13 pass, 0 fail
```
---

## FILE: `md/meta-inf/2026-04-27-mapping-v37-future-phases.md`
# Mapping V3.7 — future phases placeholder

## Doel

Geen implementatie. Alleen de latere fasering explicieter vastleggen in `meta-inf/frame_md.tmp`.

## Toegevoegd aan de placeholder

- uitingstype / clause mode
  - stellend / declarative
  - vragend / interrogative
  - gebiedend / imperative
  - uitroepend / exclamative
- volledigheid / ellipsis
  - volledig / full
  - elliptisch / elliptic
- relatie met latere WH-vraagzinnen
- relatie met DET/lidwoorden
- relatie met negatie en bijwoorden
- FRAME.graph blijft on hold

## Scope

Niet gewijzigd:

- Java-code
- generator
- validator
- checker
- placement rules
- UI/rendering

## Fasering

De huidige lijn blijft:

1. Mapping V3 core stabiel houden.
2. Expected-output checker gebruiken voor regressie.
3. Daarna pas WH/DET/FRAME openen.
4. Uitingstype en volledigheid dienen als latere lexicale-plaatsingslaag.
---

## FILE: `md/meta-inf/2026-04-27-mapping-v38-source-md-bundle.md`
# Mapping V3.8 - Source MD bundle

## Status

Documentatie-update. Geen Java-codewijziging.

## Toegevoegd

Nieuwe map:

```text
sources/mapping-v3/
```

Met de actuele Mapping V3-bronbestanden:

```text
adverbs-v2.md
current-state.md
generator-validator.md
mapping-v3-neg.md
mapping-v3-spec.md
opn-format.md
placement-rules-v3.md
projectie-master-spec.md
recipient-theme-v3.md
README.md
```

## Doel

- De project-zip bevat nu ook de actuele `.md`-bronset.
- De bestanden blijven documentatie/spec-context.
- De checker en generatorlogica zijn niet gewijzigd.

## Scope

Niet gewijzigd:

```text
Java-code
generator
validator
checker
UI/rendering
```

Latere fasen blijven on hold volgens `meta-inf/frame_md.tmp`:

```text
WH/vraagzin
NEG
DET/lidwoorden
TIME/PLACE/bijwoorden
FRAME.graph
lexicon
uitingstype / volledigheid
```
---

## FILE: `md/meta-inf/2026-04-27-mapping-v39-md-folder.md`
# Mapping v3.9 — MD folder cleanup

## Doel

Alle markdown-documentatie staat voortaan gegroepeerd onder één map:

```text
md/
```

## Verplaatst

- `README.md` → `md/README.md`
- `CHANGELOG.md` → `md/CHANGELOG.md`
- `meta-inf/*.md` → `md/meta-inf/*.md`
- `meta-inf/frame_md.tmp` → `md/meta-inf/frame_md.tmp`
- `examples/**/*.md` → `md/examples/**/*.md`
- `sources/mapping-v3/*.md` → `md/sources/mapping-v3/*.md`

## Niet gewijzigd

- Java-code
- `.class`-bestanden
- `.opn`-voorbeelden
- checker
- generator
- validator
- mappingregels

## Reden

De zip blijft overzichtelijker. Projectdocumentatie is daardoor makkelijker apart te uploaden of te vergelijken met Sources.
---

## FILE: `md/meta-inf/2026-04-27-mapping-v40-md-folder-check.md`
# 2026-04-27 — Mapping V40: MD Folder Check

## Doel

Afdwingen dat alle nieuwe Markdown-documentatie onder `md/` terechtkomt.

## Toegevoegd

- `run-md-folder-check.bat`
- `tools/check-md-folder.sh`
- `tools/CheckMdFolder.java` als Java-bron voor een latere cross-platform checker

## Regel

Alle `.md`-bestanden moeten onder `md/` staan.

Toegestaan:

```text
md/README.md
md/CHANGELOG.md
md/INDEX.md
md/meta-inf/...
md/sources/...
md/examples/...
```

Niet toegestaan:

```text
CHANGELOG.md
meta-inf/*.md
examples/**/*.md
sources/**/*.md
```

## Controle

Windows:

```text
run-md-folder-check.bat
```

POSIX/container:

```text
tools/check-md-folder.sh .
```

Verwacht:

```text
PASS: all .md files are under md/
```

## Scope

Geen wijzigingen aan Mapping V3, generator, validator, regression checker of UI.
MD-organisatie wordt nu technisch gecontroleerd.
---

## FILE: `md/meta-inf/2026-04-29-mapping-v40-start.md`
# Mapping V4.0 start

Status: **MAPPING_V4_SCOPE_FREEZE**

Date: 2026-04-29

## Base

This phase starts from:

```text
Projectie-26-04-27-mapping-v310-core-stable-checkpoint.zip
```

Base status:

```text
MAPPING_V3_CORE_STABLE
```

## Goal

Create the V4 documentation and phase baseline without changing runtime behavior.

## Added

- `md/sources/mapping-v4/README.md`
- `md/sources/mapping-v4/current-state.md`
- `md/sources/mapping-v4/v4-phasing.md`
- `md/sources/mapping-v4/scope-freeze.md`
- `md/sources-md-zip/README.md`
- embedded md-only sources zip under `md/sources-md-zip/`

## Updated

- `md/README.md`
- `md/INDEX.md`
- `md/CHANGELOG.md`

## Packaging rule

Every project zip must include an md-only sources zip containing all Markdown files included in the package, preserving their relative paths.

The md-only zip is meant for manual upload to Project Sources and for source-state verification.

## Scope

Documentation/package-only phase.

No Java source, class file, jar, generator, validator, parser, UI, rendering, or example semantics were changed.

## Expected checks

```text
Mapping V3 regression: 13 pass, 0 fail
MD folder check: PASS
```

## Next proposed phase

```text
V4.1 — NEG / TIME / PLACE
```

V4.1 should be a behavior phase only after V4.0 is accepted.

## Actual checks

Run after V4.0 packaging:

```text
Mapping V3 regression checker: summary: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-04-29-mapping-v42-wh-scope.md`
# 2026-04-29 — Mapping V4.2 WH / clause mode scope

## Status

```text
MAPPING_V4_2_WH_SCOPE
```

## Summary

V4.2 is a documentation and manifest checkpoint.

It prepares WH questions by defining:

- WH as a lexical item.
- WH target roles.
- clause mode as mapping metadata.
- expected valid and invalid outputs for a later V4.3 behavior phase.

## Base

This phase starts from:

```text
MAPPING_V4_1_NEG_TIME_PLACE
```

Base expectations remain:

```text
Mapping V3 regression: 13 pass, 0 fail
Mapping V4.1 regression: 19 pass, 0 fail
MD folder check: PASS
```

## Added

- `md/sources/mapping-v4/wh-v42.md`
- `md/meta-inf/2026-04-29-mapping-v42-wh-scope.md`
- `md/examples/opn/mapping-v4-wh-expected-output-manifest.md`
- `md/sources-md-zip/Mapping_V4-26-04-29--v42-wh-scope-md-only.zip`
- `md/sources-md-zip/Mapping_V4-26-04-29--v42-wh-scope-addendum-bundel.md`

## Decisions

### No transformations

WH is not implemented as fronting or tree movement.

The utterance is generated from mapping data and placement rules on the lexical axis.

### Clause mode

V4.2 reserves:

```text
CLAUSE_MODE: interrogative_wh
```

This is documented for the next behavior phase. It is not a V4.2 runtime change.

### DET remains outside scope

`de hond` and comparable noun phrases may be one lexical item in V4.2/V4.3.

A later DET phase may split these into separate DET and N items.

## Preserved

- V3 core roles remain stable.
- V4.1 NEG / TIME / PLACE behavior remains the current behavior baseline.
- No graph mutation is introduced.
- No UI/rendering behavior is changed.
- Markdown remains under `md/`.

## Scope boundary

V4.2 does not implement:

- WH generation.
- WH validation.
- WH parser logic.
- DET splitting.
- FRAME.graph.
- lexicon.
- UI/rendering/view-options.
- Java source changes.
- class or jar rebuilds.

## Expected checks

Because V4.2 is documentation-only:

```text
Mapping V3 regression: unchanged
Mapping V4.1 regression: unchanged
MD folder check: PASS
```

## Next phase

```text
V4.3 — minimal WH generator and validator
```

Recommended first implementation scope:

- subject-WH: `wie heeft de hond gebeten`
- object-WH: `wat heeft vrouw gebreid`
- invalid diagnostics for missing WH target, unknown WH target and duplicate WH.
---

## FILE: `md/meta-inf/2026-04-29-mapping-v43-wh-minimal.md`
# 2026-04-29 — Mapping V4.3 minimal WH

## Status

```text
MAPPING_V4_3_WH_MINIMAL
```

## Summary

V4.3 adds minimal WH behavior after the V4.2 scope freeze.

The implementation keeps the previous architecture intact: mapping logic does not mutate the graph and generated output is computed from placement constraints on the lexical axis.

## Added

- `WH` as a known Mapping role.
- `MAPPING_V4:` / `END_MAPPING_V4:` handling in the OPN mapping path.
- Valid V4.3 WH examples under `examples/opn/mapping-v4-wh/`.
- Invalid V4.3 WH examples under `examples/opn/mapping-v4-wh-invalid/`.
- `tools/MappingV4RegressionChecker.java`.
- `run-mapping-v4-checker.bat`.
- V4.1 NEG/TIME/PLACE examples and expected manifests, because this code package starts from the V4.0 scope-freeze zip.
- V4.3 documentation under `md/sources/mapping-v4/`.

## Preserved

- Mapping V3 core examples and checker source remain present.
- V3 rules are not intentionally changed.
- No UI/rendering/view-option changes.
- No graph mutation behavior.

## Scope boundary

Not included:

- DET splitting
- FRAME.graph
- lexicon / automatic role inference
- UI/rendering/view-options
- transformations / vooropplaatsing

## Expected checks

```text
javac full build: intended
Mapping V3 regression checker: expected 13 pass, 0 fail
Mapping V4.3 regression checker: expected 24 pass, 0 fail
MD folder check: expected PASS
```

## Note

This package was prepared from `Mapping_V4-26-04-29--v40-scope-freeze.zip`. Therefore it includes the V4.1 behavior/example layer plus V4.3 WH behavior in one forward package.
---

## FILE: `md/meta-inf/2026-04-30-mapping-v44-det-scope.md`
# 2026-04-30 — Mapping V4.4 DET scope

Status:

```text
MAPPING_V4_4_DET_SCOPE
```

## Type

Documentation and expected-output manifest only.

## Base

```text
Mapping_V4-26-04-29--v43-wh-minimal.zip
```

## Added

```text
md/sources/mapping-v4/det-v44.md
md/meta-inf/2026-04-30-mapping-v44-det-scope.md
md/examples/opn/mapping-v4-det-v44-expected-output-manifest.md
```

## Updated

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Scope

Defines future DET handling as a separate lexical-axis mapping slice.

In scope for this documentation phase:

- DET role design.
- `det-target` relation.
- proposed valid examples.
- proposed invalid examples.
- boundary against FRAME.graph, lexicon and UI/rendering.

Not changed:

```text
Java source
.class files
jar files
parser
generator
validator
checker
UI
rendering
runtime example semantics
```

## Expected status after applying patch

```text
MAPPING_V4_4_DET_SCOPE
Runtime behavior still: MAPPING_V4_3_WH_MINIMAL
Mapping V4.3 regression checker: 24 pass, 0 fail
MD folder check: PASS
```

## Next recommended patch

```text
Mapping V4.5 — minimal DET generator and validator
```
---

## FILE: `md/meta-inf/2026-04-30-mapping-v45-det-minimal.md`
# 2026-04-30 — Mapping V4.5 minimal DET generator / validator

## Status

```text
MAPPING_V4_5_DET_MINIMAL
```

## Summary

V4.5 implements minimal DET behavior after the V4.4 documentation-only DET scope phase.

## Base

```text
Mapping_V4-26-04-30--v44-det-scope.zip
```

## Added

- `DET` as a known Mapping role.
- `det-target` and `det_target` parsing on lexical items.
- DET target validation.
- Valid DET examples under `examples/opn/mapping-v4-det/`.
- Invalid DET examples under `examples/opn/mapping-v4-det-invalid/`.
- Expected-output and expected-fail files for DET examples.
- `md/sources/mapping-v4/det-v45.md`.
- `md/examples/opn/mapping-v4-det-v45-expected-output-manifest.md`.
- V4 regression checker expansion to V4.5 DET.

## Changed

- `userInterface/GraphFileActions.java`
- `tools/MappingV4RegressionChecker.java`
- compiled class files for the changed Java sources
- V4 current-state, phasing, README, INDEX and CHANGELOG documentation

## Preserved

- No graph mutation.
- No tree transformation / vooropplaatsing.
- No FRAME.graph behavior.
- No lexicon behavior.
- No UI/rendering behavior changes.
- Existing V3, V4.1 and V4.3 checks remain passing.

## Expected checks

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-04-30-mapping-v451-info-label.md`
# 2026-04-30 — Mapping V4.5.1 Info label clarification

## Status

```text
MAPPING_V4_5_1_INFO_LABEL
```

## Summary

V4.5.1 is a small UI text clarification patch on top of V4.5.

The Info window now shows the top generated output as:

```text
generated best: <utterance>
```

instead of:

```text
generated: best: <utterance>
```

## Changed

- `userInterface/GraphEditorInfoSupport.java`
- `userInterface/GraphEditorInfoSupport.class`
- `out/userInterface/GraphEditorInfoSupport.class`
- `OpenGraphEd.jar`
- `dist/OpenGraphEd.jar`
- documentation current-state, phasing, README, INDEX and CHANGELOG

## Preserved

- No generator logic changes.
- No validator logic changes.
- No parser changes.
- No checker changes.
- No mapping-rule changes.
- No graph mutation.
- Invalid output remains:

```text
generated: none (invalid mapping)
```

## Expected checks

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Meaning

```text
generated best = the highest-ranked output candidate derived from the placement rules
```
---

## FILE: `md/meta-inf/2026-05-01-mapping-v452-stable-handoff.md`
# 2026-05-01 — Mapping V4.5.2 stable handoff

## Status

```text
MAPPING_V4_5_2_STABLE_HANDOFF
```

## Summary

V4.5.2 is a documentation-only stable handoff checkpoint on top of V4.5.1.

It confirms the current V4 line before opening the next major scope, currently proposed as FRAME.graph.

## Base

```text
Mapping_V4-26-04-30--v451-info-label.zip
```

## Added

```text
md/sources/mapping-v4/stable-handoff-v452.md
md/meta-inf/2026-05-01-mapping-v452-stable-handoff.md
```

## Updated

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
md/sources-md-zip/Mapping_V4-26-05-01--v452-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v452-stable-handoff-md-only.zip
```

## Scope

Documentation/package checkpoint only.

Not changed:

```text
Java source
.class files
jar files
parser
generator
validator
checker
placement rules
UI behavior
graph rendering
graph mutation behavior
example semantics
```

## Actual checks

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended phase

```text
Mapping V4.6 — FRAME.graph scope
```
---

## FILE: `md/meta-inf/2026-05-01-mapping-v46-frame-graph-scope.md`
# 2026-05-01 — Mapping V4.6 FRAME.graph scope

## Status

```text
MAPPING_V4_6_FRAME_GRAPH_SCOPE
```

## Summary

V4.6 opens `FRAME.graph` as a documentation-only scope phase on top of V4.5.2.

It defines `FRAME.graph` as a future semantic/frame context layer. It does not implement parsing, validation, generation, rendering or graph mutation.

## Base

```text
Mapping_V4-26-05-01--v452-stable-handoff.zip
```

## Added

```text
md/sources/mapping-v4/frame-graph-v46.md
md/meta-inf/2026-05-01-mapping-v46-frame-graph-scope.md
md/examples/opn/mapping-v4-frame-v46-expected-output-manifest.md
```

## Updated

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
md/sources-md-zip/Mapping_V4-26-05-01--v46-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v46-frame-graph-scope-md-only.zip
```

## Scope

Documentation/package checkpoint only.

Not changed:

```text
Java source
.class files
jar files
parser
generator
validator
checker
placement rules
UI behavior
graph rendering
graph mutation behavior
example semantics
```

## Decisions

- `FRAME.graph` is a future semantic/frame context layer.
- `FRAME.graph` is not the generator.
- Existing `MAPPING_V4` lexical-axis mapping remains explicit.
- `STRUCTURE` remains a drawn view.
- No frame metadata is drawn as graph content.
- No lexical role inference is introduced.

## Actual checks

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended phase

```text
Mapping V4.7 — FRAME.graph metadata read / count
```

Recommended first behavior slice: parse a `FRAME_GRAPH` section as metadata only and report it in Info, without validation, generation, rendering or graph mutation.
---

## FILE: `md/meta-inf/2026-05-01-mapping-v47-frame-graph-metadata.md`
# 2026-05-01 — Mapping V4.7 FRAME.graph metadata read / count

## Status

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
```

## Summary

V4.7 implements the first runtime-visible FRAME.graph behavior after the V4.6 scope phase.

It reads `FRAME_GRAPH` metadata, counts frames and slots, and reports that metadata in the Info window. It does not validate frame-slot completeness, infer roles, alter generated output, render frames, or mutate the graph.

## Base

```text
Mapping_V4-26-05-01--v46-frame-graph-scope.zip
```

## Added

```text
md/sources/mapping-v4/frame-graph-v47.md
md/meta-inf/2026-05-01-mapping-v47-frame-graph-metadata.md
md/examples/opn/mapping-v4-frame-v47-expected-output-manifest.md
examples/opn/mapping-v4-frame/
```

## Changed

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
userInterface/GraphEditorInfoSupport.java
tools/MappingV4RegressionChecker.java
```

## Behavior

Recognized metadata section:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:
```

Info summary:

```text
OPN Frame graph: 1 frames, 2 slots, metadata only
```

## Preserved

```text
generator behavior
validator behavior for Mapping V4 placement rules
generated best output
graph rendering
graph mutation behavior
tree transformation boundary
explicit lexical-axis mapping
```

## Actual checks

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended phase

```text
Mapping V4.8 — FRAME.graph slot validation scope
```
---

## FILE: `md/meta-inf/2026-05-01-mapping-v48-frame-graph-slot-validation-scope.md`
# 2026-05-01 — Mapping V4.8 FRAME.graph slot validation scope

## Status

```text
MAPPING_V4_8_FRAME_GRAPH_SLOT_VALIDATION_SCOPE
```

## Summary

V4.8 is a documentation-only scope phase on top of V4.7.

It defines the intended boundary for a later minimal FRAME.graph slot validator. It does not implement that validator.

## Base

```text
Mapping_V4-26-05-01--v47-frame-graph-metadata.zip
```

Base behavior remains:

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
FRAME_GRAPH metadata read/count
Info summary for frame and slot counts
```

## Added

```text
md/sources/mapping-v4/frame-graph-v48.md
md/meta-inf/2026-05-01-mapping-v48-frame-graph-slot-validation-scope.md
md/examples/opn/mapping-v4-frame-v48-expected-output-manifest.md
md/PATCH-MANIFEST-v48-frame-graph-slot-validation-scope.md
```

## Updated

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
md/sources-md-zip/Mapping_V4-26-05-01--v48-all-md-sources-bundel.md
md/sources-md-zip/Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope-md-only.zip
```

## Scope

Documentation/package checkpoint only.

Not changed:

```text
Java source
.class files
jar files
parser
generator
validator
checker
placement rules
UI behavior
graph rendering
graph mutation behavior
example semantics
```

## Decisions

- FRAME.graph slot validation is not implemented in V4.8.
- V4.8 reserves the first validator slice for required semantic slots.
- First-slice slot roles are limited to `Agens`, `Patiens`, `RECIPIENT` and `THEME`.
- `FRAME_GRAPH` remains metadata only at runtime in this phase.
- Generated output remains derived from `MAPPING_V4` placement rules.

## Actual checks

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next recommended phase

```text
Mapping V4.9 — FRAME.graph minimal slot validator
```
---

## FILE: `md/meta-inf/2026-05-02-mapping-v410-lexicon-metadata.md`
# 2026-05-02 — Mapping V4.10 Lexicon metadata read / count

## Status

```text
MAPPING_V4_10_LEXICON_METADATA
```

## Summary

V4.10 implements the first minimal runtime-visible lexicon layer.

It reads a `LEXICON:` / `END_LEXICON:` metadata section, counts pipe-delimited lexical entries and reports the count in the Info window.

## Base

```text
Mapping_V4-26-05-02--v49-frame-graph-minimal-slot-validator.zip
```

## Added

```text
md/sources/mapping-v4/lexicon-v410.md
md/meta-inf/2026-05-02-mapping-v410-lexicon-metadata.md
md/examples/opn/mapping-v4-lexicon-v410-expected-output-manifest.md
md/PATCH-MANIFEST-v410-lexicon-metadata.md
examples/opn/mapping-v4-lexicon/
```

## Changed

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
userInterface/GraphEditorInfoSupport.java
tools/MappingV4RegressionChecker.java
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Behavior

Recognized metadata section:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
END_LEXICON:
```

Info summary:

```text
OPN Lexicon: <n> entries, metadata only
```

## Preserved

```text
generated utterance rules
Mapping V4 placement validation semantics
FRAME.graph slot validation semantics
graph rendering
graph mutation
tree transformation boundary
automatic role inference boundary
```

## Actual checks

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-02-mapping-v4101-straight-edge-midpoint-cleanup.md`
# 2026-05-02 — Mapping V4.10.1 straight-edge midpoint cleanup

## Status

```text
MAPPING_V4_10_1_STRAIGHT_EDGE_MIDPOINT_CLEANUP
```

## Summary

V4.10.1 is a small rendering cleanup on top of V4.10.

Ordinary straight undirected edges no longer draw a visible center/midpoint marker. This removes the unwanted middle dot on simple structure edges such as `S — V`.

## Base

```text
Mapping_V4-26-05-02--v410-lexicon-metadata.zip
```

## Changed

```text
graphStructure/Edge.java
graphStructure/Edge.class
out/graphStructure/Edge.class
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Preserved

```text
curved and orthogonal undirected edge markers
directed edge arrows
Mapping V4 parser/generator/validator
FRAME.graph metadata and validation
Lexicon metadata read/count
OPN examples and semantics
graph mutation behavior
```

## Actual checks

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-02-mapping-v411-lexicon-validation-scope.md`
# 2026-05-02 — Mapping V4.11 Lexicon validation / coupling scope

## Status

```text
MAPPING_V4_11_LEXICON_VALIDATION_SCOPE
```

## Summary

V4.11 is a documentation-only scope phase on top of V4.10.1.

It defines the first minimal lexicon validation/coupling boundary for a later behavior phase. Runtime behavior remains V4.10.1.

## Base

```text
Mapping_V4-26-05-02--v4101-straight-edge-midpoint-cleanup.zip
```

## Added

```text
md/sources/mapping-v4/lexicon-validation-v411.md
md/meta-inf/2026-05-02-mapping-v411-lexicon-validation-scope.md
md/examples/opn/mapping-v4-lexicon-v411-expected-output-manifest.md
md/PATCH-MANIFEST-v411-lexicon-validation-scope.md
```

## Runtime change

```text
none
```

## Preserved

```text
Mapping V4 parser/generator/validator
FRAME.graph metadata and validation
Lexicon metadata read/count
straight-edge midpoint cleanup
graph rendering
graph mutation behavior
OPN example semantics
```

## Actual checks

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-02-mapping-v412-lexicon-validator.md`
# 2026-05-02 — Mapping V4.12 Lexicon validator

Status:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-02--v411-lexicon-validation-scope-FIXED-slim.zip
```

## Added

- Runtime Lexicon validation diagnostics.
- Invalid Lexicon examples under `examples/opn/mapping-v4-lexicon-invalid/`.
- V4.12 source document.
- V4.12 expected-output manifest.
- V4.12 patch manifest.

## Changed

- `graphStructure/Graph.java`
- `userInterface/GraphFileActions.java`
- `tools/MappingV4RegressionChecker.java`
- `examples/opn/mapping-v4-lexicon/EXPECTED.txt`
- compiled class files and runtime jars

## Preserved

- Generated output remains based on explicit `MAPPING_V4` placement rules.
- Lexicon validation failures do not suppress generated output.
- No role inference.
- No automatic lexical insertion.
- No automatic frame selection.
- No morphology / inflection.
- No Lexicon rendering.
- No graph mutation.

## Actual checks

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-02-mapping-v413-lexicon-morphology-frame-selection-scope.md`
# 2026-05-02 — Mapping V4.13 Lexicon / morphology / frame-selection scope

Status:

```text
MAPPING_V4_13_LEXICON_MORPHOLOGY_FRAME_SELECTION_SCOPE
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v412-lexicon-validator.zip
```

Base runtime behavior:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Added

```text
md/sources/mapping-v4/lexicon-morphology-frame-selection-v413.md
md/meta-inf/2026-05-02-mapping-v413-lexicon-morphology-frame-selection-scope.md
md/examples/opn/mapping-v4-lexicon-v413-expected-output-manifest.md
md/PATCH-MANIFEST-v413-lexicon-morphology-frame-selection-scope.md
```

## Changed

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Runtime change

```text
none
```

## Summary

V4.13 defines the later work boundary for Lexicon, morphology metadata and frame-selection behavior.

It keeps V4.12 as the runtime behavior and records that future morphology and frame-selection behavior must be introduced only in later explicit behavior slices.

## Preserved

```text
Java source
class files
jar files
parser
generator
Mapping V4 validator
FRAME.graph metadata and slot validation
Lexicon validator behavior
OPN example semantics
graph rendering
graph mutation
```

## Actual checks

Because this phase is documentation-only, checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-02-mapping-v414-morphology-metadata-validation-scope.md`
# 2026-05-02 — Mapping V4.14 morphology metadata validation scope

Status:

```text
MAPPING_V4_14_MORPHOLOGY_METADATA_VALIDATION_SCOPE
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v413-lexicon-morphology-frame-selection-scope.zip
```

Base runtime behavior:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Added

```text
md/sources/mapping-v4/morphology-v414.md
md/meta-inf/2026-05-02-mapping-v414-morphology-metadata-validation-scope.md
md/examples/opn/mapping-v4-morphology-v414-expected-output-manifest.md
md/PATCH-MANIFEST-v414-morphology-metadata-validation-scope.md
```

## Changed

```text
md/CHANGELOG.md
md/INDEX.md
md/README.md
md/sources/mapping-v4/current-state.md
md/sources/mapping-v4/v4-phasing.md
```

## Runtime change

```text
none
```

## Summary

V4.14 scopes the first later morphology step as metadata validation only.

The reserved morphology metadata keys are:

```text
tense
number
person
gender
case
mood
aspect
finite
```

This phase does not implement the validator.

## Preserved

```text
Java source
class files
jar files
parser
generator
Mapping V4 validator
FRAME.graph metadata and slot validation
Lexicon validator behavior
OPN example semantics
graph rendering
graph mutation
```

## Check status

Because this phase is documentation-only, checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-02-mapping-v415-morphology-metadata-validator-target.md`
# Phase manifest — Mapping V4.15 morphology metadata validator target

Status:

```text
MAPPING_V4_15_MORPHOLOGY_METADATA_VALIDATOR_TARGET
```

Date:

```text
2026-05-02
```

## Type

Documentation-only implementation target.

## Base

```text
Mapping_V4-26-05-02--v412-lexicon-validator-slim.zip
```

This rebuild carries forward the V4.13 and V4.14 documentation-only phases and adds V4.15.

## Runtime behavior

Unchanged:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Added documents

```text
md/sources/mapping-v4/morphology-v415.md
md/meta-inf/2026-05-02-mapping-v415-morphology-metadata-validator-target.md
md/examples/opn/mapping-v4-morphology-v415-expected-output-manifest.md
md/PATCH-MANIFEST-v415-morphology-metadata-validator-target.md
```

## Scope

V4.15 fixes the target behavior for a later morphology metadata validator.

It defines:

```text
accepted morphology keys
minimal allowed value domain
target valid-output summaries
target invalid diagnostics
compatibility boundary between morphology features and pos
```

## Preserved

```text
no Java source changes
no class-file changes
no jar changes
no parser changes
no generator changes
no validator changes
no checker changes
no UI/rendering changes
no graph mutation
no OPN example semantics changes
```

## Expected unchanged checks

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-02-mapping-v416-morphology-metadata-validator.md`
# Phase manifest — Mapping V4.16 morphology metadata validator

Status:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

Date:

```text
2026-05-02
```

## Type

Small behavior slice.

## Base

```text
Mapping_V4-26-05-02--v415-morphology-metadata-validator-target-full-from-v412-slim.zip
```

## Runtime behavior

V4.16 adds diagnostic-only morphology metadata validation for explicit fields on `LEXICON` rows.

The Info summary may now include:

```text
Lexicon: <n> entries; lexicon validation: <ok> ok, <fail> fail; morphology validation: <ok> ok, <fail> fail
```

## Added examples

```text
examples/opn/mapping-v4-morphology/
examples/opn/mapping-v4-morphology-invalid/
```

## Added documents

```text
md/sources/mapping-v4/morphology-v416.md
md/meta-inf/2026-05-02-mapping-v416-morphology-metadata-validator.md
md/examples/opn/mapping-v4-morphology-v416-expected-output-manifest.md
md/PATCH-MANIFEST-v416-morphology-metadata-validator.md
```

## Changed Java

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
```

## Preserved

```text
generated utterance rules
Mapping V4 placement validation semantics
FRAME.graph metadata or validation
Lexicon basic validation behavior
OPN structure rendering
graph mutation behavior
automatic inflection
surface-form generation
role inference
automatic lexical insertion
automatic frame selection
```

## Actual checks

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-02-mapping-v49-frame-graph-minimal-slot-validator.md`
# 2026-05-02 — Mapping V4.9 FRAME.graph minimal slot validator

## Status

```text
MAPPING_V4_9_FRAME_GRAPH_MINIMAL_SLOT_VALIDATOR
```

## Summary

V4.9 implements the minimal `FRAME_GRAPH` slot validator defined by the V4.8 scope phase.

It validates required frame slots against explicit `MAPPING_V4` lexical roles and reports diagnostics in the Info window and regression checker.

## Base

```text
Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope.zip
```

## Added

```text
md/sources/mapping-v4/frame-graph-v49.md
md/meta-inf/2026-05-02-mapping-v49-frame-graph-minimal-slot-validator.md
md/examples/opn/mapping-v4-frame-v49-expected-output-manifest.md
md/PATCH-MANIFEST-v49-frame-graph-minimal-slot-validator.md
examples/opn/mapping-v4-frame-invalid/
```

## Changed

```text
graphStructure/Graph.java
userInterface/GraphFileActions.java
tools/MappingV4RegressionChecker.java
examples/opn/mapping-v4-frame/EXPECTED.txt
compiled class files for changed Java sources
OpenGraphEd.jar
dist/OpenGraphEd.jar
```

## Behavior

Info now reports:

```text
OPN Frame graph: <n> frames, <m> slots; frame validation: <ok> ok, <fail> fail (...)
```

Frame-validation failures do not suppress generated output. Generated output remains based on explicit `MAPPING_V4` placement rules.

## Actual checks

```text
Mapping V4.9 FRAME.graph minimal slot validator regression checker: 37 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-03-mapping-v417-frame-selection-scope.md`
# Phase manifest — Mapping V4.17 explicit frame-selection scope

Status:

```text
MAPPING_V4_17_FRAME_SELECTION_SCOPE
```

Date:

```text
2026-05-03
```

## Type

Documentation-only scope phase.

## Base

```text
Mapping_V4-26-05-02--v416-morphology-metadata-validator.zip
```

## Runtime behavior

Unchanged:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

## Added documents

```text
md/sources/mapping-v4/frame-selection-v417.md
md/meta-inf/2026-05-03-mapping-v417-frame-selection-scope.md
md/examples/opn/mapping-v4-frame-selection-v417-expected-output-manifest.md
md/PATCH-MANIFEST-v417-frame-selection-scope.md
```

## Scope

V4.17 defines the boundary for later explicit frame-selection validation.

It reserves:

```text
frame:<name> on verbal Lexicon rows as an explicit selector candidate
multi-frame FRAME_GRAPH inventory handling
selected-frame slot validation
selected-frame licensing diagnostics
```

## Preserved

```text
no Java source changes
no class-file changes
no jar changes
no parser changes
no generator changes
no validator changes
no checker changes
no UI/rendering changes
no graph mutation
no OPN example semantics changes
```

## Expected unchanged checks

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-03-mapping-v418-frame-selection-validator-target.md`
# Phase manifest — Mapping V4.18 explicit frame-selection validator target

Status:

```text
MAPPING_V4_18_FRAME_SELECTION_VALIDATOR_TARGET
```

Date:

```text
2026-05-03
```

## Type

Documentation-only implementation target.

## Base

```text
Mapping_V4-26-05-03--v417-frame-selection-scope.zip
```

## Runtime behavior

Unchanged:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

## Added documents

```text
md/sources/mapping-v4/frame-selection-v418.md
md/meta-inf/2026-05-03-mapping-v418-frame-selection-validator-target.md
md/examples/opn/mapping-v4-frame-selection-v418-expected-output-manifest.md
md/PATCH-MANIFEST-v418-frame-selection-validator-target.md
```

## Scope

V4.18 fixes the target behavior for the next explicit frame-selection validator.

It defines:

```text
verbal Lexicon frame:<name> as selected-frame source
selected-frame validation against selected frame(s), not all inventory frames
multi-frame FRAME_GRAPH inventory handling
exact first-slice diagnostic strings
future Info-line summary shape
```

## Preserved

```text
no Java source changes
no class-file changes
no jar changes
no parser changes
no generator changes
no runtime validator changes
no checker changes
no UI/rendering changes
no graph mutation
no OPN example semantics changes
```

## Expected unchanged checks

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-03-mapping-v420-language-tree-opn-test.md`
# 2026-05-03 — Mapping V4.20 Language Tree OPN test slice

## Status

```text
MAPPING_V4_20_LANGUAGE_TREE_OPN_TEST
```

## Added

- Language Tree OPN auto-open projection defaults.
- Three first testable language-tree examples.
- `tools/LanguageTreeRegressionChecker.java`.
- `run-language-tree-checker.bat`.

## Changed

- `graphStructure/Graph.java`
- `userInterface/GraphFileActions.java`
- `tools/MappingV4RegressionChecker.java` accepts role `C` for complementizer examples.

## Actual checks

```text
Mapping V4.20 Language Tree OPN regression checker: 3 pass, 0 fail
Mapping V4.19 baseline checker from this package line remains V4.16/V4.18-based if starting from V4.18.
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/meta-inf/2026-05-03-mapping-v4201-language-tree-opn-open-grid-fix.md`
# Phase manifest — Mapping V4.20.1 Language Tree OPN open-grid fix

Status:

```text
MAPPING_V4_20_1_LANGUAGE_TREE_OPN_OPEN_GRID_FIX
```

Base:

```text
MAPPING_V4_20_LANGUAGE_TREE_OPN_TEST
```

## Type

Small corrective behavior/test-data slice.

## Included

- Pipe OPN coordinate load fixed from 25 px scaling to OpenGraphGrid 20 px scaling.
- Pipe OPN grid size now expands to fit the largest source grid coordinate.
- Three Language Tree OPN examples revised so source nodes occupy unique horizontal grid rows.
- Language Tree checker expanded with grid-alignment, unique-row and in-grid assertions.

## Checks

```text
Mapping V4.20.1 Language Tree OPN regression checker: 3 pass, 0 fail
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v3/README.md`
# Mapping V3 source MD set

Status: bundled project-source snapshot.

Deze map bevat de actuele Mapping V3-bronbestanden die als projectcontext worden gebruikt.

Inhoud:

- adverbs-v2.md
- current-state.md
- generator-validator.md
- mapping-v3-neg.md
- mapping-v3-spec.md
- opn-format.md
- placement-rules-v3.md
- projectie-master-spec.md
- recipient-theme-v3.md

Let op:

- Deze bestanden zijn documentatie/spec-bronnen.
- Ze wijzigen geen Java-code.
- De actieve testlaag blijft Mapping V3 core + checker.
- WH/DET/FRAME/lexicon/NEG-bump blijven latere fasen volgens `meta-inf/frame_md.tmp`.
---

## FILE: `md/sources/mapping-v3/adverbs-v2.md`
# Adverbs (TIME, PLACE, NEG)

## Rollen

- TIME
- PLACE
- NEG

## Gedrag

```text
TIME  → begin
NEG   → na AUX
PLACE → einde
```

## Voorbeeld

```text
gisteren vrouw heeft niet trui gebreid daar
```

## Status

Historisch begonnen als v2-uitbreiding. In de actuele lijn horen bijwoorden in MAPPING_V3 thuis.
---

## FILE: `md/sources/mapping-v3/current-state.md`
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
---

## FILE: `md/sources/mapping-v3/generator-validator.md`
# Generator & Validator

## Pipeline

1. Lees mapping.
2. Valideer constraints.
3. Genereer volgorde.
4. Toon resultaat in Info.

## Validator

- Controleert constraints.
- Geeft OK / FAIL per regel.
- Toont samenvatting met aantallen.

Voorbeeld:

```text
validation: 7 ok, 0 fail
```

## Generator

- Gebruikt rules.
- Bouwt lineaire volgorde.
- Geeft:
  - best
  - alternatieven

Voorbeeld:

```text
generated:
  best: gisteren vrouw heeft niet trui gebreid daar
```

## Belangrijk

- Geen graph-mutatie.
- Alles via de lexicale as.
- Info-output gebruikt multiline wrap.
---

## FILE: `md/sources/mapping-v3/mapping-v3-neg.md`
# NEG Constraint V3

## Regel

```text
NEG -> after_aux_before_object
```

## Constraint

```text
V-AUX < NEG < Patiens
```

## Doel

Voorkomen:
- NEG vóór AUX
- NEG na object

## Correct

```text
heeft niet trui gebreid
```

## Status

Deze regel hoort in MAPPING_V3.
---

## FILE: `md/sources/mapping-v3/mapping-v3-spec.md`
# Mapping V3 Spec

## Principes

- Data-driven: gedrag komt uit `.opn`, niet uit hardcoded volgorde.
- Meerdere placement-opties zijn toegestaan.
- Ranking kan via placement rules worden vastgelegd.
- Mapping wordt gelezen als modeldata en niet als graph-nodes getekend.

## Rollen

- Agens
- Patiens
- RECIPIENT
- THEME
- TIME
- PLACE
- NEG
- V-AUX
- V-PART

## Constraints

- V-AUX < NEG < Patiens
- RECIPIENT < THEME
- THEME < V-PART

## Doel

```text
Mapping → constraints → validatie → generatie
```

## Compatibiliteit

- MAPPING_V3 is leidend.
- MAPPING_V1 en MAPPING_V2 bestaan alleen nog voor compatibiliteit.
---

## FILE: `md/sources/mapping-v3/opn-format.md`
# OPN Format (v3)

## Structure (visual)

```text
STRUCTURE_NODES:
id|label|x|y

STRUCTURE_EDGES:
id|id

END_STRUCTURE:
```

## Mapping (logic)

```text
MAPPING_V3:
LEXICAL_ITEMS:
x1|woord|role:...|pos:...

VERB_DOMAIN:
V|x2,x4

PLACEMENT_RULES:
ROLE -> constraint

END_MAPPING_V3:
```

## Regels

- STRUCTURE wordt getekend.
- MAPPING wordt niet getekend.
- Pipe-safe parsing is verplicht.
- Save OPN forceert extensie `.opn`.
- Roundtrip moet STRUCTURE en MAPPING_V3 behouden.
---

## FILE: `md/sources/mapping-v3/placement-rules-v3.md`
# Placement Rules V3

## Basis

```text
Agens     -> left_of_V
Patiens   -> right_of_V
RECIPIENT -> before_Theme
THEME     -> before_V_PART
```

## Verb

```text
V-AUX  -> anchor
V-PART -> clause_end
```

## Negatie

```text
NEG -> after_aux_before_object
```

Constraint:

```text
V-AUX < NEG < Patiens
```

## Bijwoorden

```text
TIME  -> before_clause
PLACE -> after_clause
```

## V3-uitbreiding

V3 ondersteunt ranked alternatieven. Het systeem moet de beste output tonen en maximaal enkele alternatieven in Info.
---

## FILE: `md/sources/mapping-v3/projectie-master-spec.md`
# Projectie Master Spec

## Architectuur

```text
Frame   → rollen
Phrase  → structuur
Lex     → lineaire as
```

## Kernregel

```text
Structuur + mapping → uiting
```

## Geen

- transformaties op bomen
- VP als verplichte node
- graph-mutatie door generator

## Wel

- projectie
- constraint-based volgorde
- V-centrische phrase-structuur
- Frame als rol-/projectieregelruimte
- lexicale as als primaire doelruimte van de uiting

## Frame / Phrase scheiding

Wijzigingen in Frame en Phrase gelden niet automatisch voor beide. Alleen expliciet gekoppelde besluiten gelden voor beide.

## OPN

- `.graph` = zichtbare snapshot.
- `.opn` = modelbestand met structure + mapping.
- Save OPN forceert extensie `.opn`.
- Roundtrip moet mapping behouden.
---

## FILE: `md/sources/mapping-v3/recipient-theme-v3.md`
# Recipient / Theme V3

## Rollen

- RECIPIENT
- THEME

## Regel

```text
RECIPIENT < THEME
```

## Voorbeeld

```text
vrouw geeft man boek
```

## Uitbreiding

Combineerbaar met:
- NEG
- TIME
- PLACE

## Doel

Ondersteunen van meerdere argumenten per uiting.
---

## FILE: `md/sources/mapping-v4/README.md`
# Mapping V4

Mapping V4 starts from the stable Mapping V3 core checkpoint.

## Start status

V4.0 is a scope-freeze and test-basis phase.

No Java code, runtime behavior, generator logic, validator logic, UI behavior, rendering behavior, `.opn` parsing behavior, or checker logic is changed in this phase.

## Base checkpoint

Base package:

```text
Projectie-26-04-27-mapping-v310-core-stable-checkpoint.zip
```

Base status:

```text
MAPPING_V3_CORE_STABLE
```

Base regression:

```text
Mapping V3 regression: 13 pass, 0 fail
MD folder check: PASS
```

## V4 rule

Every V4 phase must preserve the V3 core unless the phase explicitly states otherwise.

At the end of each phase, the following must remain true:

```text
Mapping V3 regression: pass
MD folder check: PASS
all .md files under md/
```

## Packaging rule

Every project zip must include an md-only sources zip containing all Markdown files included in the package, preserving their relative paths.

The md-only zip is intended for manual upload to Project Sources and for source-state verification.

Current package location:

```text
md/sources-md-zip/
```
---

## FILE: `md/sources/mapping-v4/current-state.md`
# Current State (Mapping V4)

Status:

```text
MAPPING_V4_17_FRAME_SELECTION_SCOPE
```

## Phase

V4.17 is a documentation-only explicit frame-selection scope phase on top of V4.16.

Runtime behavior remains:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

V4.16 remains the active runtime behavior: it validates explicit morphology fields on `LEXICON` rows and appends a diagnostic-only morphology summary to the Lexicon Info line when morphology metadata is present. V4.17 adds no Java behavior. Generated output remains based on explicit `MAPPING_V4` placement rules.

## Stable V3 core preserved

The V3 stable core remains:

- Agens
- Patiens
- RECIPIENT
- THEME
- V
- V-AUX
- V-PART
- split VP: `V-AUX ... V-PART`

## Controlled in V4.1

- `NEG|after_aux_before_object|Patiens`
- `TIME|realizes_before|Agens`
- `TIME|before_clause`
- `PLACE|realizes_after|V-PART`
- `PLACE|after_clause`

## Added in V4.3

- `WH` as a known role.
- Minimal WH generation via lexical-axis placement constraints.
- `CLAUSE_MODE: interrogative_wh` as mapping metadata in examples.
- Valid examples:
  - `wie heeft de hond gebeten`
  - `wat heeft vrouw gebreid`
  - `wie bijt hond`
- Invalid examples:
  - missing `V-AUX`
  - ordering cycle

## Added in V4.5

- `DET` as a known Mapping V4 role.
- `det-target` and `det_target` parsing in lexical items.
- DET target validation:
  - missing `det-target`
  - unknown target role
  - known but absent target role
- DET generation through ordinary placement rules, especially `DET|before|Patiens|core`.
- Valid DET examples:
  - `vrouw bijt de hond`
  - `vrouw heeft de trui gebreid`
  - `wie heeft de hond gebeten`
- Invalid DET examples:
  - missing `det-target`
  - unknown `det-target`
  - absent lexical target
  - ordering cycle
- Mapping V4 checker expanded to include DET.

## Added in V4.5.1

- Info-window label clarification:
  - old: `generated: best: ...`
  - new: `generated best: ...`
- This is display formatting only.
- Invalid mapping output remains `generated: none (invalid mapping)`.

## Added in V4.5.2

- Stable handoff checkpoint on top of V4.5.1.
- Documentation/package state only.
- No Java, class, jar, parser, generator, validator, checker, mapping-rule, UI, rendering, graph-mutation or example-semantics change.

## Added in V4.6

- FRAME.graph scope document.
- FRAME.graph expected-output manifest for later behavior phases.
- FRAME.graph phase manifest.
- Patch manifest.
- Refreshed md-only source zip and all-md source bundle.

## V4.7 architecture rule

```text
STRUCTURE = view
MAPPING_V4 = explicit lexical-axis mapping
FRAME.graph = metadata-only semantic/frame context
WH = lexical item on the lexical axis
DET = lexical item on the lexical axis
geen graph-mutatie
geen transformaties
output via generator
validatie en generatie via de lexicale as
```

## Added in V4.7

- `FRAME_GRAPH:` / `END_FRAME_GRAPH:` is recognized as metadata.
- Distinct frame names are counted.
- `slot:<role>` fields are counted.
- Info reports frame metadata as `Frame graph: ... metadata only`.
- Mapping V4 checker expanded with two FRAME.graph metadata examples.
- Generated utterance remains unchanged and still comes from placement rules.

## Added in V4.8

- FRAME.graph slot-validation scope document.
- FRAME.graph slot-validation expected-output manifest for a later implementation phase.
- FRAME.graph slot-validation phase manifest.
- Patch manifest.
- Refreshed md-only source zip and all-md source bundle.
- First-slice semantic slot roles reserved for later validation: `Agens`, `Patiens`, `RECIPIENT`, `THEME`.
- Runtime FRAME.graph behavior remains metadata read/count only.


## Added in V4.9

- Runtime minimal `FRAME_GRAPH` slot validation.
- Info summary now includes `frame validation: <ok> ok, <fail> fail`.
- First-slice accepted frame slot roles: `Agens`, `Patiens`, `RECIPIENT`, `THEME`.
- Diagnostics for missing required slot, unknown slot role, lexical semantic role not licensed by frame, and malformed `FRAME_GRAPH` row.
- Invalid FRAME.graph examples under `examples/opn/mapping-v4-frame-invalid/`.
- Mapping V4 checker expanded to V4.9.
- FRAME.graph validation failures do not suppress generated output.

## Added in V4.10

- Runtime `LEXICON:` / `END_LEXICON:` metadata read/count.
- Info summary reports `Lexicon: <n> entries, metadata only`.
- Valid Lexicon metadata examples under `examples/opn/mapping-v4-lexicon/`.
- Mapping V4 checker expanded to V4.10.
- Generated output remains derived from explicit `MAPPING_V4` placement rules.
- Lexicon metadata does not infer roles, select frames, validate morphology, render entries or mutate the graph.

## Fixed in V4.10.1

- Ordinary straight undirected edges no longer draw a visible center/midpoint marker.
- This removes the blue midpoint marker on simple structure edges such as `S — V`.
- Curved and orthogonal undirected edges still show their center/bend marker.
- Directed edge arrows are unchanged.
- No mapping, generator, validator, FRAME.graph, Lexicon metadata, graph-mutation or OPN semantics changes.

## Added in V4.11

- Lexicon validation/coupling scope document.
- Defines the first minimal lexicon diagnostics for later implementation:
  - malformed lexicon row
  - missing lexical key
  - missing lemma
  - missing form
  - duplicate lexical key
  - unknown lexical role
  - lexical role not present in explicit `MAPPING_V4` items
  - lexicon frame reference not present in `FRAME_GRAPH`, only when `FRAME_GRAPH` is present
- Runtime remains V4.10.1.
- No Java, class, jar, parser, generator, validator, checker, UI, rendering, graph-mutation or example-semantics changes.

## Added in V4.12

- Runtime Lexicon validation diagnostics for `LEXICON:` / `END_LEXICON:` rows.
- Info summary now reports `Lexicon: <n> entries; lexicon validation: <ok> ok, <fail> fail`.
- Valid Lexicon examples updated from metadata-only expectation to validation expectation.
- Invalid Lexicon examples added under `examples/opn/mapping-v4-lexicon-invalid/`.
- Mapping V4 checker expanded to V4.12.
- Diagnostics cover malformed rows, missing key, missing lemma, missing form, duplicate key, unknown role, role absent from explicit `MAPPING_V4`, and missing `FRAME_GRAPH` frame reference when `FRAME_GRAPH` is present.
- Lexicon validation failures do not suppress generated output.


## Added in V4.13

- Lexicon / morphology / frame-selection scope document.
- Documentation-only expected-output target manifest for later morphology and frame-selection behavior.
- V4.13 phase manifest and patch manifest.
- Reserves future morphology metadata validation without implementing it.
- Reserves future explicit frame-selection validation without implementing it.
- Runtime behavior remains V4.12.
- Generated output remains derived from explicit `MAPPING_V4` placement rules.

## Added in V4.14

- Morphology metadata validation scope document.
- Documentation-only expected-output target manifest for later morphology validation behavior.
- V4.14 phase manifest and patch manifest.
- Reserves morphology metadata validation as a future behavior slice.
- Reserved future morphology keys: `tense`, `number`, `person`, `gender`, `case`, `mood`, `aspect`, `finite`.
- Runtime behavior remains V4.12.
- Generated output remains derived from explicit `MAPPING_V4` placement rules.
- `form:<surface>` remains authoritative; no automatic inflection or surface-form generation is added.


## Added in V4.15

- Morphology metadata validator target document.
- Documentation-only expected-output target manifest for a later Java morphology validator.
- V4.15 phase manifest and patch manifest.
- Fixes the target validator boundary for accepted morphology keys, value domains, diagnostics and compatibility with `pos`.
- Runtime behavior remains V4.12.
- Generated output remains derived from explicit `MAPPING_V4` placement rules.
- `form:<surface>` remains authoritative; no automatic inflection or surface-form generation is added.


## Added in V4.16

- Runtime morphology metadata validation for explicit fields on `LEXICON` rows.
- Info summary now appends `morphology validation: <ok> ok, <fail> fail` to the Lexicon line when morphology metadata is present.
- Accepted morphology keys: `tense`, `number`, `person`, `gender`, `case`, `mood`, `aspect`, `finite`.
- Diagnostics cover unknown morphology feature, missing value, duplicate feature key, feature/`pos` incompatibility and unknown value.
- Valid morphology examples added under `examples/opn/mapping-v4-morphology/`.
- Invalid morphology examples added under `examples/opn/mapping-v4-morphology-invalid/`.
- Mapping V4 checker expanded to V4.16.
- Morphology validation failures do not suppress generated output.


## Added in V4.17

- Explicit frame-selection scope document.
- Documentation-only expected-output target manifest for later frame-selection validation behavior.
- V4.17 phase manifest and patch manifest.
- Defines `frame:<name>` on verbal `LEXICON` rows as the proposed explicit selector source.
- Reserves multi-frame `FRAME_GRAPH` inventory handling for a later behavior slice.
- Defines that future selected-frame validation should validate against selected frame(s), not every frame listed in `FRAME_GRAPH`.
- Runtime behavior remains V4.16.
- Generated output remains derived from explicit `MAPPING_V4` placement rules.


## Added in V4.18

- Explicit frame-selection validator target document.
- Documentation-only expected-output target manifest for the next Java behavior slice.
- V4.18 phase manifest and patch manifest.
- Fixes verbal `LEXICON` `frame:<name>` rows as the selected-frame source.
- Defines selected-frame validation against selected frame(s), not every frame listed in `FRAME_GRAPH`.
- Defines exact first-slice diagnostics for missing selector, non-verbal selector, multiple selected frames, absent selected frame, missing required selected-frame slot and lexical role not licensed by selected frame.
- Defines the future Frame graph Info-line target shape:
  - `frame selection: <selected> selected, <fail> fail`
  - `selected frame validation: <ok> ok, <fail> fail`
- Runtime behavior remains V4.16.
- Generated output remains derived from explicit `MAPPING_V4` placement rules.

## Still outside runtime behavior scope

- FRAME.graph generation behavior
- FRAME.graph rendering
- role inference from Lexicon
- automatic lexical insertion
- automatic frame selection
- automatic inflection / surface-form generation
- UI/rendering/view-options
- graph mutation behavior
- transformations / vooropplaatsing
- adjective placement
- complex NP structure
- relative clauses
- multiple DETs targeting the same nominal role

## Actual checks

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```



## Mapping V4.20 Language Tree OPN test slice

Current added test target: `MAPPING_V4_20_LANGUAGE_TREE_OPN_TEST`.

Language Tree OPN files with `STRUCTURE_TYPE: LANGUAGE_TREE` open with LEX-left/SYN-right projection defaults.


## Added in V4.20.1

- Corrected pipe-style OPN coordinate loading for `STRUCTURE_NODES`: source coordinates now map to the 20 px OpenGraphGrid.
- Pipe-style OPN grid sizing now expands to include the largest source coordinate.
- Revised the three Language Tree OPN examples so source nodes do not share horizontal grid rows.
- Expanded the Language Tree checker to verify grid alignment, unique horizontal rows and grid containment.

## Actual checks for V4.20.1

```text
Mapping V4.20.1 Language Tree OPN regression checker: 3 pass, 0 fail
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/det-v44.md`
# Mapping V4.4 — DET scope

Status:

```text
MAPPING_V4_4_DET_SCOPE
```

## Purpose

V4.4 defines the next small Mapping V4 slice after V4.3 minimal WH: controlled handling of determiners (`DET`) as separate lexical-axis items.

This phase is documentation and expected-output scope only. It does not implement the DET generator or validator yet.

## Base

```text
Mapping_V4-26-04-29--v43-wh-minimal.zip
```

Preserved runtime behavior:

```text
MAPPING_V4_3_WH_MINIMAL
Mapping V4.3 regression checker: 24 pass, 0 fail
```

## Scope

In scope for the future DET behavior:

- `DET` as a known lexical role.
- DET represented as a normal lexical item on the lexical axis.
- A `det-target` relation from DET to its nominal host role.
- Minimal NP-internal placement: DET immediately before its nominal target.
- DET combined with already-stable V4.3 WH examples.
- Invalid examples for missing target, unknown target and ordering cycle.

Out of scope for V4.4:

- Java implementation.
- `.class` rebuilds.
- jar rebuilds.
- parser/generator/validator changes.
- graph mutation.
- FRAME.graph integration.
- lexicon / automatic role inference.
- adjective placement.
- complex NP structure.
- relative clauses.
- UI/rendering changes.

## Proposed DET representation

A determiner is represented as a separate lexical item:

```text
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
```

The initial implementation should normalize both spellings:

```text
det-target
det_target
```

The preferred project spelling is:

```text
det-target
```

## Minimal DET rule pattern

For a plain declarative transitive clause:

```text
Agens|left_of|V|core
Patiens|right_of|V|core
DET|before|Patiens|core
```

This should produce:

```text
vrouw bijt de hond
```

For split VP:

```text
Agens|before|V-AUX|core
Patiens|after|V-AUX|core
DET|before|Patiens|core
Patiens|before|V-PART|core
V-PART|clause_end|core
```

This should produce:

```text
vrouw heeft de trui gebreid
```

For WH + DET, WH remains a lexical-axis item and DET remains a lexical-axis item:

```text
WH|before_clause|core
V-AUX|after|WH|core
Patiens|after|V-AUX|core
DET|before|Patiens|core
Patiens|before|V-PART|core
V-PART|clause_end|core
```

This should produce:

```text
wie heeft de hond gebeten
```

## Validation target for later V4.5 implementation

The later implementation should reject:

- DET without `det-target`.
- DET with an unknown target role.
- DET target that is absent from the lexical items.
- DET ordering that creates a cycle in the best placement rules.
- Multiple DET items targeting the same nominal role unless explicitly allowed in a later phase.

## Architecture rule

```text
STRUCTURE = view
MAPPING_V4 = logica
WH = lexical item on the lexical axis
DET = lexical item on the lexical axis
no graph mutation
no tree transformation
output via generator
validation and generation through placement constraints
```

## Next proposed phase

```text
V4.5 — minimal DET generator / validator
```
---

## FILE: `md/sources/mapping-v4/det-v45.md`
# Mapping V4.5 — minimal DET generator / validator

Status:

```text
MAPPING_V4_5_DET_MINIMAL
```

## Purpose

V4.5 implements the smallest behavior slice from the V4.4 DET scope: determiners are separate lexical-axis items and are generated by ordinary placement rules.

## Base

```text
Mapping_V4-26-04-30--v44-det-scope.zip
```

Preserved baseline:

```text
MAPPING_V4_3_WH_MINIMAL
Mapping V4.3 regression checker: 24 pass, 0 fail
```

## Implemented behavior

### DET role

`DET` is now accepted as a known Mapping role.

```text
x3|de|role:DET|det-target:Patiens|pos:DET
x4|hond|role:Patiens|pos:N
```

### Target spelling

Both spellings are accepted:

```text
det-target
det_target
```

The preferred spelling remains:

```text
det-target
```

### Generation

DET is not inserted by a special noun-phrase generator. It is ordered by the same placement-rule mechanism as the rest of Mapping V4.

Minimal pattern:

```text
DET|before|Patiens|core
```

Example output:

```text
vrouw bijt de hond
```

### Validation

The validator now rejects:

- DET without `det-target`.
- DET with an unknown target role.
- DET whose target role is known but absent from the lexical items.
- DET placement cycles in the best placement rules.

## Added examples

Valid:

```text
examples/opn/mapping-v4-det/01-vrouw-bijt-de-hond.opn
examples/opn/mapping-v4-det/02-vrouw-heeft-de-trui-gebreid.opn
examples/opn/mapping-v4-det/03-wie-heeft-de-hond-gebeten.opn
```

Invalid:

```text
examples/opn/mapping-v4-det-invalid/01-det-missing-target.opn
examples/opn/mapping-v4-det-invalid/02-det-unknown-target.opn
examples/opn/mapping-v4-det-invalid/03-det-target-absent.opn
examples/opn/mapping-v4-det-invalid/04-det-ordering-cycle.opn
```

## Checker

The V4 checker now covers:

- V3 core valid + invalid: 13 checks
- V4.1 NEG/TIME/PLACE valid + invalid: 6 checks
- V4.3 WH valid + invalid: 5 checks
- V4.5 DET valid + invalid: 7 checks

Expected result:

```text
Mapping V4.5 DET regression checker
summary: 31 pass, 0 fail
```

## Scope boundary

Not included:

- graph mutation
- tree transformation / vooropplaatsing
- FRAME.graph
- lexicon / automatic role inference
- adjective placement
- complex NP structure
- relative clauses
- multiple DET items targeting the same nominal role
- UI/rendering changes

## Architecture rule

```text
STRUCTURE = view
MAPPING_V4 = logica
DET = lexical item on the lexical axis
output via generator
validation through lexical-axis metadata and placement rules
```
---

## FILE: `md/sources/mapping-v4/frame-graph-v46.md`
# Mapping V4.6 — FRAME.graph scope

Status:

```text
MAPPING_V4_6_FRAME_GRAPH_SCOPE
```

## Purpose

V4.6 defines the role of `FRAME.graph` before any runtime implementation.

This phase is documentation and expected-output scope only. It does not parse, validate, generate from, render or mutate graph content based on `FRAME.graph`.

## Base

```text
Mapping_V4-26-05-01--v452-stable-handoff.zip
```

Preserved stable base:

```text
MAPPING_V4_5_2_STABLE_HANDOFF
Runtime behavior: MAPPING_V4_5_1_INFO_LABEL
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Core decision

`FRAME.graph` is not the generator.

`FRAME.graph` is a later semantic/frame context layer next to the existing explicit lexical-axis mapping.

The Mapping architecture remains:

```text
STRUCTURE = view
MAPPING_V4 = explicit lexical-axis mapping
FRAME.graph = future semantic/frame context
no graph mutation
no tree transformation / vooropplaatsing
output via generator
validation and generation via the lexical axis
```

## Meaning of FRAME.graph

A `FRAME.graph` describes a semantic frame and its slots.

Example frames:

```text
BIJTEN:
  required slots:
    Agens
    Patiens

GEVEN:
  required slots:
    Agens
    RECIPIENT
    THEME
```

In later phases, this can support checks such as:

- whether a frame declares all required slots;
- whether lexical roles used in `MAPPING_V4` are licensed by the selected frame;
- whether required frame slots are filled by lexical items;
- whether a verb-domain can be associated with a frame.

V4.6 does not implement those checks.

## Relationship to STRUCTURE

`STRUCTURE` remains a drawn view.

For V4.6, `FRAME.graph` does not change the drawn structure. It is not projected into the editor, not rendered as new nodes and not used to reposition existing nodes.

## Relationship to MAPPING_V4

`MAPPING_V4` remains explicit.

A V4.5-style mapping remains valid without `FRAME.graph`:

```text
x1|vrouw|role:Agens
x2|bijt|role:V
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens

Agens|left_of|V|core
Patiens|right_of|V|core
DET|before|Patiens|core
```

Expected generated best remains:

```text
vrouw bijt de hond
```

A later `FRAME.graph` layer may say that the verb belongs to frame `BIJTEN` and that `Agens` and `Patiens` are licensed slots, but this phase does not execute that logic.

## Proposed future OPN section shape

Documentation-only proposal:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:
```

Alternative spellings and the final parser form are not fixed in V4.6.

Rules for a later implementation phase:

- keep the section pipe-safe;
- keep it separate from `STRUCTURE_NODES` and `STRUCTURE_EDGES`;
- do not draw frame metadata as graph nodes;
- do not infer lexical roles unless a later lexicon phase explicitly defines that behavior.

## In scope for V4.6

- Define `FRAME.graph` as semantic/frame context.
- Define the boundary between `STRUCTURE`, `MAPPING_V4` and `FRAME.graph`.
- Define proposed future frame-slot examples.
- Define future valid and invalid behavior targets.
- Keep runtime behavior unchanged.
- Keep all Markdown under `md/`.

## Out of scope for V4.6

```text
Java source changes
.class rebuilds
jar rebuilds
FRAME_GRAPH parser
FRAME.graph validator
FRAME.graph generator behavior
FRAME.graph rendering
checker expansion
lexicon / automatic role inference
verb-frame inference
role inference from node labels
graph mutation
tree transformation / vooropplaatsing
UI/view options
adjective placement
complex NP structure
relative clauses
multiple DETs targeting the same nominal role
```

## Later implementation candidates

A later behavior phase may add one small slice at a time:

1. Parse and count a `FRAME_GRAPH` section as metadata only.
2. Report frame metadata in the Info window.
3. Validate that frame slots and lexical roles are compatible.
4. Add expected-output / expected-fail checks for frame-slot diagnostics.
5. Only after that, connect a lexicon layer to frame selection.

These are not V4.6 behavior.

## Expected checks

Because V4.6 is documentation-only:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/frame-graph-v47.md`
# Mapping V4.7 — FRAME.graph metadata read / count

Status:

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
```

## Purpose

V4.7 is the first small behavior slice after the V4.6 FRAME.graph scope phase.

It reads a `FRAME_GRAPH` section as metadata only and reports the number of frames and slots in the Info window.

## Base

```text
Mapping_V4-26-05-01--v46-frame-graph-scope.zip
```

## Implemented

- `FRAME_GRAPH:` / `END_FRAME_GRAPH:` metadata section is recognized.
- Rows of the following form are counted:

```text
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
```

- Distinct frame names are counted as frames.
- `slot:<role>` fields are counted as slots.
- Info window adds a summary such as:

```text
OPN Frame graph: 1 frames, 2 slots, metadata only
```

- Mapping V4 regression checker includes two valid FRAME.graph metadata examples.

## Still explicit

`FRAME.graph` remains metadata only.

Existing `MAPPING_V4` lexical-axis mapping remains explicit:

```text
x1|vrouw|role:Agens
x2|bijt|role:V
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
```

Generation remains derived from `PLACEMENT_RULES`, not from `FRAME_GRAPH`.

## Not implemented

V4.7 does not implement:

```text
frame-slot validation
role licensing diagnostics
automatic role inference
lexicon lookup
generated utterance changes
FRAME.graph rendering
graph mutation
tree transformation / vooropplaatsing
UI view toggles
```

## Expected checks

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next candidate phase

```text
V4.8 — FRAME.graph slot validation scope
```

Recommended first validation scope:

- required slot present / absent
- unknown slot role
- metadata diagnostics only
- still no generation changes
---

## FILE: `md/sources/mapping-v4/frame-graph-v48.md`
# Mapping V4.8 — FRAME.graph slot validation scope

Status:

```text
MAPPING_V4_8_FRAME_GRAPH_SLOT_VALIDATION_SCOPE
```

## Purpose

V4.8 defines the next FRAME.graph behavior slice before implementation: minimal slot validation.

This phase is documentation and expected-output scope only. It does not change Java source, class files, jars, parser behavior, generator behavior, validator behavior, checker behavior, UI, rendering, graph mutation or example semantics.

## Base

```text
Mapping_V4-26-05-01--v47-frame-graph-metadata.zip
```

Preserved base behavior:

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
FRAME_GRAPH metadata read/count
Info summary for frame and slot counts
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Core decision

V4.8 does not implement frame-slot validation.

It only defines the smallest later validation behavior that may be implemented in V4.9:

```text
FRAME_GRAPH declares required semantic slots.
MAPPING_V4 lexical items explicitly fill roles.
A later validator checks compatibility between declared frame slots and explicit lexical roles.
```

The generator remains unchanged:

```text
generated best comes from MAPPING_V4 placement rules
not from FRAME_GRAPH
```

## Validation model reserved for V4.9

A later minimal validator may check:

1. required frame slots are filled by explicit `MAPPING_V4` lexical roles;
2. frame slot roles are known Mapping roles;
3. lexical semantic roles are licensed by the selected frame;
4. malformed `FRAME_GRAPH` metadata is reported as metadata diagnostics;
5. invalid FRAME.graph validation suppresses generated output only if the later phase explicitly decides that frame validation is part of the mapping validity gate.

V4.8 does not decide item 5 as runtime behavior. It records the decision point.

## Known frame slot roles for first validator slice

The first validation slice should be conservative and reuse existing Mapping roles:

```text
Agens
Patiens
RECIPIENT
THEME
```

Later roles remain outside the first validator slice unless explicitly added:

```text
TIME
PLACE
NEG
WH
DET
V
V-AUX
V-PART
```

Reason: V4.8 keeps FRAME.graph semantic-slot validation separate from clause-mode, adverbial, determiner and verb-domain mechanics.

## Proposed valid examples for later V4.9

### BIJTEN with required Agens and Patiens

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:

MAPPING_V4:
x1|vrouw|role:Agens
x2|bijt|role:V
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
...
END_MAPPING_V4:
```

Expected later validator result:

```text
frame validation: 2 ok, 0 fail
```

Expected generated output remains:

```text
generated best: vrouw bijt de hond
```

### GEVEN with Agens, RECIPIENT and THEME

```text
FRAME_GRAPH:
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

Expected later validator result:

```text
frame validation: 3 ok, 0 fail
```

Expected generated output remains:

```text
generated best: vrouw heeft man boek gegeven
```

## Proposed invalid examples for later V4.9

### Missing required slot

If `FRAME_GRAPH` declares:

```text
frame|BIJTEN|slot:Patiens|required
```

but no explicit `MAPPING_V4` lexical item has `role:Patiens`, the later diagnostic should contain:

```text
missing required frame slot Patiens for frame BIJTEN
```

### Unknown frame slot role

If `FRAME_GRAPH` declares:

```text
frame|BIJTEN|slot:LOC|required
```

and `LOC` is not in the known first-slice frame slot roles, the later diagnostic should contain:

```text
unknown frame slot role LOC for frame BIJTEN
```

### Lexical role not licensed by frame

If the explicit lexical mapping contains a semantic role not declared by the frame:

```text
x5|daar|role:PLACE
```

while the selected first-slice frame only declares `Agens` and `Patiens`, the later diagnostic should contain:

```text
lexical role PLACE is not licensed by frame BIJTEN
```

For V4.8 this remains a target rule, not runtime behavior.

### Malformed frame metadata

If a `FRAME_GRAPH` row lacks a frame name or slot field, the later diagnostic should contain:

```text
malformed FRAME_GRAPH row
```

## Explicitly out of scope for V4.8

```text
Java source changes
.class rebuilds
jar rebuilds
FRAME.graph runtime validation
checker expansion
new invalid .opn examples
FRAME.graph generation behavior
role inference
verb-to-frame lexicon lookup
automatic frame selection
FRAME.graph rendering
graph mutation
tree transformation / vooropplaatsing
UI view toggles
adjective placement
complex NP structure
relative clauses
multiple DETs targeting the same nominal role
```

## Boundary retained from V4.7

V4.7 already reads and counts `FRAME_GRAPH` metadata.

V4.8 does not retract that behavior. It only defines the next validation scope.

Current runtime-visible FRAME.graph behavior remains:

```text
OPN Frame graph: <n> frames, <m> slots, metadata only
```

## Expected checks

Because V4.8 is documentation-only:

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next candidate phase

```text
V4.9 — FRAME.graph minimal slot validator
```

Recommended V4.9 scope:

```text
required slot present / absent
unknown slot role
metadata diagnostics in Info
no generated-output changes unless explicitly decided
no graph mutation
no rendering
```
---

## FILE: `md/sources/mapping-v4/frame-graph-v49.md`
# Mapping V4.9 — FRAME.graph minimal slot validator

Status:

```text
MAPPING_V4_9_FRAME_GRAPH_MINIMAL_SLOT_VALIDATOR
```

## Purpose

V4.9 implements the first minimal runtime validator for `FRAME_GRAPH` metadata.

It keeps the V4.7/V4.8 architecture boundary:

```text
STRUCTURE = view
MAPPING_V4 = explicit lexical-axis mapping
FRAME_GRAPH = semantic/frame metadata plus validation diagnostics
no graph mutation
no tree transformation / vooropplaatsing
generated output remains derived from MAPPING_V4 placement rules
```

## Base

```text
Mapping_V4-26-05-01--v48-frame-graph-slot-validation-scope.zip
```

Preserved base behavior:

```text
MAPPING_V4_7_FRAME_GRAPH_METADATA
FRAME_GRAPH metadata read/count
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Implemented behavior

V4.9 validates `FRAME_GRAPH` rows of this shape:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
END_FRAME_GRAPH:
```

The Info window now reports both counts and validation:

```text
OPN Frame graph: 1 frames, 2 slots; frame validation: 2 ok, 0 fail (frame slots satisfied)
```

## First-slice slot roles

The first runtime validator accepts these frame slot roles:

```text
Agens
Patiens
RECIPIENT
THEME
```

Rows with other `slot:<role>` values are reported as frame-validation failures.

## Validation checks

Implemented checks:

1. required frame slots must be filled by explicit `MAPPING_V4` lexical roles;
2. frame slot roles must be known first-slice roles;
3. checked lexical semantic roles must be licensed by the frame;
4. malformed `FRAME_GRAPH` rows are reported as metadata diagnostics.

Checked lexical semantic roles are:

```text
Agens
Patiens
RECIPIENT
THEME
TIME
PLACE
```

Verb-domain and clause-mechanics roles remain outside slot licensing:

```text
V
V-AUX
V-PART
DET
WH
NEG
```

## Generated output policy

Frame-validation failures do not suppress generated output in V4.9.

Invalid mapping placement still suppresses generated output as before:

```text
generated: none (invalid mapping)
```

Frame diagnostics are reported separately in the `OPN Frame graph` line.

## Added examples

Valid examples remain under:

```text
examples/opn/mapping-v4-frame/
```

Invalid frame examples are added under:

```text
examples/opn/mapping-v4-frame-invalid/
```

Invalid examples cover:

```text
missing required frame slot
unknown frame slot role
lexical role not licensed by frame
malformed FRAME_GRAPH row
```

## Checker

The V4 checker now covers:

- V3 core valid + invalid: 13 checks
- V4.1 NEG/TIME/PLACE valid + invalid: 6 checks
- V4.3 WH valid + invalid: 5 checks
- V4.5 DET valid + invalid: 7 checks
- V4.9 FRAME.graph slot validation valid + invalid: 6 checks

Expected result:

```text
Mapping V4.9 FRAME.graph minimal slot validator regression checker
summary: 37 pass, 0 fail
```

## Scope boundary

Not included:

```text
FRAME.graph generation behavior
generated-output suppression on frame-validation failure
graph rendering
view toggles
graph mutation
role inference
lexicon lookup
automatic frame selection
verb-to-frame lexicon binding
adjective placement
complex NP structure
relative clauses
```
---

## FILE: `md/sources/mapping-v4/frame-selection-v417.md`
# Mapping V4.17 — explicit frame-selection scope

Status:

```text
MAPPING_V4_17_FRAME_SELECTION_SCOPE
```

## Purpose

V4.17 is a documentation-only scope phase after V4.16.

It defines the boundary for the next frame-selection work without changing runtime behavior. The key distinction is:

```text
FRAME_GRAPH = available frame inventory / semantic context
LEXICON frame:<name> = explicit frame selector candidate
MAPPING_V4 = authoritative lexical-axis mapping and generated output source
```

## Base

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

V4.16 already provides:

- `FRAME_GRAPH:` metadata and minimal slot validation
- `LEXICON:` metadata, Lexicon validation and morphology metadata validation
- `frame:<name>` reference checking from Lexicon rows to `FRAME_GRAPH` when `FRAME_GRAPH` is present
- generated output based on explicit `MAPPING_V4` placement rules
- informational diagnostics only for FRAME.graph, Lexicon and morphology layers

## V4.17 runtime change

```text
none
```

Runtime behavior remains:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

## Problem reserved for the next behavior slice

The current FRAME.graph validator is intentionally minimal. It treats `FRAME_GRAPH` as a small context layer and validates required slots against explicit `MAPPING_V4` lexical roles.

That is sufficient for one-frame examples, but it is not yet a true frame-selection model for multi-frame inventories. A later behavior slice may need to validate only the explicitly selected frame or frames, instead of treating every frame in `FRAME_GRAPH` as equally active.

## Proposed explicit selector source

The first frame-selection behavior slice should use existing Lexicon metadata:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|geven|lemma:geven|form:gegeven|role:V-PART|frame:GEVEN|pos:VPP
```

The `frame:<name>` field is explicit source data. It is not inferred.

Candidate frame-bearing lexical roles for the first validator:

```text
V
V-AUX
V-PART
```

The most conservative rule is:

```text
frame:<name> on a verbal Lexicon row selects that frame for frame-slot validation
```

## Target validation boundary for a later behavior phase

A later Java slice may validate:

```text
selected frame exists in FRAME_GRAPH
selected frame has required slots satisfied by explicit MAPPING_V4 lexical roles
explicit lexical semantic roles are licensed by the selected frame, not by every frame in FRAME_GRAPH
multiple selected frames are reported explicitly
selected frame is absent when multiple FRAME_GRAPH frames are present
selected frame is attached to a non-verbal Lexicon row
```

## Diagnostics reserved for a later behavior phase

Candidate diagnostics:

```text
selected frame <frame> is not present in FRAME_GRAPH
missing selected frame for multi-frame FRAME_GRAPH
multiple selected frames: <frames>
selected frame <frame> is attached to non-verbal lexicon entry <key>
missing required selected-frame slot <role> for frame <frame>
lexical role <role> is not licensed by selected frame <frame>
```

Exact wording should be fixed in the next implementation-target phase, not in V4.17.

## Generated output boundary

Frame selection must not become the generator.

Generated output remains derived from explicit `MAPPING_V4` lexical-axis placement rules.

Frame-selection validation must not:

```text
infer lexical roles
insert lexical items
choose words from LEXICON
generate from FRAME_GRAPH
replace form:<surface>
inflect words
change placement rules
suppress generated output in its first diagnostic slice
mutate STRUCTURE
mutate MAPPING_V4
render frames as graph nodes
```

## Multi-frame inventory rule

A future frame-selection validator should allow `FRAME_GRAPH` to contain more than one frame.

For example:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

If the Lexicon explicitly selects `frame:BIJTEN`, the validation target should be `BIJTEN`. The unselected `GEVEN` frame remains available context and should not by itself force missing-slot failures.

## Future behavior candidates

Recommended sequence:

1. V4.18 — frame-selection validator target document.
2. V4.19 — minimal explicit frame-selection validator Java behavior slice.
3. Later — optional diagnostics for ambiguous or competing frame candidates.

## Preserved

```text
Java source unchanged
class files unchanged
jar files unchanged
Mapping V4 parser unchanged
Mapping V4 generator unchanged
Mapping V4 placement validator unchanged
FRAME.graph runtime validator unchanged
Lexicon runtime validator unchanged
Morphology runtime validator unchanged
OPN example semantics unchanged
graph rendering unchanged
graph mutation unchanged
```

## Actual checks

Because V4.17 is documentation-only, the runtime check status remains the V4.16 status:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/frame-selection-v418.md`
# Mapping V4.18 — explicit frame-selection validator target

Status:

```text
MAPPING_V4_18_FRAME_SELECTION_VALIDATOR_TARGET
```

## Purpose

V4.18 is a documentation-only implementation target after V4.17.

It fixes the exact target boundary for the next Java behavior slice: a minimal explicit frame-selection validator that uses existing `LEXICON` metadata and validates selected frames against `FRAME_GRAPH`.

V4.18 does not change runtime behavior.

## Base

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
MAPPING_V4_17_FRAME_SELECTION_SCOPE
```

V4.17 established the scope rule:

```text
FRAME_GRAPH = available frame inventory / semantic context
LEXICON frame:<name> = explicit selected-frame source
MAPPING_V4 = authoritative lexical-axis mapping and generated-output source
```

## V4.18 runtime change

```text
none
```

Runtime behavior remains:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

## Target selector source for V4.19

The next Java slice should read selected frames from explicit Lexicon metadata:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|geven|lemma:geven|form:gegeven|role:V-PART|frame:GEVEN|pos:VPP
```

Accepted first-slice selector roles:

```text
V
V-AUX
V-PART
```

A `frame:<name>` field on these roles is an explicit selected-frame declaration. It is not inferred from word form, lemma, surface position or `FRAME_GRAPH` contents.

## Target frame inventory rule

`FRAME_GRAPH` may contain one or more frame definitions:

```text
FRAME_GRAPH:
frame|BIJTEN|slot:Agens|required
frame|BIJTEN|slot:Patiens|required
frame|GEVEN|slot:Agens|required
frame|GEVEN|slot:RECIPIENT|required
frame|GEVEN|slot:THEME|required
END_FRAME_GRAPH:
```

When an explicit selected frame is present, selected-frame validation should validate only the selected frame or selected frames.

Unselected frames remain inventory/context. They must not by themselves trigger missing-slot or role-licensing failures.

## Target validation outputs

The next Java slice should preserve the existing frame graph count and add two selected-frame summaries to the Frame graph Info line.

Target form:

```text
Frame graph: <frames> frames, <slots> slots; frame selection: <selected> selected, <fail> fail; selected frame validation: <ok> ok, <fail> fail (<details>)
```

The existing V4.9 form remains acceptable only for examples without a selected-frame condition:

```text
Frame graph: <frames> frames, <slots> slots; frame validation: <ok> ok, <fail> fail (<details>)
```

## Target selection algorithm

For the first behavior slice:

1. Read all `LEXICON` rows.
2. Collect `frame:<name>` values from rows whose canonical role is `V`, `V-AUX` or `V-PART`.
3. Count distinct selected frames.
4. Report selection diagnostics before selected-frame slot validation.
5. If selection has one or more fatal selection failures, report selected-frame validation as not run or `0 ok, 0 fail` according to the checker target fixed in the implementation slice.
6. If no frame is selected and `FRAME_GRAPH` contains exactly one frame, preserve the current V4.9 one-frame validation behavior.
7. If no frame is selected and `FRAME_GRAPH` contains multiple frames, report a missing selected-frame diagnostic.

## Target selected-frame slot validation

For each valid selected frame:

- required selected-frame slots must be present as explicit `MAPPING_V4` lexical roles
- lexical semantic roles in explicit `MAPPING_V4` must be licensed by the selected frame
- validation uses selected frame slots only
- unselected frame slots are ignored for slot satisfaction and licensing

First-slice semantic slot roles remain:

```text
Agens
Patiens
RECIPIENT
THEME
```

Lexical roles checked for selected-frame licensing remain:

```text
Agens
Patiens
RECIPIENT
THEME
TIME
PLACE
```

`TIME` and `PLACE` can still produce not-licensed diagnostics when the selected frame does not license them.

## Target diagnostics for V4.19

Exact diagnostic strings for the next behavior slice should be:

```text
selected frame <frame> is not present in FRAME_GRAPH
missing selected frame for multi-frame FRAME_GRAPH
multiple selected frames: <frames>
selected frame <frame> is attached to non-verbal lexicon entry <key>
missing required selected-frame slot <role> for frame <frame>
lexical role <role> is not licensed by selected frame <frame>
```

The `<frames>` list should be stable and comma-space separated in lexical-row encounter order after de-duplication:

```text
BIJTEN, GEVEN
```

## Informational-only boundary

In the first Java behavior slice, frame-selection diagnostics should remain informational like FRAME.graph, Lexicon and morphology diagnostics.

They must not suppress generated output when the `MAPPING_V4` placement validation itself is valid.

Generated output remains derived from explicit `MAPPING_V4` placement rules.

## Preserved boundaries

V4.18 and the intended V4.19 behavior do not add:

```text
automatic frame selection
role inference
automatic lexical insertion
generation from LEXICON
generation from FRAME_GRAPH
surface-form generation
automatic inflection
graph mutation
FRAME.graph rendering
STRUCTURE mutation
MAPPING_V4 mutation
placement-rule changes
```

## Target examples

The target expected-output manifest is:

```text
md/examples/opn/mapping-v4-frame-selection-v418-expected-output-manifest.md
```

V4.18 does not add runtime `.opn` examples. It fixes the expected-output target for the next behavior slice.

## Actual checks

Because V4.18 is documentation-only, runtime checks remain the V4.16 checks:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/info-label-v451.md`
# Mapping V4.5.1 — Info label clarification

Status:

```text
MAPPING_V4_5_1_INFO_LABEL
```

## Purpose

Clarify the Info-window label for the selected generated output.

The previous display was technically correct but visually ambiguous:

```text
generated: best: vrouw ziet de man
```

The new display is:

```text
generated best: vrouw ziet de man
```

## Meaning

`generated best` is the highest-ranked generated candidate according to the current placement rules.

It does not mean that the output is independently proven semantically complete, linguistically perfect, or derived by graph/tree mutation.

## Scope

Changed:

- Info-window text formatting only.

Not changed:

- Mapping generator.
- Mapping validator.
- Placement rules.
- Checkers.
- OPN format.
- Graph rendering.
- Graph mutation behavior.
- DET / WH behavior.

## Invalid output

Invalid mappings keep the existing wording:

```text
generated: none (invalid mapping)
```
---

## FILE: `md/sources/mapping-v4/language-tree-v420.md`
# Mapping V4.20 — Language Tree OPN first test slice

## Status

```text
MAPPING_V4_20_LANGUAGE_TREE_OPN_TEST
```

## Goal

Make real language-tree `.opn` files directly testable in OpenGraphEd.

## Behavior

When an `.opn` file contains:

```text
STRUCTURE_TYPE: LANGUAGE_TREE
```

or equivalent `LANGUAGE_TREE`/`L1` metadata, Open OPN marks the graph as a language tree and opens it with the projection context active:

```text
LEX left
SYN right
```

The structure itself still comes from explicit `STRUCTURE_NODES` / `STRUCTURE_EDGES`. Metadata is not drawn as graph content.

## Examples

```text
examples/opn/language-tree-v420/01-ltree-wie-heeft-de-hond-gebeten.opn
examples/opn/language-tree-v420/02-ltree-dat-de-vrouw-de-hond-heeft-gebeten.opn
examples/opn/language-tree-v420/03-ltree-vrouw-heeft-man-boek-gegeven.opn
```

## Scope

No tree transformations. No automatic lexical insertion. No graph mutation. The generated utterance remains derived from explicit `MAPPING_V4` placement rules.
---

## FILE: `md/sources/mapping-v4/language-tree-v4201.md`
# Mapping V4.20.1 — Language Tree OPN open-grid fix

## Status

```text
MAPPING_V4_20_1_LANGUAGE_TREE_OPN_OPEN_GRID_FIX
```

## Goal

Repair the first Language Tree OPN test slice so the shipped test files open as OpenGraph-style source trees.

## Fixes

- Pipe-style `STRUCTURE_NODES` coordinates are now loaded as OpenGraph grid coordinates using the active 20 px grid cell.
- The OPN pipe loader sizes the grid display window from the largest source coordinate instead of relying only on the old fixed grid size.
- The three Language Tree OPN examples no longer place multiple source nodes on the same horizontal grid row.
- The Language Tree regression checker now asserts:
  - every source node is on the OpenGraphGrid;
  - no two source nodes share the same horizontal grid row;
  - every source node lies inside the OpenGraphGrid display window.

## Preserved

- No generated utterance rule changes.
- No FRAME.graph, Lexicon or morphology validation changes.
- No projection rendering algorithm changes.
- No graph mutation behavior changes.
- `STRUCTURE_TYPE: LANGUAGE_TREE` still opens with LEX-left and SYN-right projection defaults.

## Actual checks

```text
Mapping V4.20.1 Language Tree OPN regression checker: 3 pass, 0 fail
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/lexicon-morphology-frame-selection-v413.md`
# Mapping V4.13 — Lexicon / morphology / frame-selection scope

Status:

```text
MAPPING_V4_13_LEXICON_MORPHOLOGY_FRAME_SELECTION_SCOPE
```

## Purpose

V4.13 is a documentation-only scope phase after V4.12.

It records the boundary for later Lexicon, morphology and frame-selection work without changing runtime behavior.

## Base

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

V4.12 already provides:

- `LEXICON:` metadata read/count
- Lexicon validation diagnostics
- optional validation of `frame:<name>` references against `FRAME_GRAPH`
- generated output still based on explicit `MAPPING_V4` placement rules
- informational Lexicon diagnostics only

## V4.13 runtime change

```text
none
```

Runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Scope decisions

### Lexicon remains metadata/context

`LEXICON` is still not the generator.

It may later support checks or suggestions, but it does not currently:

- infer roles
- insert missing lexical items
- select frames automatically
- inflect words
- render entries as graph nodes
- mutate `STRUCTURE`
- mutate `MAPPING_V4`

### Explicit lexical-axis mapping remains authoritative

The generator continues to use explicit `MAPPING_V4` lexical items and placement rules.

Lexicon entries may describe possible words, forms, roles and frames, but they do not override the explicit lexical-axis sequence.

### Morphology is reserved, not implemented

V4.13 reserves future morphology metadata fields, for example:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3
```

Reserved future checks may include:

- known morphology feature names
- duplicate feature keys
- feature compatibility with `pos`
- required feature presence for a later morphology slice

V4.13 does not implement these checks.

### Form remains explicit

The field `form:<surface>` remains the visible lexical form supplied by the OPN source.

Future morphology work must not silently replace it with an inferred form unless a later behavior phase explicitly defines that rule.

### Frame selection is reserved, not implemented

`frame:<name>` in a Lexicon row is currently only validated against `FRAME_GRAPH` when `FRAME_GRAPH` is present.

Future frame-selection work may define how a lexical entry can be associated with a selected frame, but V4.13 does not add:

- automatic frame selection
- frame-driven role inference
- generation from `FRAME_GRAPH`
- generation from `LEXICON`
- fallback frame selection
- semantic disambiguation

## Future behavior candidates

Later phases may be split into small slices such as:

1. morphology metadata validation
2. Lexicon-to-Mapping consistency checks beyond role presence
3. explicit frame-selection metadata validation
4. optional user-visible diagnostics for ambiguous Lexicon/frame combinations

These candidates are not part of V4.13 runtime behavior.

## Preserved

```text
Java source unchanged
class files unchanged
jar files unchanged
Mapping V4 parser unchanged
Mapping V4 generator unchanged
Mapping V4 placement validator unchanged
FRAME.graph metadata/slot validation unchanged
Lexicon runtime validator unchanged
OPN example semantics unchanged
graph rendering unchanged
graph mutation unchanged
```

## Actual checks

Because V4.13 is documentation-only, the runtime check status remains the V4.12 status:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/lexicon-v410.md`
# Mapping V4.10 — Lexicon metadata read / count

Status:

```text
MAPPING_V4_10_LEXICON_METADATA
```

## Purpose

V4.10 adds the first runtime-visible lexicon layer.

The lexicon layer is metadata only in this phase. It lets an `.opn` file carry lexical entries next to `MAPPING_V4` and `FRAME_GRAPH`, without changing generated output, role validation, graph rendering or graph structure.

## Architecture boundary

```text
STRUCTURE = drawn view
MAPPING_V4 = explicit lexical-axis mapping and placement rules
FRAME_GRAPH = semantic/frame metadata plus frame-slot validation diagnostics
LEXICON = lexical metadata read/count only
```

V4.10 does not infer roles from the lexicon. The generator still uses explicit lexical items and explicit placement rules from `MAPPING_V4`.

## Recognized section

V4.10 recognizes this metadata section:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|vrouw|lemma:vrouw|form:vrouw|role:Agens|pos:N
END_LEXICON:
```

Rows are counted when they are pipe-delimited rows inside `LEXICON:` / `END_LEXICON:`. Field contents are not validated in this phase.

## Info-window behavior

The Info window reports the lexicon count as a separate line:

```text
OPN Lexicon: 4 entries, metadata only
```

If the same `.opn` also has Mapping and FRAME.graph metadata, the Info window reports all layers separately:

```text
OPN Mapping: ...
OPN Frame graph: ...
OPN Lexicon: ...
```

## Added examples

Valid examples are added under:

```text
examples/opn/mapping-v4-lexicon/
```

They preserve generated output from the explicit `MAPPING_V4` placement rules while adding a `LEXICON` metadata section.

## Preserved behavior

V4.10 preserves:

```text
generated output from MAPPING_V4
Mapping V4 placement validation
FRAME.graph slot validation
graph rendering
graph mutation boundary
tree transformation boundary
```

## Out of scope

Not included:

```text
lexicon validation
role inference from lexical entries
automatic frame selection
verb-to-frame binding enforcement
morphology or inflection
language-wide lexicon lookup
generated-output changes
generated-output suppression on lexicon diagnostics
UI rendering of lexical entries
graph mutation
```

## Checker

The V4 checker now covers:

- V3 core valid + invalid: 13 checks
- V4.1 NEG/TIME/PLACE valid + invalid: 6 checks
- V4.3 WH valid + invalid: 5 checks
- V4.5 DET valid + invalid: 7 checks
- V4.9 FRAME.graph slot validation valid + invalid: 6 checks
- V4.10 Lexicon metadata valid: 2 checks

Expected result:

```text
Mapping V4.10 lexicon metadata regression checker
summary: 39 pass, 0 fail
```
---

## FILE: `md/sources/mapping-v4/lexicon-validation-v411.md`
# Mapping V4.11 — Lexicon validation / coupling scope

Status:

```text
MAPPING_V4_11_LEXICON_VALIDATION_SCOPE
```

## Purpose

V4.11 defines the next lexicon step before implementation.

V4.10 already reads `LEXICON:` metadata and counts entries. V4.11 keeps runtime behavior unchanged and freezes the first minimal validation/coupling boundary for a later behavior phase.

## Current architecture boundary

```text
STRUCTURE = drawn view
MAPPING_V4 = explicit lexical-axis mapping and placement rules
FRAME_GRAPH = semantic/frame metadata plus frame-slot validation diagnostics
LEXICON = lexical metadata layer
```

The generator must still use explicit `MAPPING_V4` lexical items. Lexicon data may be checked against those explicit items, but must not silently create them, move them, infer tree transformations or mutate the graph.

## Candidate syntax already accepted from V4.10

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|vrouw|lemma:vrouw|form:vrouw|role:Agens|pos:N
END_LEXICON:
```

The first minimal validator should continue to treat rows as pipe-delimited lexical entries.

## First validator boundary for the next behavior phase

The intended first behavior phase after this scope may validate only these items:

```text
malformed lexicon row
missing lexical key
missing lemma
missing form
duplicate lexical key
unknown lexical role
lexical role not present in explicit MAPPING_V4 items
lexicon frame reference not present in FRAME_GRAPH, only when FRAME_GRAPH is present
```

## Coupling rule

Coupling is diagnostic only in the first implementation phase.

Allowed:

```text
Info diagnostics
checker diagnostics
expected-fail examples
```

Not allowed:

```text
generated-output changes
role inference
automatic lexical insertion
automatic frame selection
graph mutation
rendering of lexical entries
suppression of generated output because of lexicon diagnostics
```

## Expected Info-line direction

A later implementation can extend the current V4.10 Info line:

```text
OPN Lexicon: 4 entries, metadata only
```

to a validation summary such as:

```text
OPN Lexicon: 4 entries; lexicon validation: 4 ok, 0 fail
```

For invalid files, diagnostics should be compact and not alter the existing generated-output line.

## Example families for the next behavior phase

Valid examples may remain under:

```text
examples/opn/mapping-v4-lexicon/
```

Invalid examples can be added under:

```text
examples/opn/mapping-v4-lexicon-invalid/
```

Expected invalid cases:

```text
01-lexicon-malformed-row.opn
02-lexicon-missing-key.opn
03-lexicon-duplicate-key.opn
04-lexicon-unknown-role.opn
05-lexicon-role-not-in-mapping.opn
06-lexicon-frame-not-in-frame-graph.opn
```

## Preserved in V4.11

V4.11 itself preserves:

```text
V4.10.1 runtime behavior
Mapping V4 generation and validation
FRAME.graph validation
Lexicon read/count only at runtime
graph rendering
graph mutation boundary
```

## Checks

Expected unchanged checks:

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/lexicon-validation-v412.md`
# Mapping V4.12 — Lexicon validator

Status:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## Purpose

V4.12 implements the first minimal `LEXICON` validation slice defined in V4.11.

The validator is diagnostic only. It checks Lexicon metadata against explicit `MAPPING_V4` lexical items and, when present, `FRAME_GRAPH` frame names. It does not generate from Lexicon data.

## Runtime behavior

A Lexicon section is still written as pipe-delimited rows:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|frame:BIJTEN|pos:V
lex|vrouw|lemma:vrouw|form:vrouw|role:Agens|pos:N
END_LEXICON:
```

The Info window now reports validation:

```text
OPN Lexicon: 4 entries; lexicon validation: 4 ok, 0 fail
```

For invalid Lexicon metadata, the Info window keeps the Mapping output intact and reports compact diagnostics on the Lexicon line, for example:

```text
OPN Lexicon: 1 entries; lexicon validation: 0 ok, 1 fail (file: 04-lexicon-unknown-role.opn; unknown lexical role LOC for lexicon key loc)
```

## Validator scope

V4.12 validates:

```text
malformed LEXICON row
missing lexical key
missing lemma
missing form
duplicate lexical key
unknown lexical role
lexical role not present in explicit MAPPING_V4 items
lexicon frame reference not present in FRAME_GRAPH, only when FRAME_GRAPH is present
```

A lexicon row passes only when its key, lemma, form, role and optional frame reference are valid for this first slice.

## Coupling rule

Lexicon coupling is informational only.

Allowed:

```text
Info diagnostics
checker diagnostics
valid and invalid Lexicon examples
```

Not allowed:

```text
generated-output changes
role inference
automatic lexical insertion
automatic frame selection
morphology / inflection
graph mutation
rendering of lexical entries
suppression of generated output because of Lexicon diagnostics
```

## Examples

Valid examples remain under:

```text
examples/opn/mapping-v4-lexicon/
```

Invalid examples are under:

```text
examples/opn/mapping-v4-lexicon-invalid/
```

The invalid examples keep Mapping V4 placement validation valid. Lexicon diagnostics are checked separately and generated output is not suppressed.

## Checks

Actual checks:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/midpoint-v4101.md`
# Mapping V4.10.1 — straight-edge midpoint cleanup

Status:

```text
MAPPING_V4_10_1_STRAIGHT_EDGE_MIDPOINT_CLEANUP
```

## Purpose

Remove the visible midpoint marker from ordinary straight undirected edges in normal rendering.

This fixes the unwanted middle dot on simple structure edges, for example the edge between `S` and `V` in the V4.10 lexicon example.

## Behavior

Changed:

```text
straight undirected edge: no center/midpoint marker is drawn
```

Preserved:

```text
curved undirected edge: center marker remains visible
orthogonal undirected edge: bend marker remains visible
directed edge: arrow remains visible
selected edge outline behavior remains unchanged
```

## Scope boundary

Not changed:

```text
MAPPING_V4 parser
MAPPING_V4 generator
MAPPING_V4 validator
FRAME_GRAPH metadata or validation
LEXICON metadata read/count
OPN semantics
graph mutation behavior
file format
```

## Checks

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/morphology-v414.md`
# Mapping V4.14 — morphology metadata validation scope

Status:

```text
MAPPING_V4_14_MORPHOLOGY_METADATA_VALIDATION_SCOPE
```

## Purpose

V4.14 is a documentation-only scope phase after V4.13.

It narrows the next morphology step to metadata validation only. It does not implement runtime morphology parsing or validation.

## Base

```text
MAPPING_V4_13_LEXICON_MORPHOLOGY_FRAME_SELECTION_SCOPE
```

Base runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## V4.14 runtime change

```text
none
```

## Scope decision

The first morphology behavior slice should validate only explicit metadata fields that are already present in `LEXICON` rows.

It must not infer or generate a surface form.

Accepted future morphology metadata keys are reserved as:

```text
tense
number
person
gender
case
mood
aspect
finite
```

The future validator may check these fields only as metadata attached to a Lexicon entry, for example:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
```

## Reserved future diagnostics

The first future validator may report:

```text
unknown morphology feature
missing morphology feature value
duplicate morphology feature key
morphology feature incompatible with pos
unknown morphology feature value
```

These diagnostics must remain informational in the first behavior slice.

## Explicit form remains authoritative

The `form:<surface>` field remains the surface form used by explicit `MAPPING_V4` lexical items.

Future morphology validation must not silently replace:

```text
form:bijt
```

with a generated or inferred alternative.

## Compatibility with current Lexicon validator

The current V4.12 Lexicon validator validates basic Lexicon row structure and role/frame consistency.

V4.14 does not change that validator.

A later morphology validator should be layered after basic Lexicon validation and should not change generated output.

## Explicitly not included

```text
runtime morphology validation
automatic inflection
surface-form generation
role inference
automatic lexical insertion
automatic frame selection
generation from LEXICON
generation from FRAME_GRAPH
Lexicon rendering
graph mutation
```

## Preserved

```text
Java source unchanged
class files unchanged
jar files unchanged
Mapping V4 parser unchanged
Mapping V4 generator unchanged
Mapping V4 placement validator unchanged
FRAME.graph metadata/slot validation unchanged
Lexicon validator behavior unchanged
OPN example semantics unchanged
graph rendering unchanged
graph mutation unchanged
```

## Check status

Because V4.14 is documentation-only, runtime checks remain the V4.12 status:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/morphology-v415.md`
# Mapping V4.15 — morphology metadata validator target

Status:

```text
MAPPING_V4_15_MORPHOLOGY_METADATA_VALIDATOR_TARGET
```

## Purpose

V4.15 is a documentation-only implementation target after V4.14.

V4.14 reserved the morphology boundary. V4.15 fixes the exact target behavior for a later Java behavior slice, but does not implement runtime parsing or validation in this package.

## Base

This package is rebuilt from the full V4.12 slim runtime package and carries forward the V4.13 and V4.14 documentation-only phases.

Runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

## V4.15 runtime change

```text
none
```

## Target behavior for the next Java slice

A later behavior package may add a morphology metadata validator that runs after the current Lexicon validator.

The validator should inspect morphology fields only on `LEXICON` rows:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
END_LEXICON:
```

It should report a separate Info-window diagnostic layer, for example:

```text
Lexicon: 4 entries; lexicon validation: 4 ok, 0 fail; morphology validation: 4 ok, 0 fail
```

## Accepted morphology metadata keys

The first validator target recognizes only these keys:

```text
tense
number
person
gender
case
mood
aspect
finite
```

## Target value domain

The first target validator may use this minimal value domain:

```text
tense: present, past
number: sg, pl
gender: common, neuter, masc, fem
case: nom, acc, dat, gen
mood: indicative, imperative, subjunctive
aspect: simple, perfect, progressive
finite: true, false
person: 1, 2, 3
```

The value domain is intentionally small. It is a metadata validator, not a grammar or inflection engine.

## Target diagnostics

The next Java behavior slice may report:

```text
unknown morphology feature
missing morphology feature value
duplicate morphology feature key
morphology feature incompatible with pos
unknown morphology feature value
```

Diagnostics must be informational in the first behavior slice.

## Minimal compatibility rules

The first target validator should keep compatibility rules deliberately conservative:

```text
finite only applies to pos:V
person only applies to pos:V or pos:PRON
case only applies to pos:N, pos:PRON, pos:DET or pos:ADJ
tense, mood and aspect only apply to pos:V
gender and number may apply to pos:N, pos:PRON, pos:DET or pos:ADJ
```

A missing `pos` value should not itself be a morphology failure unless a morphology feature requires `pos` to determine compatibility. Basic Lexicon row validity remains the responsibility of the V4.12 Lexicon validator.

## Explicit form remains authoritative

The surface form remains explicit:

```text
form:<surface>
```

The morphology validator must not replace, infer or generate this surface form.

For example, this remains a metadata diagnostic case only:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:past|number:sg|person:3|finite:true
```

The first validator may report a suspicious value only if the value is outside the allowed domain. It must not know that `bijt` is not past tense.

## Generated output boundary

Generated output remains derived from explicit `MAPPING_V4` lexical-axis placement rules.

The `LEXICON` section and morphology features do not:

```text
insert lexical items
select frames
select forms
inflect words
change placement rules
suppress generated output
mutate the graph
```

## Explicitly not included

```text
runtime morphology validation in this package
automatic inflection
surface-form generation
lemma-to-form lookup
role inference
automatic lexical insertion
automatic frame selection
generation from LEXICON
generation from FRAME_GRAPH
Lexicon rendering
graph mutation
```

## Preserved

```text
Java source unchanged
class files unchanged
jar files unchanged
Mapping V4 parser unchanged
Mapping V4 generator unchanged
Mapping V4 placement validator unchanged
FRAME.graph metadata/slot validation unchanged
Lexicon validator behavior unchanged
OPN example semantics unchanged
graph rendering unchanged
graph mutation unchanged
```

## Check status

Because V4.15 is documentation-only, runtime checks remain the V4.12 status:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/morphology-v416.md`
# Mapping V4.16 — minimal morphology metadata validator

Status:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

## Purpose

V4.16 implements the first runtime morphology metadata validator that was fixed as the V4.15 target.

The validator is diagnostic only. It inspects explicit morphology fields on `LEXICON` rows and appends a morphology summary to the existing Lexicon Info line. It does not generate, infer, select or mutate anything.

## Base

```text
Mapping_V4-26-05-02--v415-morphology-metadata-validator-target-full-from-v412-slim.zip
```

## Runtime behavior

A Lexicon row may contain morphology metadata:

```text
LEXICON:
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
END_LEXICON:
```

The Info window reports morphology validation after Lexicon validation when morphology metadata is present:

```text
OPN Lexicon: 1 entries; lexicon validation: 1 ok, 0 fail; morphology validation: 1 ok, 0 fail
```

If no morphology fields or morphology diagnostics are present, the V4.12 Lexicon summary remains unchanged.

## Accepted morphology metadata keys

```text
tense
number
person
gender
case
mood
aspect
finite
```

## Accepted value domain

```text
tense: present, past
number: sg, pl
gender: common, neuter, masc, fem
case: nom, acc, dat, gen
mood: indicative, imperative, subjunctive
aspect: simple, perfect, progressive
finite: true, false
person: 1, 2, 3
```

## Compatibility rules

The validator checks a conservative first-slice compatibility boundary with `pos`:

```text
finite applies to verbal pos values
person applies to verbal pos values or pos:PRON
case applies to pos:N, pos:PRON, pos:DET or pos:ADJ
tense, mood and aspect apply to verbal pos values
gender applies to pos:N, pos:PRON, pos:DET or pos:ADJ
number applies to verbal pos values and to pos:N, pos:PRON, pos:DET or pos:ADJ
```

Verbal `pos` values are accepted when they start with `V`, so `pos:V`, `pos:VFIN` and `pos:VPP` are treated as verbal for this metadata check.

A missing `pos` is only a morphology failure when a morphology feature needs `pos` for compatibility.

## Diagnostics

V4.16 reports these morphology diagnostics:

```text
unknown morphology feature <feature> at lexicon entry <key>
missing morphology feature value for <feature> at lexicon entry <key>
duplicate morphology feature <feature> at lexicon entry <key>
morphology feature <feature> incompatible with pos:<pos> at lexicon entry <key>
unknown morphology feature value <value> for <feature> at lexicon entry <key>
```

Diagnostics are informational and do not suppress generated output.

## Counting rule

Morphology validation counts Lexicon entries that contain morphology metadata.

```text
morphology validation: <entries-with-valid-morphology> ok, <entries-with-invalid-morphology> fail
```

Lexicon rows without morphology fields are not counted as morphology passes or failures.

## Generated output boundary

Generated output remains derived from explicit `MAPPING_V4` lexical-axis placement rules.

The morphology validator does not:

```text
replace form:<surface>
generate surface forms
inflect words
infer roles
insert lexical items
select frames
change placement rules
suppress generated output
mutate the graph
render Lexicon entries
```

## Examples

Valid examples are under:

```text
examples/opn/mapping-v4-morphology/
```

Invalid examples are under:

```text
examples/opn/mapping-v4-morphology-invalid/
```

The invalid examples keep Mapping V4 placement validation valid. Morphology diagnostics are checked separately and generated output remains available.

## Checks

Actual checks:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
---

## FILE: `md/sources/mapping-v4/scope-freeze.md`
# Mapping V4.0 Scope Freeze

## Status

```text
MAPPING_V4_SCOPE_FREEZE
```

## Meaning

V4.0 is not a behavior phase.

It only records the start of V4 and preserves the V3.10 core stable checkpoint as the baseline for future work.

## Allowed in V4.0

- Add Markdown documentation.
- Update Markdown index and changelog.
- Add a V4 meta manifest.
- Add the md-only sources zip to the project package.
- Verify that no `.md` files exist outside `md/`.
- Verify the unchanged V3 regression checker status.

## Forbidden in V4.0

- Java edits
- class rebuilds
- jar rebuilds
- generator edits
- validator edits
- parser edits
- UI edits
- renderer edits
- new example semantics
- changing the V3 expected-output baseline

## Reason

The previous checkpoint declared Mapping V3 core stable. V4 must start by protecting that stable state before adding new semantic scope.
---

## FILE: `md/sources/mapping-v4/stable-handoff-v452.md`
# Mapping V4.5.2 — stable handoff checkpoint

Status:

```text
MAPPING_V4_5_2_STABLE_HANDOFF
```

## Purpose

V4.5.2 is a documentation-only checkpoint on top of V4.5.1.

It records V4.5.1 as the accepted stable base before opening the next larger Mapping V4 phase.

## Base

```text
Mapping_V4-26-04-30--v451-info-label.zip
```

Base runtime behavior:

```text
MAPPING_V4_5_1_INFO_LABEL
```

## Changed

Documentation/package state only:

- added this V4.5.2 handoff document
- added the V4.5.2 phase manifest
- updated current-state, phasing, README, INDEX and CHANGELOG documentation
- refreshed the md-only source zip and all-md source bundle

## Preserved

No runtime behavior is changed.

Not changed:

```text
Java source
.class files
jar files
parser
generator
validator
checker
mapping rules
graph rendering
graph mutation behavior
example semantics
```

## Stable scope handed off

The stable behavior handed off from V4.5.1 includes:

- V3 core roles and split VP
- V4.1 NEG / TIME / PLACE placement behavior
- V4.3 minimal WH behavior
- V4.5 minimal DET behavior
- V4.5.1 Info-window label clarification

## Actual checks

Run against the V4.5.2 package state:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Next intended phase

```text
V4.6 — FRAME.graph scope
```

V4.6 should start as a separate scope/documentation phase unless explicitly decided otherwise.
---

## FILE: `md/sources/mapping-v4/v4-phasing.md`
# Mapping V4 Phasing

## Phase rule

V4 must advance in small, separately testable phases.

Each phase should end with:

```text
Mapping V3 regression: pass
MD folder check: PASS
phase examples present when behavior changes
phase expected-output / expected-fail manifests present when behavior changes
```

## Actual phase order

The initial planning listed DET before WH. The implemented line advanced WH first. From V4.4 onward the documentation follows the actual project sequence below.

## V4.0 — Scope freeze and test basis

Status: complete.

Purpose:

- establish V4 documentation
- freeze the V3 core baseline
- define phase order
- add packaging rule for the md-only sources zip

No code changes.

## V4.1 — NEG / TIME / PLACE

Status: complete.

Purpose:

- promote selected non-core adverbial and negation behavior into a controlled V4 layer
- define exact placement rules and diagnostics

Scope:

- NEG
- TIME
- PLACE
- bijwoordplaatsing
- valid examples
- invalid examples
- expected-output and expected-fail manifests

Explicitly not combined with:

- DET
- FRAME.graph

## V4.2 — WH scope

Status: complete.

Purpose:

- define WH as a lexical-axis item before implementation
- define boundary against transformation / vooropplaatsing

No runtime behavior change.

## V4.3 — minimal WH generator / validator

Status: complete.

Purpose:

- add minimal tested WH behavior
- keep WH on the lexical axis
- avoid graph mutation and tree transformation

Scope:

- WH role
- `CLAUSE_MODE: interrogative_wh` metadata in examples
- simple WH-positioning through placement rules
- diagnostics for incomplete WH structures

Explicitly not combined with:

- DET splitting
- FRAME.graph
- broad UI redesign

## V4.4 — DET scope

Status: complete.

Purpose:

- define controlled handling of determiners and simple nominal groups after V4.3 WH is stable
- define expected examples before implementation

Scope:

- DET as lexical-axis role proposal
- `det-target` relation proposal
- simple NP-internal placement proposal
- expected valid and invalid examples

No runtime behavior change.

## V4.5 — minimal DET generator / validator

Status: complete.

Purpose:

- implement the V4.4 DET scope minimally
- extend checker with DET valid and invalid examples

Scope:

- DET known role
- `det-target` / `det_target` normalization
- DET before nominal target through ordinary placement rules
- invalid DET diagnostics
- checker extension

Explicitly not combined with:

- multiple DETs targeting the same role
- adjectives
- complex NP
- relative clauses
- FRAME.graph
- lexicon
- UI/rendering changes

## V4.5.1 — Info label clarification

Status: complete.

Purpose:

- clarify the Info-window label for the selected generated output
- change `generated: best: ...` to `generated best: ...`

Scope:

- display formatting only
- no generator, validator, parser, checker, mapping-rule or graph-rendering change

## V4.5.2 — stable handoff checkpoint

Status: complete.

Purpose:

- record V4.5.1 as the stable base before the next major phase
- refresh project-source packaging artifacts
- keep runtime behavior unchanged

Scope:

- documentation/package state only
- no Java, class, jar, parser, generator, validator, checker, mapping-rule, UI, rendering, graph-mutation or example-semantics change

Checks:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## V4.6 — FRAME.graph scope

Status: complete.

Purpose:

- define FRAME.graph as a separate architectural layer before implementation
- define the boundary between `STRUCTURE`, `MAPPING_V4` and future frame metadata

Scope:

- formal definition of `FRAME.graph`
- relation to `STRUCTURE`
- relation to `MAPPING_V4`
- documentation-only frame-slot examples
- expected-output manifest for later implementation

Explicitly not included:

- FRAME_GRAPH parser
- FRAME.graph validator
- FRAME.graph generator behavior
- FRAME.graph rendering
- checker expansion
- lexicon / automatic role inference
- graph mutation
- UI/rendering changes

Checks:

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## V4.7 — FRAME.graph metadata read / count

Status: complete.

Purpose:

- parse a `FRAME_GRAPH` section as metadata only
- count/report frames and slots in Info
- keep generation, validation, rendering and graph mutation unchanged

Explicitly not in first FRAME.graph behavior phase:

- frame-slot validation
- lexicon / automatic role inference
- generated utterance changes
- graph mutation
- UI rendering of frames

Implemented in V4.7:

```text
FRAME_GRAPH metadata read/count
Info summary for frame and slot counts
checker extension with 2 valid metadata examples
```

Checks:

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## V4.8 — FRAME.graph slot validation scope

Purpose:

- validate required frame slots against explicit lexical-axis roles
- report frame diagnostics without changing generation

Candidate scope:

- missing required slot
- unknown slot role
- role not licensed by frame

Explicitly not in first validation phase:

- generated utterance changes
- role inference
- lexicon lookup
- graph mutation
- rendering of FRAME.graph

## V4.10 — Lexicon metadata read / count

Status: implemented behavior phase.

Purpose:

- separate lexical metadata from individual examples after roles and placement are stable

Implemented scope:

- `LEXICON:` / `END_LEXICON:` metadata section recognized during OPN open
- pipe-delimited lexical entries counted
- Info line added: `Lexicon: <n> entries, metadata only`
- V4 checker expanded with two valid lexicon metadata examples

Explicitly not in first lexicon phase:

- lexicon validation
- role inference
- automatic frame selection
- broad morphology
- inflection engine
- language-wide lexicon
- generated-output changes
- graph mutation

Checks:

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## V4.11 — Lexicon validation / coupling scope

Status: completed documentation-only scope phase.

Purpose:

- define the first minimal lexicon validator boundary before implementation
- keep V4.10.1 as the current runtime behavior
- avoid mixing lexicon diagnostics with generation, graph mutation, rendering or role inference

Scope:

- documentation-only validator boundary
- expected valid/invalid target diagnostics for later Lexicon validation
- first-slice diagnostics: malformed row, missing key, missing lemma, missing form, duplicate lexical key, unknown lexical role, role not present in explicit `MAPPING_V4`, optional frame-reference mismatch when `FRAME_GRAPH` is present
- phase manifest and patch manifest

Explicitly not included:

- Java changes
- runtime Lexicon validation
- checker expansion
- invalid lexicon `.opn` examples
- generated-output changes
- role inference
- automatic lexical insertion
- automatic frame selection
- graph mutation
- UI rendering of lexical entries

Checks:

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## Later candidate — UI / rendering / view options

Purpose:

- expose mapping diagnostics and view options after semantics are stable

Candidate scope:

- visible mapping diagnostics
- view toggle for mapping layer
- vertical lexical axis option

Preserved rule:

```text
No graph mutation as a consequence of mapping logic.
```

## V4.8 — FRAME.graph slot validation scope

Status: completed documentation-only scope phase.

Purpose:

- defined the first minimal FRAME.graph slot-validation boundary before implementation
- keep V4.7 metadata read/count as the current runtime behavior
- avoid mixing slot validation with generation, lexicon lookup, rendering or graph mutation

Scope:

- documentation-only validator boundary
- expected valid and invalid target diagnostics for later V4.9
- first-slice semantic slot roles: `Agens`, `Patiens`, `RECIPIENT`, `THEME`
- phase manifest and patch manifest

Explicitly not included:

- Java changes
- runtime FRAME.graph slot validation
- checker expansion
- invalid frame `.opn` examples
- generated-output changes
- FRAME.graph rendering
- role inference
- lexicon lookup
- graph mutation

Checks:

```text
Mapping V4.7 FRAME.graph metadata regression checker: 33 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## V4.9 — FRAME.graph minimal slot validator

Status: implemented behavior phase.

Implemented scope:

- required slot present / absent
- unknown slot role
- lexical semantic role not licensed by frame
- malformed `FRAME_GRAPH` row diagnostics
- metadata diagnostics in Info
- generated output unchanged and not suppressed by FRAME.graph validation failures
- no graph mutation
- no rendering

Checks:

```text
Mapping V4.9 FRAME.graph minimal slot validator regression checker: 37 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
## V4.10.1 — straight-edge midpoint cleanup

Status: implemented rendering cleanup patch.

Implemented scope:

- ordinary straight undirected edges no longer draw a visible center/midpoint marker
- simple structure edges such as `S — V` render as an edge between two nodes without a middle dot

Preserved:

- curved and orthogonal undirected edge center/bend markers remain visible
- directed edge arrows remain visible
- Mapping V4 generation and validation unchanged
- FRAME.graph and Lexicon metadata behavior unchanged
- no graph mutation

Checks:

```text
Mapping V4.10 lexicon metadata regression checker: 39 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```





## V4.12 — minimal Lexicon validator

Status: implemented behavior phase.

Implemented scope:

- validates `LEXICON:` rows as pipe-delimited `lex|key|lemma:...|form:...|role:...` entries
- detects malformed lexicon rows
- detects missing lexical key, lemma and form
- detects duplicate lexical keys
- detects unknown lexical roles
- detects lexicon roles absent from explicit `MAPPING_V4` lexical items
- detects `frame:<name>` references absent from `FRAME_GRAPH` when `FRAME_GRAPH` is present
- adds invalid lexicon examples
- expands Mapping V4 checker
- keeps generated output unchanged
- keeps lexicon diagnostics informational

Explicitly not included:

- generated-output changes
- role inference
- automatic lexical insertion
- automatic frame selection
- morphology / inflection
- graph mutation
- UI rendering of lexicon entries

Generated output remains derived from explicit `MAPPING_V4` placement rules. Lexicon validation failures do not suppress generated output in this phase.

Actual checks:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```


## V4.13 — Lexicon / morphology / frame-selection scope

Status: documentation-only scope phase.

Scope:

- records future morphology metadata boundary
- records future frame-selection boundary
- keeps `MAPPING_V4` explicit lexical-axis mapping authoritative
- keeps Lexicon diagnostics informational
- keeps generated output unchanged
- does not implement morphology validation
- does not implement automatic inflection
- does not implement role inference
- does not implement automatic lexical insertion
- does not implement automatic frame selection
- does not implement Lexicon rendering
- does not mutate the graph

Runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

Checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```


## V4.14 — morphology metadata validation scope

Status: documentation-only scope phase.

Scope:

- reserves the first later morphology step as metadata validation only
- reserves future morphology keys: `tense`, `number`, `person`, `gender`, `case`, `mood`, `aspect`, `finite`
- records future diagnostic boundary for unknown feature, missing value, duplicate key, feature/`pos` incompatibility and unknown feature value
- keeps `MAPPING_V4` explicit lexical-axis mapping authoritative
- keeps `form:<surface>` authoritative
- keeps generated output unchanged
- does not implement runtime morphology validation
- does not implement automatic inflection
- does not implement role inference
- does not implement automatic lexical insertion
- does not implement automatic frame selection
- does not mutate the graph

Runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

Checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```



## V4.15 — morphology metadata validator target

Status: documentation-only implementation target.

Scope:

- fixes the target behavior for a later morphology metadata validator
- keeps accepted morphology keys limited to `tense`, `number`, `person`, `gender`, `case`, `mood`, `aspect`, `finite`
- fixes a minimal allowed value domain
- fixes target diagnostics for unknown feature, missing value, duplicate feature, feature/`pos` incompatibility and unknown value
- keeps `MAPPING_V4` explicit lexical-axis mapping authoritative
- keeps `form:<surface>` authoritative
- keeps generated output unchanged
- does not implement runtime morphology validation in this package
- does not implement automatic inflection
- does not implement role inference
- does not implement automatic lexical insertion
- does not implement automatic frame selection
- does not mutate the graph

Runtime behavior remains:

```text
MAPPING_V4_12_LEXICON_VALIDATOR
```

Checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## V4.16 — minimal morphology metadata validator

Status: small behavior slice.

Scope:

- implements runtime morphology metadata validation on explicit `LEXICON` rows
- appends `morphology validation: <ok> ok, <fail> fail` to the Lexicon Info summary when morphology metadata is present
- accepts `tense`, `number`, `person`, `gender`, `case`, `mood`, `aspect`, `finite`
- checks minimal value domains
- checks feature/`pos` compatibility
- adds valid and invalid morphology examples
- expands Mapping V4 checker to V4.16
- keeps generated output unchanged
- keeps morphology diagnostics informational

Explicitly not included:

- automatic inflection
- surface-form generation
- lemma-to-form lookup
- role inference
- automatic lexical insertion
- automatic frame selection
- generation from `LEXICON`
- graph mutation
- Lexicon rendering

Runtime behavior is now:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

Actual checks:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```



## V4.17 — explicit frame-selection scope

Status: documentation-only scope phase.

Scope:

- defines explicit frame-selection as a later validator boundary
- treats `frame:<name>` on verbal `LEXICON` rows as the proposed selector source
- reserves multi-frame `FRAME_GRAPH` inventory handling
- reserves selected-frame slot validation
- reserves selected-frame lexical-role licensing diagnostics
- keeps `MAPPING_V4` explicit lexical-axis mapping authoritative
- keeps generated output unchanged
- does not implement runtime frame-selection validation in this package
- does not implement automatic frame selection
- does not implement role inference
- does not implement automatic lexical insertion
- does not generate from `LEXICON`
- does not generate from `FRAME_GRAPH`
- does not mutate the graph

Runtime behavior remains:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

Checks remain:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

## V4.18 — explicit frame-selection validator target

Status: documentation-only implementation target.

Scope:

- fixes the target behavior for a later explicit frame-selection validator
- treats `frame:<name>` on verbal `LEXICON` rows as the selected-frame source
- limits first-slice selector roles to `V`, `V-AUX` and `V-PART`
- allows multi-frame `FRAME_GRAPH` inventories
- validates selected-frame slots against selected frames, not all inventory frames
- fixes target diagnostics for missing selected frame, non-verbal selector, multiple selected frames, absent selected frame, missing required selected-frame slot and lexical role not licensed by selected frame
- keeps `MAPPING_V4` explicit lexical-axis mapping authoritative
- keeps generated output unchanged
- does not implement runtime frame-selection validation in this package
- does not implement automatic frame selection
- does not implement role inference
- does not implement automatic lexical insertion
- does not generate from `LEXICON`
- does not generate from `FRAME_GRAPH`
- does not mutate the graph

Runtime behavior remains:

```text
MAPPING_V4_16_MORPHOLOGY_METADATA_VALIDATOR
```

Checks remain:

```text
Mapping V4.16 morphology metadata validator regression checker: 53 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```

Suggested next phase:

```text
V4.19 — minimal explicit frame-selection validator
```


## V4.20 — Language Tree OPN test slice

Small testable slice: Language Tree `.opn` examples plus auto-open projection defaults.


## V4.20.1 — Language Tree OPN open-grid fix

Corrective slice after V4.20.

Scope:

- load pipe-style OPN source coordinates on the 20 px OpenGraphGrid;
- expand the pipe OPN grid to the largest source coordinate;
- keep Language Tree example source rows unique;
- add checker assertions for grid alignment, unique horizontal rows and grid containment.

Status:

```text
MAPPING_V4_20_1_LANGUAGE_TREE_OPN_OPEN_GRID_FIX
```
---

## FILE: `md/sources/mapping-v4/wh-v42.md`
# Mapping V4.2 — WH / clause mode scope

Status:

```text
MAPPING_V4_2_WH_SCOPE
```

## Purpose

V4.2 is a documentation and manifest phase for WH questions.

It prepares the next behavior phase without changing Java code, parser behavior, generator behavior, validator behavior, UI, rendering, `.class` files or jar files.

## Base

V4.2 starts from:

```text
MAPPING_V4_1_NEG_TIME_PLACE
```

The V4.1 baseline must remain true:

```text
Mapping V3 regression: 13 pass, 0 fail
Mapping V4.1 regression: 19 pass, 0 fail
MD folder check: PASS
```

## Core decision

WH is not treated as a transformation.

OpenGraphEd Mapping keeps the existing architecture:

```text
STRUCTURE = view
MAPPING_V4 = logic
no graph mutation
output via generator
validation and generation via the lexical axis
```

Therefore a WH question is specified by lexical items, role labels, clause mode metadata and placement rules.

## Clause mode

V4.2 introduces the scope concept:

```text
CLAUSE_MODE: interrogative_wh
```

This is a mapping-level property, not a graph-rendering property.

For now, clause mode is only documented. It is not yet parsed as behavior in V4.2.

## WH lexical items

A WH item is a lexical item with:

```text
role:WH
wh_target:<semantic role>
```

Proposed target roles for the first behavior phase:

```text
Agens
Patiens
```

Later targets such as RECIPIENT, THEME, PLACE and TIME are out of scope for the first WH behavior phase.

## Minimal placement model

V4.2 records the intended first WH placement behavior:

```text
WH     -> before_clause
V-AUX  -> after_WH
Agens  -> after_V_AUX    when Agens is not the WH target
Patiens -> after_V_AUX   when Patiens is not the WH target
V-PART -> clause_end
```

The exact internal relation names may still be normalized in V4.3.

## DET boundary

DET splitting remains outside V4.2.

Natural noun phrases such as `de hond` and `een boek` may remain one lexical item for this phase:

```text
x3|de hond|role:Patiens
```

That keeps V4.2 focused on WH and clause mode. A later DET phase may split this into separate DET and N lexical items.

## In scope

- Define WH as a lexical-axis mapping problem.
- Define clause mode as mapping metadata.
- Define expected valid WH examples.
- Define expected invalid WH diagnostics.
- Preserve V3 core and V4.1 behavior.
- Keep all Markdown under `md/`.

## Out of scope

- Java changes.
- Parser changes.
- Generator changes.
- Validator changes.
- New checker behavior.
- DET/lidwoorden as separate roles.
- FRAME.graph integration.
- Lexicon or automatic role inference.
- UI/rendering/view-options.
- Graph mutation.
- WH transformations or movement operations on trees.

## Next behavior phase

The next behavior phase should be:

```text
V4.3 — minimal WH generator and validator
```

V4.3 may implement subject-WH and object-WH only, using the expected-output manifest from V4.2.
---

## FILE: `md/sources/mapping-v4/wh-v43.md`
# Mapping V4.3 — minimal WH generator / validator

Status:

```text
MAPPING_V4_3_WH_MINIMAL
```

## Purpose

V4.3 is the first behavior phase after the V4.2 WH scope freeze.

It adds minimal, tested WH behavior to the existing ranked Mapping V4 placement layer.

## Scope

In scope:

- `MAPPING_V4:` / `END_MAPPING_V4:` recognition in the OPN loader.
- `WH` as a known lexical role.
- WH as a lexical-axis item, not as a tree transformation.
- `CLAUSE_MODE: interrogative_wh` as mapping metadata in examples.
- Minimal WH examples:
  - subject-WH: `wie heeft de hond gebeten`
  - object-WH: `wat heeft vrouw gebreid`
  - simple finite-V WH: `wie bijt hond`
- Invalid WH examples for missing V-AUX and ordering cycles.
- V4 checker including V3 core, V4.1 NEG/TIME/PLACE and V4.3 WH examples.

Out of scope:

- DET splitting.
- FRAME.graph integration.
- automatic role inference.
- UI/rendering changes.
- graph mutation.
- transformations / vooropplaatsing.

## Minimal WH rule pattern

WH is represented as a normal lexical item:

```text
x1|wie|role:WH|wh-target:Agens|pos:WH
```

For a split-VP WH question, the stable rule pattern is:

```text
WH|before_clause|core
V-AUX|after|WH|core
Patiens|after|V-AUX|core
Patiens|before|V-PART|core
V-PART|clause_end|core
```

This produces:

```text
wie heeft de hond gebeten
```

## DET boundary

DET is still not implemented. For V4.3, multiword items such as `de hond` remain one lexical item.

## Architecture rule

```text
STRUCTURE = view
MAPPING_V4 = logica
WH = lexical item on the lexical axis
no graph mutation
no tree transformation
output via generator
validation and generation through placement constraints
```
