package operation;

import java.util.Enumeration;
import java.util.Stack;
import java.util.Vector;

import graphException.GraphException;
import graphStructure.Graph;
import graphStructure.LogEntry;
import graphStructure.NodeExtender;
import operation.extenders.BiCompEdgeEx;
import operation.extenders.BiCompNodeEx;

public class BiconnectivityOperation
{
  public static Vector getBiconnectedComponents(Graph g)
  {
    return getBiconnectedComponents( g, false );
  }

  public static Vector getBiconnectedComponents(Graph g, boolean copyData)
  {
    LogEntry logEntry = g.startLogEntry("Get Biconnected Components");
    Vector graphs = new Vector();
    Vector oldGraphs = ConnectivityOperation.getConnectedComponents(g, copyData);
    for ( int j=0; j<oldGraphs.size(); j++ )
    {
      Graph oldGraph = (Graph)oldGraphs.elementAt(j);
      if ( oldGraph.getNumNodes() <= 2 )
      {
        graphs.addElement(oldGraph.copy(copyData));
      }
      else
      {
        collectConnectedGraphBiconnectedComponents(oldGraph, graphs, copyData);
      }
    }
    logEntry.setData(graphs.size() + " Biconnected Components found");
    g.stopLogEntry(logEntry);
    return graphs;
  }

  public static Vector findSeparatingNodes(Graph g)
  {
    // Shimon Even - Graph Algorithms - p 62
    LogEntry logEntry = g.startLogEntry("Find Separator Nodes");
    g.createNodeExtenders(new BiCompNodeEx().getClass());
    g.createEdgeExtenders(new BiCompEdgeEx().getClass());
    Vector separatingNodes = new Vector();
    Vector nodesFromEachComponent = ConnectivityOperation.getNodeFromEachConnectedComponent(g, true);
    for ( int j=0; j<nodesFromEachComponent.size(); j++ )
    {
      BiCompNodeEx startNode = (BiCompNodeEx)nodesFromEachComponent.elementAt(j);
      Vector connectedNodes = ConnectivityOperation.getConnectedNodes(g, startNode);

      if ( !startNode.hasNoIncidentEdges() )
      {
        markConnectedComponentSeparators(g, separatingNodes, connectedNodes);
      }
    }
    logEntry.setData(separatingNodes.size() + " nodes found");
    g.stopLogEntry(logEntry);
    return separatingNodes;
  }

  public static boolean makeBiconnected(Graph g) throws Exception
  {
    return makeBiconnected(g, true);
  }

  public static boolean makeBiconnected(Graph g, boolean check) throws Exception
  {
    LogEntry logEntry = g.startLogEntry("Make Biconnected");
    if ( check && !PlanarityOperation.isPlanar(g) )
    {
      logEntry.setData("Graph was not Planar");
      g.stopLogEntry(logEntry);
      throw new GraphException("Graph is not planar!");
    }
    else
    {
      if ( !isBiconnected(g) )
      {
        int counter = 0;
        ConnectivityOperation.makeConnected(g);
        EmbedOperation.embed(g, false);
        Vector separators = findSeparatingNodes(g);
        Enumeration enumSeparators = separators.elements();

        while ( enumSeparators.hasMoreElements() )
        {
          BiCompNodeEx separatorNode = (BiCompNodeEx)enumSeparators.nextElement();
          Vector edges = separatorNode.incidentEdges();
          Enumeration enumEdges = edges.elements();
          while ( enumEdges.hasMoreElements() )
          {
            BiCompEdgeEx currentEdge = (BiCompEdgeEx)enumEdges.nextElement();
            BiCompEdgeEx nextEdge = (BiCompEdgeEx)currentEdge.getNextInOrderFrom(separatorNode);
            if ( !g.isTriangle( separatorNode.getRef(), currentEdge.getRef(), nextEdge.getRef() ) &&
                 ( currentEdge.getSubGraphNumber() != nextEdge.getSubGraphNumber() ||
                   currentEdge.getSubGraphNumber() == 0 ) )
            {
              BiCompNodeEx firstNode = (BiCompNodeEx)currentEdge.otherEndFrom(separatorNode);
              BiCompNodeEx secondNode = (BiCompNodeEx)nextEdge.otherEndFrom(separatorNode);
              BiCompEdgeEx newEdge  = new BiCompEdgeEx( (BiCompNodeEx)firstNode,
                                                       (BiCompNodeEx)secondNode);
              newEdge.setIsGenerated(true);
              g.addEdge( newEdge, currentEdge.getPreviousInOrderFrom(firstNode), nextEdge );
              counter++;
              if ( edges.size() == 2 )
              {
                break;
              }
            }
          }
        }
        logEntry.setData(counter + " edges added");
        g.stopLogEntry(logEntry);
        return true;
      }
      g.stopLogEntry(logEntry);
      return false;
    }
  }

