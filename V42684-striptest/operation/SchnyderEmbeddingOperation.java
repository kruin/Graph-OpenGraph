package operation;

import java.util.Vector;
import graphStructure.*;
import graphException.*;
import operation.extenders.*;

public class SchnyderEmbeddingOperation
{
  public static void straightLineGridEmbed(Graph g, int width,
                                           int height) throws Exception
  {
    straightLineGridEmbed(g, true, width, height);
  }

  public static void straightLineGridEmbed( Graph g, boolean check,
                                            int width,
                                            int height ) throws Exception
  {
    LogEntry logEntry = g.startLogEntry("Schnyder Straight Line Grid Embedding");
    if ( check && g.getNumNodes() < 3 )
    {
      logEntry.setData("Graph had less than 3 Nodes");
      g.stopLogEntry(logEntry);
      throw new GraphException("3 or more nodes required!");
    }
    else if ( check && !PlanarityOperation.isPlanar(g) )
    {
      logEntry.setData("Graph was not Planar");
      g.stopLogEntry(logEntry);
      throw new GraphException("Graph is not planar!");
    }
    else
    {
      straightLineGridEmbed(g, NormalLabelOperation.normalLabel(g, false),
                            width, height, logEntry);
    }
  }

  public static void straightLineGridEmbed( Graph g, Node fNode, Node sNode,
                                            Node tNode, int width,
                                            int height ) throws Exception
  {
    LogEntry logEntry = g.startLogEntry("Schnyder Straight Line Grid Embedding");
    straightLineGridEmbed(g, NormalLabelOperation.normalLabel(g, fNode,
                                                              sNode, tNode),
                          width, height, logEntry);
  }

  private static void straightLineGridEmbed( Graph g, Vector rootNodes,
                                             int width, int height,
                                             LogEntry logEntry ) throws Exception
  {
    SchnyderEmbeddingSupport.embedFromRootNodes(g, rootNodes, width, height);
    g.stopLogEntry(logEntry);
  }

  public static void displayStraightLineGridEmbedding(Graph g, Node fNode,
                                                      Node sNode, Node tNode,
                                                      int width,
                                                      int height) throws Exception
  {
    straightLineGridEmbed(g, fNode, sNode, tNode, width, height);
    g.markForRepaint();
  }

  public static void displayStraightLineGridEmbedding(Graph g, int width,
                                                      int height) throws Exception
  {
    straightLineGridEmbed(g, width, height);
    g.markForRepaint();
  }

  public static void displayNormalLabeling(Graph g, Node fNode, Node sNode,
                                           Node tNode, int width,
                                           int height) throws Exception
  {
    displayStraightLineGridEmbedding(g, fNode, sNode, tNode, width, height);
    displayNormalLabeling(g);
  }

  public static void displayNormalLabeling(Graph g, int width,
                                           int height) throws Exception
  {
    displayStraightLineGridEmbedding(g, width, height);
    displayNormalLabeling(g);
  }

  private static void displayNormalLabeling(Graph g) throws Exception
  {
    SchnyderEmbeddingSupport.displayNormalLabeling(g);
  }

  public static void displayCanonicalOrdering(Graph g, Node fNode, Node sNode,
                                              Node tNode, int width,
                                              int height) throws Exception
  {
    displayStraightLineGridEmbedding(g, fNode, sNode, tNode, width,
                                     height);
    displayCanonicalOrdering(g);
  }

  public static void displayCanonicalOrdering(Graph g, int width,
                                              int height) throws Exception
  {
    displayStraightLineGridEmbedding(g, width, height);
    displayCanonicalOrdering(g);
  }

  private static void displayCanonicalOrdering(Graph g) throws Exception
  {
    SchnyderEmbeddingSupport.displayCanonicalOrdering(g);
  }
}
