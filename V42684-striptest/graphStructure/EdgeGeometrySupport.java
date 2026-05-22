package graphStructure;

final class EdgeGeometrySupport
{
  private EdgeGeometrySupport() {}

  static double getSlope(Edge edge)
  {
    double rise = Math.abs(((Node)edge.getStartNode()).getLocation().intY() -
                           ((Node)edge.getEndNode()).getLocation().intY());
    double run = Math.abs(((Node)edge.getStartNode()).getLocation().intX() -
                          ((Node)edge.getEndNode()).getLocation().intX());
    if ( run == 0 )
    {
      return Double.MAX_VALUE;
    }
    return rise / run;
  }

  static double getStraightLength(Edge edge)
  {
    int ax = ((Node)edge.getStartNode()).getLocation().intX();
    int ay = ((Node)edge.getStartNode()).getLocation().intY();
    int bx = ((Node)edge.getEndNode()).getLocation().intX();
    int by = ((Node)edge.getEndNode()).getLocation().intY();
    return Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
  }

  static double getLength(Edge edge)
  {
    if ( edge.isCurved() )
    {
      return getCurvedLength(edge);
    }
    else if ( edge.isOrthogonal() )
    {
      return getOrthogonalLength(edge);
    }
    return getStraightLength(edge);
  }

  private static double getCurvedLength(Edge edge)
  {
    double length = 0;
    Location sLocation = ((Node)edge.getStartNode()).getLocation();
    Location cLocation = edge.getCenterLocation();
    Location eLocation = ((Node)edge.getEndNode()).getLocation();
    cLocation = new Location((2 * cLocation.doubleX() -
                              sLocation.doubleX() / 2 - eLocation.doubleX() / 2),
                             (2 * cLocation.doubleY() -
                              sLocation.doubleY() / 2 - eLocation.doubleY() / 2));

    double currentX = sLocation.doubleX();
    double currentY = sLocation.doubleY();

    for ( double step = 0; step < 1.0; step += Edge.CURVE_INTERVAL )
    {
      double nextX = ((sLocation.doubleX() - 2 * cLocation.doubleX() + eLocation.doubleX()) *
                      Math.pow(step, 2.0)) +
                     ((2 * cLocation.doubleX() - 2 * sLocation.doubleX()) * step) +
                     sLocation.doubleX();
      double nextY = ((sLocation.doubleY() - 2 * cLocation.doubleY() + eLocation.doubleY()) *
                      Math.pow(step, 2.0)) +
                     ((2 * cLocation.doubleY() - 2 * sLocation.doubleY()) * step) +
                     sLocation.doubleY();
      length += Math.sqrt((nextX - currentX) * (nextX - currentX) +
                          (nextY - currentY) * (nextY - currentY));
      currentX = nextX;
      currentY = nextY;
    }
    return length;
  }

  private static double getOrthogonalLength(Edge edge)
  {
    int ax = edge.getStartNode().getLocation().intX();
    int ay = edge.getStartNode().getLocation().intY();
    int bx = edge.getCenterLocation().intX();
    int by = edge.getCenterLocation().intY();
    double length = Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
    ax = edge.getCenterLocation().intX();
    ay = edge.getCenterLocation().intY();
    bx = edge.getEndNode().getLocation().intX();
    by = edge.getEndNode().getLocation().intY();
    return length + Math.sqrt((bx - ax) * (bx - ax) + (by - ay) * (by - ay));
  }

  static void initCurveAngles(Edge edge)
  {
    edge.setStartControlAngleInternal(Node.angleBetween(edge.getCenterLocation(),
                                                        edge.getStartNode().getLocation(),
                                                        edge.getEndNode().getLocation()));
    edge.setEndControlAngleInternal(Node.angleBetween(edge.getCenterLocation(),
                                                      edge.getEndNode().getLocation(),
                                                      edge.getStartNode().getLocation()));
  }

  static void translate(Edge edge, int transX, int transY)
  {
    edge.setCenterLocation(new Location(edge.getCenterLocation().intX() + transX,
                                        edge.getCenterLocation().intY() + transY));
  }

  static void rotate(Edge edge, Location referencePoint, double angle)
  {
    double cos = Math.cos(Math.toRadians(angle));
    double sin = Math.sin(Math.toRadians(angle));
    Location centerLocation = edge.getCenterLocation();
    double tempX = centerLocation.doubleX() - referencePoint.doubleX();
    double tempY = centerLocation.doubleY() - referencePoint.doubleY();
    centerLocation.setX((cos * tempX - sin * tempY) + referencePoint.doubleX());
    centerLocation.setY((sin * tempX + cos * tempY) + referencePoint.doubleY());
  }

