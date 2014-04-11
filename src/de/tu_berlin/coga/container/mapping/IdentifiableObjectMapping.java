/* zet evacuation tool copyright (c) 2007-10 zet evacuation team
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

import ds.graph.GraphLocalization;
import java.lang.reflect.Array;

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

	/**
	 * The type of the elements stored in the {@code mapping} array. Since
	 * generics are compile-time only, we need to store its type explicitly in
	 * order to be able to increase the length of {@code mapping} array later on.
	 */
	protected Class<R> rangeType;
	/**
	 * The array storing all associations. Must not be {@code null}.
	 */
	protected R[] mapping;

	public IdentifiableObjectMapping( IdentifiableObjectMapping<D,R> mapping ) {
		this.rangeType = mapping.rangeType;
		this.mapping = mapping.mapping;
	}

	public IdentifiableObjectMapping( Iterable<D> domain, Class<R> rangeType ) {
		int maxId = -1;
		for( D x : domain ) {
			if( maxId < x.id() ) {
				maxId = x.id();
			}
		}
		this.mapping = (R[])Array.newInstance( rangeType, maxId + 1 );
		this.rangeType = rangeType;
	}

	/**
	 * Constructs a new {@code IdentifiableObjectIdentifiableMapping} object with a specified
	 * initial mapping and the specified type for values. The default association
	 * for an object is as specified by {@code mapping}. Runtime O(1).
	 *
	 * @param mapping the array defining the initial mapping.
	 * @param rangeType the type of the values.
	 * @exception IllegalArgumentException if rangeType is {@code Void.TYPE}.
	 * @exception NullPointerException if {@code mapping} or {@code rangeType} are
	 * null.
	 */
	protected IdentifiableObjectMapping( R[] mapping, Class<R> rangeType ) {
		if( rangeType == Void.TYPE ) {
			throw new IllegalArgumentException( GraphLocalization.loc.getString( "algo.ca.NotInitializedException" ) );
		}
		if( mapping == null || rangeType == null ) {
			throw new NullPointerException( GraphLocalization.loc.getString( "ds.Graph.ParametersNullException" ) );
		}
		this.mapping = mapping;
		this.rangeType = rangeType;
	}

	/**
	 * Constructs a new {@code IdentifiableObjectIdentifiableMapping} object with a domain of
	 * the specified size and the specified type for values. The default
	 * association for an object is {@code null}. Runtime O(domainSize).
	 *
	 * @param domainSize the initial size of the domain.
	 * @param rangeType the type of the values.
	 * @exception IllegalArgumentException if rangeType is {@code Void.TYPE}.
	 * @exception NegativeArraySizeException if {@code value} is negative.
	 * @exception NullPointerException if {@code rangeType} is null.
	 */
	@SuppressWarnings( "unchecked" )
	public IdentifiableObjectMapping( int domainSize, Class<R> rangeType ) {
		this.mapping = (R[])Array.newInstance( rangeType, domainSize );
		this.rangeType = rangeType;
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
	 * @exception NullPointerException if {@code identifiableObject} is null.
	 * @see #getDomainSize
	 * @see #setDomainSize
	 * @see Identifiable
	 */
	@Override
	public R get( D identifiableObject ) {
		return mapping[identifiableObject.id()];
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
	 * @exception ArrayIndexOutOfBoundsException if {@code identifiableObject}'s
	 * ID is less then 0.
	 * @exception NullPointerException if {@code identifiableObject} is null.
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
	public int getDomainSize() {
		return mapping.length;
	}

	/**
	 * Sets the size of this mapping's domain to {@code value}. Runtime O(value).
	 *
	 * @param value the new size of this mapping's domain.
	 * @exception NegativeArraySizeException if {@code value} is negative.
	 */
	@SuppressWarnings( "unchecked" )
	public void setDomainSize( int value ) {
		R[] newMapping = (R[])Array.newInstance( rangeType, value );
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
	@SuppressWarnings( "unchecked" )
	public IdentifiableObjectMapping<D, R> clone() {
		R[] newMapping = (R[])Array.newInstance( rangeType, mapping.length );
		System.arraycopy( mapping, 0, newMapping, 0, mapping.length );
		return new IdentifiableObjectMapping<>( newMapping, rangeType );
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
		if( o == null || !(o instanceof IdentifiableObjectMapping) ) {
			return false;
		}
		IdentifiableObjectMapping<?,?> iom = (IdentifiableObjectMapping<?,?>)o;
		if( iom.rangeType != rangeType || iom.mapping.length != mapping.length ) {
			return false;
		}
		for( int i = 0; i < mapping.length; i++ ) {
			if( iom.mapping[i] == null && mapping[i] != null ) {
				return false;
			} else if( !iom.mapping[i].equals( mapping[i] ) ) {
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
		for( int i = 0; i < mapping.length; i++ ) {
			if( mapping[i] == null ) {
				continue;
			}
			sum += mapping[i].hashCode();
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
