package operation;

import java.util.Vector;

import graphException.GraphException;
import graphStructure.Edge;
import graphStructure.Graph;
import graphStructure.Location;
import graphStructure.LogEntry;
import graphStructure.Node;
import operation.extenders.ChanEdgeEx;
import operation.extenders.ChanNodeEx;

class ChanTreeDrawSupport
{
  private ChanTreeDrawSupport() {}

  static void validateGraphForDisplay(Graph g, Node root, int method, LogEntry logEntry)
    throws Exception
  {
    if ( !ConnectivityOperation.isConnected(g) )
    {
      logEntry.setData("Graph was not connected");
      g.stopLogEntry(logEntry);
      throw new GraphException("Graph is not connected!");
    }
    if ( TreeOperation.hasCycles(g) )
    {
      logEntry.setData("Graph had cycles");
      g.stopLogEntry(logEntry);
      throw new GraphException("Graph has Cycles!");
    }
    if ( method != 4 && !TreeOperation.isBinaryTree(g, root) )
    {
      logEntry.setData("Graph was not a Binary Tree");
      g.stopLogEntry(logEntry);
      throw new GraphException("Graph is not a Binary Tree!");
    }
  }

  static void applyDrawingMethod(Graph g, ChanNodeEx rootEx, int method)
  {
    if ( method == 1 )
    {
      firstMethod(g, rootEx);
    }
    else if ( method == 2 )
    {
      secondMethod(g, rootEx);
    }
    else if ( method == 3 )
    {
      thirdMethod(g, rootEx);
    }
  }

  static void applyGraphLayout(Graph g,
                               ChanNodeEx rootEx,
                               Vector nodes,
                               Vector edges,
                               int width,
                               int height)
  {
    correctGridCoordinates(rootEx, rootEx.getBoundX(), 0);
    g.setGridArea(rootEx.getBoundHeight()+1, height,
                  rootEx.getBoundWidth()+1, width, true);

    int widthIncrement = g.getGridColWidth();
    int heightIncrement = g.getGridRowHeight();
    relocateNodes(g, nodes, widthIncrement, heightIncrement);
    straightenEdges(g, edges);
  }

  static int buildTree(ChanNodeEx root)
  {
    Vector children = root.getChildren();
    int size = 1;
    for ( int i=0; i<children.size(); i++ )
    {
      ChanNodeEx child = (ChanNodeEx)children.elementAt(i);
      if ( child != root.getParent() )
      {
        child.setParent(root);
        size += buildTree(child);
      }
    }
    root.setSubTreeSize(size);
    return size;
  }

  private static void firstMethod(Graph g, ChanNodeEx root)
  {
    if ( isLeaf(root) )
    {
      initializeLeaf(root);
      return;
    }

    firstMethod(g, root.getLeftChild());
    if ( root.getRightChild() != null )
    {
      firstMethod(g, root.getRightChild());
      applyBalancedRule(root);
    }
    else
    {
      otherRule(root, root.getLeftChild());
    }
  }

  private static void secondMethod(Graph g, ChanNodeEx root)
  {
    secondMethod(g, root, 0, 0);
  }

  private static void secondMethod(Graph g,
                                   ChanNodeEx root,
                                   int biggestLeftSubTreeSize,
                                   int biggestRightSubTreeSize)
  {
    if ( isLeaf(root) )
    {
      initializeLeaf(root);
      return;
    }

    biggestLeftSubTreeSize = Math.max(biggestLeftSubTreeSize,
                                      root.getLeftChild().getSubTreeSize());
    if ( root.getRightChild() != null )
    {
      biggestRightSubTreeSize = Math.max(biggestRightSubTreeSize,
                                         root.getRightChild().getSubTreeSize());
    }

    secondMethod(g, root.getLeftChild(), biggestLeftSubTreeSize,
                 biggestRightSubTreeSize);
    if ( root.getRightChild() != null )
    {
      secondMethod(g, root.getRightChild(), biggestLeftSubTreeSize,
                   biggestRightSubTreeSize);
      applyWeightedRule(root, biggestLeftSubTreeSize, biggestRightSubTreeSize);
    }
    else
    {
      otherRule(root, root.getLeftChild());
    }
  }

  private static double log2(double x)
  {
    return Math.log(x)/Math.log(2.0);
  }

  private static ChanNodeEx findLastNodeWithSizeGreaterThan(ChanNodeEx root, double num)
  {
    if ( root.getSubTreeSize() < num )
    {
      return root.getParent() != null ? root.getParent() : root;
    }

    if ( root.getRightChild() == null ||
         root.getLeftChild().getSubTreeSize() >= root.getRightChild().getSubTreeSize() )
    {
      return findLastNodeWithSizeGreaterThan(root.getLeftChild(), num);
    }
    return findLastNodeWithSizeGreaterThan(root.getRightChild(), num);
  }

