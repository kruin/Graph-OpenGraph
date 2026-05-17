# Windows Explorer thumbnails voor `.graph`

Deze map bevat een native C++ Shell Extension voor miniaturen/thumbnail-weergave van `.graph`- en `.opn`-bestanden in Windows Explorer.

## Doel

- Windows Verkenner toont `.graph`- en `.opn`-bestanden als gerenderde miniatuur.
- Werkt naast de bestaande viewer, F3-wrapper en Preview Pane-handler.
- Registratie is per gebruiker via `HKCU`, dus normaal zonder administratorrechten.

## Belangrijk

Dit onderdeel is native Windows-COM. Bouw en test dit op Windows, niet in ChatGPT/Linux.

Vereist:

- Windows 10/11 64-bit;
- Visual Studio Build Tools;
- workload: **Desktop development with C++**;
- x64 build, omdat 64-bit Explorer een 64-bit thumbnail provider nodig heeft.

## Installeren

Vanaf de hoofdmap van deze ZIP:

```bat
install_windows_thumbnail_provider.bat
```

Of direct vanuit deze map:

```bat
install_thumbnail_provider_user.bat
```

De installer doet:

1. build van `GraphThumbnailProvider.dll`;
2. registratie via 64-bit `regsvr32`;
3. registratie van `.graph` als thumbnail-bestandstype;
4. korte registry-check.

## Controleren

```bat
check_thumbnail_provider_registration.bat
```

## Als thumbnails niet verschijnen

1. Zet Explorer op **Large icons** of **Extra large icons**.
2. Controleer dat thumbnails niet uitgeschakeld zijn:
   - Explorer Options → View → **Always show icons, never thumbnails** moet uit staan.
3. Wis cache:

```bat
clear_thumbnail_cache.bat
```

4. Sluit en heropen Verkenner.

## Verwijderen

Vanaf de hoofdmap:

```bat
uninstall_windows_thumbnail_provider.bat
```

Of direct:

```bat
uninstall_thumbnail_provider_user.bat
```

## Techniek

De provider implementeert:

- `IThumbnailProvider`
- `IInitializeWithStream`
- COM class factory
- `DllRegisterServer` / `DllUnregisterServer`

De renderer accepteert dezelfde toleranties als de Python-renderer:

- optionele lege eerste regel;
- float-coördinaten, bijvoorbeeld `319.0`;
- lege labels;
- dot-blokken van 6 regels;
- optionele edge-sectie van 10 regels per edge.

## CLSID's

Thumbnail provider:

```text
{B86C773A-62BD-4F47-85D4-132380F52AE3}
```

Windows Shell thumbnail handler key:

```text
{E357FCCD-A995-4576-B01F-234630154E96}
```

## v12 structured OPN

v12 also accepts structured `.opn` files, including `.structure.opn` names:

- YAML-like `structure: nodes: / edges:` files.
- Section-based `STRUCTURE_NODES:` / `STRUCTURE_EDGES:` files.

After unpacking v12, rebuild/reinstall this provider with `install_windows_thumbnail_provider.bat`, then clear the thumbnail cache.
