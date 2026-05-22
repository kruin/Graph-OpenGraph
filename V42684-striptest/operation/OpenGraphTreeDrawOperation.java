package operation;

import graphException.*;
import graphStructure.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import operation.extenders.*;

public class OpenGraphTreeDrawOperation
{
  private static final int DEFAULT_DRAWING_OFFSET_X = 4;
  private static final int DEFAULT_DRAWING_OFFSET_Y = 4;
  private static final int DEFAULT_CELL_WIDTH = 20;
  private static final int DEFAULT_CELL_HEIGHT = 20;

  /**
   * Keep one empty grid column between the root axis and each child box.
   * This preserves the open-tree character while keeping the reserved box minimal.
   */
  private static final int ROOT_SIDE_GAP = 1;
  private static final int LANGUAGE_TREE_TOP_SLOT_ROWS = 1;
  private static final String LAYOUT_FREE_D_LITE = "free_d_lite";
  private static final String LAYOUT_NARY_COMPACT_LR = "nary_compact_lr";
  private static final String LAYOUT_PROJECTION_BOX = "projection_box";
  private static final String LAYOUT_ROLE_BOX = "role_box";
  private static final int SIDE_LEFT = -1;
  private static final int SIDE_NEUTRAL = 0;
  private static final int SIDE_RIGHT = 1;

  /* v4.25.6: Functional Tree role-box configuration is read lazily from
   * config/opengraph_defaults.properties, then config/opengraph_user.properties,
   * then config/opengraphed_user.properties.  Later files override earlier ones.
   * This keeps LT untouched and makes FT role ranks/sides testable without GUI.
   */
  private static Properties cachedRoleBoxProperties = null;

  /* v4.27.5: transient FT proposals generated from a purely syntactic tree.
   * These are not persisted.  Node menu choices remain authoritative; proposals
   * are used only when the user has not explicitly configured a node yet.
   */
  private static Hashtable functionalTreeRoleProposals = new Hashtable();
  private static Hashtable functionalTreeNodeTypeProposals = new Hashtable();


  public static synchronized void resetFunctionalTreeConfigCache()
  {
    cachedRoleBoxProperties = null;
  }

  public static void displayOpenGraphTreeDrawing(Graph g, Node root, int method,
                                            int width, int height) throws Exception
  {
    displayOpenGraphTreeDrawing(g, root, method,
                            DEFAULT_DRAWING_OFFSET_X,
                            DEFAULT_DRAWING_OFFSET_Y,
                            DEFAULT_CELL_WIDTH, DEFAULT_CELL_HEIGHT,
                            width, height, false);
  }


  /**
   * Compatibility overload for older callers. Right and bottom structure offsets
   * are intentionally ignored: tree positioning is anchored by left/top only.
   */
  public static void displayOpenGraphTreeDrawing(Graph g, Node root, int method,
                                             int leftOffset, int rightOffset,
                                             int topOffset, int bottomOffset,
                                             int cellWidth, int cellHeight,
                                             int width, int height) throws Exception
  {
    displayOpenGraphTreeDrawing(g, root, method,
                            leftOffset, topOffset,
                            cellWidth, cellHeight,
                            width, height, false);
  }
  public static void displayOpenGraphTreeDrawing(Graph g, Node root, int method,
                                             int leftOffset, int topOffset,
                                             int cellWidth, int cellHeight,
                                             int width, int height) throws Exception
  {
    displayOpenGraphTreeDrawing(g, root, method, leftOffset, topOffset,
                                cellWidth, cellHeight, width, height, false);
  }

  public static void displayOpenGraphTreeDrawing(Graph g, Node root, int method,
                                             int leftOffset, int topOffset,
                                             int cellWidth, int cellHeight,
                                             int width, int height,
                                             boolean reserveLanguageSlots) throws Exception
  {
    displayOpenGraphTreeDrawing(g, root, method, leftOffset, topOffset,
                                cellWidth, cellHeight, width, height,
                                reserveLanguageSlots, false);
  }

  public static void displayOpenGraphTreeDrawing(Graph g, Node root, int method,
                                             int leftOffset, int topOffset,
                                             int cellWidth, int cellHeight,
                                             int width, int height,
                                             boolean reserveLanguageSlots,
                                             boolean lengthenLanguageTreeRootBranch) throws Exception
  {
    displayOpenGraphTreeDrawing(g, root, method, leftOffset, topOffset,
                                cellWidth, cellHeight, width, height,
                                reserveLanguageSlots, lengthenLanguageTreeRootBranch,
                                "pv_vd");
  }

  public static void displayOpenGraphTreeDrawing(Graph g, Node root, int method,
                                             int leftOffset, int topOffset,
                                             int cellWidth, int cellHeight,
                                             int width, int height,
                                             boolean reserveLanguageSlots,
                                             boolean lengthenLanguageTreeRootBranch,
                                             String languageTreeVerbClusterOrder) throws Exception
  {
    displayOpenGraphTreeDrawing(g, root, method, leftOffset, topOffset,
                                cellWidth, cellHeight, width, height,
                                reserveLanguageSlots, lengthenLanguageTreeRootBranch,
                                languageTreeVerbClusterOrder, LAYOUT_FREE_D_LITE);
  }

  public static void displayOpenGraphTreeDrawing(Graph g, Node root, int method,
                                             int leftOffset, int topOffset,
                                             int cellWidth, int cellHeight,
                                             int width, int height,
                                             boolean reserveLanguageSlots,
                                             boolean lengthenLanguageTreeRootBranch,
                                             String languageTreeVerbClusterOrder,
                                             String languageTreeLayoutStrategy) throws Exception
  {
    boolean oldSpecialSelected = root.isSpecialSelected();
    root.setSpecialSelected(true);
    LogEntry logEntry = g.startLogEntry("OpenGraph Tree Drawing");
    try
    {
      if ( !ConnectivityOperation.isConnected(g) )
      {
        logEntry.setData("Graph was not connected");
        g.stopLogEntry(logEntry);
        throw new GraphException("Graph is not connected!");
      }
      else if ( TreeOperation.hasCycles(g) )
      {
        logEntry.setData("Graph had cycles");
        g.stopLogEntry(logEntry);
        throw new GraphException("Graph has Cycles!");
      }
      else
      {
        Vector nodes = g.createNodeExtenders(OpenGraphNodeEx.class);
        Vector edges = g.createEdgeExtenders(OpenGraphEdgeEx.class);
        OpenGraphNodeEx rootEx = (OpenGraphNodeEx)root.getExtender();
        rootEx.setParent(null);

        buildTree(rootEx);

        String normalizedLayoutStrategy = normalizeLanguageTreeLayoutStrategy(languageTreeLayoutStrategy);
        buildFunctionalTreeRoleProposals(rootEx, normalizedLayoutStrategy);
        String roleDiagnostics = roleDiagnosticsForTree(rootEx, normalizedLayoutStrategy);

        if ( method == 1 )
        {
          domainTreeMethod(rootEx, languageTreeVerbClusterOrder,
                           normalizedLayoutStrategy,
                           lengthenLanguageTreeRootBranch);
          if ( lengthenLanguageTreeRootBranch )
          {
            /*
             * Kept for legacy callers, but the main UI no longer requests this
             * for LT.  v4.28.0 aligns LT root branching with the FT variant:
             * no extra global one-row drop after the recursive box layout.
             */
            lengthenFirstRootBranch(rootEx);
          }
        }
        else if ( method == 5 )
        {
          logEntry.setData("Method 5 is not implemented reliably");
          g.stopLogEntry(logEntry);
          throw new GraphException("OpenGraph method 5 is not implemented reliably. Use the Simple open-tree renderer.");
        }
        else
        {
          logEntry.setData("Unsupported OpenGraph method: " + method);
          g.stopLogEntry(logEntry);
          throw new GraphException("Unsupported OpenGraph drawing method: " + method);
        }

        int gridWidth = rootEx.getBoundWidth();
        int gridHeight = rootEx.getBoundHeight();

        leftOffset = Math.max(0, leftOffset);
        topOffset = Math.max(0, topOffset);
        if ( reserveLanguageSlots )
        {
          topOffset += LANGUAGE_TREE_TOP_SLOT_ROWS;
        }
        cellWidth = Math.max(2, cellWidth);
        cellHeight = Math.max(2, cellHeight);

        correctGridCoordinates(rootEx, leftOffset + rootEx.getBoundX(), topOffset);

        int totalGridCols = gridWidth + leftOffset + 1;
        int totalGridRows = gridHeight + topOffset + 1;

        g.setGrid(totalGridRows, cellHeight,
                  totalGridCols, cellWidth, true);

        OpenGraphNodeEx aNode;
        OpenGraphEdgeEx anEdge;
        for ( int i=0; i<nodes.size(); i++ )
        {
          aNode = (OpenGraphNodeEx)nodes.elementAt(i);
          g.relocateNode(aNode.getRef(),
                         new Location(aNode.getGridX()*cellWidth,
                                      aNode.getGridY()*cellHeight),
                         true);
        }

        for ( int i=0; i<edges.size(); i++ )
        {
          anEdge = (OpenGraphEdgeEx)edges.elementAt(i);
          g.straightenEdge(anEdge.getRef(), true);
        }

        if ( roleDiagnostics.length() > 0 )
        {
          logEntry.setData(roleDiagnostics);
          System.out.println(roleDiagnostics);
        }
        else
        {
          logEntry.setData("layoutStrategy=" + normalizedLayoutStrategy + "; roleDiagnostics=no known FG roles");
        }

        g.stopLogEntry(logEntry);
      }
    }
    finally
    {
      root.setSpecialSelected(oldSpecialSelected);
    }
  }

  private static int buildTree(OpenGraphNodeEx root)
  {
    Vector children = root.getChildren();
    OpenGraphNodeEx child;
    int size = 1;
    for ( int i=0; i<children.size(); i++ )
    {
      child = (OpenGraphNodeEx)children.elementAt(i);
      if ( child != root.getParent() )
      {
        child.setParent(root);
        size += buildTree(child);
      }
    }
    root.setSubTreeSize(size);
    root.setSubTreeDone(false);
    return size;
  }

  private static void domainTreeMethod(OpenGraphNodeEx root, String languageTreeVerbClusterOrder,
                                       String languageTreeLayoutStrategy,
                                       boolean freeLanguageLayout)
  {
    domainTreeMethod(root, languageTreeVerbClusterOrder, languageTreeLayoutStrategy,
                     freeLanguageLayout, SIDE_NEUTRAL);
  }

