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
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

package org.zetool.container.mapping;

import org.zetool.container.localization.CollectionLocalization;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;


/**
 * The {@code TimeIntegerMapping} class represents a mapping from integers to
 * integers. It is a specialized version of {@code IntegerObjectMapping} made
 * for mappings from integers to integers. These mappings are particulary useful
 * for functions taking time as a parameter. Therefore values of this mapping's
 * domain are referred to as time henceforth. Internally, the
 * {@code TimeIntegerMapping} is considered as a step function. Consequently,
 * the mapping is stored as a sorted collection of step starts which is
 * obviously sufficient to encode the mapping. The size needed to encode an
 * {@code TimeIntegerMapping} is therefore linear in the number of steps
 * required. In order to access steps efficiently, a TreeSet is used which in
 * turn is based on a red-black tree. This allows the addition, removal and
 * search for steps in O(log (number of steps)) time. For mappings of integers
 * to arbitrary values see {@link IntegerObjectMapping}.
 */
public class TimeIntegerMapping implements Cloneable, Iterable<TimeIntegerPair>, Mapping<Integer,Integer> {

	/**
	 * Stores the mapping internally. Must not be null.
	 */
	private final TreeSet<TimeIntegerPair> mapping;
	/*
	 * Stores whether the mapping should be interpreted as piecewise constant or
	 * piecewise linear.
	 */
	private boolean linear;

	/**
	 * Creates a new {@code IntegerIntegerMapping} that is defined for all integer
	 * values. Initially, all integers are mapped to 0. Runtime O(1).
	 */
	public TimeIntegerMapping() {
		mapping = new TreeSet<>();
		set( Integer.MIN_VALUE, 0 );
		set( Integer.MAX_VALUE, 0 );
	}

	/**
	 * Creates a new {@code IntegerIntegerMapping} that is defined for all integer
	 * values. Initially, all integers are mapped to 0. Runtime O(1).
	 *
	 * @param linear if {@code true} this mapping is interpreted as piecewise
	 * linear by the get-function instead of piecewise constant.
	 */
	public TimeIntegerMapping( boolean linear ) {
		mapping = new TreeSet<>();
		this.linear = linear;
		set( Integer.MIN_VALUE, 0 );
		set( Integer.MAX_VALUE, 0 );
	}

	public boolean lessEqual( int start, int end, int value ) {
		for( int i = start; i < end; i++ ) {
			if( get( i ) > value ) {
				return false;
			}
		}
		return true;
	}

	public boolean greaterEqual( int start, int end, int value ) {
		for( int i = start; i < end; i++ ) {
			if( get( i ) < value ) {
				return false;
			}
		}
		return true;
	}

	public int getMaximumValue() {
		int maximum = Integer.MIN_VALUE;
		for( TimeIntegerPair tip : mapping ) {
			if( tip.value() > maximum ) {
				maximum = tip.value();
			}
		}
		return maximum;
	}

	public boolean isZero() {
		boolean result = true;
		for( TimeIntegerPair tip : mapping ) {
			if( tip.value() != 0 ) {
				result = false;
			}
		}
		return result;
	}

	public int getLastTimeWithNonZeroValue() {
		TimeIntegerPair tip = mapping.lower( new TimeIntegerPair( Integer.MAX_VALUE, 0 ) );
		if( tip.value() != 0 ) {
			throw new AssertionError( "This should not happen." );
		}
		if( tip.time() == Integer.MIN_VALUE ) {
			return 0;
		} else {
			return tip.time();
		}
	}

	/**
	 * Checks how the internally stored values are interpreted by the get method.
	 *
	 * @return {@code true} if this mapping is considered to represent a piecewise
	 * linear function, {@code false} if it is interpreted as piecewise constant.
	 */
	public boolean isPiecewiseLinear() {
		return linear;
	}

	/**
	 * Returns the integer associated with the specified value. Runtime O(log
	 * (number of steps)).
	 *
	 * @param time the value for which the associated integer is to be returned.
	 * @return the integer associated with the specified value.
	 */
	public int get( int time ) {
		return mapping.floor( new TimeIntegerPair( time, 0 ) ).value();
	}

	@Override
	public Integer get( Integer d ) {
		return this.get( d.intValue() );
	}


	/**
	 * Maps the integer {@code time} to the integer {@code value}. Runtime O(log
	 * (number of steps)).
	 *
	 * @param time the integer for which an association is to be made.
	 * @param value the value to be associated with the integer.
	 */
	public void set( int time, int value ) {
		TimeIntegerPair tip = new TimeIntegerPair( time, value );
		TimeIntegerPair floor = mapping.floor( tip );
		if( floor != null && floor.equals( tip ) ) {
			floor.set( value );
		} else {
			mapping.add( tip );
		}
	}

