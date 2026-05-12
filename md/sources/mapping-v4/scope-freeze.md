# Mapping V4.0 Scope Freeze

## Status

```text
MAPPING_V4_SCOPE_FREEZE
```

## Meaning

V4.0 is not a behavior phase.

It only records the start of V4 and preserves the V3.10 core stable checkpoint as the baseline for future work.

## Allowed in V4.0

- Add Markdown documentation.
- Update Markdown index and changelog.
- Add a V4 meta manifest.
- Add the md-only sources zip to the project package.
- Verify that no `.md` files exist outside `md/`.
- Verify the unchanged V3 regression checker status.

## Forbidden in V4.0

- Java edits
- class rebuilds
- jar rebuilds
- generator edits
- validator edits
- parser edits
- UI edits
- renderer edits
- new example semantics
- changing the V3 expected-output baseline

## Reason

The previous checkpoint declared Mapping V3 core stable. V4 must start by protecting that stable state before adding new semantic scope.
