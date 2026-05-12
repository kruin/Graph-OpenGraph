# Mapping V4.5.1 — Info label clarification

Status:

```text
MAPPING_V4_5_1_INFO_LABEL
```

## Purpose

Clarify the Info-window label for the selected generated output.

The previous display was technically correct but visually ambiguous:

```text
generated: best: vrouw ziet de man
```

The new display is:

```text
generated best: vrouw ziet de man
```

## Meaning

`generated best` is the highest-ranked generated candidate according to the current placement rules.

It does not mean that the output is independently proven semantically complete, linguistically perfect, or derived by graph/tree mutation.

## Scope

Changed:

- Info-window text formatting only.

Not changed:

- Mapping generator.
- Mapping validator.
- Placement rules.
- Checkers.
- OPN format.
- Graph rendering.
- Graph mutation behavior.
- DET / WH behavior.

## Invalid output

Invalid mappings keep the existing wording:

```text
generated: none (invalid mapping)
```
