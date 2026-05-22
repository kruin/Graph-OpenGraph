package graphStructure.mementos;

import graphStructure.Graph;

public class GridDisplayWindowMemento implements MementoInterface
{
  private static final int NO_TYPE = 0;
  private static final int CHANGE_GRID_DISPLAY_WINDOW_TYPE = 1;

  private final Graph target;
  private int originX;
  private int originY;
  private int displayRows;
  private int displayCols;
  private int type;

  private GridDisplayWindowMemento(Graph target)
  {
    this.target = target;
    originX = target.getGridOriginX();
    originY = target.getGridOriginY();
    displayRows = target.getGridDisplayRows();
    displayCols = target.getGridDisplayCols();
    type = NO_TYPE;
  }

  public static GridDisplayWindowMemento createGridDisplayWindowMemento(Graph target)
  {
    GridDisplayWindowMemento toReturn = new GridDisplayWindowMemento(target);
    toReturn.type = CHANGE_GRID_DISPLAY_WINDOW_TYPE;
    return toReturn;
  }

  public void apply(Graph graph)
  {
    if ( type != CHANGE_GRID_DISPLAY_WINDOW_TYPE )
    {
      return;
    }

    int tempOriginX = target.getGridOriginX();
    int tempOriginY = target.getGridOriginY();
    int tempDisplayRows = target.getGridDisplayRows();
    int tempDisplayCols = target.getGridDisplayCols();

    target.setGridDisplayWindow(originX, originY, displayRows, displayCols);

    originX = tempOriginX;
    originY = tempOriginY;
    displayRows = tempDisplayRows;
    displayCols = tempDisplayCols;
  }

  public String toString()
  {
    if ( type == CHANGE_GRID_DISPLAY_WINDOW_TYPE )
    {
      return "ChangeGridDisplayWindow: " + target + " " + originX + " " + originY +
             " " + displayRows + " " + displayCols;
    }
    return "Unknown: " + target;
  }

  public boolean isUseless()
  {
    return false;
  }
}
