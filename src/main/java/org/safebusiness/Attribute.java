package org.safebusiness;

public abstract class Attribute {
	
	private Datatype dataType;
	// The owner of the attribute
	private Customizable owner;
	// Attribute type
	private AttributeType attributeType;
	// Attribute value
	private Object value;
	// The attribute name
	private String name;

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
			return dataType.getValue();
		 case Datatype.INTEGER :
			return Integer.valueOf(dataType.getValue());
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
	
}
