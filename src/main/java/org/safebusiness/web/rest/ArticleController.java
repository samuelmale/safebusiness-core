package org.safebusiness.web.rest;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.Article;
import org.safebusiness.api.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/safebusiness/api/v1/article")
public class ArticleController {
  
  @Autowired
  ArticleRepository articleRepo;
  
  /**
   * Gets all Sections from the system
   * @return
   */
  @GetMapping
  public List<Article> getAll() {
    Iterable<Article> interator = articleRepo.findAll();
    return interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
  }

}
