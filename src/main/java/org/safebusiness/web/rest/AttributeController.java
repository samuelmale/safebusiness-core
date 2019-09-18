package org.safebusiness.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.ActionAttribute;
import org.safebusiness.AttributeType;
import org.safebusiness.api.repo.AttributeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/safebusiness/api/v1/attribute")
public class AttributeController {
	
	 @Autowired
	 AttributeTypeRepository attributeTypeRepo;
	  
	  /**
	   * Gets all {@link ActionAttribute}s from the system
	   * @return
	   */
	  @GetMapping
	  public List<AttributeType> getAll() {
	    Iterable<AttributeType> interator = attributeTypeRepo.findAll();
	    return interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
	  }
	  
}
