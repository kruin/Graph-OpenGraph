# PATCH MANIFEST v4.24.1 — Language Tree restore LR/RL recursion

## Doel

Herstel de n-binaire compacte Language Tree-recursie na v4.24.0.
De v4.24.0 directional box-avoid aanpak hield beide binaire kinderen op dezelfde buitenzijde
(RR/LL-cascade). Dat is ongewenst voor de eerste vaste versie.

## Gewijzigd

- Binary Language Tree-combiner gebruikt weer LR/RL:
  - linker context: eerste kind links, tweede kind rechts;
  - rechter context: eerste kind rechts, tweede kind links.
- De richtingcontext wordt wel doorgegeven aan beide kinderen, zodat diepe terminals naar de
  juiste buitenzijde kunnen blijven uitwaaieren.
- `freeBinarySameSideBoxAvoidRule` blijft alleen als legacy-code aanwezig, maar wordt niet meer
  aangeroepen door de Language Tree-layout.
- Runtime resource fix en Windows classpath fix blijven behouden.

## Bewust nog niet gedaan

- Geen configuratiematrix voor alternatieve plaatsingsstrategieën.
- Geen globale ancestor-box-avoidance.
