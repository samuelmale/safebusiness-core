package org.safebusiness;

import java.util.List;

public class Action {
	
	private List<String> instructions;
	private List<Attribute> actionAttributes;
	
	// Getters and Setters
	public List<String> getInstructions() {
		return instructions;
	}
	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}
	public List<Attribute> getActionAttributes() {
		return actionAttributes;
	}
	public void setActionAttributes(List<Attribute> actionAttributes) {
		this.actionAttributes = actionAttributes;
	}
	
}
