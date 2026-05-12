# Mapping V4.15 expected-output manifest — morphology metadata validator target

Status:

```text
documentation-only target manifest
runtime remains V4.12
```

## Purpose

This manifest defines target output for the next Java behavior slice that may implement morphology metadata validation.

V4.15 itself does not add runtime examples or checker cases.

## Future valid examples

Future directory:

```text
examples/opn/mapping-v4-morphology/
```

### 01-morphology-bijten-present-3sg.opn

Purpose:

```text
valid finite verb morphology metadata
```

Lexicon target:

```text
lex|bijten|lemma:bijten|form:bijt|role:V|pos:V|tense:present|number:sg|person:3|finite:true
```

Expected future morphology summary:

```text
morphology validation: 1 ok, 0 fail
```

Generated best remains derived from `MAPPING_V4` placement rules.

### 02-morphology-nominal-number-gender.opn

Purpose:

```text
valid nominal morphology metadata
```

Lexicon target:

```text
lex|hond|lemma:hond|form:hond|role:Patiens|pos:N|number:sg|gender:common
```

Expected future morphology summary:

```text
morphology validation: 1 ok, 0 fail
```

### 03-morphology-det-number-gender.opn

Purpose:

```text
valid determiner morphology metadata
```

Lexicon target:

```text
lex|de|lemma:de|form:de|role:DET|pos:DET|number:sg|gender:common
```

Expected future morphology summary:

```text
morphology validation: 1 ok, 0 fail
```

## Future invalid examples

Future directory:

```text
examples/opn/mapping-v4-morphology-invalid/
```

These examples should keep Mapping V4 placement validation valid. Morphology diagnostics should be reported separately, and generated output should not be suppressed.

### 01-morphology-unknown-feature.opn

Expected future diagnostic:

```text
unknown morphology feature degree at lexicon entry bijten
```

Expected future morphology summary:

```text
morphology validation: 0 ok, 1 fail
```

### 02-morphology-missing-value.opn

Expected future diagnostic:

```text
missing morphology feature value for tense at lexicon entry bijten
```

Expected future morphology summary:

```text
morphology validation: 0 ok, 1 fail
```

### 03-morphology-duplicate-feature.opn

Expected future diagnostic:

```text
duplicate morphology feature number at lexicon entry hond
```

Expected future morphology summary:

```text
morphology validation: 0 ok, 1 fail
```

### 04-morphology-incompatible-pos.opn

Expected future diagnostic:

```text
morphology feature tense incompatible with pos:N at lexicon entry hond
```

Expected future morphology summary:

```text
morphology validation: 0 ok, 1 fail
```

### 05-morphology-unknown-value.opn

Expected future diagnostic:

```text
unknown morphology feature value dual for number at lexicon entry hond
```

Expected future morphology summary:

```text
morphology validation: 0 ok, 1 fail
```

## Future pass condition

Exact pass/fail counts should be fixed only when the Java behavior, example files and checker cases are added.

Proposed future result:

```text
Mapping V4.16 morphology metadata validator regression checker: pass
```

## Current V4.15 pass condition

Because V4.15 is documentation-only, the actual checks remain:

```text
Mapping V4.12 lexicon validation regression checker: 45 pass, 0 fail
Mapping V3 regression checker: 13 pass, 0 fail
MD folder check: PASS
```
