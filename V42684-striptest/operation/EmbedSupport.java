package operation;

import java.util.Vector;
import java.util.Enumeration;
import dataStructure.pqTree.*;
import graphStructure.*;
import operation.extenders.*;

final class EmbedSupport
{
  private EmbedSupport()
  {
  }

  static void rebuildEmbeddedComponentCopies(Graph g, Graph aGraph)
  {
    associateCopiesForComponentEdges(aGraph);
    attachCopiesToOriginalGraph(g, aGraph);
  }

  static void initialisePQEdgeLeaves(Vector edges)
  {
    PQEdgeEx anEdge;
    for ( int i=0; i<edges.size(); i++ )
    {
      anEdge = (PQEdgeEx)edges.elementAt(i);
      anEdge.setPQNode(new PQNode(anEdge));
    }
  }

  static void replaceNodesWithPQExtenders(Vector nodesInStOrder)
  {
    STNodeEx stNode;
    PQNodeEx pqNode;
    for ( int i=0; i<nodesInStOrder.size(); i++ )
    {
      stNode = (STNodeEx)nodesInStOrder.elementAt(i);
      pqNode = (PQNodeEx)stNode.getRef().getExtender();
      pqNode.setStNumber(stNode.getStNumber());
      nodesInStOrder.setElementAt(pqNode, i);
    }
  }

  static void seedRootChildren(PQTree pqTree, PQNodeEx currentNode) throws Exception
  {
    Enumeration enumEdges = currentNode.incidentEdges().elements();
    while ( enumEdges.hasMoreElements() )
    {
      pqTree.getRoot().addChild(((PQEdgeEx)enumEdges.nextElement()).getPQNode());
    }
  }

  static Vector collectIncidentPQNodes(PQNodeEx currentNode, int pivotStNumber, boolean smaller)
  {
    Vector edges = new Vector();
    Enumeration enumEdges = currentNode.incidentEdges().elements();
    PQEdgeEx anEdge;
    while ( enumEdges.hasMoreElements() )
    {
      anEdge = (PQEdgeEx)enumEdges.nextElement();
      if ( smaller )
      {
        if ( ((PQNodeEx)anEdge.otherEndFrom(currentNode)).getStNumber() < pivotStNumber )
        {
          edges.addElement(anEdge.getPQNode());
        }
      }
      else
      {
        if ( ((PQNodeEx)anEdge.otherEndFrom(currentNode)).getStNumber() > pivotStNumber )
        {
          edges.addElement(anEdge.getPQNode());
        }
      }
    }
    return edges;
  }

  static PQNode buildNewRoot(Vector edges) throws Exception
  {
    if ( edges.size() == 1 )
    {
      return (PQNode)edges.firstElement();
    }

    PQNode newRoot = new PQNode();
    Enumeration enumEdges = edges.elements();
    while ( enumEdges.hasMoreElements() )
    {
      newRoot.addChild((PQNode)enumEdges.nextElement());
    }
    return newRoot;
  }

  static void replacePertinentRoot(PQTree pqTree, PQNodeEx currentNode, PQNode pertRoot, PQNode newRoot) throws Exception
  {
    if ( pertRoot.isQNode() &&
       ( !pertRoot.isFull() || pertRoot.isPseudoNode() ) )
    {
      PQNode to = pertRoot.getFullLeavesTo();
      PQNode from = pertRoot.getFullLeavesFrom();
      pertRoot.replaceFullChildrenWith(newRoot);
      PQDNode dNode = new PQDNode(currentNode);
      dNode.setParent(newRoot.getParent());
      if ( from != null )
      {
        from.getSiblings().replacePQNode(newRoot, dNode);
        dNode.getSiblings().addPQNode(from);
        newRoot.getSiblings().replacePQNode(from, dNode);
        dNode.getSiblings().addPQNode(newRoot);
        dNode.setDirection(newRoot);
      }
      else if ( to != null )
      {
        to.getSiblings().replacePQNode(newRoot, dNode);
        dNode.getSiblings().addPQNode(to);
        newRoot.getSiblings().replacePQNode(to, dNode);
        dNode.getSiblings().addPQNode(newRoot);
        dNode.setDirection(to);
      }
      else
      {
        throw new Exception("*** ERROR neither from or to existed when adding dNode!");
      }
      if ( pertRoot.hasOnlyTwoChildren() && !pertRoot.isPseudoNode() )
      {
        validateEndMostChildren(pertRoot, 2, "*** ERROR pNode was created with dNode children!");
        pertRoot.convertToPNode();
      }
      else if ( pertRoot.hasOnlyOneChild() && !pertRoot.isPseudoNode() )
      {
        validateEndMostChildren(pertRoot, 1, "*** ERROR pNode was created with dNode child!");
        if ( pertRoot == pqTree.getRoot() )
        {
          newRoot.becomeRoot();
          pqTree.setRoot(newRoot);
        }
        else
        {
          pertRoot.getParent().replaceChild(pertRoot, newRoot);
        }
      }
    }
    else
    {
      if ( pertRoot == pqTree.getRoot() )
      {
        pqTree.setRoot(newRoot);
      }
      else
      {
        pertRoot.getParent().replaceChild(pertRoot, newRoot);
      }
    }
    pertRoot.clear();
  }

