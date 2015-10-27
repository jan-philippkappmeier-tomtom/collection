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

package org.zetool.container.util;

import org.zetool.container.localization.CollectionLocalization;
import java.util.Iterator;

/**
 * A custom iterator thatallows to iterate over several iterators in one run.
 * @author Jan-Philipp Kappmeier
 * @param <T> the type that is iterated over
 */
public class IteratorIterator<T> implements Iterator<T> {
  /** An array of all iterators to be iterated over. */
  private final Iterator<T>[] iterators;
  /** The index of the iterator currently iterated over. */
  private int currentIteratorIndex;

  /**
   * Initializes the iterator iterator with some iterators.
   * @param iterators a list of iterators.
   */
  @SafeVarargs
  public IteratorIterator( Iterator<T>... iterators ) {
    this.iterators = iterators;
  }

  @Override
  public boolean hasNext() {
    while( currentIteratorIndex < iterators.length && !iterators[currentIteratorIndex].hasNext() ) {
      currentIteratorIndex++;
    }

    return currentIteratorIndex < iterators.length;
  }

  @Override
  public T next() {
    while( currentIteratorIndex < iterators.length && !iterators[currentIteratorIndex].hasNext() ) {
      currentIteratorIndex++;
    }

    return iterators[currentIteratorIndex].next();
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException( CollectionLocalization.LOC.getString(
            "zet.collection.RemovalNotSupportedException" ) );
  }
}
