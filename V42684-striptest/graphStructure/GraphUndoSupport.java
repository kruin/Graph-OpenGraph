package graphStructure;

import dataStructure.DoublyLinkedList;
import graphStructure.mementos.MementoGrouper;

final class GraphUndoSupport
{
  private GraphUndoSupport() {}

  static DoublyLinkedList createUndoList()
  {
    return new DoublyLinkedList();
  }

  static MementoGrouper newMemento(DoublyLinkedList mementos, MementoGrouper currentMemento,
                                   boolean trackUndos, String title)
  {
    if ( trackUndos )
    {
      if ( currentMemento != null )
      {
        mementos.enqueueAfterCurrent(currentMemento);
      }
      return new MementoGrouper(title);
    }
    return currentMemento;
  }

  static void renameMemento(MementoGrouper currentMemento, boolean trackUndos, String title)
  {
    if ( trackUndos )
    {
      currentMemento.setTitle(title);
    }
  }

  static MementoGrouper doneMemento(DoublyLinkedList mementos, MementoGrouper currentMemento,
                                    boolean trackUndos)
  {
    if ( trackUndos )
    {
      if ( currentMemento != null )
      {
        currentMemento.removeUselessMementos();
        if ( currentMemento.size() > 0 )
        {
          mementos.enqueueAfterCurrent(currentMemento);
        }
        return null;
      }
    }
    return currentMemento;
  }

  static void undoMemento(Graph graph, MementoGrouper currentMemento)
  {
    if ( currentMemento != null && currentMemento.size() > 0 )
    {
      currentMemento.apply(graph);
    }
  }

  static MementoGrouper abortMemento(boolean trackUndos, MementoGrouper currentMemento)
  {
    if ( trackUndos )
    {
      return null;
    }
    return currentMemento;
  }

  static boolean hasMoreUndos(DoublyLinkedList mementos)
  {
    return mementos.getCurrent() != null;
  }

  static void undo(Graph graph, DoublyLinkedList mementos, boolean trackUndos)
  {
    if ( trackUndos )
    {
      if ( mementos.getCurrent() != null )
      {
        ((MementoGrouper)mementos.getCurrent()).apply(graph);
        mementos.toPrev();
      }
      else
      {
        mementos.toHead();
      }
    }
  }

  static MementoGrouper peekUndo(DoublyLinkedList mementos)
  {
    return (MementoGrouper)mementos.getCurrent();
  }

  static boolean hasMoreRedos(DoublyLinkedList mementos)
  {
    return mementos.hasNext();
  }

  static void redo(Graph graph, DoublyLinkedList mementos, boolean trackUndos)
  {
    if ( trackUndos )
    {
      if ( mementos.hasNext() )
      {
        mementos.toNext();
        ((MementoGrouper)mementos.getCurrent()).apply(graph);
      }
      else
      {
        mementos.toTail();
      }
    }
  }

  static MementoGrouper peekRedo(DoublyLinkedList mementos)
  {
    if ( mementos.hasNext() )
    {
      mementos.toNext();
      MementoGrouper mg = (MementoGrouper)mementos.getCurrent();
      mementos.toPrev();
      return mg;
    }
    return null;
  }
}
