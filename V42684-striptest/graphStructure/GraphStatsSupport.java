package graphStructure;

import java.util.Enumeration;
import java.util.Vector;

final class GraphStatsSupport
{
  private GraphStatsSupport() {}

  static boolean edgeNumbersAreInSync(Graph graph)
  {
    return graph.getNumEdges() == graph.getEdges().size();
  }

  static void makeAllEdgesStraight(Vector edges)
  {
    Edge anEdge;
    for ( int i=0; i<edges.size(); i++ )
    {
      anEdge = (Edge)edges.elementAt(i);
      if ( anEdge.isCurved() )
      {
        anEdge.makeStraight();
      }
    }
  }

  static int getNumEdges(Vector nodeVector)
  {
    int numEdges = 0;
    Enumeration allNodes = nodeVector.elements();
    while ( allNodes.hasMoreElements() )
    {
      numEdges += ((Node)allNodes.nextElement()).getNumEdges();
    }
    return numEdges / 2;
  }

  static int getNumGeneratedEdges(Vector edges)
  {
    int numGenerated = 0;
    for ( int i=0; i<edges.size(); i++ )
    {
      if ( ((Edge)edges.elementAt(i)).isGenerated() )
      {
        numGenerated++;
      }
    }
    return numGenerated;
  }

  static int getNumCurvedEdges(Vector edges)
  {
    int numCurved = 0;
    for ( int i=0; i<edges.size(); i++ )
    {
      if ( ((Edge)edges.elementAt(i)).isCurved() )
      {
        numCurved++;
      }
    }
    return numCurved;
  }

  static Vector getEdges(Vector nodeVector, boolean onlyCurved)
  {
    Vector edges = new Vector(getNumEdges(nodeVector));
    Enumeration allNodes = nodeVector.elements();
    while ( allNodes.hasMoreElements() )
    {
      Enumeration someEdges = ((Node)allNodes.nextElement()).incidentEdges().elements();
      while ( someEdges.hasMoreElements() )
      {
        ((Edge)someEdges.nextElement()).setIsAdded(false);
      }
    }

    allNodes = nodeVector.elements();
    while ( allNodes.hasMoreElements() )
    {
      Enumeration someEdges = ((Node)allNodes.nextElement()).incidentEdges().elements();
      while ( someEdges.hasMoreElements() )
      {
        Edge anEdge = (Edge)someEdges.nextElement();
        if ( !anEdge.isAdded() )
        {
          if ( !onlyCurved || anEdge.isCurved() )
          {
            edges.addElement(anEdge);
          }
          anEdge.setIsAdded(true);
        }
      }
    }

    allNodes = nodeVector.elements();
    while ( allNodes.hasMoreElements() )
    {
      Enumeration someEdges = ((Node)allNodes.nextElement()).incidentEdges().elements();
      while ( someEdges.hasMoreElements() )
      {
        ((Edge)someEdges.nextElement()).setIsAdded(false);
      }
    }
    return edges;
  }

  static Node[] getRandomTriangularFace(Vector nodes)
  {
    Node triangleNodes[] = new Node[3];
    triangleNodes[0] = (Node)nodes.firstElement();
    Edge tempEdge = (Edge)triangleNodes[0].incidentEdges().firstElement();
    triangleNodes[1] = (Node)tempEdge.otherEndFrom(triangleNodes[0]);
    triangleNodes[2] = (Node)tempEdge.getNextInOrderFrom(triangleNodes[0]).otherEndFrom(triangleNodes[0]);
    if ( triangleNodes[2] ==
         tempEdge.getPreviousInOrderFrom(triangleNodes[1]).otherEndFrom(triangleNodes[1]) )
    {
      return triangleNodes;
    }
    return null;
  }

  static String toSummaryString(String label, int numNodes, int numEdges)
  {
    return label + "(" + numNodes + " nodes, " + numEdges + " edges)";
  }

  static void printAll(Vector nodes)
  {
    for ( int i=0; i<nodes.size(); i++ )
    {
      ((Node)nodes.elementAt(i)).printAll();
    }
  }
}
