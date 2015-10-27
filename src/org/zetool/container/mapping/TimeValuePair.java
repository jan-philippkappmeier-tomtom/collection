package org.zetool.container.mapping;

/**
 * A utility class used for the underlying {@code TreeSet}. A mapping of a time {@code t} to an value {@code v} is
 * stored by adding a {@code TimeValuePair (t,v)} to the tree set.
 */
public class TimeValuePair implements Cloneable, Comparable<TimeValuePair> {

    /** Stores the time component of the pair. */
    protected int time;
    /** Stores the value component of this pair. */
    protected double value;

    /**
     * Constructs a new {@code TimeValuePair} with the specified values. Runtime O(1).
     *
     * @param time the time component of the pair.
     * @param value the value component of the pair.
     */
    protected TimeValuePair(int time, double value) {
        this.time = time;
        this.value = value;
    }

    /**
     * Sets the value of this {@code TimeValuePair} to the specified value. Runtime O(1).
     *
     * @param newValue the new value of this time - value pair.
     */
    public void set(double newValue) {
        value = newValue;
    }

    /**
     * Returns the time component of this {@code TimeValuePair}. Runtime O(1).
     *
     * @return the time component of this {@code TimeValuePair}.
     */
    public int time() {
        return time;
    }

    /**
     * Returns the value component of this {@code TimeValuePair}. Runtime O(1).
     *
     * @return the value component of this {@code TimeValuePair}.
     */
    public double value() {
        return value;
    }

    /**
     * Compares two {@code TimeValuePair}s by their time component. Runtime O(1).
     *
     * @param o the {@code TimeValuePair} to be compared.
     * @return 0 if this pair is equal to the specified pair; a value less than 0 if this pair's time component is
     * numerically less than the specified pair's time component; and a value greater than 0 if this pair's time
     * component is numerically greater than the specified pair's time component.
     */
    public int compareTo(TimeValuePair o) {
        long temp = time;
        return Math.round(Math.signum(temp - o.time()));
    }

    /**
     * Creates a copy of this {@code TimeValuePair}. Runtime O(1).
     *
     * @return a copy of this {@code TimeValuePair}.
     */
    @Override
    public TimeValuePair clone() {
        return new TimeValuePair(time, value);
    }

    /**
     * Compares this {@code TimeValuePair} to the specified object. The result is true if and only if the argument is
     * not null and is a {@code TimeValuePair} which has the same time component. The value component is ignored. This
     * is due to the fact that the underlying tree set must not contain two {@code TimeValuePair}s with the same time
     * component. Runtime O(1).
     *
     * @param o the object this mapping is to be compared with.
     * @return {@code true} if the given object represents an {@code TimeValuePair} equivalent to this pair,
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        return (o != null) && (o instanceof TimeValuePair) && ((TimeValuePair) o).time() == time;
    }

    /**
     * Returns a hash code for this {@code TimeValuePair}. Since this hash code should be consistent with
     * {@link #equals} just the time component of the pair is used. Runtime O(1).
     *
     * @return the time component of this {@code TimeValuePair}.
     */
    @Override
    public int hashCode() {
        return time;
    }

    /**
     * Returns a string representation of this {@code TimeValuePair}. This representation is of the form "time = value".
     * Runtime O(1).
     *
     * @return a string representation of this {@code TimeValuePair}.
     */
    @Override
    public String toString() {
        return String.format("%1$s = %2$s", time, value);
    }
}
