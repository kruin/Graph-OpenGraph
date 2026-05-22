package graphStructure;

import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.geom.*;

/**
 * This class represents an edge object that links two nodes to each other.
 * Edges can be added, deleted, blocked or made directional by using the
 * interface of the editor component.
 *
 * @author Jon Harris
 */

public class Edge implements EdgeInterface
{
  /** The width in pixels to use when drawing this Edge in the UI */
  public static int THICKNESS = 4;
  public static int GENERATED_DASH_LENGTH = 10;
  public static int SELECTED_DASH_LENGTH = 2;
  public static int ARROW_WIDTH =6 ;
  public static int ARROW_HEIGHT = 20;
  public static double CURVE_INTERVAL = 0.01;
  /** The Color to use when drawing this Edge in the UI */
  public static Color DEFAULT_COLOR = Color.blue;
  public static Color SELECTED_COLOR = Color.black;
  private static boolean drawUndirectedMidpointMarkers = true;
  /** The Color to use when drawing this Edge as selected Edge in the UI */
  private Color color;
  private boolean isSelected;
  private boolean isAdded;
  private boolean isGenerated;
  private boolean isCurved;
  private boolean isOrthogonal;
  private boolean isOrthogonalLeftFromStart;
  private HalfEdge startEdge;
  private HalfEdge endEdge;
  private EdgeInterface copy;
  private Node directedSourceNode;
  private Location centerLocation;
  private double startControlAngle;
  private double endControlAngle;
  private int index;
  private EdgeExtender extender;
  private boolean isVisible;

  public static void setDrawUndirectedMidpointMarkers(boolean draw)
  {
    drawUndirectedMidpointMarkers = draw;
  }

  public static boolean getDrawUndirectedMidpointMarkers()
  {
    return drawUndirectedMidpointMarkers;
  }

  public Color getColor() { return color; }

  public void setColor(Color aColor) { color = aColor; }

  public void setIsAdded(boolean aAdded) { isAdded = aAdded; }

  public boolean isAdded() { return isAdded; }

  public boolean isGenerated() { return isGenerated; }

  public void setIsGenerated(boolean generated) { isGenerated = generated; }

  public int getIndex() { return index; }

  public void setIndex(int index) { this.index = index; }

  public boolean isCurved() { return isCurved; }

  public void setIsCurved(boolean curved) { isCurved = curved; }
  
  public void makeCurved()
  {
    isCurved = true;
    isOrthogonal = false;
    initCurveAngles();
  }
  
  public boolean isOrthogonal() { return isOrthogonal; }

  public void setIsOrthogonal(boolean orth) { isOrthogonal = orth; }

  public void makeOrthogonal()
  {
    isOrthogonal = true;
    isCurved = false;
    initOrthogonalBendLocation();
  }
  
  private void initOrthogonalBendLocation()
  {
    Location center = getOrthogonalLocation();
    if ( center == null )
    {
      makeStraight();
    }
    else
    {
      centerLocation = center;
    }
  }
  
  public void setCopy(EdgeInterface aCopy) { copy = aCopy; }

  public EdgeInterface getCopy() { return copy; }

  public EdgeInterface getMasterCopy()
  {
    if ( copy == null )
    {
      return null;
    }
    Edge cp = this;
    while ( cp.copy != null )
    {
      cp = (Edge)cp.copy;
    };
    return cp;
  }
  
  public void setIsVisible(boolean v) { isVisible = v; }

  /**
   * Constructor for class Edge that constructs an edge based on the
   * start and end nodes.
   *
   * @param Node start: The start node of the new Edge.
   * @param Node end: The end node of the new Edge.
   */
  protected Edge(NodeInterface start, NodeInterface end)
  {
    initialize(start, end);
    color = DEFAULT_COLOR;
    centerLocation = new Location( ( (start.getX() + end.getX())/2 ),
                                   ( (start.getY() + end.getY())/2 ) );
  }

