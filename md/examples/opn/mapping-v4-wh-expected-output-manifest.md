# Mapping V4.2 WH expected-output manifest

Status: documentation-only expected-output manifest.

This manifest defines the target behavior for a later V4.3 implementation. V4.2 does not implement or run these checks yet.

## Directory proposal

Future valid examples:

```text
examples/opn/mapping-v4-wh/
```

Future invalid examples:

```text
examples/opn/mapping-v4-wh-invalid/
```

## Valid examples

### 01-subject-wh-vchain.opn

Purpose:

```text
subject-WH
```

Lexical interpretation:

```text
x1|wie|role:WH|wh_target:Agens
x2|heeft|role:V-AUX
x3|de hond|role:Patiens
x4|gebeten|role:V-PART
```

Expected mapping summary:

```text
Mapping v4: 4 lexical items, 1 verb domains, WH clause mode
```

Expected validation:

```text
validation: 4 ok, 0 fail (WH placement rules satisfied)
```

Expected generated:

```text
generated: best: wie heeft de hond gebeten
```

### 02-object-wh-vchain.opn

Purpose:

```text
object-WH
```

Lexical interpretation:

```text
x1|wat|role:WH|wh_target:Patiens
x2|vrouw|role:Agens
x3|heeft|role:V-AUX
x4|gebreid|role:V-PART
```

Expected mapping summary:

```text
Mapping v4: 4 lexical items, 1 verb domains, WH clause mode
```

Expected validation:

```text
validation: 4 ok, 0 fail (WH placement rules satisfied)
```

Expected generated:

```text
generated: best: wat heeft vrouw gebreid
```

### 03-subject-wh-with-neg.opn

Purpose:

```text
WH combined with existing NEG placement
```

Lexical interpretation:

```text
x1|wie|role:WH|wh_target:Agens
x2|heeft|role:V-AUX
x3|niet|role:NEG
x4|de hond|role:Patiens
x5|gebeten|role:V-PART
```

Expected mapping summary:

```text
Mapping v4: 5 lexical items, 1 verb domains, WH clause mode
```

Expected validation:

```text
validation: 5 ok, 0 fail (WH and NEG placement rules satisfied)
```

Expected generated:

```text
generated: best: wie heeft niet de hond gebeten
```

## Invalid examples

### 01-wh-missing-target.opn

Expected validation starts with:

```text
validation: 2 ok, 1 fail
```

Expected details contain:

```text
file: 01-wh-missing-target.opn
missing wh_target for role WH at x1
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 02-wh-unknown-target.opn

Expected validation starts with:

```text
validation: 2 ok, 1 fail
```

Expected details contain:

```text
file: 02-wh-unknown-target.opn
unknown wh_target LOC for role WH at x1
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 03-duplicate-wh.opn

Expected validation starts with:

```text
validation: 3 ok, 1 fail
```

Expected details contain:

```text
file: 03-duplicate-wh.opn
duplicate WH lexical item
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 04-wh-missing-vaux.opn

Expected validation starts with:

```text
validation: 2 ok, 1 fail
```

Expected details contain:

```text
file: 04-wh-missing-vaux.opn
missing V-AUX for WH clause mode
```

Expected generated:

```text
generated: none (invalid mapping)
```

## Pass condition for future V4.3

When implemented in V4.3, the checker should include the unchanged V3 and V4.1 baselines plus these WH cases.

Proposed future result:

```text
Mapping V4.3 WH regression checker: pass
```

Exact pass/fail counts should be fixed only when the example files and checker cases are added.
