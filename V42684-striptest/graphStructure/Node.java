package graphStructure;

import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.geom.*;
import java.awt.font.*;

/**
 * This class represents a node object that can be used as end points for edges.
 * Nodes can be added, deleted or moved by using the
 * interface of the editor component.
 *
 * @author Jon Harris
 */
public class Node implements NodeInterface
{
  /** The radius in pixels to use when drawing this Node in the UI */
  public static int RADIUS = 5;
  public static int LINE_THICKNESS = 3;
  public static int DASH_LENGTH = 2;
  public static double MIN_FOR_SCALE = 10;
  /** The Color to use when drawing this Node in the UI */
  public static Color DEFAULT_COLOR = Color.blue;
  public static Color TEXT_COLOR = Color.black;
  public static Color SELECTED_COLOR = Color.black;
  public static Color SPECIAL_SELECTED_COLOR = Color.red;
  public static boolean OPAQUE_TEXT = true;

  public static Font drawTextFont = new Font("Courier", Font.BOLD, 12);

  private static TextLayout thisTl;

  private String label;
  private Color color;
  private boolean drawX;
  private Location location;
  private Edge accessEdge;
  private boolean isSelected;
  private boolean isSpecialSelected;
  private int index;
  private int numEdges;
  private NodeInterface copy;
  private boolean isAdded;
  private boolean isVisible;

  private NodeExtender extender;

  /* v4.27.4: optional Functional Tree metadata.
   * Stored per concrete node and persisted by GraphPersistenceSupport.
   */
  private String ftNodeType;
  private String ftProcessType;
  private String ftRolePath;

  public boolean isAdded() { return isAdded; }

  public void setIsAdded(boolean added) { isAdded = added; }

  public Color getColor() { return color; }

  public void setColor(Color aColor) { color = aColor; }

  public int getIndex() { return index; }

  public void setIndex(int index) { this.index = index; }

  public void setCopy(NodeInterface aCopy) { copy = aCopy; }

  public NodeInterface getCopy() { return copy; }

  public NodeInterface getMasterCopy()
  {
    if ( copy == null )
    {
      return null;
    }
    Node cp = this;
    while ( cp.copy != null )
    {
      cp = (Node)cp.copy;
    };
    return cp;
  }
  
  public void setDrawX(boolean draw) { drawX = draw; }

  public boolean getDrawX() { return drawX; }

  public int getNumEdges() { return numEdges; }

  public void setIsVisible(boolean v) { isVisible = v; }

  /**
   * Constructor for class Node
   */
  protected Node()
  {
    initialize();
  }

  /**
   * Constructor for class Node which assigns a label to the Node.
   *
   * @param String aLabel: A label to assign to the Node.
   */
  protected Node(String aLabel)
  {
    initialize();
    label = aLabel;
  }

  public Node(int x, int y)
  {
    initialize();
    location = new Location(x,y);
  }
  
  /**
   * Constructor for class Node which constructs the Node at the given location.
   *
   * @param Point aPoint: The point at which to locate the new Node.
   */
  protected Node(Location aPoint)
  {
    initialize();
    location = new Location(aPoint);
  }

  public Node(Node aNode)
  {
    initialize();
    location = new Location(aNode.getLocation());
    label = new String(aNode.label);
    if ( aNode.color != null )
    {
      color = new Color( aNode.color.getRGB() );
    }
    drawX = aNode.drawX;
    isSelected = aNode.isSelected;
    isVisible = aNode.isVisible;
    ftNodeType = aNode.ftNodeType;
    ftProcessType = aNode.ftProcessType;
    ftRolePath = aNode.ftRolePath;
  }

  /**
   * Constructor for class Node which assigns a label to the Node,
   * and constructs the Node at the given location.
   *
   * @param String aLabel: A label to assign to the Node.
   * @param Point aPoint: The point at which to locate the new Node.
   */
  protected Node(String aLabel, Point aPoint)
  {
    initialize();
    label = aLabel;
    location = new Location(aPoint);
  }

  /**
   * Returns the X coordinate of this Node's location.
   *
   * @return int: The X coordinate of this Node's location.
   */
  public int getX()
  {
    return location.intX();
  }

  /**
   * Returns the Y coordinate of this Node's location.
   *
   * @return int: The Y coordinate of this Node's location.
   */
  public int getY()
  {
    return location.intY();
  }

  // Initialize the node's data.
  private void initialize()
  {
    label = "";
    location = new Location(0,0);
    accessEdge = null;
    isSelected = false;
    isSpecialSelected = false;
    color = DEFAULT_COLOR;
    numEdges = 0;
    isVisible = true;
  }

