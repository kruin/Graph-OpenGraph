package operation;

import java.util.Vector;
import java.awt.Color;
import graphStructure.*;
import operation.extenders.*;
import dataStructure.binaryHeap.*;


public class OpenGraphShortestPathOperation
{
  public static Graph getShortestPath(Graph g, Node source, Node dest)
  {
    return getShortestPath(g, source, dest, false);
  }

  public static Graph getShortestPath(Graph g, Node source, Node dest, boolean copyData)
  {
    LogEntry logEntry = g.startLogEntry("Dijkstra Shortest Path");
    if ( g.getNumNodes() < 2 )
    {
      logEntry.setData("Graph had less than 2 Nodes");
      g.stopLogEntry(logEntry);
      return null;
    }
    
    BinaryHeap heap = new BinaryHeap();
    double length;
    boolean destReached = false;

    OpenGraphNodeEx sourceNode, destNode, currentNode, otherNode;
    OpenGraphEdgeEx currentEdge, dummyEdge = new OpenGraphEdgeEx();
    Vector incidentEdges, openGraphEdges = new Vector();

    //g.clearNodeLabels();
    //g.removeEdgeDirections();

    Vector nodes = g.createNodeExtenders(new OpenGraphNodeEx().getClass());
    g.createEdgeExtenders(new OpenGraphEdgeEx().getClass());

    for ( int i=0; i<nodes.size(); i++ )
    {
      currentNode = (OpenGraphNodeEx)nodes.elementAt(i);
      currentNode.setCost(Double.MAX_VALUE);
      currentNode.setTraceBackEdge(null);
      currentNode.setIsDone(false);
    }

    sourceNode = (OpenGraphNodeEx)source.getExtender();
    destNode = (OpenGraphNodeEx)dest.getExtender();

    sourceNode.setCost(0.0);
    sourceNode.setTraceBackEdge(dummyEdge);
    sourceNode.setIsDone(true);

    heap.insert(sourceNode);

    do
    {
      currentNode = (OpenGraphNodeEx)heap.extractMin();
      if ( currentNode.getCost() == Double.MAX_VALUE )
      {
        logEntry.setData("No Path Found");
        g.stopLogEntry(logEntry);
        return null;
      }
      currentNode.setIsDone(true);
      if ( currentNode == destNode )
      {
        traceBack(destNode, dummyEdge, openGraphEdges);
        destReached = true;
        break;
      }
      incidentEdges = currentNode.incidentOutgoingEdges();
      for ( int i=0; i<incidentEdges.size(); i++ )
      {
        currentEdge = (OpenGraphEdgeEx)incidentEdges.elementAt(i);
        length = currentEdge.getLength();
        otherNode = (OpenGraphNodeEx)currentEdge.otherEndFrom(currentNode);
        if ( !otherNode.isDone() &&
             currentNode.getCost() + length < otherNode.getCost() )
        {
          otherNode.setCost(currentNode.getCost() + length);
          if ( !otherNode.isUsed() )
          {
            heap.insert(otherNode);
          }
          else
          {
            heap.decreaseKey(otherNode);
          }
          otherNode.setTraceBackEdge(currentEdge);
        }
      }
    }
    while ( !heap.isEmpty() );

    if ( destReached )
    {
      logEntry.setData("Path Length: " + destNode.getCost());
      g.stopLogEntry(logEntry);
      return g.copyEdges(openGraphEdges, copyData);
    }
    else
    {
      logEntry.setData("No Path Found");
      g.stopLogEntry(logEntry);
      return null;
    }
  }

  private static void traceBack(OpenGraphNodeEx node, OpenGraphEdgeEx dummyEdge, Vector openGraphEdges)
  {
    if ( node.getTraceBackEdge() != null )
    {
      if ( node.getTraceBackEdge() != dummyEdge )
      {
        openGraphEdges.addElement(node.getTraceBackEdge().getRef());
        traceBack( (OpenGraphNodeEx)node.getTraceBackEdge().otherEndFrom(node), dummyEdge, openGraphEdges );
      }
    }
  }

  public static double drawShortestPath(Graph g, Node source, Node dest)
  {
    Graph gc = getShortestPath(g, source, dest, true);
    Edge anEdge;
    Node aNode;

    Vector nodes = g.getNodes();
    for ( int i=0; i<nodes.size(); i++ )
    {
      aNode = (Node)nodes.elementAt(i);
      g.changeNodeColor(aNode, Node.DEFAULT_COLOR, true);
      g.changeNodeDrawX(aNode, false, true);
    }

    Vector edges = g.getEdges();
    for ( int i=0; i<edges.size(); i++ )
    {
      anEdge = (Edge)edges.elementAt(i);
      g.changeEdgeColor(anEdge, Edge.DEFAULT_COLOR, true);
    }

    if ( gc == null )
    {
      g.markForRepaint();
      return -1;
    }
    else
    {
      double pathLength = 0;
      nodes = gc.getNodes();
      for ( int i=0; i<nodes.size(); i++ )
      {
        aNode = (Node)nodes.elementAt(i);
        g.changeNodeColor(aNode.getCopy(), Color.green, true);
      }
      edges = gc.getEdges();
      for ( int i=0; i<edges.size(); i++ )
      {
        anEdge = (Edge)edges.elementAt(i);
        g.changeEdgeColor(anEdge.getCopy(), Color.green, true);
        pathLength+= anEdge.getLength();
      }
      g.markForRepaint();
      return pathLength;
    }
  }
}