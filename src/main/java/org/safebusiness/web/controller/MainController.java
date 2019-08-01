package org.safebusiness.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.Act;
import org.safebusiness.Action;
import org.safebusiness.ActionAttribute;
import org.safebusiness.Article;
import org.safebusiness.Datatype;
import org.safebusiness.Section;
import org.safebusiness.api.APIUtils;
import org.safebusiness.api.repo.ActRepository;
import org.safebusiness.api.repo.ActionAttributeRepository;
import org.safebusiness.api.repo.ActionRepository;
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
	@Autowired
	ActionRepository actionRepo;
	@Autowired
	ActRepository actRepo;
	
	@GetMapping("safebusiness/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("safebusiness")
	public String baseUrl() {
		return "redirect:safebusiness/index";
	}
	
	/**
	 * Article GetMapping and PostMApping
	 */
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
	
	@GetMapping("safebusiness/addArticle/article/{id}")
	public String createOrViewArticleDuplicateHandler(Model model, @PathVariable("id") String id) {
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
	
	@PostMapping("safebusiness/addArticle/{action}")
	public String addArticle(@Valid Article article, @PathVariable("action") String action) {
		try {
			// Make updating possible
			// TODO This has to be done to all domains supporting view modes
			article.setId(Integer.parseInt(action));
						
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
		}
		List<Article> children = APIUtils.parseArticleString(article.getChildrenCommaSeparatedList(), articleRepo);
		article.setChildArticles(children);
		Article savedArticle = articleRepo.save(article);
		if (savedArticle != null) {
			return "redirect:article/" + savedArticle.getId();
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
			
	/*
	 * Attributes GetMappping and PostMapping
	 */
	@GetMapping("safebusiness/listAttributes")
	public String listAttributes(Model model) {
		Iterable<ActionAttribute> interator = attributeRepo.findAll();
		@SuppressWarnings("unchecked")
		List<ActionAttribute> attributes = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("attributes", attributes);
		return "listAttributes";
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
	
	/*
	 * Section GetMapping and PostMapping
	 *
	 */

	@GetMapping("safebusiness/section/{id}")
	public String createOrViewSection(Model model, @PathVariable("id") String id) {
		if (id != null) {
			try {
				Section section = APIUtils.getSectionById(Integer.parseInt(id), sectionRepo.findAll());
				if (section != null) {
					model.addAttribute("section", sectionRepo.findById(Integer.parseInt(id)).get());
					model.addAttribute("inViewMode", true);
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("section", new Section());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("section", new Section());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("section", new Section());
		}
		
		return "section";
	}
	
	@PostMapping("safebusiness/addSection/{string}")
	public String addSection(@Valid Section section, @PathVariable("string") String action, HttpServletResponse httpResponse) {
		try {
			// Make updating possible
			// TODO This has to be done to all domains supporting view modes
			section.setId(Integer.parseInt(action));
						
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
		}
		Section savedSection = sectionRepo.save(section);
		if (savedSection != null) {
			return "redirect:viewSection/" + savedSection.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	@GetMapping("safebusiness/listSections")
	public String listSections(Model model) {
		Iterable<Section> interator = sectionRepo.findAll();
		@SuppressWarnings("unchecked")
		List<Section> sections = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("sections", sections);
		return "listSections";
	}
		
	@GetMapping("safebusiness/addSection/viewSection/{id}")
	public String viewSectionDuplicateHandler(Model model, @PathVariable("id") String id) {
		if (id != null) {
			try {
				Section section = APIUtils.getSectionById(Integer.parseInt(id), sectionRepo.findAll());
				if (section != null) {
					model.addAttribute("section", sectionRepo.findById(Integer.parseInt(id)).get());
					model.addAttribute("inViewMode", true);
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("section", new Section());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("section", new Section());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("section", new Section());
		}
		
		return "section";
	}
	
	/*
	 * Action GetMapping and PostMapping Bellow
	 * 
	 */
	@GetMapping("safebusiness/listActions")
	public String listActions(Model model) {
		Iterable<Action> interator = actionRepo.findAll();
		@SuppressWarnings("unchecked")
		List<Action> actions = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("actions", actions);
		return "listAction";
	}
	@GetMapping("safebusiness/action/{id}")
	public String createOrViewAction(Model model, @PathVariable("id") String id) {
		if (id != null) {
			try {
				Action action = APIUtils.getActionById(Integer.parseInt(id), actionRepo.findAll());
				if (action != null) {
					model.addAttribute("action", action);
					model.addAttribute("inViewMode", true);
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("action", new Action());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("action", new Action());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("action", new Action());
		}
		
		return "action";
	}
	
	@PostMapping("safebusiness/addAction/{string}")
	public String addAction(@Valid Action action, @PathVariable("string") String routeAction, HttpServletResponse httpResponse) {
		try {
			// Make updating possible
			// TODO This has to be done to all domains supporting view modes
			action.setId(Integer.parseInt(routeAction));
						
		} catch(NumberFormatException ex) {
			ex.printStackTrace();
			// chill, stuff happens.
		}
		Action savedAction = actionRepo.save(action);
		if (savedAction != null) {
			return "redirect:viewAction/" + savedAction.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	// Another hack around here
	@GetMapping("safebusiness/addAction/viewAction/{id}")
	public String viewActionDuplicateHandler(Model model, @PathVariable("id") String id) {
		if (id != null) {
			try {
				actionRepo.findAll(); // Leave as is
				Action action = actionRepo.findById(Integer.parseInt(id)).isPresent() ? actionRepo.findById(Integer.parseInt(id)).get() : null;
				if (action != null) {
					model.addAttribute("action", action);
					model.addAttribute("inViewMode", true);
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("action", new Action());
				}
			} catch(Exception ex) {
				ex.printStackTrace();
				model.addAttribute("inViewMode", false);
				model.addAttribute("action", new Action());
			} 
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("action", new Action());
		}
		
		return "action";
	}
	/*
	 * Act GetMapping and PostMapping
	 */
	@GetMapping("safebusiness/listActs")
	public String listActs(Model model) {
		Iterable<Act> interator = actRepo.findAll();
		@SuppressWarnings("unchecked")
		List<Act> acts = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("acts", acts);
		return "listActs";
	}
	@GetMapping("safebusiness/act/{id}")
	public String createOrViewAct(Model model, @PathVariable("id") String id) {
		if (id != null) {
			try {
				Act act = APIUtils.getActById(Integer.parseInt(id), actRepo.findAll());
				if (act != null) {
					model.addAttribute("act", act);
					model.addAttribute("inViewMode", true);
					//model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("act", new Act());
					//model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("act", new Act());
				//model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("act", new Act());
			//model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
		}
		
		return "act";
	}
	
	@PostMapping("safebusiness/addAct/{string}")
	public String addAct(@Valid Act act, @PathVariable("string") String action, HttpServletResponse httpResponse) {
		try {
			// Make updating possible
			// TODO This has to be done to all domains supporting view modes
			act.setId(Integer.parseInt(action));
						
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
		}
		Act savedAct = actRepo.save(act);
		if (savedAct != null) {
			return "redirect:viewAct/" + savedAct.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	// Another hack around here
	@GetMapping("safebusiness/addAct/viewAct/{id}")
	public String viewActDuplicateHandler(Model model, @PathVariable("id") String id) {
		if (id != null) {
			try {
				Act act = APIUtils.getActById(Integer.parseInt(id), actRepo.findAll());
				if (act != null) {
					model.addAttribute("act", act);
					model.addAttribute("inViewMode", true);
					//model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("act", new Act());
					//model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("act", new Act());
				//model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("act", new Act());
			//model.addAttribute("dataTypes", Datatype.getSupportedDatatypes());
		}
		
		return "act";
	}
	
	
}
