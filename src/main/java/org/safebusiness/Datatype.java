package org.safebusiness;

import java.util.Arrays;
import java.util.List;

public class Datatype {
	// TODO we will need to add special datatypes like phone number. This because it should be store as String in Java
	// 		but at the client side should be handle as a Number
	
	// Supported data types
	
	public static final String INTEGER = "java.lang.Integer";
	
	public static final String TEXT = "java.lang.String";
	
	public static final String FILE = "java.util.File";
	
	public static final String DATE = "java.util.Date";
	
	// This is the dataType selected for a given attribute
	private String value = null;
	
	public List<String> getSupportedDatatypes() {
		return Arrays.asList(INTEGER, TEXT, FILE, DATE);
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		if (!getSupportedDatatypes().contains(value)) {
			throw new IllegalArgumentException("Unsupported Datatype : " + value);
		}
		this.value = value;
	}
}
