package operation;

import java.util.Vector;

import graphStructure.Graph;
import graphStructure.LogEntry;
import graphStructure.Node;
import operation.extenders.ChanEdgeEx;
import operation.extenders.ChanNodeEx;

public class ChanTreeDrawOperation
{
  public static void displayChanTreeDrawing(Graph g, Node root, int method,
                                            int width, int height) throws Exception
  {
    LogEntry logEntry = g.startLogEntry("Chan Tree Drawing");
    ChanTreeDrawSupport.validateGraphForDisplay(g, root, method, logEntry);

    Vector nodes = g.createNodeExtenders(ChanNodeEx.class);
    Vector edges = g.createEdgeExtenders(ChanEdgeEx.class);
    ChanNodeEx rootEx = (ChanNodeEx)root.getExtender();
    ChanTreeDrawSupport.buildTree(rootEx);

    if ( method != 1 && method != 2 && method != 3 )
    {
      return;
    }

    ChanTreeDrawSupport.applyDrawingMethod(g, rootEx, method);
    ChanTreeDrawSupport.applyGraphLayout(g, rootEx, nodes, edges, width, height);
    g.stopLogEntry(logEntry);
  }
}
