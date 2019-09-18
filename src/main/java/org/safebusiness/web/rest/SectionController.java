package org.safebusiness.web.rest;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.Section;
import org.safebusiness.api.repo.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main controller of the {@link Section} Resource. 
 * It exposes all REST operations on a Section.
 * 
 * @author samuel
 */

@RestController
@RequestMapping("/safebusiness/api/v1/section")
public class SectionController {

  @Autowired
  SectionRepository sectionRepo;
  
  /**
   * Gets all Sections from the system
   * @return
   */
  @GetMapping
  public List<Section> getAll() {
    Iterable<Section> interator = sectionRepo.findAll();
    return interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
  }
  
}
