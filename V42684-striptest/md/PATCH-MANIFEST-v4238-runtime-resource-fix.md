# PATCH MANIFEST v4.23.8 — runtime resource fix

## Doel

Fix voor de crash bij `run` / openen van een graph:

```text
Caused by: java.lang.NullPointerException: Cannot invoke "java.net.URL.toString()" because "url" is null
at userInterface.GraphEditor.<clinit>(GraphEditor.java:28)
```

## Oorzaak

`run.bat` koos eerst de losse classes in `out\`. In v4.23.7 bevatte `out\` wel classes, maar niet de image-resources. Daardoor gaf:

```java
GraphEditor.class.getResource("/images/RotateCursor.gif")
```

`null` terug. De static initializer van `GraphEditor` crashte daardoor nog vóór het editorvenster kon openen.

## Correctie

- `out\images` wordt opnieuw meegeleverd.
- Alle launchers gebruiken bij losse classes nu classpath `out;projectroot`, zodat resources uit de projectroot ook vindbaar zijn.
- `GraphEditor` heeft nu een veilige fallback voor de rotate cursor: als `RotateCursor.gif` ontbreekt of niet geladen kan worden, gebruikt de editor een standaard hand-cursor in plaats van te crashen.
- Manifest/build-versie verhoogd naar `v4.23.8`.

## Aangeraakt

- `userInterface/GraphEditor.java`
- `run.bat`
- `OpenGraphEd.bat`
- `OpenGraphEd-console.bat`
- `START-OpenGraphEd.bat`
- `OpenGraphed.bat`
- `Opengraphed.bat`
- `build.bat`
- `META-INF/MANIFEST.MF`
- `manifest.mf`
- `out/images/*`
- `md/CHANGELOG.md`
- `md/INDEX.md`
