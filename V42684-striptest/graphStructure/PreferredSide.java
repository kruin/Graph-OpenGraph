package graphStructure;

/**
 * Internal preparation for projection/role-driven n-ary box layout.
 * This is deliberately not exposed in the GUI yet.
 */
public final class PreferredSide
{
  public static final int LEFT = -1;
  public static final int CENTER = 0;
  public static final int RIGHT = 1;
  public static final int DOWN = 2;

  private PreferredSide() {}

  public static int parse(String value, int fallback)
  {
    if ( value == null ) return fallback;
    String v = value.trim().toLowerCase();
    if ( v.equals("left") || v.equals("links") || v.equals("l") ) return LEFT;
    if ( v.equals("right") || v.equals("rechts") || v.equals("r") ) return RIGHT;
    if ( v.equals("center") || v.equals("centre") || v.equals("midden") || v.equals("c") ) return CENTER;
    if ( v.equals("down") || v.equals("onder") || v.equals("beneden") || v.equals("d") ) return DOWN;
    return fallback;
  }
}
