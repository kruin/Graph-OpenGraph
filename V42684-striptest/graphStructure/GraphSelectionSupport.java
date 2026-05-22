package graphStructure;

import java.util.*;
import java.awt.geom.*;

public class GraphSelectionSupport
{
  public static Vector getNodesInRectangle(Vector nodes, Rectangle2D.Double rect)
  {
    Vector nodesToReturn = new Vector();
    Node aNode;
    for ( int i=0; i<nodes.size(); i++ )
    {
      aNode = (Node)nodes.elementAt(i);
      if ( rect.contains( aNode.getX(), aNode.getY() ) )
      {
        nodesToReturn.addElement(aNode);
      }
    }
    return nodesToReturn;
  }

  public static Vector getEdgesInRectangle(Vector edges, Rectangle2D.Double rect)
  {
    Vector edgesToReturn = new Vector();
    Edge anEdge;
    for ( int i=0; i<edges.size(); i++ )
    {
      anEdge = (Edge)edges.elementAt(i);
      if ( rect.contains( anEdge.getCenterLocation().intX(),
                          anEdge.getCenterLocation().intY() ) )
      {
        edgesToReturn.addElement(anEdge);
      }
    }
    return edgesToReturn;
  }

  public static Vector selectedNodes(Vector nodes)
  {
    Vector selected = new Vector();
    Enumeration allNodes = nodes.elements();
    Node aNode;
    while (allNodes.hasMoreElements())
    {
      aNode = (Node)allNodes.nextElement();
      if (aNode.isSelected())
      {
        selected.addElement(aNode);
      }
    }
    return selected;
  }

  public static Vector selectedEdges(Vector edges)
  {
    Vector selected = new Vector();
    Enumeration allEdges = edges.elements();
    Edge anEdge;
    while (allEdges.hasMoreElements())
    {
      anEdge = (Edge)allEdges.nextElement();
      if (anEdge.isSelected())
      {
        selected.addElement(anEdge);
      }
    }
    return selected;
  }

  public static void unselectAll(Vector selectedEdges, Vector selectedNodes)
  {
    Enumeration highlightedEdges = selectedEdges.elements();
    while (highlightedEdges.hasMoreElements())
    {
      ((Edge)highlightedEdges.nextElement()).setSelected(false);
    }
    Enumeration highlightedNodes = selectedNodes.elements();
    while (highlightedNodes.hasMoreElements())
    {
      ((Node)highlightedNodes.nextElement()).setSelected(false);
    }
  }

  public static void deleteSelected(Graph graph, Vector selectedEdges, Vector selectedNodes)
  {
    Enumeration highlightedEdges = selectedEdges.elements();
    while (highlightedEdges.hasMoreElements())
    {
      graph.deleteEdge((Edge)highlightedEdges.nextElement());
    }
    Enumeration highlightedNodes = selectedNodes.elements();
    while (highlightedNodes.hasMoreElements())
    {
      graph.deleteNode((Node)highlightedNodes.nextElement());
    }
  }

  public static void toggleEdgeSelection(Edge anEdge)
  {
    anEdge.toggleSelected();
  }

  public static void toggleNodeSelection(Node aNode)
  {
    aNode.toggleSelected();
  }

  public static void selectNodes(Vector sNodes)
  {
    for ( int i=0; i<sNodes.size(); i++ )
    {
      ((Node)sNodes.elementAt(i)).setSelected(true);
    }
  }

  public static void selectEdges(Vector sEdges)
  {
    for ( int i=0; i<sEdges.size(); i++ )
    {
      ((Edge)sEdges.elementAt(i)).setSelected(true);
    }
  }

  public static void deleteAll(Graph graph, Vector nodes)
  {
    Enumeration nodeEnum = nodes.elements();
    while (nodeEnum.hasMoreElements())
    {
      graph.deleteNode((Node)nodeEnum.nextElement());
    }
  }
}
