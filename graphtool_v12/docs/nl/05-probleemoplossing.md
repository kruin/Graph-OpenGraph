# Probleemoplossing

## `py` wordt niet gevonden

Installeer Python voor Windows en zorg dat de Python Launcher beschikbaar is. Test:

```bat
py --version
```

## PNG wordt niet gemaakt

Installeer Pillow:

```bat
py -m pip install pillow
```

SVG-export werkt zonder Pillow.

## Total Commander toont "Toepassing is niet gevonden"

Waarschijnlijk gebruikt Total Commander nog een oude Windows-koppeling. Sluit TC, installeer opnieuw:

```bat
install_windows_graph_open_assoc.bat
```

Open TC daarna opnieuw.

## F3 toont niets in Total Commander

Gebruik de debug-wrapper:

```text
tc_f3_graph_lister_png_debug.bat
```

Controleer of de F3-instelling exact dit gebruikt:

```text
Parameters: "%P%N"
Start path: %P
```

## Thumbnails verschijnen niet

1. Run:

```bat
windows_shell_thumbnail\clear_thumbnail_cache.bat
```

2. Herstart Explorer:

```bat
taskkill /f /im explorer.exe
start explorer.exe
```

3. Zet de mapweergave op grote of extra grote pictogrammen.

## Build faalt

Controleer dat Visual Studio Build Tools is geïnstalleerd met de workload **Desktop development with C++**.

Gebruik debug:

```bat
install_windows_thumbnail_provider_debug.bat
```

## Oude versie blijft zichtbaar

Windows cachet thumbnails agressief. Wis de cache en test in een nieuwe map of hernoem tijdelijk een `.graph`/`.opn`-bestand.
