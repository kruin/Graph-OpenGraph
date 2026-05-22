package graphStructure;

import java.util.Vector;

/**
 * Internal support for Graph median and partition helpers.
 */
final class GraphMedianSupport
{
  private GraphMedianSupport() {}

  static Node partitionAroundMedianX(Vector pNodes, Vector lesser, Vector greater)
  {
    return partitionAroundMedian(pNodes, lesser, greater, true);
  }

  static Node getMedianXNode(Vector sNodes)
  {
    return getMedianNode(sNodes, true);
  }

  static Node getMedianOfMediansXNode(Node mNodes[])
  {
    return getMedianOfMediansNode(mNodes, true);
  }

  static Node partitionAroundMedianY(Vector pNodes, Vector lesser, Vector greater)
  {
    return partitionAroundMedian(pNodes, lesser, greater, false);
  }

  static Node getMedianYNode(Vector sNodes)
  {
    return getMedianNode(sNodes, false);
  }

  static Node getMedianOfMediansYNode(Node mNodes[])
  {
    return getMedianOfMediansNode(mNodes, false);
  }

  private static Node partitionAroundMedian(Vector pNodes, Vector lesser, Vector greater, boolean useX)
  {
    Node mNode = getMedianNode(pNodes, useX);
    Node currentNode;

    if ( pNodes.size() > 1 )
    {
      preparePartitionVectors(pNodes.size(), lesser, greater);

      for ( int i=0; i<pNodes.size(); i++ )
      {
        currentNode = (Node)pNodes.elementAt(i);
        if ( getCoordinate(currentNode, useX) < getCoordinate(mNode, useX) )
        {
          lesser.addElement(currentNode);
        }
        else if ( getCoordinate(currentNode, useX) > getCoordinate(mNode, useX) )
        {
          greater.addElement(currentNode);
        }
      }

      for ( int i=0; i<pNodes.size(); i++ )
      {
        currentNode = (Node)pNodes.elementAt(i);
        if ( currentNode != mNode && getCoordinate(currentNode, useX) == getCoordinate(mNode, useX) )
        {
          if ( lesser.size() < pNodes.size()/2 )
          {
            lesser.addElement(currentNode);
          }
          else
          {
            greater.addElement(currentNode);
          }
        }
      }
    }
    return mNode;
  }

  private static void preparePartitionVectors(int size, Vector lesser, Vector greater)
  {
    if ( size % 2 == 1 )
    {
      lesser.ensureCapacity(size/2);
      greater.ensureCapacity(size/2);
    }
    else
    {
      lesser.ensureCapacity(size/2);
      greater.ensureCapacity(size/2-1);
    }
    lesser.removeAllElements();
    greater.removeAllElements();
  }

  private static Node getMedianNode(Vector sNodes, boolean useX)
  {
    if ( sNodes.size() > 0 )
    {
      Node mNodes[] = new Node[sNodes.size()];
      sNodes.toArray(mNodes);
      return quickSelect(mNodes, mNodes.length/2, useX);
    }
    return null;
  }

  private static Node quickSelect(Node mNodes[], int k, boolean useX)
  {
    if ( mNodes.length == 1 )
    {
      return mNodes[0];
    }
    Node medianNode = getMedianOfMediansNode(mNodes, useX);
    int lesserCount= 0, equalCount = 0, greaterCount = 0;
    for ( int i=0; i<mNodes.length; i++ )
    {
      int coordinate = getCoordinate(mNodes[i], useX);
      int medianCoordinate = getCoordinate(medianNode, useX);
      if ( coordinate < medianCoordinate )
      {
        lesserCount++;
      }
      else if ( coordinate > medianCoordinate )
      {
        greaterCount++;
      }
    }
    Node lesserNodes[] = new Node[lesserCount];
    Node equalNodes[] = new Node[mNodes.length-lesserCount-greaterCount];
    Node greaterNodes[] = new Node[greaterCount];
    lesserCount = equalCount = greaterCount = 0;
    for ( int i=0; i<mNodes.length; i++ )
    {
      int coordinate = getCoordinate(mNodes[i], useX);
      int medianCoordinate = getCoordinate(medianNode, useX);
      if ( coordinate < medianCoordinate )
      {
        lesserNodes[lesserCount++] = mNodes[i];
      }
      else if ( coordinate > medianCoordinate )
      {
        greaterNodes[greaterCount++] = mNodes[i];
      }
      else
      {
        equalNodes[equalCount++] = mNodes[i];
      }
    }
    if ( k < lesserNodes.length )
    {
      return quickSelect(lesserNodes, k, useX);
    }
    else if ( k < lesserNodes.length + equalNodes.length )
    {
      return medianNode;
    }
    else
    {
      return quickSelect(greaterNodes, k - lesserNodes.length - equalNodes.length, useX);
    }
  }

  private static Node getMedianOfMediansNode(Node mNodes[], boolean useX)
  {
    do
    {
      mNodes = findMedians(mNodes, useX);
    }
    while ( mNodes.length != 1 );
    return mNodes[0];
  }

  private static Node[] findMedians(Node mNodes[], boolean useX)
  {
    Node medians[];
    int leftOver = mNodes.length % 5;
    Node temp[] = new Node[5];
    Node currentNode, switcherNode;
    int i = 0, j = 0, m = 0;
    if ( leftOver == 0 )
    {
      medians = new Node[mNodes.length/5];
    }
    else
    {
      medians = new Node[mNodes.length/5+1];
    }
    for ( i=0, m=0; i<mNodes.length; i++ )
    {
      if ( i > 0 && i % 5 == 0 )
      {
        medians[m++] = temp[2];
      }

      currentNode = mNodes[i];
      switcherNode = null;

      for ( j=0; j<i%5; j++ )
      {
        if ( getCoordinate(temp[j], useX) > getCoordinate(currentNode, useX) )
        {
          switcherNode = temp[j];
          break;
        }
      }
      temp[j] = currentNode;
      if ( switcherNode != null )
      {
        for ( ; j<i%5; j++ )
        {
          currentNode = temp[j+1];
          temp[j+1] = switcherNode;
          switcherNode = currentNode;
        }
      }

    }
    if ( leftOver != 0 )
    {
      medians[m] = temp[leftOver/2];
    }
    else
    {
      medians[m] = temp[2];
    }
    return medians;
  }

  private static int getCoordinate(Node node, boolean useX)
  {
    return useX ? node.getX() : node.getY();
  }
}
