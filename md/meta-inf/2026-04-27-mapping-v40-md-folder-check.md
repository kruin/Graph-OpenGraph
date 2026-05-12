# 2026-04-27 — Mapping V40: MD Folder Check

## Doel

Afdwingen dat alle nieuwe Markdown-documentatie onder `md/` terechtkomt.

## Toegevoegd

- `run-md-folder-check.bat`
- `tools/check-md-folder.sh`
- `tools/CheckMdFolder.java` als Java-bron voor een latere cross-platform checker

## Regel

Alle `.md`-bestanden moeten onder `md/` staan.

Toegestaan:

```text
md/README.md
md/CHANGELOG.md
md/INDEX.md
md/meta-inf/...
md/sources/...
md/examples/...
```

Niet toegestaan:

```text
CHANGELOG.md
meta-inf/*.md
examples/**/*.md
sources/**/*.md
```

## Controle

Windows:

```text
run-md-folder-check.bat
```

POSIX/container:

```text
tools/check-md-folder.sh .
```

Verwacht:

```text
PASS: all .md files are under md/
```

## Scope

Geen wijzigingen aan Mapping V3, generator, validator, regression checker of UI.
MD-organisatie wordt nu technisch gecontroleerd.
