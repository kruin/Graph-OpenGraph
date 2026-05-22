
## v4.26.3 — FT role-box adjunct-stack refinement

- Functional Tree down-stack roles now default to `adjunct_stack` instead of the wide edge-cone lane.
- `instrument`, `locatief`, and `tijd` are anchored near the predicate spine with small x-offsets and vertical gaps.
- Added properties: `functional.layout.downStack.mode`, `functional.layout.downStack.anchor`, `functional.layout.downStack.xOffset`, `functional.layout.downStack.verticalGap`.
- Default debug output is disabled again.
- LT remains `projection_box`; FT remains `role_box`.

# v4.26.2 — FT role-box edge-cone clearance

- Down-stack roles krijgen nu een echte edge-corridor, niet alleen node-clearance.
- `functional.layout.downStack.minClearanceX=8`.
- Nieuwe property: `functional.layout.downStack.edgeConeClearance=3`.
- Debug trace toont beide waarden.
- LT blijft ongewijzigd (`projection_box`).

# v4.26.1 — FT role-box debug trace + down-stack clearance

- Added detailed FT role-box debug trace, temporarily enabled by user config.
- Increased predicate/down-stack diagonal clearance.
- Down-stack roles no longer start near the root/predicate spine.
- LT remains projection_box; FT remains role_box.

# v4.26.0 — FT role-box spine clearance

- Functional Tree role_box: center/pred roles no longer align exactly below the root.
- Down-stack roles (instrument/locatief/tijd) no longer use x-offset 0.
- Fixes vertical unfree columns such as CLAUSE → pred → locatief.
- LT remains projection_box; no intended LT layout change.

# v4.25.9 — FT role-box tiered stacking

- Functional Tree role-box gebruikt nu vaste visuele tiers in plaats van een vlakke fan-out.
- `pred` blijft op de functionele spine; `agens` links; `patiens/recipiens` rechts; `instrument/locatief/tijd` onder elkaar in down-stack.
- Down-stack start onder de volledige reeds geplaatste role-box, met extra vrije rij.
- LT blijft `projection_box`.


## v4.25.7 — FT role-box visual refinement

- Functional Tree role-box gebruikt nu aparte frontiers per visuele rolrichting.
- `pred` blijft centraal; `agens` links; `patiens/recipiens` rechts; `instrument/locatief/tijd` in down-stack.
- Role-rank blijft leidend; boxvalidatie verschuift alleen complete subtrees.
- LT blijft `projection_box`; geen bedoelde wijziging in Language Tree.

# v4.25.5 — Functional Tree first role-box layout

## Fixed / changed

- Eerste dedicated FT n-ary role-box combiner geactiveerd voor `role_box`.
- `pred`, `agens`, `patiens`, `recipiens`, `instrument`, `locatief`, `tijd` worden op rolrank en voorkeurscorridor geplaatst.
- Elke rol wordt als complete local subtree-box verschoven; rolvolgorde blijft leidend.
- `examples/ft-test-geven-jan-boek-marie-gisteren.graph` toegevoegd.
- LT blijft `projection_box`; bestaande LT-testgraphs zijn niet bedoeld gewijzigd.

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK

# v4.25.4 — Functional Tree type skeleton

- Added FT = Functional Tree as separate structure type.
- Added OpenGraphDraw radio button and header combo entry for FT.
- FT uses the functional projection profile and `role_box` internally.
- LT remains `projection_box`.
- Role diagnostics remain active.


## v4.25.3 — Role detection diagnostics

- Added FG role detection diagnostics for future role-box layout.
- Writes recognized roles to the OpenGraph operation log and to console output when roles are present.
- Keeps `projection_box` as the default layout.
- No intended visual change for standard Language Tree graphs.

# v4.25.2 — Role-box preparation for Functional Grammar

## Fixed / changed

- Added internal role descriptors for future FG n-ary projection boxes: `LayoutRole`, `PreferredSide`, `CorridorPolicy`.
- Added hidden `role_box` layout alias for later/manual experiments; default remains `projection_box`.
- Added initial built-in FG role ranks: pred, agens, patiens, recipiens, instrument, locatief, tijd.
- No GUI setting added. No default Language Tree behavior should change.

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK

# v4.25.1 — Language Tree projection-box layout

## Fixed / changed

- Projectievolgorde wordt eerst bepaald; box/corridorplaatsing komt daarna.
- `projection_box` is de actieve Language Tree layoutstrategie.
- n-ary lokale vorm wordt niet meer vervormd door geërfde links/rechts-context.
- Externe context mag de complete subtree-box verschuiven, maar niet de interne projectievorm veranderen.
- `nary_compact_lr` blijft als alias/fallback herkend.

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK

# v4.24.9 — Language Tree n-ary terminal diagonal clearance

## v4.25.0 — Language Tree n-ary nominal lexical order

- Normalizes terminal-only n-ary NP/DP projections before corridor placement.
- Fixes incorrect nominal order such as `kleine de man` to `de kleine man`.
- Keeps n-ary layout direct, with corridor placement after lexical order normalization.
- Compile and dry-run checks passed.


## Fixed

- n-ary lexical child edges such as `NP-de` and `NP-grote` no longer start in the first adjacent column from the parent.
- Terminal children get one extra horizontal clearance column before same-side corridor numbering is applied.
- This keeps n-ary child edges diagonally free while retaining source-order vertical stacking.

## Kept

- direct n-ary placement for 3, 4, 5, ... children
- unary-aware subtree boxes
- terminal-aware stacking
- structural `VP -> NP V`
- toolbar-controlled `pv-VD` / `VD-pv`
- no RR/LL cascade

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK

# v4.24.8 — Language Tree n-ary same-side terminal corridor fix

## Fixed

- n-ary fan-out no longer places repeated same-side lexical children in the same terminal corridor.
- Examples: `de` / `hond` and `kleine` / `man` are now separated horizontally while retaining source-order vertical stacking.
- Same-side n-ary children are widened as `L1, R1, L2, R2, ...`.

## Kept

- unary-aware subtree boxes
- terminal-aware vertical stacking
- direct n-ary placement for 3, 4, 5, ... children
- structural `VP -> NP V`
- toolbar-controlled `pv-VD` / `VD-pv`
- no RR/LL cascade

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK