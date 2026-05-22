package graphStructure;

import java.awt.geom.Rectangle2D;
import java.util.Vector;

/**
 * Helper methods for bounds and center-point calculations used by Graph.
 */
public final class GraphBoundsSupport
{
  private GraphBoundsSupport()
  {
  }

  public static Rectangle2D.Double getBounds(Graph graph)
  {
    return getBounds(graph, 0, 0);
  }

  public static Rectangle2D.Double getBounds(Graph graph, int xAdd, int yAdd)
  {
    return getBounds(graph, graph.getNodes(), xAdd, yAdd);
  }

  public static Rectangle2D.Double getBounds(Graph graph, Vector someNodes)
  {
    return getBounds(graph, someNodes, 0, 0);
  }

  public static Rectangle2D.Double getBounds(Graph graph, Vector someNodes, int xAdd, int yAdd)
  {
    double minX = 0;
    double minY = 0;
    double maxX = 0;
    double maxY = 0;

    if ( !someNodes.isEmpty() )
    {
      Location aLocation = ((Node)someNodes.firstElement()).getLocation();
      maxX = minX = aLocation.doubleX();
      maxY = minY = aLocation.doubleY();

      double[] xBounds = minMax(minX, maxX);
      double[] yBounds = minMax(minY, maxY);
      updateBoundsFromNodes(someNodes, xBounds, yBounds);
      updateBoundsFromCurvedEdges(graph.getCurvedEdges(someNodes), xBounds, yBounds);
      minX = xBounds[0];
      maxX = xBounds[1];
      minY = yBounds[0];
      maxY = yBounds[1];
    }

    return new Rectangle2D.Double(minX, minY, maxX - minX + xAdd, maxY - minY + yAdd);
  }

  public static Location getCenterPointLocation(Graph graph)
  {
    Node currentNode;
    int xAcc = 0;
    int yAcc = 0;
    Vector nodes = graph.getNodes();

    if ( nodes.size() > 0 )
    {
      for ( int i=0; i<nodes.size(); i++ )
      {
        currentNode = (Node)nodes.elementAt(i);
        xAcc += currentNode.getX();
        yAcc += currentNode.getY();
      }
      return new Location(xAcc / nodes.size(), yAcc / nodes.size());
    }
    else
    {
      return new Location(0, 0);
    }
  }

  private static void updateBoundsFromNodes(Vector someNodes, double[] xBounds, double[] yBounds)
  {
    Location aLocation;

    for ( int i=1; i<someNodes.size(); i++ )
    {
      aLocation = ((Node)someNodes.elementAt(i)).getLocation();
      if ( aLocation.doubleX() < xBounds[0] )
      {
        xBounds[0] = aLocation.doubleX();
      }
      else if ( aLocation.doubleX() > xBounds[1] )
      {
        xBounds[1] = aLocation.doubleX();
      }
      if ( aLocation.doubleY() < yBounds[0] )
      {
        yBounds[0] = aLocation.doubleY();
      }
      else if ( aLocation.doubleY() > yBounds[1] )
      {
        yBounds[1] = aLocation.doubleY();
      }
    }
  }

  private static void updateBoundsFromCurvedEdges(Vector edges, double[] xBounds, double[] yBounds)
  {
    Rectangle2D rect;

    for ( int i=0; i<edges.size(); i++ )
    {
      rect = ((Edge)edges.elementAt(i)).getQuadCurve().getBounds2D();

      if ( rect.getMinX() < xBounds[0] )
      {
        xBounds[0] = rect.getMinX();
      }
      if ( rect.getMaxX() > xBounds[1] )
      {
        xBounds[1] = rect.getMaxX();
      }
      if ( rect.getMinY() < yBounds[0] )
      {
        yBounds[0] = rect.getMinY();
      }
      if ( rect.getMaxY() > yBounds[1] )
      {
        yBounds[1] = rect.getMaxY();
      }
    }
  }

  private static double[] minMax(double min, double max)
  {
    return new double[] { min, max };
  }
}
