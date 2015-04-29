
package org.zetool.container.priority;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class MinHeapTest {
  
  @Test
  public void testMinHeap() {
    MinHeap<String,Integer> strings = new MinHeap<>();
    strings.insert( "One", 3 );
    strings.insert( "Three", 5 );
    strings.insert( "Four", 4 );
    strings.insert( "Eleven", 6 );
    strings.insert( "Thirteen", 8 );
    assertEquals(strings.extractMin().getObject(), "One" );
    assertEquals(strings.extractMin().getObject(), "Four" );
    assertEquals(strings.extractMin().getObject(), "Three" );
    assertEquals(strings.extractMin().getObject(), "Eleven" );
    assertEquals(strings.extractMin().getObject(), "Thirteen" );
  }
  
  @Test
  public void testDecrease() {
    MinHeap<String,Integer> strings = new MinHeap<>();
    strings.insert( "One", 3 );
    strings.insert( "Three", 5 );
    strings.insert( "Four", 4 );
    strings.insert( "Eleven", 6 );
    strings.insert( "Thirteen", 8 );
    
    strings.decreasePriority( "Thirteen", 1 );
    
    assertEquals(strings.extractMin().getObject(), "Thirteen" );
    assertEquals(strings.extractMin().getObject(), "One" );
    assertEquals(strings.extractMin().getObject(), "Four" );
    assertEquals(strings.extractMin().getObject(), "Three" );
    assertEquals(strings.extractMin().getObject(), "Eleven" );    
  }
  
  @Test
  public void testGetMinDoesNotExtract() {
    MinHeap<String,Integer> strings = new MinHeap<>();
    strings.insert( "One", 3 );
    strings.insert( "Three", 5 );
    strings.insert( "Four", 4 );
    strings.insert( "Eleven", 6 );
    strings.insert( "Thirteen", 8 );

    assertEquals(strings.getMin().getObject(), "One" );
    assertEquals(strings.getMin().getObject(), "One" );
  }
  
  @Test
  public void testEmptyness() {
    MinHeap<String,Integer> heap = new MinHeap<>();
    assertTrue(heap.isEmpty());
    heap.insert( "Text", Integer.SIZE );
    assertFalse( heap.isEmpty());
    heap.extractMin();
    assertTrue(heap.isEmpty());
  }
  
  @Test
  public void testContains() {
    MinHeap<String,Integer> strings = new MinHeap<>();
    assertFalse(strings.contains( "One" ));
    strings.insert( "One", 3 );
    assertTrue(strings.contains( "One" ));
    strings.insert( "Three", 5 );
    strings.insert( "Four", 4 );
    assertTrue(strings.contains( "One" ));
    assertTrue(strings.contains( "Three" ));
    assertTrue(strings.contains( "Four" ));
    assertFalse(strings.contains( "Seven" ));
    
    assertEquals(strings.extractMin().getObject(), "One" );
    assertFalse(strings.contains( "One" ));
    assertTrue(strings.contains( "Three" ));
    assertTrue(strings.contains( "Four" ));
    
    assertEquals(strings.extractMin().getObject(), "Four" );
    assertFalse(strings.contains( "One" ));
    assertTrue(strings.contains( "Three" ));
    assertFalse(strings.contains( "Four" ));
    
    assertEquals(strings.extractMin().getObject(), "Three" );
    assertFalse(strings.contains( "One" ));
    assertFalse(strings.contains( "Three" ));
    assertFalse(strings.contains( "Four" ));
  }
  
  @Test
  public void testPriority() {
    MinHeap<String,Integer> strings = new MinHeap<>();
    strings.insert( "One", 3 );
    strings.insert( "Three", 5 );
    strings.insert( "Four", 4 );
    assertEquals((int)strings.priority( "One"), 3 );
    assertEquals((int)strings.priority( "Three"), 5 );
    assertEquals((int)strings.priority( "Four"), 4 );
    
    strings.decreasePriority( "One", -1 );
    assertEquals((int)strings.priority( "One"), -1 );
    assertEquals((int)strings.priority( "Three"), 5 );
    assertEquals((int)strings.priority( "Four"), 4 );
  }
}
