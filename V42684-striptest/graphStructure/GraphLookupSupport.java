package graphStructure;

import java.util.Enumeration;
import java.util.Vector;

final class GraphLookupSupport
{
  private GraphLookupSupport() {}

  static void resetCopyData(Vector nodes)
  {
    Enumeration enumNodes = nodes.elements();
    while ( enumNodes.hasMoreElements() )
    {
      Node aNode = (Node)enumNodes.nextElement();
      aNode.setCopy(null);
      Enumeration enumEdges = aNode.incidentEdges().elements();
      while ( enumEdges.hasMoreElements() )
      {
        ((Edge)enumEdges.nextElement()).setCopy(null);
      }
    }
  }

  static Node getNodeAt(Vector nodes, int index)
  {
    if ( nodes.isEmpty() || index < 0 || index > nodes.size()-1 )
    {
      return null;
    }
    return (Node)nodes.elementAt(index);
  }

  static Node nodeNamed(Vector nodes, String aLabel)
  {
    for ( int i=0; i<nodes.size(); i++ )
    {
      Node aNode = (Node)nodes.elementAt(i);
      if ( aNode.getLabel().equals(aLabel) )
      {
        return aNode;
      }
    }
    return null;
  }

  static Node nodeAt(Vector nodes, Location p)
  {
    for ( int i=0; i<nodes.size(); i++ )
    {
      Node aNode = (Node)nodes.elementAt(i);
      int distance = (p.intX() - aNode.getLocation().intX()) * (p.intX() - aNode.getLocation().intX()) +
                     (p.intY() - aNode.getLocation().intY()) * (p.intY() - aNode.getLocation().intY());
      if ( distance <= (Node.RADIUS * Node.RADIUS) )
      {
        return aNode;
      }
    }
    return null;
  }

  static Edge edgeAt(Vector edges, Location p)
  {
    for ( int i=0; i<edges.size(); i++ )
    {
      Edge anEdge = (Edge)edges.elementAt(i);
      int midPointX = anEdge.getCenterLocation().intX();
      int midPointY = anEdge.getCenterLocation().intY();
      int distance = (p.intX() - midPointX) * (p.intX() - midPointX) +
                     (p.intY() - midPointY) * (p.intY() - midPointY);
      if ( distance <= (Node.RADIUS * Node.RADIUS) )
      {
        return anEdge;
      }
    }
    return null;
  }
}
