
package de.tu_berlin.coga.container.util;

import java.util.Iterator;

/**
 * A simple an specialized iterator that only returns one element.
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
    E ret = e;
    e = null;
    return ret;
  }
}
