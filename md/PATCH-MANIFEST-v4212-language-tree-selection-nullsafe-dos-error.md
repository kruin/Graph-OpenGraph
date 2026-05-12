# PATCH MANIFEST — v4.21.2 Language Tree selectie null-safe + DOS error logging

## Doel

Herstel crash bij directe zinstype-draw na openen van `lextest.graph` en zorg dat OpenGraph-foutmeldingen ook in het DOS/consolevenster verschijnen.

## Wijzigingen

- `GraphEditorModeSupport.getSpecialNodeSelections()` is null-safe gemaakt.
- `GraphEditorListener.getSpecialNodeSelections()` geeft nu een lege `Vector` terug als er nog geen speciale selectie is geïnitialiseerd.
- OpenGraph drawing display fouten worden nu naar `System.err` geschreven met titel, melding en stacktrace.
- Dialogmelding blijft bestaan, maar dezelfde technische informatie is nu ook zichtbaar in DOS/console.
- Undo/abort in OpenGraph-catchblokken is null-safe gemaakt.

## Test

- `javac`: OK, warnings only.
- `Language Tree`: 3 pass, 0 fail.
- `Mapping V4`: 53 pass, 0 fail.
- `Mapping V3`: 13 pass, 0 fail.
- Startup onder xvfb: OK, geen startup-fout.
