# PATCH MANIFEST — v4.21.1 Language Tree direct zinstype draw

## Changes

- Zinstype buttons execute OpenGraph Draw/Redraw directly.
- The editor-window Draw button is removed.
- `Structure type` is shown directly in the editor window.
- Structure type changes redraw immediately.
- Zinstype buttons set `Language Tree / Phrase` automatically and redraw.

## Notes

- Dialog-based OpenGraph Draw remains in code for compatibility, but the normal Language Tree workflow no longer requires opening it.
- Physical LEX-axis placement is still preview-level; this patch is workflow/UX.
