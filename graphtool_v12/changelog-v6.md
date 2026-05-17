# Changelog v6 — native Windows thumbnails

## Toegevoegd

- `windows_shell_thumbnail/`
  - native C++ `.graph` thumbnail provider;
  - `IThumbnailProvider` + `IInitializeWithStream`;
  - tolerant parser voor `space3` en `spiral`-achtige `.graph`- en `.opn`-bestanden;
  - GDI+ renderer naar `HBITMAP`;
  - user-level COM-registratie via `HKCU`;
  - buildscript voor Visual Studio Build Tools / MSVC x64;
  - cache-wis-script voor Explorer thumbnails.

- Hoofdmap wrappers:
  - `install_windows_thumbnail_provider.bat`
  - `uninstall_windows_thumbnail_provider.bat`

## Behouden uit v5

- Python viewer;
- SVG/PNG-renderbatch;
- Total Commander F3-routes;
- Windows Explorer Preview Pane-handler;
- voorbeelden en sidecar previews.

## Niet lokaal uitvoerbaar in deze omgeving

De native Shell Extension kan alleen op Windows met MSVC worden gebouwd en in Explorer worden getest. De broncode en registry/install-scripts zijn hier wel statisch gecontroleerd en in één ZIP verpakt.
