package dataStructure.pqTree;

import java.util.Vector;

final class PQNodeDebugSupport
{
  private PQNodeDebugSupport() {}

  static String infoString(PQNode node)
  {
    String outString = new String("[");
    if (node.isQNode())
    {
      outString+= "Q";
      if ( node.isPseudoNode() )
      {
        outString+= "P";
      }
    }
    else if (node.isPNode())
    {
      outString+= "P";
    }
    else if (node.isDNode())
    {
      outString+= "D";
    }

    PQNode parent = node.getParent();
    if (parent != null)
    {
      outString = outString + " p: " + parent.hashCode();
    }
    else
    {
      outString = outString + " p: null";
    }

    if (node.isDeleted())
    {
      outString+=" DELETED]";
      return outString;
    }

    Object data = node.debugGetDataValue();
    if (data != null)
    {
      outString = outString + " " + node.hashCode() + " " + data.toString();
    }
    else
    {
      outString = outString + " " + node.hashCode() + " null ";
    }
    if ( node.isFull() )
    {
      outString = outString + " f ";
    }
    else if ( node.isPartial() )
    {
      outString = outString + " p ";
    }
    else if ( node.isEmpty() )
    {
      outString = outString + " e ";
    }
    outString = outString + " fc: " + node.debugGetFullChildCount();
    outString = outString + " pc: " + node.debugGetPartialChildCount();
    if (parent != null)
    {
      outString = outString + " p: " + parent.hashCode();
      if ( parent.isPNode() )
      {
        outString = outString + " l: " + hashOrNull(node.debugGetLeftChild());
        outString = outString + " r: " + hashOrNull(node.debugGetRightChild());
      }
      else if ( parent.isQNode() )
      {
        PQNodePair siblings = node.getSiblings();
        if ( siblings == null )
        {
          outString = outString + " siblings are null!";
        }
        else
        {
          outString = outString + " s1: " + hashOrNull(siblings.PQNodeAt(0));
          outString = outString + " s2: " + hashOrNull(siblings.PQNodeAt(1));
        }
      }
      outString = outString + " fl: " + hashOrNull(node.debugGetFullLeftChild());
      outString = outString + " fr: " + hashOrNull(node.debugGetFullRightChild());
      outString = outString + " pl: " + hashOrNull(node.debugGetPartialLeftChild());
      outString = outString + " pr: " + hashOrNull(node.debugGetPartialRightChild());
    }
    if ( node.isQNode() )
    {
      PQNodePair endMostChildren = node.debugGetEndMostChildrenValue();
      outString = outString + " e:";
      for ( int i=0; i<endMostChildren.size(); i++ )
      {
        outString = outString + " " + infoString((PQNode)endMostChildren.PQNodeAt(i));
      }
    }
    outString = outString + " perl: " + node.getPertinentLeafCount();
    outString = outString + " perc: " + node.getPertinentChildCount();
    if ( node.debugGetFullChildAccessNode() == null )
    {
      outString = outString + " fcan: null";
    }
    else
    {
      outString = outString + " fcan: " + node.debugGetFullChildAccessNode();
    }
    outString+= " " + node.debugIsQueuedValue() + " " + node.debugIsBlockedValue();
    outString = outString + "]";
    return outString;
  }

  static String toDisplayString(PQNode node)
  {
    String returnString = new String();
    if (node.isQNode())
    {
      returnString+="Q";
    }
    else if (node.isPNode())
    {
      returnString+="P";
    }
    Object data = node.debugGetDataValue();
    if ( data != null )
    {
      return returnString + data.toString();
    }
    else
    {
      return returnString + "Interior Node";
    }
  }

  static void printStructure(PQNode node) throws Exception
  {
    System.out.print(infoString(node));
    if ( node.isDeleted() )
    {
      System.out.println(" DELETED");
    }
    else
    {
      System.out.println();
      Vector children = node.getAllChildren();
      for (int i=0; i<children.size(); i++)
      {
        printStructure((PQNode)children.elementAt(i));
      }
    }
  }

  static int countSubLeaves(PQNode node, int parentDepth) throws Exception
  {
    node.debugSetSubLeafCountValue(0);
    node.debugSetDepthValue(parentDepth+1);
    int tempDepth = node.getDepth();
    if ( node.hasChildren() )
    {
      PQNode childNode;
      Vector children = node.getAllChildren();
      for (int i=0; i<children.size(); i++)
      {
        childNode = (PQNode)children.elementAt(i);
        node.debugSetSubLeafCountValue(node.debugGetSubLeafCountValue() + countSubLeaves(childNode, tempDepth));
        if (childNode.getDepth() > node.getDepth())
        {
          node.debugSetDepthValue(childNode.getDepth());
        }
      }
      return node.debugGetSubLeafCountValue();
    }
    else
    {
      return 1;
    }
  }

  static int countSubDeletedNodes(PQNode node) throws Exception
  {
    return countSubDeletedNodes(node, new Vector());
  }

  static int countSubDeletedNodes(PQNode node, Vector deletedNodes) throws Exception
  {
    int subDeletedNodeCount = 0;
    if ( node.hasChildren() )
    {
      PQNode childNode;
      Vector children = node.getAllChildren();
      for (int i=0; i<children.size(); i++)
      {
        childNode = (PQNode)children.elementAt(i);
        if ( childNode.getParent().isDeleted() )
        {
          if ( !deletedNodes.contains(childNode.getParent()) )
          {
            deletedNodes.addElement(childNode.getParent());
            subDeletedNodeCount++;
          }
        }
        subDeletedNodeCount += countSubDeletedNodes(childNode, deletedNodes);
      }
    }
    return subDeletedNodeCount;
  }

  static int countSubNodes(PQNode node) throws Exception
  {
    int subNodeCount = 1;
    if ( node.hasChildren() )
    {
      Vector children = node.getAllChildren();
      for (int i=0; i<children.size(); i++)
      {
        subNodeCount += countSubNodes((PQNode)children.elementAt(i));
      }
    }
    return subNodeCount;
  }

  private static String hashOrNull(PQNode node)
  {
    return node == null ? "null" : Integer.toString(node.hashCode());
  }
}
