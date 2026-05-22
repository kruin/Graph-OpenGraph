# PATCH MANIFEST v4.25.2 — Role-box preparation for Functional Grammar

## Purpose

Prepare the Language Tree / projection-box layout for future Functional Grammar n-ary structures without adding a GUI setting yet.

Main invariant remains:

```text
projectie bepaalt structuur en volgorde
box-layout bepaalt alleen vrije plaatsing
```

## Added internally

New internal descriptors in `graphStructure`:

- `LayoutRole`
- `PreferredSide`
- `CorridorPolicy`

These support a future FG-oriented role-box layout with fields such as:

```text
role name
rank
preferred side
corridor policy
```

## Hidden/experimental layout alias

The layout strategy normalizer now recognizes:

```properties
language.layout.strategy=role_box
language.layout.nary.mode=role_box
```

This is not enabled by default and not exposed in the UI.

## Built-in default role ranks

Initial internal role order:

```text
pred        rank 0   center
a(g)ens     rank 10  left
patiens     rank 20  right
recipiens   rank 30  right
instrument  rank 40  down
locatief    rank 50  down
tijd        rank 60  down
unknown     rank 1000
```

Accepted synonyms include:

```text
agens/agent/actor
patiens/patient/theme/thema
recipiens/recipient/beneficiens/beneficiary
locatief/locative/plaats/location
tijd/time/tempus
```

## Important limitation

v4.25.2 is preparation, not the final FG renderer:

- default remains `projection_box`;
- no GUI control is added;
- role-box uses the existing n-ary corridor engine when manually activated;
- dedicated FG rendering, editable role profiles, and projectie-specific UI config come later.

## Expected behavior

The v4.25.1 test graphs should remain unchanged. The role-box additions should not affect default Language Tree drawings.
