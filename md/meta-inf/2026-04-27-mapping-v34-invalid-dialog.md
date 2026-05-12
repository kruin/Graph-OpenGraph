# Mapping v3.4 invalid dialog

## Doel

Bij het openen van een `.opn` met ongeldige `MAPPING_V3` moet de gebruiker de fout direct zien.

## Gedrag

- De graph/editor wordt geopend.
- Het Info-scherm wordt geopend/bijgewerkt.
- Er verschijnt direct een modal melding met OK-knop.
- De melding noemt de loadfile.
- De melding verwijst naar het Info-scherm voor de volledige diagnose.

## Voorbeeld

```text
OPN Mapping-fout in loadfile:
05-recipient-theme-cycle.opn

Zie het Info-scherm voor de volledige diagnose.

validation: ... fail (...)
```

## Scope

Wel:
- invalid mapping zichtbaar maken bij open `.opn`
- Info-output behouden als volledige diagnose

Niet:
- UI-layout wijzigen
- graph rendering wijzigen
- mapping/generatorregels inhoudelijk uitbreiden
