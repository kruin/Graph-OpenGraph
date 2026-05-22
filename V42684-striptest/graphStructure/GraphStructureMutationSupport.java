package graphStructure;

import java.util.Enumeration;
import java.util.Vector;

import graphStructure.mementos.EdgeBetweenMemento;
import graphStructure.mementos.EdgeMemento;
import graphStructure.mementos.MementoGrouper;
import graphStructure.mementos.NodeMemento;

final class GraphStructureMutationSupport
{
  private GraphStructureMutationSupport() {}

  static void addNode(Vector nodes, Node aNode, MementoGrouper currentMemento,
                      boolean trackUndos, boolean addMemento)
  {
    nodes.addElement(aNode);
    if ( addMemento && currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(NodeMemento.createCreateMemento(aNode));
    }
  }

  static Node createNode(Vector nodes, Location aPoint, MementoGrouper currentMemento,
                         boolean trackUndos)
  {
    Node newNode = new Node(aPoint);
    nodes.addElement(newNode);
    if ( currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(NodeMemento.createCreateMemento(newNode));
    }
    return newNode;
  }

  static void addEdge(EdgeInterface newEdge, EdgeInterface startPrevEdge,
                      EdgeInterface endPrevEdge, MementoGrouper currentMemento,
                      boolean trackUndos, boolean addMemento)
  {
    NodeInterface startNode = newEdge.getStartNode();
    NodeInterface endNode = newEdge.getEndNode();
    startNode.addEdgeBetween(newEdge,
                             startPrevEdge,
                             startPrevEdge.getNextInOrderFrom(startNode));
    endNode.addEdgeBetween(newEdge,
                           endPrevEdge,
                           endPrevEdge.getNextInOrderFrom(endNode));
    if ( addMemento && currentMemento != null && trackUndos )
    {
      Edge a, b, c;
      if ( newEdge instanceof EdgeExtender )
      {
        a = ((EdgeExtender)newEdge).getRef();
      }
      else
      {
        a = (Edge)newEdge;
      }
      if ( startPrevEdge instanceof EdgeExtender )
      {
        b = ((EdgeExtender)startPrevEdge).getRef();
      }
      else
      {
        b = (Edge)startPrevEdge;
      }
      if ( endPrevEdge instanceof EdgeExtender )
      {
        c = ((EdgeExtender)endPrevEdge).getRef();
      }
      else
      {
        c = (Edge)endPrevEdge;
      }
      currentMemento.addMemento(EdgeBetweenMemento.createCreateMemento(a, b, c));
    }
  }

  static void addEdge(Graph graph, Node start, Node end, MementoGrouper currentMemento,
                      boolean trackUndos, boolean addMemento)
  {
    Edge anEdge = new Edge(start, end);
    if ( start.addIncidentEdge(anEdge) && end.addIncidentEdge(anEdge) &&
         addMemento && currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(EdgeBetweenMemento.createCreateMemento(anEdge,
        (Edge)anEdge.getPreviousInOrderFrom(start),
        (Edge)anEdge.getPreviousInOrderFrom(end)));
    }
    if ( !graph.edgeNumbersAreInSync() )
    {
      System.out.println("error2: " + anEdge);
    }
  }

  static void addEdgeNoCheck(Edge anEdge, MementoGrouper currentMemento,
                             boolean trackUndos, boolean addMemento)
  {
    anEdge.getStartNode().addIncidentEdgeNoCheck(anEdge);
    anEdge.getEndNode().addIncidentEdgeNoCheck(anEdge);
    if ( addMemento && currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(EdgeBetweenMemento.createCreateMemento(anEdge,
        (Edge)anEdge.getPreviousInOrderFrom(anEdge.getStartNode()),
        (Edge)anEdge.getPreviousInOrderFrom(anEdge.getEndNode())));
    }
  }

  static void addEdgeNoCheck(NodeInterface aNode, EdgeInterface anEdge)
  {
    aNode.addIncidentEdgeNoCheck(anEdge);
  }

  static void addEdgeNoCheck(Graph graph, Node start, Node end, MementoGrouper currentMemento,
                             boolean trackUndos, boolean addMemento)
  {
    Edge anEdge = new Edge(start, end);
    start.addIncidentEdgeNoCheck(anEdge);
    end.addIncidentEdgeNoCheck(anEdge);
    if ( addMemento && currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(EdgeBetweenMemento.createCreateMemento(anEdge,
        (Edge)anEdge.getPreviousInOrderFrom(start),
        (Edge)anEdge.getPreviousInOrderFrom(end)));
    }
    if ( !graph.edgeNumbersAreInSync() )
    {
      System.out.println("error4: " + anEdge);
    }
  }

  static void addGeneratedEdgeNoCheck(Node start, Node end, MementoGrouper currentMemento,
                                      boolean trackUndos, boolean addMemento)
  {
    Edge anEdge = new Edge(start, end);
    anEdge.setIsGenerated(true);
    start.addIncidentEdgeNoCheck(anEdge);
    end.addIncidentEdgeNoCheck(anEdge);
    if ( addMemento && currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(EdgeBetweenMemento.createCreateMemento(anEdge,
        (Edge)anEdge.getPreviousInOrderFrom(start),
        (Edge)anEdge.getPreviousInOrderFrom(end)));
    }
  }

  static void deleteEdge(Edge anEdge, MementoGrouper currentMemento,
                         boolean trackUndos, boolean createMemento)
  {
    if ( createMemento && currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(EdgeBetweenMemento.createDeleteMemento(anEdge,
        (Edge)anEdge.getPreviousInOrderFrom(anEdge.getStartNode()),
        (Edge)anEdge.getPreviousInOrderFrom(anEdge.getEndNode())));
    }
    anEdge.getStartNode().deleteIncidentEdge(anEdge);
    anEdge.getEndNode().deleteIncidentEdge(anEdge);
  }

  static void deleteNode(Graph graph, Vector nodes, Node aNode, boolean addMemento,
                         MementoGrouper currentMemento, boolean trackUndos)
  {
    Enumeration someEdges = aNode.incidentEdgesInReverse().elements();
    while(someEdges.hasMoreElements())
    {
      Edge anEdge = (Edge)someEdges.nextElement();
      graph.deleteEdge(anEdge, addMemento);
    }
    nodes.removeElement(aNode);
    if ( addMemento && currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(NodeMemento.createDeleteMemento(aNode));
    }
  }

  static void makeGeneratedEdgePermanent(Edge anEdge, MementoGrouper currentMemento,
                                         boolean trackUndos)
  {
    if ( anEdge.isGenerated() )
    {
      anEdge.setIsGenerated(false);
      if ( currentMemento != null && trackUndos )
      {
        currentMemento.addMemento(EdgeMemento.createPreserveGeneratedMemento(anEdge));
      }
    }
  }

  static void makeGeneratedEdgesPermanent(Graph graph, Vector edges)
  {
    Enumeration enumEdges = edges.elements();
    while ( enumEdges.hasMoreElements() )
    {
      graph.makeGeneratedEdgePermanent((Edge)enumEdges.nextElement());
    }
  }

  static void deleteGeneratedEdges(Graph graph, Vector nodes)
  {
    Enumeration enumNodes = nodes.elements();
    while ( enumNodes.hasMoreElements() )
    {
      Node currentNode = (Node)enumNodes.nextElement();
      Vector edges = currentNode.incidentEdges();
      for ( int i=0; i<edges.size(); i++ )
      {
        Edge currentEdge = (Edge)edges.elementAt(i);
        if ( currentEdge.isGenerated() )
        {
          graph.deleteEdge(currentEdge);
        }
      }
    }
  }
}
