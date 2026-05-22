package graphStructure;

import java.util.Vector;

final class EdgeCycleSupport
{
  private EdgeCycleSupport() {}

  static NodeInterface otherEndFrom(Edge edge, NodeInterface aNode)
  {
    if ( edge.getStartNode() == aNode )
    {
      return edge.getEndNode();
    }
    return edge.getStartNode();
  }

  static Vector edgesFromSameCycle(Edge edge)
  {
    Vector edgeVector = new Vector();
    HalfEdge startEdge = edge.getStartHalfEdge();
    HalfEdge he = startEdge;
    do
    {
      edgeVector.addElement(he.getParentEdge());
      he = he.getNext();
    }
    while ( he != startEdge );
    return edgeVector;
  }

  static Vector edgesFromSameCycleOnOtherSide(Edge edge)
  {
    Vector edgeVector = new Vector();
    HalfEdge endEdge = edge.getEndHalfEdge();
    HalfEdge he = endEdge;
    do
    {
      edgeVector.addElement(he.getParentEdge());
      he = he.getNext();
    }
    while ( he != endEdge );
    return edgeVector;
  }
}
