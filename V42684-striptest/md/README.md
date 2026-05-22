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

- `PATCH-MANIFEST-v4253-role-detection-diagnostics.md` — role detection diagnostics for future FG role-box layout.
