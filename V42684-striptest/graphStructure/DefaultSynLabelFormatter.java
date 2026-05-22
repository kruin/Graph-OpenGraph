package graphStructure;

import java.util.Vector;

public final class DefaultSynLabelFormatter implements SynLabelFormatter
{
  public String format(Vector orderedChildCategories)
  {
    if ( orderedChildCategories == null || orderedChildCategories.isEmpty() )
    {
      return "";
    }

    Vector cleaned = new Vector();
    for ( int i=0; i<orderedChildCategories.size(); i++ )
    {
      Object value = orderedChildCategories.elementAt(i);
      String label = value == null ? "" : value.toString().trim();
      if ( label.length() > 0 )
      {
        cleaned.addElement(label);
      }
    }

    if ( cleaned.isEmpty() )
    {
      return "";
    }

    if ( cleaned.size() == 1 )
    {
      return cleaned.elementAt(0).toString();
    }

    StringBuffer buffer = new StringBuffer();
    for ( int i=0; i<cleaned.size(); i++ )
    {
      String label = cleaned.elementAt(i).toString();
      if ( buffer.length() > 0 )
      {
        buffer.append(", ");
      }
      buffer.append(label);
    }
    return buffer.toString();
  }
}
