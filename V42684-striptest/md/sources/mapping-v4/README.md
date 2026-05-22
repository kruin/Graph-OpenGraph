# Mapping V4

Mapping V4 starts from the stable Mapping V3 core checkpoint.

## Start status

V4.0 is a scope-freeze and test-basis phase.

No Java code, runtime behavior, generator logic, validator logic, UI behavior, rendering behavior, `.opn` parsing behavior, or checker logic is changed in this phase.

## Base checkpoint

Base package:

```text
Projectie-26-04-27-mapping-v310-core-stable-checkpoint.zip
```

Base status:

```text
MAPPING_V3_CORE_STABLE
```

Base regression:

```text
Mapping V3 regression: 13 pass, 0 fail
MD folder check: PASS
```

## V4 rule

Every V4 phase must preserve the V3 core unless the phase explicitly states otherwise.

At the end of each phase, the following must remain true:

```text
Mapping V3 regression: pass
MD folder check: PASS
all .md files under md/
```

## Packaging rule

Every project zip must include an md-only sources zip containing all Markdown files included in the package, preserving their relative paths.

The md-only zip is intended for manual upload to Project Sources and for source-state verification.

Current package location:

```text
md/sources-md-zip/
```