  /**
   * Returns whether or not this Node is equal to the given Object.<br>
   * To be equal, the Object must be a Node and have the same location.
   *
   * @return boolean: Whether or not this Node is equal to the given Object.
   */
  public boolean equals(Object compareNode)
  {
    if ( compareNode instanceof Node )
    {
      return location.equals(((Node)compareNode).getLocation());
    }
    else if ( compareNode instanceof Point )
    {
      return location.equals((Point)compareNode);
    }
    else if ( compareNode instanceof Location )
    {
      return location.equals((Location)compareNode);
    }
    else
    {
      return false;
    }
  }

  public boolean contains(Point p, int radius)
  {
    return NodeGeometrySupport.contains(this, p, radius);
  }

  /**
   * Returns the label of this Node.
   *
   * @return String: This Node's label.
   */
  public String getLabel() { return label; }

  /**
   * Returns the location of this Node as a Point.
   *
   * @return Point: The location of this Node.
   */
  public Location getLocation() { return location; }

  /**
   * Returns whether or not this Node is selected.
   *
   * @return boolean: Whether or not this Node is selected.
   */
  public boolean isSelected() { return isSelected; }

  public boolean isSpecialSelected() { return isSpecialSelected; }

  public boolean hasNoIncidentEdges()
  {
    return NodeIncidentSupport.hasNoIncidentEdges(this);
  }

  public boolean hasOnlyOneIncidentEdge()
  {
    return NodeIncidentSupport.hasOnlyOneIncidentEdge(this);
  }

  public boolean hasOnlyTwoIncidentEdges()
  {
    return NodeIncidentSupport.hasOnlyTwoIncidentEdges(this);
  }

  /**
   * Returns a Vector of the Edge objects that are incident to this Node.
   *
   * @return Vector: The Edges that are incident to this Node.
   */
  public Vector incidentEdges()
  {
    return NodeIncidentSupport.incidentEdges(this);
  }

  public EdgeIterator incidentEdgesIterator()
  {
    return new EdgeIterator(this, accessEdge);
  }

  public Vector incidentEdgesInReverse()
  {
    return NodeIncidentSupport.incidentEdgesInReverse(this);
  }

  public EdgeIterator incidentEdgeInReverseIterator()
  {
    return new EdgeIterator(this, accessEdge);
  }

  public Vector incidentOutgoingEdges()
  {
    return NodeIncidentSupport.incidentOutgoingEdges(this);
  }

  public EdgeIterator incidentOutgoingEdgesIterator()
  {
    return new EdgeIterator(this, accessEdge);
  }

  public EdgeInterface incidentEdgeWith(NodeInterface aNode)
  {
    return NodeIncidentSupport.incidentEdgeWith(this, aNode);
  }

  /**
   * Sets the label of this Node to the given label.
   *
   * @param String newLabel: The new label to assign to this Node.
   */
  public void setLabel(String newLabel) { label = newLabel; }

  public void appendLabel(String newLabel) { label+= newLabel; }

  /**
   * Sets the location of this Node to the given Point's location.
   *
   * @param Point aPoint: The Point describing the new location for this node.
   */

  public void setLocation(Location aLocation)
  {
    location = new Location(aLocation);
  }

  /**
   * Sets the location of this Node to the given x and y coordinates.
   *
   * @param int x: The x coordinate of the new location for this node.
   * @param int y: The y coordinate of the new location for this node.
   */
  public void setLocation(int x, int y) { location = new Location(x, y); }

  public void setLocation(double x, double y) { location = new Location(x, y); }

  public void translate(int transX, int transY)
  {
    NodeGeometrySupport.translate(this, transX, transY);
  }

  public void rotate(Location referencePoint, double angle)
  {
    NodeGeometrySupport.rotate(this, referencePoint, angle);
  }

  public static double angleBetween(Location p1, Location p2, Location p3)
  {
    return NodeGeometrySupport.angleBetween(p1, p2, p3);
  }

  public static double angleBetween(Node node1, Node node2, Node node3, Node node4)
  {
    return NodeGeometrySupport.angleBetween(node1, node2, node3, node4);
  }

  public void scaleBy( double minX, double minY, double xFactor, double yFactor)
  {
    NodeGeometrySupport.scaleBy(this, minX, minY, xFactor, yFactor);
  }

  /**
   * Sets whether or not this Node is selected.
   *
   * @param boolean: Whether or not this Node is selected.
   */
  public void setSelected(boolean state) { isSelected = state; }

  public void setSpecialSelected(boolean state) { isSpecialSelected = state; }

  /**
   * Toggles whether or not this Node is selected.
   */
  public void toggleSelected() { isSelected = !isSelected; }

  public void toggleSpecialSelected() { isSpecialSelected = !isSpecialSelected; }

