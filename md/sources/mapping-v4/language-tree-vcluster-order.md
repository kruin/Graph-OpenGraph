# Language Tree V-cluster order

## Purpose

Dutch Language Tree can compare two V-cluster orders:

```text
heeft gebeten
gebeten heeft
```

This is handled as a local V-cluster drawing/order choice, not as a projection transformation.

## UI

When `Structure type = Language Tree / Phrase`, the OpenGraph header shows:

```text
V-cluster: [PV-VD] [VD-PV]
```

- `PV-VD` = `heeft gebeten`
- `VD-PV` = `gebeten heeft`

Changing the button redraws from the original `.graph`.

## Implementation rule

For a local V-cluster:

```text
V
├── PV/heeft
└── VD/gebeten
```

OpenGraph draw may order the two child subtrees as:

```text
PV-VD: PV first, VD second
VD-PV: VD first, PV second
```

The source graph is not mutated. The LEX projection mechanism stays:

```text
terminal node -> horizontal projection to LEX axis
```

## Config

```properties
language.verbcluster.order=pv_vd
projection.profile.language.verbcluster.order=pv_vd
```

Allowed values:

```text
pv_vd
vd_pv
```
