package graphStructure;

import java.util.Enumeration;
import java.util.Vector;

final class NodeIncidentSupport
{
  private NodeIncidentSupport() {}

  static boolean hasNoIncidentEdges(Node node)
  {
    return node.getAccessEdge() == null;
  }

  static boolean hasOnlyOneIncidentEdge(Node node)
  {
    if ( hasNoIncidentEdges(node) )
    {
      return false;
    }
    Edge accessEdge = node.getAccessEdge();
    return accessEdge.getNextInOrderFrom(node) == accessEdge;
  }

  static boolean hasOnlyTwoIncidentEdges(Node node)
  {
    if ( hasNoIncidentEdges(node) || hasOnlyOneIncidentEdge(node) )
    {
      return false;
    }
    Edge accessEdge = node.getAccessEdge();
    return accessEdge.getNextInOrderFrom(node).getNextInOrderFrom(node) == accessEdge;
  }

  static Vector incidentEdges(Node node)
  {
    Vector edgeVector = new Vector(node.getNumEdges());
    Edge accessEdge = node.getAccessEdge();
    if ( accessEdge != null )
    {
      Edge currentEdge = accessEdge;
      do
      {
        edgeVector.addElement(currentEdge);
        currentEdge = (Edge)currentEdge.getNextInOrderFrom(node);
      }
      while ( currentEdge != accessEdge );
    }
    return edgeVector;
  }

  static Vector incidentEdgesInReverse(Node node)
  {
    Vector edgeVector = new Vector(node.getNumEdges());
    Edge accessEdge = node.getAccessEdge();
    if ( accessEdge != null )
    {
      Edge startEdge = (Edge)accessEdge.getPreviousInOrderFrom(node);
      Edge currentEdge = startEdge;
      do
      {
        edgeVector.addElement(currentEdge);
        currentEdge = (Edge)currentEdge.getPreviousInOrderFrom(node);
      }
      while ( currentEdge != startEdge );
    }
    return edgeVector;
  }

  static Vector incidentOutgoingEdges(Node node)
  {
    Vector edgeVector = new Vector(node.getNumEdges());
    Edge accessEdge = node.getAccessEdge();
    if ( accessEdge != null )
    {
      Edge currentEdge = accessEdge;
      do
      {
        if ( currentEdge.getDirectedSourceNode() == null ||
             currentEdge.getDirectedSourceNode() == node )
        {
          edgeVector.addElement(currentEdge);
        }
        currentEdge = (Edge)currentEdge.getNextInOrderFrom(node);
      }
      while ( currentEdge != accessEdge );
    }
    return edgeVector;
  }

  static EdgeInterface incidentEdgeWith(Node node, NodeInterface otherNode)
  {
    Vector edges = incidentEdges(node);
    Edge returnEdge = null;
    for ( int i=0; i<edges.size(); i++ )
    {
      Edge currentEdge = (Edge)edges.elementAt(i);
      if ( currentEdge.otherEndFrom(node) == otherNode )
      {
        returnEdge = currentEdge;
      }
    }
    return returnEdge;
  }

  static boolean hasEdge(Node node, EdgeInterface edge)
  {
    return incidentEdges(node).contains(edge);
  }

  static boolean addIncidentEdge(Node node, EdgeInterface edge)
  {
    Edge e = (Edge)edge;
    if ( !incidentEdges(node).contains(e) )
    {
      addIncidentEdgeNoCheck(node, e);
      return true;
    }
    return false;
  }

  static void addIncidentEdgeNoCheck(Node node, EdgeInterface edge)
  {
    Edge e = (Edge)edge;
    node.incrementNumEdgesInternal();
    Edge accessEdge = node.getAccessEdge();
    if ( accessEdge == null )
    {
      node.setAccessEdge(e);
      e.setNextInOrderFrom(node, e);
      e.setPreviousInOrderFrom(node, e);
    }
    else if ( accessEdge.getNextInOrderFrom(node) == accessEdge )
    {
      accessEdge.setNextInOrderFrom(node, e);
      accessEdge.setPreviousInOrderFrom(node, e);
      e.setNextInOrderFrom(node, accessEdge);
      e.setPreviousInOrderFrom(node, accessEdge);
    }
    else
    {
      Edge prev = (Edge)accessEdge.getPreviousInOrderFrom(node);
      accessEdge.setPreviousInOrderFrom(node, e);
      e.setNextInOrderFrom(node, accessEdge);
      e.setPreviousInOrderFrom(node, prev);
      prev.setNextInOrderFrom(node, e);
    }
  }

  static void addEdgeBetween(Node node, EdgeInterface edge, EdgeInterface prev, EdgeInterface next)
  {
    node.incrementNumEdgesInternal();
    prev.setNextInOrderFrom(node, edge);
    edge.setPreviousInOrderFrom(node, prev);
    edge.setNextInOrderFrom(node, next);
    next.setPreviousInOrderFrom(node, edge);
    if ( node.getAccessEdge() == null )
    {
      if ( edge instanceof Edge )
      {
        node.setAccessEdge((Edge)edge);
      }
      else
      {
        node.setAccessEdge(((EdgeExtender)edge).getRef());
      }
    }
  }

  static void resetIncidentEdges(Node node)
  {
    node.resetNumEdgesInternal();
    node.setAccessEdge(null);
  }

  static void deleteIncidentEdge(Node node, EdgeInterface edge)
  {
    Edge e = (Edge)edge;
    node.decrementNumEdgesInternal();
    if ( e.getNextInOrderFrom(node) == e )
    {
      node.setAccessEdge(null);
    }
    else if ( e.getNextInOrderFrom(node).getNextInOrderFrom(node) == e )
    {
      Edge accessEdge = (Edge)e.getNextInOrderFrom(node);
      node.setAccessEdge(accessEdge);
      accessEdge.setNextInOrderFrom(node, accessEdge);
      accessEdge.setPreviousInOrderFrom(node, accessEdge);
    }
    else
    {
      Edge accessEdge = node.getAccessEdge();
      if ( e == accessEdge )
      {
        node.setAccessEdge((Edge)accessEdge.getNextInOrderFrom(node));
      }
      Edge prev = (Edge)e.getPreviousInOrderFrom(node);
      Edge next = (Edge)e.getNextInOrderFrom(node);
      prev.setNextInOrderFrom(node, next);
      next.setPreviousInOrderFrom(node, prev);
    }
  }

  static void printAll(Node node)
  {
    Vector incidentEdges = incidentEdges(node);
    System.out.print("** " + node + " ** ");
    for ( int i=0; i<incidentEdges.size(); i++ )
    {
      System.out.print(incidentEdges.elementAt(i));
      if ( i < incidentEdges.size() - 1 )
      {
        System.out.print(", ");
      }
      else
      {
        System.out.print("\n");
      }
    }
  }

  static Vector neighbours(Node node)
  {
    Vector result = new Vector(node.getNumEdges());
    Enumeration edges = incidentEdges(node).elements();
    while ( edges.hasMoreElements() )
    {
      result.addElement(((Edge)edges.nextElement()).otherEndFrom(node));
    }
    return result;
  }
}
