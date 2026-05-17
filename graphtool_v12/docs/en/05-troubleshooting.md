# Troubleshooting

## `py` is not found

Install Python for Windows and make sure the Python Launcher is available. Test:

```bat
py --version
```

## PNG is not created

Install Pillow:

```bat
py -m pip install pillow
```

SVG export works without Pillow.

## Total Commander shows "Application not found"

Total Commander is probably still using an old Windows file association. Close TC, reinstall the association:

```bat
install_windows_graph_open_assoc.bat
```

Then open TC again.

## F3 shows nothing in Total Commander

Use the debug wrapper:

```text
tc_f3_graph_lister_png_debug.bat
```

Check that the F3 configuration uses exactly:

```text
Parameters: "%P%N"
Start path: %P
```

## Thumbnails do not appear

1. Run:

```bat
windows_shell_thumbnail\clear_thumbnail_cache.bat
```

2. Restart Explorer:

```bat
taskkill /f /im explorer.exe
start explorer.exe
```

3. Set the folder view to Large icons or Extra large icons.

## Build fails

Check that Visual Studio Build Tools are installed with the **Desktop development with C++** workload.

Use debug mode:

```bat
install_windows_thumbnail_provider_debug.bat
```

## Old visual style still appears

Windows caches thumbnails aggressively. Clear the cache and test in a new folder, or temporarily rename a `.graph` file.
