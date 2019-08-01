package org.safebusiness.api.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.safebusiness.Article;
import org.safebusiness.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SectionRepositoryTest {

	@Autowired
	SectionRepository repo;
	
	@Autowired
	ArticleRepository articleRepo;
	
	@Test
	public void save() {
		Section sec = new Section();
		sec.setArticles(null);
		sec.setName("Test Name");
		repo.save(sec);
	}
	
}
