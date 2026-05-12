# 2026-05-12 — Mapping V4.20.8 Language Tree zinstype hover + selection

## Scope

Refines the Language Tree zinstype UI introduced in v4.20.7.

## Changes

- `Zinstype` buttons are now persistent toggle buttons.
  - The chosen profile remains visually marked after clicking.
  - The button state is refreshed when a Language Tree file applies projection defaults.
- Detailed placement-rule preview moved from the drawing overlay to button hover tooltips.
  - The overlay now stays compact: `LANGUAGE TREE` plus active `Zinstype`.
  - This avoids preview text disappearing behind the DS tree.
- `Topicalisatie` reads the standard selected graph node.
  - If one node is selected, the preview records it as the topic candidate.
  - For categorial nodes with terminal descendants, the preview is grouped, e.g. `NP(de man)`.
  - This does not yet perform physical lexical-axis placement; it sets the profile and preview only.

## Design decision

Language Tree displacement rules should attach to categorial nodes (`NP`, `DP`, `VP`, etc.), not to individual lexical leaves.  Lexical leaves under that category are treated as a phrase label for the lexical axis.  For example, selecting `NP` with descendants `de` and `man` yields `NP(de man)` for the topicalisation preview.

## Tests

- `tools.LanguageTreeRegressionChecker`: `3 pass, 0 fail`.
- `tools.MappingV4RegressionChecker`: `53 pass, 0 fail`.
- `tools.MappingV3RegressionChecker`: `13 pass, 0 fail`.
- `tools.CheckMdFolder`: pass.
- `javac`: OK, warnings only.
