package graphStructure;

import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import graphStructure.mementos.EdgeBetweenMemento;
import graphStructure.mementos.EdgeMovementMemento;
import graphStructure.mementos.MementoGrouper;
import graphStructure.mementos.NodeChangeMemento;
import graphStructure.mementos.NodeMovementMemento;

final class GraphPersistenceSupport
{
  private GraphPersistenceSupport() {}

  static void saveTo(PrintWriter aFile, String label, int gridRows, int gridRowHeight,
                     int gridCols, int gridColWidth, Vector nodes, Vector edges)
  {
    aFile.println(label);
    aFile.println(gridRows);
    aFile.println(gridRowHeight);
    aFile.println(gridCols);
    aFile.println(gridColWidth);
    aFile.println(nodes.size());
    enumerateNodeAndEdgeIndices(nodes, edges);
    for ( int i=0; i<nodes.size(); i++ )
    {
      ((Node)nodes.elementAt(i)).saveTo(aFile);
    }
    aFile.println(edges.size());
    for ( int i=0; i<edges.size(); i++ )
    {
      ((Edge)edges.elementAt(i)).saveTo(aFile);
    }

    saveFunctionalTreeNodeMetadata(aFile, nodes);
  }

  static Graph loadFrom(BufferedReader aFile) throws IOException
  {
    Graph aGraph = new Graph(aFile.readLine());
    int gridRows = Integer.valueOf(aFile.readLine()).intValue();
    int gridRowHeight = Integer.valueOf(aFile.readLine()).intValue();
    int gridCols = Integer.valueOf(aFile.readLine()).intValue();
    int gridColWidth = Integer.valueOf(aFile.readLine()).intValue();
    aGraph.setGrid(gridRows, gridRowHeight, gridCols, gridColWidth, false);

    int numNodes = Integer.valueOf(aFile.readLine()).intValue();
    Vector allEdgeIndices = new Vector();
    for ( int i=0; i<numNodes; i++ )
    {
      aGraph.addNode(Node.loadFrom(aFile, allEdgeIndices), false);
    }

    int numEdges = Integer.valueOf(aFile.readLine()).intValue();
    Vector edges = new Vector(numEdges);
    for ( int i=0; i<numEdges; i++ )
    {
      edges.addElement(Edge.loadFrom(aFile, aGraph.getNodes()));
    }

    for ( int i=0; i<numNodes; i++ )
    {
      Node aNode = (Node)aGraph.getNodes().elementAt(i);
      Vector edgeIndices = (Vector)allEdgeIndices.elementAt(i);
      for ( int j=0; j<edgeIndices.size(); j++ )
      {
        aNode.addIncidentEdgeNoCheck((Edge)edges.elementAt(((Integer)edgeIndices.elementAt(j)).intValue()-1));
      }
    }
    loadFunctionalTreeNodeMetadata(aFile, aGraph.getNodes());
    return aGraph;
  }


  private static void saveFunctionalTreeNodeMetadata(PrintWriter aFile, Vector nodes)
  {
    int count = 0;
    for ( int i=0; i<nodes.size(); i++ )
    {
      Node node = (Node)nodes.elementAt(i);
      if ( node.hasFunctionalTreeMetadata() ) count++;
    }
    if ( count == 0 ) return;

    aFile.println("#OPENGRAPHED_FT_NODE_META_V1");
    aFile.println(count);
    for ( int i=0; i<nodes.size(); i++ )
    {
      Node node = (Node)nodes.elementAt(i);
      if ( node.hasFunctionalTreeMetadata() )
      {
        aFile.println(node.getIndex() + "|" + escapeMeta(node.getFunctionalTreeNodeType()) +
                      "|" + escapeMeta(node.getFunctionalTreeProcessType()) +
                      "|" + escapeMeta(node.getFunctionalTreeRolePath()));
      }
    }
    aFile.println("#END_OPENGRAPHED_FT_NODE_META_V1");
  }

  private static void loadFunctionalTreeNodeMetadata(BufferedReader aFile, Vector nodes) throws IOException
  {
    String line;
    while ( (line = aFile.readLine()) != null )
    {
      if ( !line.equals("#OPENGRAPHED_FT_NODE_META_V1") )
      {
        continue;
      }
      String countLine = aFile.readLine();
      if ( countLine == null ) return;
      int count = 0;
      try { count = Integer.valueOf(countLine).intValue(); } catch (Exception ex) { count = 0; }
      for ( int i=0; i<count; i++ )
      {
        String meta = aFile.readLine();
        if ( meta == null ) return;
        String[] parts = splitMeta(meta);
        if ( parts.length >= 4 )
        {
          try
          {
            int index = Integer.valueOf(parts[0]).intValue();
            if ( index >= 1 && index <= nodes.size() )
            {
              ((Node)nodes.elementAt(index - 1)).setFunctionalTreeMetadata(unescapeMeta(parts[1]), unescapeMeta(parts[2]), unescapeMeta(parts[3]));
            }
          }
          catch (Exception ex) { }
        }
      }
      return;
    }
  }

