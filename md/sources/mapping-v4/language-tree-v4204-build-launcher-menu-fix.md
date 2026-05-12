# Language Tree v4.20.4 — build launcher menu fix

This source note records the operational patch after v4.20.3.

The OpenGraph workflow remains as introduced in v4.20.3: Draw, Grid, Toggle Projections and Save OPN are grouped for user-visible workflow coherence.

v4.20.4 fixes packaging/runtime issues:

- `build.bat` uses a local `.build-tmp` folder instead of Windows `%TEMP%`.
- Launchers are direct and do not call each other, avoiding batch recursion.
- The OpenGraph menu descriptor has no embedded comma in the description field, preventing a null menu component during startup.
- Application version displays as `OpenGraphEd.jar: v4.20.4`.
