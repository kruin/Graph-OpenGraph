# PATCH MANIFEST — v4.20.8 Language Tree zinstype hover selection

## Modified

- `userInterface/GraphEditorWindow.java`
  - zinstype buttons changed to persistent toggle buttons.
  - hover tooltips contain detailed placement-rule previews.
- `userInterface/OpenGraphActions.java`
  - `Topicalisatie` reads current standard node selection and builds a topic preview.
  - categorial node lexical descendants are grouped, e.g. `NP(de man)`.
- `userInterface/OpenGraphProjectionSettings.java`
  - stores topicalisation preview text.
- `userInterface/OpenGraphProjectionSupport.java`
  - overlay preview made compact and moved above the structure grid.
  - detailed rules are no longer drawn over the DS tree.
- `userInterface/GraphFileActions.java`
  - refreshes zinstype button state when Language Tree defaults are applied.
- `userInterface/OpenGraphEdAppInfo.java`, `manifest.mf`, `META-INF/MANIFEST.MF`, launchers/build metadata
  - version set to `v4.20.8`.

## Tests

- `tools.LanguageTreeRegressionChecker`: `3 pass, 0 fail`.
- `tools.MappingV4RegressionChecker`: `53 pass, 0 fail`.
- `tools.MappingV3RegressionChecker`: `13 pass, 0 fail`.
- `tools.CheckMdFolder`: pass.
- `javac`: OK, warnings only.