  /**
   * Recursive Language Tree/OpenGraph layout.
   *
   * v4.24.5 correction: binary and n-ary vertical stacking is now based on
   * actual subtree boxes, not cached boundHeight values.  A second subtree
   * root is placed below the real bottom of the previous subtree box, so a
   * terminal such as man cannot share a row with the following V node.
   * Unary descendants and true n-ary children remain part of the subtree box.
   */
  private static void domainTreeMethod(OpenGraphNodeEx root, String languageTreeVerbClusterOrder,
                                       String languageTreeLayoutStrategy,
                                       boolean freeLanguageLayout,
                                       int sidePreference)
  {
    Vector children = root.getChildren();

    if ( children.size() == 0 )
    {
      initializeLeaf(root);
      return;
    }

    if ( children.size() == 1 )
    {
      OpenGraphNodeEx child = (OpenGraphNodeEx)children.elementAt(0);
      int childSide = freeLanguageLayout ? preferredUnaryDirection(root, child, sidePreference)
                                         : SIDE_NEUTRAL;
      domainTreeMethod(child, languageTreeVerbClusterOrder,
                       languageTreeLayoutStrategy, freeLanguageLayout, childSide);

      if ( freeLanguageLayout )
      {
        freeUnaryRule(root, child, childSide);
      }
      else
      {
        otherRule(root, child);
      }
      return;
    }

    if ( children.size() == 2 )
    {
      OpenGraphNodeEx leftChild = (OpenGraphNodeEx)children.elementAt(0);
      OpenGraphNodeEx rightChild = (OpenGraphNodeEx)children.elementAt(1);
      int structuralFirstSide = SIDE_LEFT;

      if ( isVerbClusterNode(root) )
      {
        OpenGraphNodeEx[] ordered = orderVerbClusterChildren(leftChild, rightChild, languageTreeVerbClusterOrder);
        if ( ordered != null )
        {
          /*
           * V-cluster order is visual/structural order: pv-VD means pv left,
           * VD right.  The surrounding right/left context may enlarge the
           * reserved box later, but must not flip the cluster into VD-pv.
           */
          domainTreeMethod(leftChild, languageTreeVerbClusterOrder,
                           languageTreeLayoutStrategy, freeLanguageLayout,
                           childExpansionSideForOrderedBinary(leftChild, ordered[0], ordered[1]));
          domainTreeMethod(rightChild, languageTreeVerbClusterOrder,
                           languageTreeLayoutStrategy, freeLanguageLayout,
                           childExpansionSideForOrderedBinary(rightChild, ordered[0], ordered[1]));
          verbClusterRule(root, ordered[0], ordered[1], leftChild, rightChild, structuralFirstSide);
          return;
        }
      }

      if ( !(root.getNode().isSelected()) )
      {
        domainTreeMethod(leftChild, languageTreeVerbClusterOrder,
                         languageTreeLayoutStrategy, freeLanguageLayout, SIDE_LEFT);
        domainTreeMethod(rightChild, languageTreeVerbClusterOrder,
                         languageTreeLayoutStrategy, freeLanguageLayout, SIDE_RIGHT);

        if ( freeLanguageLayout )
        {
          freeBinaryRule(root, leftChild, rightChild, structuralFirstSide);
        }
        else
        {
          domainRule(root, leftChild, rightChild);
        }
      }
      else
      {
        /*
         * Do not mutate the actual node label during rendering.
         * Focus still affects the binary subtree ordering, but the visual
         * label is left untouched.
         */
        domainTreeMethod(rightChild, languageTreeVerbClusterOrder,
                         languageTreeLayoutStrategy, freeLanguageLayout, SIDE_LEFT);
        domainTreeMethod(leftChild, languageTreeVerbClusterOrder,
                         languageTreeLayoutStrategy, freeLanguageLayout, SIDE_RIGHT);

        if ( freeLanguageLayout )
        {
          freeBinaryRule(root, rightChild, leftChild, structuralFirstSide);
        }
        else
        {
          domainRule(root, rightChild, leftChild);
        }
      }
      return;
    }

    /*
     * v4.25.1: projection-order first, local box first.
     *
     * The n-ary combiner is not allowed to invent a new phrase order.  It
     * receives the children in projection order first (LEX/source order for
     * terminals, SYN structural order for category nodes, and later ROLE order
     * for Functional Grammar).  Corridor placement is then only geometry.
     *
     * Local shape is also isolated from the inherited sidePreference: the
     * complete n-ary subtree is built with the same internal L/R pattern in its
     * own coordinate system.  Higher VP/S context may shift the whole subtree
     * box afterwards, but it must not deform the internal NP/DP/ROLE shape.
     */
    Vector layoutChildren = orderNaryChildrenByProjection(root, children, languageTreeLayoutStrategy);

    int firstSide = firstSideForNaryProjectionBox(root, sidePreference, languageTreeLayoutStrategy);
    for ( int i=0; i<layoutChildren.size(); i++ )
    {
      int childSide = (i % 2) == 0 ? firstSide : -firstSide;
      domainTreeMethod((OpenGraphNodeEx)layoutChildren.elementAt(i), languageTreeVerbClusterOrder,
                       languageTreeLayoutStrategy, freeLanguageLayout, childSide);
    }

    /*
     * v4.25.8: Functional Tree must use the dedicated role-box combiner
     * regardless of the freeLanguageLayout flag.  In v4.25.7 FT could fall
     * through to the generic list n-ary rule, producing a horizontal sequence
     * (pred, agens, patiens, instrument) instead of the configured role sides.
     * LT still only uses compact/projection combiner when freeLanguageLayout
     * is active.
     */
    if ( isRoleBoxLayout(languageTreeLayoutStrategy) )
    {
      nAryRoleBoxRule(root, layoutChildren);
    }
    else if ( freeLanguageLayout && isNaryCompactLR(languageTreeLayoutStrategy) )
    {
      nAryCompactLRRule(root, layoutChildren, firstSide, languageTreeLayoutStrategy);
    }
    else
    {
      nAryRule(root, layoutChildren);
    }
  }

  private static int firstSideFor(int sidePreference)
  {
    return sidePreference == SIDE_RIGHT ? SIDE_RIGHT : SIDE_LEFT;
  }

  private static int firstSideForNaryProjectionBox(OpenGraphNodeEx root,
                                                   int sidePreference,
                                                   String strategy)
  {
    if ( isProjectionBoxLayout(strategy) || isNaryCompactLR(strategy) )
    {
      /*
       * Projectie eerst, box daarna: a right-context parent must not make an
       * NP/DP/ROLE n-ary fan-out choose a different internal start side.
       * The parent combiner can still move the complete resulting subtree box.
       */
      return SIDE_LEFT;
    }
    return firstSideFor(sidePreference);
  }

  private static Vector orderNaryChildrenByProjection(OpenGraphNodeEx root, Vector children, String strategy)
  {
    Vector ordered = new Vector();
    if ( children == null )
    {
      return ordered;
    }
    for ( int i=0; i<children.size(); i++ )
    {
      ordered.addElement(children.elementAt(i));
    }

    if ( isRoleBoxLayout(strategy) )
    {
      stableSortNaryChildrenByRoleRank(ordered);
      return ordered;
    }

    if ( isNominalProjectionNode(root) && allTerminalNodes(ordered) )
    {
      for ( int i=1; i<ordered.size(); i++ )
      {
        Object value = ordered.elementAt(i);
        int valueRank = nominalTerminalRank((OpenGraphNodeEx)value);
        int j = i - 1;
        while ( j >= 0 && nominalTerminalRank((OpenGraphNodeEx)ordered.elementAt(j)) > valueRank )
        {
          ordered.setElementAt(ordered.elementAt(j), j + 1);
          j--;
        }
        ordered.setElementAt(value, j + 1);
      }
    }
    return ordered;
  }


  /**
   * v4.25.3 diagnostics: identify Functional Grammar role labels without
   * changing the default projection_box layout.  Output is written to the
   * operation log and to the console only when known FG roles are present.
   */
  private static String roleDiagnosticsForTree(OpenGraphNodeEx root, String strategy)
  {
    RoleDiagnostics d = new RoleDiagnostics(strategy);
    collectRoleDiagnostics(root, d);
    return d.toLogString();
  }

  private static void collectRoleDiagnostics(OpenGraphNodeEx node, RoleDiagnostics d)
  {
    if ( node == null ) return;
    LayoutRole role = layoutRoleFor(node);
    if ( isKnownLayoutRoleName(role.getName()) )
    {
      d.add(role, cleanLabel(node.getNode() == null ? null : node.getNode().getLabel()));
    }

    Vector children = node.getChildren();
    for ( int i=0; i<children.size(); i++ )
    {
      collectRoleDiagnostics((OpenGraphNodeEx)children.elementAt(i), d);
    }
  }

  private static boolean isKnownLayoutRoleName(String role)
  {
    if ( role == null ) return false;
    String r = canonicalRoleName(role.trim().toLowerCase());
    return r.equals("pred") || r.equals("agens") || r.equals("patiens") ||
           r.equals("recipiens") || r.equals("instrument") ||
           r.equals("locatief") || r.equals("tijd");
  }

  private static String preferredSideName(int side)
  {
    if ( side == PreferredSide.LEFT ) return "left";
    if ( side == PreferredSide.RIGHT ) return "right";
    if ( side == PreferredSide.DOWN ) return "down";
    if ( side == PreferredSide.CENTER ) return "center";
    return "auto";
  }

  private static String corridorPolicyName(int policy)
  {
    if ( policy == CorridorPolicy.OUTER ) return "outer";
    if ( policy == CorridorPolicy.INNER ) return "inner";
    if ( policy == CorridorPolicy.STACK ) return "stack";
    return "auto";
  }

  private static final class RoleDiagnostics
  {
    private final String strategy;
    private final Vector entries = new Vector();
    private int knownCount = 0;

    RoleDiagnostics(String strategy)
    {
      this.strategy = strategy == null ? "" : strategy;
    }

    void add(LayoutRole role, String label)
    {
      knownCount++;
      entries.addElement(label + " -> " + role.getName() +
                         " rank=" + role.getRank() +
                         " side=" + preferredSideName(role.getPreferredSide()) +
                         " corridor=" + corridorPolicyName(role.getCorridorPolicy()));
    }

    String toLogString()
    {
      if ( knownCount == 0 ) return "";
      StringBuffer sb = new StringBuffer();
      sb.append("layoutStrategy=").append(strategy);
      sb.append("; roleDiagnostics=known FG roles: ").append(knownCount);
      sb.append("; entries=");
      for ( int i=0; i<entries.size(); i++ )
      {
        if ( i > 0 ) sb.append(" | ");
        sb.append(entries.elementAt(i));
      }
      return sb.toString();
    }
  }

  /**
   * v4.25.2 preparation: Functional Grammar role-box ordering.
   * In role_box mode, n-ary children are sorted by semantic/function role rank.
   * This is not active in the default GUI yet; projection_box remains active.
   */
  private static void stableSortNaryChildrenByRoleRank(Vector ordered)
  {
    for ( int i=1; i<ordered.size(); i++ )
    {
      Object value = ordered.elementAt(i);
      int valueRank = layoutRoleFor((OpenGraphNodeEx)value).getRank();
      int j = i - 1;
      while ( j >= 0 && layoutRoleFor((OpenGraphNodeEx)ordered.elementAt(j)).getRank() > valueRank )
      {
        ordered.setElementAt(ordered.elementAt(j), j + 1);
        j--;
      }
      ordered.setElementAt(value, j + 1);
    }
  }


