OpenGraphEd v4.26.8 - FT role selection / Frame preparation

Purpose
-------
Functional Tree now has explicit config keys for separating compact FT core roles from frame roles.

Core roles remain in the compact role-box:
  pred, agens, patiens, recipiens

Frame roles are reserved for the FRAME view/projection:
  instrument, locatief/plaats, tijd

Config keys
-----------
functional.layout.coreRoles=pred,agens,patiens,recipiens
functional.layout.frameRoles=instrument,locatief,plaats,tijd
functional.layout.role.<role>.scope=core|frame
functional.layout.role.<role>.visible=true|false
functional.layout.frame.enabled=true
functional.layout.frame.caption=FRAME
functional.layout.frame.roles=instrument,locatief,plaats,tijd

Status
------
This version exposes the configuration and keeps the FT compact unit horizontal spacing.
Full visual suppression/migration of frame roles from the role-box into a separate frame panel is reserved for the next implementation step.
