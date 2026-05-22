package graphStructure;

import java.util.*;
import java.awt.*;
import java.io.*;
import java.awt.geom.*;

import dataStructure.DoublyLinkedList;
import graphStructure.mementos.*;

/**
 * This class represents the model of the graph object, that stores all the data
 * that makes up the structure of the graph, and methods to perform operations
 * on this data such as saving to and loading from a file, adding nodes or edges
 * etc...
 *
 * @author Jon Harris
 */
public class Graph
{
  private String    label;
  private Vector    nodes;
  private boolean drawSelected;
  private Color drawColor;
  private String filePath;
  private DoublyLinkedList mementos;
  private MementoGrouper currentMemento;
  private boolean showCoords;
  private boolean showLabels;
  private boolean trackUndos;
  private boolean hasChangedSinceLastSave;
  private boolean hasChangedSinceLastDraw;
  private boolean logChangedSinceLastDraw;
  private Vector logEntries;
  private LogEntry currentLogEntry;
  private Graph parent;
  private boolean drawGrid;
  private int gridRows;
  private int gridCols;
  private int gridColWidth;
  private int gridRowHeight;
  private int gridOriginX;
  private int gridOriginY;
  private int gridDisplayRows;
  private int gridDisplayCols;
  private ProjectionLayoutConfig projectionLayoutConfig;
  private Vector projectionLinks;
  private SynLabelFormatter synLabelFormatter;
  private boolean opnMappingV1Present;
  private int opnLexicalItemCount;
  private int opnVerbDomainCount;
  private int opnPlacementRuleCount;
  private String opnMappingV1Summary;
  private int opnMappingV1ValidationPassed;
  private int opnMappingV1ValidationFailed;
  private String opnMappingV1ValidationSummary;
  private String opnMappingV1GeneratedUtterance;
  private boolean opnFrameGraphPresent;
  private int opnFrameGraphFrameCount;
  private int opnFrameGraphSlotCount;
  private int opnFrameGraphValidationPassed;
  private int opnFrameGraphValidationFailed;
  private String opnFrameGraphValidationDetails;
  private String opnFrameGraphSummary;
  private boolean opnLexiconPresent;
  private int opnLexiconEntryCount;
  private int opnLexiconValidationPassed;
  private int opnLexiconValidationFailed;
  private String opnLexiconValidationDetails;
  private boolean opnMorphologyValidationPresent;
  private int opnMorphologyValidationPassed;
  private int opnMorphologyValidationFailed;
  private String opnMorphologyValidationDetails;
  private String opnLexiconSummary;
  private boolean opnLanguageTreePreferred;
  private String opnLanguageTreeTypeSummary;

  /**
   * Constructor for class Graph.
   */
  public Graph()
  {
    label = "";
    nodes = new Vector();
    drawColor = null;
    drawSelected = true;
    filePath = "";
    mementos = new DoublyLinkedList();
    currentMemento = null;
    trackUndos = true;
    hasChangedSinceLastSave = false;
    hasChangedSinceLastDraw = true;
    showCoords = false;
    showLabels = true;
    logEntries = new Vector();
    currentLogEntry = null;
    parent = null;
    drawGrid = false;
    gridRows = -1;
    gridCols = -1;
    gridColWidth = 20;
    gridRowHeight = 20;
    gridOriginX = 0;
    gridOriginY = 0;
    gridDisplayRows = -1;
    gridDisplayCols = -1;
    projectionLayoutConfig = new ProjectionLayoutConfig();
    projectionLinks = new Vector();
    synLabelFormatter = new DefaultSynLabelFormatter();
    clearOPNMappingV1Info();
    opnLanguageTreePreferred = false;
    opnLanguageTreeTypeSummary = "";
  }

  /**
   * Constructor for class Graph that assigns a label to the Graph.
   *
   * @param String aLabel: The label to assign to the Graph.
   */
  public Graph(String aLabel)
  {
    this();
    label = aLabel;
  }

  /**
   * Constructor for class Graph that assigns a label to the Graph and
   * provides the initial node set for the Graph.
   *
   * @param String aLabel: The label to assign to the Graph.
   * @param Vector initialNodes: A Vector containing the initial Nodes for the Graph.
   */
  public Graph(String aLabel, Vector initialNodes)
  {
    this();
    label = aLabel;
    nodes = initialNodes;
  }

  public Graph(Graph aGraph)
  {
    parent = aGraph;
    label = new String(aGraph.label);
    opnMappingV1Present = aGraph.opnMappingV1Present;
    opnLexicalItemCount = aGraph.opnLexicalItemCount;
    opnVerbDomainCount = aGraph.opnVerbDomainCount;
    opnPlacementRuleCount = aGraph.opnPlacementRuleCount;
    opnMappingV1Summary = aGraph.opnMappingV1Summary;
    opnMappingV1ValidationPassed = aGraph.opnMappingV1ValidationPassed;
    opnMappingV1ValidationFailed = aGraph.opnMappingV1ValidationFailed;
    opnMappingV1ValidationSummary = aGraph.opnMappingV1ValidationSummary;
    opnMappingV1GeneratedUtterance = aGraph.opnMappingV1GeneratedUtterance;
    opnFrameGraphPresent = aGraph.opnFrameGraphPresent;
    opnFrameGraphFrameCount = aGraph.opnFrameGraphFrameCount;
    opnFrameGraphSlotCount = aGraph.opnFrameGraphSlotCount;
    opnFrameGraphValidationPassed = aGraph.opnFrameGraphValidationPassed;
    opnFrameGraphValidationFailed = aGraph.opnFrameGraphValidationFailed;
    opnFrameGraphValidationDetails = aGraph.opnFrameGraphValidationDetails;
    opnFrameGraphSummary = aGraph.opnFrameGraphSummary;
    opnLexiconPresent = aGraph.opnLexiconPresent;
    opnLexiconEntryCount = aGraph.opnLexiconEntryCount;
    opnLexiconValidationPassed = aGraph.opnLexiconValidationPassed;
    opnLexiconValidationFailed = aGraph.opnLexiconValidationFailed;
    opnLexiconValidationDetails = aGraph.opnLexiconValidationDetails;
    opnMorphologyValidationPresent = aGraph.opnMorphologyValidationPresent;
    opnMorphologyValidationPassed = aGraph.opnMorphologyValidationPassed;
    opnMorphologyValidationFailed = aGraph.opnMorphologyValidationFailed;
    opnMorphologyValidationDetails = aGraph.opnMorphologyValidationDetails;
    opnLexiconSummary = aGraph.opnLexiconSummary;
    opnLanguageTreePreferred = aGraph.opnLanguageTreePreferred;
    opnLanguageTreeTypeSummary = aGraph.opnLanguageTreeTypeSummary;
    drawSelected = aGraph.drawSelected;
    trackUndos = aGraph.trackUndos;
    hasChangedSinceLastSave = aGraph.hasChangedSinceLastSave;
    hasChangedSinceLastDraw = true;
    if ( aGraph.drawColor == null )
    {
      drawColor = null;
    }
    else
    {
      drawColor = new Color(aGraph.drawColor.getRGB());
    }
    filePath = new String(aGraph.filePath);
    nodes = new Vector();
    mementos = new DoublyLinkedList();
    currentMemento = null;
    showCoords = aGraph.showCoords;
    showLabels = aGraph.showLabels;
    logEntries = new Vector();
    currentLogEntry = null;
    drawGrid = aGraph.drawGrid;
    gridRows = aGraph.gridRows;
    gridCols = aGraph.gridCols;
    gridColWidth = aGraph.gridColWidth;
    gridRowHeight = aGraph.gridRowHeight;
    gridOriginX = aGraph.gridOriginX;
    gridOriginY = aGraph.gridOriginY;
    gridDisplayRows = aGraph.gridDisplayRows;
    gridDisplayCols = aGraph.gridDisplayCols;
    projectionLayoutConfig = new ProjectionLayoutConfig(aGraph.projectionLayoutConfig);
    projectionLinks = new Vector();
    synLabelFormatter = new DefaultSynLabelFormatter();
  }