  public static boolean isBiconnected(Graph g)
  {
    LogEntry logEntry = g.startLogEntry("Test Biconnectivity");
    boolean isBiconnected = getBiconnectedComponents(g, false).size() <= 1;
    g.stopLogEntry(logEntry);
    return isBiconnected;
  }

  public static void displayBiconnectedComponents(Graph g)
  {
    Vector graphs = getBiconnectedComponents( g, true );
    BiconnectivitySupport.colorComponentDisplays(g, graphs);
    BiconnectivitySupport.highlightSeparatingNodes(g, findSeparatingNodes(g));
    g.markForRepaint();
  }

  private static void collectConnectedGraphBiconnectedComponents(Graph oldGraph,
                                                                  Vector graphs,
                                                                  boolean copyData)
  {
    Vector newEdges = new Vector();
    Graph graph = oldGraph.copy(copyData);
    Vector nodes = graph.createNodeExtenders(new BiCompNodeEx().getClass());
    BiconnectivitySupport.resetNodeTraversalState(nodes);
    Vector edges = graph.createEdgeExtenders(new BiCompEdgeEx().getClass());
    BiconnectivitySupport.resetEdgeTraversalState(edges);

    BiCompNodeEx rootNode = (BiCompNodeEx)nodes.firstElement();
    BiCompNodeEx tempNode = rootNode;
    int i = 0;
    boolean alreadyVisited = false;
    Stack nodeStack = new Stack();
    Stack edgeStack = new Stack();

    do
    {
      if ( !alreadyVisited )
      {
        i++;
        tempNode.setNumber(i);
        tempNode.setLowNumber(i);
        nodeStack.push(tempNode);
      }

      BiCompEdgeEx tempEdge = BiconnectivitySupport.useFirstUnusedIncidentEdge(tempNode);
      boolean hasUnusedEdges = tempEdge != null;
      boolean flag = false;
      alreadyVisited = false;

      if ( hasUnusedEdges )
      {
        edgeStack.push(tempEdge);
        BiCompNodeEx otherNode = (BiCompNodeEx)tempEdge.otherEndFrom(tempNode);
        if ( otherNode.getNumber() == 0 )
        {
          otherNode.setParent(tempNode);
          tempNode = otherNode;
        }
        else
        {
          tempNode.setLowNumber(Math.min(tempNode.getLowNumber(),otherNode.getNumber()));
          alreadyVisited = true;
        }
      }

      if ( !hasUnusedEdges )
      {
        if ( tempNode.getParent().getNumber() != 1 )
        {
          if ( tempNode.getLowNumber() < tempNode.getParent().getNumber() )
          {
            tempNode.getParent().setLowNumber( Math.min( tempNode.getParent().getLowNumber(),
                                                         tempNode.getLowNumber() ) );
          }
          else
          {
            graphs.addElement(BiconnectivitySupport.buildComponentGraph(oldGraph,
                                                                        tempNode,
                                                                        (BiCompNodeEx)tempNode.getParent(),
                                                                        nodeStack,
                                                                        edgeStack,
                                                                        newEdges,
                                                                        copyData));
          }
          tempNode = (BiCompNodeEx)tempNode.getParent();
          alreadyVisited = true;
          flag = true;
        }
        if ( !flag )
        {
          graphs.addElement(BiconnectivitySupport.buildComponentGraph(oldGraph,
                                                                      tempNode,
                                                                      rootNode,
                                                                      nodeStack,
                                                                      edgeStack,
                                                                      newEdges,
                                                                      copyData));
          if ( !BiconnectivitySupport.hasUnusedIncidentEdges(rootNode) )
          {
            break;
          }
          tempNode = rootNode;
          alreadyVisited = true;
        }
      }
    }
    while ( true );
  }