  static void scaleBy(Edge edge, double minX, double minY, double xFactor, double yFactor)
  {
    Location centerLocation = edge.getCenterLocation();
    double temp = xFactor * (centerLocation.doubleX() - minX);
    double tempX = centerLocation.doubleX();
    double tempY = centerLocation.doubleY();
    if ( temp > Node.MIN_FOR_SCALE )
    {
      tempX = minX + temp;
    }
    temp = yFactor * (centerLocation.doubleY() - minY);
    if ( temp > Node.MIN_FOR_SCALE )
    {
      tempY = minY + temp;
    }
    edge.setCenterLocation(new Location(tempX, tempY));
  }

  static void update(Edge edge)
  {
    if ( edge.isCurved() )
    {
      Location s = getLocationAtAngleFrom(edge, (Node)edge.getStartNode(), edge.getStartControlAngleInternal());
      Location e = getLocationAtAngleFrom(edge, (Node)edge.getEndNode(), edge.getEndControlAngleInternal());
      edge.setCenterLocation(getIntersectionLocation(edge.getStartNode().getLocation(), s,
                                                     edge.getEndNode().getLocation(), e));
    }
    else if ( edge.isOrthogonal() )
    {
      Location centerLocation = getOrthogonalLocation(edge);
      if ( centerLocation == null )
      {
        edge.makeStraight();
      }
      else
      {
        edge.setCenterLocation(centerLocation);
      }
    }
    else
    {
      edge.setCenterLocation(getNormalLocation(edge));
    }
  }

  static Location getIntersectionLocation(Location l1, Location l2, Location l3, Location l4)
  {
    double x43 = l4.doubleX() - l3.doubleX();
    double y43 = l4.doubleY() - l3.doubleY();
    double x21 = l2.doubleX() - l1.doubleX();
    double y21 = l2.doubleY() - l1.doubleY();
    double u = ((x43 * (l1.doubleY() - l3.doubleY())) -
                (y43 * (l1.doubleX() - l3.doubleX()))) /
               ((y43 * x21) - (x43 * y21));
    return new Location((l1.doubleX() + u * x21), (l1.doubleY() + u * y21));
  }

  static Location getNormalLocation(Edge edge)
  {
    return new Location(((edge.getStartNode().getX() + edge.getEndNode().getX()) / 2),
                        ((edge.getStartNode().getY() + edge.getEndNode().getY()) / 2));
  }

  static Location getOrthogonalLocation(Edge edge)
  {
    double turnOrientation = getTurnOrientation(edge);
    Location s = edge.getStartNode().getLocation();
    Location e = edge.getEndNode().getLocation();
    if ( s.intX() == e.intX() || s.intY() == e.intY() )
    {
      return null;
    }
    else if ( isLeftTurn(turnOrientation) )
    {
      edge.setOrthogonalLeftFromStartInternal(true);
      if ( (s.intX() < e.intX() && s.intY() < e.intY()) ||
           (s.intX() > e.intX() && s.intY() > e.intY()) )
      {
        return new Location(e.intX(), s.intY());
      }
      return new Location(s.intX(), e.intY());
    }
    else if ( isRightTurn(turnOrientation) )
    {
      edge.setOrthogonalLeftFromStartInternal(false);
      if ( (s.intX() < e.intX() && s.intY() > e.intY()) ||
           (s.intX() > e.intX() && s.intY() < e.intY()) )
      {
        return new Location(e.intX(), s.intY());
      }
      return new Location(s.intX(), e.intY());
    }
    return null;
  }

  static double getTurnOrientation(Edge edge)
  {
    Location s = edge.getStartNode().getLocation();
    Location e = edge.getEndNode().getLocation();
    Location c = edge.getCenterLocation();
    return (e.doubleX() - s.doubleX()) * (c.doubleY() - s.doubleY()) -
           (e.doubleY() - s.doubleY()) * (c.doubleX() - s.doubleX());
  }

  static boolean isLeftTurn(double turnOrientation)
  {
    return turnOrientation < 0;
  }

  static boolean isRightTurn(double turnOrientation)
  {
    return turnOrientation > 0;
  }

  static boolean hasZeroLength(Edge edge)
  {
    return edge.getStartNode().getLocation().equals(edge.getEndNode().getLocation());
  }

