# Mapping V4.5 expected-output manifest — minimal DET

## Valid examples

Directory:

```text
examples/opn/mapping-v4-det/
```

Expected:

```text
01-vrouw-bijt-de-hond.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw bijt de hond

02-vrouw-heeft-de-trui-gebreid.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: vrouw heeft de trui gebreid

03-wie-heeft-de-hond-gebeten.opn
expected mapping: Mapping v4: 5 lexical items, 1 verb domains, 6 placement rules
expected validation: validation: 6 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: wie heeft de hond gebeten
```

## Invalid examples

Directory:

```text
examples/opn/mapping-v4-det-invalid/
```

Expected:

```text
01-det-missing-target.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 01-det-missing-target.opn
expected details contain: missing det-target for role DET at x3
expected generated: generated: none (invalid mapping)

02-det-unknown-target.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 02-det-unknown-target.opn
expected details contain: unknown det-target LOC for role DET at x3
expected generated: generated: none (invalid mapping)

03-det-target-absent.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 03-det-target-absent.opn
expected details contain: missing lexical target Patiens for DET at x3
expected generated: generated: none (invalid mapping)

04-det-ordering-cycle.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 04-det-ordering-cycle.opn
expected details contain: ordering cycle in best placement rules
expected generated: generated: none (invalid mapping)
```

## Pass condition

```text
Mapping V4.5 DET regression checker: 31 pass, 0 fail
```
