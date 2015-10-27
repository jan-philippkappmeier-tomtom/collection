/* zet evacuation tool copyright (c) 2007-15 zet evacuation team
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

import java.util.Iterator;
import java.util.function.Function;

/**
 * Returns an iterator by applying an adapter to the types of a different iterator.
 * @param <T> the original type that is iterated over
 * @param <R> the adapted result type
 * @author Jan-Philipp Kappmeier
 */
public class IteratorAdapter<T, R> implements Iterator<R> {
  /** Iterataor over the original types. */
  private final Iterator<T> original;
  /** Adapting the original type to the adapted type. */
  private final Function<T, R> adapter;

  public IteratorAdapter( Iterable<T> iterable, Function<T, R> adapter ) {
    this.original = iterable.iterator();
    this.adapter = adapter;
  }

  @Override
  public boolean hasNext() {
    return original.hasNext();
  }

  @Override
  public R next() {
    return adapter.apply( original.next() );
  }
}