  public String getShowString()
  {
    if ( showCoords )
    {
      return "Show Coordinates";
    }
    else
    {
      if ( showLabels )
      {
        return "Show Labels";
      }
      else
      {
        return "Show Nothing";
      }
    }
  }
  
  public void shareMementos(Graph aGraph) { mementos = aGraph.mementos; }

  public void setTrackUndos(boolean tu) { trackUndos = tu; initUndo(); }
  private void initUndo()
  {
    mementos = GraphUndoSupport.createUndoList();
    currentMemento = null;
  }

  
  public boolean getTrackUndos() { return trackUndos; }

  public void setDrawSelected(boolean draw) { drawSelected = draw; }

  public boolean getDrawSelected() { return drawSelected; }

  public void setDrawColor(Color aColor) { drawColor = aColor; }

  public Color getDrawColor() { return drawColor; }

  public void setShowCoords(boolean c)
  {
    showCoords = c;
    hasChangedSinceLastDraw = true;
  }

  public boolean getShowCoords() { return showCoords; }

  public void setShowLabels(boolean l)
  {
    showLabels = l;
    hasChangedSinceLastDraw = true;
  }

  public boolean getShowLabels() { return showLabels; }

  public String getFilePath() { return filePath; }

  public String getFileName()
  {
    if ( filePath == null || filePath.trim().length() == 0 )
    {
      return label != null && label.trim().length() > 0 ? label : "Untitled";
    }

    String normalizedPath = filePath.replace('\\', '/');
    int slashIndex = normalizedPath.lastIndexOf('/');
    String name = slashIndex >= 0 ? normalizedPath.substring(slashIndex + 1) : normalizedPath;

    int dotIndex = name.lastIndexOf('.');
    if ( dotIndex > 0 )
    {
      return name.substring(0, dotIndex);
    }
    return name;
  }

  public void setFilePath(String fp) { filePath = fp; }

  public boolean hasChangedSinceLastSave() { return hasChangedSinceLastSave; }

  public boolean hasChangedSinceLastDraw() { return hasChangedSinceLastDraw; }

  public boolean logChangedSinceLastDraw() { return logChangedSinceLastDraw; }

  public void markForRepaint() { hasChangedSinceLastDraw = true; }

  private int getRowHeight(int numRows, int height)
  {
    return GraphGridSupport.getRowHeight(numRows, height);
  }

  private int getColWidth(int numCols, int width)
  {
    return GraphGridSupport.getColWidth(numCols, width);
  }

  public void setGrid(int numRows, int rowHeight, int numCols, int colWidth,
                      boolean addMemento)
  {
    if ( addMemento && currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(GridSizeMemento.createGridSizeMemento(this));
    }
    int[] gridState = GraphGridSupport.normalizeGrid(numRows, rowHeight, numCols, colWidth);
    gridRows = gridState[0];
    gridRowHeight = gridState[1];
    gridCols = gridState[2];
    gridColWidth = gridState[3];
    resetGridDisplayWindow();
  }

  public void setGridArea(int numRows, int height, int numCols, int width,
                          boolean addMemento)
  {
    if ( addMemento && currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(GridSizeMemento.createGridSizeMemento(this));
    }
    int[] gridState = GraphGridSupport.normalizeGridArea(numRows, height, numCols, width);
    gridRows = gridState[0];
    gridRowHeight = gridState[1];
    gridCols = gridState[2];
    gridColWidth = gridState[3];
    resetGridDisplayWindow();
  }

  public int getGridRows() { return gridRows; }

  public int getGridCols() { return gridCols; }

  public int getGridColWidth() { return gridColWidth; }

  public int getGridRowHeight() { return gridRowHeight; }

  public void setGridDisplayWindow(int originX, int originY, int numRows, int numCols)
  {
    if ( currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(GridDisplayWindowMemento.createGridDisplayWindowMemento(this));
    }
    gridOriginX = originX;
    gridOriginY = originY;
    gridDisplayRows = GraphGridSupport.normalizeDisplayCount(numRows);
    gridDisplayCols = GraphGridSupport.normalizeDisplayCount(numCols);
  }

  public void resetGridDisplayWindow()
  {
    if ( currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(GridDisplayWindowMemento.createGridDisplayWindowMemento(this));
    }
    gridOriginX = 0;
    gridOriginY = 0;
    gridDisplayRows = GraphGridSupport.defaultDisplayCount(gridRows);
    gridDisplayCols = GraphGridSupport.defaultDisplayCount(gridCols);
  }

  public int getGridOriginX() { return gridOriginX; }

  public int getGridOriginY() { return gridOriginY; }

  public int getGridDisplayRows() { return GraphGridSupport.resolveDisplayCount(gridDisplayRows, gridRows); }

  public int getGridDisplayCols() { return GraphGridSupport.resolveDisplayCount(gridDisplayCols, gridCols); }

  public int getGridDisplayWidth() { return GraphGridSupport.getDisplayWidth(getGridDisplayCols(), gridColWidth); }

  public int getGridDisplayHeight() { return GraphGridSupport.getDisplayHeight(getGridDisplayRows(), gridRowHeight); }

  public int getGridDisplayMinX() { return gridOriginX; }

  public int getGridDisplayMinY() { return gridOriginY; }

  public int getGridDisplayMaxX() { return gridOriginX + getGridDisplayWidth(); }

  public int getGridDisplayMaxY() { return gridOriginY + getGridDisplayHeight(); }

  public void shiftGridDisplayWindow(int dx, int dy, boolean addMemento)
  {
    if ( addMemento && currentMemento != null && trackUndos )
    {
      currentMemento.addMemento(GridDisplayWindowMemento.createGridDisplayWindowMemento(this));
    }
    if ( dx != 0 || dy != 0 )
    {
      hasChangedSinceLastSave = true;
      hasChangedSinceLastDraw = true;
      gridOriginX += dx;
      gridOriginY += dy;
    }
  }

  public void setDrawGrid(boolean draw) { drawGrid = draw; }

  public boolean getDrawGrid()
  {
    return GraphGridSupport.shouldDrawGrid(drawGrid, gridRows, gridCols, gridRowHeight, gridColWidth);
  }

  public int getGridHeight()
  {
    if ( gridRows < 2 || gridRowHeight < 2 )
    {
      return 0;
    }
    return getGridDisplayHeight();
  }

  public int getGridWidth()
  {
    if ( gridCols < 2 || gridColWidth < 2 )
    {
      return 0;
    }
    return getGridDisplayWidth();
  }

  public void drawGrid(Graphics2D g2, int xOffset, int yOffset)
  {
    GraphGridSupport.drawGrid(g2, getDrawGrid(), getGridDisplayRows(), getGridDisplayCols(),
                              gridOriginX, gridOriginY, gridColWidth, gridRowHeight,
                              xOffset, yOffset);
  }

  public Location getClosestGridLocation(Location location)
  {
    return GraphGridSupport.getClosestGridLocation(location, gridOriginX, gridOriginY,
                                                   gridRowHeight, gridColWidth,
                                                   getGridDisplayRows(), getGridDisplayCols());
  }

