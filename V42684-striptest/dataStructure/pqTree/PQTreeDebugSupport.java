package dataStructure.pqTree;

import java.util.Vector;

final class PQTreeDebugSupport
{
  private PQTreeDebugSupport() {}

  static void printFrontier(PQTree tree)
  {
    Vector leaves = tree.getLeaves();
    for ( int i=0; i<leaves.size(); i++ )
    {
      System.out.println(leaves.elementAt(i));
    }
  }

  static void printTree(PQTree tree) throws Exception
  {
    System.out.println("$$$ PRINT TREE START $$$");
    tree.getRoot().printStructure();
    System.out.println("$$$ PRINT TREE END $$$");
  }

  static void prepareToDrawTree(PQTree tree) throws Exception
  {
    if (tree.hasChangedForDrawing())
    {
      int depth = 0;
      int width = tree.getRoot().countSubLeaves(depth);
      tree.setDrawWidth(width*(PQNode.DRAW_SIZE+PQNode.DRAW_BOUNDARY_SIZE)+50);
      tree.setDrawHeight(tree.getRoot().getDepth()*(PQNode.DRAW_SIZE+PQNode.DRAW_CONNECTOR_SIZE)+50);
    }
  }

  static int getNumNodes(PQTree tree) throws Exception
  {
    return tree.getRoot().countSubNodes();
  }

  static int getNumDeletedNodes(PQTree tree) throws Exception
  {
    return tree.getRoot().countSubDeletedNodes();
  }
}
