# Generator & Validator

## Pipeline

1. Lees mapping.
2. Valideer constraints.
3. Genereer volgorde.
4. Toon resultaat in Info.

## Validator

- Controleert constraints.
- Geeft OK / FAIL per regel.
- Toont samenvatting met aantallen.

Voorbeeld:

```text
validation: 7 ok, 0 fail
```

## Generator

- Gebruikt rules.
- Bouwt lineaire volgorde.
- Geeft:
  - best
  - alternatieven

Voorbeeld:

```text
generated:
  best: gisteren vrouw heeft niet trui gebreid daar
```

## Belangrijk

- Geen graph-mutatie.
- Alles via de lexicale as.
- Info-output gebruikt multiline wrap.