  public boolean isOnGrid(Location location)
  {
    return GraphGridSupport.isOnGrid(location, gridOriginX, gridOriginY, gridColWidth, gridRowHeight);
  }
  public void newMemento(String title)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    currentMemento = GraphUndoSupport.newMemento(mementos, currentMemento, trackUndos, title);
  }
  public void renameMemento(String title)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphUndoSupport.renameMemento(currentMemento, trackUndos, title);
  }
  public void doneMemento()
  {
    currentMemento = GraphUndoSupport.doneMemento(mementos, currentMemento, trackUndos);
  }
  public void undoMemento()
  {
    GraphUndoSupport.undoMemento(this, currentMemento);
  }
  public void abortMemento()
  {
    currentMemento = GraphUndoSupport.abortMemento(trackUndos, currentMemento);
  }
  public boolean hasMoreUndos()
  {
    return GraphUndoSupport.hasMoreUndos(mementos);
  }
  public void undo()
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphUndoSupport.undo(this, mementos, trackUndos);
  }
  public MementoGrouper peekUndo()
  {
    return GraphUndoSupport.peekUndo(mementos);
  }
  public boolean hasMoreRedos()
  {
    return GraphUndoSupport.hasMoreRedos(mementos);
  }
  public void redo()
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphUndoSupport.redo(this, mementos, trackUndos);
  }
  public MementoGrouper peekRedo()
  {
    return GraphUndoSupport.peekRedo(mementos);
  }
  public String getLogString()
  {
    return GraphLogSupport.getLogString(logEntries);
  }

  
  public Vector getLogEntries() { return logEntries; }
  public LogEntry startLogEntry(String operationName)
  {
    LogEntry newEntry = new LogEntry(operationName, this, System.currentTimeMillis());
    if ( parent != null )
    {
      parent.startLogEntry(newEntry);
      return newEntry;
    }
    currentLogEntry = GraphLogSupport.attachLogEntry(logEntries, currentLogEntry, newEntry);
    return newEntry;
  }
  private void startLogEntry(LogEntry logEntry)
  {
    if ( parent != null )
    {
      parent.startLogEntry(logEntry);
    }
    else
    {
      currentLogEntry = GraphLogSupport.attachLogEntry(logEntries, currentLogEntry, logEntry);
    }
  }
  public void stopLogEntry(LogEntry logEntry)
  {
    if ( parent != null )
    {
      parent.stopLogEntry(logEntry);
      return;
    }
    else
    {
      logChangedSinceLastDraw = true;
    }
    currentLogEntry = GraphLogSupport.stopLogEntry(logEntry);
  }

  
  public Rectangle2D.Double getBounds()
  {
    return GraphBoundsSupport.getBounds(this);
  }

  public Rectangle2D.Double getBounds( int xAdd, int yAdd )
  {
    return GraphBoundsSupport.getBounds(this, xAdd, yAdd);
  }

  public Rectangle2D.Double getBounds( Vector someNodes )
  {
    return GraphBoundsSupport.getBounds(this, someNodes);
  }

  public Rectangle2D.Double getBounds( Vector someNodes, int xAdd, int yAdd )
  {
    return GraphBoundsSupport.getBounds(this, someNodes, xAdd, yAdd);
  }

  public Location getCenterPointLocation()
  {
    return GraphBoundsSupport.getCenterPointLocation(this);
  }

  public static Node partitionAroundMedianX(Vector pNodes, Vector lesser, Vector greater)
  {
    return GraphMedianSupport.partitionAroundMedianX(pNodes, lesser, greater);
  }

  public Node getMedianXNode()
  {
    return getMedianXNode(nodes);
  }

  public static Node getMedianXNode(Vector sNodes)
  {
    return GraphMedianSupport.getMedianXNode(sNodes);
  }

  public static Node getMedianOfMediansXNode(Node mNodes[])
  {
    return GraphMedianSupport.getMedianOfMediansXNode(mNodes);
  }

  public static Node partitionAroundMedianY(Vector pNodes, Vector lesser, Vector greater)
  {
    return GraphMedianSupport.partitionAroundMedianY(pNodes, lesser, greater);
  }

  public Node getMedianYNode()
  {
    return getMedianYNode(nodes);
  }

  public static Node getMedianYNode(Vector sNodes)
  {
    return GraphMedianSupport.getMedianYNode(sNodes);
  }

  public static Node getMedianOfMediansYNode(Node mNodes[])
  {
    return GraphMedianSupport.getMedianOfMediansYNode(mNodes);
  }
  
  public Graph copy()
  {
    return GraphCopySupport.copy(this);
  }
  
  /**
   * Returns a copy of the graph, maintaining the order of edges at each node.  
   * @param multiLevel - If true, nodes and edges will set their
   * copy field to the value of the copy field of the node or edge they are copying.
   * @return The copy of the graph
   */
  public Graph copy( boolean keepReferences )
  {
    return GraphCopySupport.copy(this, keepReferences);
  }


  public Graph copyNodes(Vector nodeVector)
  {
    return GraphCopySupport.copyNodes(this, nodeVector);
  }

  // copies all nodes in the nodeVector and ALL of their edges.
  public Graph copyNodes( Vector nodeVector, boolean keepReferences )
  {
    return GraphCopySupport.copyNodes(this, nodeVector, keepReferences);
  }

  public Graph copyNode(Node aNode)
  {
    return GraphCopySupport.copyNode(this, aNode);
  }

  public Graph copyNode( Node aNode, boolean keepCopyReferences )
  {
    return GraphCopySupport.copyNode(this, aNode, keepCopyReferences);
  }

  public Graph copyNode(Node aNode, boolean keepCopyReferences,
                        boolean updateCopyReferences)
  {
    return GraphCopySupport.copyNode(this, aNode, keepCopyReferences, updateCopyReferences);
  }

  // copies all of the edges in the vector and any related nodes.
  
  public Graph copyEdges( Vector edges )
  {
    return GraphCopySupport.copyEdges(this, edges);
  }

  public Graph copyEdges( Vector edges, boolean keepCopyReferences )
  {
    return GraphCopySupport.copyEdges(this, edges, keepCopyReferences);
  }

  public void resetCopyData()
  {
    GraphLookupSupport.resetCopyData(nodes);
  }

  public void clearOPNMappingV1Info()
  {
    opnMappingV1Present = false;
    opnLexicalItemCount = 0;
    opnVerbDomainCount = 0;
    opnPlacementRuleCount = 0;
    opnMappingV1Summary = "";
    opnMappingV1ValidationPassed = 0;
    opnMappingV1ValidationFailed = 0;
    opnMappingV1ValidationSummary = "";
    opnMappingV1GeneratedUtterance = "";
    clearOPNFrameGraphInfo();
    clearOPNLexiconInfo();
  }

  public void clearOPNFrameGraphInfo()
  {
    opnFrameGraphPresent = false;
    opnFrameGraphFrameCount = 0;
    opnFrameGraphSlotCount = 0;
    opnFrameGraphValidationPassed = 0;
    opnFrameGraphValidationFailed = 0;
    opnFrameGraphValidationDetails = "";
    opnFrameGraphSummary = "";
  }


  public void clearOPNLexiconInfo()
  {
    opnLexiconPresent = false;
    opnLexiconEntryCount = 0;
    opnLexiconValidationPassed = 0;
    opnLexiconValidationFailed = 0;
    opnLexiconValidationDetails = "";
    opnMorphologyValidationPresent = false;
    opnMorphologyValidationPassed = 0;
    opnMorphologyValidationFailed = 0;
    opnMorphologyValidationDetails = "";
    opnLexiconSummary = "";
  }


  public void setOPNLanguageTreePreferred(boolean preferred)
  {
    opnLanguageTreePreferred = preferred;
    hasChangedSinceLastDraw = true;
  }

  public boolean isOPNLanguageTreePreferred()
  {
    return opnLanguageTreePreferred;
  }

  public void setOPNLanguageTreeTypeSummary(String summary)
  {
    opnLanguageTreeTypeSummary = summary == null ? "" : summary.trim();
    hasChangedSinceLastDraw = true;
  }

  public void setOPNLanguageTreeTypeFromTopLabel(String topLabel)
  {
    String label = normalizeLanguageTreeTopLabel(topLabel);
    if ( "S".equals(label) )
    {
      setOPNLanguageTreeTypeSummary("Language Tree: DS tree; top=S; type=n-ary allowed; mode=lexical-axis placement");
    }
    else if ( "V".equals(label) )
    {
      setOPNLanguageTreeTypeSummary("Language Tree: functional/tree frame; top=V; type=n-ary allowed; mode=lexical-axis placement");
    }
    else if ( label.length() > 0 )
    {
      setOPNLanguageTreeTypeSummary("Language Tree: top=" + label + "; type=unspecified");
    }
    else
    {
      setOPNLanguageTreeTypeSummary("Language Tree: type=unspecified");
    }
  }

  private String normalizeLanguageTreeTopLabel(String topLabel)
  {
    if ( topLabel == null ) return "";
    String label = topLabel.trim();
    if ( label.length() >= 2 && label.startsWith("\"") && label.endsWith("\"") )
    {
      label = label.substring(1, label.length() - 1).trim();
    }
    int colon = label.indexOf(':');
    if ( colon >= 0 && colon < label.length() - 1 )
    {
      label = label.substring(colon + 1).trim();
    }
    int space = label.indexOf(' ');
    if ( space > 0 )
    {
      label = label.substring(0, space).trim();
    }
    return label.toUpperCase();
  }

  public boolean hasOPNLanguageTreeTypeInfo()
  {
    return opnLanguageTreeTypeSummary != null && opnLanguageTreeTypeSummary.length() > 0;
  }

  public String getOPNLanguageTreeTypeSummary()
  {
    return opnLanguageTreeTypeSummary == null ? "" : opnLanguageTreeTypeSummary;
  }

  public void setOPNLexiconInfo(int entries)
  {
    opnLexiconPresent = true;
    opnLexiconEntryCount = Math.max(0, entries);
    opnLexiconValidationPassed = 0;
    opnLexiconValidationFailed = 0;
    opnLexiconValidationDetails = "";
    opnMorphologyValidationPresent = false;
    opnMorphologyValidationPassed = 0;
    opnMorphologyValidationFailed = 0;
    opnMorphologyValidationDetails = "";
    opnLexiconSummary = "Lexicon: " + opnLexiconEntryCount +
      " entries, metadata only";
  }

  public void setOPNLexiconInfo(int entries, int validationPassed, int validationFailed, String validationDetails)
  {
    setOPNLexiconInfo(entries, validationPassed, validationFailed, validationDetails,
      false, 0, 0, "");
  }

  public void setOPNLexiconInfo(int entries, int validationPassed, int validationFailed, String validationDetails,
                                boolean morphologyPresent, int morphologyPassed, int morphologyFailed, String morphologyDetails)
  {
    opnLexiconPresent = true;
    opnLexiconEntryCount = Math.max(0, entries);
    opnLexiconValidationPassed = Math.max(0, validationPassed);
    opnLexiconValidationFailed = Math.max(0, validationFailed);
    opnLexiconValidationDetails = validationDetails == null ? "" : validationDetails.trim();
    opnMorphologyValidationPresent = morphologyPresent;
    opnMorphologyValidationPassed = Math.max(0, morphologyPassed);
    opnMorphologyValidationFailed = Math.max(0, morphologyFailed);
    opnMorphologyValidationDetails = morphologyDetails == null ? "" : morphologyDetails.trim();

    opnLexiconSummary = "Lexicon: " + opnLexiconEntryCount +
      " entries; lexicon validation: " + opnLexiconValidationPassed +
      " ok, " + opnLexiconValidationFailed + " fail";
    if ( opnLexiconValidationDetails.length() > 0 )
    {
      opnLexiconSummary += " (" + opnLexiconValidationDetails + ")";
    }
    if ( opnMorphologyValidationPresent )
    {
      opnLexiconSummary += "; morphology validation: " + opnMorphologyValidationPassed +
        " ok, " + opnMorphologyValidationFailed + " fail";
      if ( opnMorphologyValidationDetails.length() > 0 )
      {
        opnLexiconSummary += " (" + opnMorphologyValidationDetails + ")";
      }
    }
  }

  public boolean hasOPNLexiconInfo()
  {
    return opnLexiconPresent;
  }

  public String getOPNLexiconSummary()
  {
    return opnLexiconSummary == null ? "" : opnLexiconSummary;
  }

  public void setOPNFrameGraphInfo(int frames, int slots)
  {
    opnFrameGraphPresent = true;
    opnFrameGraphFrameCount = Math.max(0, frames);
    opnFrameGraphSlotCount = Math.max(0, slots);
    opnFrameGraphValidationPassed = 0;
    opnFrameGraphValidationFailed = 0;
    opnFrameGraphValidationDetails = "";
    opnFrameGraphSummary = "Frame graph: " + opnFrameGraphFrameCount +
      " frames, " + opnFrameGraphSlotCount + " slots, metadata only";
  }

  public void setOPNFrameGraphInfo(int frames, int slots, int validationPassed, int validationFailed, String validationDetails)
  {
    opnFrameGraphPresent = true;
    opnFrameGraphFrameCount = Math.max(0, frames);
    opnFrameGraphSlotCount = Math.max(0, slots);
    opnFrameGraphValidationPassed = Math.max(0, validationPassed);
    opnFrameGraphValidationFailed = Math.max(0, validationFailed);
    opnFrameGraphValidationDetails = validationDetails == null ? "" : validationDetails.trim();
    opnFrameGraphSummary = "Frame graph: " + opnFrameGraphFrameCount +
      " frames, " + opnFrameGraphSlotCount + " slots; frame validation: " +
      opnFrameGraphValidationPassed + " ok, " + opnFrameGraphValidationFailed + " fail";
    if ( opnFrameGraphValidationDetails.length() > 0 )
    {
      opnFrameGraphSummary += " (" + opnFrameGraphValidationDetails + ")";
    }
  }

  public boolean hasOPNFrameGraphInfo()
  {
    return opnFrameGraphPresent;
  }

  public String getOPNFrameGraphSummary()
  {
    return opnFrameGraphSummary == null ? "" : opnFrameGraphSummary;
  }

  public void setOPNMappingV1Info(int lexicalItems, int verbDomains, int placementRules)
  {
    setOPNMappingInfo("v1", lexicalItems, verbDomains, placementRules);
  }

  public void setOPNMappingInfo(String version, int lexicalItems, int verbDomains, int placementRules)
  {
    opnMappingV1Present = true;
    opnLexicalItemCount = Math.max(0, lexicalItems);
    opnVerbDomainCount = Math.max(0, verbDomains);
    opnPlacementRuleCount = Math.max(0, placementRules);
    if ( version == null || version.length() == 0 ) version = "v1";
    opnMappingV1Summary = "Mapping " + version + ": " + opnLexicalItemCount +
      " lexical items, " + opnVerbDomainCount +
      " verb domains, " + opnPlacementRuleCount + " placement rules";
  }

  public boolean hasOPNMappingV1Info()
  {
    return opnMappingV1Present;
  }

  public void setOPNMappingV1Validation(int passed, int failed, String details)
  {
    opnMappingV1ValidationPassed = Math.max(0, passed);
    opnMappingV1ValidationFailed = Math.max(0, failed);
    String base = "validation: " + opnMappingV1ValidationPassed + " ok, " +
      opnMappingV1ValidationFailed + " fail";
    if ( details != null && details.length() > 0 ) base += " (" + details + ")";
    opnMappingV1ValidationSummary = base;
  }

  public void setOPNMappingV1GeneratedUtterance(String generatedUtterance)
  {
    if ( generatedUtterance == null ) opnMappingV1GeneratedUtterance = "";
    else opnMappingV1GeneratedUtterance = generatedUtterance.trim();
  }

  public boolean hasOPNMappingV1ValidationFailures()
  {
    return opnMappingV1Present && opnMappingV1ValidationFailed > 0;
  }

  public int getOPNMappingV1ValidationFailed()
  {
    return opnMappingV1ValidationFailed;
  }

  public String getOPNMappingV1ValidationSummary()
  {
    return opnMappingV1ValidationSummary == null ? "" : opnMappingV1ValidationSummary;
  }

  public String getOPNMappingV1Summary()
  {
    String mappingSummary = opnMappingV1Summary == null ? "" : opnMappingV1Summary;
    String validationSummary = opnMappingV1ValidationSummary == null ? "" : opnMappingV1ValidationSummary;
    String generated = opnMappingV1GeneratedUtterance == null ? "" : opnMappingV1GeneratedUtterance;

    String summary = "";
    if ( hasOPNMappingV1ValidationFailures() && validationSummary.length() > 0 )
    {
      summary = validationSummary;
      if ( mappingSummary.length() > 0 ) summary += "; " + mappingSummary;
    }
    else
    {
      summary = mappingSummary;
      if ( validationSummary.length() > 0 ) summary += "; " + validationSummary;
    }

    if ( generated.length() > 0 )
    {
      if ( summary.length() > 0 ) summary += "; ";
      summary += "generated: " + generated;
    }
    return summary;
  }

  /**
   * Returns the label of this Graph
   *
   * @return String: The label of this Graph.
   */
  public String getLabel() { return label; }

  /**
   * Returns the Nodes contained within this Graph.
   *
   * @return Vector: A Vector containing the Nodes of this Graph.
   */
  public Vector getNodes() { return nodes; }

  public Node getNodeAt(int index)
  {
    return GraphLookupSupport.getNodeAt(nodes, index);
  }

  public int getNumNodes() { return nodes.size(); }

  /**
   * Sets the label of this Graph.
   *
   * @param String newLabel: The new label for this Graph.
   */
  public void setLabel(String newLabel) { label = newLabel; }

  public boolean edgeNumbersAreInSync()
  {
    return GraphStatsSupport.edgeNumbersAreInSync(this);
  }

  public void makeAllEdgesStraight()
  {
    GraphStatsSupport.makeAllEdgesStraight(getEdges());
  }

  public int getNumEdges()
  {
    return getNumEdges(nodes);
  }

  public int getNumEdges(Vector nodeVector)
  {
    return GraphStatsSupport.getNumEdges(nodeVector);
  }

  public int getNumGeneratedEdges()
  {
    return GraphStatsSupport.getNumGeneratedEdges(getEdges());
  }

  public int getNumCurvedEdges()
  {
    return GraphStatsSupport.getNumCurvedEdges(getEdges());
  }

  /**
   * Returns all of the Edges of this Graph as retrieved by asking
   * all of the Nodes in this Graph for their adjacent Edges.
   *
   * @return Vector: A Vector containing the Edges of this Graph.
   */
  public Vector getEdges()
  {
    return getEdges(nodes);
  }

  // get all edges incident to the nodes in the vector
  
  public Vector getEdges(Vector nodeVector)
  {
    return getEdges(nodeVector, false);
  }

  public Vector getCurvedEdges(Vector nodeVector)
  {
    return getEdges(nodeVector, true);
  }

  private Vector getEdges(Vector nodeVector, boolean onlyCurved)
  {
    return GraphStatsSupport.getEdges(nodeVector, onlyCurved);
  }

  public Node[] getRandomTriangularFace()
  {
    return GraphStatsSupport.getRandomTriangularFace(nodes);
  }

  /**
   * Returns a String representation of this Graph including it's label,
   * number of Nodes, and number of Edges.
   *
   * @return String: A String representation of this Graph.
   */
  public String toString()
  {
    return GraphStatsSupport.toSummaryString(label, nodes.size(), getNumEdges());
  }

  public void printAll()
  {
    GraphStatsSupport.printAll(nodes);
  }

  /**
   * Adds the given Node to the Nodes contained within this Graph.
   *
   * @param Node aNode: The Node to add to this Graph.
   */
  public void addNode(Node aNode)
  {
    addNode(aNode, true);
  }

  public void addNode(Node aNode, boolean addMemento)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.addNode(nodes, aNode, currentMemento, trackUndos, addMemento);
  }

  public Node createNode(Location aPoint)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    return GraphStructureMutationSupport.createNode(nodes, aPoint, currentMemento, trackUndos);
  }


  public void translateNode(Node aNode, int dx, int dy, boolean createMemento )
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphEdgeMutationSupport.translateNode(aNode, dx, dy, currentMemento, trackUndos, createMemento);
  }

  public void relocateNode(NodeInterface aNode, Location aLocation, boolean createMemento )
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphEdgeMutationSupport.relocateNode(aNode, aLocation, currentMemento, trackUndos, createMemento);
  }

  public void translateNodes( Vector someNodes, int dx, int dy, boolean createMemento )
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphEdgeMutationSupport.translateNodes(someNodes, dx, dy, currentMemento, trackUndos, createMemento);
  }
  
  private void translate( Vector someNodes, int dx, int dy, boolean createMemento )
  {
    GraphEdgeMutationSupport.translateNodes(someNodes, dx, dy, currentMemento, trackUndos, createMemento);
  }

  public void relocateEdge( Edge anEdge, Location newLocation, boolean createMemento )
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphEdgeMutationSupport.relocateEdge(anEdge, newLocation, currentMemento, trackUndos, createMemento);
  }
  
  public void curveEdge( Edge anEdge, int dx, int dy, boolean createMemento )
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphEdgeMutationSupport.curveEdge(anEdge, dx, dy, currentMemento, trackUndos, createMemento);
  }

  public void orthogonalizeEdge( Edge anEdge, boolean createMemento )
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphEdgeMutationSupport.orthogonalizeEdge(anEdge, currentMemento, trackUndos, createMemento);
  }
  
  public void straightenEdge( EdgeInterface anEdge, boolean createMemento )
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphEdgeMutationSupport.straightenEdge(anEdge, currentMemento, trackUndos, createMemento);
  }

  public void straightenEdges(boolean createMemento)
  {
    straightenEdges(getEdges(), createMemento);
  }
  
  public void straightenEdges(Vector edges, boolean createMemento)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphEdgeMutationSupport.straightenEdges(edges, currentMemento, trackUndos, createMemento);
  }
  
  public void updateEdge( EdgeInterface anEdge, boolean createMemento )
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphEdgeMutationSupport.updateEdge(anEdge, currentMemento, trackUndos, createMemento);
  }
  
  public void updateEdges(Vector edges, boolean createMemento)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphEdgeMutationSupport.updateEdges(edges, currentMemento, trackUndos, createMemento);
  }
  
  public void refreshEdgeCurves()
  {
    GraphEdgeMutationSupport.refreshEdgeCurves(getEdges());
  }

  public void updateEdgeCurveAngles()
  {
    GraphEdgeMutationSupport.updateEdgeCurveAngles(getEdges());
  }

  
  public void refreshOrthogonalEdges(Vector edges)
  {
    GraphEdgeMutationSupport.refreshOrthogonalEdges(edges);
  }
  public void changeNodeLabel ( NodeInterface aNode, String label, boolean createMemento )
  {
    if ( !label.equals(aNode.getLabel()) )
    {
      hasChangedSinceLastSave = true;
      hasChangedSinceLastDraw = true;
      GraphPropertyMutationSupport.changeNodeLabel(aNode, label, currentMemento, trackUndos, createMemento);
    }
  }
  public void changeNodeDrawX ( NodeInterface aNode, boolean drawX, boolean createMemento )
  {
    if ( drawX != aNode.getDrawX() )
    {
      hasChangedSinceLastSave = true;
      hasChangedSinceLastDraw = true;
      GraphPropertyMutationSupport.changeNodeDrawX(aNode, drawX, currentMemento, trackUndos, createMemento);
    }
  }
  public void changeNodeColor ( NodeInterface aNode, Color aColor, boolean createMemento )
  {
    if ( !aColor.equals( aNode.getColor() ) )
    {
      hasChangedSinceLastSave = true;
      hasChangedSinceLastDraw = true;
      GraphPropertyMutationSupport.changeNodeColor(aNode, aColor, currentMemento, trackUndos, createMemento);
    }
  }
  public void changeEdgeColor ( EdgeInterface anEdge, Color aColor, boolean createMemento )
  {
    if ( !aColor.equals( anEdge.getColor() ) )
    {
      hasChangedSinceLastSave = true;
      hasChangedSinceLastDraw = true;
      GraphPropertyMutationSupport.changeEdgeColor(anEdge, aColor, currentMemento, trackUndos, createMemento);
    }
  }
  public void changeEdgeDirection( EdgeInterface anEdge, NodeInterface sourceNode, boolean createMemento )
  {
    if ( (sourceNode != null && !sourceNode.equals( anEdge.getDirectedSourceNode() )) ||
         (sourceNode == null && anEdge.getDirectedSourceNode() != null) )
    {
      hasChangedSinceLastSave = true;
      hasChangedSinceLastDraw = true;
      GraphPropertyMutationSupport.changeEdgeDirection(anEdge, sourceNode, currentMemento, trackUndos, createMemento);
    }
  }
  public boolean isTriangle(Node sourceNode, Edge firstEdge, Edge secondEdge)
  {
    return GraphTopologySupport.isTriangle(sourceNode, firstEdge, secondEdge);
  }
  public boolean isInQuadrilateral(Edge anEdge)
  {
    return GraphTopologySupport.isInQuadrilateral(anEdge);
  }
  public void flip(Edge anEdge)
  {
    GraphTopologySupport.flip(this, anEdge);
  }


  public void addEdge( EdgeInterface newEdge, EdgeInterface startPrevEdge,
                       EdgeInterface endPrevEdge )
  {
    addEdge( newEdge, startPrevEdge, endPrevEdge, true );
  }

  public void addEdge( EdgeInterface newEdge, EdgeInterface startPrevEdge,
                       EdgeInterface endPrevEdge, boolean addMemento )
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.addEdge(newEdge, startPrevEdge, endPrevEdge,
                                          currentMemento, trackUndos, addMemento);
  }

  private void addEdge(Node start, Node end, boolean addMemento )
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.addEdge(this, start, end, currentMemento, trackUndos, addMemento);
  }

  public void addEdge(Node start, Node end)
  {
    addEdge(start, end, true);
  }

  public void addEdgeNoCheck(Edge anEdge)
  {
    addEdgeNoCheck(anEdge, true);
  }

  public void addEdgeNoCheck(Edge anEdge, boolean addMemento)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.addEdgeNoCheck(anEdge, currentMemento, trackUndos, addMemento);
  }


  public void addEdgeNoCheck(NodeInterface aNode, EdgeInterface anEdge)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.addEdgeNoCheck(aNode, anEdge);
  }

  public void addEdgeNoCheck(Node start, Node end)
  {
    addEdgeNoCheck(start, end, true);
  }

  public void addEdgeNoCheck(Node start, Node end, boolean addMemento)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.addEdgeNoCheck(this, start, end, currentMemento, trackUndos, addMemento);
  }

  public void addGeneratedEdgeNoCheck(Node start, Node end)
  {
    addGeneratedEdgeNoCheck(start, end, true);
  }

  public void addGeneratedEdgeNoCheck(Node start, Node end, boolean addMemento)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.addGeneratedEdgeNoCheck(start, end, currentMemento, trackUndos, addMemento);
  }

  /**
   * Adds the given Edge to the Graph by searching the Nodes for
   * ones with the given labels and creates a new Edge
   * using the Nodes found within the Graph.
   *
   * @param String startLabel: The label of the first end Node of the new Edge to add.
   * @param String endLabel: The label of the second end Node of the new Edge to add.
   */
  public void addEdge(String startLabel, String endLabel)
  {
    Node start, end;
    start = nodeNamed(startLabel);
    end = nodeNamed(endLabel);
    if ((start != null) && (end != null))
      addEdge(start, end);
  }

  /**
   * Removes the given Edge from this Graph by telling the end
   * Nodes of the Edge to delete the Edge from their incident
   * Edge list.
   *
   * @param Edge anEdge: The Edge to remove from this Graph.
   */
  public void deleteEdge(Edge anEdge)
  {
    deleteEdge(anEdge, true);
  }

  public void deleteEdge(Edge anEdge, boolean createMemento)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.deleteEdge(anEdge, currentMemento, trackUndos, createMemento);
  }

  /**
   * Removes the given Node from this Graph, deleting any Edges
   * incident to it.
   *
   * @param Node aNode: The Node to remove from this Graph.
   */
  public void deleteNode(Node aNode)
  {
    deleteNode(aNode, true);
  }

  public void deleteNode(Node aNode, boolean addMemento)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.deleteNode(this, nodes, aNode, addMemento, currentMemento, trackUndos);
  }
  public void makeGeneratedEdgePermanent(Edge anEdge)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.makeGeneratedEdgePermanent(anEdge, currentMemento, trackUndos);
  }
  public void makeGeneratedEdgesPermanent()
  {
    GraphStructureMutationSupport.makeGeneratedEdgesPermanent(this, getEdges());
  }
  public void deleteGeneratedEdges()
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    GraphStructureMutationSupport.deleteGeneratedEdges(this, nodes);
  }


  public void removeEdgeDirections()
  {
    removeEdgeDirections(true);
  }

  public void removeEdgeDirections(boolean createMemento)
  {
    GraphAppearanceSupport.removeEdgeDirections(this, getEdges(), createMemento);
  }

  public void clearNodeLabels()
  {
    clearNodeLabels(true);
  }

  public void clearNodeLabels(boolean createMemento)
  {
    GraphAppearanceSupport.clearNodeLabels(this, nodes, createMemento);
  }

  /**
   * Returns the Node with the given Label, or Null if none exists.
   *
   * @param String aLabel: The label of the Node to retrieve.
   * @return Node: The Node with label matching the given label.
   */
  public Node nodeNamed(String aLabel)
  {
    return GraphLookupSupport.nodeNamed(nodes, aLabel);
  }

  /**
   * Returns the Node at the given location, or Null if none exists.
   *
   * @param Point p: The Point representing the location of the Node to retrieve.
   * @return Node: The Node with location matching the given location.
   */
  public Node nodeAt(Point p)
  {
    return nodeAt(new Location(p));
  }

  public Node nodeAt(Location p)
  {
    return GraphLookupSupport.nodeAt(nodes, p);
  }

  /**
   * Returns the Edge whose midpoint is at (or very close to) the given location,
   * or Null if none exists.
   *
   * @param Point p: The Point representing the location of the Edge to retrieve.
   * @return Edge: The Edge with location matching the given location.
   */
  public Edge edgeAt(Location p)
  {
    return GraphLookupSupport.edgeAt(getEdges(), p);
  }

  public Vector getNodesInRectangle( Rectangle2D.Double rect )
  {
    return GraphSelectionSupport.getNodesInRectangle(nodes, rect);
  }

  public Vector getEdgesInRectangle( Rectangle2D.Double rect )
  {
    return GraphSelectionSupport.getEdgesInRectangle(getEdges(), rect);
  }

  /**
   * Returns the Edge that passes through the given location,
   * or Null if none exists.
   *
   * @param Point p: The Point that the Edge to retrieve must pass through.
   * @return Edge: The Edge passing through the given location.
   */
  /*public Edge edgeContainingPoint(Point p)
  {
    Vector edges = getEdges();
    Edge anEdge;
    double distance = 0;
    for (int i=0; i<edges.size(); i++)
    {
      anEdge = (Edge)edges.elementAt(i);
      distance = Line2D.ptLineDist(anEdge.getStartNode().getLocation().intX(),
                                   anEdge.getStartNode().getLocation().intY(),
                                   anEdge.getEndNode().getLocation().intX(),
                                   anEdge.getEndNode().getLocation().intY(),
                                   p.x,
                                   p.y);
      if (distance == 0)
        return anEdge;
    }
    return null;
  }*/

  /**
   * Returns all of the Nodes in this Graph that have been selected.
   *
   * @return Vector: A Vector containing all of the selected Nodes of this Graph.
   */
  public Vector selectedNodes()
  {
    return GraphSelectionSupport.selectedNodes(nodes);
  }

  /**
   * Returns all of the Edges in this Graph that have been selected.
   *
   * @return Vector: A Vector containing all of the selected Edges of this Graph.
   */
  public Vector selectedEdges()
  {
    return GraphSelectionSupport.selectedEdges(getEdges());
  }

  public void unselectAll()
  {
    hasChangedSinceLastDraw = true;
    GraphSelectionSupport.unselectAll(selectedEdges(), selectedNodes());
  }

  public void deleteSelected()
  {
    hasChangedSinceLastDraw = true;
    GraphSelectionSupport.deleteSelected(this, selectedEdges(), selectedNodes());
  }

  public void toggleEdgeSelection(Edge anEdge)
  {
    hasChangedSinceLastDraw = true;
    GraphSelectionSupport.toggleEdgeSelection(anEdge);
  }

  public void toggleNodeSelection(Node aNode)
  {
    hasChangedSinceLastDraw = true;
    GraphSelectionSupport.toggleNodeSelection(aNode);
  }

  public void selectNodes(Vector sNodes)
  {
    hasChangedSinceLastDraw = true;
    GraphSelectionSupport.selectNodes(sNodes);
  }

  public void selectEdges(Vector sEdges)
  {
    hasChangedSinceLastDraw = true;
    GraphSelectionSupport.selectEdges(sEdges);
  }

  public void deleteAll()
  {
    hasChangedSinceLastDraw = true;
    GraphSelectionSupport.deleteAll(this, new Vector(nodes));
  }

  public void resetColors(boolean createMemento)
  {
    hasChangedSinceLastDraw = true;
    GraphAppearanceSupport.resetColors(this, nodes, getEdges(), createMemento);
  }

  public ProjectionLayoutConfig getProjectionLayoutConfig()
  {
    if ( projectionLayoutConfig == null )
    {
      projectionLayoutConfig = new ProjectionLayoutConfig();
    }
    return new ProjectionLayoutConfig(projectionLayoutConfig);
  }

  public void setProjectionLayoutConfig(ProjectionLayoutConfig config)
  {
    projectionLayoutConfig = config == null ? new ProjectionLayoutConfig() : new ProjectionLayoutConfig(config);
    hasChangedSinceLastDraw = true;
  }

  public Vector getProjectionLinks()
  {
    return new Vector(projectionLinks);
  }

  public ProjectionLayoutResult getProjectionLayoutResult()
  {
    ProjectionLayoutResult result = new ProjectionLayoutResult();
    result.sourceBounds = computeSourceBounds();
    result.renderBounds = computeRenderBounds(result.sourceBounds);
    return result;
  }

  public void clearProjections()
  {
    projectionLinks = new Vector();
    hasChangedSinceLastDraw = true;
  }

  public void rebuildProjections()
  {
    clearProjections();
    buildProjection(ProjectionType.LEX);
    buildProjection(ProjectionType.SYN);
  }

  public void buildProjection(ProjectionType type)
  {
    if ( type == null )
    {
      return;
    }

    ProjectionSpec spec = getProjectionSpec(type);
    if ( spec == null || !spec.enabled )
    {
      return;
    }

    Vector sources = getProjectionSources(type);
    for ( int i=0; i<sources.size(); i++ )
    {
      Node sourceNode = (Node)sources.elementAt(i);
      ProjectionLink link = createProjectionLink(sourceNode, type, spec);
      if ( link != null )
      {
        projectionLinks.addElement(link);
      }
    }
    hasChangedSinceLastDraw = true;
  }

  public Vector getProjectionSources(ProjectionType type)
  {
    Vector sources = new Vector();
    ProjectionSourceSelector selector = getProjectionSourceSelector(type);
    if ( selector == null )
    {
      return sources;
    }

    for ( int i=0; i<nodes.size(); i++ )
    {
      Node node = (Node)nodes.elementAt(i);
      if ( selector.matches(node, this) )
      {
        sources.addElement(node);
      }
    }

    sortProjectionNodesByPosition(sources);
    return sources;
  }

  public boolean isProjectionLeafSource(Node node)
  {
    return node != null && countChildrenBelow(node) == 0;
  }

  public boolean isProjectionBranchingSource(Node node)
  {
    return node != null && countChildrenBelow(node) >= 2;
  }

  private ProjectionSourceSelector getProjectionSourceSelector(ProjectionType type)
  {
    if ( type == ProjectionType.LEX )
    {
      return new LexSourceSelector();
    }
    if ( type == ProjectionType.SYN )
    {
      return new SynSourceSelector();
    }
    return null;
  }

  private ProjectionSpec getProjectionSpec(ProjectionType type)
  {
    ProjectionLayoutConfig config = projectionLayoutConfig == null
                                    ? new ProjectionLayoutConfig()
                                    : projectionLayoutConfig;
    if ( type == ProjectionType.LEX )
    {
      return config.lex;
    }
    if ( type == ProjectionType.SYN )
    {
      return config.syn;
    }
    if ( type == ProjectionType.LOG )
    {
      return config.log;
    }
    return null;
  }

  private ProjectionLink createProjectionLink(Node sourceNode, ProjectionType type, ProjectionSpec spec)
  {
    if ( sourceNode == null || type == null || spec == null )
    {
      return null;
    }

    ProjectedLabel projectedLabel = new ProjectedLabel();
    projectedLabel.mode = spec.labelTransfer;
    projectedLabel.sourceKind = spec.labelSource;
    projectedLabel.text = computeProjectedLabel(sourceNode, type, spec);

    Node projectionNode = createProjectionNode(sourceNode, type, spec, projectedLabel.text);

    ProjectionNodeData nodeData = new ProjectionNodeData();
    nodeData.projectionType = type;
    nodeData.sourceNode = sourceNode;
    nodeData.direction = spec.direction;
    nodeData.projectedLabel = projectedLabel.text;

    ProjectionLink link = new ProjectionLink();
    link.sourceNode = sourceNode;
    link.projectionNode = projectionNode;
    link.projectionType = type;
    link.projectionNodeData = nodeData;
    link.projectedLabel = projectedLabel;
    return link;
  }

  private Node createProjectionNode(Node sourceNode, ProjectionType type, ProjectionSpec spec, String label)
  {
    Location location = computeProjectionNodeLocation(sourceNode, spec.direction);
    Node node = new Node(location.intX(), location.intY());
    node.setLabel(label);
    node.setColor(Node.DEFAULT_COLOR);
    node.setIsVisible(true);
    return node;
  }

  private Location computeProjectionNodeLocation(Node sourceNode, ProjectionDirection direction)
  {
    int sourceX = sourceNode.getLocation().intX();
    int sourceY = sourceNode.getLocation().intY();
    int offsetCells = projectionLayoutConfig == null ? 1 : Math.max(1, projectionLayoutConfig.projectionOffsetInGridUnits);
    int offsetX = offsetCells * Math.max(1, gridColWidth);
    int offsetY = offsetCells * Math.max(1, gridRowHeight);

    int targetX = sourceX;
    int targetY = sourceY;

    if ( getDrawGrid() )
    {
      if ( direction == ProjectionDirection.LEFT )
      {
        targetX = getGridDisplayMinX();
      }
      else if ( direction == ProjectionDirection.RIGHT )
      {
        targetX = getGridDisplayMaxX();
      }
      else if ( direction == ProjectionDirection.UP )
      {
        targetY = getGridDisplayMinY();
      }
      else if ( direction == ProjectionDirection.DOWN )
      {
        targetY = getGridDisplayMaxY();
      }
    }
    else
    {
      if ( direction == ProjectionDirection.LEFT )
      {
        targetX -= offsetX;
      }
      else if ( direction == ProjectionDirection.RIGHT )
      {
        targetX += offsetX;
      }
      else if ( direction == ProjectionDirection.UP )
      {
        targetY -= offsetY;
      }
      else if ( direction == ProjectionDirection.DOWN )
      {
        targetY += offsetY;
      }
    }

    return new Location(targetX, targetY);
  }

  private String computeProjectedLabel(Node sourceNode, ProjectionType type, ProjectionSpec spec)
  {
    if ( type == ProjectionType.SYN )
    {
      Vector orderedChildLabels = getOrderedChildLabels(sourceNode);
      if ( !orderedChildLabels.isEmpty() )
      {
        return synLabelFormatter == null ? "" : synLabelFormatter.format(orderedChildLabels);
      }

      String label = sourceNode.getLabel();
      return label == null ? "" : label.trim();
    }
    String label = sourceNode.getLabel();
    return label == null ? "" : label.trim();
  }

  private Vector getOrderedChildLabels(Node sourceNode)
  {
    Vector labels = new Vector();
    Vector children = getChildrenBelowSortedLeftToRight(sourceNode);
    for ( int i=0; i<children.size(); i++ )
    {
      Node child = (Node)children.elementAt(i);
      String label = child.getLabel();
      if ( label != null && label.trim().length() > 0 )
      {
        labels.addElement(label.trim());
      }
    }
    return labels;
  }

  private Vector getChildrenBelowSortedLeftToRight(Node node)
  {
    Vector children = new Vector();
    if ( node == null )
    {
      return children;
    }

    Vector neighbours = node.neighbours();
    int nodeY = node.getLocation().intY();
    for ( int i=0; i<neighbours.size(); i++ )
    {
      Node neighbour = (Node)neighbours.elementAt(i);
      if ( neighbour.getLocation().intY() > nodeY )
      {
        children.addElement(neighbour);
      }
    }

    sortProjectionNodesByPosition(children);
    return children;
  }

  private int countChildrenBelow(Node node)
  {
    if ( node == null )
    {
      return 0;
    }
    Vector neighbours = node.neighbours();
    int count = 0;
    int nodeY = node.getLocation().intY();
    for ( int i=0; i<neighbours.size(); i++ )
    {
      Node neighbour = (Node)neighbours.elementAt(i);
      if ( neighbour.getLocation().intY() > nodeY )
      {
        count++;
      }
    }
    return count;
  }

  private void sortProjectionNodesByPosition(Vector projectionNodes)
  {
    java.util.Collections.sort(projectionNodes, new java.util.Comparator()
    {
      public int compare(Object o1, Object o2)
      {
        Node n1 = (Node)o1;
        Node n2 = (Node)o2;
        int y1 = n1.getLocation().intY();
        int y2 = n2.getLocation().intY();
        if ( y1 != y2 )
        {
          return y1 - y2;
        }
        return n1.getLocation().intX() - n2.getLocation().intX();
      }
    });
  }

  private Rectangle computeSourceBounds()
  {
    if ( nodes == null || nodes.size() == 0 )
    {
      return new Rectangle();
    }

    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;

    for ( int i=0; i<nodes.size(); i++ )
    {
      Node node = (Node)nodes.elementAt(i);
      int x = node.getLocation().intX();
      int y = node.getLocation().intY();
      minX = Math.min(minX, x - Node.RADIUS);
      minY = Math.min(minY, y - Node.RADIUS);
      maxX = Math.max(maxX, x + Node.RADIUS);
      maxY = Math.max(maxY, y + Node.RADIUS);
    }

    return new Rectangle(minX, minY, Math.max(1, maxX - minX), Math.max(1, maxY - minY));
  }

  private Rectangle computeRenderBounds(Rectangle sourceBounds)
  {
    Rectangle renderBounds = sourceBounds == null ? new Rectangle() : new Rectangle(sourceBounds);
    for ( int i=0; i<projectionLinks.size(); i++ )
    {
      ProjectionLink link = (ProjectionLink)projectionLinks.elementAt(i);
      if ( link == null || link.projectionNode == null )
      {
        continue;
      }

      int projectionX = link.projectionNode.getLocation().intX();
      int projectionY = link.projectionNode.getLocation().intY();
      Rectangle nodeBounds = new Rectangle(projectionX - Node.RADIUS, projectionY - Node.RADIUS,
                                           Node.RADIUS * 2, Node.RADIUS * 2);
      renderBounds = renderBounds.union(nodeBounds);

      if ( link.sourceNode != null )
      {
        int sourceX = link.sourceNode.getLocation().intX();
        int sourceY = link.sourceNode.getLocation().intY();
        int minX = Math.min(sourceX, projectionX) - Node.RADIUS;
        int minY = Math.min(sourceY, projectionY) - Node.RADIUS;
        int width = Math.max(1, Math.abs(sourceX - projectionX) + (2 * Node.RADIUS));
        int height = Math.max(1, Math.abs(sourceY - projectionY) + (2 * Node.RADIUS));
        Rectangle lineBounds = new Rectangle(minX, minY, width, height);
        renderBounds = renderBounds.union(lineBounds);
      }
    }
    return renderBounds;
  }

  /**
   * Draws this Graph using the default Colors
   *
   * @param Graphics aPen: The Graphics object to use to draw this Graph.
   */
  public void draw(Graphics2D g2)
  {
    draw(g2, 0, 0);
  }

  public void draw(Graphics2D g2, int xOffset, int yOffset)
  {
    hasChangedSinceLastDraw = false;
    logChangedSinceLastDraw = false;
    GraphTransformSupport.draw(g2, getEdges(), nodes, xOffset, yOffset,
                               drawSelected, showCoords, showLabels);
  }

  public void rotate(Location pivotPoint, double angle, boolean createMemento)
  {
    GraphTransformSupport.rotate(nodes, getEdges(), pivotPoint, angle,
                                 currentMemento, trackUndos, createMemento);
  }

  public void translate(int dx, int dy, boolean createMemento)
  {
    hasChangedSinceLastSave = true;
    hasChangedSinceLastDraw = true;
    translate(nodes, dx, dy, createMemento);
  }

  /**
   * Save this Graph to the File that is provided as a Parameter. The label,
   * size, number of Nodes and number of Edges is outputted and the saveTo
   * method of each Node and Edge in the Graph is called.
   *
   * @param PrintWriter aFile: The file to save to that is open/ready for output.
   */
  public void saveTo(PrintWriter aFile)
  {
    GraphPersistenceSupport.saveTo(aFile, label, gridRows, gridRowHeight, gridCols, gridColWidth,
                                   nodes, getEdges());
    hasChangedSinceLastSave = false;
  }

  /**
   * Load this Graph from the File that is provided as a Parameter. The label
   * and scale are read in, and each Node and Edge stored in the file is loaded
   * by calling its loadFrom method.<br>
   * <br>
   * Note that after the Nodes and Edges are loaded, it is necessary to go through
   * the Graph and connected the Nodes and Edges properly.
   *
   * @param BufferedReader aFile: The file to load from that is open/ready for input.
   */
  public static Graph loadFrom(BufferedReader aFile) throws IOException
  {
    return GraphPersistenceSupport.loadFrom(aFile);
  }

  public boolean hasNodes()
  {
    return nodes.size() != 0;
  }

  public void enumerateNodeAndEdgeIndices()
  {
    GraphPersistenceSupport.enumerateNodeAndEdgeIndices(nodes, getEdges());
  }

  public EdgeInterface[] sortEdges()
  {
    return sortEdges(getEdges());
  }

  public EdgeInterface[] sortEdges(Vector edges)
  {
    return GraphPersistenceSupport.sortEdges(nodes, edges);
  }

  public void deleteAllEdges()
  {
    GraphPersistenceSupport.deleteAllEdges(nodes, getEdges(), currentMemento, trackUndos);
  }

  public boolean checkForDuplicateEdges()
  {
    return GraphPersistenceSupport.checkForDuplicateEdges(nodes, getEdges());
  }

  public void scaleTo(Rectangle2D.Double newBounds, boolean createMemento )
  {
    if ( !nodes.isEmpty() )
    {
      GraphPersistenceSupport.scaleTo(nodes, getEdges(), getBounds(), newBounds,
                                      currentMemento, trackUndos, createMemento);
    }
  }

  public Vector createNodeExtenders(Class NodeExtenderClass)
  {
    return GraphExtenderSupport.createNodeExtenders(nodes, NodeExtenderClass);
  }

  public Vector createEdgeExtenders(Class EdgeExtenderClass)
  {
    return GraphExtenderSupport.createEdgeExtenders(getEdges(), EdgeExtenderClass);
  }

  public Vector getNodeExtenders()
  {
    return GraphExtenderSupport.getNodeExtenders(nodes);
  }

  public Vector getEdgeExtenders()
  {
    return GraphExtenderSupport.getEdgeExtenders(getEdges());
  }

  public Vector getEdgeExtenders(Vector nodeVector)
  {
    return GraphExtenderSupport.getEdgeExtenders(getEdges(nodeVector));
  }
  
  public void permuteNodeOrder()
  {
    GraphExtenderSupport.permuteNodeOrder(nodes);
  }
}