  private static void buildFunctionalTreeRoleProposals(OpenGraphNodeEx root, String strategy)
  {
    functionalTreeRoleProposals = new Hashtable();
    functionalTreeNodeTypeProposals = new Hashtable();
    if ( root == null ) return;
    if ( !functionalSyntaxProposalEnabled() ) return;
    if ( !LAYOUT_ROLE_BOX.equals(strategy) && !looksLikeFunctionalSyntaxRoot(root) ) return;

    proposeFunctionalRolesRec(root, null);
  }

  private static boolean functionalSyntaxProposalEnabled()
  {
    Properties props = roleBoxProperties();
    String value = props.getProperty("functional.layout.syntaxProposal.enabled", "true");
    value = value == null ? "" : value.trim().toLowerCase();
    return value.equals("true") || value.equals("yes") || value.equals("ja") || value.equals("1") || value.equals("on");
  }

  private static boolean looksLikeFunctionalSyntaxRoot(OpenGraphNodeEx root)
  {
    String label = cleanLabel(root == null || root.getNode() == null ? null : root.getNode().getLabel()).toUpperCase();
    return label.equals("S") || label.equals("CLAUSE") || label.equals("V");
  }

  private static void proposeFunctionalRolesRec(OpenGraphNodeEx node, OpenGraphNodeEx parent)
  {
    if ( node == null || node.getNode() == null ) return;

    String nodeLabel = cleanLabel(node.getNode().getLabel()).toUpperCase();
    Vector children = node.getChildren();

    if ( parent == null && (nodeLabel.equals("S") || nodeLabel.equals("CLAUSE") || nodeLabel.equals("V")) )
    {
      putFunctionalTreeProposal(node, "top", defaultProcessTypeForTree(node), "pred");
    }

    if ( children == null || children.size() == 0 ) return;

    boolean parentIsSentence = nodeLabel.equals("S") || nodeLabel.equals("CLAUSE");
    boolean parentIsVerbGroup = nodeLabel.equals("VP") || nodeLabel.equals("V") || nodeLabel.equals("PRED") || nodeLabel.equals("PROCESS");
    boolean foundSubject = false;
    boolean foundPatient = false;

    for ( int i=0; i<children.size(); i++ )
    {
      OpenGraphNodeEx child = (OpenGraphNodeEx)children.elementAt(i);
      String childLabel = cleanLabel(child == null || child.getNode() == null ? null : child.getNode().getLabel()).toUpperCase();
      String role = "";
      String nodeType = "participant";

      if ( isVerbLikeNode(childLabel) )
      {
        role = "pred";
        nodeType = "process";
      }
      else if ( isNominalLikeNode(childLabel) )
      {
        if ( parentIsSentence && !foundSubject )
        {
          role = "agens";
          foundSubject = true;
        }
        else if ( parentIsVerbGroup && !foundPatient )
        {
          role = "patiens";
          foundPatient = true;
        }
        else if ( !foundPatient )
        {
          role = "patiens";
          foundPatient = true;
        }
        else
        {
          role = "recipiens";
        }
      }
      else if ( childLabel.equals("PP") || childLabel.equals("P") )
      {
        role = proposeFrameRoleFromLexicalContent(child);
        nodeType = "frame";
      }
      else if ( childLabel.equals("ADV") || childLabel.equals("ADVP") )
      {
        role = "tijd";
        nodeType = "frame";
      }
      else if ( childLabel.equals("AP") || childLabel.equals("ADJP") )
      {
        role = "eigenschap";
        nodeType = "participant";
      }
      else if ( parentIsSentence && i == 0 )
      {
        role = "agens";
      }
      else if ( parentIsVerbGroup )
      {
        role = "patiens";
      }

      if ( role.length() > 0 && child != null && child.getNode() != null && !child.getNode().hasFunctionalTreeMetadata() )
      {
        putFunctionalTreeProposal(child, nodeType, "", role);
      }

      proposeFunctionalRolesRec(child, node);
    }
  }

  private static boolean isVerbLikeNode(String label)
  {
    return label.equals("VP") || label.equals("V") || label.equals("PRED") || label.equals("PROCESS") || label.equals("PROCES");
  }

  private static boolean isNominalLikeNode(String label)
  {
    return label.equals("NP") || label.equals("DP") || label.equals("N") || label.equals("PRON") || label.equals("PRO") || label.equals("GN");
  }

  private static String defaultProcessTypeForTree(OpenGraphNodeEx root)
  {
    String all = lexicalYield(root).toLowerCase();
    if ( all.indexOf("gebeur") >= 0 || all.indexOf("verschijn") >= 0 || all.indexOf("event") >= 0 ) return "event";
    return "action";
  }

  private static String proposeFrameRoleFromLexicalContent(OpenGraphNodeEx node)
  {
    String y = lexicalYield(node).toLowerCase();
    if ( containsAny(y, new String[] { "in ", "op ", "onder ", "boven ", "naast ", "bij ", "achter ", "voor ", "naar ", "uit ", "van " }) ) return "locatief";
    if ( containsAny(y, new String[] { "met ", "door middel", "middels" }) ) return "instrument";
    if ( containsAny(y, new String[] { "omdat", "door ", "wegens" }) ) return "oorzaak";
    return "locatief";
  }

  private static boolean containsAny(String value, String[] needles)
  {
    if ( value == null ) return false;
    for ( int i=0; i<needles.length; i++ )
    {
      if ( value.indexOf(needles[i]) >= 0 ) return true;
    }
    return false;
  }

  private static String lexicalYield(OpenGraphNodeEx node)
  {
    if ( node == null || node.getNode() == null ) return "";
    Vector children = node.getChildren();
    if ( children == null || children.size() == 0 ) return cleanLabel(node.getNode().getLabel());
    StringBuffer sb = new StringBuffer();
    for ( int i=0; i<children.size(); i++ )
    {
      String part = lexicalYield((OpenGraphNodeEx)children.elementAt(i));
      if ( part.length() > 0 )
      {
        if ( sb.length() > 0 ) sb.append(' ');
        sb.append(part);
      }
    }
    return sb.toString();
  }

  private static void putFunctionalTreeProposal(OpenGraphNodeEx node, String type, String process, String role)
  {
    if ( node == null || node.getNode() == null ) return;
    if ( role != null && role.length() > 0 ) functionalTreeRoleProposals.put(node.getNode(), role);
    if ( type != null && type.length() > 0 ) functionalTreeNodeTypeProposals.put(node.getNode(), type + "/" + (process == null ? "" : process));
  }

  private static String functionalTreeProposalFor(graphStructure.Node node)
  {
    if ( node == null || functionalTreeRoleProposals == null ) return "";
    Object value = functionalTreeRoleProposals.get(node);
    return value == null ? "" : value.toString();
  }

  private static LayoutRole layoutRoleFor(OpenGraphNodeEx node)
  {
    String label = cleanLabel(node == null || node.getNode() == null ? null : node.getNode().getLabel());
    String role = canonicalRoleName(extractRoleName(label));
    if ( node != null && node.getNode() != null )
    {
      if ( node.getNode().hasFunctionalTreeMetadata() )
      {
        role = canonicalRoleName(extractFunctionalRoleFromNodeMetadata(node.getNode(), role));
      }
      else
      {
        String proposal = functionalTreeProposalFor(node.getNode());
        if ( proposal.length() > 0 ) role = canonicalRoleName(proposal);
      }
    }

    int defaultRank = 1000;
    int defaultSide = PreferredSide.CENTER;
    int defaultCorridor = CorridorPolicy.AUTO;

    if ( role.equals("pred") )
    {
      defaultRank = 0;
      defaultSide = PreferredSide.CENTER;
      defaultCorridor = CorridorPolicy.AUTO;
    }
    else if ( role.equals("agens") )
    {
      defaultRank = 10;
      defaultSide = PreferredSide.LEFT;
      defaultCorridor = CorridorPolicy.OUTER;
    }
    else if ( role.equals("patiens") )
    {
      defaultRank = 20;
      defaultSide = PreferredSide.RIGHT;
      defaultCorridor = CorridorPolicy.OUTER;
    }
    else if ( role.equals("recipiens") )
    {
      defaultRank = 30;
      defaultSide = PreferredSide.RIGHT;
      defaultCorridor = CorridorPolicy.INNER;
    }
    else if ( role.equals("instrument") )
    {
      defaultRank = 40;
      defaultSide = PreferredSide.DOWN;
      defaultCorridor = CorridorPolicy.STACK;
    }
    else if ( role.equals("locatief") )
    {
      defaultRank = 50;
      defaultSide = PreferredSide.DOWN;
      defaultCorridor = CorridorPolicy.STACK;
    }
    else if ( role.equals("tijd") )
    {
      defaultRank = 60;
      defaultSide = PreferredSide.DOWN;
      defaultCorridor = CorridorPolicy.STACK;
    }

    return configuredLayoutRole(role, defaultRank, defaultSide, defaultCorridor);
  }

  private static LayoutRole configuredLayoutRole(String role, int defaultRank,
                                                 int defaultSide, int defaultCorridor)
  {
    Properties props = roleBoxProperties();
    String prefix = "functional.layout.role." + role + ".";
    int rank = parseIntProperty(props.getProperty(prefix + "rank"), defaultRank);
    int side = parsePreferredSide(props.getProperty(prefix + "side"), defaultSide);
    int corridor = parseCorridorPolicy(props.getProperty(prefix + "corridor"), defaultCorridor);
    return new LayoutRole(role, rank, side, corridor);
  }

  private static String canonicalRoleName(String role)
  {
    if ( role == null ) return "";
    String r = role.trim().toLowerCase();
    if ( r.equals("predicate") || r.equals("predicaat") || r.equals("verb") || r.equals("proces") || r.equals("action") || r.equals("event") ) return "pred";
    if ( r.equals("agent") || r.equals("actor") || r.equals("natuurlijke oorzaak") || r.equals("natuurlijke oorzaak") ) return "agens";
    if ( r.equals("patient") || r.equals("theme") || r.equals("thema") || r.equals("object") ) return "patiens";
    if ( r.equals("recipient") || r.equals("recipiënt") || r.equals("recipient") || r.equals("ontvanger") || r.equals("beneficiens") || r.equals("beneficiënt") || r.equals("beneficiary") ) return "recipiens";
    if ( r.equals("ervaarder") || r.equals("voeler") ) return "agens";
    if ( r.equals("fenomeen") || r.equals("bewoording") || r.equals("stakeholder") ) return "patiens";
    if ( r.equals("doel") ) return "recipiens";
    if ( r.equals("middel") ) return "instrument";
    if ( r.equals("locative") || r.equals("plaats") || r.equals("location") || r.equals("bron") || r.equals("richting") ) return "locatief";
    if ( r.equals("time") || r.equals("tempus") ) return "tijd";
    if ( r.equals("reden") || r.equals("motief") || r.equals("oorzaak") || r.equals("manier") ) return "instrument";
    return r;
  }