  public Edge getAccessEdge() { return accessEdge; }

  public void setAccessEdge(Edge aEdge) { accessEdge = aEdge; }

  public boolean hasEdge(EdgeInterface edge) { return NodeIncidentSupport.hasEdge(this, edge); }

  /**
   * Adds the given Edge as an incident Edge to this Node.
   *
   * @param Edge e: The Edge to add as incident to this Node.
   */
  public boolean addIncidentEdge(EdgeInterface edge)
  {
    return NodeIncidentSupport.addIncidentEdge(this, edge);
  }

  /**
   * Adds the given Edge as an incident Edge to this Node. Allowing duplicates.
   *
   * @param Edge e: The Edge to add as incident to this Node.
   */
  public void addIncidentEdgeNoCheck(EdgeInterface edge)
  {
    NodeIncidentSupport.addIncidentEdgeNoCheck(this, edge);
  }

  public void addEdgeBetween( EdgeInterface edge, EdgeInterface prev,
                              EdgeInterface next )
  {
    NodeIncidentSupport.addEdgeBetween(this, edge, prev, next);
  }

  public void resetIncidentEdges()
  {
    NodeIncidentSupport.resetIncidentEdges(this);
  }

  /**
   * Removes the given Edge from the incident Edges of this Node.
   *
   * @param Edge e: The edge to remove from the incident Edges of this Node.
   */
  public void deleteIncidentEdge(EdgeInterface edge)
  {
    NodeIncidentSupport.deleteIncidentEdge(this, edge);
  }

  public double distanceSquaredFrom(Node otherNode)
  {
    return NodeGeometrySupport.distanceSquaredFrom(this, otherNode);
  }

  /**
   * Returns a String representation of this Node (ie. label, x coordinate,
   * y coordinate).
   *
   * @return String: A String representation of this Node.
   */
  public String toString()
  {
    return(label + "(" + location.intX() + "," + location.intY() + ")");
  }

  public void printAll()
  {
    NodeIncidentSupport.printAll(this);
  }

  /**
   * Returns a Vector of all Nodes that are connected to this Node
   * by a single Edge.
   *
   * @return Vector: A Vector of all neighbouring Nodes of this Node.
   */
  public Vector neighbours()
  {
    return NodeIncidentSupport.neighbours(this);
  }

  /**
   * Draw the Node with default Colors.
   *
   * @param Graphics aPen: The Graphics object to use to draw the Node.
   */
  public void draw( Graphics2D g2, boolean drawSelected,
                    boolean showCoord, boolean showLabel )
  {
    draw( g2, 0, 0, drawSelected, showCoord, showLabel );
  }

