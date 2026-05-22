package operation.extenders;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import graphStructure.*;
import dataStructure.binaryHeap.*;

public class OpenGraphNodeEx extends NodeExtender implements HeapObject
{
  protected OpenGraphNodeEx parent;
  protected int subTreeSize;
  protected int gridX, gridY;
  protected int boundX, boundY;
  protected int boundWidth, boundHeight;

  protected int subTreeWidth, subTreeHeight;
  protected boolean subTreeDone;
  private double cost;
  private boolean isDone;
  private OpenGraphEdgeEx traceBackEdge;
  private HeapNode heapNode;

  public OpenGraphNodeEx()
  {
    super();
    parent = null;
    cost = 0;
    isDone = false;
    traceBackEdge = null;
    heapNode = null;
    subTreeDone = false;
  }

  public double getCost() { return cost; }

  public void setCost(double c) { cost = c; }

  public boolean isDone() { return isDone; }

  public void setIsDone(boolean d) { isDone = d; }

  public OpenGraphEdgeEx getTraceBackEdge() { return traceBackEdge; }

  public void setTraceBackEdge(OpenGraphEdgeEx t) { traceBackEdge = t; }

  public boolean isUsed() { return traceBackEdge != null; }

  public HeapNode getHeapNode() { return heapNode; }

  public void setHeapNode(HeapNode hn) { heapNode = hn; }

  public void setParent(OpenGraphNodeEx parent) { this.parent = parent; }

  public OpenGraphNodeEx getParent() { return parent; }

  /**
   * Returns child nodes in a deterministic structural order.
   *
   * The old implementation inherited the circular incident-edge order from the
   * editor and treated the first encountered child as "left" and the second as
   * "right". That makes the rendered tree depend on save/load history and on
   * the order in which edges were inserted, not on the intended structure.
   *
   * Here the order is derived from the source drawing geometry before the new
   * layout is computed: first by X (left to right), then by Y (top to bottom),
   * then by node index as a stable fallback.
   */
  public Vector getChildren()
  {
    Vector edges = incidentEdges();
    Vector children = new Vector(edges.size());
    OpenGraphNodeEx node;
    for ( int i=0; i<edges.size(); i++ )
    {
      node = (OpenGraphNodeEx)((OpenGraphEdgeEx)edges.elementAt(i)).otherEndFrom(this);
      if ( node != parent )
      {
        children.addElement(node);
      }
    }

    Collections.sort(children, new Comparator()
    {
      public int compare(Object a, Object b)
      {
        OpenGraphNodeEx left = (OpenGraphNodeEx)a;
        OpenGraphNodeEx right = (OpenGraphNodeEx)b;

        int dx = left.getNode().getX() - right.getNode().getX();
        if ( dx != 0 )
        {
          return dx;
        }

        int dy = left.getNode().getY() - right.getNode().getY();
        if ( dy != 0 )
        {
          return dy;
        }

        return left.getRef().getIndex() - right.getRef().getIndex();
      }
    });

    return children;
  }

  public OpenGraphNodeEx getLeftChild()
  {
    if ( (parent == null && (refNode.getNumEdges() > 2 ||
                             refNode.getNumEdges() < 1)) ||
         (parent != null && (refNode.getNumEdges() > 3 ||
                             refNode.getNumEdges() < 2)) )
    {
      return null;
    }

    Vector children = getChildren();
    if ( children.size() < 1 )
    {
      return null;
    }
    return (OpenGraphNodeEx)children.elementAt(0);
  }

  public OpenGraphNodeEx getRightChild()
  {
    if ( (parent == null && refNode.getNumEdges() != 2) ||
         (parent != null && refNode.getNumEdges() != 3) )
    {
      return null;
    }

    Vector children = getChildren();
    if ( children.size() < 2 )
    {
      return null;
    }
    return (OpenGraphNodeEx)children.elementAt(1);
  }

  public int getGridX() { return gridX; }
  public void setGridX(int gridX) { this.gridX = gridX; }
  public int getGridY() { return gridY; }
  public void setGridY(int gridY) { this.gridY = gridY; }
  public void shiftX(int shiftX) { gridX+= shiftX; }
  public void shiftY(int shiftY) { gridY+= shiftY; }
  public int getBoundX() { return boundX; }
  public void setBoundX(int boundX) { this.boundX = boundX; }
  public int getBoundY() { return boundY; }
  public void setBoundY(int boundY) { this.boundY = boundY; }
  public int getBoundWidth() { return boundWidth; }
  public void setBoundWidth(int boundWidth) { this.boundWidth = boundWidth; }
  public int getBoundHeight() { return boundHeight; }
  public void setBoundHeight(int boundHeight) { this.boundHeight = boundHeight; }

  public int getSubTreeSize() { return subTreeSize; }
  public void setSubTreeSize(int subTreeSize) { this.subTreeSize = subTreeSize; }
  public boolean getSubTreeDone() { return subTreeDone; }
  public void setSubTreeDone(boolean subTreeDone) { this.subTreeDone = subTreeDone; }

  public String toString()
  {
    return refNode.getLocation() + "X:" + gridX + "Y:" + gridY + " " + subTreeSize;
  }
}
