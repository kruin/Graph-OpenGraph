v4.27.5 - FT thematic auto proposal

Purpose:
Given a graph that only contains S/CLAUSE/V, syntactic category nodes and terminal nodes, Draw can now first make an FT thematic proposal.

Default proposal rules:
- S/CLAUSE/V top node -> action, role pred
- first NP/DP under S/CLAUSE -> agens
- VP/V/process node -> pred
- first NP/DP under VP/V -> patiens
- additional NP/DP nodes -> recipiens
- PP/P -> Frame/locatief by default; lexical hints can suggest instrument or cause
- ADV/ADVP -> tijd
- AP/ADJP -> eigenschap

Node FT menu:
Per non-terminal node, FT... still lets the user override/refine the proposal through the hierarchy. Explicit node metadata wins over the automatic proposal.
