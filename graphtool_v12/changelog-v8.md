# Changelog v8 — thumbnail register/build fix

## Hersteld

- `install_windows_thumbnail_provider.bat` bleef hangen bij `regsvr32` op sommige systemen.
- Oorzaak: de DLL startte GDI+ vanuit `DllMain`. Dat is onveilig voor shell extensions en kan bij laden/registreren door `regsvr32` of Explorer vastlopen.
- Oplossing: GDI+ wordt nu pas gestart binnen `GetThumbnail`/rendering, niet meer tijdens DLL-load.

## Build hersteld

- Buildstap gesplitst in `cl.exe /c` en `link.exe`.
- Daardoor wordt `/OUT:"C:\..."` niet meer door `cl.exe` geïnterpreteerd als reeks `/O...` compileropties.
- De waarschuwingen zoals `ignoring unknown option '/OC'`, `/OG`, `/Or` enz. horen hiermee weg te zijn.

## Gebruik

Run opnieuw:

```bat
install_windows_thumbnail_provider.bat
```

Als v7 nog hangt: sluit dat commandovenster of breek af met `Ctrl+C`, pak v8 uit en start v8.
