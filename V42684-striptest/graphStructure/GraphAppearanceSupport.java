package graphStructure;

import java.util.Vector;

final class GraphAppearanceSupport
{
  private GraphAppearanceSupport() {}

  static void removeEdgeDirections(Graph graph, Vector edges, boolean createMemento)
  {
    for ( int i=0; i<edges.size(); i++ )
    {
      graph.changeEdgeDirection((Edge)edges.elementAt(i), null, createMemento);
    }
  }

  static void clearNodeLabels(Graph graph, Vector nodes, boolean createMemento)
  {
    for ( int i=0; i<nodes.size(); i++ )
    {
      graph.changeNodeLabel((Node)nodes.elementAt(i), "", createMemento);
    }
  }

  static void resetColors(Graph graph, Vector nodes, Vector edges, boolean createMemento)
  {
    for ( int i=0; i<nodes.size(); i++ )
    {
      Node aNode = (Node)nodes.elementAt(i);
      graph.changeNodeColor(aNode, Node.DEFAULT_COLOR, createMemento);
      graph.changeNodeDrawX(aNode, false, createMemento);
    }
    for ( int i=0; i<edges.size(); i++ )
    {
      graph.changeEdgeColor((Edge)edges.elementAt(i), Edge.DEFAULT_COLOR, createMemento);
    }
  }
}