  static void reverseUpwardEdges(Vector nodesInStOrder, Vector upwardEdges)
  {
    boolean reverse;
    int indexOfNodeToReverse;
    Vector edgesOfNodeToReverse, edgesOfNodeReversed;
    for ( int h=nodesInStOrder.size()-1; h>=0; h-- )
    {
      Vector fullLeaves = (Vector)upwardEdges.elementAt(h);
      PQNode pqNode;
      for ( int k=0; k< fullLeaves.size(); k++ )
      {
        pqNode = (PQNode)fullLeaves.elementAt(k);
        if ( pqNode.isDNode() )
        {
          reverse = ((PQDNode)pqNode).readInReverseDirection();

          if ( reverse )
          {
            indexOfNodeToReverse = ((PQNodeEx)pqNode.getData()).getStNumber()-1;
            edgesOfNodeToReverse = (Vector)upwardEdges.elementAt(indexOfNodeToReverse);
            edgesOfNodeReversed = new Vector();
            for ( int x=edgesOfNodeToReverse.size()-1; x>=0; x-- )
            {
              if ( ((PQNode)edgesOfNodeToReverse.elementAt(x)).isDNode() )
              {
                ((PQDNode)edgesOfNodeToReverse.elementAt(x)).toggleReadInReverseDirection();
              }
              edgesOfNodeReversed.addElement(edgesOfNodeToReverse.elementAt(x));
            }
            upwardEdges.setElementAt(edgesOfNodeReversed, indexOfNodeToReverse);
          }
        }
      }
    }
  }

  static void applyIncidentEdgeOrder(Vector nodesInStOrder, Vector upwardEdges)
  {
    PQNodeEx currentNode;
    PQNode pqNode;
    Vector fullLeaves;
    for ( int h=0; h<nodesInStOrder.size(); h++ )
    {
      currentNode = (PQNodeEx)nodesInStOrder.elementAt(h);
      fullLeaves = (Vector)upwardEdges.elementAt(h);
      currentNode.resetIncidentEdges();
      for ( int k=0; k< fullLeaves.size(); k++ )
      {
        pqNode = (PQNode)fullLeaves.elementAt(k);
        if ( !pqNode.isDNode() )
        {
          currentNode.addIncidentEdgeNoCheck((PQEdgeEx)pqNode.getData());
        }
      }
    }
  }

  static void resetOldFlags(Vector nodes)
  {
    for ( int i=0; i<nodes.size(); i++ )
    {
      ((PQNodeEx)nodes.elementAt(i)).setIsOld(false);
    }
  }

  private static void associateCopiesForComponentEdges(Graph aGraph)
  {
    Vector edges = aGraph.getEdges();
    Edge anEdge;
    Node sNode, dNode;
    for ( int h=0; h<edges.size(); h++ )
    {
      anEdge = (Edge)edges.elementAt(h);
      sNode = (Node)anEdge.getStartNode();
      dNode = (Node)anEdge.getEndNode();
      if ( anEdge.getDirectedSourceNode() != null )
      {
        anEdge.setCopy(new Edge((Edge)anEdge.getMasterCopy(), anEdge.getDirectedSourceNode().getMasterCopy(),
            (Node)sNode.getMasterCopy(), (Node)dNode.getMasterCopy()));
      }
      else
      {
        anEdge.setCopy(new Edge((Edge)anEdge.getMasterCopy(), null,
            (Node)sNode.getMasterCopy(), (Node)dNode.getMasterCopy()));
      }
    }
  }

  private static void attachCopiesToOriginalGraph(Graph g, Graph aGraph)
  {
    Vector nodes = aGraph.getNodes();
    Node aNode, sNode;
    Edge anEdge;
    for ( int j=0; j<nodes.size(); j++ )
    {
      aNode = (Node)nodes.elementAt(j);
      for ( int k=0; k<aNode.incidentEdges().size(); k++ )
      {
        anEdge = (Edge)aNode.incidentEdges().elementAt(k);
        sNode = aNode;
        while ( sNode.getCopy() != null )
        {
          sNode = (Node)sNode.getCopy();
        }
        g.addEdgeNoCheck(sNode, anEdge.getCopy());
      }
    }
  }

  private static void validateEndMostChildren(PQNode pertRoot, int expectedSize, String dNodeMessage) throws Exception
  {
    if ( pertRoot.getEndMostChildren().size() == expectedSize )
    {
      for ( int i=0; i<expectedSize; i++ )
      {
        if ( ((PQNode)pertRoot.getEndMostChildren().PQNodeAt(i)).isDNode() )
        {
          throw new Exception(dNodeMessage);
        }
      }
    }
    else
    {
      throw new Exception("*** ERROR endMostChildren did not have size " + expectedSize + "!");
    }
  }
}
