package graphStructure;

public final class DirectionSpec
{
  public LabelAnchor labelAnchor = LabelAnchor.GRID_OUTWARD;
  public TextOrientation textOrientation = TextOrientation.HORIZONTAL;
  public boolean nodeOutsideGrid = true;

  public DirectionSpec()
  {
  }

  public DirectionSpec(DirectionSpec other)
  {
    if ( other != null )
    {
      labelAnchor = other.labelAnchor;
      textOrientation = other.textOrientation;
      nodeOutsideGrid = other.nodeOutsideGrid;
    }
  }
}