  private static synchronized Properties roleBoxProperties()
  {
    if ( cachedRoleBoxProperties != null ) return cachedRoleBoxProperties;
    Properties props = new Properties();
    loadRoleBoxProperties(props, "config/opengraph_defaults.properties");
    loadRoleBoxProperties(props, "config/opengraph_user.properties");
    loadRoleBoxProperties(props, "config/opengraphed_user.properties");
    cachedRoleBoxProperties = props;
    return cachedRoleBoxProperties;
  }

  private static void loadRoleBoxProperties(Properties props, String path)
  {
    InputStream in = null;
    try
    {
      File file = new File(path);
      if ( !file.exists() ) return;
      in = new FileInputStream(file);
      props.load(in);
    }
    catch ( Exception ignored )
    {
      /* Configuration is optional.  Built-in role defaults remain active. */
    }
    finally
    {
      if ( in != null )
      {
        try { in.close(); } catch ( Exception ignored ) { }
      }
    }
  }

  private static int parseIntProperty(String value, int fallback)
  {
    if ( value == null ) return fallback;
    try
    {
      return Integer.parseInt(value.trim());
    }
    catch ( Exception ignored )
    {
      return fallback;
    }
  }

  private static int parsePreferredSide(String value, int fallback)
  {
    if ( value == null ) return fallback;
    String v = value.trim().toLowerCase();
    if ( v.equals("left") || v.equals("links") || v.equals("l") ) return PreferredSide.LEFT;
    if ( v.equals("right") || v.equals("rechts") || v.equals("r") ) return PreferredSide.RIGHT;
    if ( v.equals("down") || v.equals("beneden") || v.equals("onder") || v.equals("d") ) return PreferredSide.DOWN;
    if ( v.equals("center") || v.equals("centre") || v.equals("midden") || v.equals("c") ) return PreferredSide.CENTER;
    return fallback;
  }

  private static int parseCorridorPolicy(String value, int fallback)
  {
    if ( value == null ) return fallback;
    String v = value.trim().toLowerCase();
    if ( v.equals("outer") || v.equals("buiten") ) return CorridorPolicy.OUTER;
    if ( v.equals("inner") || v.equals("binnen") ) return CorridorPolicy.INNER;
    if ( v.equals("stack") || v.equals("stacked") || v.equals("stapel") ) return CorridorPolicy.STACK;
    if ( v.equals("auto") ) return CorridorPolicy.AUTO;
    return fallback;
  }


  private static String extractFunctionalRoleFromNodeMetadata(graphStructure.Node node, String fallback)
  {
    if ( node == null || !node.hasFunctionalTreeMetadata() ) return fallback;
    String type = node.getFunctionalTreeNodeType();
    String process = node.getFunctionalTreeProcessType();
    String rolePath = node.getFunctionalTreeRolePath();

    if ( type != null && type.equals("top") )
    {
      if ( process != null && (process.equals("action") || process.equals("event")) ) return "pred";
      return "pred";
    }

    if ( rolePath == null || rolePath.length() == 0 ) return fallback;
    String value = rolePath.trim();
    int idx = value.lastIndexOf('/');
    if ( idx >= 0 && idx < value.length() - 1 ) value = value.substring(idx + 1);
    return value;
  }

  private static String extractRoleName(String label)
  {
    if ( label == null ) return "";
    String value = label.trim().toLowerCase();
    int idx = value.indexOf(':');
    if ( idx < 0 ) idx = value.indexOf('=');
    if ( idx < 0 ) idx = value.indexOf('-');
    if ( idx > 0 ) value = value.substring(0, idx).trim();
    return value;
  }

  private static boolean isNominalProjectionNode(OpenGraphNodeEx node)
  {
    String label = cleanLabel(node == null || node.getNode() == null ? null : node.getNode().getLabel());
    return label.equals("np") || label.equals("dp");
  }

  private static boolean allTerminalNodes(Vector nodes)
  {
    if ( nodes == null || nodes.size() == 0 )
    {
      return false;
    }
    for ( int i=0; i<nodes.size(); i++ )
    {
      if ( !isTerminalNode((OpenGraphNodeEx)nodes.elementAt(i)) )
      {
        return false;
      }
    }
    return true;
  }

  private static int nominalTerminalRank(OpenGraphNodeEx node)
  {
    String label = cleanLabel(node == null || node.getNode() == null ? null : node.getNode().getLabel());
    if ( isDutchDeterminerLabel(label) ) return 0;
    if ( isDutchAdjectiveLabel(label) ) return 1;
    return 2;
  }

  private static boolean isDutchDeterminerLabel(String label)
  {
    if ( label == null ) return false;
    String value = label.trim().toLowerCase();
    return value.equals("de") || value.equals("het") || value.equals("een") ||
           value.equals("dit") || value.equals("dat") || value.equals("deze") ||
           value.equals("die") || value.equals("mijn") || value.equals("jouw") ||
           value.equals("zijn") || value.equals("haar") || value.equals("ons") ||
           value.equals("onze") || value.equals("hun") || value.equals("geen") ||
           value.equals("elk") || value.equals("elke") || value.equals("ieder") ||
           value.equals("iedere") || value.equals("welk") || value.equals("welke");
  }

  private static boolean isDutchAdjectiveLabel(String label)
  {
    if ( label == null ) return false;
    String value = label.trim().toLowerCase();
    return value.equals("klein") || value.equals("kleine") ||
           value.equals("groot") || value.equals("grote") ||
           value.equals("oud") || value.equals("oude") ||
           value.equals("jong") || value.equals("jonge") ||
           value.equals("dik") || value.equals("dikke") ||
           value.equals("mooi") || value.equals("mooie") ||
           value.equals("nieuw") || value.equals("nieuwe") ||
           value.equals("bijzonder") || value.equals("bijzondere");
  }

  private static int childExpansionSideForOrderedBinary(OpenGraphNodeEx child,
                                                        OpenGraphNodeEx visualLeft,
                                                        OpenGraphNodeEx visualRight)
  {
    return child == visualRight ? SIDE_RIGHT : SIDE_LEFT;
  }

  private static String normalizeLanguageTreeLayoutStrategy(String value)
  {
    if ( value == null ) return LAYOUT_NARY_COMPACT_LR;
    String v = value.trim().toLowerCase();
    v = v.replace('-', '_').replace(' ', '_');
    if ( v.equals("free") || v.equals("d_lite") || v.equals("free_d_lite") )
    {
      return LAYOUT_FREE_D_LITE;
    }
    if ( v.equals("projection_box") || v.equals("projection_order_box") ||
         v.equals("projectie_box") || v.equals("projectiegestuurd") ||
         v.equals("projection_nary_box") )
    {
      return LAYOUT_PROJECTION_BOX;
    }
    if ( v.equals("role_box") || v.equals("rolebox") || v.equals("fg_role_box") ||
         v.equals("functional_role_box") || v.equals("rollen_box") )
    {
      return LAYOUT_ROLE_BOX;
    }
    if ( v.equals("compact") || v.equals("lr") || v.equals("compact_lr") ||
         v.equals("nary_compact_lr") || v.equals("n_ary_compact_lr") ||
         v.equals("n_binair") || v.equals("n_binair_compact_lr") ||
         v.equals("nbinair") || v.equals("non_binary") || v.equals("nonbinary") )
    {
      return LAYOUT_NARY_COMPACT_LR;
    }
    return LAYOUT_PROJECTION_BOX;
  }

  private static boolean isNaryCompactLR(String strategy)
  {
    String normalized = normalizeLanguageTreeLayoutStrategy(strategy);
    return LAYOUT_NARY_COMPACT_LR.equals(normalized) || LAYOUT_PROJECTION_BOX.equals(normalized) || LAYOUT_ROLE_BOX.equals(normalized);
  }

  private static boolean isProjectionBoxLayout(String strategy)
  {
    String normalized = normalizeLanguageTreeLayoutStrategy(strategy);
    return LAYOUT_PROJECTION_BOX.equals(normalized) || LAYOUT_ROLE_BOX.equals(normalized);
  }

  private static boolean isRoleBoxLayout(String strategy)
  {
    return LAYOUT_ROLE_BOX.equals(normalizeLanguageTreeLayoutStrategy(strategy));
  }

  private static boolean isFunctionalLayoutDebug()
  {
    Properties props = roleBoxProperties();
    String value = props.getProperty("functional.layout.debug", "false");
    return parseBooleanProperty(value, false);
  }

  private static int functionalDownStackMinClearanceX()
  {
    Properties props = roleBoxProperties();
    return Math.max(4, parseIntProperty(props.getProperty("functional.layout.downStack.minClearanceX"), 8));
  }

  private static int functionalDownStackEdgeConeClearance()
  {
    Properties props = roleBoxProperties();
    return Math.max(1, parseIntProperty(props.getProperty("functional.layout.downStack.edgeConeClearance"), 3));
  }

  private static String functionalDownStackMode()
  {
    Properties props = roleBoxProperties();
    String value = props.getProperty("functional.layout.downStack.mode", "adjunct_stack");
    if ( value == null ) return "adjunct_stack";
    return value.trim().toLowerCase().replace('-', '_').replace(' ', '_');
  }

  private static int functionalDownStackXOffset()
  {
    Properties props = roleBoxProperties();
    return Math.max(1, parseIntProperty(props.getProperty("functional.layout.downStack.xOffset"), 2));
  }

  private static int functionalDownStackVerticalGap()
  {
    Properties props = roleBoxProperties();
    return Math.max(1, parseIntProperty(props.getProperty("functional.layout.downStack.verticalGap"), 2));
  }

  private static boolean parseBooleanProperty(String value, boolean fallback)
  {
    if ( value == null ) return fallback;
    String v = value.trim().toLowerCase();
    if ( v.equals("true") || v.equals("yes") || v.equals("ja") || v.equals("1") || v.equals("on") ) return true;
    if ( v.equals("false") || v.equals("no") || v.equals("nee") || v.equals("0") || v.equals("off") ) return false;
    return fallback;
  }

  private static boolean isVerbClusterNode(OpenGraphNodeEx root)
  {
    String label = cleanLabel(root == null || root.getNode() == null ? null : root.getNode().getLabel());
    return label.equals("v") || label.equals("v-cluster") || label.equals("vcluster") ||
           label.equals("werkwoordgroep") || label.equals("wwgroep");
  }

  private static OpenGraphNodeEx[] orderVerbClusterChildren(OpenGraphNodeEx a,
                                                            OpenGraphNodeEx b,
                                                            String order)
  {
    int aKind = verbClusterKind(a);
    int bKind = verbClusterKind(b);
    if ( aKind == 0 || bKind == 0 || aKind == bKind )
    {
      return null;
    }

    OpenGraphNodeEx pv = aKind == 1 ? a : b;
    OpenGraphNodeEx vd = aKind == 2 ? a : b;
    if ( isVdPvOrder(order) )
    {
      return new OpenGraphNodeEx[] { vd, pv };
    }
    return new OpenGraphNodeEx[] { pv, vd };
  }

