/**
 * Mapping.java
 * Created: 08.04.2014, 13:34:40
 */
package org.zetool.container.mapping;


/**
 * The mapping framework allows to store values of a range type that are mapped
 * to values of a domain size. A typical example of such mappings are hash maps,
 * which store objects according to key values.
 * @author Jan-Philipp Kappmeier
 */
public interface Mapping<D,R> {
	R get( D d );
	void set( D identifiableObject, R value );
}
