
package org.zetool.container.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple specialized iterator that only returns one element.
 * @author Jan-Philipp Kappmeier
 */
public class SingleIterator<E> implements Iterator<E> {
  /** The element that should be iterated. */
  private E e;

  /**
   * Initializes the single element iterator with the single element.
   * @param e the single element that is iterated over
   */
  public SingleIterator( E e ) {
    this.e = e;
  }

  @Override
  public boolean hasNext() {
    return e != null;
  }

  @Override
  public E next() {
    if( e == null ) {
      throw new NoSuchElementException( "Single element already taken out." );
    }
    E ret = e;
    e = null;
    return ret;
  }
}
