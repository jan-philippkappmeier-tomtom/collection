
package org.zetool.container.collection;

import org.zetool.container.collection.ArraySet;
import org.zetool.container.mapping.Identifiable;

/**
 * A shifted arrayset contains object that are identifiable, but that do not
 * necessarily start at index 0. E.g., a shifted array set can store all objects
 * with ids 100 to 200, without using space 0 to 99.
 * @param <E>

* @author Jan-Philipp Kappmeier
 */
public class ShiftedArraySet<E extends Identifiable> extends ArraySet<E> {
  final int offset;
  
	/**
	 * Constructs an {@code ArraySet} containing the elements in the
	 * given array. The elements must be stored in the field corresponding
	 * to their ID, elsewise an {@code IllegalArgumentException}
	 * is thrown.
	 * @param elements an array with elements that shall be contained in this {@code ArraySet}.
   * @param offset the index which is the first element
	 */
	public ShiftedArraySet( E[] elements, int offset ) {
    super( elements );
    this.offset = offset;
	}

	/**
	 * Constructs an {@code ArraySet},
	 * typed to {@code elementType}, but containing no elements and
	 * with zero capacity.
	 * The capacity must be set by {@code public void setCapacity(int capacity)}
	 * before storing elements in the {@code ArraySet}.
	 * @param elementType the type the elements in this {@code ArraySet}
	 *                    will have.
   * @param offset the index which is the first element
	 */
	public ShiftedArraySet( Class<E> elementType, int offset ) {
		this( elementType, 0, offset );
	}

//	public ShiftedArraySet( ArraySet<E> s ) {
//    super( s );
//	}

	/**
	 * Constructs an {@code ArraySet} containing no elements, but typed
	 * to {@code elementType} and with a capacity to store elements
	 * with IDs from zero to {@code capacity-1}.
	 * @param elementType the type the elements in this {@code ArraySet}
	 *                    will have.
	 * @param capacity the highest possible ID for elements plus one.
   * @param offset the index which is the first element
	 */
	@SuppressWarnings( "unchecked" )
	public ShiftedArraySet( Class<E> elementType, int capacity, int offset ) {
    super( elementType, capacity );
    this.offset = offset;
  }

	@Override
	public boolean add( E element ) {
    return add( element, element.id() - offset );
	}

  @Override
	public void remove( E element ) {
    remove( element.id() - offset );
	}

	@Override
	public boolean contains( E element ) {
    return contains( element, element.id() - offset );
	}

	@Override
	public E predecessor( E element ) {
		if( contains( element ) ) {
			int index = element.id() - offset - 1;
      return predecessor( index );
		}
		return null;
	}

	@Override
	public E successor( E element ) {
		if( contains( element ) ) {
			int index = element.id() - offset;
      return successor( index );
    }
		return null;
	}
}
