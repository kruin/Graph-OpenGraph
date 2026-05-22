package graphStructure;

import java.awt.Point;

final class NodeGeometrySupport
{
  private NodeGeometrySupport() {}

  static boolean contains(Node node, Point point, int radius)
  {
    Location location = node.getLocation();
    int distance = (point.x - location.intX()) * (point.x - location.intX()) +
                   (point.y - location.intY()) * (point.y - location.intY());
    return distance <= (radius * radius);
  }

  static void translate(Node node, int transX, int transY)
  {
    Location location = node.getLocation();
    node.setLocation(location.intX() + transX, location.intY() + transY);
  }

  static void rotate(Node node, Location referencePoint, double angle)
  {
    double cos = Math.cos(Math.toRadians(angle));
    double sin = Math.sin(Math.toRadians(angle));
    Location location = node.getLocation();
    double tempX = location.doubleX() - referencePoint.doubleX();
    double tempY = location.doubleY() - referencePoint.doubleY();
    location.setX((cos * tempX - sin * tempY) + referencePoint.doubleX());
    location.setY((sin * tempX + cos * tempY) + referencePoint.doubleY());
  }

  static double angleBetween(Location p1, Location p2, Location p3)
  {
    double ax = p1.intX();
    double ay = p1.intY();
    double bx = p2.intX();
    double by = p2.intY();
    double cx = p3.intX();
    double cy = p3.intY();
    double crossProduct = (ax-bx)*(cy-by)-(ay-by)*(cx-bx);
    double dotProduct = (ax-bx)*(cx-bx)+(ay-by)*(cy-by);
    double tan = Math.abs(Math.toDegrees(Math.atan(crossProduct / dotProduct)));
    double angle = tan;
    if ( dotProduct < 0 )
    {
      angle = 180.0 - angle;
    }
    if ( crossProduct < 0 )
    {
      angle = angle * -1;
    }
    return angle;
  }

  static double angleBetween(Node node1, Node node2, Node node3, Node node4)
  {
    double x1 = node1.getLocation().intX();
    double x2 = node2.getLocation().intX();
    double x3 = node3.getLocation().intX();
    double x4 = node4.getLocation().intX();
    double y1 = node1.getLocation().intY();
    double y2 = node2.getLocation().intY();
    double y3 = node3.getLocation().intY();
    double y4 = node4.getLocation().intY();

    double ax, ay, bx, by, cx, cy;

    if ( x1 == x3 && y1 == y3 )
    {
      ax = x2;
      ay = y2;
      bx = x1;
      by = y1;
      cx = x4;
      cy = y4;
    }
    else if ( x1 == x4 && y1 == y4 )
    {
      ax = x2;
      ay = y2;
      bx = x1;
      by = y1;
      cx = x3;
      cy = y3;
    }
    else if ( x2 == x3 && y2 == y3 )
    {
      ax = x1;
      ay = y1;
      bx = x2;
      by = y2;
      cx = x4;
      cy = y4;
    }
    else if ( x2 == x4 && y2 == y4 )
    {
      ax = x1;
      ay = y1;
      bx = x2;
      by = y2;
      cx = x3;
      cy = y3;
    }
    else
    {
      double numerator = (x4-x3)*(y1-y3)-(y4-y3)*(x1-x3);
      double denominator = (y4-y3)*(x2-x1)-(x4-x3)*(y2-y1);
      if ( denominator == 0 )
      {
        return 0.0;
      }
      double temp = numerator / denominator;
      ax = x1;
      ay = y1;
      bx = x1 + temp*(x2-x1);
      by = y1 + temp*(y2-y1);
      cx = x3;
      cy = y3;
    }

    double crossProduct = (ax-bx)*(cy-by)-(ay-by)*(cx-bx);
    double dotProduct = (ax-bx)*(cx-bx)+(ay-by)*(cy-by);
    double tan = Math.abs(Math.toDegrees(Math.atan(crossProduct / dotProduct)));
    double angle = tan;
    if ( dotProduct < 0 )
    {
      angle = 180.0 - angle;
    }
    if ( crossProduct < 0 )
    {
      angle = angle * -1;
    }
    return angle;
  }

  static void scaleBy(Node node, double minX, double minY, double xFactor, double yFactor)
  {
    Location location = node.getLocation();
    double temp = xFactor * (location.doubleX() - minX);
    double tempX = location.doubleX();
    double tempY = location.doubleY();
    if ( temp > Node.MIN_FOR_SCALE )
    {
      tempX = minX + temp;
    }
    temp = yFactor * (location.doubleY() - minY);
    if ( temp > Node.MIN_FOR_SCALE )
    {
      tempY = minY + temp;
    }
    node.setLocation(tempX, tempY);
  }

  static double distanceSquaredFrom(Node node, Node otherNode)
  {
    double ax = node.getLocation().doubleX();
    double ay = node.getLocation().doubleY();
    double bx = otherNode.getLocation().doubleX();
    double by = otherNode.getLocation().doubleY();
    return Math.pow((bx-ax),2) + Math.pow((by-ay),2);
  }
}
