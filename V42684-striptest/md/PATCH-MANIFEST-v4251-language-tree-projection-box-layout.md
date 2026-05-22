# PATCH MANIFEST v4.25.1 — Language Tree projection-box layout

## Purpose

Projecties blijven leidend.  De n-ary box-layout is nu expliciet een
projectiegestuurde plaatsingslaag: projectievolgorde eerst, daarna pas vrije
box/corridorplaatsing.

## Changed

- Added `projection_box` as active Language Tree layout strategy.
- Added config keys:
  - `language.layout.nary.mode=projection_box`
  - `language.layout.nary.localShapeFirst=true`
  - `language.layout.nary.preserveProjectionOrder=true`
- n-ary nodes now use `orderNaryChildrenByProjection(...)` before corridor placement.
- n-ary local shape no longer inherits the surrounding left/right context as a start-side decision.
- Higher context may still shift the complete subtree box, but must not deform the internal n-ary projection shape.
- Existing `nary_compact_lr` remains accepted as an alias/fallback.

## Kept

- nominal NP/DP lexical normalization from v4.25.0
- unary-aware subtree boxes
- terminal-aware vertical stacking
- direct n-ary placement for 3, 4, 5, ... children
- structural `VP -> NP V`
- toolbar-controlled `pv-VD` / `VD-pv`
- no RR/LL cascade

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK
- Zip integrity: OK
