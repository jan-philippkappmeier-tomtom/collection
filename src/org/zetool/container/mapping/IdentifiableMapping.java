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

package org.zetool.container.mapping;

/**
 * The identifiable mapping framework provides array-like and array-based
 * datastructures which can be accessed by {@link Identifiable} instead of a
 * number. However, the identifiable have an ID which is used to store the data.
 * The mapping allows to store a value which is assigned to each identifiable.
 * @param <D> the domain type
 * @param <R> the range type
 * @author Jan-Philipp Kappmeier
 */
public interface IdentifiableMapping<D extends Identifiable,R> extends Mapping<D,R> {
	int getDomainSize();
	void setDomainSize( int value );
	boolean isDefinedFor( D identifiableObject );
}
