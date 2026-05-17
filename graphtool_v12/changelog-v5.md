# Changelog v5

Datum: 2026-05-15
Project: Mapping V4 / Graph tools

Toegevoegd:

- Windows Explorer Preview Pane handler voor `.graph`.
- C#/.NET Framework COM preview handler in `windows_shell_preview/src/GraphShellExtension.cs`.
- Per-user installer via HKCU, normaal zonder admin.
- Buildscript met `csc.exe` uit .NET Framework 4.x.
- Uninstallscript.
- Registratiecontrole-script.

Behouden uit v4:

- Python/Tkinter Graph Viewer.
- PNG/SVG-renderbatch.
- Total Commander F3-wrapper.
- Total Commander Lister-PNG-wrapper.
- Preview sidecar-generator.
- Voorbeelden `space3_eerste_vrije_plek.graph` en `spiral.graph`.

Niet toegevoegd:

- Explorer thumbnail provider. Reden: managed thumbnail providers in Explorer zijn minder wenselijk; later native C++ bouwen als thumbnails echt nodig zijn.
