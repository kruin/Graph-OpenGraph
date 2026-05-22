Functional Tree tests for v4.25.6

Open these with OpenGraphDraw -> FT - Functional Tree.

Files:
- ft-test-01-geven.graph
- ft-test-02-zien.graph
- ft-test-03-snijden-met-mes.graph
- ft-test-04-locatief-tijd.graph

Purpose:
- verify role detection diagnostics;
- verify configurable rank/side/corridor properties;
- verify LT is unaffected because FT uses role_box separately.

Config keys live in config/opengraph_defaults.properties and can be overridden in config/opengraph_user.properties or config/opengraphed_user.properties:

functional.layout.role.agens.rank=10
functional.layout.role.agens.side=left
functional.layout.role.agens.corridor=outer
