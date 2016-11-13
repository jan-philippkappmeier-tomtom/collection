package org.zetool.container.mapping;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public interface IdentifiableCloneable extends Identifiable, Cloneable {
    public IdentifiableCloneable clone() throws CloneNotSupportedException;
}
