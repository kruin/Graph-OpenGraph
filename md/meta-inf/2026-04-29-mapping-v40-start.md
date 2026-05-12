# Mapping V4.0 start

Status: **MAPPING_V4_SCOPE_FREEZE**

Date: 2026-04-29

## Base

This phase starts from:

```text
Projectie-26-04-27-mapping-v310-core-stable-checkpoint.zip
```

Base status:

```text
MAPPING_V3_CORE_STABLE
```

## Goal

Create the V4 documentation and phase baseline without changing runtime behavior.

## Added

- `md/sources/mapping-v4/README.md`
- `md/sources/mapping-v4/current-state.md`
- `md/sources/mapping-v4/v4-phasing.md`
- `md/sources/mapping-v4/scope-freeze.md`
- `md/sources-md-zip/README.md`
- embedded md-only sources zip under `md/sources-md-zip/`

## Updated

- `md/README.md`
- `md/INDEX.md`
- `md/CHANGELOG.md`

## Packaging rule

Every project zip must include an md-only sources zip containing all Markdown files included in the package, preserving their relative paths.

The md-only zip is meant for manual upload to Project Sources and for source-state verification.

## Scope

Documentation/package-only phase.

No Java source, class file, jar, generator, validator, parser, UI, rendering, or example semantics were changed.

## Expected checks

```text
Mapping V3 regression: 13 pass, 0 fail
MD folder check: PASS
```

## Next proposed phase

```text
V4.1 — NEG / TIME / PLACE
```

V4.1 should be a behavior phase only after V4.0 is accepted.

## Actual checks

Run after V4.0 packaging:

```text
Mapping V3 regression checker: summary: 13 pass, 0 fail
MD folder check: PASS
```
