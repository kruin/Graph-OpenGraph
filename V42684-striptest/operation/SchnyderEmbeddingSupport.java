package operation;

import java.awt.Color;
import java.util.Vector;

import graphStructure.Edge;
import graphStructure.Graph;
import graphStructure.Location;
import graphStructure.Node;
import operation.extenders.NormalEdgeEx;
import operation.extenders.NormalNodeEx;
import operation.extenders.SchnyderEdgeEx;
import operation.extenders.SchnyderNodeEx;

public final class SchnyderEmbeddingSupport
{
  private SchnyderEmbeddingSupport() {}

  public static void embedFromRootNodes(Graph g, Vector rootNodes,
                                        int width, int height) throws Exception
  {
    Vector oldNodes = g.getNodeExtenders();
    Vector oldEdges = g.getEdgeExtenders();

    g.createNodeExtenders(new SchnyderNodeEx().getClass());
    g.createEdgeExtenders(new SchnyderEdgeEx().getClass());

    Vector nodes = g.getNodeExtenders();
    Vector edges = g.getEdgeExtenders();

    copyNodeState(oldNodes, nodes);
    copyEdgeState(oldEdges, edges);

    SchnyderNodeEx[] rootNodesEx = getRootNodes(rootNodes);
    initializeTrees(g, rootNodesEx[0], rootNodesEx[1], rootNodesEx[2]);
    relocateNodes(g, nodes, width, height);
    straightenEdges(g, edges);
  }

  private static void copyNodeState(Vector oldNodes, Vector nodes) throws Exception
  {
    SchnyderNodeEx newNode;
    NormalNodeEx oldNode;
    for ( int i=0; i<oldNodes.size(); i++ )
    {
      oldNode = (NormalNodeEx)oldNodes.elementAt(i);
      newNode = (SchnyderNodeEx)nodes.elementAt(i);
      newNode.setR1Parent((SchnyderNodeEx)oldNode.getR1Parent().getRef().getExtender());
      newNode.setR2Parent((SchnyderNodeEx)oldNode.getR2Parent().getRef().getExtender());
      newNode.setR3Parent((SchnyderNodeEx)oldNode.getR3Parent().getRef().getExtender());
      newNode.setCanonicalNumber(oldNode.getCanonicalNumber());
    }
  }

  private static void copyEdgeState(Vector oldEdges, Vector edges) throws Exception
  {
    SchnyderEdgeEx newEdge;
    NormalEdgeEx oldEdge;
    for ( int i=0; i<oldEdges.size(); i++ )
    {
      oldEdge = (NormalEdgeEx)oldEdges.elementAt(i);
      newEdge = (SchnyderEdgeEx)edges.elementAt(i);
      newEdge.setNormalLabel(oldEdge.getNormalLabel());
    }
  }

  private static SchnyderNodeEx[] getRootNodes(Vector rootNodes) throws Exception
  {
    return new SchnyderNodeEx[]
    {
      (SchnyderNodeEx)((NormalNodeEx)rootNodes.elementAt(2)).getRef().getExtender(),
      (SchnyderNodeEx)((NormalNodeEx)rootNodes.elementAt(1)).getRef().getExtender(),
      (SchnyderNodeEx)((NormalNodeEx)rootNodes.elementAt(0)).getRef().getExtender()
    };
  }

  private static void initializeTrees(Graph g, SchnyderNodeEx firstNode,
                                      SchnyderNodeEx secondNode,
                                      SchnyderNodeEx thirdNode) throws Exception
  {
    traverseTree(1, firstNode);
    traverseTree(2, secondNode);
    traverseTree(3, thirdNode);

    firstNode.setTX(2, 1);
    firstNode.setTX(3, 1);
    secondNode.setTX(1, 1);
    secondNode.setTX(3, 1);
    thirdNode.setTX(1, 1);
    thirdNode.setTX(2, 1);

    traverseTree2(1, firstNode);
    traverseTree2(2, secondNode);
    traverseTree2(3, thirdNode);

    traverseTree3(1, firstNode);
    traverseTree3(2, secondNode);
    traverseTree3(3, thirdNode);

    firstNode.setRX(1, g.getNumNodes()-2);
    firstNode.setRX(2, 1);
    firstNode.setRX(3, 0);
    firstNode.setPX(1, 0);

    secondNode.setRX(1, 0);
    secondNode.setRX(2, g.getNumNodes()-2);
    secondNode.setRX(3, 1);
    secondNode.setPX(2, 0);

    thirdNode.setRX(1, 1);
    thirdNode.setRX(2, 0);
    thirdNode.setRX(3, g.getNumNodes()-2);
    thirdNode.setPX(3, 0);
  }

