package operation;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Random;
import java.util.Stack;
import java.util.Vector;

import graphStructure.Edge;
import graphStructure.EdgeExtender;
import graphStructure.Graph;
import graphStructure.Node;
import graphStructure.NodeExtender;
import operation.extenders.BiCompEdgeEx;
import operation.extenders.BiCompNodeEx;

class BiconnectivitySupport
{
  private BiconnectivitySupport() {}

  static void resetNodeTraversalState(Vector nodes)
  {
    Enumeration enum1 = nodes.elements();
    while ( enum1.hasMoreElements() )
    {
      BiCompNodeEx tempNode = (BiCompNodeEx)enum1.nextElement();
      tempNode.setNumber(0);
      tempNode.setLowNumber(0);
      tempNode.setParent(null);
    }
  }

  static void resetConnectedNodeState(Vector connectedNodes)
  {
    Enumeration enum1 = connectedNodes.elements();
    while ( enum1.hasMoreElements() )
    {
      BiCompNodeEx tempNode = (BiCompNodeEx)enum1.nextElement();
      tempNode.setNumber(0);
      tempNode.setLowNumber(0);
      tempNode.setParent(null);
      tempNode.setSubGraphNumber(0);
    }
  }

  static void resetEdgeTraversalState(Vector edges)
  {
    Enumeration enum1 = edges.elements();
    while ( enum1.hasMoreElements() )
    {
      BiCompEdgeEx tempEdge = (BiCompEdgeEx)enum1.nextElement();
      tempEdge.setIsUsed(false);
      tempEdge.setWasAdded(false);
    }
  }

  static void resetEdgeTraversalAndSubGraphState(Vector edges)
  {
    Enumeration enum1 = edges.elements();
    while ( enum1.hasMoreElements() )
    {
      BiCompEdgeEx tempEdge = (BiCompEdgeEx)enum1.nextElement();
      tempEdge.setIsUsed(false);
      tempEdge.setSubGraphNumber(0);
      tempEdge.setWasAdded(false);
    }
  }

  static BiCompEdgeEx useFirstUnusedIncidentEdge(BiCompNodeEx tempNode)
  {
    Enumeration enum1 = tempNode.incidentEdges().elements();
    while ( enum1.hasMoreElements() )
    {
      BiCompEdgeEx tempEdge = (BiCompEdgeEx)enum1.nextElement();
      if ( !tempEdge.isUsed() )
      {
        tempEdge.setIsUsed(true);
        tempEdge.setWasAdded(true);
        return tempEdge;
      }
    }
    return null;
  }

  static Graph buildComponentGraph(Graph sourceGraph,
                                   BiCompNodeEx componentNode,
                                   BiCompNodeEx anchorNode,
                                   Stack nodeStack,
                                   Stack edgeStack,
                                   Vector newEdges,
                                   boolean copyData)
  {
    Graph newGraph = new Graph(sourceGraph);
    addComponentNodes(newGraph, componentNode, anchorNode, nodeStack);
    newEdges.removeAllElements();
    collectComponentEdges(componentNode, anchorNode, edgeStack, newEdges);
    return newGraph.copyEdges(EdgeExtender.toEdge(newEdges), copyData);
  }

  static void markSubGraphComponent(BiCompNodeEx componentNode,
                                    BiCompNodeEx anchorNode,
                                    Stack nodeStack,
                                    Stack edgeStack,
                                    int subGraphNumber)
  {
    while ( nodeStack.peek() != componentNode )
    {
      ((BiCompNodeEx)nodeStack.pop()).setSubGraphNumber(subGraphNumber);
    }
    if ( nodeStack.peek() == componentNode )
    {
      ((BiCompNodeEx)nodeStack.pop()).setSubGraphNumber(subGraphNumber);
    }
    collectComponentEdges(componentNode, anchorNode, edgeStack, null, subGraphNumber);
  }

  static boolean hasUnusedIncidentEdges(BiCompNodeEx node)
  {
    Enumeration enum1 = node.incidentEdges().elements();
    while ( enum1.hasMoreElements() )
    {
      if ( !((BiCompEdgeEx)enum1.nextElement()).isUsed() )
      {
        return true;
      }
    }
    return false;
  }

