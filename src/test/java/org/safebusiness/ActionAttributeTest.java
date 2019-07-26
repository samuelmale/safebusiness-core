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
	private Datatype datatype;
	
	@Before
	public void setup() {
		action = new Action();
		datatype = new Datatype();
		// Some instructions
		instructions = Arrays.asList("Provide Laywer's full Name", "Provide LC-IV Phone number");
		action.setInstructions(instructions);
		
	}
	
	@Test
	public void shouldCustomizeActionWithNewAttributes() {
		// setup
		
		// Create attributes to collect info for given instructions
		Attribute lawyerzName = new ActionAttribute();
		lawyerzName.setName("Lawyers name");
		datatype.setValue(Datatype.TEXT);
		lawyerzName.setDataType(datatype);
		lawyerzName.setValue("Smith Doe");
		Attribute lc5PhoneNum = new ActionAttribute();
		datatype.setValue(Datatype.TEXT);
		lc5PhoneNum.setName("LC-IV Phone number");
		lc5PhoneNum.setDataType(datatype);
		lc5PhoneNum.setValue("256773437821");
		action.addAttribute(lawyerzName);
		action.addAttribute(lc5PhoneNum);
		
		// assert
		assertTrue(action.hasAttributes());
		assertEquals(2, action.getAttributes().size());
		assertEquals("Smith Doe", lawyerzName.getValue());
		
	}
}
