/* zet evacuation tool copyright (c) 2007-20 zet evacuation team
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
package org.zetool.container.collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.Test;
import org.zetool.container.mapping.IdentifiableCloneable;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ListSequenceTest {

    @Test
    public void addAll_shouldAddAll() {
        IdentifiableCloneable i1 = mock(IdentifiableCloneable.class);
        IdentifiableCloneable i2 = mock(IdentifiableCloneable.class);

        ListSequence<IdentifiableCloneable> fixture = new ListSequence<>();
        boolean modified = fixture.addAll(Arrays.asList(i1, i2));

        assertThat(modified, is(true));

        assertThat(fixture.size(), is(equalTo(2)));
        assertThat(fixture.get(0), is(sameInstance(i1)));
        assertThat(fixture.get(1), is(sameInstance(i2)));
    }

    @Test
    public void removeAll_removesAllElements() {
        IdentifiableCloneable i1 = mock(IdentifiableCloneable.class);
        IdentifiableCloneable i2 = mock(IdentifiableCloneable.class);
        IdentifiableCloneable i3 = mock(IdentifiableCloneable.class);

        ListSequence<IdentifiableCloneable> fixture = new ListSequence<>(Arrays.asList(i1, i2, i3));

        boolean modified = fixture.removeAll(Arrays.asList(i1, i3));

        assertThat(modified, is(true));

        assertThat(fixture.size(), is(equalTo(1)));
        assertThat(fixture.get(0), is(sameInstance(i2)));
    }

    @Test
    public void containsAll_detectsUncontained() {
        IdentifiableCloneable i1 = mock(IdentifiableCloneable.class);
        IdentifiableCloneable i2 = mock(IdentifiableCloneable.class);
        IdentifiableCloneable i3 = mock(IdentifiableCloneable.class);

        ListSequence<IdentifiableCloneable> fixture = new ListSequence<>(Arrays.asList(i2, i3));

        boolean modified = fixture.containsAll(Arrays.asList(i1, i3));

        assertThat(modified, is(false));
    }

    @Test
    public void containsAll_passes() {
        IdentifiableCloneable i1 = mock(IdentifiableCloneable.class);
        IdentifiableCloneable i2 = mock(IdentifiableCloneable.class);
        IdentifiableCloneable i3 = mock(IdentifiableCloneable.class);

        ListSequence<IdentifiableCloneable> fixture = new ListSequence<>(Arrays.asList(i1, i2, i3));

        boolean modified = fixture.containsAll(Arrays.asList(i1, i3));

        assertThat(modified, is(true));
    }

}