  static void resetSeparatingNodes(Vector separatingNodes)
  {
    Enumeration enum1 = separatingNodes.elements();
    while ( enum1.hasMoreElements() )
    {
      BiCompNodeEx tempNode = (BiCompNodeEx)enum1.nextElement();
      tempNode.setParent(null);
      tempNode.setIsOld(false);
    }
  }

  static void colorComponentDisplays(Graph g, Vector graphs)
  {
    Random rand = new Random();
    for ( int i=0; i<graphs.size(); i++ )
    {
      Color aColor = new Color( rand.nextInt(256), rand.nextInt(256),
                                rand.nextInt(256) );
      colorNodes(g, ((Graph)graphs.elementAt(i)).getNodes(), aColor);
      colorEdges(g, ((Graph)graphs.elementAt(i)).getEdges(), aColor);
    }
  }

  static void highlightSeparatingNodes(Graph g, Vector separatingNodes)
  {
    Vector nodes = NodeExtender.toNode(separatingNodes);
    for ( int i=0; i<nodes.size(); i++ )
    {
      Node aNode = (Node)nodes.elementAt(i);
      g.changeNodeColor(aNode, Color.red, true);
      g.changeNodeDrawX(aNode, true, true);
    }
  }

  private static void addComponentNodes(Graph newGraph,
                                        BiCompNodeEx componentNode,
                                        BiCompNodeEx anchorNode,
                                        Stack nodeStack)
  {
    while ( nodeStack.peek() != componentNode )
    {
      newGraph.addNode(((BiCompNodeEx)nodeStack.pop()).getRef());
    }
    if ( nodeStack.peek() == componentNode )
    {
      newGraph.addNode(((BiCompNodeEx)nodeStack.pop()).getRef());
    }
    newGraph.addNode(anchorNode.getRef());
  }

  private static void collectComponentEdges(BiCompNodeEx componentNode,
                                            BiCompNodeEx anchorNode,
                                            Stack edgeStack,
                                            Vector newEdges)
  {
    collectComponentEdges(componentNode, anchorNode, edgeStack, newEdges, -1);
  }

  private static void collectComponentEdges(BiCompNodeEx componentNode,
                                            BiCompNodeEx anchorNode,
                                            Stack edgeStack,
                                            Vector newEdges,
                                            int subGraphNumber)
  {
    while ( !((BiCompEdgeEx)edgeStack.peek()).isBetween(componentNode, anchorNode) )
    {
      BiCompEdgeEx tempEdge = (BiCompEdgeEx)edgeStack.pop();
      tempEdge.setWasAdded(false);
      if ( newEdges != null )
      {
        newEdges.addElement(tempEdge);
      }
      if ( subGraphNumber >= 0 )
      {
        tempEdge.setSubGraphNumber(subGraphNumber);
      }
    }
    if ( ((BiCompEdgeEx)edgeStack.peek()).isBetween(componentNode, anchorNode) )
    {
      BiCompEdgeEx tempEdge = (BiCompEdgeEx)edgeStack.pop();
      tempEdge.setWasAdded(false);
      if ( newEdges != null )
      {
        newEdges.addElement(tempEdge);
      }
      if ( subGraphNumber >= 0 )
      {
        tempEdge.setSubGraphNumber(subGraphNumber);
      }
    }
  }

  private static void colorNodes(Graph g, Vector nodes, Color aColor)
  {
    for ( int j=0; j<nodes.size(); j++ )
    {
      Node aNode = (Node)((Node)nodes.elementAt(j)).getMasterCopy();
      g.changeNodeColor(aNode, aColor, true);
      g.changeNodeDrawX(aNode, false, true);
      g.changeNodeLabel(aNode, "", true);
    }
  }

  private static void colorEdges(Graph g, Vector edges, Color aColor)
  {
    for ( int j=0; j<edges.size(); j++ )
    {
      Edge anEdge = (Edge)((Edge)edges.elementAt(j)).getMasterCopy();
      g.changeEdgeColor(anEdge, aColor, true);
      g.changeEdgeDirection(anEdge, null, true);
    }
  }
}