  private static boolean isVdPvOrder(String order)
  {
    if ( order == null )
    {
      return false;
    }
    String value = order.trim().toLowerCase();
    return value.equals("vd_pv") || value.equals("vd-pv") || value.equals("gebeten heeft");
  }

  /**
   * Returns 1 for pv/finite auxiliary, 2 for VD/participle, 0 if unknown.
   * The detection first checks explicit category labels and then descends into
   * terminal labels so both V->pv/VD and V->heeft/gebeten drawings work.
   */
  private static int verbClusterKind(OpenGraphNodeEx node)
  {
    String label = cleanLabel(node == null || node.getNode() == null ? null : node.getNode().getLabel());
    if ( isFiniteVerbLabel(label) ) return 1;
    if ( isParticipleLabel(label) ) return 2;

    Vector children = node == null ? null : node.getChildren();
    if ( children == null )
    {
      return 0;
    }
    int result = 0;
    for ( int i=0; i<children.size(); i++ )
    {
      int childKind = verbClusterKind((OpenGraphNodeEx)children.elementAt(i));
      if ( childKind != 0 )
      {
        if ( result != 0 && result != childKind )
        {
          return 0;
        }
        result = childKind;
      }
    }
    return result;
  }

  private static boolean isFiniteVerbLabel(String label)
  {
    if ( label == null ) return false;
    String value = label.trim().toLowerCase();
    return value.equals("pv") || value.equals("fin") || value.equals("vfin") ||
           value.equals("v-aux") || value.equals("aux") || value.equals("heeft") ||
           value.equals("heb") || value.equals("hebt") || value.equals("hebben") ||
           value.equals("had") || value.equals("is") || value.equals("zijn") ||
           value.equals("was") || value.equals("wordt") || value.equals("werd") ||
           value.equals("zal") || value.equals("kan") || value.equals("moet") ||
           value.equals("mag") || value.equals("wil");
  }

  private static boolean isParticipleLabel(String label)
  {
    if ( label == null ) return false;
    String value = label.trim().toLowerCase();
    return value.equals("vd") || value.equals("vpart") || value.equals("v-part") ||
           value.equals("part") || value.equals("vpp") || value.equals("voltooid deelwoord") ||
           value.equals("gebeten") || value.equals("gebreid") || value.equals("gegeven") ||
           (value.startsWith("ge") && value.length() > 4 && !isFiniteVerbLabel(value));
  }

  private static String cleanLabel(String label)
  {
    if ( label == null )
    {
      return "";
    }
    return label.trim().toLowerCase();
  }

  private static void initializeLeaf(OpenGraphNodeEx root)
  {
    root.setGridX(0);
    root.setGridY(0);
    root.setBoundX(0);
    root.setBoundY(0);
    root.setBoundWidth(0);
    root.setBoundHeight(0);
  }


  /**
   * Language Tree V-cluster layout rule.
   *
   * Switching pv-VD <-> VD-pv must not change the surrounding tree anchor.
   * The ordinary binary domain rule lets the parent bounding box depend on
   * which child is on the left.  For V-clusters that made the fitted grid gain
   * one column on the left and shifted the whole tree horizontally.
   *
   * This rule keeps the same reserved box for both orders and only swaps the
   * visible child assignment.  The projection mechanism remains unchanged.
   */
  private static void verbClusterRule(OpenGraphNodeEx root,
                                      OpenGraphNodeEx visualFirst,
                                      OpenGraphNodeEx visualSecond,
                                      OpenGraphNodeEx childA,
                                      OpenGraphNodeEx childB,
                                      int firstSide)
  {
    LayoutBox boxA = LayoutBox.fromSubtree(childA);
    LayoutBox boxB = LayoutBox.fromSubtree(childB);
    int maxWidth = Math.max(childA.getBoundWidth(), childB.getBoundWidth());
    int sideCapacity = maxWidth + ROOT_SIDE_GAP + 1;

    OpenGraphNodeEx visualLeft = firstSide == SIDE_RIGHT ? visualSecond : visualFirst;
    OpenGraphNodeEx visualRight = firstSide == SIDE_RIGHT ? visualFirst : visualSecond;

    LayoutBox leftBox = visualLeft == childA ? boxA : boxB;
    LayoutBox rightBox = visualRight == childA ? boxA : boxB;
    LayoutBox topBox = firstSide == SIDE_RIGHT ? rightBox : leftBox;
    LayoutBox lowerBox = firstSide == SIDE_RIGHT ? leftBox : rightBox;

    int leftShiftX = -(sideCapacity - leftExtent(visualLeft));
    int rightShiftX = sideCapacity - rightExtent(visualRight);
    int topShiftY = 1;
    int lowerShiftY = stackedBelowShiftY(topBox, topShiftY, lowerBox, 0);
    int leftShiftY = firstSide == SIDE_RIGHT ? lowerShiftY : topShiftY;
    int rightShiftY = firstSide == SIDE_RIGHT ? topShiftY : lowerShiftY;

    visualLeft.shiftX(leftShiftX);
    visualLeft.shiftY(leftShiftY);

    visualRight.shiftX(rightShiftX);
    visualRight.shiftY(rightShiftY);

    /*
     * Keep lexical preterminals free/open as well.  In side-aware mode the
     * terminal fans out to the same outer side as its preterminal: left-side
     * preterminals to the left, right-side preterminals to the right.  This is
     * the important correction for right-growing VP/V clusters: pv -> heeft no
     * longer folds back to the left of the higher VP axis.
     */
    fanOutUnaryPreterminal(visualLeft, SIDE_LEFT);
    fanOutUnaryPreterminal(visualRight, SIDE_RIGHT);

    int minX = Math.min(0, Math.min(boxMinX(visualLeft), boxMinX(visualRight)));
    int maxX = Math.max(0, Math.max(boxMaxX(visualLeft), boxMaxX(visualRight)));
    int maxY = Math.max(0, Math.max(boxMaxY(visualLeft), boxMaxY(visualRight)));

    root.setGridX(0);
    root.setGridY(0);
    root.setBoundX(-minX);
    root.setBoundY(0);
    root.setBoundWidth(maxX - minX);
    root.setBoundHeight(maxY);
  }

  private static void fanOutUnaryPreterminal(OpenGraphNodeEx node, int direction)
  {
    if ( node == null )
    {
      return;
    }
    Vector children = node.getChildren();
    if ( children == null || children.size() != 1 )
    {
      return;
    }

    OpenGraphNodeEx child = (OpenGraphNodeEx)children.elementAt(0);
    if ( child == null || child.getChildren().size() != 0 )
    {
      return;
    }

    forceUnaryFanOut(node, child, direction);
  }

  /**
   * D-lite Language Tree rule: unary edges are still built recursively, but the
   * returned box is now a real occupied-cell box.  A single child is not left
   * directly below its parent in free Language Tree layout.
   */
  private static void freeUnaryRule(OpenGraphNodeEx root, OpenGraphNodeEx child, int direction)
  {
    child.shiftY(1);
    forceUnaryFanOut(root, child, direction);
  }

  private static int preferredUnaryDirection(OpenGraphNodeEx root, OpenGraphNodeEx child,
                                             int sidePreference)
  {
    if ( sidePreference == SIDE_LEFT || sidePreference == SIDE_RIGHT )
    {
      return sidePreference;
    }
    return SIDE_RIGHT;
  }

  private static void forceUnaryFanOut(OpenGraphNodeEx root, OpenGraphNodeEx child, int direction)
  {
    if ( root == null || child == null )
    {
      return;
    }

    /*
     * Grid coordinates are local until correctGridCoordinates() accumulates
     * them at the end of the operation.  A unary child therefore needs a
     * local offset from its parent, not an absolute x based on the already
     * shifted parent.  The previous version used root.getGridX()+/-1, which
     * let terminals such as "heeft" escape the box seen by the parent VP.
     */
    int targetLocalX = direction < 0 ? -1 : 1;
    child.shiftX(targetLocalX - child.getGridX());
    recomputeOccupiedBox(root);
  }

  /**
   * D-lite binary combiner.  It keeps the recursive box approach, but candidate
   * offsets are now checked against occupied node cells instead of only the
   * coarse bounding box.  The current compact/stacked OpenGraph shape remains
   * the first candidate; if it is invalid, the right subtree is moved down until
   * the occupied cells and parent-child constraints are valid.
   */
  private static void freeBinaryRule(OpenGraphNodeEx root,
                                     OpenGraphNodeEx first,
                                     OpenGraphNodeEx second,
                                     int firstSide)
  {
    int normalizedFirstSide = firstSide == SIDE_RIGHT ? SIDE_RIGHT : SIDE_LEFT;
    int secondSide = -normalizedFirstSide;

    int firstShiftX = normalizedFirstSide == SIDE_LEFT
                    ? -(rightExtent(first) + ROOT_SIDE_GAP)
                    :  (leftExtent(first) + ROOT_SIDE_GAP);
    int firstShiftY = 1;

    int secondShiftX = secondSide == SIDE_LEFT
                     ? -(rightExtent(second) + ROOT_SIDE_GAP)
                     :  (leftExtent(second) + ROOT_SIDE_GAP);
    OpenGraphNodeEx visualLeft = normalizedFirstSide == SIDE_LEFT ? first : second;
    OpenGraphNodeEx visualRight = normalizedFirstSide == SIDE_LEFT ? second : first;
    int extraGap = needsExtraRootSentenceGap(root, visualLeft, visualRight) ? 1 : 0;

    LayoutBox firstBox = LayoutBox.fromSubtree(first);
    LayoutBox secondBox = LayoutBox.fromSubtree(second);
    int secondShiftY = stackedBelowShiftY(firstBox, firstShiftY, secondBox, extraGap);

    /*
     * v4.24.3 structural LR + inherited avoid-bound:
     * binary child order is not flipped by a right-side context.  The complete
     * right/second subtree box may not intrude into the zone reserved by the
     * left/first subtree box.  If needed, the already-recursed child box is
     * shifted farther outward and then downward until the position is free.
     */
    secondShiftX = enforceSiblingBoxAvoid(firstBox, secondBox,
                                          firstShiftX, secondShiftX, secondSide);

    int guard = Math.max(8, first.getSubTreeSize() + second.getSubTreeSize() + 8);
    int attempt = 0;
    while ( attempt < guard &&
            !candidateBinaryPlacementIsValid(firstBox, secondBox,
                                             firstShiftX, firstShiftY,
                                             secondShiftX, secondShiftY) )
    {
      secondShiftY++;
      attempt++;
    }

    first.shiftX(firstShiftX);
    first.shiftY(firstShiftY);

    second.shiftX(secondShiftX);
    second.shiftY(secondShiftY);

    recomputeOccupiedBox(root);
  }

