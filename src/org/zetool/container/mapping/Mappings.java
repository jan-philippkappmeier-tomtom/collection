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
 * {@code Mappings} provides utility functions for mappings.
 * @author Martin Groß
 * @author Jan-Philipp Kappmeier
 */
public final class Mappings {

	/** Private constructor for utility class. */
	private Mappings() {
	}

	/**
	 * A printing method for integer mappings. Returns a {@link String}
	 * representation of an integer mapping. Each element in the {@code domain} is
	 * checked if it exists, and if so is appended to the output. Otherwise, it
	 * is stated to be 'UNDEFINED'. For {@link Integer.MAX_VALUE}, the text
	 * is given out.
	 * @param <T> the domain type of the mapping
	 * @param domain the domain that is printed
	 * @param mapping the mapping
	 * @return a {@link String} representation of an integer mapping
	 */
	public static <T extends Identifiable> String toString( Iterable<T> domain, IdentifiableIntegerMapping<T> mapping ) {
		StringBuilder result = initToString();
		boolean isEmpty = true;
		for( T identifiable : domain ) {
			isEmpty = false;
			if( mapping.isDefinedFor( identifiable ) ) {
				if( mapping.get( identifiable ) == Integer.MAX_VALUE ) {
					appendEntry( result, identifiable.toString(),"MAX_INT" );
				} else {
					appendEntry( result, identifiable.toString(), Integer.toString( mapping.get( identifiable ) ) );
				}
			} else {
				appendEntry( result, identifiable.toString(), "UNDEFINED" );
			}
		}

		return finalizeToString( result, isEmpty );
	}

/**
	 * A general printing method for mappings. Returns a {@link String}
	 * representation of a general mapping. For each element in the {@code domain}
	 * its string representation is given out.
	 * @param <T> the domain type of the mapping
	 * @param domain the domain that is printed
	 * @param mapping the mapping
	 * @return a {@link String} representation of a general mapping
	 */
	public static <T extends Identifiable> String toString( Iterable<T> domain,
					IdentifiableObjectMapping<T, ?> mapping ) {
		StringBuilder result = initToString();
		boolean isEmpty = true;
		for( T identifiable : domain ) {
			isEmpty = false;
			appendEntry( result, identifiable.toString(), mapping.get( identifiable ).toString() );
		}
		return finalizeToString( result, isEmpty );
	}

	/**
	 * Adds an item to the current representation of the output string. The item
	 * will have the form 'key = value ,'. The additional ',' at the end is
	 * removed by the call of {@link #finalizeToString(java.lang.StringBuilder, boolean) }
	 * if the final {@link String} is returned.
	 * @param result the {@link StringBuilder} used to generate the output
	 * @param key the current item
	 * @param value the value of the current item
	 */
	private static void appendEntry( StringBuilder result, String key, String value ) {
		result.append( key ).append( " = " ).append( value ).append( ", " );
	}

	/**
	 * Initializes the string representation of a mapping. Any mapping starts with
	 * opening bracket '['.
	 * @return the initial string representation of a mapping
	 */
	private static StringBuilder initToString() {
		StringBuilder result = new StringBuilder( 2 );
		result.append( "[" );
		return result;
	}

	/**
	 * Finalizes the string representation of a mapping. Any mapping ends with a
	 * closing bracket ']'. If no element is contained in the mapping, two
	 * characters are removed (the last ',' in the sequence).
	 * @param result the current state of the string builder
	 * @param isEmpty decides whether the mapping was empty, or not
	 * @return the complete {@link String}
	 */
	private static String finalizeToString( StringBuilder result, boolean isEmpty ) {
		if( !isEmpty ) {
			result.delete( result.length() - 2, result.length() );
		}
		result.append( "]" );
		return result.toString();
	}
}
