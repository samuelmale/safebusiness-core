package org.safebusiness.api.repo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safebusiness.Action;
import org.safebusiness.ActionAttribute;
import org.safebusiness.Datatype;
import org.safebusiness.RegressionTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionAttributeRepositoryTest extends RegressionTestHelper {
	
	@Autowired
	ActionAttributeRepository actionAttributeRepo;
	
	@Autowired
	ActionRepository actionRepo;
	
	Action action;
	
	ActionAttribute attribute;
	
	@Before
	public void setup() {
		action = new Action();
		actionRepo.save(action);
		attribute = createActionAttribute(Datatype.TEXT, "Lawyers name", "Smith Doe");
		attribute.setAction(action);
	}
	
	@Test
	public void save_shouldSaveNewActionAttribute() {
		// replay
		ActionAttribute savedAttribute = actionAttributeRepo.save(attribute);
		
		// verify
		assertNotNull(savedAttribute);
		assertNotNull(savedAttribute.getId());
	}
	
	@Test
	public void save_shouldSaveAttributeWithoutAction() {
		// replay
		attribute.setAction(null);
		ActionAttribute savedAttribute = actionAttributeRepo.save(attribute);
		
		// verify
		assertNotNull(savedAttribute);
		assertNotNull(savedAttribute.getId());
	}
	
	@Test
	public void findById_shouldGetAttribute() {
		attribute.setAction(null);
		attribute.setId(null);
		Integer id = actionAttributeRepo.save(attribute).getId();
		
		// verify
		assertTrue(actionAttributeRepo.findById(id).isPresent());
	}
	
	@Test
	public void delete_shouldDeleteExistingAttribute() {
		// setup
		ActionAttribute savedAttribute = actionAttributeRepo.save(attribute);
		int id = savedAttribute.getId();
		
		// verify
		assertTrue(actionAttributeRepo.findById(id).isPresent());
		
		// replay
		actionAttributeRepo.delete(savedAttribute);
		
		// verify
		assertFalse(actionAttributeRepo.findById(id).isPresent());
	}
	
	// TODO : Add more unit tests
}