  /**
   * Legacy v4.24.0 combiner.  Deliberately not used for Language Tree now:
   * it produced RR/LL cascades.  The active v4.24.2 rule keeps the requested
   * LR/RL binary combiner and only enlarges child-box shifts to obey inherited
   * exclusion boundaries.
   */
  private static void freeBinarySameSideBoxAvoidRule(OpenGraphNodeEx root,
                                                    OpenGraphNodeEx first,
                                                    OpenGraphNodeEx second,
                                                    int side)
  {
    int direction = side == SIDE_RIGHT ? SIDE_RIGHT : SIDE_LEFT;

    LayoutBox firstBox = LayoutBox.fromSubtree(first);
    LayoutBox secondBox = LayoutBox.fromSubtree(second);

    int firstShiftX = direction == SIDE_LEFT
                    ? -(rightExtent(first) + ROOT_SIDE_GAP)
                    :  (leftExtent(first) + ROOT_SIDE_GAP);
    int firstShiftY = 1;

    int secondShiftX;
    if ( direction == SIDE_RIGHT )
    {
      int requiredMinX = firstBox.maxX + firstShiftX + ROOT_SIDE_GAP + 1;
      secondShiftX = requiredMinX - secondBox.minX;
    }
    else
    {
      int requiredMaxX = firstBox.minX + firstShiftX - ROOT_SIDE_GAP - 1;
      secondShiftX = requiredMaxX - secondBox.maxX;
    }

    int extraGap = needsExtraRootSentenceGap(root, first, second) ? 1 : 0;
    int secondShiftY = stackedBelowShiftY(firstBox, firstShiftY, secondBox, extraGap);

    int guard = Math.max(16, first.getSubTreeSize() + second.getSubTreeSize() + 16);
    int attempt = 0;
    while ( attempt < guard &&
            !candidateBinaryPlacementIsValid(firstBox, secondBox,
                                             firstShiftX, firstShiftY,
                                             secondShiftX, secondShiftY) )
    {
      secondShiftY++;
      attempt++;
    }

    first.shiftX(firstShiftX);
    first.shiftY(firstShiftY);

    second.shiftX(secondShiftX);
    second.shiftY(secondShiftY);

    recomputeOccupiedBox(root);
  }

  /**
   * Returns the local Y shift for a later subtree whose full box must start
   * below the real bottom of an earlier subtree box.  This uses LayoutBox
   * geometry instead of boundHeight, because boundHeight can be stale or too
   * small after unary fan-out.
   *
   * v4.24.6: if the previous sibling box ends in a lexical/end terminal,
   * reserve one extra row.  Terminal rows are lexical positions; a following
   * category root such as V or VD must not be drawn on the same row as man or
   * heeft even when the occupied-node boxes do not technically collide.
   */
  private static int stackedBelowShiftY(LayoutBox upperBox, int upperShiftY,
                                        LayoutBox lowerBox, int extraGap)
  {
    int terminalGap = upperBox.endsInTerminal() ? 1 : 0;
    int gap = 1 + Math.max(0, extraGap) + terminalGap;
    return upperShiftY + upperBox.maxY + gap - lowerBox.minY;
  }

  private static int enforceSiblingBoxAvoid(LayoutBox firstBox, LayoutBox secondBox,
                                            int firstShiftX, int secondShiftX,
                                            int secondSide)
  {
    int minGap = ROOT_SIDE_GAP + 1;
    if ( secondSide == SIDE_RIGHT )
    {
      int requiredMinX = firstBox.maxX + firstShiftX + minGap;
      int currentMinX = secondBox.minX + secondShiftX;
      if ( currentMinX < requiredMinX )
      {
        return secondShiftX + (requiredMinX - currentMinX);
      }
      return secondShiftX;
    }

    int requiredMaxX = firstBox.minX + firstShiftX - minGap;
    int currentMaxX = secondBox.maxX + secondShiftX;
    if ( currentMaxX > requiredMaxX )
    {
      return secondShiftX - (currentMaxX - requiredMaxX);
    }
    return secondShiftX;
  }

  private static boolean candidateBinaryPlacementIsValid(LayoutBox leftBox,
                                                         LayoutBox rightBox,
                                                         int leftShiftX,
                                                         int leftShiftY,
                                                         int rightShiftX,
                                                         int rightShiftY)
  {
    if ( leftShiftX == 0 || rightShiftX == 0 )
    {
      return false;
    }
    if ( leftBox.boundsIntersectShifted(rightBox, leftShiftX, leftShiftY, rightShiftX, rightShiftY) )
    {
      return false;
    }
    if ( leftBox.intersectsShifted(rightBox, leftShiftX, leftShiftY, rightShiftX, rightShiftY) )
    {
      return false;
    }
    if ( leftBox.containsShifted(0, 0, leftShiftX, leftShiftY) ||
         rightBox.containsShifted(0, 0, rightShiftX, rightShiftY) )
    {
      return false;
    }
    return true;
  }

  private static void recomputeOccupiedBox(OpenGraphNodeEx root)
  {
    LayoutBox box = LayoutBox.fromSubtree(root);
    box.applyTo(root);
  }

  private static class LayoutBox
  {
    int minX;
    int maxX;
    int minY;
    int maxY;
    Hashtable leftContour;
    Hashtable rightContour;
    HashSet occupied;
    boolean hasTerminal;
    int terminalMaxY;

    private LayoutBox()
    {
      minX = 0;
      maxX = 0;
      minY = 0;
      maxY = 0;
      leftContour = new Hashtable();
      rightContour = new Hashtable();
      occupied = new HashSet();
      hasTerminal = false;
      terminalMaxY = 0;
    }

    static LayoutBox fromSubtree(OpenGraphNodeEx root)
    {
      LayoutBox box = new LayoutBox();
      box.collect(root, 0, 0);
      return box;
    }

    private void collect(OpenGraphNodeEx node, int parentX, int parentY)
    {
      if ( node == null )
      {
        return;
      }

      int x = parentX + node.getGridX();
      int y = parentY + node.getGridY();
      Vector children = node.getChildren();
      add(x, y, children.size() == 0);

      for ( int i=0; i<children.size(); i++ )
      {
        collect((OpenGraphNodeEx)children.elementAt(i), x, y);
      }
    }

    private void add(int x, int y, boolean terminal)
    {
      if ( occupied.size() == 0 )
      {
        minX = maxX = x;
        minY = maxY = y;
      }
      else
      {
        minX = Math.min(minX, x);
        maxX = Math.max(maxX, x);
        minY = Math.min(minY, y);
        maxY = Math.max(maxY, y);
      }
      occupied.add(cellKey(x, y));
      if ( terminal )
      {
        if ( !hasTerminal || y > terminalMaxY )
        {
          terminalMaxY = y;
        }
        hasTerminal = true;
      }

      Integer row = Integer.valueOf(y);
      Integer left = (Integer)leftContour.get(row);
      Integer right = (Integer)rightContour.get(row);
      if ( left == null || x < left.intValue() )
      {
        leftContour.put(row, Integer.valueOf(x));
      }
      if ( right == null || x > right.intValue() )
      {
        rightContour.put(row, Integer.valueOf(x));
      }
    }

    boolean endsInTerminal()
    {
      return hasTerminal && terminalMaxY >= maxY;
    }

    boolean containsShifted(int x, int y, int dx, int dy)
    {
      return occupied.contains(cellKey(x - dx, y - dy));
    }

    boolean intersectsShifted(LayoutBox other, int thisDx, int thisDy, int otherDx, int otherDy)
    {
      Iterator it = occupied.iterator();
      while ( it.hasNext() )
      {
        String key = (String)it.next();
        int comma = key.indexOf(',');
        int x = Integer.parseInt(key.substring(0, comma)) + thisDx;
        int y = Integer.parseInt(key.substring(comma + 1)) + thisDy;
        if ( other.containsShifted(x, y, otherDx, otherDy) )
        {
          return true;
        }
      }
      return false;
    }

    boolean boundsIntersectShifted(LayoutBox other, int thisDx, int thisDy, int otherDx, int otherDy)
    {
      int thisMinX = minX + thisDx;
      int thisMaxX = maxX + thisDx;
      int thisMinY = minY + thisDy;
      int thisMaxY = maxY + thisDy;
      int otherMinX = other.minX + otherDx;
      int otherMaxX = other.maxX + otherDx;
      int otherMinY = other.minY + otherDy;
      int otherMaxY = other.maxY + otherDy;

      boolean xOverlap = thisMinX <= otherMaxX && otherMinX <= thisMaxX;
      boolean yOverlap = thisMinY <= otherMaxY && otherMinY <= thisMaxY;
      return xOverlap && yOverlap;
    }

    void applyTo(OpenGraphNodeEx root)
    {
      root.setBoundX(root.getGridX() - minX);
      root.setBoundY(0);
      root.setBoundWidth(maxX - minX);
      root.setBoundHeight(maxY - root.getGridY());
    }

    private static String cellKey(int x, int y)
    {
      return x + "," + y;
    }
  }

  /**
   * Compact bottom-up box integration for the open tree.
   *
   * Each child first owns its smallest reserved bounding box.
   * The left subtree is placed completely left of the root axis;
   * the right subtree is placed completely right of the root axis and below
   * the reserved left box, so sibling leaves do not collapse onto one line.
   */
  static void domainRule(OpenGraphNodeEx root, OpenGraphNodeEx left, OpenGraphNodeEx right)
  {
    LayoutBox leftBox = LayoutBox.fromSubtree(left);
    LayoutBox rightBox = LayoutBox.fromSubtree(right);

    int leftShiftX = -(rightExtent(left) + ROOT_SIDE_GAP);
    int leftShiftY = 1;

    int rightShiftX = leftExtent(right) + ROOT_SIDE_GAP;
    int rightExtraGap = needsExtraRootSentenceGap(root, left, right) ? 1 : 0;
    int rightShiftY = stackedBelowShiftY(leftBox, leftShiftY, rightBox, rightExtraGap);

    left.shiftX(leftShiftX);
    left.shiftY(leftShiftY);

    right.shiftX(rightShiftX);
    right.shiftY(rightShiftY);

    int minX = Math.min(0, Math.min(boxMinX(left), boxMinX(right)));
    int maxX = Math.max(0, Math.max(boxMaxX(left), boxMaxX(right)));
    int maxY = Math.max(0, Math.max(boxMaxY(left), boxMaxY(right)));

    root.setGridX(0);
    root.setGridY(0);
    root.setBoundX(-minX);
    root.setBoundY(0);
    root.setBoundWidth(maxX - minX);
    root.setBoundHeight(maxY);
  }

  /**
   * General open-tree rule for nodes with three or more children.
   *
   * Children are processed in deterministic structural order and stacked on
   * successive grid rows below the parent.  A small horizontal spread keeps
   * the outgoing edges visually distinguishable, while the vertical stacking
   * preserves the left lexical-axis reading order used by language trees.
   */

  /**
   * Dutch Language Tree readability tweak.
   *
   * For the classic S -> NP VP split, the VP category should not land on the
   * same visual row as the deepest terminal inside the subject NP box.
   * A one-row extra gap below the left NP box keeps the VP category visually
   * separate and more intuitive without affecting other binary trees.
   */
  private static boolean needsExtraRootSentenceGap(OpenGraphNodeEx root,
                                                   OpenGraphNodeEx left,
                                                   OpenGraphNodeEx right)
  {
    if ( root == null || left == null || right == null )
    {
      return false;
    }
    return isLabel(root, "s") &&
           ((isLabel(left, "np") && isLabel(right, "vp")) ||
            (isLabel(left, "dp") && isLabel(right, "vp")));
  }

