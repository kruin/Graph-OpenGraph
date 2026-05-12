# PATCH MANIFEST v4.20.4 — build, launcher, menu fix

Date: 2026-05-05
Base: v4.20.3

## Scope

This patch keeps the v4.20.3 OpenGraph action workflow and fixes:

- Windows build failure caused by reliance on `%TEMP%`; `build.bat` now uses local `.build-tmp`.
- Launcher recursion; launchers call Java directly and do not call each other.
- OpenGraph menu parser crash caused by an extra comma in the menu descriptor.
- Version metadata updated to `v4.20.4`.

## Main files touched

- `build.bat`
- `OpenGraphEd.bat`
- `OpenGraphed.bat`
- `Opengraphed.bat`
- `OpenGraphEd-console.bat`
- `run.bat`
- `START-OpenGraphEd.bat`
- `userInterface/OpenGraphEdAppInfo.java`
- `userInterface/menuAndToolBar/MenuAndToolBarControlCatalog.java`

## Notes

`md/sources-md-zip` is intentionally omitted from the clean project zip because it contains duplicate historic source bundles. The normal `md/` tree remains present.
