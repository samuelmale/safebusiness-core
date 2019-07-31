package org.safebusiness.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.Act;
import org.safebusiness.ActionAttribute;
import org.safebusiness.Article;
import org.safebusiness.Datatype;
import org.safebusiness.Section;
import org.safebusiness.api.APIUtils;
import org.safebusiness.api.repo.ActionAttributeRepository;
import org.safebusiness.api.repo.ArticleRepository;
import org.safebusiness.api.repo.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

	@Autowired
	ArticleRepository articleRepo;
	@Autowired
	SectionRepository sectionRepo;
	@Autowired
	ActionAttributeRepository attributeRepo;
	
	@GetMapping("safebusiness/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("safebusiness")
	public String baseUrl() {
		return "redirect:safebusiness/index";
	}
	
	@GetMapping("safebusiness/article/{id}")
	public String createOrViewArticle(Model model, @PathVariable("id") String id) {
		if (id != null) {
			try {
				if (articleRepo.findById(Integer.parseInt(id)).isPresent()) {
					model.addAttribute("article", articleRepo.findById(Integer.parseInt(id)).get());
					model.addAttribute("inViewMode", true);
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("article", new Article());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("article", new Article());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("article", new Article());
		}
		
		return "article";
	}
	
	@PostMapping("addArticle")
	public String addArticle(@Valid Article article) {
		Article savedArticle = articleRepo.save(article);
		if (savedArticle != null) {
			return "redirect:safebusiness/article/" + savedArticle.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	@GetMapping("safebusiness/listArticles")
	public String listArticles(Model model) {
		Iterable<Article> interator = articleRepo.findAll();
		@SuppressWarnings("unchecked")
		List<Article> articles = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("articles", articles);
		return "listArticles";
	}
	
	@GetMapping("safebusiness/listAttributes")
	public String listAttributes(Model model) {
		Iterable<ActionAttribute> interator = attributeRepo.findAll();
		@SuppressWarnings("unchecked")
		List<ActionAttribute> attributes = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("attributes", attributes);
		return "listAttributes";
	}
	private List<Article> parseArticleString(String val) {
		List<Article> ret = new ArrayList<>();
		String[] stringIds = val.split(",");

		for (String id : stringIds) {
			// Lookup the Article
			try {
				if (articleRepo.findById(Integer.parseInt(id)).isPresent()) {
					ret.add(articleRepo.findById(Integer.parseInt(id)).get());	
				}
			} catch(NumberFormatException ex) {
				// Ignore..
			}
		}
		return ret;
	}
		
	@GetMapping("safebusiness/newSection")
	public String newSection(Model model) {
		model.addAttribute("section", new Section());
		return "addSection";
	}
	
	@PostMapping("addSection")
	public String addSection(@Valid Section section) {
		Integer actId = section.getActId();
		if(actId != null) {
			Act act = new Act();
			act.setId(actId);
		}else {
			section.setAct(null);
		}
		sectionRepo.save(section);
		return "redirect:safebusiness/index";
	}
	
	@GetMapping("safebusiness/attribute/{id}")
	public String createOrViewAttribute(Model model, @PathVariable("id") String id) {
		if (id != null) {
			try {
				ActionAttribute actionAtt = APIUtils.getActionAttributeById(Integer.parseInt(id), attributeRepo.findAll());
				if (actionAtt != null) {
					model.addAttribute("attribute", actionAtt);
					model.addAttribute("inViewMode", true);
					model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("attribute", new ActionAttribute());
					model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("attribute", new ActionAttribute());
				model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("attribute", new ActionAttribute());
			model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
		}
		
		return "attribute";
	}
	
	@PostMapping("safebusiness/addAttribute/{string}")
	public String addAttribute(@Valid ActionAttribute attribute, @PathVariable("string") String action, HttpServletResponse httpResponse) {
		try {
			// Make updating possible
			// TODO This has to be done to all domains supporting view modes
			attribute.setId(Integer.parseInt(action));
						
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
		}
		ActionAttribute savedAttribute = attributeRepo.save(attribute);
		if (savedAttribute != null) {
			return "redirect:viewAttribute/" + savedAttribute.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	// Another hack around here
	@GetMapping("safebusiness/addAttribute/viewAttribute/{id}")
	public String viewAttributeDuplicateHandler(Model model, @PathVariable("id") String id) {
		if (id != null) {
			try {
				ActionAttribute actionAtt = APIUtils.getActionAttributeById(Integer.parseInt(id), attributeRepo.findAll());
				if (actionAtt != null) {
					model.addAttribute("attribute", actionAtt);
					model.addAttribute("inViewMode", true);
					model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("attribute", new ActionAttribute());
					model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("attribute", new ActionAttribute());
				model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("attribute", new ActionAttribute());
			model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
		}
		
		return "attribute";
	}
	private List<Section> parseSectionString(String val) {
		List<Section> sec = new ArrayList<>();
		String[] stringIds = val.split(",");

		for (String id : stringIds) {
			// Lookup the Section
			try {
				if (sectionRepo.findById(Integer.parseInt(id)).isPresent()) {
					sec.add(sectionRepo.findById(Integer.parseInt(id)).get());	
				}
			} catch(NumberFormatException ex) {
				// Ignore..
			}
		}
		return sec;
	}
	
	
}
