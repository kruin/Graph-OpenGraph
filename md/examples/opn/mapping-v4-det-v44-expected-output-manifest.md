# Mapping V4.4 expected-output manifest — DET scope

Status: documentation-only expected-output manifest.

This manifest defines target behavior for a later V4.5 implementation. V4.4 does not implement or run these checks yet.

## Directory proposal

Future valid examples:

```text
examples/opn/mapping-v4-det/
```

Future invalid examples:

```text
examples/opn/mapping-v4-det-invalid/
```

## Valid examples

### 01-vrouw-bijt-de-hond.opn

Purpose:

```text
simple DET + Patiens
```

Lexical interpretation:

```text
x1|vrouw|role:Agens
x2|bijt|role:V
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
```

Expected mapping summary:

```text
Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules, DET scope
```

Expected validation:

```text
validation: 3 ok, 0 fail (DET placement rules satisfied)
```

Expected generated:

```text
generated: best: vrouw bijt de hond
```

### 02-vrouw-heeft-de-trui-gebreid.opn

Purpose:

```text
split VP + DET
```

Lexical interpretation:

```text
x1|vrouw|role:Agens
x2|heeft|role:V-AUX
x3|de|role:DET|det-target:Patiens
x4|trui|role:Patiens
x5|gebreid|role:V-PART
```

Expected mapping summary:

```text
Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules, DET scope
```

Expected validation:

```text
validation: 5 ok, 0 fail (DET placement rules satisfied)
```

Expected generated:

```text
generated: best: vrouw heeft de trui gebreid
```

### 03-wie-heeft-de-hond-gebeten.opn

Purpose:

```text
WH + DET
```

Lexical interpretation:

```text
x1|wie|role:WH|wh-target:Agens|pos:WH
x2|heeft|role:V-AUX
x3|de|role:DET|det-target:Patiens
x4|hond|role:Patiens
x5|gebeten|role:V-PART
```

Expected mapping summary:

```text
Mapping v4: 5 lexical items, 1 verb domains, 6 placement rules, WH + DET scope
```

Expected validation:

```text
validation: 6 ok, 0 fail (WH and DET placement rules satisfied)
```

Expected generated:

```text
generated: best: wie heeft de hond gebeten
```

## Invalid examples

### 01-det-missing-target.opn

Expected validation starts with:

```text
validation: ... 1 fail
```

Expected details contain:

```text
file: 01-det-missing-target.opn
missing det-target for role DET at x3
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 02-det-unknown-target.opn

Expected validation starts with:

```text
validation: ... 1 fail
```

Expected details contain:

```text
file: 02-det-unknown-target.opn
unknown det-target LOC for role DET at x3
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 03-det-target-absent.opn

Expected validation starts with:

```text
validation: ... 1 fail
```

Expected details contain:

```text
file: 03-det-target-absent.opn
missing lexical target Patiens for DET at x3
```

Expected generated:

```text
generated: none (invalid mapping)
```

### 04-det-ordering-cycle.opn

Expected validation starts with:

```text
validation: ... 1 fail
```

Expected details contain:

```text
file: 04-det-ordering-cycle.opn
ordering cycle in best placement rules
```

Expected generated:

```text
generated: none (invalid mapping)
```

## Pass condition for future V4.5

When implemented in V4.5, the checker should include the unchanged V3 core, V4.1 NEG/TIME/PLACE, V4.3 WH and these DET cases.

Proposed future result:

```text
Mapping V4.5 DET regression checker: pass
```

Exact pass/fail counts should be fixed only when the example files and checker cases are added.
