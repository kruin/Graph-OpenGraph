package graphStructure;

/**
 * Immutable role descriptor used by the future Functional Grammar role-box
 * layout.  v4.25.2 adds this as preparation only; no GUI setting is added.
 */
public final class LayoutRole
{
  private final String name;
  private final int rank;
  private final int preferredSide;
  private final int corridorPolicy;

  public LayoutRole(String name, int rank, int preferredSide, int corridorPolicy)
  {
    this.name = name == null ? "" : name;
    this.rank = rank;
    this.preferredSide = preferredSide;
    this.corridorPolicy = corridorPolicy;
  }

  public String getName()
  {
    return name;
  }

  public int getRank()
  {
    return rank;
  }

  public int getPreferredSide()
  {
    return preferredSide;
  }

  public int getCorridorPolicy()
  {
    return corridorPolicy;
  }

  public String toString()
  {
    return name + "[rank=" + rank + ",side=" + preferredSide + ",corridor=" + corridorPolicy + "]";
  }
}
