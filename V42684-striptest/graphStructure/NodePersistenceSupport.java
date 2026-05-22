package graphStructure;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;

final class NodePersistenceSupport
{
  private NodePersistenceSupport() {}

  static void saveTo(Node node, PrintWriter aFile)
  {
    aFile.println(node.getIndex());
    aFile.println(node.getLocation().doubleX());
    aFile.println(node.getLocation().doubleY());
    aFile.println(node.getLabel());
    aFile.println(node.getColor().getRGB());
    Vector incidentEdges = node.incidentEdges();
    for ( int i=0; i<incidentEdges.size(); i++ )
    {
      aFile.print(((Edge)incidentEdges.elementAt(i)).getIndex());
      if ( i < incidentEdges.size() - 1 )
      {
        aFile.print(",");
      }
    }
    aFile.println();
  }

  static Node loadFrom(BufferedReader aFile, Vector edgeIndices) throws IOException
  {
    Node aNode = new Node();
    aNode.setIndex(Integer.valueOf(aFile.readLine()).intValue());
    aNode.setLocation(new Location(
        Double.valueOf(aFile.readLine()).doubleValue(),
        Double.valueOf(aFile.readLine()).doubleValue()));
    aNode.setLabel(aFile.readLine());
    aNode.setColor(new Color(Integer.valueOf(aFile.readLine()).intValue()));
    String edgeIndexString = aFile.readLine();
    StringTokenizer st = new StringTokenizer(edgeIndexString, ",");
    Vector indices = new Vector();
    while ( st.hasMoreTokens() )
    {
      indices.addElement(Integer.valueOf(st.nextToken()));
    }
    edgeIndices.addElement(indices);
    return aNode;
  }
}
