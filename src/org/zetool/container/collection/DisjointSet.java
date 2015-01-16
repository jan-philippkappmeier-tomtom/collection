
package org.zetool.container.collection;

import org.zetool.container.mapping.Identifiable;
import org.zetool.container.mapping.IdentifiableObjectMapping;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jan-Philipp Kappmeier
 * @param <E>
 */
public class DisjointSet<E extends Identifiable> {
    /** Stores the information about the aprents. */
  IdentifiableObjectMapping<E,E> parents;

  /**
   * Initializes the disjoint-set forest by creating a tree for each set containing only one element.
   * @param elements the elements in the set.
   */
  public DisjointSet( IdentifiableCollection<E> elements ) {
    parents = new IdentifiableObjectMapping<>( elements.size() );
    makeSet( elements );
  }
  
  public DisjointSet( int size ) {
    parents = new IdentifiableObjectMapping<>( size );
  }
  
  public final void makeSet( IdentifiableCollection<E> elements ) {
    for( E e : elements ) {
      parents.set( e, e );
    }
  }

  /**
   * Initializes the sets.
   * @param elements the initial elements
   */
  public final void makeSet( Collection<E> elements ) {
    elements.stream().forEach( (e) -> { parents.set( e, e ); } );
  }

//  @Override
  public E find( E e ) {
    final E parent = parents.get( e );
    if( parent.equals( e ) ) {
      return e;
    } else {
      return find( parent );
    }
  }

//  @Override
  public void union( E e1, E e2 ) {
    parents.set( find( e1 ), find( e2 ) );
  }
  
    private static class IdEx implements Identifiable {
      
      
      int value;

    public IdEx( int value ) {
      this.value = value;
    }
      
      
    @Override
    public int id() {
      return value;
    }

    @Override
    public Identifiable clone() {
      throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
      return "IdEx{" + "value=" + value + '}';
    }
    
    
      
    }
  public static void main( String... args ) {
    final int elementCount = 10;
    List<IdEx> els = new LinkedList<>();
    for( int i = 0; i < elementCount; ++i ) {
      els.add( new IdEx(i) );
    }
    
    DisjointSet<IdEx> uf = new DisjointSet<>( els.size() );
    uf.makeSet( els );
    
    for( int i = 0; i < elementCount; ++i ) {
      System.out.println( "" + i + ": " + uf.find( els.get( i ) ) );
    }
    
    for( int i = 0; i < elementCount-1; i+=2 ) {
      uf.union( els.get( i ), els.get( i + 1 ) );
    }
    for( int i = 0; i < elementCount; ++i ) {
      System.out.println( "" + i + ": " + uf.find( els.get( i ) ) );
    }
  }
}
