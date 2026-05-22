package operation;

import java.util.Vector;
import java.util.Enumeration;
import dataStructure.pqTree.*;
import graphStructure.*;
import graphException.*;
import operation.extenders.*;

public class EmbedOperation
{
  public static void embed(Graph g) throws Exception
  {
    embed(g, true);
  }

  public static void embed(Graph g, boolean check) throws Exception
  {
    LogEntry logEntry = g.startLogEntry("Embedding");
    if ( check && !PlanarityOperation.isPlanar(g) )
    {
      logEntry.setData("Graph was not Planar");
      g.stopLogEntry(logEntry);
      throw new GraphException("Graph is not planar!");
    }
    else
    {
      Vector graphs = BiconnectivityOperation.getBiconnectedComponents(g, true);
      Graph aGraph;
      g.deleteAllEdges();

      for ( int i=0; i<graphs.size(); i++ )
      {
        aGraph = (Graph)graphs.elementAt(i);
        if ( aGraph.getNumNodes() > 2 )
        {
          PQNodeEx tNode = upwardEmbed(aGraph);
          if ( tNode != null )
          {
            entireEmbed(aGraph, tNode);
          }
        }
        EmbedSupport.rebuildEmbeddedComponentCopies(g, aGraph);
      }
      g.stopLogEntry(logEntry);
    }
  }

  private static PQNodeEx upwardEmbed(Graph g) throws Exception
  {
    LogEntry logEntry = g.startLogEntry("Upward Embedding");
    if ( g.getNumNodes() == 2 )
    {
      g.stopLogEntry(logEntry);
      return null;
    }

    PQNodeEx tNode = null;
    Vector edges;
    Vector fullLeaves;
    Vector upwardEdges = new Vector();
    Enumeration enumEdges;
    PQEdgeEx anEdge;

    try
    {
      PQTree pqTree = new PQTree();

      Vector nodesInStOrder = STNumberOperation.stNumber(g, false);

      g.createNodeExtenders((new PQNodeEx()).getClass());
      edges = g.createEdgeExtenders((new PQEdgeEx()).getClass());
      EmbedSupport.initialisePQEdgeLeaves(edges);

      EmbedSupport.replaceNodesWithPQExtenders(nodesInStOrder);

      tNode = (PQNodeEx)nodesInStOrder.lastElement();
      PQNodeEx currentNode = (PQNodeEx)nodesInStOrder.firstElement();
      upwardEdges.addElement(new Vector());
      EmbedSupport.seedRootChildren(pqTree, currentNode);

      for ( int j=2; j<=nodesInStOrder.size(); j++ )
      {
        currentNode = (PQNodeEx)nodesInStOrder.elementAt(j-1);
        edges = EmbedSupport.collectIncidentPQNodes(currentNode, j, true);

        PQNode pertRoot = pqTree.reduction(edges);

        if ( pqTree.isNullTree() )
        {
          throw new Exception("A PQ-Tree reduction returned a null tree during upwardEmbed!");
        }

        fullLeaves = pertRoot.getFullLeaves();
        if ( fullLeaves != null )
        {
          upwardEdges.addElement(fullLeaves);
        }
        else
        {
          throw new Exception("*** ERROR no full leaves were returned during embedding!");
        }

        if ( j < nodesInStOrder.size() )
        {
          edges = EmbedSupport.collectIncidentPQNodes(currentNode, j, false);
          PQNode newRoot = EmbedSupport.buildNewRoot(edges);

          EmbedSupport.replacePertinentRoot(pqTree, currentNode, pertRoot, newRoot);
        }
      }

      EmbedSupport.reverseUpwardEdges(nodesInStOrder, upwardEdges);
      EmbedSupport.applyIncidentEdgeOrder(nodesInStOrder, upwardEdges);
    }
    catch (Exception e)
    {
      System.out.println("PQTree error during embedding test");
      g.stopLogEntry(logEntry);
      throw e;
    }
    g.stopLogEntry(logEntry);
    return tNode;
  }

  private static void entireEmbed(Graph g, PQNodeEx tNode)
  {
    LogEntry logEntry = g.startLogEntry("Entire Embedding");
    Vector nodes = g.getNodeExtenders();
    EmbedSupport.resetOldFlags(nodes);
    entireEmbedHelper(g, tNode);
    EmbedSupport.resetOldFlags(nodes);
    g.stopLogEntry(logEntry);
  }

  private static void entireEmbedHelper(Graph g, PQNodeEx aNode)
  {
    aNode.setIsOld(true);
    PQNodeEx otherNode;
    PQEdgeEx currentEdge;
    Vector incidentEdges = aNode.incidentEdges();
    for ( int i=incidentEdges.size()-1; i>=0; i-- )
    {
      currentEdge = (PQEdgeEx)incidentEdges.elementAt(i);
      otherNode = (PQNodeEx)currentEdge.otherEndFrom(aNode);
      if ( otherNode.getStNumber() < aNode.getStNumber() )
      {
        otherNode.addIncidentEdgeNoCheck(currentEdge); // FIXME what order?
        if ( !otherNode.isOld() )
        {
          entireEmbedHelper(g, otherNode);
        }
      }
    }
  }
}