  private static void thirdMethod(Graph g, ChanNodeEx root)
  {
    double a = g.getNumNodes() / Math.pow(2, Math.sqrt(2*log2(g.getNumNodes())));
    ChanNodeEx kNode = findLastNodeWithSizeGreaterThan(root, g.getNumNodes()-a);
    boolean left = kNode == kNode.getParent().getLeftChild();
    kNode = kNode.getParent();
    thirdMethod1(g, root, kNode);

    int shift = left ? root.getBoundX() : root.getBoundWidth() - root.getBoundX();
    thirdMethod2(g, root, kNode, false, left, shift);
  }

  private static void thirdMethod1(Graph g, ChanNodeEx root, ChanNodeEx kNode)
  {
    if ( isLeaf(root) )
    {
      initializeLeaf(root);
      return;
    }

    if ( root == kNode )
    {
      return;
    }

    thirdMethod1(g, root.getLeftChild(), kNode);
    if ( root.getRightChild() != null )
    {
      thirdMethod1(g, root.getRightChild(), kNode);
      applyBalancedRule(root);
    }
    else
    {
      otherRule(root, root.getLeftChild());
    }
  }

  private static void thirdMethod2(Graph g,
                                   ChanNodeEx root,
                                   ChanNodeEx kNode,
                                   boolean passedKNode,
                                   boolean left,
                                   int shift)
  {
    if ( isLeaf(root) )
    {
      initializeLeaf(root);
      return;
    }

    if ( root == kNode )
    {
      thirdMethod2(g, root.getLeftChild(), kNode, true, left, shift);
      if ( root.getRightChild() != null )
      {
        thirdMethod2(g, root.getRightChild(), kNode, true, left, shift);
        applyExtendedRule(root, left, shift);
      }
      else
      {
        extendedOtherRule(root, root.getLeftChild(), shift);
      }
      return;
    }

    thirdMethod2(g, root.getLeftChild(), kNode, passedKNode, left, shift);
    if ( root.getRightChild() != null )
    {
      thirdMethod2(g, root.getRightChild(), kNode, passedKNode, left, shift);
      if ( passedKNode )
      {
        applyDirectionalRule(root, left);
      }
      else
      {
        applyBalancedRule(root);
      }
    }
    else
    {
      otherRule(root, root.getLeftChild());
    }
  }

  private static void applyBalancedRule(ChanNodeEx root)
  {
    ChanNodeEx left = root.getLeftChild();
    ChanNodeEx right = root.getRightChild();
    if ( left.getSubTreeSize() < right.getSubTreeSize() )
    {
      leftRule(root, left, right);
    }
    else
    {
      rightRule(root, left, right);
    }
  }

  private static void applyWeightedRule(ChanNodeEx root,
                                        int biggestLeftSubTreeSize,
                                        int biggestRightSubTreeSize)
  {
    ChanNodeEx left = root.getLeftChild();
    ChanNodeEx right = root.getRightChild();
    if ( left.getSubTreeSize() + biggestRightSubTreeSize <
         right.getSubTreeSize() + biggestLeftSubTreeSize )
    {
      leftRule(root, left, right);
    }
    else
    {
      rightRule(root, left, right);
    }
  }

  private static void applyDirectionalRule(ChanNodeEx root, boolean left)
  {
    if ( left )
    {
      rightRule(root, root.getLeftChild(), root.getRightChild());
    }
    else
    {
      leftRule(root, root.getLeftChild(), root.getRightChild());
    }
  }

  private static void applyExtendedRule(ChanNodeEx root, boolean left, int shift)
  {
    if ( left )
    {
      extendedRightRule(root, root.getLeftChild(), root.getRightChild(), shift);
    }
    else
    {
      extendedLeftRule(root, root.getLeftChild(), root.getRightChild(), shift);
    }
  }

  private static boolean isLeaf(ChanNodeEx root)
  {
    return root.getSubTreeSize() == 1;
  }

  private static void initializeLeaf(ChanNodeEx root)
  {
    root.setGridX(0);
    root.setGridY(0);
    root.setBoundX(0);
    root.setBoundY(0);
    root.setBoundWidth(0);
    root.setBoundHeight(0);
  }

