package graphStructure;

public final class ProjectedLabel
{
  public String text;
  public LabelTransferMode mode;
  public String sourceKind;

  public ProjectedLabel()
  {
    text = "";
    mode = LabelTransferMode.COPY;
    sourceKind = "source_node_label";
  }
}
