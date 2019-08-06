package org.safebusiness.api.repo;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.safebusiness.Document;
import org.safebusiness.RegressionTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DocumentIntegrationtest extends RegressionTestHelper {

	@Autowired
	DocumentRepository docRepo;
	
	@Ignore
	@Test
	public void save() {
		Document doc = new Document();
		doc.setName("Demo doc");
		doc.setTemplates(Arrays.asList(createProcedureTemplate()));
		docRepo.save(doc);
	}
	
}
