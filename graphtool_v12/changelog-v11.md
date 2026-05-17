# Changelog v11 — OPN support

Project: Mapping V4 / graph tools
Date: 2026-05-17

## Added

- `.opn` and `.OPN` support alongside `.graph`.
- Python renderer now scans both `.graph` and `.opn` in current-directory and directory modes.
- Graph Viewer file-open dialog now includes `.graph`, `.opn`, `.GRAPH`, and `.OPN`.
- Total Commander F3 wrappers route `.opn` to the rendered viewer/Lister path.
- Render batch and preview batch accept `.opn` files and TC `%L` selections containing `.opn` files.
- Windows Open/double-click association can register both `.graph` and `.opn`.
- Windows Explorer Preview Pane handler registration now covers both `.graph` and `.opn`.
- Native Windows thumbnail-provider registration now covers both `.graph` and `.opn`.
- Example `.opn` files were added by copying the existing example `.graph` files.

## Compatibility note

The `.opn` support uses the same parser as `.graph`. This is intended for OPN files with the same OpenGraph/Mapping line-based graph format.
