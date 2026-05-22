package graphStructure;

public final class ProjectionLayoutConfig
{
  public GridResizeMode gridResizeMode = GridResizeMode.FIXED;

  public boolean includeSourceStructureInBounds = true;
  public boolean includeProjectionNodesInBounds = false;
  public boolean includeProjectionLabelsInBounds = false;

  public boolean renderMarginEnabled = true;
  public boolean renderMarginIncludeProjectionNodes = true;
  public boolean renderMarginIncludeProjectionLabels = true;

  public ProjectionSpec lex = new ProjectionSpec();
  public ProjectionSpec log = new ProjectionSpec();
  public ProjectionSpec syn = new ProjectionSpec();

  public DirectionSpec left = new DirectionSpec();
  public DirectionSpec right = new DirectionSpec();
  public DirectionSpec up = new DirectionSpec();
  public DirectionSpec down = new DirectionSpec();

  public int projectionOffsetInGridUnits = 1;
  public int labelGapFromProjectionNodeInGridUnits = 0;

  public ProjectionLayoutConfig()
  {
    lex.enabled = true;
    lex.direction = ProjectionDirection.LEFT;
    lex.attachTo = ProjectionAttachRule.TERMINAL_NODE;
    lex.labelTransfer = LabelTransferMode.COPY;
    lex.labelSource = "source_node_label";

    log.enabled = false;
    log.direction = ProjectionDirection.DOWN;
    log.attachTo = ProjectionAttachRule.BRANCHING_NODE;
    log.labelTransfer = LabelTransferMode.COPY;
    log.labelSource = "source_node_label";

    syn.enabled = true;
    syn.direction = ProjectionDirection.RIGHT;
    syn.attachTo = ProjectionAttachRule.BRANCHING_NODE;
    syn.labelTransfer = LabelTransferMode.COMPUTED;
    syn.labelSource = "ordered_child_categories";

    down.textOrientation = TextOrientation.DOWNWARD;
  }

  public ProjectionLayoutConfig(ProjectionLayoutConfig other)
  {
    this();
    if ( other != null )
    {
      gridResizeMode = other.gridResizeMode;
      includeSourceStructureInBounds = other.includeSourceStructureInBounds;
      includeProjectionNodesInBounds = other.includeProjectionNodesInBounds;
      includeProjectionLabelsInBounds = other.includeProjectionLabelsInBounds;
      renderMarginEnabled = other.renderMarginEnabled;
      renderMarginIncludeProjectionNodes = other.renderMarginIncludeProjectionNodes;
      renderMarginIncludeProjectionLabels = other.renderMarginIncludeProjectionLabels;
      lex = new ProjectionSpec(other.lex);
      log = new ProjectionSpec(other.log);
      syn = new ProjectionSpec(other.syn);
      left = new DirectionSpec(other.left);
      right = new DirectionSpec(other.right);
      up = new DirectionSpec(other.up);
      down = new DirectionSpec(other.down);
      projectionOffsetInGridUnits = other.projectionOffsetInGridUnits;
      labelGapFromProjectionNodeInGridUnits = other.labelGapFromProjectionNodeInGridUnits;
    }
  }
}