  private static String[] splitMeta(String line)
  {
    java.util.Vector parts = new java.util.Vector();
    StringBuffer current = new StringBuffer();
    boolean escaped = false;
    for ( int i=0; line != null && i<line.length(); i++ )
    {
      char ch = line.charAt(i);
      if ( escaped )
      {
        current.append(ch);
        escaped = false;
      }
      else if ( ch == '\\' )
      {
        escaped = true;
      }
      else if ( ch == '|' )
      {
        parts.addElement(current.toString());
        current.setLength(0);
      }
      else
      {
        current.append(ch);
      }
    }
    parts.addElement(current.toString());
    String[] result = new String[parts.size()];
    for ( int i=0; i<parts.size(); i++ ) result[i] = (String)parts.elementAt(i);
    return result;
  }

  private static String escapeMeta(String value)
  {
    if ( value == null ) return "";
    StringBuffer out = new StringBuffer();
    for ( int i=0; i<value.length(); i++ )
    {
      char ch = value.charAt(i);
      if ( ch == '|' || ch == '\\' ) out.append('\\');
      out.append(ch);
    }
    return out.toString();
  }

  private static String unescapeMeta(String value)
  {
    return value == null ? "" : value;
  }

  static void enumerateNodeAndEdgeIndices(Vector nodes, Vector edges)
  {
    for ( int i=0; i<nodes.size(); i++ )
    {
      ((Node)nodes.elementAt(i)).setIndex(i+1);
    }
    for ( int i=0; i<edges.size(); i++ )
    {
      ((Edge)edges.elementAt(i)).setIndex(i+1);
    }
  }

  static EdgeInterface[] sortEdges(Vector nodes, Vector edges)
  {
    int count[] = new int[nodes.size()];
    EdgeInterface sortedEdges[] = new EdgeInterface[edges.size()];
    EdgeInterface sortedEdges2[] = new EdgeInterface[edges.size()];
    int i = 0;
    for ( i=0; i<nodes.size(); i++ )
    {
      count[i] = 0;
      ((Node)nodes.elementAt(i)).setIndex(i+1);
    }
    for ( i=0; i<edges.size(); i++ )
    {
      count[((EdgeInterface)edges.elementAt(i)).getHigherIndex()-1]++;
    }
    for ( i=1; i<nodes.size(); i++ )
    {
      count[i] += count[i-1];
    }
    for ( i=edges.size()-1; i>=0; i-- )
    {
      sortedEdges[count[((EdgeInterface)edges.elementAt(i)).getHigherIndex()-1]-1] = (EdgeInterface)edges.elementAt(i);
      count[((EdgeInterface)edges.elementAt(i)).getHigherIndex()-1]--;
    }

    for ( i=0; i<nodes.size(); i++ )
    {
      count[i] = 0;
    }
    for ( i=0; i<edges.size(); i++ )
    {
      count[sortedEdges[i].getLowerIndex()-1]++;
    }
    for ( i=1; i<nodes.size(); i++ )
    {
      count[i] += count[i-1];
    }
    for ( i=edges.size()-1; i>=0; i-- )
    {
      sortedEdges2[count[sortedEdges[i].getLowerIndex()-1]-1] = sortedEdges[i];
      count[sortedEdges[i].getLowerIndex()-1]--;
    }
    return sortedEdges2;
  }

  static void deleteAllEdges(Vector nodes, Vector edges, MementoGrouper currentMemento,
                             boolean trackUndos)
  {
    for ( int i=0; i<edges.size(); i++ )
    {
      Edge edge = (Edge)edges.elementAt(i);
      if ( currentMemento != null && trackUndos )
      {
        currentMemento.addMemento(EdgeBetweenMemento.createChangeMemento(edge));
      }
    }

    for ( int i=0; i<nodes.size(); i++ )
    {
      Node node = (Node)nodes.elementAt(i);
      if ( currentMemento != null && trackUndos )
      {
        currentMemento.addMemento(NodeChangeMemento.createChangeMemento(node));
      }
      node.resetIncidentEdges();
    }
  }

  static boolean checkForDuplicateEdges(Vector nodes, Vector edges)
  {
    EdgeInterface sortedEdges[] = sortEdges(nodes, edges);
    for ( int i=0; i<sortedEdges.length-1; i++ )
    {
      if ( sortedEdges[i].equals(sortedEdges[i+1]) )
      {
        return true;
      }
    }
    return false;
  }

  static void scaleTo(Vector nodes, Vector edges, Rectangle2D.Double oldBounds,
                      Rectangle2D.Double newBounds, MementoGrouper currentMemento,
                      boolean trackUndos, boolean createMemento)
  {
    boolean memento = currentMemento != null && trackUndos && createMemento;
    double xFactor = newBounds.getWidth() / oldBounds.getWidth();
    double yFactor = newBounds.getHeight() / oldBounds.getHeight();

    for ( int i=0; i<nodes.size(); i++ )
    {
      Node currentNode = (Node)nodes.elementAt(i);
      if ( memento )
      {
        currentMemento.addMemento(NodeMovementMemento.createMoveMemento(currentNode));
      }
      currentNode.scaleBy(oldBounds.getMinX(), oldBounds.getMinY(), xFactor, yFactor);
    }
    for ( int i=0; i<edges.size(); i++ )
    {
      Edge anEdge = (Edge)edges.elementAt(i);
      if ( memento )
      {
        currentMemento.addMemento(EdgeMovementMemento.createMoveMemento(anEdge));
      }
      anEdge.scaleBy(oldBounds.getMinX(), oldBounds.getMinY(), xFactor, yFactor);
    }
  }
}
