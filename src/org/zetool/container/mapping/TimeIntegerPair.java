package org.zetool.container.mapping;

/**
 * A utility class used for the underlying {@code TreeSet}. A mapping of a time {@code t} to an integer value {@code v}
 * is stored by adding a {@code TimeIntegerPair (t,v)} to the tree set.
 */
public class TimeIntegerPair implements Cloneable, Comparable<TimeIntegerPair> {

    /** Stores the time component of the pair. */
    protected int time;
    /** Stores the integer component of this pair. */
    protected int value;

    /**
     * Constructs a new {@code TimeIntegerPair} with the specified values. Runtime O(1).
     *
     * @param time the time component of the pair.
     * @param value the integer component of the pair.
     */
    protected TimeIntegerPair(int time, int value) {
        this.time = time;
        this.value = value;
    }

    /**
     * Sets the value of this {@code TimeIntegerPair} to the specified value. Runtime O(1).
     *
     * @param newValue the new value of this time - integer pair.
     */
    public void set(int newValue) {
        value = newValue;
    }

    /**
     * Returns the time component of this {@code TimeIntegerPair}. Runtime O(1).
     *
     * @return the time component of this {@code TimeIntegerPair}.
     */
    public int time() {
        return time;
    }

    /**
     * Returns the integer component of this {@code TimeIntegerPair}. Runtime O(1).
     *
     * @return the integer component of this {@code TimeIntegerPair}.
     */
    public int value() {
        return value;
    }

    /**
     * Compares two {@code TimeIntegerPair}s by their time component. Runtime O(1).
     *
     * @param o the {@code TimeIntegerPair} to be compared.
     * @return 0 if this pair is equal to the specified pair; a value less than 0 if this pair's time component is
     * numerically less than the specified pair's time component; and a value greater than 0 if this pair's time
     * component is numerically greater than the specified pair's time component.
     */
    public int compareTo(TimeIntegerPair o) {
        if (time > o.time) {
            return 1;
        } else if (time < o.time) {
            return -1;
        } else {
            return 0;
        }
		//long temp = time;
        //return Math.round(Math.signum(temp - o.time()));
    }

    /**
     * Creates a copy of this {@code TimeIntegerPair}. Runtime O(1).
     *
     * @return a copy of this {@code TimeIntegerPair}.
     */
    @Override
    public TimeIntegerPair clone() {
        return new TimeIntegerPair(time, value);
    }

    /**
     * Compares this {@code TimeIntegerPair} to the specified object. The result is true if and only if the argument is
     * not null and is a {@code TimeIntegerPair} which has the same time component. The integer component is ignored.
     * This is due to the fact that the underlying tree set must not contain two {@code TimeIntegerPair}s with the same
     * time component. Runtime O(1).
     *
     * @param o the object this mapping is to be compared with.
     * @return {@code true} if the given object represents an {@code TimeIntegerPair} equivalent to this pair,
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return (o != null) && (o instanceof TimeIntegerPair) && ((TimeIntegerPair) o).time() == time;
    }

    /**
     * Returns a hash code for this {@code TimeIntegerPair}. Since this hash code should be consistent with
     * {@link #equals} just the time component of the pair is used. Runtime O(1).
     *
     * @return the time component of this {@code TimeIntegerPair}.
     */
    @Override
    public int hashCode() {
        return time;
    }

    /**
     * Returns a string representation of this {@code TimeIntegerPair}. This representation is of the form "time =
     * value". Runtime O(1).
     *
     * @return a string representation of this {@code TimeIntegerPair}.
     */
    @Override
    public String toString() {
        return String.format("%1$s = %2$s", time, value);
    }

}
