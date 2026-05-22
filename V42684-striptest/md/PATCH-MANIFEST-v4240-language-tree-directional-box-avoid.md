# PATCH MANIFEST v4.24.0 — Language Tree directional box-avoid recursion

## Purpose

Corrects the remaining right-branch folding in `nary_compact_lr`.  A subtree that already grows to the right must not place its recursive continuation back inside the horizontal box reserved by the earlier child.

## Rule

For a binary node inside an inherited side context:

```text
Root_R avoids the full reserved box of Root_L.
```

Right-side context:

```text
xMin(Root_R) >= xMax(box(Root_L)) + gap
```

Left-side context:

```text
xMax(Root_R) <= xMin(box(Root_L)) - gap
```

The vertical position is then shifted downward until the placement is free.

## Files changed

- `operation/OpenGraphTreeDrawOperation.java`
- `md/CHANGELOG.md`
- `build.bat` and launcher version comments

## Notes

The active strategy remains:

```properties
language.layout.strategy=nary_compact_lr
projection.profile.language.layout.strategy=nary_compact_lr
```

This is intentionally not configurable yet.
