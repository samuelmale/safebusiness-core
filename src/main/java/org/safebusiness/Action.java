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
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;

@Entity
public class Action implements Customizable<ActionAttribute> {
	
	@Id
	@GeneratedValue
	@Column(name="action_id")
	private Integer id;
//	@ElementCollection
//	@CollectionTable(name="Instructions", joinColumns=@JoinColumn(name="action_id"))
//	@Column(name="instructions")
	@Transient // As for now
	private List<String> instructions;
	@Transient
	//@OneToMany(mappedBy="action")
	private List<ActionAttribute> attributes;
	@OneToMany(mappedBy="action")
	private List<AttributeType> attributeTypes;
	// Procedure owning this Action
	@OneToOne
	@JoinColumn(name="procedure_id", nullable=true)
	private Procedure procedure;
	// As for now
	@Column
	private String instructionString;
	
	// This a hack around having this @id in String format
	// I hate casting in thymeleafe templates
	@Column
	private String stringId;
	@Column(unique=true)
	private String name;
	
	@Transient
	private String attributeNamesString;
	
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
		if(this.attributes == null) {
			this.attributes = new ArrayList<>();
		}
		this.attributes.addAll(attributes);
	}
	
	public boolean hasAttributes() {
		if (attributes == null) {
			return false;
		}
		return !attributes.isEmpty();
	}

	public Integer getId() {
		if (id != null) {
			setStringId(id.toString());
		}
		return id;
	}

	public void setId(Integer id) {
		if (id != null) {
			setStringId(id.toString());
		}
		this.id = id;
	}

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

	public String getInstructionString() {
		return instructionString;
	}

	public void setInstructionString(String instructionString) {
		this.instructionString = instructionString;
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttributeNamesString() {
		return attributeNamesString;
	}

	public void setAttributeNamesString(String attributeNamesString) {
		this.attributeNamesString = attributeNamesString;
	}

	public List<AttributeType> getAttributeTypes() {
		return attributeTypes;
	}

	public void setAttributeTypes(List<AttributeType> attributeTypes) {
		if (this.attributeTypes == null) {
			this.attributeTypes = new ArrayList<>();
		}
		this.attributeTypes.addAll(attributeTypes);
	}

}
