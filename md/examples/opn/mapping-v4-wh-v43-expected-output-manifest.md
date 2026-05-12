# Mapping V4.3 expected-output manifest — minimal WH

## Valid examples

Directory:

```text
examples/opn/mapping-v4-wh/
```

Expected:

```text
01-wie-heeft-de-hond-gebeten.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: wie heeft de hond gebeten

02-wat-heeft-vrouw-gebreid.opn
expected mapping: Mapping v4: 4 lexical items, 1 verb domains, 5 placement rules
expected validation: validation: 5 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: wat heeft vrouw gebreid

03-wie-bijt-hond.opn
expected mapping: Mapping v4: 3 lexical items, 1 verb domains, 3 placement rules
expected validation: validation: 3 ok, 0 fail (best placement rules satisfied)
expected generated: generated: best: wie bijt hond
```

## Invalid examples

Directory:

```text
examples/opn/mapping-v4-wh-invalid/
```

Expected:

```text
01-wh-missing-vaux.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 01-wh-missing-vaux.opn
expected details contain: missing role for rule V-AUX after WH
expected generated: generated: none (invalid mapping)

02-wh-ordering-cycle.opn
expected validation starts with: validation: 3 ok, 1 fail
expected details contain: file: 02-wh-ordering-cycle.opn
expected details contain: ordering cycle in best placement rules
expected generated: generated: none (invalid mapping)
```

## Pass condition

```text
Mapping V4.3 regression checker: 24 pass, 0 fail
```

The checker total consists of:

- V3 core valid + invalid: 13 checks
- V4.1 NEG/TIME/PLACE valid + invalid: 6 checks
- V4.3 WH valid + invalid: 5 checks
