package org.safebusiness;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class Attribute {
	@Id 
	@GeneratedValue
	private Integer id;
	// This is a transient type, just a cache of the Datatype instance for this attribute
	@Transient
	private Datatype dataType;
	// The owner of the attribute
	@Transient
	private Customizable owner;
	// Attribute type
	@Transient
	private AttributeType attributeType;
	// Attribute value
	@Transient
	private Object value;
	// The attribute name
	@Column
	private String name;
	// data type name that will be saved to the DB eg. java.lang.String
	@Column(name="data_type_string")
	private String dataTypeString;
	// The String representation of this attribute value
	@Column(name="value")
	private String valueText;

	// Getters and Setters
	public Datatype getDataType() {
		return dataType;
	}

	public void setDataType(Datatype dataType) {
		this.dataType = dataType;
	}

	public Customizable getOwner() {
		return owner;
	}

	public void setOwner(Customizable owner) {
		this.owner = owner;
	}

	public AttributeType getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(AttributeType attributeType) {
		this.attributeType = attributeType;
	}
	
	private Object processAttributeValue() {
		if (dataType == null || dataType.getValue() == null) {
			throw new IllegalArgumentException("Attribute dataType can't be null!");
		}
		switch(dataType.getValue()) {
		
		 case Datatype.TEXT :
			return value.toString();
		 case Datatype.INTEGER :
			return Integer.valueOf(value.toString());
		 case Datatype.DATE:
			 // TODO Should parse date in correct format from the web
			 return null;
		 case Datatype.FILE :
			 // TODO and implementation for files
			return null;
		default :
			throw new IllegalArgumentException("Unsupported datatype :" + dataType.getValue());
		}
	}
	
	/**
	 * Possibly the value we want to persist to the DB
	 * 
	 * @return the text representation of the datatype
	 */
	public String getValueText() {
		return value.toString();
	}
	
	/**
	 * @return the real datatype of this {@link Attribute}
	 */
	public Object getValue() {
		return processAttributeValue();
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataTypeString() {
		return dataTypeString;
	}

	public void setDataTypeString(String dataTypeString) {
		this.dataTypeString = dataTypeString;
	}

	public void setValueText(String valueText) {
		this.valueText = valueText;
	}
	
}