  private static void relocateNodes(Graph g, Vector nodes, int widthIncrement, int heightIncrement)
  {
    for ( int i=0; i<nodes.size(); i++ )
    {
      ChanNodeEx aNode = (ChanNodeEx)nodes.elementAt(i);
      g.relocateNode(aNode.getRef(),
                     new Location(aNode.getGridX()*widthIncrement,
                                  aNode.getGridY()*heightIncrement),
                     true);
    }
  }

  private static void straightenEdges(Graph g, Vector edges)
  {
    for ( int i=0; i<edges.size(); i++ )
    {
      ChanEdgeEx anEdge = (ChanEdgeEx)edges.elementAt(i);
      g.straightenEdge((Edge)anEdge.getRef(), true);
    }
  }

  private static void leftRule( ChanNodeEx root, ChanNodeEx left, ChanNodeEx right )
  {
    left.shiftX(-1*(left.getBoundWidth()-left.getBoundX()+1));
    left.shiftY(1);
    right.shiftY(1+left.getBoundHeight());
    root.setGridX(0);
    root.setGridY(0);
    root.setBoundWidth(Math.max(right.getBoundX(), 1+left.getBoundWidth()) +
                       right.getBoundWidth()-right.getBoundX());
    root.setBoundHeight(left.getBoundHeight()+right.getBoundHeight()+1);
    root.setBoundX(Math.max(right.getBoundX(), 1+left.getBoundWidth()));
    root.setBoundY(0);
  }

  private static void rightRule( ChanNodeEx root, ChanNodeEx left, ChanNodeEx right )
  {
    right.shiftX(1+right.getBoundX());
    right.shiftY(1);
    left.shiftY(1+right.getBoundHeight());
    root.setGridX(0);
    root.setGridY(0);
    root.setBoundWidth(left.getBoundX() +
                       Math.max(1+right.getBoundWidth(), left.getBoundWidth()-left.getBoundX()));
    root.setBoundHeight(left.getBoundHeight()+right.getBoundHeight()+1);
    root.setBoundX(left.getBoundX());
    root.setBoundY(0);
  }

  private static void otherRule( ChanNodeEx root, ChanNodeEx child )
  {
    child.shiftY(1);
    root.setGridX(0);
    root.setGridY(0);
    root.setBoundWidth(child.getBoundWidth());
    root.setBoundHeight(child.getBoundHeight()+1);
    root.setBoundX(child.getBoundX());
    root.setBoundY(0);
  }

  private static void extendedLeftRule( ChanNodeEx root, ChanNodeEx left, ChanNodeEx right, int shift )
  {
    left.shiftX(-1*(left.getBoundWidth()-left.getBoundX()+1));
    left.shiftY(1);
    right.shiftX(shift);
    right.shiftY(1+left.getBoundHeight());
    root.setGridX(0);
    root.setGridY(0);
    root.setBoundWidth(shift +
                       Math.max(right.getBoundWidth()-shift, 1+left.getBoundWidth()));
    root.setBoundHeight(left.getBoundHeight()+right.getBoundHeight()+1);
    root.setBoundX(Math.max(right.getBoundWidth()-shift, 1+left.getBoundWidth()));
    root.setBoundY(0);
  }

  private static void extendedRightRule( ChanNodeEx root, ChanNodeEx left, ChanNodeEx right, int shift )
  {
    right.shiftX(1+right.getBoundX());
    right.shiftY(1);
    left.shiftX(-1*shift);
    left.shiftY(1+right.getBoundHeight());
    root.setGridX(0);
    root.setGridY(0);
    root.setBoundWidth(shift +
                       Math.max(1+right.getBoundWidth(), left.getBoundWidth())-shift);
    root.setBoundHeight(left.getBoundHeight()+right.getBoundHeight()+1);
    root.setBoundX(shift);
    root.setBoundY(0);
  }

  private static void extendedOtherRule(ChanNodeEx root, ChanNodeEx child, int shift)
  {
    child.shiftX(-1*shift);
    child.shiftY(1);
    root.setGridX(0);
    root.setGridY(0);
    root.setBoundWidth(child.getBoundWidth());
    root.setBoundHeight(child.getBoundHeight()+1);
    root.setBoundX(shift);
    root.setBoundY(0);
  }

  private static void correctGridCoordinates(ChanNodeEx root, int shiftX, int shiftY)
  {
    root.shiftX(shiftX);
    root.shiftY(shiftY);
    if ( root.getLeftChild() != null )
    {
      correctGridCoordinates(root.getLeftChild(), root.getGridX(), root.getGridY());
    }
    if ( root.getRightChild() != null )
    {
      correctGridCoordinates(root.getRightChild(), root.getGridX(), root.getGridY());
    }
  }
}
