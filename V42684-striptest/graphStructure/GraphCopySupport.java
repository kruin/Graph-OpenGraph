package graphStructure;

import java.util.Vector;

/**
 * Helper methods for copying graphs, selected nodes, and selected edges
 * without changing the public API exposed by Graph.
 */
public final class GraphCopySupport
{
  private GraphCopySupport()
  {
  }

  public static Graph copy(Graph sourceGraph)
  {
    return copyNodes(sourceGraph, sourceGraph.getNodes(), false);
  }

  public static Graph copy(Graph sourceGraph, boolean keepReferences)
  {
    return copyNodes(sourceGraph, sourceGraph.getNodes(), keepReferences);
  }

  public static Graph copyNodes(Graph sourceGraph, Vector nodeVector)
  {
    return copyNodes(sourceGraph, nodeVector, false);
  }

  public static Graph copyNodes(Graph sourceGraph, Vector nodeVector, boolean keepReferences)
  {
    Graph newGraph = new Graph(sourceGraph);
    Vector sourceEdges = sourceGraph.getEdges(nodeVector);
    Vector originalNodeCopies = new Vector(sourceGraph.getNodes().size());
    Vector originalEdgeCopies = new Vector(sourceEdges.size());

    copyNodesIntoGraph(newGraph, nodeVector, originalNodeCopies);
    copyEdgesForNodes(nodeVector, sourceEdges, originalEdgeCopies);
    attachCopiedIncidentEdges(nodeVector);
    restoreNodeCopyReferences(nodeVector, originalNodeCopies, keepReferences);
    restoreEdgeCopyReferences(sourceEdges, originalEdgeCopies, keepReferences);

    return newGraph;
  }

  public static Graph copyNode(Graph sourceGraph, Node aNode)
  {
    return copyNode(sourceGraph, aNode, false, false);
  }

  public static Graph copyNode(Graph sourceGraph, Node aNode, boolean keepCopyReferences)
  {
    return copyNode(sourceGraph, aNode, keepCopyReferences, false);
  }

  public static Graph copyNode(Graph sourceGraph, Node aNode, boolean keepCopyReferences,
                               boolean updateCopyReferences)
  {
    Graph newGraph = new Graph(sourceGraph);
    Node newNode = new Node(aNode);
    if ( updateCopyReferences )
    {
      newNode.setCopy(aNode.getCopy());
    }
    else
    {
      newNode.setCopy(aNode);
    }
    newGraph.addNode(newNode);
    if ( !keepCopyReferences )
    {
      newNode.setCopy(null);
    }
    return newGraph;
  }

  public static Graph copyEdges(Graph sourceGraph, Vector edges)
  {
    return copyEdges(sourceGraph, edges, false);
  }

  public static Graph copyEdges(Graph sourceGraph, Vector edges, boolean keepCopyReferences)
  {
    Graph newGraph = new Graph(sourceGraph);
    Vector sourceNodes = collectSourceNodes(edges);
    Vector originalNodeCopies = new Vector(sourceNodes.size());

    copyNodesIntoGraph(newGraph, sourceNodes, originalNodeCopies);
    copySelectedEdges(edges);
    restoreCopiedEdgeReferences(newGraph, edges, sourceNodes, originalNodeCopies, keepCopyReferences);

    return newGraph;
  }

  private static void copyNodesIntoGraph(Graph newGraph, Vector sourceNodes, Vector originalNodeCopies)
  {
    Node currentNode;
    Node newNode;

    for ( int i=0; i<sourceNodes.size(); i++ )
    {
      currentNode = (Node)sourceNodes.elementAt(i);
      originalNodeCopies.addElement(currentNode.getCopy());
      newNode = new Node(currentNode);
      newNode.setCopy(currentNode);
      currentNode.setCopy(newNode);
      newGraph.addNode(newNode);
    }
  }

  private static void copyEdgesForNodes(Vector nodeVector, Vector sourceEdges, Vector originalEdgeCopies)
  {
    Edge currentEdge;
    Edge newEdge;

    for ( int i=0; i<sourceEdges.size(); i++ )
    {
      currentEdge = (Edge)sourceEdges.elementAt(i);
      originalEdgeCopies.addElement(currentEdge.getCopy());
      if ( currentEdge.getDirectedSourceNode() != null )
      {
        newEdge = new Edge(currentEdge, currentEdge.getDirectedSourceNode().getCopy(),
            currentEdge.getStartNode().getCopy(), currentEdge.getEndNode().getCopy());
      }
      else
      {
        newEdge = new Edge(currentEdge, null,
            currentEdge.getStartNode().getCopy(), currentEdge.getEndNode().getCopy());
      }
      newEdge.setCopy(currentEdge);
      currentEdge.setCopy(newEdge);
    }
  }

