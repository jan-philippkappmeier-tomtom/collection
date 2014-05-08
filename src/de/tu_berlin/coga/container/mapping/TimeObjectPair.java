/**
 * TimeObjectPair.java
 * Created: 08.04.2014, 13:08:13
 */
package de.tu_berlin.coga.container.mapping;


/**
 * A utility class used for the underlying {@code TreeSet}. A mapping
 * of a time {@code t} to an arbitrary object {@code v} is stored
 * by adding a {@code TimeObjectPair (t,v)} to the tree set.
 * @param <R> the object type stored for each time
 */
class TimeObjectPair<R> implements Cloneable, Comparable<TimeObjectPair<R>> {
	/**
	 * Stores the time component of the pair.
	 */
	protected int time;
	/**
	 * Stores the value component of this pair.
	 */
	protected R value;
	//private final IntegerObjectMapping<R> outer;

	/**
	 * Constructs a new {@code TimeObjectPair} with the specified
	 * values. Runtime O(1).
	 * @param time the time component of the pair.
	 * @param value the value component of the pair.
	 */
	protected TimeObjectPair( int time, R value ) {
		//this.outer = outer;
		this.time = time;
		this.value = value;
	}

	/**
	 * Sets the value of this {@code TimeObjectPair} to the specified
	 * value. Runtime O(1).
	 * @param newValue the new value of this time - object pair.
	 */
	public void set( R newValue ) {
		value = newValue;
	}

	/**
	 * Returns the time component of this {@code TimeObjectPair}.
	 * Runtime O(1).
	 * @return the time component of this {@code TimeObjectPair}.
	 */
	public int time() {
		return time;
	}

	/**
	 * Returns the value component of this {@code TimeObjectPair}.
	 * Runtime O(1).
	 * @return the value component of this {@code TimeObjectPair}.
	 */
	public R value() {
		return value;
	}

	/**
	 * Compares two {@code TimeObjectPair}s by their time component.
	 * Runtime O(1).
	 * @param o the {@code TimeObjectPair} to be compared.
	 * @return 0 if this pair is equal to the specified pair; a value less
	 * than 0 if this pair's time component is numerically less than the
	 * specified pair's time component; and a value greater than 0 if this
	 * pair's time component is numerically greater than the specified
	 * pair's time component.
	 */
  @Override
	public int compareTo( TimeObjectPair<R> o ) {
		if( time > o.time ) {
			return 1;
		} else if( time < o.time ) {
			return -1;
		} else {
			return 0;
		}
		//long t = (long) time;
		//long ot = (long) o.time();
		//return Math.round(Math.signum(t - ot));
	}

	/**
	 * Creates a copy of this {@code TimeObjectPair}. Runtime O(1).
	 * @return a copy of this {@code TimeObjectPair}.
	 */
	@Override
	public TimeObjectPair<R> clone() {
		return new TimeObjectPair<>( time, value );
	}

	/**
	 * Compares this {@code TimeObjectPair} to the specified object.
	 * The result is true if and only if the argument is not null and is a
	 * {@code TimeObjectPair} which has the same time component. The
	 * value component is ignored. This is due to the fact that the
	 * underlying tree set must not contain two
	 * {@code TimeObjectPair}s with the same time component.
	 * Runtime O(1).
	 * @param o the object this mapping is to be compared with.
	 * @return {@code true} if the given object represents an
	 * {@code TimeObjectPair} equivalent to this pair,
	 * {@code false} otherwise.
	 */
	@Override
	public boolean equals( Object o ) {
		return (o != null) && (o instanceof TimeObjectPair) && ((TimeObjectPair)o).time() == time;
	}

	/**
	 * Returns a hash code for this {@code TimeObjectPair}. Since
	 * this hash code should be consistent with {@link #equals} just the
	 * time component of the pair is used. Runtime O(1).
	 * @return the time component of this {@code TimeObjectPair}.
	 */
	@Override
	public int hashCode() {
		return time;
	}

	/**
	 * Returns a string representation of this {@code TimeObjectPair}.
	 * This representation is of the form "time = value". Runtime O(1).
	 * @return a string representation of this {@code TimeObjectPair}.
	 */
	@Override
	public String toString() {
		return String.format( "%1$s = %2$s", time, value );
	}

}
