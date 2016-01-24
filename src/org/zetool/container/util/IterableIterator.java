package org.zetool.container.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.zetool.container.localization.CollectionLocalization;

/**
 *
 * @param <E> the iterables
 * @author Jan-Philipp Kappmeier
 */
public class IterableIterator<E> implements Iterator<E> {

    /** The stored elements. */
    private final List<Iterable<E>> data;
    private final Iterator<Iterable<E>> iterator;
    /** The current iterator. */
    private Iterator<E> current = Collections.<E>emptyList().iterator();

    public IterableIterator(List<Iterable<E>> data, int index) {
        this.data = Objects.requireNonNull(data, "Array must not be null.");
        this.iterator = data.iterator();
    }

    /**
     * Returns whether there is one more element in the {@code array}.
     *
     * @return {@code true} if there is one more element in the {@code array}, {@code false} else.
     */
    @Override
    public boolean hasNext() {
        if (current.hasNext()) {
            return true;
        }
        while (iterator.hasNext()) {
            current = iterator.next().iterator();
            if (current.hasNext()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the next element of the {@code array}. Returns {@code null} if there is no more
     * element.
     *
     * @return the next element of the {@code array} if there is one more, {@code null} else.
     */
    @Override
    public E next() {
        return current.next();
    }

    /**
     * Removes the element that was last returned by {@code public E next()}. Method is not yet
     * implemented.
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException(CollectionLocalization.LOC.getString(
                "zet.collection.RemovalNotSupportedException"));
    }

}