  private static void markConnectedComponentSeparators(Graph g,
                                                       Vector separatingNodes,
                                                       Vector connectedNodes)
  {
    Vector edges = g.getEdgeExtenders(NodeExtender.toNode(connectedNodes));
    BiconnectivitySupport.resetConnectedNodeState(connectedNodes);
    BiconnectivitySupport.resetEdgeTraversalAndSubGraphState(edges);

    BiCompNodeEx rootNode = (BiCompNodeEx)connectedNodes.firstElement();
    BiCompNodeEx tempNode = rootNode;
    int i = 0;
    int subGraphNumber = 1;
    boolean alreadyVisited = false;
    Stack nodeStack = new Stack();
    Stack edgeStack = new Stack();

    do
    {
      if ( !alreadyVisited )
      {
        i++;
        tempNode.setNumber(i);
        tempNode.setLowNumber(i);
        nodeStack.push(tempNode);
      }

      BiCompEdgeEx tempEdge = BiconnectivitySupport.useFirstUnusedIncidentEdge(tempNode);
      boolean hasUnusedEdges = tempEdge != null;
      boolean flag = false;
      alreadyVisited = false;

      if ( hasUnusedEdges )
      {
        edgeStack.push(tempEdge);
        BiCompNodeEx otherNode = (BiCompNodeEx)tempEdge.otherEndFrom(tempNode);
        if ( otherNode.getNumber() == 0 )
        {
          otherNode.setParent(tempNode);
          tempNode = otherNode;
        }
        else
        {
          tempNode.setLowNumber(Math.min(tempNode.getLowNumber(),otherNode.getNumber()));
          alreadyVisited = true;
        }
      }

      if ( !hasUnusedEdges )
      {
        if ( tempNode.getParent().getNumber() != 1 )
        {
          if ( tempNode.getLowNumber() < tempNode.getParent().getNumber() )
          {
            tempNode.getParent().setLowNumber( Math.min( tempNode.getParent().getLowNumber(),
                                                         tempNode.getLowNumber() ) );
          }
          else
          {
            BiconnectivitySupport.markSubGraphComponent(tempNode,
                                                        (BiCompNodeEx)tempNode.getParent(),
                                                        nodeStack,
                                                        edgeStack,
                                                        subGraphNumber);
            subGraphNumber++;
            if ( !((BiCompNodeEx)tempNode.getParent()).isOld() )
            {
              ((BiCompNodeEx)tempNode.getParent()).setSubGraphNumber(0);
              ((BiCompNodeEx)tempNode.getParent()).setIsOld(true);
              separatingNodes.addElement(tempNode.getParent());
            }
          }
          tempNode = (BiCompNodeEx)tempNode.getParent();
          alreadyVisited = true;
          flag = true;
        }
        if ( !flag )
        {
          BiconnectivitySupport.markSubGraphComponent(tempNode,
                                                      rootNode,
                                                      nodeStack,
                                                      edgeStack,
                                                      subGraphNumber);
          rootNode.setSubGraphNumber(subGraphNumber);
          subGraphNumber++;
          if ( !BiconnectivitySupport.hasUnusedIncidentEdges(rootNode) )
          {
            break;
          }
          tempNode = rootNode;
          if ( !tempNode.isOld() )
          {
            tempNode.setSubGraphNumber(0);
            tempNode.setIsOld(true);
            separatingNodes.addElement(tempNode);
          }
          alreadyVisited = true;
        }
      }
    }
    while ( true );

    BiconnectivitySupport.resetSeparatingNodes(separatingNodes);
    BiconnectivitySupport.resetEdgeTraversalState(edges);
  }
}
