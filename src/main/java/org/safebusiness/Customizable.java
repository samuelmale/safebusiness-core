package org.safebusiness;

import java.util.Collection;

/**
 * Types that can be customized by adding new {@link Attribute}s.
 * @author samuel
 *
 */
public interface Customizable<A extends Attribute> {
	
	/**
	 * @return all attributes
	 */
	Collection<A> getAttributes();
	
	/**
	 * Adds an attribute.
	 * 
	 * @param attribute
	 */
	void addAttribute(A attribute);
}
