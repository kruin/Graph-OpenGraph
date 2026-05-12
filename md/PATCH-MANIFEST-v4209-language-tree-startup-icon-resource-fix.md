# PATCH MANIFEST — v4.20.9 Language Tree startup icon resource fix

Date: 2026-05-12
Base: v4.20.8

## Scope

Fixes the startup crash reported as:

```text
Exception in thread "main" java.lang.NullPointerException:
Cannot invoke "java.net.URL.toExternalForm()" because "location" is null
  at javax.swing.ImageIcon.<init>(ImageIcon.java:232)
  at userInterface.menuAndToolBar.MenuAndToolBarBuildSupport.createMenuAndToolbarAction
```

## Cause

`run.bat` prefers the precompiled `out\` directory. The v4.20.8 zip contained root-level `images\`, but the precompiled `out\` tree did not include `out\images`. Classpath resource lookup therefore returned null for toolbar icons.

## Changes

- `MenuAndToolBarBuildSupport` now loads icons through a guarded helper.
- If classpath lookup fails, it falls back to root-level file lookup such as `images\New.gif`.
- If the icon is still missing, startup continues with a null icon instead of throwing.
- `out\images`, `out\help`, and `out\config` are included.
- Version metadata set to `v4.20.9`.
