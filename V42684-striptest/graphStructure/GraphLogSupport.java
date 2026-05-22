package graphStructure;

import java.util.Vector;

final class GraphLogSupport
{
  private GraphLogSupport() {}

  static String getLogString(Vector logEntries)
  {
    String returnString = "";
    for ( int i=0; i<logEntries.size(); i++ )
    {
      returnString += "Log Entry " + (i+1) +
                      "---------------------" +
                      ((LogEntry)logEntries.elementAt(i)).infoString() +
                      "---------------------\n\n";
    }
    return returnString;
  }

  static LogEntry attachLogEntry(Vector logEntries, LogEntry currentLogEntry, LogEntry newEntry)
  {
    if ( currentLogEntry == null )
    {
      logEntries.addElement(newEntry);
    }
    else
    {
      currentLogEntry.addSubEntry(newEntry);
    }
    return newEntry;
  }

  static LogEntry stopLogEntry(LogEntry logEntry)
  {
    logEntry.updateTimeTaken(System.currentTimeMillis());
    return logEntry.getParentEntry();
  }
}