  private static boolean isLabel(OpenGraphNodeEx node, String expected)
  {
    if ( expected == null )
    {
      return false;
    }
    return cleanLabel(node == null || node.getNode() == null ? null : node.getNode().getLabel()).equals(expected);
  }

  /**
   * Projectiegestuurde n-ary Language Tree combiner.
   *
   * True n-ary nodes (3, 4, 5, ... children) are not converted to an artificial
   * binary chain.  Children arrive here in projection order.  The rule then
   * places their already-computed local subtree boxes in alternating visual
   * corridors.  It may shift boxes to keep the drawing free, but it may not
   * reorder the projection.
   */
  private static void nAryCompactLRRule(OpenGraphNodeEx root, Vector children, int firstSide, String strategy)
  {
    if ( children == null || children.size() == 0 )
    {
      initializeLeaf(root);
      return;
    }

    Vector placed = new Vector();
    ShiftedLayoutBox previous = null;
    int count = children.size();
    int guard = Math.max(16, root.getSubTreeSize() + 16);
    int leftSideOrdinal = 0;
    int rightSideOrdinal = 0;

    for ( int i=0; i<count; i++ )
    {
      OpenGraphNodeEx child = (OpenGraphNodeEx)children.elementAt(i);
      LayoutBox childBox = LayoutBox.fromSubtree(child);
      int childSide = naryChildSideFor(root, child, i, firstSide, strategy);
      boolean placeLeft = childSide == SIDE_LEFT;

      /*
       * v4.24.8: terminals in n-ary fan-out have a lexical-position status.
       * Alternating LR alone put child 0 and child 2 on the same outer column
       * (for example de/hond or kleine/man).  That makes the lower lexical
       * child share the previous terminal corridor.  Keep the vertical source
       * order, but widen each repeated side by one column, so same-side
       * terminal/frontier boxes are distinct: L1, R1, L2, R2, ... .
       */
      int sameSideOrdinal;
      if ( placeLeft )
      {
        sameSideOrdinal = leftSideOrdinal++;
      }
      else
      {
        sameSideOrdinal = rightSideOrdinal++;
      }
      /*
       * v4.24.9: the first terminal corridors must also be diagonally free
       * from the n-ary parent.  With only one grid column between NP and a
       * lexical child, edges such as NP-de and NP-grote are visually/vertically
       * unfree.  Terminals therefore start one column farther out, while
       * repeated same-side children still get their own L2/R2/... corridors.
       */
      int terminalDiagonalGap = isTerminalNode(child) ? 1 : 0;
      int sameSideExtraGap = sameSideOrdinal + terminalDiagonalGap;

      int childShiftX = placeLeft ? (-ROOT_SIDE_GAP - sameSideExtraGap - childBox.maxX)
                                  : ( ROOT_SIDE_GAP + sameSideExtraGap - childBox.minX);

      /*
       * v4.24.7: n-ary children are stacked in source order with one
       * accumulated vertical frontier.  The previous implementation kept
       * separate left/right frontiers; with terminal children this allowed
       * child 0 and child 1, for example test and de, to share the same row.
       * Lexical terminals are real occupied rows in Language Tree layout,
       * so each later n-ary child must be placed below the full previous
       * subtree box, including terminal-aware bottom spacing.
       */
      int childShiftY = previous == null
                      ? 1 - childBox.minY
                      : stackedBelowShiftY(previous.box, previous.shiftY, childBox, 0);

      int attempt = 0;
      while ( attempt < guard &&
              !candidateNAryPlacementIsValid(childBox, childShiftX, childShiftY, placed) )
      {
        childShiftY++;
        attempt++;
      }

      child.shiftX(childShiftX);
      child.shiftY(childShiftY);

      previous = new ShiftedLayoutBox(childBox, childShiftX, childShiftY);
      placed.addElement(previous);
    }

    recomputeOccupiedBox(root);
  }


  /**
   * v4.25.5: first active Functional Tree role-box combiner.
   *
   * Functional Tree uses role identity rather than child index as the main
   * geometry hint.  The already sorted children arrive in role-rank order.
   * Roles are placed into a deterministic local shape:
   *
   *   pred       -> center corridor below the functional root
   *   agens      -> left corridor
   *   patiens    -> right outer corridor
   *   recipiens  -> right inner/deeper corridor
   *   instrument/locatief/tijd -> down stack
   *
   * The rule remains box-based: each child is first drawn as its own local
   * subtree and is then shifted as a whole.  It never rewrites role order.
   */
  private static void nAryRoleBoxRule(OpenGraphNodeEx root, Vector children)
  {
    if ( children == null || children.size() == 0 )
    {
      initializeLeaf(root);
      return;
    }

    Vector placed = new Vector();
    int guard = Math.max(32, root.getSubTreeSize() + 32);
    boolean debug = isFunctionalLayoutDebug();
    StringBuffer trace = debug ? new StringBuffer() : null;
    if ( debug )
    {
      trace.append("FT role_box layout trace\n");
      trace.append("root=").append(cleanLabel(root.getNode() == null ? null : root.getNode().getLabel()))
           .append(" x=0 y=0 strategy=role_box roles.count=").append(children.size())
           .append(" downStack.minClearanceX=").append(functionalDownStackMinClearanceX())
           .append(" downStack.edgeConeClearance=").append(functionalDownStackEdgeConeClearance())
           .append(" downStack.mode=").append(functionalDownStackMode())
           .append(" downStack.xOffset=").append(functionalDownStackXOffset())
           .append(" downStack.verticalGap=").append(functionalDownStackVerticalGap())
           .append('\n');
    }

    /*
     * v4.25.7: Functional Tree visual refinement.
     *
     * v4.25.5/6 stacked every role below the previous role globally.  That was
     * safe but visually too much like a list.  FT now keeps separate visual
     * frontiers per role side: pred/center on the functional spine, agens in a
     * left tier, patiens/recipiens in a right tier, and locatief/tijd/etc. in a
     * down stack.  Collision and box checks can still push a candidate down, but
     * they never reorder the role-rank sequence and they shift each child
     * subtree as a whole.
     */
    int leftOrdinal = 0;
    int rightOrdinal = 0;
    int downOrdinal = 0;
    int centerOrdinal = 0;

    /*
     * v4.26.5: FT role placement uses a compact equal-angle fan.  Earlier
     * tier/stack variants avoided node collisions but produced long branches
     * and repeated edge directions.  Roles now receive preferred polar-ish
     * offsets first; collision recovery changes both x and y so it does not
     * create long same-slope branches by only pushing downward.
     */
    for ( int i=0; i<children.size(); i++ )
    {
      OpenGraphNodeEx child = (OpenGraphNodeEx)children.elementAt(i);
      LayoutBox childBox = LayoutBox.fromSubtree(child);
      LayoutRole role = layoutRoleFor(child);
      int side = role.getPreferredSide();
      int corridor = role.getCorridorPolicy();

      int ordinal;
      if ( side == PreferredSide.LEFT )
      {
        ordinal = leftOrdinal++;
      }
      else if ( side == PreferredSide.RIGHT )
      {
        ordinal = rightOrdinal++;
      }
      else if ( side == PreferredSide.DOWN )
      {
        ordinal = downOrdinal++;
      }
      else
      {
        ordinal = centerOrdinal++;
      }

      int preferredX = roleBoxPreferredX(role, ordinal, i, children.size());
      int preferredY = roleBoxPreferredY(role, ordinal, i, children.size());
      int childShiftX = preferredX - childBox.minX;
      int childShiftY = preferredY - childBox.minY;
      int initialShiftX = childShiftX;
      int initialShiftY = childShiftY;

      int attempt = 0;
      while ( attempt < guard &&
              !candidateNAryPlacementIsValidForRoleBox(childBox, childShiftX, childShiftY, placed) )
      {
        attempt++;
        int retryX = roleBoxRetryX(preferredX, attempt);
        int retryY = roleBoxRetryY(preferredY, attempt);
        childShiftX = retryX - childBox.minX;
        childShiftY = retryY - childBox.minY;
      }

      if ( debug )
      {
        trace.append("child label=\"").append(cleanLabel(child.getNode() == null ? null : child.getNode().getLabel())).append("\"")
             .append(" role=").append(role.getName())
             .append(" rank=").append(role.getRank())
             .append(" side=").append(preferredSideName(side))
             .append(" corridor=").append(corridorPolicyName(corridor))
             .append(" localOffset=(").append(initialShiftX).append(',').append(initialShiftY).append(')')
             .append(" finalOffset=(").append(childShiftX).append(',').append(childShiftY).append(')')
             .append(" attempts=").append(attempt)
             .append(" subtreeBox=").append(layoutBoxToString(childBox))
             .append('\n');
      }

      child.shiftX(childShiftX);
      child.shiftY(childShiftY);
      placed.addElement(new ShiftedLayoutBox(childBox, childShiftX, childShiftY));
    }

    recomputeOccupiedBox(root);
    if ( debug )
    {
      trace.append("FINAL boxes: root-subtree=").append(layoutBoxToString(LayoutBox.fromSubtree(root))).append('\n');
      System.out.println(trace.toString());
    }
  }

  private static String layoutBoxToString(LayoutBox box)
  {
    if ( box == null ) return "[]";
    return "[" + box.minX + "," + box.minY + "," + box.maxX + "," + box.maxY + "; terminalBottom=" + box.endsInTerminal() + "]";
  }

  private static int roleBoxPreferredX(LayoutRole role, int ordinal, int globalOrdinal, int totalCount)
  {
    /*
     * v4.26.9: correction of v4.26.7/8.  The requested unit spacing was
     * vertical, not horizontal.  X is compact again and may use role anchors;
     * Y gets the exact configured unit-step.
     */
    int anchorX = roleBoxAnchorX(role, ordinal);
    int fanX = roleBoxFanSlotX(globalOrdinal, totalCount);
    String spread = functionalHorizontalSpread();
    if ( spread.equals("wide") ) return roundDiv(anchorX + fanX, 2);
    if ( spread.equals("medium") ) return roundDiv(anchorX + (2 * fanX), 3);
    return fanX;
  }

  private static int roleBoxPreferredY(LayoutRole role, int ordinal, int globalOrdinal, int totalCount)
  {
    int step = functionalVerticalStep();
    int start = functionalVerticalStart();
    return start + (step * globalOrdinal);
  }

