
package de.tu_berlin.coga.container.collection;

import de.tu_berlin.coga.container.mapping.Identifiable;
import de.tu_berlin.coga.container.util.IteratorIterator;
import java.util.Iterator;

/**
 * @param <E> the type of the elements that shall be stored in this IdentifiableCollection.
 *          {@code E} must implement {@link Identifiable}.

* @author Jan-Philipp Kappmeier
 */
public class CombinedCollection<E extends Identifiable> implements IdentifiableCollection<E> {
  private IdentifiableCollection<E> one;
  private IdentifiableCollection<E> two;
  private int firstNew;

  public CombinedCollection( IdentifiableCollection<E> one, IdentifiableCollection<E> two ) {
    this.one = one;
    this.two = two;
    firstNew = one.size();
  }

  private IdentifiableCollection<E> col( E element ) {
    return element.id() < firstNew ? one : two;
  }

  private IdentifiableCollection<E> col( int id ) {
    return id < firstNew ? one : two;
  }


  @Override
  public boolean add( E element ) {
    return col( element ).add( element );
  }

  @Override
  public void remove( E element ) {
    col( element ).remove( element );
  }

  @Override
  public E removeLast() {
    return two.removeLast();
  }

  @Override
  public boolean contains( E element ) {
    return col( element ).contains( element );
  }

  @Override
  public boolean empty() {
    return false;
  }

  @Override
  public int size() {
    return firstNew + two.size();
  }

  @Override
  public E get( int id ) {
    return col( id ).get( id );
  }

  @Override
  public E first() {
    return one.first();
  }

  @Override
  public E last() {
    return two.last();
  }

  @Override
  public E predecessor( E element ) {
    if( element.id() == firstNew ) {
      return one.last();
    } else {
      return col( element ).predecessor( element );
    }
  }

  @Override
  public E successor( E element ) {
    if( element.id() == firstNew-1 ) {
      return two.first();
    } else {
      return col( element ).successor( element );
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public Iterator<E> iterator() {
    return new IteratorIterator<>( one.iterator(), two.iterator() );
  }
}
