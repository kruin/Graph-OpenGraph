package graphStructure;

import java.util.Vector;

import graphStructure.mementos.EdgeMovementMemento;
import graphStructure.mementos.MementoGrouper;
import graphStructure.mementos.NodeMovementMemento;

final class GraphEdgeMutationSupport
{
  private GraphEdgeMutationSupport() {}

  static void translateNode(Node aNode, int dx, int dy, MementoGrouper currentMemento,
                            boolean trackUndos, boolean createMemento)
  {
    boolean memento = currentMemento != null && trackUndos && createMemento;
    if ( memento )
    {
      currentMemento.addMemento(NodeMovementMemento.createMoveMemento(aNode));
    }
    aNode.translate(dx, dy);
    Vector edges = aNode.incidentEdges();
    for ( int i=0; i<edges.size(); i++ )
    {
      Edge anEdge = (Edge)edges.elementAt(i);
      if ( memento )
      {
        currentMemento.addMemento(EdgeMovementMemento.createMoveMemento(anEdge));
      }
      anEdge.update();
    }
  }

  static void relocateNode(NodeInterface aNode, Location aLocation, MementoGrouper currentMemento,
                           boolean trackUndos, boolean createMemento)
  {
    boolean memento = currentMemento != null && trackUndos && createMemento;
    if ( memento )
    {
      currentMemento.addMemento(NodeMovementMemento.createMoveMemento(aNode));
    }
    aNode.setLocation(aLocation);
  }

  static void translateNodes(Vector someNodes, int dx, int dy, MementoGrouper currentMemento,
                             boolean trackUndos, boolean createMemento)
  {
    boolean memento = currentMemento != null && trackUndos && createMemento;
    for ( int i=0; i<someNodes.size(); i++ )
    {
      Node aNode = (Node)someNodes.elementAt(i);
      if ( memento )
      {
        currentMemento.addMemento(NodeMovementMemento.createMoveMemento(aNode));
      }
      aNode.translate(dx, dy);
    }
    refreshEdgeCurves(GraphStatsSupport.getEdges(someNodes, true));
  }

  static void relocateEdge(Edge anEdge, Location newLocation, MementoGrouper currentMemento,
                           boolean trackUndos, boolean createMemento)
  {
    if ( currentMemento != null && trackUndos && createMemento )
    {
      currentMemento.addMemento(EdgeMovementMemento.createMoveMemento(anEdge));
    }
    anEdge.setCenterLocation(newLocation);
  }

  static void curveEdge(Edge anEdge, int dx, int dy, MementoGrouper currentMemento,
                        boolean trackUndos, boolean createMemento)
  {
    if ( currentMemento != null && trackUndos && createMemento )
    {
      currentMemento.addMemento(EdgeMovementMemento.createMoveMemento(anEdge));
    }
    anEdge.makeCurved();
    anEdge.translate(dx, dy);
  }

  static void orthogonalizeEdge(Edge anEdge, MementoGrouper currentMemento,
                                boolean trackUndos, boolean createMemento)
  {
    if ( currentMemento != null && trackUndos && createMemento )
    {
      currentMemento.addMemento(EdgeMovementMemento.createMoveMemento(anEdge));
    }
    anEdge.makeOrthogonal();
  }

  static void straightenEdge(EdgeInterface anEdge, MementoGrouper currentMemento,
                             boolean trackUndos, boolean createMemento)
  {
    if ( currentMemento != null && trackUndos && createMemento )
    {
      currentMemento.addMemento(EdgeMovementMemento.createMoveMemento(anEdge));
    }
    anEdge.makeStraight();
  }

  static void straightenEdges(Vector edges, MementoGrouper currentMemento,
                              boolean trackUndos, boolean createMemento)
  {
    for ( int i=0; i<edges.size(); i++ )
    {
      straightenEdge((EdgeInterface)edges.elementAt(i), currentMemento, trackUndos, createMemento);
    }
  }

  static void updateEdge(EdgeInterface anEdge, MementoGrouper currentMemento,
                         boolean trackUndos, boolean createMemento)
  {
    if ( currentMemento != null && trackUndos && createMemento )
    {
      currentMemento.addMemento(EdgeMovementMemento.createMoveMemento(anEdge));
    }
    anEdge.update();
  }

  static void updateEdges(Vector edges, MementoGrouper currentMemento,
                          boolean trackUndos, boolean createMemento)
  {
    for ( int i=0; i<edges.size(); i++ )
    {
      updateEdge((EdgeInterface)edges.elementAt(i), currentMemento, trackUndos, createMemento);
    }
  }

  static void refreshEdgeCurves(Vector edges)
  {
    for ( int i=0; i<edges.size(); i++ )
    {
      ((Edge)edges.elementAt(i)).update();
    }
  }

  static void updateEdgeCurveAngles(Vector edges)
  {
    for ( int i=0; i<edges.size(); i++ )
    {
      ((Edge)edges.elementAt(i)).initCurveAngles();
    }
  }

  static void refreshOrthogonalEdges(Vector edges)
  {
    for ( int i=0; i<edges.size(); i++ )
    {
      Edge anEdge = (Edge)edges.elementAt(i);
      if ( anEdge.isOrthogonal() )
      {
        Location location = anEdge.getOrthogonalLocation();
        if ( location == null )
        {
          anEdge.setCenterLocation(anEdge.getNormalLocation());
        }
        else
        {
          anEdge.setCenterLocation(location);
        }
      }
      else
      {
        anEdge.setCenterLocation(anEdge.getNormalLocation());
      }
    }
  }
}