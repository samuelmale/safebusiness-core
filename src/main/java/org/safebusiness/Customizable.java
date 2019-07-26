package org.safebusiness;

import java.util.Collection;

/**
 * Types that can be customized by adding new {@link Attribute}s.
 * @author samuel
 *
 */
public interface Customizable {
	
	/**
	 * @return all attributes
	 */
	Collection<Attribute> getAttributes();
	
	/**
	 * Adds an attribute.
	 * 
	 * @param attribute
	 */
	void addAttribute(Attribute attribute);
}
