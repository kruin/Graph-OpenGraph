package graphStructure;

public final class ProjectionSpec
{
  public boolean enabled;
  public ProjectionDirection direction;
  public ProjectionAttachRule attachTo;
  public LabelTransferMode labelTransfer;
  public String labelSource;

  public ProjectionSpec()
  {
    enabled = false;
    direction = ProjectionDirection.LEFT;
    attachTo = ProjectionAttachRule.TERMINAL_NODE;
    labelTransfer = LabelTransferMode.COPY;
    labelSource = "source_node_label";
  }

  public ProjectionSpec(ProjectionSpec other)
  {
    this();
    if ( other != null )
    {
      enabled = other.enabled;
      direction = other.direction;
      attachTo = other.attachTo;
      labelTransfer = other.labelTransfer;
      labelSource = other.labelSource;
    }
  }
}
