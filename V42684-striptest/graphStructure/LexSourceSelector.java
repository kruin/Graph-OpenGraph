package graphStructure;

public final class LexSourceSelector implements ProjectionSourceSelector
{
  public boolean matches(Node node, Graph graph)
  {
    return graph != null && graph.isProjectionLeafSource(node);
  }
}
