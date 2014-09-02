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

import java.util.Iterator;
import java.util.TreeSet;

/**
 * The {@code TimeObjectMapping} class represents a mapping from integers to
 * arbitrary. Values of this mapping's domain are referred to as time
 * henceforth, since time being the domain will be the primary application for
 * such mappings. Internally, the {@code TimeObjectMapping} is considered as
 * a step function. Consequently, the mapping is stored as a sorted collection
 * of step starts which is obviously sufficient to encode the mapping. The size
 * needed to encode an {@code TimeObjectMapping} is therefore linear in the
 * number of steps required. In order to access steps efficiently, a TreeSet is
 * used which in turn is based on a red-black tree. This allows the addition,
 * removal and search for steps in O(log (number of steps)) time. For mappings
 * of integers to integers see {@link IntegerIntegerMapping}.
 * @param <R>
 */
public class TimeObjectMapping<R> implements Cloneable, Iterable<TimeObjectPair<R>>, Mapping<Integer,R> {

	/** Stores the mapping internally. Must not be null. */
	protected TreeSet<TimeObjectPair<R>> mapping;

	/**
	 * Creates a new {@code IntegerObjectMapping} that is defined for all integer
	 * values. Initially, all integers are mapped to null. Runtime O(1).
	 */
	public TimeObjectMapping() {
		mapping = new TreeSet<>();
		set( Integer.MIN_VALUE, null );
	}

	/**
	 * Returns the value associated with the specified integer. Runtime O(log
	 * (number of steps)).
	 * @param time the integer for which the associated value is to be returned.
	 * @return the value associated with the specified integer.
	 */
	public R get( int time ) {
		TimeObjectPair<R> test = new TimeObjectPair<>( time, null );
		if( mapping.contains( test ) ) {
			return mapping.floor( test ).value();
		} else {
			return null;
		}
	}

	@Override
	public R get( Integer d ) {
		return get( d.intValue() );
	}

	public int getLastTime() {
		return mapping.last().time();
	}

	/**
	 * Maps the integer {@code time} to the object {@code value}. Runtime O(log
	 * (number of steps)).
	 * @param time the integer for which an association is to be made.
	 * @param value the value to be associated with the integer.
	 */
	public void set( int time, R value ) {
		TimeObjectPair<R> tip = new TimeObjectPair<>( time, value );
		TimeObjectPair<R> floor = mapping.floor( tip );
		if( floor != null && floor.equals( tip ) ) {
			floor.set( value );
		} else {
			mapping.add( tip );
		}
	}

	@Override
	public void set( Integer identifiableObject, R value ) {
		this.set( identifiableObject.intValue(), value );
	}

	/**
	 * Returns an iterator over the time - value mappings in this
	 * {@code TimeObjectMapping}. Runtime O(1).
	 * @return an iterator over the time - value mappings in this
	 * {@code TimeObjectMapping}.
	 */
	@Override
	public Iterator<TimeObjectPair<R>> iterator() {
		return mapping.iterator();
	}

	/**
	 * Returns a copy of this mapping. Runtime O(number of steps).
	 * @return a copy of this mapping.
	 */
	@Override
	public TimeObjectMapping<R> clone() {
		TimeObjectMapping<R> clone = new TimeObjectMapping<>();
		for( TimeObjectPair<R> tip : mapping ) {
			clone.set( tip.time(), tip.value() );
		}
		return clone;
	}

	/**
	 * Checks whether the specified object is equivalent to this mapping. This is
	 * the case if and only if the specified object is not {@code null</code},
	 * of type {@code TimeObjectMapping} and makes exactly the same
	 * time - value associations. Runtime O(number of steps).
	 * @param o the object to be compared with this mapping.
	 * @return true if the specified object is an {@code TimeObjectMapping} that is equivalent with this one,
   * {@code false} otherwise.
	 */
	@Override
	public boolean equals( Object o ) {
		return (o != null) && (o instanceof TimeObjectMapping) && ((TimeObjectMapping)o).mapping.equals( mapping );
	}

	/**
	 * Returns a hash code for this mapping. Runtime O(number of steps).
	 * @return the hash code of the underlying {@code TreeSet}.
	 */
	@Override
	public int hashCode() {
		return mapping.hashCode();
	}

	/**
	 * Returns a string representation of this mapping. Runtime O(number of
	 * steps).
	 * @return the string representation of the underlying {@code TreeSet}.
	 */
	@Override
	public String toString() {
		return mapping.toString();
	}

}
