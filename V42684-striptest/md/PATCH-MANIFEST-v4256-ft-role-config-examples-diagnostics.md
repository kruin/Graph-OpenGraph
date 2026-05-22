# PATCH MANIFEST v4.25.6 — FT role-config + examples + diagnostics

## Goal

Stabilize Functional Tree (FT) after the first role-box implementation.

## Changes

- Added configurable FT role-box properties:
  - `functional.layout.role.<role>.rank`
  - `functional.layout.role.<role>.side`
  - `functional.layout.role.<role>.corridor`
- Loaded role config from:
  1. `config/opengraph_defaults.properties`
  2. `config/opengraph_user.properties`
  3. `config/opengraphed_user.properties`
- Added canonical role mapping for aliases such as:
  - `agent` -> `agens`
  - `patient/theme` -> `patiens`
  - `recipient/beneficiary` -> `recipiens`
  - `time` -> `tijd`
- Kept LT default untouched: `projection_box`.
- Kept FT default: `role_box`.

## Examples

Added FT test graphs:

- `examples/ft-test-01-geven.graph`
- `examples/ft-test-02-zien.graph`
- `examples/ft-test-03-snijden-met-mes.graph`
- `examples/ft-test-04-locatief-tijd.graph`
- `examples/README-FT-v4256.txt`

## Checks

- Java compile: OK
- Fresh jar: OK
- `java --dry-run -cp out:. OpenGraphEdFrame`: OK
- `java --dry-run -jar dist/OpenGraphEd.jar`: OK
