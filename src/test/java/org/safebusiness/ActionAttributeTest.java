package org.safebusiness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionAttributeTest {
	
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
		Attribute lawyerzName = new ActionAttribute();
		lawyerzName.setName("Lawyers name");
		lawyerzName.setDataType(new Datatype(Datatype.TEXT));
		lawyerzName.setValue("Smith Doe");
		Attribute lc5PhoneNum = new ActionAttribute();
		lc5PhoneNum.setName("LC-IV Phone number");
		lc5PhoneNum.setDataType(new Datatype(Datatype.TEXT));
		lc5PhoneNum.setValue("256773437821");
		Attribute age = new ActionAttribute();
		age.setName("Age");
		age.setDataType(new Datatype(Datatype.INTEGER));
		age.setValue(45);
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
