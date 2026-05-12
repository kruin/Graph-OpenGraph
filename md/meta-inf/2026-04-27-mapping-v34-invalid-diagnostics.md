# Mapping v3.4 — Core invalid diagnostics

## Doel

Bij openen van een `.opn` met foute `MAPPING_V3` moet de fout zichtbaar zijn in het Info-venster.
De foutmelding noemt de geladen bestandsnaam, zodat de fout snel te koppelen is aan de testfile.

## Toegevoegd

```text
examples/opn/mapping-v3-core-invalid/
  01-missing-role.opn
  02-unknown-role.opn
  03-missing-theme-target.opn
  04-missing-vpart-target.opn
  05-recipient-theme-cycle.opn
  06-verb-domain-missing-id.opn
  07-duplicate-lexical-id.opn
  08-missing-verb-anchor.opn
  README.txt
```

## Verwacht gedrag

Bij openen van een invalid `.opn`:

```text
validation: ... fail (file: <loadfile>; <diagnostic>)
generated: none (invalid mapping)
```

## Buiten scope

- UI-layout wijzigen
- graph rendering
- WH/vraagzinnen
- NEG
- DET-splitsing
- FRAME.graph
