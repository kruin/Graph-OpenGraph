# Mapping V4.11 — Lexicon validation expected-output manifest

Status:

```text
documentation-only target manifest
runtime remains V4.10.1
```

## Purpose

This manifest reserves the expected target behavior for the next lexicon validation implementation phase.

## Current V4.11 behavior

No runtime behavior changes are made in V4.11.

Current valid lexicon examples continue to use the V4.10 metadata-only expectation:

```text
Lexicon: <n> entries, metadata only
```

## Target valid behavior for later implementation

Candidate valid summary:

```text
Lexicon: <n> entries; lexicon validation: <ok> ok, 0 fail
```

Valid cases should not change generated output.

## Target invalid behavior for later implementation

Candidate invalid examples:

```text
examples/opn/mapping-v4-lexicon-invalid/01-lexicon-malformed-row.opn
examples/opn/mapping-v4-lexicon-invalid/02-lexicon-missing-key.opn
examples/opn/mapping-v4-lexicon-invalid/03-lexicon-duplicate-key.opn
examples/opn/mapping-v4-lexicon-invalid/04-lexicon-unknown-role.opn
examples/opn/mapping-v4-lexicon-invalid/05-lexicon-role-not-in-mapping.opn
examples/opn/mapping-v4-lexicon-invalid/06-lexicon-frame-not-in-frame-graph.opn
```

Diagnostics should remain informational. Generated output should not be suppressed by lexicon validation failures in the first validator phase.

## Out of scope

```text
generated-output changes
role inference
automatic lexical insertion
automatic frame selection
morphology / inflection
UI rendering of lexicon entries
graph mutation
```
