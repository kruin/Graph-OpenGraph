package dataStructure.pqTree;

import java.util.Vector;
import dataStructure.Queue;

final class PQTreeLifecycleSupport
{
  private PQTreeLifecycleSupport() {}

  static void init(PQTree tree, boolean initLeaves) throws Exception
  {
    tree.hasChanged = true;
    tree.templateMatchString = new String();
    tree.templateTimeString = new String();
    tree.reduceString = new String();
    tree.doneReduction = true;
    tree.flaggedAsNull = false;
    if (tree.constraints != null)
    {
      clear(tree);
      tree.constraints = null;
    }
    tree.cleared = true;
    tree.queue = null;
    tree.clearQueue = new Queue();
    tree.root = new PQNode();
    if (initLeaves)
    {
      tree.leaves = new Vector();
    }
  }

  static void resetTree(PQTree tree) throws Exception
  {
    init(tree, false);
    for ( int i=0; i<tree.leaves.size(); i++ )
    {
      PQNode aNode = (PQNode)tree.leaves.elementAt(i);
      aNode.clear(false);
      tree.root.addChild(aNode);
    }
    System.gc();
  }

  static void reductionByValue(PQTree tree, Vector data) throws Exception
  {
    Vector s = new Vector();
    for ( int i=0; i<tree.leaves.size(); i++ )
    {
      if ( data.contains( ((PQNode)tree.leaves.elementAt(i)).getData() ) )
      {
        s.addElement(tree.leaves.elementAt(i));
      }
    }
    tree.reduction(s, 0);
  }

  static void clear(PQTree tree) throws Exception
  {
    tree.cleared = true;
    if ( tree.clearQueue != null )
    {
      PQNode currentNode;
      while ( tree.clearQueue.size() > 0)
      {
        currentNode = (PQNode)tree.clearQueue.dequeue();
        currentNode.clear();
      }
      if (tree.constraints != null)
      {
        for ( int j=0; j<tree.constraints.size(); j++ )
        {
          ((PQNode)tree.constraints.elementAt(j)).clear();
        }
      }
    }
  }

  static void clear(PQTree tree, Queue queueToClear) throws Exception
  {
    tree.cleared = true;
    if ( queueToClear != null )
    {
      PQNode currentNode;
      while ( queueToClear.size() > 0)
      {
        currentNode = (PQNode)tree.clearQueue.dequeue();
        currentNode.clear(false);
      }
    }
  }
}