  private static int roleBoxAnchorX(LayoutRole role, int ordinal)
  {
    String name = role == null ? "" : role.getName();
    if ( name == null ) name = "";
    name = name.toLowerCase();

    if ( name.equals("pred") || name.equals("predicate") ) return -1;
    if ( name.equals("agens") || name.equals("agent") || name.equals("subject") || name.equals("subj") ) return -4 - ordinal;
    if ( name.equals("patiens") || name.equals("patient") || name.equals("theme") || name.equals("object") || name.equals("obj") ) return 4 + ordinal;
    if ( name.equals("recipiens") || name.equals("recipient") || name.equals("beneficiens") || name.equals("beneficiary") ) return 5 + ordinal;
    if ( name.equals("instrument") || name.equals("middel") ) return 0 - ordinal;
    if ( name.equals("locatief") || name.equals("locative") || name.equals("plaats") || name.equals("location") ) return 2 + ordinal;
    if ( name.equals("tijd") || name.equals("time") || name.equals("tempus") ) return 1 + ordinal;

    int side = role == null ? PreferredSide.CENTER : role.getPreferredSide();
    if ( side == PreferredSide.LEFT ) return -4 - ordinal;
    if ( side == PreferredSide.RIGHT ) return 4 + ordinal;
    if ( side == PreferredSide.DOWN ) return 1 + ordinal;
    return 0;
  }

  private static int roleBoxFanSlotX(int globalOrdinal, int totalCount)
  {
    if ( totalCount <= 1 ) return 0;

    int radius = Math.max(2, totalCount / 2);
    if ( (totalCount % 2) == 0 )
    {
      return -radius + globalOrdinal;
    }
    return -(totalCount / 2) + globalOrdinal;
  }

  private static int functionalVerticalStep()
  {
    Properties props = roleBoxProperties();
    return Math.max(1, parseIntProperty(props.getProperty("functional.layout.verticalStep"), 1));
  }

  private static int functionalVerticalStart()
  {
    Properties props = roleBoxProperties();
    return Math.max(1, parseIntProperty(props.getProperty("functional.layout.verticalStart"), 2));
  }

  private static String functionalHorizontalSpread()
  {
    Properties props = roleBoxProperties();
    String value = props.getProperty("functional.layout.horizontalSpread", "compact");
    if ( value == null ) return "compact";
    value = value.trim().toLowerCase();
    if ( value.equals("wide") || value.equals("medium") || value.equals("compact") ) return value;
    return "compact";
  }

  private static int roundDiv(int numerator, int denominator)
  {
    if ( denominator == 0 ) return 0;
    if ( numerator >= 0 ) return (numerator + (denominator / 2)) / denominator;
    return -((-numerator + (denominator / 2)) / denominator);
  }

  private static int roleBoxRetryX(int preferredX, int attempt)
  {
    int step = (attempt + 1) / 2;
    if ( attempt % 2 == 1 ) return preferredX + step;
    return preferredX - step;
  }

  private static int roleBoxRetryY(int preferredY, int attempt)
  {
    /* Keep recovery compact.  Every two horizontal tries, go one row lower. */
    return preferredY + (attempt / 3);
  }

  private static boolean candidateNAryPlacementIsValidForRoleBox(LayoutBox childBox,
                                                                 int childShiftX,
                                                                 int childShiftY,
                                                                 Vector placed)
  {
    if ( childBox.containsShifted(0, 0, childShiftX, childShiftY) )
    {
      return false;
    }

    if ( !roleBoxHasAngularClearance(childShiftX, childShiftY, placed) )
    {
      return false;
    }

    for ( int i=0; i<placed.size(); i++ )
    {
      ShiftedLayoutBox other = (ShiftedLayoutBox)placed.elementAt(i);
      if ( other.box.boundsIntersectShifted(childBox, other.shiftX, other.shiftY,
                                            childShiftX, childShiftY) )
      {
        return false;
      }
      if ( other.box.intersectsShifted(childBox, other.shiftX, other.shiftY,
                                       childShiftX, childShiftY) )
      {
        return false;
      }
    }
    return true;
  }

  private static boolean roleBoxHasAngularClearance(int shiftX, int shiftY, Vector placed)
  {
    if ( shiftX == 0 && shiftY == 0 ) return false;
    final int minNodeDistance = 2;
    for ( int i=0; i<placed.size(); i++ )
    {
      ShiftedLayoutBox other = (ShiftedLayoutBox)placed.elementAt(i);
      int ox = other.shiftX;
      int oy = other.shiftY;
      int dx = shiftX - ox;
      int dy = shiftY - oy;
      if ( Math.abs(dx) + Math.abs(dy) < minNodeDistance )
      {
        return false;
      }

      long cross = Math.abs((long)shiftX * (long)oy - (long)shiftY * (long)ox);
      long dot = (long)shiftX * (long)ox + (long)shiftY * (long)oy;
      if ( dot > 0 )
      {
        /* Compact FT fan: only reject truly same/nearly-same directions. */
        long scale = Math.max(1L, (Math.abs(shiftX) + Math.abs(shiftY) + Math.abs(ox) + Math.abs(oy)) / 8L);
        if ( cross <= scale )
        {
          return false;
        }
      }
    }
    return true;
  }

  private static int naryChildSideFor(OpenGraphNodeEx root,
                                      OpenGraphNodeEx child,
                                      int index,
                                      int firstSide,
                                      String strategy)
  {
    if ( isRoleBoxLayout(strategy) )
    {
      LayoutRole role = layoutRoleFor(child);
      int side = role.getPreferredSide();
      if ( side == PreferredSide.LEFT ) return SIDE_LEFT;
      if ( side == PreferredSide.RIGHT ) return SIDE_RIGHT;
      /* center/down roles keep the projection rank but are stacked in the
       * alternating corridor system until the dedicated FG renderer is enabled.
       */
    }
    return (index % 2) == 0 ? firstSide : -firstSide;
  }

  private static boolean isTerminalNode(OpenGraphNodeEx node)
  {
    Vector children = node == null ? null : node.getChildren();
    return children == null || children.size() == 0;
  }

  private static boolean candidateNAryPlacementIsValid(LayoutBox childBox,
                                                       int childShiftX,
                                                       int childShiftY,
                                                       Vector placed)
  {
    if ( childShiftX == 0 )
    {
      return false;
    }
    if ( childBox.containsShifted(0, 0, childShiftX, childShiftY) )
    {
      return false;
    }

    for ( int i=0; i<placed.size(); i++ )
    {
      ShiftedLayoutBox other = (ShiftedLayoutBox)placed.elementAt(i);
      if ( other.box.boundsIntersectShifted(childBox, other.shiftX, other.shiftY,
                                            childShiftX, childShiftY) )
      {
        return false;
      }
      if ( other.box.intersectsShifted(childBox, other.shiftX, other.shiftY,
                                       childShiftX, childShiftY) )
      {
        return false;
      }
    }

    return true;
  }

  private static class ShiftedLayoutBox
  {
    LayoutBox box;
    int shiftX;
    int shiftY;

    ShiftedLayoutBox(LayoutBox box, int shiftX, int shiftY)
    {
      this.box = box;
      this.shiftX = shiftX;
      this.shiftY = shiftY;
    }
  }

  private static void nAryRule(OpenGraphNodeEx root, Vector children)
  {
    int currentY = 1;
    int minX = 0;
    int maxX = 0;
    int maxY = 0;
    int count = children == null ? 0 : children.size();
    int horizontalStep = ROOT_SIDE_GAP + 1;

    for ( int i=0; i<count; i++ )
    {
      OpenGraphNodeEx child = (OpenGraphNodeEx)children.elementAt(i);
      LayoutBox childBox = LayoutBox.fromSubtree(child);
      int childShiftX = ((2 * i) - (count - 1)) * horizontalStep;
      child.shiftX(childShiftX);
      child.shiftY(currentY);

      minX = Math.min(minX, boxMinX(child));
      maxX = Math.max(maxX, boxMaxX(child));
      maxY = Math.max(maxY, boxMaxY(child));

      currentY = currentY + childBox.maxY + 1;
    }

    root.setGridX(0);
    root.setGridY(0);
    root.setBoundWidth(maxX - minX);
    root.setBoundHeight(maxY);
    root.setBoundX(-minX);
    root.setBoundY(0);
  }

  /**
   * Dutch Language Tree layout convention.
   *
   * The first branching under the DS root is drawn one grid row longer.  This
   * reserves a normal grid row on the lexical axis for FIN/V2 between slot1
   * and the first real DS child row.  It replaces the earlier half-row FIN
   * placement and keeps surface-order positions visually independent of DS
   * node levels.
   */
  private static void lengthenFirstRootBranch(OpenGraphNodeEx root)
  {
    if ( root == null )
    {
      return;
    }

    Vector children = root.getChildren();
    if ( children == null || children.size() == 0 )
    {
      return;
    }

    for ( int i=0; i<children.size(); i++ )
    {
      shiftSubtreeY((OpenGraphNodeEx)children.elementAt(i), 1);
    }
    root.setBoundHeight(root.getBoundHeight() + 1);
  }

  private static void shiftSubtreeX(OpenGraphNodeEx node, int shiftX)
  {
    if ( node == null )
    {
      return;
    }

    node.shiftX(shiftX);
    Vector children = node.getChildren();
    for ( int i=0; i<children.size(); i++ )
    {
      shiftSubtreeX((OpenGraphNodeEx)children.elementAt(i), shiftX);
    }
  }

  private static void shiftSubtreeY(OpenGraphNodeEx node, int shiftY)
  {
    if ( node == null )
    {
      return;
    }

    node.shiftY(shiftY);
    Vector children = node.getChildren();
    for ( int i=0; i<children.size(); i++ )
    {
      shiftSubtreeY((OpenGraphNodeEx)children.elementAt(i), shiftY);
    }
  }

  private static void otherRule(OpenGraphNodeEx root, OpenGraphNodeEx child)
  {
    child.shiftY(1);

    int minX = Math.min(0, boxMinX(child));
    int maxX = Math.max(0, boxMaxX(child));
    int maxY = Math.max(0, boxMaxY(child));

    root.setGridX(0);
    root.setGridY(0);
    root.setBoundWidth(maxX - minX);
    root.setBoundHeight(maxY);
    root.setBoundX(-minX);
    root.setBoundY(0);
  }

  private static int leftExtent(OpenGraphNodeEx node)
  {
    return node.getBoundX();
  }

  private static int rightExtent(OpenGraphNodeEx node)
  {
    return node.getBoundWidth() - node.getBoundX();
  }

  private static int boxMinX(OpenGraphNodeEx node)
  {
    return node.getGridX() - node.getBoundX();
  }

  private static int boxMaxX(OpenGraphNodeEx node)
  {
    return node.getGridX() + node.getBoundWidth() - node.getBoundX();
  }

  private static int boxMaxY(OpenGraphNodeEx node)
  {
    return node.getGridY() + node.getBoundHeight();
  }

  private static void correctGridCoordinates(OpenGraphNodeEx root, int shiftX, int shiftY)
  {
    root.shiftX(shiftX);
    root.shiftY(shiftY);

    Vector children = root.getChildren();
    for ( int i=0; i<children.size(); i++ )
    {
      correctGridCoordinates((OpenGraphNodeEx)children.elementAt(i),
                             root.getGridX(), root.getGridY());
    }
  }
}
