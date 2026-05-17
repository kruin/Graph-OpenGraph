# Changelog v12 — structured OPN support

## Added

- Support for structured `.opn` / `.structure.opn` files.
- Parser for YAML-like `structure.nodes` / `structure.edges` files.
- Parser for section-based `STRUCTURE_NODES` / `STRUCTURE_EDGES` files.
- Example files:
  - `examples/onbezield-bovenboom.structure.opn`
  - `examples/voorbeeldzin-vrouw-heeft-trui-gebreid.structure.opn`
- Preview and clean PNG/SVG examples for both structured OPN files.

## Updated

- Python renderer and viewer parser.
- Windows Explorer Preview Handler C# parser.
- Windows Explorer Thumbnail Provider C++ parser.
- README in Dutch and English.

## Notes

The Windows Shell handlers must be rebuilt and installed again on Windows after unpacking v12.
