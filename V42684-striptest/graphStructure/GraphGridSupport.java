package graphStructure;

import java.awt.Color;
import java.awt.Graphics2D;

final class GraphGridSupport
{
  private GraphGridSupport() {}

  static int getRowHeight(int numRows, int height)
  {
    if ( numRows <= 1 )
    {
      numRows = 2;
    }
    return Math.max(1, height / (numRows-1));
  }

  static int getColWidth(int numCols, int width)
  {
    if ( numCols <= 1 )
    {
      numCols = 2;
    }
    return Math.max(1, width / (numCols-1));
  }

  static int[] normalizeGrid(int numRows, int rowHeight, int numCols, int colWidth)
  {
    if ( numRows <= 1 )
    {
      numRows = 2;
    }
    if ( numCols <= 1 )
    {
      numCols = 2;
    }
    return new int[] { numRows, Math.max(2, rowHeight), numCols, Math.max(2, colWidth) };
  }

  static int[] normalizeGridArea(int numRows, int height, int numCols, int width)
  {
    if ( numRows <= 1 )
    {
      numRows = 2;
    }
    if ( numCols <= 1 )
    {
      numCols = 2;
    }
    return new int[] { numRows, Math.max(2, getRowHeight(numRows, height)),
                       numCols, Math.max(2, getColWidth(numCols, width)) };
  }

  static int normalizeDisplayCount(int count)
  {
    return Math.max(2, count);
  }

  static int defaultDisplayCount(int gridCount)
  {
    return Math.max(2, gridCount);
  }

  static int resolveDisplayCount(int displayCount, int gridCount)
  {
    return displayCount < 2 ? Math.max(2, gridCount) : displayCount;
  }

  static int getDisplayWidth(int displayCols, int gridColWidth)
  {
    return (displayCols-1) * gridColWidth;
  }

  static int getDisplayHeight(int displayRows, int gridRowHeight)
  {
    return (displayRows-1) * gridRowHeight;
  }

  static boolean shouldDrawGrid(boolean drawGrid, int gridRows, int gridCols,
                                int gridRowHeight, int gridColWidth)
  {
    return drawGrid &&
           gridRows >= 2 &&
           gridCols >= 2 &&
           gridRowHeight >= 2 &&
           gridColWidth >= 2;
  }

  static void drawGrid(Graphics2D g2, boolean drawGrid, int drawRows, int drawCols,
                       int gridOriginX, int gridOriginY, int gridColWidth,
                       int gridRowHeight, int xOffset, int yOffset)
  {
    if ( !drawGrid )
    {
      return;
    }

    int startX = gridOriginX + xOffset;
    int startY = gridOriginY + yOffset;
    int endX = startX + (drawCols-1) * gridColWidth;
    int endY = startY + (drawRows-1) * gridRowHeight;

    g2.setColor(Color.gray);
    for ( int i=0; i<drawRows; i++ )
    {
      int y = startY + i * gridRowHeight;
      g2.drawLine(startX, y, endX, y);
    }
    for ( int i=0; i<drawCols; i++ )
    {
      int x = startX + i * gridColWidth;
      g2.drawLine(x, startY, x, endY);
    }
  }

  static Location getClosestGridLocation(Location location, int gridOriginX, int gridOriginY,
                                         int gridRowHeight, int gridColWidth,
                                         int safeGridRows, int safeGridCols)
  {
    int safeRowHeight = Math.max(2, gridRowHeight);
    int safeColWidth = Math.max(2, gridColWidth);

    int row = (int)Math.round((location.doubleY() - gridOriginY) / safeRowHeight);
    int col = (int)Math.round((location.doubleX() - gridOriginX) / safeColWidth);

    if ( row < 0 )
    {
      row = 0;
    }
    if ( col < 0 )
    {
      col = 0;
    }
    if ( row > safeGridRows-1 )
    {
      row = safeGridRows-1;
    }
    if ( col > safeGridCols-1 )
    {
      col = safeGridCols-1;
    }
    return new Location(gridOriginX + col * safeColWidth,
                        gridOriginY + row * safeRowHeight);
  }

  static boolean isOnGrid(Location location, int gridOriginX, int gridOriginY,
                          int gridColWidth, int gridRowHeight)
  {
    if ( gridColWidth < 2 || gridRowHeight < 2 )
    {
      return false;
    }
    return (location.intX() - gridOriginX) % gridColWidth == 0 &&
           (location.intY() - gridOriginY) % gridRowHeight == 0;
  }
}
