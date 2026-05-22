package graphStructure;

import java.util.Random;
import java.util.Vector;

final class GraphExtenderSupport
{
  private GraphExtenderSupport() {}

  static Vector createNodeExtenders(Vector nodes, Class nodeExtenderClass)
  {
    Vector newVector = new Vector(nodes.size());
    for ( int i=0; i<nodes.size(); i++ )
    {
      Node currentNode = (Node)nodes.elementAt(i);
      try
      {
        NodeExtender currentNodeExtender = (NodeExtender)nodeExtenderClass.newInstance();
        currentNode.setExtender(currentNodeExtender);
        currentNodeExtender.setRef(currentNode);
        newVector.addElement(currentNodeExtender);
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
    }
    return newVector;
  }

  static Vector createEdgeExtenders(Vector edges, Class edgeExtenderClass)
  {
    Vector newVector = new Vector(edges.size());
    for ( int i=0; i<edges.size(); i++ )
    {
      Edge currentEdge = (Edge)edges.elementAt(i);
      try
      {
        EdgeExtender currentEdgeExtender = (EdgeExtender)edgeExtenderClass.newInstance();
        currentEdge.setExtender(currentEdgeExtender);
        currentEdgeExtender.setRef(currentEdge);
        newVector.addElement(currentEdgeExtender);
      }
      catch ( Exception e )
      {
        e.printStackTrace();
      }
    }
    return newVector;
  }

  static Vector getNodeExtenders(Vector nodes)
  {
    Vector newVector = new Vector(nodes.size());
    for ( int i=0; i<nodes.size(); i++ )
    {
      NodeExtender currentNodeExtender = ((Node)nodes.elementAt(i)).getExtender();
      if ( currentNodeExtender != null )
      {
        newVector.addElement(currentNodeExtender);
      }
    }
    return newVector;
  }

  static Vector getEdgeExtenders(Vector edges)
  {
    Vector newVector = new Vector(edges.size());
    for ( int i=0; i<edges.size(); i++ )
    {
      EdgeExtender currentEdgeExtender = ((Edge)edges.elementAt(i)).getExtender();
      if ( currentEdgeExtender != null )
      {
        newVector.addElement(currentEdgeExtender);
      }
    }
    return newVector;
  }

  static void permuteNodeOrder(Vector nodes)
  {
    Random rand = new Random();
    for ( int i=0; i<nodes.size(); i++ )
    {
      int j = rand.nextInt(nodes.size());
      Object temp = nodes.elementAt(i);
      nodes.setElementAt(nodes.elementAt(j), i);
      nodes.setElementAt(temp, j);
    }
  }
}
