v4.27.4 - FT per-node menu skeleton

Functional Tree metadata is now assigned per non-terminal node.

Popup:
  - Open Edit mode / node popup.
  - Non-terminal nodes get an FT... button.
  - Terminal nodes do not get the FT menu.

Top node:
  - For labels CLAUSE or V, choose action or event.

Participant:
  - Participant choices follow the uploaded thematic-position hierarchy:
    actor -> BEZIELD / ONBEZIELD and process stakeholder roles.

Frame:
  - Frame choices follow FRAME.GRAPH:
    RICHTING, STATISCH, HOE, ACTIE, DYNAMISCH.

Persistence:
  - Node FT metadata is saved in an optional #OPENGRAPHED_FT_NODE_META_V1 block after the normal graph data.

Draw:
  - FT Draw uses node metadata before falling back to label-based role detection.