  static boolean intersects(Edge first, Edge second)
  {
    if ( hasZeroLength(first) )
    {
      if ( hasZeroLength(second) &&
           first.getStartNode().getLocation().equals(second.getStartNode().getLocation()) )
      {
        return true;
      }
      else if ( first.getStartNode().getLocation().equals(second.getStartNode().getLocation()) ||
                first.getStartNode().getLocation().equals(second.getEndNode().getLocation()) )
      {
        return true;
      }
    }
    int x1 = first.getStartNode().getX();
    int y1 = first.getStartNode().getY();
    int x2 = first.getEndNode().getX();
    int y2 = first.getEndNode().getY();
    int x3 = second.getStartNode().getX();
    int y3 = second.getStartNode().getY();
    int x4 = second.getEndNode().getX();
    int y4 = second.getEndNode().getY();

    double x21 = x2 - x1;
    double y21 = y2 - y1;
    double x43 = x4 - x3;
    double y43 = y4 - y3;
    double x31 = x3 - x1;
    double y31 = y3 - y1;
    double denominator = x21 * y43 - y21 * x43;
    double numerator1 = x31 * y43 - y31 * x43;
    double numerator2 = x31 * y21 - y31 * x21;

    if ( denominator != 0 )
    {
      double determinant1 = numerator1 / denominator;
      double determinant2 = numerator2 / denominator;
      if ( determinant1 >= 0 && determinant1 <= 1 &&
           determinant2 >= 0 && determinant2 <= 1 )
      {
        if ( (x1 == x3 && y1 == y3) || (x1 == x4 && y1 == y4) ||
             (x2 == x3 && y2 == y3) || (x2 == x4 && y2 == y4) )
        {
          return false;
        }
        return true;
      }
      return false;
    }

    if ( numerator1 != 0 && numerator2 != 0 )
    {
      return false;
    }

    if ( x1 != x2 )
    {
      double minX1 = Math.min(x1, x2);
      double maxX1 = Math.max(x1, x2);
      double minX2 = Math.min(x3, x4);
      double maxX2 = Math.max(x3, x4);
      if ( maxX2 < minX1 || minX2 > maxX1 )
      {
        return false;
      }
      else if ( maxX2 == minX1 || minX2 == maxX1 )
      {
        return false;
      }
      return true;
    }

    double minY1 = Math.min(y1, y2);
    double maxY1 = Math.max(y1, y2);
    double minY2 = Math.min(y3, y4);
    double maxY2 = Math.max(y3, y4);
    if ( maxY2 < minY1 || minY2 > maxY1 )
    {
      return false;
    }
    else if ( maxY2 == minY1 || minY2 == maxY1 )
    {
      return false;
    }
    return true;
  }

  static Location getLocationAtAngleFrom(Edge edge, Node pivotEndNode, double angle)
  {
    double totalAngle = getAngleFrom(edge, pivotEndNode);
    if ( totalAngle == -1 )
    {
      return null;
    }
    totalAngle += angle;
    double slope = -1.0 * Math.tan(Math.toRadians(totalAngle));
    double intercept = pivotEndNode.getLocation().doubleY() -
                       slope * pivotEndNode.getLocation().doubleX();
    return new Location(pivotEndNode.getLocation().doubleX() + 5000.0,
                        slope * (pivotEndNode.getLocation().doubleX() + 5000.0) + intercept);
  }

  static double getDirectedAngle(Edge edge, Node directionSource)
  {
    if ( directionSource == edge.getStartNode() )
    {
      return getAngleFrom(edge, (Node)edge.getStartNode());
    }
    else if ( directionSource == edge.getEndNode() )
    {
      return getAngleFrom(edge, (Node)edge.getEndNode());
    }
    return -1.0;
  }

  static double getAngleFrom(Edge edge, Node aNode)
  {
    if ( aNode != edge.getStartNode() && aNode != edge.getEndNode() )
    {
      return -1.0;
    }

    Node otherNode = (Node)edge.otherEndFrom(aNode);
    return getAngleFrom(otherNode.getLocation().intX(),
                        otherNode.getLocation().intY(),
                        aNode.getLocation().intX(),
                        aNode.getLocation().intY(),
                        aNode.getLocation().intX() + 100.0,
                        aNode.getLocation().intY());
  }

  private static double getAngleFrom(double ax, double ay, double bx,
                                     double by, double cx, double cy)
  {
    double crossProduct = (ax - bx) * (cy - by) - (ay - by) * (cx - bx);
    double dotProduct = (ax - bx) * (cx - bx) + (ay - by) * (cy - by);

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
    if ( angle < 0 )
    {
      angle = 360.0 + angle;
    }
    return angle;
  }
}
