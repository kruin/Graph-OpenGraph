# Windows Explorer

## Double-click / Open

Install the normal `.graph` association:

```bat
install_windows_graph_open_assoc.bat
```

After that, double-clicking a `.graph` file opens the Graph Viewer.

## Preview Pane / Alt+P

Install the Windows Preview Handler:

```bat
install_windows_preview_handler.bat
```

Then:

1. Close Explorer.
2. Open Explorer again.
3. Enable the Preview Pane with `Alt+P`.
4. Select a `.graph` file.

Remove it with:

```bat
uninstall_windows_preview_handler.bat
```

## Thumbnails

Install the native thumbnail provider:

```bat
install_windows_thumbnail_provider.bat
```

Then clear the cache:

```bat
windows_shell_thumbnail\clear_thumbnail_cache.bat
```

Set Explorer view to:

```text
Large icons
```

or:

```text
Extra large icons
```

## Important note about the DLL

The thumbnail provider is a Windows Shell Extension. The DLL is therefore built and registered locally. The Python renderer was tested here; the Shell DLL must be built on your Windows machine using the included batch file.
