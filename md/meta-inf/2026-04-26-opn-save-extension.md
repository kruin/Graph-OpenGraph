# 2026-04-26 — Save OPN extension enforcement

Wijziging:
- File → Save OPN forceert nu altijd de extensie `.opn`.
- Zonder extensie wordt `.opn` toegevoegd.
- Met een andere extensie, bijvoorbeeld `test.txt`, wordt `test.txt.opn` opgeslagen.
- De Save OPN chooser gebruikt een OPN-filter en verbergt andere extensies.

Compile:
- `javac @sources.txt` OK
- alleen bestaande deprecation warning in `GraphFileActions.java`.