	@Override
	public void set( Integer identifiableObject, Integer value ) {
		this.set( identifiableObject.intValue(), value.intValue() );
	}

	/**
	 * A convenience method for increasing the value associated with a single
	 * integer. It is equivalent to {@code increase(time, time+1, amount)}.
	 * Runtime O(log (number of steps)).
	 *
	 * @param time the integer for which the associated value is to be increased.
	 * @param amount the amount by which the value is to be increased.
	 */
	public void increase( int time, int amount ) {
		increase( time, time + 1, amount );
	}

	/**
	 * A convenience method for increasing the values associated with a range of
	 * integers from {@code fromTime} (inclusively) to {@code toTime}
	 * (exclusively). It is equivalent to calling
	 * {@code set(time, get(time) + amount)} for all integers in the range defined
	 * above. Runtime O(log (number of steps) + number of steps changed).
	 *
	 * @param fromTime the first integer for which the associated value is to be
	 * increased.
	 * @param toTime the first integer after {@code fromTime} for which the
	 * associated value is <b>not</b> to be increased.
	 * @param amount the amount by which the values are to be increased.
	 * @exception IllegalArgumentException if {@code toTime} is less equal than
	 * {@code fromTime}.
	 */
	public void increase( int fromTime, int toTime, int amount ) {
		if( toTime <= fromTime ) {
      throw new IllegalArgumentException( CollectionLocalization.LOC.getString( "zet.collection.timeMapping.toTimeException" ) );
		}
		TimeIntegerPair from = new TimeIntegerPair( fromTime, 0 );
		TimeIntegerPair to = new TimeIntegerPair( toTime, 0 );
		TimeIntegerPair first = mapping.floor( from );
		int lastBefore = mapping.lower( to ).value();
		TimeIntegerPair last = mapping.ceiling( to );
		if( first.time() < fromTime ) {
			mapping.add( new TimeIntegerPair( fromTime, first.value() + amount ) );
		} else {
			first.set( first.value() + amount );
		}
		if( toTime < last.time() ) {
			mapping.add( new TimeIntegerPair( toTime, lastBefore ) );
		}
		NavigableSet<TimeIntegerPair> subSet = mapping.subSet( mapping.floor( from ), false, mapping.ceiling( to ), false );
		for( TimeIntegerPair tip : subSet ) {
			tip.set( tip.value() + amount );
		}
		if( mapping.lower( first ) != null && mapping.lower( first ).value() == first.value() ) {
			mapping.remove( first );
		}
	}

	/**
	 * A convenience method for decreasing the value associated with a single
	 * integer. It is equivalent to {@code increase(time, time+1, -amount)}.
	 * Runtime O(log (number of steps)).
	 *
	 * @param time the integer for which the associated value is to be decreased.
	 * @param amount the amount by which the value is to be decreased.
	 */
	public void decrease( int time, int amount ) {
		increase( time, -amount );
	}

	/**
	 * A convenience method for decreasing the values associated with a range of
	 * integers from {@code fromTime} (inclusively) to {@code toTime}
	 * (exclusively). It is equivalent to calling
	 * {@code increase(fromTime, toTime, -amount)}. defined above. Runtime O(log
	 * (number of steps) + number of steps changed).
	 *
	 * @param fromTime the first integer for which the associated value is to be
	 * decreased.
	 * @param toTime the first integer after {@code fromTime} for which the
	 * associated value is <b>not</b> to be decreased.
	 * @param amount the amount by which the values are to be decreased.
	 */
	public void decrease( int fromTime, int toTime, int amount ) {
		if( fromTime == toTime )
			System.err.println( "Are equal!" );
		increase( fromTime, toTime, -amount );
	}

	/**
	 * Adds the specified mapping to this mapping. {@code TimeIntegerMapping}
	 * objects are treated as mathematical functions Z -> Z for this purpose.
	 * Runtime(number of steps in {@code mapping}).
	 *
	 * @param mapping the mapping to be added to this mapping.
	 * @exception NullPointerException if mapping is null.
	 */
	public void addMapping( TimeIntegerMapping mapping ) {
		TimeIntegerPair last = null;
		for( TimeIntegerPair tip : mapping ) {
			if( last == null ) {
				last = tip;
				continue;
			}
			increase( last.time(), tip.time(), last.value() );
		}
	}

	/**
	 * Subtracts the specified mapping from this mapping.
	 * {@code TimeIntegerMapping} objects are treated as mathematical functions
	 * Z -> Z for this purpose. Runtime(number of steps in {@code mapping}).
	 *
	 * @param mapping the mapping to be subtracted to this mapping.
	 * @exception NullPointerException if mapping is null.
	 */
	public void subtractMapping( TimeIntegerMapping mapping ) {
		TimeIntegerPair last = null;
		for( TimeIntegerPair tip : mapping ) {
			if( last == null ) {
				last = tip;
				continue;
			}
			decrease( last.time(), tip.time(), last.value() );
		}
	}

