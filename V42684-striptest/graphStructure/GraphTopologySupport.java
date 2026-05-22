package graphStructure;

final class GraphTopologySupport
{
  private GraphTopologySupport() {}

  static boolean isTriangle(Node sourceNode, Edge firstEdge, Edge secondEdge)
  {
    Node firstNode = (Node)firstEdge.otherEndFrom(sourceNode);
    Node secondNode = (Node)secondEdge.otherEndFrom(sourceNode);
    return firstEdge.getPreviousInOrderFrom(firstNode).otherEndFrom(firstNode) == secondNode;
  }

  static boolean isInQuadrilateral(Edge anEdge)
  {
    Node firstNode = (Node)anEdge.getStartNode();
    Node secondNode = (Node)anEdge.otherEndFrom(firstNode);
    return ( anEdge.getNextInOrderFrom(firstNode).otherEndFrom(firstNode) ==
             anEdge.getPreviousInOrderFrom(secondNode).otherEndFrom(secondNode) &&
             anEdge.getPreviousInOrderFrom(firstNode).otherEndFrom(firstNode) ==
             anEdge.getNextInOrderFrom(secondNode).otherEndFrom(secondNode) );
  }

  static void flip(Graph graph, Edge anEdge)
  {
    Node firstNode = (Node)anEdge.getStartNode();
    Node secondNode = (Node)anEdge.getEndNode();
    Node newFirstNode = (Node)((Edge)anEdge.getPreviousInOrderFrom(firstNode)).otherEndFrom(firstNode);
    Node newSecondNode = (Node)((Edge)anEdge.getNextInOrderFrom(firstNode)).otherEndFrom(firstNode);
    Edge newFirstPrevEdge = (Edge)anEdge.getNextInOrderFrom(secondNode);
    Edge newSecondPrevEdge = (Edge)anEdge.getNextInOrderFrom(firstNode);
    Edge newEdge = new Edge(anEdge, null, newFirstNode, newSecondNode );

    graph.deleteEdge(anEdge);
    graph.addEdge(newEdge, newFirstPrevEdge, newSecondPrevEdge);
  }
}
