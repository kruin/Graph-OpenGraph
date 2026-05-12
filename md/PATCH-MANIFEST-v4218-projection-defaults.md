# v4.21.8 — Projection defaults restored

Restores OpenGraph projection defaults:

- Left: `LEX`
- Right: `SYNT`
- Bottom: `LF`
- Top: `pm`

All projection sides are enabled by default for projection-capable structures.
Simple tree remains projection-free.

Notes:

- `SYNT` replaces the earlier `SYN` side caption.
- `pm` is rendered as the top projection caption.
- The projection captions are displayed when the side is enabled, even if that side has no concrete projected items yet.
