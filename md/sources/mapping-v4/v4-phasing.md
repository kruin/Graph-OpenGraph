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