  public void draw( Graphics2D g2, int xOffset, int yOffset,
                    boolean drawSelected, boolean showCoord, boolean showLabel )
  {
    if ( isVisible )
    {
      g2.setStroke(new BasicStroke( (float)LINE_THICKNESS ));
      g2.setColor(color);
      Location aLocation;
      aLocation = new Location(location.intX() + xOffset, location.intY() + yOffset);
      g2.fill (new Ellipse2D.Double( aLocation.intX() - RADIUS,
                                     aLocation.intY() - RADIUS,
                                     RADIUS * 2, RADIUS * 2) );
      // Draw a black border around the circle
      g2.setColor(Color.black);
      g2.draw ( new Ellipse2D.Double( aLocation.intX() - RADIUS,
                                      aLocation.intY() - RADIUS,
                                      RADIUS * 2, RADIUS * 2 ) );

      if ( isSpecialSelected )
      {
        float dash1[] = {(float)DASH_LENGTH};
        g2.setStroke(new BasicStroke( (float)LINE_THICKNESS,
                                      BasicStroke.CAP_BUTT,
                                      BasicStroke.JOIN_MITER,
                                      10.0f, dash1, 0.0f ));
        g2.setColor(SPECIAL_SELECTED_COLOR);
        g2.draw ( new Ellipse2D.Double( aLocation.intX() - (RADIUS+2),
                                        aLocation.intY() - (RADIUS+2),
                                        (RADIUS+2)*2, (RADIUS+2)*2 ) );
      }
      else if ( isSelected && drawSelected )
      {
        float dash1[] = {(float)DASH_LENGTH};
        g2.setStroke(new BasicStroke( (float)LINE_THICKNESS,
                                      BasicStroke.CAP_BUTT,
                                      BasicStroke.JOIN_MITER,
                                      10.0f, dash1, 0.0f ));
        g2.setColor(SELECTED_COLOR);
        g2.draw ( new Ellipse2D.Double( aLocation.intX() - (RADIUS+2),
                                        aLocation.intY() - (RADIUS+2),
                                        (RADIUS+2)*2, (RADIUS+2)*2 ) );
      }

      if ( drawX )
      {
        g2.setStroke(new BasicStroke( (float)LINE_THICKNESS*2.0f/3.0f ));
        g2.setColor(Color.black);
        g2.drawLine( aLocation.intX() - RADIUS, aLocation.intY() - RADIUS,
                     aLocation.intX() + RADIUS, aLocation.intY() + RADIUS );
        g2.drawLine( aLocation.intX() - RADIUS, aLocation.intY() + RADIUS,
                     aLocation.intX() + RADIUS, aLocation.intY() - RADIUS );
      }
      g2.setStroke(new BasicStroke( (float)LINE_THICKNESS ));
      if ( showLabel )
      {
        if ( label.length() > 0 )
        {
          thisTl = new TextLayout(label, drawTextFont, g2.getFontRenderContext());
          if ( OPAQUE_TEXT )
          {
            Rectangle2D bounds = thisTl.getBounds();
            g2.setColor(userInterface.GraphEditor.backgroundColor);
            g2.fill( new Rectangle2D.Double( aLocation.intX() + RADIUS+3,
                                             aLocation.intY() + RADIUS+2
                                               - bounds.getHeight() + 1,
                                             bounds.getWidth() + 2,
                                             bounds.getHeight() ) );
          }
          g2.setColor(TEXT_COLOR);
          thisTl.draw(g2, aLocation.intX() + RADIUS+3, aLocation.intY() + RADIUS+2);
        }
      }
      else if ( showCoord )
      {
        thisTl = new TextLayout( String.valueOf(location.intX()) + ", " +
                                 String.valueOf(location.intY()),
                                 drawTextFont, g2.getFontRenderContext());
        if ( OPAQUE_TEXT )
        {
          Rectangle2D bounds = thisTl.getBounds();
          g2.setColor(userInterface.GraphEditor.backgroundColor);
          g2.fill( new Rectangle2D.Double( aLocation.intX() + RADIUS+3,
                                           aLocation.intY() + RADIUS+2
                                             - bounds.getHeight() + 1,
                                           bounds.getWidth() + 2,
                                           bounds.getHeight() ) );
        }
        g2.setColor(TEXT_COLOR);
        thisTl.draw(g2, aLocation.intX() + RADIUS+3, aLocation.intY() + RADIUS+2);
      }
    }
  }

  /**
   * Save this Node to the File that is provided as a Parameter. Note that the
   * incident edges are not saved at this point.
   *
   * @param PrintWriter aFile: The file to save to that is open/ready for output.
   */
  public void saveTo(PrintWriter aFile)
  {
    NodePersistenceSupport.saveTo(this, aFile);
  }

  /**
   * Load this Node from the File that is provided. Note that the incident edges
   * are not connected.
   *
   * @param BufferedReader aFile: The file to load from that is open/ready for input.
   */
  public static Node loadFrom(BufferedReader aFile, Vector edgeIndices) throws IOException
  {
    return NodePersistenceSupport.loadFrom(aFile, edgeIndices);
  }


  void incrementNumEdgesInternal() { numEdges++; }

  void decrementNumEdgesInternal() { numEdges--; }

  void resetNumEdgesInternal() { numEdges = 0; }

  public void setFunctionalTreeMetadata(String nodeType, String processType, String rolePath)
  {
    ftNodeType = normalizeFunctionalTreeValue(nodeType);
    ftProcessType = normalizeFunctionalTreeValue(processType);
    ftRolePath = normalizeFunctionalTreeValue(rolePath);
  }

  public void clearFunctionalTreeMetadata()
  {
    ftNodeType = null;
    ftProcessType = null;
    ftRolePath = null;
  }

  public String getFunctionalTreeNodeType() { return ftNodeType == null ? "" : ftNodeType; }
  public String getFunctionalTreeProcessType() { return ftProcessType == null ? "" : ftProcessType; }
  public String getFunctionalTreeRolePath() { return ftRolePath == null ? "" : ftRolePath; }

  public boolean hasFunctionalTreeMetadata()
  {
    return (ftNodeType != null && ftNodeType.length() > 0) ||
           (ftProcessType != null && ftProcessType.length() > 0) ||
           (ftRolePath != null && ftRolePath.length() > 0);
  }

  private String normalizeFunctionalTreeValue(String value)
  {
    if ( value == null ) return null;
    value = value.trim();
    return value.length() == 0 ? null : value;
  }

  public void setExtender(NodeExtender ex)
  {
    extender = ex;
  }

  public NodeExtender getExtender()
  {
    return extender;
  }

  public Node getNode()
  {
    return this;
  }
}