  public Edge(Edge anEdge, NodeInterface dNode, NodeInterface start, NodeInterface end)
  {
    initialize(start, end);
    if ( anEdge.color != null )
    {
      color = new Color(anEdge.color.getRGB());
    }
    else
    {
      color = DEFAULT_COLOR;
    }
    isSelected = anEdge.isSelected;
    isGenerated = anEdge.isGenerated;
    isCurved = anEdge.isCurved;
    isOrthogonal = anEdge.isOrthogonal;
    isOrthogonalLeftFromStart = anEdge.isOrthogonalLeftFromStart;
    centerLocation = new Location( anEdge.getCenterLocation() );
    startControlAngle = anEdge.startControlAngle;
    endControlAngle = anEdge.endControlAngle;
    directedSourceNode = (Node)dNode;
    isVisible = anEdge.isVisible;
  }

  /**
   * Returns whether or not this Edge is equal to the given Object.<br>
   * To be equal, the Object must be an edge and have the same start and end nodes.
   *
   * @return boolean: Whether or not this Edge is equal to the given Object.
   */
  public boolean equals(Object object)
  {
    try
    {
      Edge compareEdge = (Edge)object;
      if ((((Node)getStartNode()).equals(compareEdge.getStartNode()) && ((Node)getEndNode()).equals(compareEdge.getEndNode())) ||
          (((Node)getEndNode()).equals(compareEdge.getStartNode()) && ((Node)getStartNode()).equals(compareEdge.getEndNode())))
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    catch (ClassCastException cce)
    {
      return false;
    }
  }

  public Node getDirectedSourceNode()
  {
    return directedSourceNode;
  }

  public void setDirectedFrom(NodeInterface directedSourceNode)
  {
    if ( directedSourceNode == null )
    {
      this.directedSourceNode = null;
    }
    else if ( getStartNode() == directedSourceNode ||
              getEndNode() == directedSourceNode )
    {
      this.directedSourceNode = (Node)directedSourceNode;
    }
  }

  public boolean isBetween(NodeInterface firstNode, NodeInterface secondNode)
  {
    return ( (((Node)getStartNode()).equals(firstNode) && ((Node)getEndNode()).equals(secondNode)) ||
             (((Node)getStartNode()).equals(secondNode) && ((Node)getEndNode()).equals(firstNode)) );
  }

  // initialize instance variables...
  private void initialize(NodeInterface start, NodeInterface end)
  {
    isSelected = false;
    isGenerated = false;
    isCurved = false;
    isOrthogonal = false;
    isOrthogonalLeftFromStart = false;
    isAdded = false;
    startEdge = null;
    endEdge = null;
    startControlAngle = 0;
    endControlAngle = 0;
    startEdge = new HalfEdge((Node)start, this, null);
    endEdge = new HalfEdge((Node)end, this, startEdge);
    startEdge.setTwinEdge(endEdge);
    isVisible = true;
  }

  /**
   * Returns the start Node of this Edge.
   *
   * @return Node: The start Node of this Edge.
   */
  public NodeInterface getStartNode() { return startEdge.getSourceNode(); }

  /**
   * Returns the end Node of this Edge.
   *
   * @return Node: The end Node of this Edge.
   */
  public NodeInterface getEndNode() { return endEdge.getSourceNode(); }

  public HalfEdge getStartHalfEdge() { return startEdge; }

  public HalfEdge getEndHalfEdge() { return endEdge; }

  public HalfEdge getHalfEdgeFrom(Node aNode)
  {
    if ( aNode == getStartNode() )
    {
      return startEdge;
    }
    else if ( aNode == getEndNode() )
    {
      return endEdge;
    }
    else
    {
      return null;
    }
  }

  public HalfEdge getHalfEdgeTo(Node aNode)
  {
    if ( aNode == getEndNode() )
    {
      return startEdge;
    }
    else if ( aNode == getStartNode() )
    {
      return endEdge;
    }
    else
    {
      return null;
    }
  }

  // set the next edge in counter clockwise order around the given node.
  public void setNextInOrderFrom( NodeInterface sourceNode, EdgeInterface nextEdge )
  {
    getHalfEdgeFrom( (Node)sourceNode ).setPrevious( ((Edge)nextEdge).getHalfEdgeTo( (Node)sourceNode ) );
  }

  public void setPreviousInOrderFrom( NodeInterface sourceNode, EdgeInterface prevEdge )
  {
    getHalfEdgeTo( (Node)sourceNode ).setNext( ((Edge)prevEdge).getHalfEdgeFrom( (Node)sourceNode ) );
  }

  public EdgeInterface getNextInOrderFrom( NodeInterface sourceNode )
  {
    return getHalfEdgeFrom( (Node)sourceNode ).getPrevious().getParentEdge();
  }

  public EdgeInterface getPreviousInOrderFrom( NodeInterface sourceNode )
  {
    return getHalfEdgeTo( (Node)sourceNode ).getNext().getParentEdge();
  }

  public int getLowerIndex()
  {
    return Math.min(((Node)getStartNode()).getIndex(), ((Node)getEndNode()).getIndex());
  }

  public int getHigherIndex()
  {
    return Math.max(((Node)getStartNode()).getIndex(), ((Node)getEndNode()).getIndex());
  }

  /**
   * Returns whether or not this Edge is selected.
   *
   * @return boolean: Whether or not this Edge is selected.
   */
  public boolean isSelected() { return isSelected; }

  /**
   * Sets whether or not this Edge is selected.
   *
   * @param boolean state: Whether or not this Edge is selected.
   */
  public void setSelected(boolean state) { isSelected = state; }

  /**
   * Toggles whether or not this Edge is selected.
   */
  public void toggleSelected() { isSelected = !isSelected; }

  /**
   * Returns a String representation of this Edge.
   *
   * @return String: A String representation of this Edge.
   */
  public String toString()
  {
    return(((Node)getStartNode()).toString() + " --> " + ((Node)getEndNode()).toString());
  }

/*  public String infoString()
  {
    return "used: " + used + " added: " + added + " backEdge: " + backEdge +
    "old: " + isOld + " isGeneratedEdge: " + isGeneratedEdge +
    "startNode: " + getStartNode() + " endNode: " + getEndNode() + "hash: " + hashCode() +
    "\ns he: " + startEdge.infoString() + "\ne he: " + endEdge.infoString();
  }*/

  /**
   * Returns the Node at the other end of this Edge from the given Node.
   *
   * @param Node aNode: The Node to use to find the Node at the other end of this Edge.
   * @return Node: The Node at the other end of this Edge from the given Node.
   */
  public NodeInterface otherEndFrom(NodeInterface aNode)
  {
    return EdgeCycleSupport.otherEndFrom(this, aNode);
  }

  public Vector edgesFromSameCycle()
  {
    return EdgeCycleSupport.edgesFromSameCycle(this);
  }

  public Vector edgesFromSameCycleOnOtherSide()
  {
    return EdgeCycleSupport.edgesFromSameCycleOnOtherSide(this);
  }

  public boolean isDirected() { return directedSourceNode != null; }

  public QuadCurve2D.Double getQuadCurve()
  {
    Location sLocation, eLocation;
    sLocation = ((Node)getStartNode()).getLocation();
    eLocation = ((Node)getEndNode()).getLocation();
    return new QuadCurve2D.Double( sLocation.intX(), sLocation.intY(),
                                   centerLocation.intX()*2 - sLocation.intX()/2 - eLocation.intX()/2,
                                   centerLocation.intY()*2 - sLocation.intY()/2 - eLocation.intY()/2,
                                   eLocation.intX(), eLocation.intY() );
  }

  public void draw(Graphics2D g2, boolean drawSelected)
  {
    draw(g2, 0, 0, drawSelected);
  }

  public void draw( Graphics2D g2, int xOffset, int yOffset,
                    boolean drawSelected )
  {
    if ( isVisible &&
         !getStartNode().getLocation().equals(getEndNode().getLocation()) )
    {
      Location sLocation, eLocation, cLocation;
      sLocation = ((Node)getStartNode()).getLocation();
      eLocation = ((Node)getEndNode()).getLocation();
      cLocation = centerLocation;
      sLocation = new Location( sLocation.intX() + xOffset, sLocation.intY() + yOffset );
      eLocation = new Location( eLocation.intX() + xOffset, eLocation.intY() + yOffset );
      cLocation = new Location( cLocation.intX() + xOffset, cLocation.intY() + yOffset );

      float dash1[] = {(float)SELECTED_DASH_LENGTH};
      if ( isSelected && drawSelected )
      {
        g2.setStroke(new BasicStroke( (float)2*THICKNESS,
                                      BasicStroke.CAP_BUTT,
                                      BasicStroke.JOIN_MITER,
                                      10.0f, dash1, 0.0f ));
        g2.setColor(SELECTED_COLOR);
        if ( isCurved )
        {
          drawCurved( g2, sLocation, eLocation, cLocation );
        }
        else if ( isOrthogonal )
        {
          drawOrthogonal( g2, sLocation, eLocation, cLocation );
        }
        else
        {
          drawStraight( g2, sLocation, eLocation );
        }
      }
      g2.setColor(color);
      if ( isGenerated )
      {
        dash1[0] = (float)GENERATED_DASH_LENGTH;
        g2.setStroke(new BasicStroke( (float)THICKNESS,
                                      BasicStroke.CAP_BUTT,
                                      BasicStroke.JOIN_MITER,
                                      10.0f, dash1, 0.0f ));
      }
      else
      {
        g2.setStroke(new BasicStroke( (float)THICKNESS ));
      }
      if ( isCurved )
      {
        drawCurved( g2, sLocation, eLocation, cLocation );
      }
      else if ( isOrthogonal )
      {
        drawOrthogonal( g2, sLocation, eLocation, cLocation );
      }
      else
      {
        drawStraight( g2, sLocation, eLocation );
      }
      g2.setStroke(new BasicStroke( (float)THICKNESS ));
      g2.setColor(color);
      if ( isDirected() )
      {
        Location dLocation = directedSourceNode.getLocation();
        dLocation = new Location( dLocation.intX() + xOffset, dLocation.intY() + yOffset );
        drawDirected( g2, xOffset, yOffset );
      }
      else if ( drawUndirectedMidpointMarkers && ( isCurved || isOrthogonal ) )
      {
        if ( isSelected && drawSelected )
        {
          g2.setStroke(new BasicStroke( 1.0f ));
          g2.setColor(SELECTED_COLOR);
          g2.fill( new Ellipse2D.Double( cLocation.intX()-(THICKNESS+1),
                                         cLocation.intY()-(THICKNESS+1),
                                         (THICKNESS+1) * 2, (THICKNESS+1) * 2 ) );
          g2.setColor(color);
        }
        g2.fill( new Ellipse2D.Double( cLocation.intX()-THICKNESS,
                                       cLocation.intY()-THICKNESS,
                                       THICKNESS * 2, THICKNESS * 2 ) );
      }
    }
  }

  /**
   * Draw the Edge, directed from the given source node, allowing it to be drawn
   * with a different Color.
   * Directed Edges are drawn with a triangle indicating their
   * direction at their centre point.
   *
   * @param Graphics aPen: The Graphics object to use to draw the Edge.
   */
  private void drawDirected( Graphics2D g2, int xOffset, int yOffset )
  {
    g2.fill(getDirectionArrow(directedSourceNode, xOffset, yOffset));
  }

  public Polygon getDirectionArrow(Node directionSource, int xOffset, int yOffset)
  {
    return getDirectionArrow(directionSource, xOffset, yOffset, 0, 0);
  }

  public Polygon getDirectionArrow(Node directionSource, int xOffset, int yOffset,
                                   int extraWidth, int extraHeight)
  {
    double edgeAngle = -1.0 * getDirectedAngle(directionSource);
    double midPointX = centerLocation.intX() + xOffset;
    double midPointY = centerLocation.intY() + yOffset;
    int width = ARROW_WIDTH + extraWidth;
    int height = ARROW_HEIGHT + extraHeight;
    midPointX = (int)Math.round( midPointX - height/2.0 * Math.cos( Math.toRadians( edgeAngle ) ) );
    midPointY = (int)Math.round( midPointY - height/2.0 * Math.sin( Math.toRadians( edgeAngle ) ) );

    int triX[] = new int[3];
    int triY[] = new int[3];
    triX[0] = (int)Math.round( midPointX + width * Math.cos( Math.toRadians( edgeAngle+90.0 ) ) );
    triX[1] = (int)Math.round( midPointX + width * Math.cos( Math.toRadians( edgeAngle-90.0 ) ) );
    triX[2] = (int)Math.round( midPointX + height * Math.cos( Math.toRadians( edgeAngle ) ) );
    triY[0] = (int)Math.round( midPointY + width * Math.sin( Math.toRadians( edgeAngle+90.0 ) ) );
    triY[1] = (int)Math.round( midPointY + width * Math.sin( Math.toRadians( edgeAngle-90.0 ) ) );
    triY[2] = (int)Math.round( midPointY + height * Math.sin( Math.toRadians( edgeAngle ) ) );
    return new Polygon(triX, triY, 3);
  }

  private void drawCurved( Graphics2D g2, Location sLocation,
                           Location eLocation, Location cLocation )
  {
    g2.draw( new QuadCurve2D.Double( sLocation.intX(), sLocation.intY(),
                                     cLocation.intX()*2 - sLocation.intX()/2 - eLocation.intX()/2,
                                     cLocation.intY()*2 - sLocation.intY()/2 - eLocation.intY()/2,
                                     eLocation.intX(), eLocation.intY() ) );
  }

  public QuadCurve2D.Double getCurve()
  {
    return getCurve(0,0);
  }

  public QuadCurve2D.Double getCurve(int xOffset, int yOffset)
  {
    int cPoint[] = new int[6];
    cPoint[0] = getStartNode().getLocation().intX() + xOffset;
    cPoint[1] = getStartNode().getLocation().intY() + yOffset;
    cPoint[4] = getEndNode().getLocation().intX() + xOffset;
    cPoint[5] = getEndNode().getLocation().intY() + yOffset;
    cPoint[2] = (centerLocation.intX() + xOffset)*2 - cPoint[0]/2 - cPoint[4]/2;
    cPoint[3] = (centerLocation.intY() + yOffset)*2 - cPoint[1]/2 - cPoint[5]/2;
    return new QuadCurve2D.Double( cPoint[0], cPoint[1], cPoint[2],
                                   cPoint[3], cPoint[4], cPoint[5] );
  }

  public Polygon getBend()
  {
    return getBend(0,0);
  }
  
  public Polygon getBend(int xOffset, int yOffset)
  {
    int triX[] = new int[3];
    int triY[] = new int[3];
    triX[0] = getStartNode().getLocation().intX() + xOffset;
    triX[1] = centerLocation.intX() + xOffset;
    triX[2] = getEndNode().getLocation().intX() + xOffset;
    triY[0] = getStartNode().getLocation().intY() + yOffset;
    triY[1] = centerLocation.intY() + yOffset;
    triY[2] = getEndNode().getLocation().intY() + yOffset;
    return new Polygon(triX, triY, 3);
  }
  
  private void drawStraight( Graphics2D g2, Location sLocation,
                             Location eLocation )
  {
    g2.draw( new Line2D.Double( sLocation.intX(), sLocation.intY(),
                                eLocation.intX(), eLocation.intY() ) );
  }
  
  private void drawOrthogonal( Graphics2D g2, Location sLocation,
                               Location eLocation, Location cLocation )
  {
    g2.draw( new Line2D.Double( sLocation.intX(), sLocation.intY(),
                                cLocation.intX(), cLocation.intY() ) );
    g2.draw( new Line2D.Double( cLocation.intX(), cLocation.intY(),
                                eLocation.intX(), eLocation.intY() ) );
  }

  /**
   * Save this Edge to the File that is provided as a Parameter. The Nodes that
   * are the end points of the Edge are not saved since we assume that Node locations
   * are unique identifiers for the Nodes.
   *
   * @param PrintWriter aFile: The file to save to that is open/ready for output.
   */
  public void saveTo(PrintWriter aFile)
  {
    EdgePersistenceSupport.saveTo(this, aFile);
  }

  /**
   * Load this Edge from the File that is provided. Note that the nodes themselves are
   * not loaded. We are actually making temporary nodes here that do not correspond to
   * the actual graph nodes that this edge connects.  We'll have to throw out these
   * TEMP nodes later and replace them with the actual graph nodes that connect to this
   * edge.
   *
   * @param BufferedReader aFile: The file to load from that is open/ready for input.
   */
  public static Edge loadFrom(BufferedReader aFile, Vector nodeVector) throws IOException
  {
    return EdgePersistenceSupport.loadFrom(aFile, nodeVector);
  }

  /**
   * Returns the slope of this Edge.
   *
   * @return double: The slope of this Edge.
   */
  public double getSlope()
  {
    return EdgeGeometrySupport.getSlope(this);
  }

  /**
   * Returns the length of this Edge.
   *
   * @return double: The length of this Edge.
   */
  public double getStraightLength()
  {
    return EdgeGeometrySupport.getStraightLength(this);
  }

  public double getLength()
  {
    return EdgeGeometrySupport.getLength(this);
  }

  public void setCenterLocation(Location aLocation)
  {
    centerLocation = new Location(aLocation);
  }

  public void initCurveAngles()
  {
    EdgeGeometrySupport.initCurveAngles(this);
  }
  
  public void translate(int transX, int transY)
  {
    EdgeGeometrySupport.translate(this, transX, transY);
  }

  public void rotate(Location referencePoint, double angle)
  {
    EdgeGeometrySupport.rotate(this, referencePoint, angle);
  }

  public void scaleBy( double minX, double minY, double xFactor, double yFactor)
  {
    EdgeGeometrySupport.scaleBy(this, minX, minY, xFactor, yFactor);
  }

  public void update()
  {
    EdgeGeometrySupport.update(this);
  }

  public static Location getIntersectionLocation( Location l1, Location l2,
                                                  Location l3, Location l4 )
  {
    return EdgeGeometrySupport.getIntersectionLocation(l1, l2, l3, l4);
  }
  
  public Location getNormalLocation()
  {
    return EdgeGeometrySupport.getNormalLocation(this);
  }
  
  public Location getOrthogonalLocation()
  {
    return EdgeGeometrySupport.getOrthogonalLocation(this);
  }
  
  // determines the turn orientation of going from s to e and then to c.
  public double getTurnOrientation()
  {
    return EdgeGeometrySupport.getTurnOrientation(this);
  }
  
  public boolean isLeftTurn( double turnOrientation )
  {
    return EdgeGeometrySupport.isLeftTurn(turnOrientation);
  }
  
  public boolean isRightTurn( double turnOrientation )
  {
    return EdgeGeometrySupport.isRightTurn(turnOrientation);
  }

  public Location getCenterLocation() { return centerLocation; }


  double getStartControlAngleInternal() { return startControlAngle; }

  void setStartControlAngleInternal(double angle) { startControlAngle = angle; }

  double getEndControlAngleInternal() { return endControlAngle; }

  void setEndControlAngleInternal(double angle) { endControlAngle = angle; }

  void setOrthogonalLeftFromStartInternal(boolean value)
  {
    isOrthogonalLeftFromStart = value;
  }

  public void makeStraight()
  {
    isCurved = false;
    isOrthogonal = false;
    update();
  }

  public void setExtender(EdgeExtender ex)
  {
    extender = ex;
  }

  public EdgeExtender getExtender()
  {
    return extender;
  }

  public boolean hasZeroLength()
  {
    return EdgeGeometrySupport.hasZeroLength(this);
  }

  public boolean intersects(Edge edge)
  {
    return EdgeGeometrySupport.intersects(this, edge);
  }

  public Location getLocationAtAngleFrom(Node pivotEndNode, double angle)
  {
    return EdgeGeometrySupport.getLocationAtAngleFrom(this, pivotEndNode, angle);
  }

  public double getDirectedAngle(Node directionSource)
  {
    return EdgeGeometrySupport.getDirectedAngle(this, directionSource);
  }

  public double getAngleFrom(Node aNode)
  {
    return EdgeGeometrySupport.getAngleFrom(this, aNode);
  }

  public Edge getEdge()
  {
    return this;
  }
}