	/**
	 * Computes the integral of this mapping. {@code TimeIntegerMapping} is
	 * considered a step function with step starts defined by its mapping for this
	 * purpose. The result (a piecewise linear function) is interpreted as an
	 * {@code TimeIntegerMapping} by defining a map for each start point of a
	 * linear segment. Runtime is O(number of steps).
	 *
	 * @return the integral of this mapping.
	 */
	public TimeIntegerMapping integral() {
		TimeIntegerMapping summatedMapping = new TimeIntegerMapping( true );
		int sum = 0;
		int lastTime = 0;
		int lastValue = 0;
		boolean first;
		for( TimeIntegerPair tip : mapping ) {
			first = (tip.time() == Integer.MIN_VALUE);
			if( !first ) {
				sum += (tip.time() - lastTime) * lastValue;
				summatedMapping.set( tip.time(), sum );
				lastTime = tip.time();
				lastValue = tip.value();
			}
		}
		return summatedMapping;
	}

	/**
	 * Returns an iterator over the time - integer mappings in this
	 * {@code TimeIntegerMapping}. Runtime O(1).
	 *
	 * @return an iterator over the time - integer mappings in this
	 * {@code TimeIntegerMapping}.
	 */
	@Override
	public Iterator<TimeIntegerPair> iterator() {
		return mapping.iterator();
	}

	/**
	 * Returns a copy of this mapping. Runtime O(number of steps).
	 *
	 * @return a copy of this mapping.
	 */
	@Override
	public TimeIntegerMapping clone() {
		TimeIntegerMapping clone = new TimeIntegerMapping();
		for( TimeIntegerPair tip : mapping ) {
			clone.set( tip.time(), tip.value() );
		}
		return clone;
	}

	/**
	 * Checks whether the specified object is equivalent to this mapping. This is
	 * the case if and only if the specified object is not {@code null</code,
 of type {@code TimeIntegerMapping} and makes exactly the same
 time - integer associations. Runtime O(number of steps).

 @param o the object to be compared with this mapping.
	 * @return true if the specified object is an null	 {@code TimeIntegerMapping} that is equivalent with this one,
     * {@code false}  otherwise.
	 */
	@Override
	public boolean equals( Object o ) {
		return (o != null) && (o instanceof TimeIntegerMapping) && ((TimeIntegerMapping)o).mapping.equals( mapping );
	}

	/**
	 * Returns a hash code for this mapping. Runtime O(number of steps).
	 *
	 * @return the hash code of the underlying {@code TreeSet}.
	 */
	@Override
	public int hashCode() {
		return mapping.hashCode();
	}

	/**
	 * Returns a string representation of this mapping. Runtime O(number of
	 * steps).
	 *
	 * @return the string representation of the underlying {@code TreeSet}.
	 */
	@Override
	public String toString() {
		String result = mapping.toString().replace( ", " + Integer.MAX_VALUE + " = 0", "" );
		result = result.replace( Integer.MIN_VALUE + " = 0, ", "" );
		if( result.equals( "[" + Integer.MIN_VALUE + " = 0]" ) ) {
			return "[0]";
		} else {
			return result;
		}
	}

	public int nextPositiveValue( int time ) {
		TimeIntegerPair tip = mapping.floor( new TimeIntegerPair( time, 0 ) );
		if( tip.value() > 0 ) {
			return time;
		}
		while( tip != null && tip.value() <= 0 ) {
			tip = mapping.higher( tip );
		}
		return (tip != null) ? tip.time() : Integer.MAX_VALUE;
	}

	/**
	 * Returns the minimum value between the specified times.
	 * @param fromTime the first time point (inclusive).
	 * @param toTime the last time point (exclusive).
	 * @throws IllegalArgumentException if {@code toTime} is smaller than
	 * {@code fromTime}.
	 * @return the minimum value between the specified times.
	 */
	public int minimum( int fromTime, int toTime ) throws IllegalArgumentException {
		if( fromTime > toTime ) {
			throw new IllegalArgumentException( fromTime + " cannot be smaller than " + toTime + "." );
		} else if( fromTime == toTime ) {
			return 0;
		} else {
			int minimum = Integer.MAX_VALUE;
			TimeIntegerPair firstStep = mapping.floor( new TimeIntegerPair( fromTime, 0 ) );
			TimeIntegerPair lastStep = mapping.lower( new TimeIntegerPair( toTime, 0 ) );
			NavigableSet<TimeIntegerPair> subSet = mapping.subSet( firstStep, true, lastStep, true );
			for( TimeIntegerPair tip : subSet ) {
				if( tip.value() < minimum ) {
					minimum = tip.value();
				}
			}
			return minimum;
		}
	}
}
