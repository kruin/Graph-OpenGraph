# Mapping v3.9 — MD folder cleanup

## Doel

Alle markdown-documentatie staat voortaan gegroepeerd onder één map:

```text
md/
```

## Verplaatst

- `README.md` → `md/README.md`
- `CHANGELOG.md` → `md/CHANGELOG.md`
- `meta-inf/*.md` → `md/meta-inf/*.md`
- `meta-inf/frame_md.tmp` → `md/meta-inf/frame_md.tmp`
- `examples/**/*.md` → `md/examples/**/*.md`
- `sources/mapping-v3/*.md` → `md/sources/mapping-v3/*.md`

## Niet gewijzigd

- Java-code
- `.class`-bestanden
- `.opn`-voorbeelden
- checker
- generator
- validator
- mappingregels

## Reden

De zip blijft overzichtelijker. Projectdocumentatie is daardoor makkelijker apart te uploaden of te vergelijken met Sources.
