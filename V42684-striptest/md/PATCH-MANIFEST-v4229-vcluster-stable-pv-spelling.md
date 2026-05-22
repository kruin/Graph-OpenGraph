# v4.22.9 — V-cluster stable redraw + pv spelling

## Fix

Switching the Language Tree V-cluster between `pv-VD` and `VD-pv` no longer shifts the whole tree or adds/removes one left grid column.

## Implementation

The V-cluster draw rule now reserves a stable local bounding box for both orders.  The child order changes, but the parent/root normalization stays invariant.

## UI

User-facing labels use `pv` instead of `pv`:

- `pv-VD` = `heeft gebeten`
- `VD-pv` = `gebeten heeft`

Config keys remain unchanged: `pv_vd` and `vd_pv`.