  private static void attachCopiedIncidentEdges(Vector nodeVector)
  {
    Node currentNode;
    Edge currentEdge;
    Vector incidentEdges;

    for ( int i=0; i<nodeVector.size(); i++ )
    {
      currentNode = (Node)nodeVector.elementAt(i);
      incidentEdges = currentNode.incidentEdges();
      for ( int j=0; j<incidentEdges.size(); j++ )
      {
        currentEdge = (Edge)incidentEdges.elementAt(j);
        currentNode.getCopy().addIncidentEdgeNoCheck(currentEdge.getCopy());
      }
    }
  }

  private static void restoreNodeCopyReferences(Vector sourceNodes, Vector originalNodeCopies,
                                                boolean keepReferences)
  {
    Node currentNode;

    for ( int i=0; i<sourceNodes.size(); i++ )
    {
      currentNode = (Node)sourceNodes.elementAt(i);
      if ( !keepReferences )
      {
        currentNode.getCopy().setCopy(null);
      }
      currentNode.setCopy((Node)originalNodeCopies.elementAt(i));
    }
  }

  private static void restoreEdgeCopyReferences(Vector sourceEdges, Vector originalEdgeCopies,
                                                boolean keepReferences)
  {
    Edge currentEdge;

    for ( int i=0; i<sourceEdges.size(); i++ )
    {
      currentEdge = (Edge)sourceEdges.elementAt(i);
      if ( !keepReferences )
      {
        currentEdge.getCopy().setCopy(null);
      }
      currentEdge.setCopy((Edge)originalEdgeCopies.elementAt(i));
    }
  }

  private static Vector collectSourceNodes(Vector edges)
  {
    Vector sourceNodes = new Vector(2*edges.size());
    Edge currentEdge;
    Node startNode;
    Node endNode;

    for ( int j=0; j<edges.size(); j++ )
    {
      currentEdge = (Edge)edges.elementAt(j);
      currentEdge.setIsAdded(false);
      ((Node)currentEdge.getStartNode()).setIsAdded(false);
      ((Node)currentEdge.getEndNode()).setIsAdded(false);
    }

    for ( int j=0; j<edges.size(); j++ )
    {
      currentEdge = (Edge)edges.elementAt(j);
      startNode = (Node)currentEdge.getStartNode();
      endNode = (Node)currentEdge.getEndNode();
      if ( !startNode.isAdded() )
      {
        startNode.setIsAdded(true);
        sourceNodes.addElement(startNode);
      }
      if ( !endNode.isAdded() )
      {
        endNode.setIsAdded(true);
        sourceNodes.addElement(endNode);
      }
    }
    return sourceNodes;
  }

  private static void copySelectedEdges(Vector edges)
  {
    Edge currentEdge;
    Edge newEdge;

    for ( int j=0; j<edges.size(); j++ )
    {
      currentEdge = (Edge)edges.elementAt(j);
      if ( !currentEdge.isAdded() )
      {
        currentEdge.setIsAdded(true);
        if ( currentEdge.getDirectedSourceNode() != null )
        {
          newEdge = new Edge(currentEdge, currentEdge.getDirectedSourceNode().getCopy(),
              currentEdge.getStartNode().getCopy(), currentEdge.getEndNode().getCopy());
        }
        else
        {
          newEdge = new Edge(currentEdge, null,
              currentEdge.getStartNode().getCopy(), currentEdge.getEndNode().getCopy());
        }
        newEdge.setCopy(currentEdge);
        currentEdge.getStartNode().getCopy().addIncidentEdgeNoCheck(newEdge);
        currentEdge.getEndNode().getCopy().addIncidentEdgeNoCheck(newEdge);
      }
    }
  }

  private static void restoreCopiedEdgeReferences(Graph newGraph, Vector sourceEdges, Vector sourceNodes,
                                                  Vector originalNodeCopies, boolean keepCopyReferences)
  {
    Node currentNode;
    Edge currentEdge;
    Edge newEdge;
    Vector newEdges = newGraph.getEdges();

    for ( int i=0; i<sourceNodes.size(); i++ )
    {
      currentNode = (Node)sourceNodes.elementAt(i);
      if ( !keepCopyReferences )
      {
        currentNode.getCopy().setCopy(null);
      }
      currentNode.setCopy((Node)originalNodeCopies.elementAt(i));
      currentNode.setIsAdded(false);
    }

    for ( int j=0; j<newEdges.size(); j++ )
    {
      currentEdge = (Edge)sourceEdges.elementAt(j);
      newEdge = (Edge)newEdges.elementAt(j);
      if ( !keepCopyReferences )
      {
        newEdge.setCopy(null);
      }
      currentEdge.setIsAdded(false);
    }
  }
}
