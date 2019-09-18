package org.safebusiness.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.Procedure;
import org.safebusiness.api.repo.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/safebusiness/api/v1/procedure")
public class ProcedureController {

	 @Autowired
	 ProcedureRepository procedureRepo;
	  
	  /**
	   * Gets all {@link Procedure}s from the system
	   * @return
	   */
	  @GetMapping
	  public List<Procedure> getAll() {
	    Iterable<Procedure> interator = procedureRepo.findAll();
	    return interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
	  }
}
