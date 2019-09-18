package org.safebusiness.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.Act;
import org.safebusiness.api.repo.ActRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/safebusiness/api/v1/act")
public class ActController {
	
	 @Autowired
	 ActRepository actRepo;
	  
	  /**
	   * Gets all {@link Act}s from the system
	   * @return
	   */
	  @GetMapping
	  public List<Act> getAll() {
	    Iterable<Act> interator = actRepo.findAll();
	    return interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
	  }
}
