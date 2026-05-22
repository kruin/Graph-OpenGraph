# PATCH MANIFEST v4.25.0 — Language Tree n-ary nominal lexical order

Date: 2026-05-17

## Purpose

Fix incorrect flat n-ary nominal projections such as:

```text
kleine de man
```

when the intended Dutch NP/DP terminal order is:

```text
de kleine man
```

## Changes

- Added n-ary nominal child normalization before corridor layout.
- Applies only to terminal-only `NP` / `DP` n-ary projections.
- Stable order:
  1. determiners (`de`, `het`, `een`, etc.)
  2. known adjectives (`kleine`, `grote`, etc.)
  3. noun/rest terminals
- Corridor placement remains a drawing decision after lexical order has been normalized.
- Does not change binary `VP -> NP V` or V-cluster `pv-VD` / `VD-pv` behavior.

## Main file touched

- `operation/OpenGraphTreeDrawOperation.java`

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK
