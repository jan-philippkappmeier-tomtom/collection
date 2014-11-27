/* zet evacuation tool copyright (c) 2007-14 zet evacuation team
 *
 * This program is free software; you can redistribute it and/or
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.tu_berlin.coga.container.mapping;

import de.tu_berlin.coga.container.localization.CollectionLocalization;
import java.util.Objects;

/**
 * The {@code IdentifiableObjectIdentifiableMapping} class represents a mapping from a set
 * of identifiable objects to arbitrary values. An object is considered
 * identifiable if and only if it implements the interface {@link Identifiable}.
 * An array is used for storing the mapping internally. The ID of an
 * identifiable object determines the position in the array where the object's
 * value is stored. This approach allows a very efficient implementation of
 * mappings. It is recommended that the objects' IDs are from the set
 * {@code {0,...,#objects-1}} to ensure the best performance. For mappings of
 * objects to integers the use of the specialized class
 * {@link IdentifiableIntegerMapping} is advised.
 *
 * @param <D> the type of this mapping's domain, i.e. the type of the objects
 * that are to be mapped to values. {@code D} must implement
 * {@link Identifiable}.
 * @param <R> the type of this mapping's range, i.e. the type of the values the
 * objects can be mapped to.
 */
public class IdentifiableObjectMapping<D extends Identifiable, R> implements Cloneable, IdentifiableMapping<D,R> {
	private static final int STRING_BREAK_COUNT = 10;

	/** The array storing all associations. Must not be {@code null}. */
	protected Object[] mapping;

	public IdentifiableObjectMapping( IdentifiableObjectMapping<D,R> mapping ) {
		this.mapping = Objects.requireNonNull( mapping.mapping, "Mapping must not be null!" );
	}

  /**
   * Initializes the mapping for a collection of elements in the domain. The
   * runtime is O(#domain_elements), which is linear in the size. All elements are
   * checked for their respective ids and thus it is not necessary that the ids
   * are a consecutive list.
   * @param domain the elements of the domain that are to be stored
   * @throws IllegalArgumentException if a primitive type is given, as they cannot be used generically
   */
  public IdentifiableObjectMapping( Iterable<D> domain ) throws IllegalArgumentException {
    int maxId = -1;
		for( D x : domain ) {
			if( maxId < x.id() ) {
				maxId = x.id();
			}
		}
    this.mapping = new Object[maxId + 1];
	}

	/**
	 * Constructs a new {@code IdentifiableObjectIdentifiableMapping} object with a specified
	 * initial mapping and the specified type for values. The default association
	 * for an object is as specified by {@code mapping}. Runtime O(1).
	 *
	 * @param mapping the array defining the initial mapping.
	 */
	protected IdentifiableObjectMapping( R[] mapping ) throws IllegalArgumentException {
    this.mapping = Objects.requireNonNull( mapping, CollectionLocalization.LOC.getString( "zet.collection.MappingNullException" ) );
	}

	/**
	 * Constructs a new {@code IdentifiableObjectIdentifiableMapping} object with a domain of
	 * the specified size and the specified type for values. The default
	 * association for an object is {@code null}. Runtime O(domainSize).
	 *
	 * @param domainSize the initial size of the domain.
	 */
	public IdentifiableObjectMapping( int domainSize ) throws IllegalArgumentException {
    this.mapping = new Object[domainSize];
	}

	/**
	 * Returns the value associated with {@code identifiableObject} in this
	 * mapping. Runtime O(1).
	 *
	 * @param identifiableObject the object for which the associated value is to
	 * be returned.
	 * @return the value associated with {@code identifiableObject} in this
	 * mapping.
	 * @exception ArrayIndexOutOfBoundsException if {@code identifiableObject}'s
	 * ID is less then 0 or greater equal than the size of the domain.
	 * @see #getDomainSize
	 * @see #setDomainSize
	 * @see Identifiable
	 */
	@Override
  @SuppressWarnings("unchecked")
	public R get( D identifiableObject ) {
		return (R)mapping[identifiableObject.id()];
	}

	/**
	 * Associates {@code identifiableObject} with {@code value} in this mapping.
	 * Any previously made association for {@code identifiableObject} is lost in
	 * the process. Calling {@code set} with an {@code identifiableObject} whose
	 * ID is greater equal than the current size of the domain will automatically
	 * increase the size of the domain to accommodate {@code identifiableObject}'s
	 * ID. Runtime O(1).
	 *
	 * @param identifiableObject the object for which an association is to be
	 * made.
	 * @param value the value to be associated with {@code identifiableObject}.
	 * @see #getDomainSize
	 * @see #setDomainSize
	 * @see Identifiable
	 */
	@Override
	public void set( D identifiableObject, R value ) {
		if( identifiableObject.id() >= getDomainSize() ) {
			setDomainSize( identifiableObject.id() + 1 );
		}
		mapping[identifiableObject.id()] = value;
	}

	/**
	 * Returns the size of this mapping's domain. Associations of objects and
	 * values can only be made for objects with an ID between {@code 0} and
	 * {@code getDomainSize()-1}. Runtime O(1).
	 *
	 * @return the size of this mapping's domain.
	 */
  @Override
	public int getDomainSize() {
		return mapping.length;
	}

	/**
	 * Sets the size of this mapping's domain to {@code value}. Runtime O(value).
   * The domain size is changed without any checks, e.g. if the size is smaller
   * than before, the elements are deleted.
	 *
	 * @param value the new size of this mapping's domain.
	 * @exception NegativeArraySizeException if {@code value} is negative.
	 */
  @Override
	public void setDomainSize( int value ) {
		Object[] newMapping = new Object[value];
    System.arraycopy(
						mapping, 0,
						newMapping, 0,
						Math.min( mapping.length, newMapping.length ) );
		mapping = newMapping;
	}

	/**
	 * Checks whether {@code identifiableObject} has been defined in this mapping,
	 * i.e. whether its ID fits the size of the domain and whether the it is
	 * associated with an object other than {@code null}. Runtime O(1).
	 *
	 * @param identifiableObject the object to check for whether it is defined in
	 * this mapping.
	 * @return true if {@code get(identifiableObject)} would return a
	 * non-{@code null} value and false otherwise.
	 * @exception NullPointerException if {@code identifiableObject} is null.
	 */
  @Override
	public boolean isDefinedFor( D identifiableObject ) {
		return 0 <= identifiableObject.id()
						&& identifiableObject.id() < getDomainSize()
						&& mapping[identifiableObject.id()] != null;
	}

	/**
	 * Creates a copy of this mapping. Runtime O(number of values).
	 *
	 * @return a copy of this mapping.
	 */
	@Override
	public IdentifiableObjectMapping<D, R> clone() {
    @SuppressWarnings("unchecked")
    R[] newMapping = (R[])new Object[mapping.length];
		System.arraycopy( mapping, 0, newMapping, 0, mapping.length );
		return new IdentifiableObjectMapping<>( newMapping );
	}

	/**
	 * Compares this mapping to the specified object. The result is true if and
	 * only if the argument is not null and is an
	 * {@code IdentifiableObjectIdentifiableMapping} object which has an domain of equal type
	 * and size and makes exactly the same object - value associations. Runtime
	 * O(size of the domain).
	 *
	 * @param o the object this mapping is to be compared with.
	 * @return {@code true} if the given object represents an
	 * {@code IdentifiableObjectIdentifiableMapping} equivalent to this mapping, {@code false}
	 * otherwise.
	 */
	@Override
	public boolean equals( Object o ) {
    if( o == this ) {
      return true;
    }
		if( o == null || !(o instanceof IdentifiableObjectMapping) ) {
			return false;
		}
		IdentifiableObjectMapping<?,?> iom = (IdentifiableObjectMapping<?,?>)o;
		if( iom.mapping.length != mapping.length ) {
			return false;
		}
		for( int i = 0; i < mapping.length; i++ ) {
			if( (iom.mapping[i] == null && mapping[i] != null) || ( !iom.mapping[i].equals( mapping[i] ) ) ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a hash code for this {@code IdentifiableObjectIdentifiableMapping}. Runtime
	 * O(size of the domain).
	 *
	 * @return the sum of the hash codes of the values associated with objects in
	 * this mapping.
	 */
	@Override
	public int hashCode() {
		int sum = 0;
    for( Object mapping1 : mapping ) {
      if( mapping1 == null ) {
        continue;
      }
      sum += mapping1.hashCode();
    }
		return sum;
	}

	/**
	 * Return a {@code String} object representing this mapping. The returned
	 * {@code String} will consist of a list of all object - value associations
	 * made in this mapping. Note that object - null associations will be omitted
	 * in this representation. Runtime O(size of the domain).
	 *
	 * @return a string representation of this mapping.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( '[' );
		int counter = 0;
		for( int i = 0; i < mapping.length; i++ ) {
			if( mapping[i] == null ) { continue; }
			if( counter == STRING_BREAK_COUNT ) {
				counter = 0;
				builder.append( "\n" );
			}
			builder.append( i );
			builder.append( " = " );
			builder.append( mapping[i] );
			if( i < mapping.length - 1 ) {
				builder.append( ", " );
			}
			counter++;
		}
		builder.append( ']' );
		return builder.toString();
	}
}
