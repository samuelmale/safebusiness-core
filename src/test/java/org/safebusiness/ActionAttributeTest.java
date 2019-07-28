package org.safebusiness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ActionAttributeTest extends RegressionTestHelper {
	
	private Action action;
	private List<String> instructions;
	
	@Before
	public void setup() {
		action = new Action();
		// Some instructions
		instructions = Arrays.asList("Provide Laywer's full Name", "Provide LC-IV Phone number", "Provide your age");
		action.setInstructions(instructions);
		
	}
	
	@Test
	public void shouldCustomizeActionWithNewAttributes() {
		// setup
		
		// Create attributes to collect info for given instructions
		ActionAttribute lawyerzName = createActionAttribute(Datatype.TEXT, "Lawyers name", "Smith Doe");
		ActionAttribute lc5PhoneNum = createActionAttribute(Datatype.TEXT, "LC-IV Phone number", "256773437821");
		ActionAttribute age = createActionAttribute(Datatype.INTEGER, "Age", 45);
		
		action.addAttribute(lawyerzName);
		action.addAttribute(lc5PhoneNum);
		action.addAttribute(age);
		
		// assert
		assertTrue(action.hasAttributes());
		assertEquals(3, action.getAttributes().size());
		assertEquals("Smith Doe", lawyerzName.getValue());
		assertEquals("256773437821", lc5PhoneNum.getValue());
		assertEquals(45, age.getValue());
		// should return equivalent string
		assertEquals("45", age.getValueText());
	}
}
