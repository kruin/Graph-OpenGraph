# PATCH MANIFEST v4.20.6 — Language Tree slot click guard

Touched files:

- `userInterface/OpenGraphProjectionSupport.java`
  - adds virtual slot hit testing, stable structure-root anchoring, and visible LANGUAGE TREE caption.
- `userInterface/modes/EditListener.java`
  - consumes clicks on virtual Language Tree slots before normal node creation/selection.
- `userInterface/GraphFileActions.java`
  - native `.graph` files with `LANGUAGE_TREE`, `LANGUAGE TREE`, or `LEXTEST` in label/name open with Language Tree projection defaults.
- `userInterface/OpenGraphEdAppInfo.java`
  - version set to `v4.20.6`.
- `examples/graph/lextest.graph`
  - native language-tree test file.
- `md/sources/mapping-v4/language-tree-zinstype-placement-rules.md`
  - placement-rule alternatives for the next phase.
