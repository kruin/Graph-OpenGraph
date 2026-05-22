package graphStructure;

import java.awt.Graphics2D;
import java.util.Vector;

import graphStructure.mementos.EdgeMovementMemento;
import graphStructure.mementos.MementoGrouper;
import graphStructure.mementos.NodeMovementMemento;

final class GraphTransformSupport
{
  private GraphTransformSupport() {}

  static void draw(Graphics2D g2, Vector edges, Vector nodes, int xOffset, int yOffset,
                   boolean drawSelected, boolean showCoords, boolean showLabels)
  {
    for ( int i=0; i<edges.size(); i++ )
    {
      ((Edge)edges.elementAt(i)).draw(g2, xOffset, yOffset, drawSelected);
    }
    for ( int i=0; i<nodes.size(); i++ )
    {
      ((Node)nodes.elementAt(i)).draw(g2, xOffset, yOffset,
                                      drawSelected, showCoords, showLabels);
    }
  }

  static void rotate(Vector nodes, Vector edges, Location pivotPoint, double angle,
                     MementoGrouper currentMemento, boolean trackUndos, boolean createMemento)
  {
    boolean memento = currentMemento != null && trackUndos && createMemento;
    for ( int i=0; i<nodes.size(); i++ )
    {
      Node currentNode = (Node)nodes.elementAt(i);
      if ( memento )
      {
        currentMemento.addMemento(NodeMovementMemento.createMoveMemento(currentNode));
      }
      currentNode.rotate(pivotPoint, angle);
    }
    for ( int i=0; i<edges.size(); i++ )
    {
      Edge anEdge = (Edge)edges.elementAt(i);
      if ( memento )
      {
        currentMemento.addMemento(EdgeMovementMemento.createMoveMemento(anEdge));
      }
      anEdge.rotate(pivotPoint, angle);
      anEdge.update();
    }
  }
}
