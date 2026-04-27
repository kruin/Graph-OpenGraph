# Placement Rules V3

## Basis

```text
Agens     -> left_of_V
Patiens   -> right_of_V
RECIPIENT -> before_Theme
THEME     -> before_V_PART
```

## Verb

```text
V-AUX  -> anchor
V-PART -> clause_end
```

## Negatie

```text
NEG -> after_aux_before_object
```

Constraint:

```text
V-AUX < NEG < Patiens
```

## Bijwoorden

```text
TIME  -> before_clause
PLACE -> after_clause
```

## V3-uitbreiding

V3 ondersteunt ranked alternatieven. Het systeem moet de beste output tonen en maximaal enkele alternatieven in Info.
