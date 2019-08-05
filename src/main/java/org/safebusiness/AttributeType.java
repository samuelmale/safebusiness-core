package org.safebusiness;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * Used for configuring or defining {@link Attribute} schemes
 * @author samuel
 *
 */
@Entity
public class AttributeType {

	@Id 
	@GeneratedValue
	private Integer id;
	// This is a transient type, just a cache of the Datatype instance for this attribute
	@Transient
	private List<String> dataTypes;
	
	// The attribute name
	@Column
	private String name;
	// data type name that will be saved to the DB eg. java.lang.String
	@Column(name="data_type_string")
	private String dataTypeString;
	
	// This a hack around having this @id in String format
	// I hate casting in thymeleafe templates
	@Column
	private String stringId;
	@OneToMany(mappedBy="attributeType")
	private List<ActionAttribute> attributes;
	
	@ManyToOne
    @JoinColumn(name="action_id", nullable=true)
	private Action action;
	
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

	public List<String> getDataTypes() {
		if (dataTypes == null) {
			dataTypes = Datatype.getSupportedDatatypes();
		}
		return dataTypes;
	}

	public void setDataTypes(List<String> dataTypes) {
		this.dataTypes = dataTypes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataTypeString() {
		return dataTypeString;
	}

	public void setDataTypeString(String dataTypeString) {
		this.dataTypeString = dataTypeString;
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}

	public List<ActionAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<ActionAttribute> attributes) {
		if (this.attributes == null) {
			this.attributes = new ArrayList<>();
		}
		
		this.attributes.addAll(attributes);
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
}
