# PATCH MANIFEST — v4.21.0 Language Tree tooltip + Phrase default

## Changed
- Zinstype hover tooltips are shown immediately (`ToolTipManager` initial/reshow delay set to 0).
- OpenGraph structure default is now `Language Tree / Phrase` instead of `Frame`.
- Dialog label changed from ambiguous `Phrase` to `Language Tree / Phrase`.
- Frame label changed to `Frame (roles/functions)` to avoid confusion with Language Tree work.
- Config defaults and user settings now use `structure.type=2`.

## Notes
- Use `Language Tree / Phrase` for DS + lexical-axis zinstype work.
- Use `Frame (roles/functions)` for later functional-role frames, not for the current Language Tree zinstype UI.

## Version
- Version metadata set to `v4.21.0`.
