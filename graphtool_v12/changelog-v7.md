# Changelog v7 — thumbnail buildfix

- `windows_shell_thumbnail/build_thumbnail_provider_x64.bat` herbouwd.
- Build gebruikt nu een `cl.exe` response-file in plaats van kwetsbare multiline commandoregel.
- Expliciete controle toegevoegd op aanwezigheid van `GraphThumbnailProvider.cpp` en `.def`.
- Debug-buildbatch toegevoegd: `build_thumbnail_provider_x64_debug.bat`.
- Doel: oplossen van `cl : Command line error D8003 : missing source filename`.
