package org.safebusiness;

import java.util.Arrays;
import java.util.List;

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
	
	public ProcedureTemplate createProcedureTemplate() {
		ProcedureTemplate template = new ProcedureTemplate();
		ActionAttribute age = createActionAttribute(Datatype.INTEGER, "Age", 17);
		ActionAttribute height = createActionAttribute(Datatype.INTEGER, "Heigt", 17);
		List<ActionAttribute> atts = Arrays.asList(age, height);
		template.setAttributes(atts);
		return template;
	}
}
