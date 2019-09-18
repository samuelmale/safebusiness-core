package org.safebusiness.web.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.safebusiness.Act;
import org.safebusiness.Action;
import org.safebusiness.ActionAttribute;
import org.safebusiness.Article;
import org.safebusiness.AttributeType;
import org.safebusiness.Document;
import org.safebusiness.Procedure;
import org.safebusiness.ProcedureTemplate;
import org.safebusiness.Process;
import org.safebusiness.Section;
import org.safebusiness.api.APIUtils;
import org.safebusiness.api.Context;
import org.safebusiness.api.repo.ActRepository;
import org.safebusiness.api.repo.ActionAttributeRepository;
import org.safebusiness.api.repo.ActionRepository;
import org.safebusiness.api.repo.ArticleRepository;
import org.safebusiness.api.repo.AttributeTypeRepository;
import org.safebusiness.api.repo.DocumentRepository;
import org.safebusiness.api.repo.ProcedureRepository;
import org.safebusiness.api.repo.ProcedureTemplateRepository;
import org.safebusiness.api.repo.ProcessRepository;
import org.safebusiness.api.repo.SectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	Logger log = LoggerFactory.getLogger(MainController.class);

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
	@Autowired
	ProcedureRepository procedureRepo;
	@Autowired
	ProcessRepository processRepo;
	@Autowired
	Context applicationContext;
	@Autowired
	AttributeTypeRepository attTypeRepo;
	@Autowired
	DocumentRepository documentRepo;
	@Autowired
	ProcedureTemplateRepository templateRepo;
	
	@GetMapping("safebusiness/index")
	public String index(HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		return "index";
	}
	
	@GetMapping("safebusiness")
	public String baseUrl(HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		return "redirect:safebusiness/index";
	}
	
	////////////////////////////////////////////
	//Login
	///////////////////////////////////////////
	
	@GetMapping("safebusiness/login")
	public String getLogin() {
		return "login";
	}
	
	@PostMapping("safebusiness/login")
	public String login(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		HttpSession session,
		ModelMap modelMap) {
		if(applicationContext.authenticate(username, password)) {
			session.setAttribute("username", username);
			return "redirect:/safebusiness/index";
		} else {
			modelMap.put("error", "Invalid Account");
			return "safebusiness/login";
		}
	}

	@GetMapping("safebusiness/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		// TODO : Logout from the applicationContext
		return "redirect:/safebusiness/login";
	}
	
	/////////////////////////////////////////////
	// Article
	////////////////////////////////////////////
	
	@GetMapping("safebusiness/article/{id}")
	public String createOrViewArticle(Model model, @PathVariable("id") String id, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		if (id != null) {
			try {
				if (articleRepo.findById(Integer.parseInt(id)).isPresent()) {
					Article article = articleRepo.findById(Integer.parseInt(id)).get();
					// Hack around to have `stringId` initialized
					// Tired of writing dirty code :-(
					article.getId();
					model.addAttribute("article", article);
					model.addAttribute("inViewMode", true);
					// children
					model.addAttribute("articles", article.getChildArticles() != null ? article.getChildArticles() : new ArrayList<>());
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
	public String createOrViewArticleDuplicateHandler(Model model, @PathVariable("id") String id, HttpSession session) {
		return createOrViewArticle(model, id, session);
	}
	
	@PostMapping("safebusiness/addArticle/{action}")
	public String addArticle(@Valid Article article, @PathVariable("action") String action,@RequestBody MultiValueMap<String, String> formData) {
		try {
			Integer id = Integer.parseInt(action);
			article = articleRepo.findById(id).get();
			String articleValue  = formData.getFirst("articlevalue");
			String articleNumberString = formData.getFirst("articlenumber");
			String translation = formData.getFirst("basicTranslation");
			System.out.println("Trans: " + translation);
			String childrenCommaSeparatedList  = formData.getFirst("childrenCommaSeparatedList");
			
			if (StringUtils.isNotBlank(articleValue)) {
				article.setValue(articleValue);
			}
			if (StringUtils.isNotBlank(articleNumberString)) {
				try {
					Integer articleNumber = Integer.parseInt(articleNumberString);
					article.setArticleNumber(articleNumber);
				} catch(NumberFormatException ex) {}
			}
			if (StringUtils.isNotBlank(translation)) {
				article.setBasicTranslation(translation);
			}
			if (StringUtils.isNotBlank(childrenCommaSeparatedList)) {
				List<Article> children = APIUtils.parseArticleString(childrenCommaSeparatedList, articleRepo);
				for (Article child : children) {
					child.setParent(article);
				}
				article.setChildArticles(children);
			}	
			Article savedArticle = articleRepo.save(article);
			if (savedArticle != null) {
				return "redirect:article/" + savedArticle.getId();
			}	
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
		}
		
		List<Article> children = APIUtils.parseArticleString(article.getChildrenCommaSeparatedList(), articleRepo);
		for (Article child : children) {
			child.setParent(article);
		}
		article.setChildArticles(children);
		Article savedArticle = articleRepo.save(article);
		if (savedArticle != null) {
			return "redirect:article/" + savedArticle.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	@GetMapping("safebusiness/listArticles")
	public String listArticles(Model model, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		Iterable<Article> interator = articleRepo.findAll();
		@SuppressWarnings("unchecked")
		List<Article> articles = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("articles", articles);
		return "listArticles";
	}
	
	/////////////////////////////////////////////
	// AttributeType
	////////////////////////////////////////////
	
	@GetMapping("safebusiness/listAttributeTypes")
	public String listAttributeTypes(Model model, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		Iterable<AttributeType> interator = attTypeRepo.findAll();
		@SuppressWarnings("unchecked")
		List<AttributeType> attributeTypes = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("attributeTypes", attributeTypes);
		return "listAttributeTypes";
	}
	
	@GetMapping("safebusiness/attributeType/{id}")
	public String createOrViewAttributeType(Model model, @PathVariable("id") String id, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		if (id != null) {
			try {
				AttributeType type = attTypeRepo.findById(Integer.parseInt(id)).get();
				if (type != null) {
					// Make sure `stringId` is set
					// I hate this, I think a better strategy should be bought
					type.getId();
					model.addAttribute("attributeType", type);
					model.addAttribute("inViewMode", true);
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("attributeType", new AttributeType());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("attributeType", new AttributeType());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("attributeType", new AttributeType());
		}
		
		return "attributeType";
	}
	
	@PostMapping("safebusiness/addAttributeType/{string}")
	public String addAttributeType(@Valid AttributeType type, @PathVariable("string") String action) {
		try {
			AttributeType cache = type;
			type = attTypeRepo.findById(Integer.parseInt(action)).get();
			if (StringUtils.isNotBlank(cache.getName())) {
				type.setName(cache.getName());
			}
			if (StringUtils.isNotBlank(cache.getDataTypeString())) {
				type.setDataTypeString(cache.getDataTypeString());
			}
			AttributeType savedtype = attTypeRepo.save(type);
			if (savedtype != null) {
				return "redirect:/safebusiness/attributeType/" + savedtype.getId();
			}			
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
		}		
		AttributeType savedtype = attTypeRepo.save(type);
		if (savedtype != null) {
			return "redirect:/safebusiness/attributeType/" + savedtype.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	// Another hack around here
	@GetMapping("safebusiness/addAttribute/viewAttribute/{id}")
	public String viewAttributeTypeDuplicateHandler(Model model, @PathVariable("id") String id, HttpSession session) {
		return createOrViewAttributeType(model, id, session);
	}
	
	/////////////////////////////////////////////
	// Section
	////////////////////////////////////////////
	
	@GetMapping("safebusiness/section/{id}")
	public String createOrViewSection(Model model, @PathVariable("id") String id, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		if (id != null) {
			try {
				Section section = APIUtils.getSectionById(Integer.parseInt(id), sectionRepo.findAll());
				if (section != null) {
					Section sec = sectionRepo.findById(Integer.parseInt(id)).get();
					model.addAttribute("section", sec);
					model.addAttribute("inViewMode", true);
					model.addAttribute("articles", sec.getArticles() != null ? sec.getArticles() : new ArrayList<>());
					model.addAttribute("sections", sec.getSubSections() != null ? sec.getSubSections() : new ArrayList<>());
					
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
	public String addSection(@Valid Section section, @PathVariable("string") String action, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		try {
			Section cache = section;
			section = sectionRepo.findById((Integer.parseInt(action))).get();
			if (StringUtils.isNotBlank(cache.getName())) {
				section.setName(cache.getName());
			}
			List<Article> articles = APIUtils.parseArticleString(cache.getArticleCommaSeparatedList(), articleRepo);
			for (Article article : articles) {
				article.setSection(section);
			}
			section.setArticles(articles);
			List<Section> subSecs = APIUtils.parseSectionString(cache.getChildrenCommaSeparatedList(), sectionRepo);
			for (Section sec : subSecs) {
				sec.setParent(section);
			}
			section.setSubSections(subSecs);
			Section savedSection = sectionRepo.save(section);
			if (savedSection != null) {
				return "redirect:viewSection/" + savedSection.getId();
			}			
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
		}
		List<Article> articles = APIUtils.parseArticleString(section.getArticleCommaSeparatedList(), articleRepo);
		for (Article article : articles) {
			article.setSection(section);
		}
		section.setArticles(articles);
		List<Section> subSecs = APIUtils.parseSectionString(section.getChildrenCommaSeparatedList(), sectionRepo);
		for (Section sec : subSecs) {
			sec.setParent(section);
		}
		section.setSubSections(subSecs);
		Section savedSection = sectionRepo.save(section);
		if (savedSection != null) {
			return "redirect:viewSection/" + savedSection.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	@GetMapping("safebusiness/listSections")
	public String listSections(Model model, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		Iterable<Section> interator = sectionRepo.findAll();
		@SuppressWarnings("unchecked")
		List<Section> sections = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("sections", sections);
		return "listSections";
	}
		
	@GetMapping("safebusiness/addSection/viewSection/{id}")
	public String viewSectionDuplicateHandler(Model model, @PathVariable("id") String id, HttpSession session) {
		return createOrViewSection(model, id, session);
	}
	
	/////////////////////////////////////////////
	// Action
	////////////////////////////////////////////
	
	@GetMapping("safebusiness/listActions")
	public String listActions(Model model, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		Iterable<Action> interator = actionRepo.findAll();
		@SuppressWarnings("unchecked")
		List<Action> actions = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("actions", actions);
		return "listActions";
	}
	
	@GetMapping("safebusiness/action/{id}")
	public String createOrViewAction(Model model, @PathVariable("id") String id, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		if (id != null) {
			try {
				Action action = APIUtils.getActionById(Integer.parseInt(id), actionRepo.findAll());
				if (action != null) {
					model.addAttribute("action", action);
					model.addAttribute("inViewMode", true);
					model.addAttribute("attributeTypes", action.getAttributeTypes());
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
	public String addAction(@Valid Action action, @PathVariable("string") String routeAction) {
		try {
			Action cache = action;
			action = actionRepo.findById((Integer.parseInt(routeAction))).get();
			if (StringUtils.isNotBlank(cache.getName())) {
				action.setName(cache.getName());
			}
			if (StringUtils.isNotBlank(cache.getInstructionString())) {
				action.setInstructionString(cache.getInstructionString());
			}
			List<AttributeType> attributeTypes = APIUtils.parseAttributeTypeString(cache.getAttributeNamesString(), attTypeRepo);
			for (AttributeType att : attributeTypes) {
				att.setAction(action);
			}
			action.setAttributeTypes(attributeTypes);
			Action savedAction = actionRepo.save(action);
			if (savedAction != null) {
				return "redirect:viewAction/" + savedAction.getId();
			}			
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
		}
		List<AttributeType> attributeTypes = APIUtils.parseAttributeTypeString(action.getAttributeNamesString(), attTypeRepo);
		for (AttributeType att : attributeTypes) {
			att.setAction(action);
		}
		action.setAttributeTypes(attributeTypes);
		Action savedAction = actionRepo.save(action);
		if (savedAction != null) {
			return "redirect:viewAction/" + savedAction.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	// Another hack around here
	@GetMapping("safebusiness/addAction/viewAction/{id}")
	public String viewActionDuplicateHandler(Model model, @PathVariable("id") String id, HttpSession session) {		
		return createOrViewAction(model, id, session);
	}
	
	/////////////////////////////////////////////
	// Act
	////////////////////////////////////////////
	
	@GetMapping("safebusiness/listActs")
	public String listActs(Model model, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		Iterable<Act> interator = actRepo.findAll();
		@SuppressWarnings("unchecked")
		List<Act> acts = interator != null ? IteratorUtils.toList(interator.iterator()) : new ArrayList<>();
		model.addAttribute("acts", acts);
		return "listActs";
	}
	
	@GetMapping("safebusiness/act/{id}")
	public String createOrViewAct(Model model, @PathVariable("id") String id, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		if (id != null) {
			try {
				Act act = APIUtils.getActById(Integer.parseInt(id), actRepo.findAll());
				if (act != null) {
					model.addAttribute("act", act);
					model.addAttribute("inViewMode", true);
					model.addAttribute("sections", act.getSections() != null ? act.getSections() : new ArrayList<>());
					// Make sure `stringId` is initialized
					act.getId();
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("act", new Act());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("act", new Act());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("act", new Act());
		}
		
		return "act";
	}
	
	@PostMapping("safebusiness/addAct/{string}")
	public String addAct(@Valid Act act, @PathVariable("string") String action, HttpServletResponse httpResponse) {
		try {
			Act cache = act;
			act = actRepo.findById(Integer.parseInt(action)).get();
			if (StringUtils.isNotBlank(cache.getName())) {
				act.setName(cache.getName());
			}
			List<Section> sections = APIUtils.parseSectionString(cache.getSectionListString(), sectionRepo);
			for (Section sec : sections) {
				sec.setAct(act);
			}
			act.setSections(sections);
			Act savedAct = actRepo.save(act);
			if (savedAct != null) {
				return "redirect:viewAct/" + savedAct.getId();
			}
			
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
		}
		List<Section> sections = APIUtils.parseSectionString(act.getSectionListString(), sectionRepo);
		for (Section sec : sections) {
			sec.setAct(act);
		}
		act.setSections(sections);
		Act savedAct = actRepo.save(act);
		if (savedAct != null) {
			return "redirect:viewAct/" + savedAct.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	// Another hack around here
	@GetMapping("safebusiness/addAct/viewAct/{id}")
	public String viewActDuplicateHandler(Model model, @PathVariable("id") String id, HttpSession session) {		
		return createOrViewAct(model, id, session);
	}
	
	////////////////////////////////////
	// Procedure
	///////////////////////////////////
	
	@GetMapping("safebusiness/procedure/{id}")
	public String createOrViewProcedure(Model model, @PathVariable("id") String id,HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		if (id != null) {
			try {
				Procedure procedure = procedureRepo.findById(Integer.parseInt(id)).get();
				if (procedure != null) {
					model.addAttribute("procedure", procedure);
					model.addAttribute("inViewMode", true);
					model.addAttribute("acts", procedure.getActs() != null ? procedure.getActs() : new ArrayList<>());
					model.addAttribute("action", procedure.getAction());
					// Make sure stringId is set
					procedure.getId();
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("procedure", new Procedure());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("procedure", new Procedure());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("procedure", new Procedure());
		}
		
		return "procedure";
	}
	
	@GetMapping("safebusiness/addProcedure/viewProcedure/{id}")
	public String createOrViewDuplicateProcedureRouteHandler(Model model, @PathVariable("id") String id,HttpSession session) {
		return createOrViewProcedure(model, id, session);
	}
	
	@PostMapping("safebusiness/addProcedure/{string}")
	public String addProcedure(@Valid Procedure procedure, @PathVariable("string") String action) {
		try {
			Procedure cache = procedure;
			procedure = procedureRepo.findById(Integer.parseInt(action)).get();
			if (StringUtils.isNotBlank(cache.getName())) {
				procedure.setName(cache.getName());
			}
			List<Act> acts = APIUtils.parseActString(cache.getActNamesString(), actRepo);
			for (Act act : acts) {
				act.setProcedure(procedure);
			}
			if (!acts.isEmpty()) {
				procedure.setActs(acts);
			} 
			// set action
			if (StringUtils.isNotBlank(cache.getActionName())) {
				Action ac = actionRepo.findByName(cache.getActionName());
				if (ac != null) {
					ac.setProcedure(procedure);
					procedure.setAction(ac);
				} else {
					log.error("Failed to find Action :" + cache.getActionName());
				}
			}
			
			Procedure savedProcedure = procedureRepo.save(procedure);
			if (savedProcedure != null) {
				return "redirect:viewProcedure/" + savedProcedure.getId();
			}
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
		}
		List<Act> acts = APIUtils.parseActString(procedure.getActNamesString(), actRepo);
		for (Act act : acts) {
			act.setProcedure(procedure);
		}
		if (!acts.isEmpty()) {
			// TODO : https://trello.com/c/V3htZorj/16-in-controller-just-append-an-item-to-the-list-instead-of-just-calling-setlistname
			procedure.setActs(acts);
		} 
		// set action
		if (StringUtils.isNotBlank(procedure.getActionName())) {
			Action ac = actionRepo.findByName(procedure.getActionName());
			if (ac != null) {
				ac.setProcedure(procedure);
				procedure.setAction(ac);
			} else {
				log.error("Failed to find Action :" + procedure.getActionName());
			}
		}
		
		Procedure savedProcedure = procedureRepo.save(procedure);
		if (savedProcedure != null) {
			return "redirect:viewProcedure/" + savedProcedure.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	@GetMapping("safebusiness/listProcedures")
	public String listProcedures(Model model,HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		List<Procedure> procedures = APIUtils.careFullyCastIterableToList(procedureRepo.findAll());
		model.addAttribute("procedures", procedures);
		return "listProcedures";
	}
	
	///////////////////////////////////////////
	// Process
	//////////////////////////////////////////
	
	@GetMapping("safebusiness/process/{id}")
	public String createOrViewProcess(Model model, @PathVariable("id") String id, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		if (id != null) {
			try {
				Process process = processRepo.findById(Integer.parseInt(id)).get();
				if (process != null) {
					model.addAttribute("process", process);
					model.addAttribute("inViewMode", true);
					model.addAttribute("procedures", process.getProcedures() != null ? process.getProcedures() : new ArrayList<>());
					// Make sure stringId is set
					process.getId();
				} else {
					model.addAttribute("inViewMode", false);
					model.addAttribute("process", new Process());
				}
			} catch(NumberFormatException ex) {
				model.addAttribute("inViewMode", false);
				model.addAttribute("process", new Process());
			}
			
		} else {
			model.addAttribute("inViewMode", false);
			model.addAttribute("process", new Process());
		}
		
		return "process";
	}
	
	@GetMapping("safebusiness/addProcess/viewProcess/{id}")
	public String createOrViewDuplicateProcessRouteHandler(Model model, @PathVariable("id") String id, HttpSession session) {
		return createOrViewProcess(model, id, session);
	}
	
	@PostMapping("safebusiness/addProcess/{string}")
	public String addProcess(@Valid Process process, @PathVariable("string") String action) {
		try {
			Process cache = process;
			Integer id = Integer.parseInt(action);
			process = processRepo.findById(id).get();
			List<Procedure> procedures = APIUtils.parseProcedureString(cache.getProcedureNames(), procedureRepo);
			for (Procedure proc : procedures) {
				proc.setProcess(process);
			}
			if (!procedures.isEmpty()) {
				process.setProcedures(procedures);
			} else {
				log.warn("Found no Procedures for Process:" + cache.getProcessName());
			}
			
			Process savedProcess = processRepo.save(process);
			if (savedProcess != null) {
				return "redirect:viewProcess/" + savedProcess.getId();
			}			
		} catch(NumberFormatException ex) {
			// chill, stuff happens.
			log.error("Error parsing ID", ex);
		}
		List<Procedure> procedures = APIUtils.parseProcedureString(process.getProcedureNames(), procedureRepo);
		for (Procedure proc : procedures) {
			proc.setProcess(process);
		}
		if (!procedures.isEmpty()) {
			process.setProcedures(procedures);
		} else {
			log.warn("Found no Procedures for Process:" + process.getProcessName());
		}
		
		Process savedProcess = processRepo.save(process);
		if (savedProcess != null) {
			return "redirect:viewProcess/" + savedProcess.getId();
		}
		return "redirect:safebusiness/index";
	}
	
	@GetMapping("safebusiness/listProcesses")
	public String listProcess(Model model) {
		List<Process> processes = APIUtils.careFullyCastIterableToList(processRepo.findAll());
		model.addAttribute("processes", processes);
		return "listProcesses";
	}
	
	//////////////////////////////////////
	// Document
	/////////////////////////////////////
	
	@GetMapping("safebusiness/newDocument")
	public String createDocument(Model model, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		Document document = new Document();
		model.addAttribute("document", document);
		model.addAttribute("processes", (List<Process>)APIUtils.careFullyCastIterableToList(processRepo.findAll()));
		return "newDocument";
	}
	
	@PostMapping("safebusiness/addDocument")
	public String addDocument(Model model, @Valid Document document) {
		Process process = processRepo.findByName(document.getProcessName());
		process.getDocuments().add(document);
		document.setupTemplates(process, attributeRepo, templateRepo);
		for (ProcedureTemplate template : document.getTemplates()) {
			template.setDocument(document);
		}
		document = documentRepo.save(document);
		return "redirect:/safebusiness/viewDocument/" + document.getId();
	}
	
	@PostMapping("safebusiness/updateDocument")
	public String updateDocument(Model model, @Valid Document document, @RequestBody MultiValueMap<String, String> formData, 
			@RequestParam("documentId") String docId, @RequestParam("attributeBatchSize") String attributeBatchSize) {
		try {
			document = documentRepo.findById(Integer.parseInt(docId)).get();
			String firstName = formData.getFirst("firstName");
			String lastName = formData.getFirst("lastName");
			if (StringUtils.isNotBlank(firstName)) {
				document.setClientFirstName(firstName);
			}
			if (StringUtils.isNotBlank(lastName)) {
				document.setClientLastName(lastName);;
			}
			// Save Attributes
			APIUtils.saveAttributesFromDocument(formData, Integer.parseInt(attributeBatchSize), attributeRepo);
			documentRepo.save(document);
		} catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Invalid Document ID :" + docId);
		}
		return "redirect:/safebusiness/viewDocument/" + document.getId();
	}
	
	@GetMapping("safebusiness/viewDocument/{id}")
	public String viewDocument(Model model, @PathVariable("id") String id, HttpSession session) {
		if (!isAuthenticatedSession(session)) {
			return "redirect:/safebusiness/login";
		}
		try {
			Integer docId = Integer.parseInt(id);
			Document doc = documentRepo.findById(docId).get();
	        Set<ProcedureTemplate> templatesWithoutDuplicates = new HashSet<ProcedureTemplate>(doc.getTemplates());
	        int attributeBatchSize = 0;
	        for (ProcedureTemplate template : templatesWithoutDuplicates) {
	        	List<ActionAttribute> atts = template.getAttributes();
	        	if (atts != null) {
	        		attributeBatchSize = attributeBatchSize + atts.size();
	        	}
	        }
	        doc.updateActRefsInDoc();
			model.addAttribute("document", doc);
			model.addAttribute("templates", templatesWithoutDuplicates);
			model.addAttribute("attributeBatchSize", attributeBatchSize);
			
		} catch(NumberFormatException ex) {
		}
		// TODO : A lot more work is required
		
		return "document";
	}
	
	@GetMapping("safebusiness/listDocuments")
	public String listDocuments(Model model) {
		List<Document> docs = APIUtils.careFullyCastIterableToList(documentRepo.findAll());
		model.addAttribute("documents", docs);
		return "listDocuments";
	}
	
	// Checks whether current HttpSession is authenticated
	private boolean isAuthenticatedSession(HttpSession session) {
	      return session.getAttribute("username") != null;
	}

}
