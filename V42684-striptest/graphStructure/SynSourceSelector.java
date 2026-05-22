package graphStructure;

public final class SynSourceSelector implements ProjectionSourceSelector
{
  public boolean matches(Node node, Graph graph)
  {
    return graph != null && graph.isProjectionBranchingSource(node);
  }
}
