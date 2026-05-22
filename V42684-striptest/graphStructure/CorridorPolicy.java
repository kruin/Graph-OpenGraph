package graphStructure;

/**
 * Internal preparation for role-box layout.  Corridor policy is only a geometry
 * hint; projection/role order remains authoritative.
 */
public final class CorridorPolicy
{
  public static final int AUTO = 0;
  public static final int INNER = 1;
  public static final int OUTER = 2;
  public static final int STACK = 3;

  private CorridorPolicy() {}

  public static int parse(String value, int fallback)
  {
    if ( value == null ) return fallback;
    String v = value.trim().toLowerCase();
    if ( v.equals("auto") ) return AUTO;
    if ( v.equals("inner") || v.equals("binnen") ) return INNER;
    if ( v.equals("outer") || v.equals("buiten") ) return OUTER;
    if ( v.equals("stack") || v.equals("stacked") || v.equals("stapel") ) return STACK;
    return fallback;
  }
}
