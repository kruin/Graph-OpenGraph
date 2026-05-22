package graphStructure;

import java.awt.Color;

import graphStructure.mementos.EdgeColorMemento;
import graphStructure.mementos.EdgeDirectionMemento;
import graphStructure.mementos.MementoGrouper;
import graphStructure.mementos.NodeColorMemento;
import graphStructure.mementos.NodeDrawXMemento;
import graphStructure.mementos.NodeLabelMemento;

final class GraphPropertyMutationSupport
{
  private GraphPropertyMutationSupport() {}

  static void changeNodeLabel(NodeInterface aNode, String label,
                              MementoGrouper currentMemento,
                              boolean trackUndos, boolean createMemento)
  {
    if ( currentMemento != null && trackUndos && createMemento )
    {
      currentMemento.addMemento(NodeLabelMemento.createLabelMemento(aNode));
    }
    aNode.setLabel(label);
  }

  static void changeNodeDrawX(NodeInterface aNode, boolean drawX,
                              MementoGrouper currentMemento,
                              boolean trackUndos, boolean createMemento)
  {
    if ( currentMemento != null && trackUndos && createMemento )
    {
      currentMemento.addMemento(NodeDrawXMemento.createDrawXMemento(aNode));
    }
    aNode.setDrawX(drawX);
  }

  static void changeNodeColor(NodeInterface aNode, Color aColor,
                              MementoGrouper currentMemento,
                              boolean trackUndos, boolean createMemento)
  {
    if ( currentMemento != null && trackUndos && createMemento )
    {
      currentMemento.addMemento(NodeColorMemento.createColorMemento(aNode));
    }
    aNode.setColor(aColor);
  }

  static void changeEdgeColor(EdgeInterface anEdge, Color aColor,
                              MementoGrouper currentMemento,
                              boolean trackUndos, boolean createMemento)
  {
    if ( currentMemento != null && trackUndos && createMemento )
    {
      currentMemento.addMemento(EdgeColorMemento.createColorMemento(anEdge));
    }
    anEdge.setColor(aColor);
  }

  static void changeEdgeDirection(EdgeInterface anEdge, NodeInterface sourceNode,
                                  MementoGrouper currentMemento,
                                  boolean trackUndos, boolean createMemento)
  {
    if ( currentMemento != null && trackUndos && createMemento )
    {
      currentMemento.addMemento(EdgeDirectionMemento.createDirectionMemento(anEdge));
    }
    anEdge.setDirectedFrom(sourceNode);
  }
}
