package org.safebusiness;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Action implements Customizable {
	
	private List<String> instructions;
	private List<Attribute> attributes;
			
	@Override
	public void addAttribute(Attribute attribute) {
		if (attributes == null) {
			attributes = new ArrayList<> ();
		}
		attributes.add(attribute);
	}
	
	// Getters and Setters
	public List<String> getInstructions() {
		return instructions;
	}
	
	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	@Override
	public Collection<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public boolean hasAttributes() {
		if (attributes == null) {
			return false;
		}
		return !attributes.isEmpty();
	}

}
