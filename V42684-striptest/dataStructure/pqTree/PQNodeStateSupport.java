package dataStructure.pqTree;

final class PQNodeStateSupport
{
  private PQNodeStateSupport() {}

  static int getNumChildren(PQNode node) throws Exception
  {
    if ( node.isQNode() )
    {
      throw new Exception("*** Warning, Qnodes do not store num children");
    }
    return node.childCount;
  }

  static int getNumEmptyChildren(PQNode node) throws Exception
  {
    if ( node.isQNode() )
    {
      throw new Exception("*** Warning, Qnodes do not store num (empty) children");
    }
    return node.childCount - node.fullChildCount - node.partialChildCount;
  }

  static void convertToQNode(PQNode node) throws Exception
  {
    node.type = PQNode.TYPE_QNODE;
    node.endMostChildren = new PQNodePair();
    if ( node.childCount > 0 )
    {
      throw new Exception("*** ERROR cannot convert to qnode unless no children present!");
    }
  }

  static void convertToPNode(PQNode node) throws Exception
  {
    if (node.isQNode())
    {
      PQNode aNode = node.endMostChildren.PQNodeAt(0);
      PQNode bNode = node.endMostChildren.PQNodeAt(1);
      if ( hasOnlyTwoChildren(node) )
      {
        node.type = PQNode.TYPE_PNODE;
        node.childAccessNode = aNode;
        node.childCount = 2;
        aNode.siblings = null;
        aNode.left = bNode;
        aNode.right = bNode;
        bNode.siblings = null;
        bNode.left = aNode;
        bNode.right = aNode;
        node.endMostChildren = null;
      }
      else
      {
        throw new Exception("*** ERROR convert to pnode was only designed for cases when a qnode has 2 children!");
      }
    }
  }

  static void convertToDNode(PQNode node) throws Exception
  {
    if (node.isPNode())
    {
      if ( node.childCount == 0 )
      {
        node.type = PQNode.TYPE_DNODE;
      }
      else
      {
        throw new Exception("*** ERROR convert to dnode is only allowed for child-less pnodes!");
      }
    }
    else
    {
      throw new Exception("*** ERROR convert to dnode is only allowed for pnodes!");
    }
  }

  static void labelAsFull(PQNode node) throws Exception
  {
    if ( !node.isFull() )
    {
      if ( node.parent != null )
      {
        node.parent.removeChild(node, false);
      }
      node.label = PQNode.LABEL_FULL;
      if ( node.parent != null )
      {
        node.parent.addChild(node, false);
      }
    }
  }

  static void labelAsPartial(PQNode node) throws Exception
  {
    if ( !node.isPartial() )
    {
      if ( node.parent != null )
      {
        node.parent.removeChild(node, false);
      }
      node.label = PQNode.LABEL_PARTIAL;
      if ( node.parent != null )
      {
        node.parent.addChild(node, false);
      }
    }
  }

  static void labelAsEmpty(PQNode node) throws Exception
  {
    if ( !node.isEmpty() )
    {
      if ( node.parent != null )
      {
        node.parent.removeChild(node, false);
      }
      node.label = PQNode.LABEL_EMPTY;
      if ( node.parent != null )
      {
        node.parent.addChild(node, false);
      }
    }
  }

  static boolean hasChildren(PQNode node)
  {
    if ( node.isPNode() )
    {
      return ( node.childCount > 0 );
    }
    else if ( node.isQNode() )
    {
      return ( node.endMostChildren.size() > 0 );
    }
    else
    {
      return false;
    }
  }

  static boolean hasOnlyOneChild(PQNode node)
  {
    if ( node.isQNode() )
    {
      return ( node.endMostChildren.size() == 1 );
    }
    else
    {
      return ( node.childCount == 1 );
    }
  }

  static boolean hasOnlyTwoChildren(PQNode node)
  {
    if ( node.isQNode() )
    {
      if ( node.endMostChildren.size() == 2 )
      {
        return ((PQNode)node.endMostChildren.PQNodeAt(0)).siblings.PQNodeAt(0) ==
               node.endMostChildren.PQNodeAt(1);
      }
      else
      {
        return false;
      }
    }
    else
    {
      return node.childCount == 2;
    }
  }

  static void clear(PQNode node) throws Exception
  {
    clear(node, true);
  }

  static void clear(PQNode node, boolean recurse) throws Exception
  {
    labelAsEmpty(node);
    node.queued = false;
    node.blocked = false;
    node.pertinentChildCount = 0;
    node.pertinentLeafCount = 0;

    if ( recurse )
    {
      if ( node.parent != null && (node.parent.label != PQNode.LABEL_EMPTY ||
           node.parent.queued || node.parent.blocked || node.parent.pertinentChildCount != 0 ||
           node.parent.pertinentLeafCount != 0 || node.parent.fullChildCount != 0 || node.parent.partialChildCount != 0 ) )
      {
        node.parent.clear();
      }
    }
  }
}
