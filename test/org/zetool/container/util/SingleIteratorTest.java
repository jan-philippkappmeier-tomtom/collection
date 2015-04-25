package org.zetool.container.util;

import java.util.NoSuchElementException;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class SingleIteratorTest {
  
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Test
  public void test() {
    Integer original = 3;
    SingleIterator<Integer> intIterator = new SingleIterator<>( original );
    assertTrue( "No next element available", intIterator.hasNext() );
    Integer i = intIterator.next();
    assertFalse( "Another next element available", intIterator.hasNext() );
    Assert.assertEquals("Not the same value taken out.", original, i );
  }
  
  @Test
  public void testException() {
    SingleIterator<Integer> intIterator = new SingleIterator<>( 3 );
    intIterator.next();
    exception.expect( NoSuchElementException.class );
    intIterator.next();
  }
}
