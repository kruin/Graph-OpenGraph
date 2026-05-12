# PATCH MANIFEST v4.22.5 — Language Tree V-cluster settings UI

Adds the V-cluster order to the OpenGraphDraw settings UI, not only the header.

## UI

For `Language Tree / Phrase`, OpenGraphDraw settings now includes a `Language Tree` tab:

```text
V-cluster / werkwoordelijke eindgroep
[PV-VD   heeft gebeten]
[VD-PV   gebeten heeft]
```

The existing header controls remain.

## Behavior

- `PV-VD`: PV first/short, VD second/long.
- `VD-PV`: VD first/short, PV second/long.
- Projection logic remains unchanged.

## Config

The dialog saves the order for Language Tree as:

```properties
language.verbcluster.order=pv_vd
projection.profile.language.verbcluster.order=pv_vd
```
