package org.zetool.container.util;

import java.util.NoSuchElementException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class ArrayIteratorTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  /**
   * Tests fast failure if {@code null} reference is passed.
   */
  @Test(expected = NullPointerException.class)
  public void testInvocationFailure() {
    ArrayIterator<Object> objectIterator = new ArrayIterator<>( null );
  }

  /**
   * Tests iteration of an empty array.
   */
  @Test
  public void testEmptyArray() {
    Object[] objects = new Object[0];
    Object[] objects10 = new Object[10];
    while( new ArrayIterator<>( objects ).hasNext() ) {
      fail( "Should not iterate over emtpy array!" );
    }
    while( new ArrayIterator<>( objects10 ).hasNext() ) {
      fail( "Should not iterate over emtpy array!" );
    }
  }

  /**
   * Tests iteration of a full array.
   */
  @Test
  public void testFullArray() {
    Integer[] integers = {1, 2, 3};
    int sum = 0;
    ArrayIterator<Integer> ii = new ArrayIterator<>( integers );
    int invocations = 0;
    while( ii.hasNext() ) {
      sum += ii.next();
      invocations++;
    }
    assertEquals( sum, 6 );
    assertEquals( invocations, 3 );
  }

  /**
   * Tests iteration over sparsely filled arrays.
   */
  @Test
  public void testSparseArray() {
    Integer[] integers = new Integer[3];
    integers[0] = 1;
    Double[] doubles = new Double[5];
    doubles[3] = Math.PI;
    Long[] longs = new Long[7];
    longs[6] = 123456789L;
    int invocations = 0;

    ArrayIterator<Integer> ii = new ArrayIterator<>( integers );
    while( ii.hasNext() ) {
      assertEquals( ii.next(), (Integer) 1 );
      invocations++;
    }
    ArrayIterator<Double> di = new ArrayIterator<>( doubles );
    while( di.hasNext() ) {
      assertEquals( di.next(), (Double) Math.PI );
      invocations++;
    }
    ArrayIterator<Long> li = new ArrayIterator<>( longs );
    while( li.hasNext() ) {
      assertEquals( li.next(), (Long) 123456789L );
      invocations++;
    }
    assertEquals( invocations, 3 );
  }

  /**
   * Tests that {@link ArrayIterator#next() } obeys the contract and throws an exeption.
   */
  @Test(expected = NoSuchElementException.class)
  public void testToManyCalls() {
    Integer[] integers = {1, 2, 3};
    ArrayIterator<Integer> ii = new ArrayIterator<>( integers );
    while( ii.hasNext() ) {
    }
    ii.next();
  }

  /**
   * Tests that not implemented {@link ArrayIterator#remove() } throws an exception.
   */
  @Test(expected = UnsupportedOperationException.class)
  public void testRemoveNotSupported() {
    ArrayIterator<Integer> ii = new ArrayIterator<>( new Integer[0] );
    ii.remove();
  }
}
