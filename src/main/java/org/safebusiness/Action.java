package org.safebusiness;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

@Entity
public class Action implements Customizable<ActionAttribute> {
	
	@Id
	@GeneratedValue
	@Column(name="action_id")
	private Integer id;
	@ElementCollection
	@CollectionTable(name="Instructions", joinColumns=@JoinColumn(name="action_id"))
	@Column(name="instructions")
	private List<String> instructions;
	@OneToMany(mappedBy="action")
	private List<ActionAttribute> attributes;
			
	@Override
	public void addAttribute(ActionAttribute attribute) {
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
	public Collection<ActionAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ActionAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public boolean hasAttributes() {
		if (attributes == null) {
			return false;
		}
		return !attributes.isEmpty();
	}

}
