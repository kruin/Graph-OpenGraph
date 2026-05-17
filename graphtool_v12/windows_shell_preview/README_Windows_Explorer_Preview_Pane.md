# Windows Explorer Preview Pane voor `.graph`

Deze map bevat een echte Windows Shell Preview Handler voor `.graph`- en `.opn`-bestanden.

Doel:

- Windows Verkenner: selecteer `.graph` → Preview Pane toont direct de gerenderde graph.
- Geen Python nodig voor de Windows Preview Pane zelf.
- Installatie per gebruiker via `HKCU`, dus normaal geen administrator nodig.

## Snel installeren

Dubbelklik in de hoofdmap:

```bat
install_windows_preview_handler.bat
```

of vanuit deze map:

```bat
install_windows_preview_handler_user.bat
```

Daarna:

1. Sluit alle Windows Verkenner-vensters.
2. Open Verkenner opnieuw.
3. Zet Preview Pane aan met `Alt+P`.
4. Selecteer een `.graph`/`.opn`-bestand.

## Verwijderen

```bat
uninstall_windows_preview_handler_user.bat
```

of vanuit de hoofdmap:

```bat
uninstall_windows_preview_handler.bat
```

## Build

```bat
build_windows_shell_preview_handler.bat
```

De build gebruikt `csc.exe` uit .NET Framework 4.x:

```text
%WINDIR%\Microsoft.NET\Framework64\v4.0.30319\csc.exe
```

Als `csc.exe` ontbreekt: installeer .NET Framework 4.x Developer Pack of Visual Studio Build Tools.

## Techniek

De preview handler is een COM-server in C#/.NET Framework:

- implementeert `IPreviewHandler`;
- implementeert `IInitializeWithStream` en `IInitializeWithFile`;
- draait via een eigen `prevhost.exe`-surrogate;
- registreert `.graph` onder `HKCU\Software\Classes`.

Er is bewust geen managed Thumbnail Provider geïnstalleerd. Een thumbnail provider wordt vaak directer door Explorer gebruikt; daarvoor is later beter een kleine native C++ handler te maken. Deze versie richt zich op de Preview Pane zelf.

## Controle

```bat
check_windows_preview_handler_registration.bat
```

## Parser

De handler accepteert hetzelfde praktische formaat als de Python-renderer:

- lege eerste regel toegestaan;
- integer- en floatcoördinaten toegestaan;
- lege labels toegestaan;
- zesde dot-regel wordt als adjacency/metadata overgeslagen;
- edge-sectie wordt gelezen en getekend.

## v12 structured OPN

v12 also accepts structured `.opn` files, including `.structure.opn` names:

- YAML-like `structure: nodes: / edges:` files.
- Section-based `STRUCTURE_NODES:` / `STRUCTURE_EDGES:` files.

After unpacking v12, rebuild/reinstall this handler with `install_windows_preview_handler.bat`.
