package graphStructure;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

final class EdgePersistenceSupport
{
  private EdgePersistenceSupport() {}

  static void saveTo(Edge edge, PrintWriter aFile)
  {
    aFile.println(edge.getIndex());
    aFile.println(((Node)edge.getStartNode()).getIndex());
    aFile.println(((Node)edge.getEndNode()).getIndex());
    if ( edge.isDirected() )
    {
      aFile.println(edge.getDirectedSourceNode().getIndex());
    }
    else
    {
      aFile.println("-1");
    }
    aFile.println(edge.getCenterLocation().doubleX());
    aFile.println(edge.getCenterLocation().doubleY());
    aFile.println(edge.isCurved());
    aFile.println(edge.isOrthogonal());
    aFile.println(edge.isGenerated());
    aFile.println(edge.getColor().getRGB());
  }

  static Edge loadFrom(BufferedReader aFile, Vector nodeVector) throws IOException
  {
    int index = Integer.valueOf(aFile.readLine()).intValue();
    int startIndex = Integer.valueOf(aFile.readLine()).intValue();
    int endIndex = Integer.valueOf(aFile.readLine()).intValue();
    Edge anEdge = new Edge((Node)nodeVector.elementAt(startIndex - 1),
                           (Node)nodeVector.elementAt(endIndex - 1));
    anEdge.setIndex(index);
    int directedSourceIndex = Integer.valueOf(aFile.readLine()).intValue();
    if ( directedSourceIndex != -1 )
    {
      if ( directedSourceIndex == startIndex )
      {
        anEdge.setDirectedFrom(anEdge.getStartNode());
      }
      else if ( directedSourceIndex == endIndex )
      {
        anEdge.setDirectedFrom(anEdge.getEndNode());
      }
      else
      {
        throw new IOException("Direction source was not an end node of an edge");
      }
    }

    anEdge.setCenterLocation(new Location(
        Double.valueOf(aFile.readLine()).doubleValue(),
        Double.valueOf(aFile.readLine()).doubleValue()));
    anEdge.setIsCurved(Boolean.valueOf(aFile.readLine()).booleanValue());
    anEdge.setIsOrthogonal(Boolean.valueOf(aFile.readLine()).booleanValue());
    if ( anEdge.isCurved() )
    {
      anEdge.initCurveAngles();
    }
    anEdge.setIsGenerated(Boolean.valueOf(aFile.readLine()).booleanValue());
    anEdge.setColor(new Color(Integer.valueOf(aFile.readLine()).intValue()));
    return anEdge;
  }
}
