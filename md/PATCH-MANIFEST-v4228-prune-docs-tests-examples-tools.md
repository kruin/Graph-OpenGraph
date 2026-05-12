# PATCH MANIFEST v4.22.8 — prune docs, regression examples and tools

## Doel

Deze patch ruimt de distributie op. Historische patchlogs, oude expected-output-manifests, oude Mapping V3/V4 regressievoorbeelden en bijbehorende regressietools zijn uit de hoofdzip verwijderd.

## Behouden

- Actuele runtime-code en classes.
- Actuele config.
- Actuele Language Tree voorbeeldgraph: `examples/graph/lextest.graph`.
- Twee handmatige OPN-voorbeelden in `examples/opn/`.
- Actuele documentatie:
  - `md/README.md`
  - `md/INDEX.md`
  - `md/CHANGELOG.md`
  - `md/sources/mapping-v4/current-state.md`
  - `md/sources/mapping-v4/language-tree-zinstype-placement-rules.md`
  - `md/sources/mapping-v4/language-tree-vcluster-order.md`
  - `md/sources/mapping-v4/opengraph-projection-config.md`
  - `md/sources/mapping-v4/opengraph-projection-profiles.md`

## Verwijderd uit distributie

- Oude `md/PATCH-MANIFEST-v*.md` bestanden, behalve deze actuele prune-manifest.
- `md/meta-inf/` historische handoffbestanden.
- `md/examples/opn/` expected-output-manifests.
- Oude `md/sources/mapping-v3/`.
- Oude gefaseerde Mapping V4 bronnotities, vervangen door actuele samenvattende docs.
- Oude `examples/opn/mapping-v1`, `mapping-v2`, `mapping-v3*`, `mapping-v4*` regressiesets.
- Oude regressietools `MappingV3RegressionChecker` en `MappingV4RegressionChecker`.

## Beleid vanaf deze versie

- Nieuwe historische patchinformatie alleen nog in `CHANGELOG.md` en maximaal één actuele patchmanifest.
- Regressievoorbeelden niet meer in de hoofdzip, tenzij ze direct nodig zijn voor de gebruiker.
- Oude testsets alleen bewaren buiten distributie of in een aparte testbundle.
