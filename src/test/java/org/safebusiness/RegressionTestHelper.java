package org.safebusiness;

/**
 * Contains utility methods required for Test cases
 * 
 * @author samuel
 *
 */
public abstract class RegressionTestHelper {

	/**
	 * Helper method for creating a basic attribute type
	 * @param dataType
	 * @param name
	 * @param value
	 * @return
	 */
	public ActionAttribute createActionAttribute(String dataType, String name, Object value) {
		ActionAttribute attribute = new ActionAttribute();
		attribute.setName(name);
		attribute.setDataType(new Datatype(dataType));
		attribute.setValue(value);
		return attribute;
	}
	
}