  private static void relocateNodes(Graph g, Vector nodes,
                                    int width, int height) throws Exception
  {
    g.setGridArea(g.getNumNodes()-1, height, g.getNumNodes()-1, width, true);
    int widthIncrement = g.getGridColWidth();
    int heightIncrement = g.getGridRowHeight();
    SchnyderNodeEx currentNode;
    for ( int i=0; i<nodes.size(); i++ )
    {
      currentNode = (SchnyderNodeEx)nodes.elementAt(i);
      g.relocateNode(currentNode.getRef(),
                     new Location((currentNode.getRX(1)-currentNode.getPX(3))*widthIncrement,
                                  (currentNode.getRX(2)-currentNode.getPX(1))*heightIncrement),
                     true);
    }
  }

  private static void straightenEdges(Graph g, Vector edges) throws Exception
  {
    SchnyderEdgeEx currentEdge;
    for ( int i=0; i<edges.size(); i++ )
    {
      currentEdge = (SchnyderEdgeEx)edges.elementAt(i);
      g.straightenEdge(currentEdge.getRef(), true);
    }
  }

  private static void traverseTree(int treeNumber, SchnyderNodeEx currentNode) throws Exception
  {
    Vector incidentEdges = currentNode.incidentEdges();
    SchnyderEdgeEx currentEdge;
    if ( currentNode.getRXParent(treeNumber) != currentNode )
    {
      currentNode.setPX(treeNumber, currentNode.getRXParent(treeNumber).getPX(treeNumber)+1);
    }
    else
    {
      currentNode.setPX(treeNumber, 1);
    }
    int count = 0;
    for ( int i=0; i<incidentEdges.size(); i++ )
    {
      currentEdge = (SchnyderEdgeEx)incidentEdges.elementAt(i);
      if ( isTreeChildEdge(treeNumber, currentNode, currentEdge) )
      {
        SchnyderNodeEx childNode = (SchnyderNodeEx)currentEdge.otherEndFrom(currentNode);
        traverseTree(treeNumber, childNode);
        count += childNode.getTX(treeNumber);
      }
    }
    currentNode.setTX(treeNumber, 1+count);
  }

  private static void traverseTree2(int treeNumber, SchnyderNodeEx currentNode) throws Exception
  {
    Vector incidentEdges = currentNode.incidentEdges();
    SchnyderEdgeEx currentEdge;

    if ( treeNumber == 1 )
    {
      currentNode.setTemp(1, currentNode.getTX(2) + currentNode.getRXParent(treeNumber).getTemp(1));
      currentNode.setTemp(2, currentNode.getTX(3) + currentNode.getRXParent(treeNumber).getTemp(2));
    }
    else if ( treeNumber == 2 )
    {
      currentNode.setTemp(3, currentNode.getTX(1) + currentNode.getRXParent(treeNumber).getTemp(3));
      currentNode.setTemp(4, currentNode.getTX(3) + currentNode.getRXParent(treeNumber).getTemp(4));
    }
    else if ( treeNumber == 3 )
    {
      currentNode.setTemp(5, currentNode.getTX(1) + currentNode.getRXParent(treeNumber).getTemp(5));
      currentNode.setTemp(6, currentNode.getTX(2) + currentNode.getRXParent(treeNumber).getTemp(6));
    }

    for ( int i=0; i<incidentEdges.size(); i++ )
    {
      currentEdge = (SchnyderEdgeEx)incidentEdges.elementAt(i);
      if ( isTreeChildEdge(treeNumber, currentNode, currentEdge) )
      {
        traverseTree2(treeNumber, (SchnyderNodeEx)currentEdge.otherEndFrom(currentNode));
      }
    }
  }

