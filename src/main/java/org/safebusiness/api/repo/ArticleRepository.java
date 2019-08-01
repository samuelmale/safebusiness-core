package org.safebusiness.api.repo;

import java.util.List;

import org.safebusiness.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository <Article, Integer> {
	
	@Query(
			value = "SELECT a FROM Article a WHERE a.articleNumber = ?1", 
			nativeQuery = true)
	public Article findByNumber(Integer number);
}
