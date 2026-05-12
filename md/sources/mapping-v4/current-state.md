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