  private static void traverseTree3(int treeNumber, SchnyderNodeEx currentNode) throws Exception
  {
    Vector incidentEdges = currentNode.incidentEdges();
    SchnyderEdgeEx currentEdge;

    if ( treeNumber == 1 )
    {
      currentNode.setRX(treeNumber, currentNode.getTemp(3) + currentNode.getTemp(5) - currentNode.getTX(treeNumber));
    }
    else if ( treeNumber == 2 )
    {
      currentNode.setRX(treeNumber, currentNode.getTemp(1) + currentNode.getTemp(6) - currentNode.getTX(treeNumber));
    }
    else if ( treeNumber == 3 )
    {
      currentNode.setRX(treeNumber, currentNode.getTemp(2) + currentNode.getTemp(4) - currentNode.getTX(treeNumber));
    }

    for ( int i=0; i<incidentEdges.size(); i++ )
    {
      currentEdge = (SchnyderEdgeEx)incidentEdges.elementAt(i);
      if ( isTreeChildEdge(treeNumber, currentNode, currentEdge) )
      {
        traverseTree3(treeNumber, (SchnyderNodeEx)currentEdge.otherEndFrom(currentNode));
      }
    }
  }

  private static boolean isTreeChildEdge(int treeNumber,
                                         SchnyderNodeEx currentNode,
                                         SchnyderEdgeEx currentEdge) throws Exception
  {
    return currentEdge.getNormalLabel() == treeNumber &&
           currentEdge.getNormalLabelSourceNode() == currentEdge.otherEndFrom(currentNode);
  }

  public static void displayNormalLabeling(Graph g) throws Exception
  {
    Vector nodes = g.getNodeExtenders();
    Vector edges = g.getEdgeExtenders();
    SchnyderNodeEx currentNode;
    SchnyderEdgeEx currentEdge;

    for ( int j=0; j<nodes.size(); j++ )
    {
      currentNode = (SchnyderNodeEx)nodes.elementAt(j);
      if ( currentNode.getCanonicalNumber() == 1 )
      {
        g.changeNodeColor(currentNode, Color.blue, true);
      }
      else if ( currentNode.getCanonicalNumber() == 2 )
      {
        g.changeNodeColor(currentNode, Color.green, true);
      }
      else if ( currentNode.getCanonicalNumber() == nodes.size() )
      {
        g.changeNodeColor(currentNode, Color.red, true);
      }
      else
      {
        g.changeNodeColor(currentNode, Color.darkGray, true);
      }
    }

    for ( int i=0; i<edges.size(); i++ )
    {
      currentEdge = (SchnyderEdgeEx)edges.elementAt(i);
      if ( currentEdge.getNormalLabel() == 1 )
      {
        g.changeEdgeColor(currentEdge, Color.red, true);
        g.changeEdgeDirection(currentEdge, currentEdge.getNormalLabelSourceNode(), true);
      }
      else if ( currentEdge.getNormalLabel() == 2 )
      {
        g.changeEdgeColor(currentEdge, Color.green, true);
        g.changeEdgeDirection(currentEdge, currentEdge.getNormalLabelSourceNode(), true);
      }
      else if ( currentEdge.getNormalLabel() == 3 )
      {
        g.changeEdgeColor(currentEdge, Color.blue, true);
        g.changeEdgeDirection(currentEdge, currentEdge.getNormalLabelSourceNode(), true);
      }
      else
      {
        g.changeEdgeColor(currentEdge, Color.black, true);
        g.changeEdgeDirection(currentEdge, null, true);
      }
    }
    g.markForRepaint();
  }

  public static void displayCanonicalOrdering(Graph g) throws Exception
  {
    Vector nodes = g.getNodeExtenders();
    SchnyderNodeEx currentNode;
    for ( int i=0; i<nodes.size(); i++ )
    {
      currentNode = (SchnyderNodeEx)nodes.elementAt(i);
      g.changeNodeDrawX(currentNode, false, true);
      g.changeNodeLabel(currentNode, String.valueOf(currentNode.getCanonicalNumber()), true);
      if ( currentNode.getCanonicalNumber() == 1 ||
           currentNode.getCanonicalNumber() == 2 ||
           currentNode.getCanonicalNumber() == nodes.size() )
      {
        g.changeNodeColor(currentNode, Color.green, true);
      }
      else
      {
        g.changeNodeColor(currentNode, Node.DEFAULT_COLOR, true);
      }
    }
    Vector edges = g.getEdgeExtenders();
    SchnyderEdgeEx currentEdge;
    for ( int i=0; i<edges.size(); i++ )
    {
      currentEdge = (SchnyderEdgeEx)edges.elementAt(i);
      g.changeEdgeColor(currentEdge, Edge.DEFAULT_COLOR, true);
      g.changeEdgeDirection(currentEdge, null, true);
    }
    g.markForRepaint();
  }
}
