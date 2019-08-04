package org.safebusiness.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.safebusiness.Section;
import org.safebusiness.Act;
import org.safebusiness.Action;
import org.safebusiness.ActionAttribute;
import org.safebusiness.Article;
import org.safebusiness.Attribute;
import org.safebusiness.Procedure;
import org.safebusiness.User;
import org.safebusiness.api.repo.ActRepository;
import org.safebusiness.api.repo.ActionAttributeRepository;
import org.safebusiness.api.repo.ArticleRepository;
import org.safebusiness.api.repo.ProcedureRepository;
import org.safebusiness.api.repo.SectionRepository;
import org.safebusiness.web.controller.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines API Utility methods
 * @author samuel
 *
 */
public class APIUtils {
	
	private static Logger log = LoggerFactory.getLogger(MainController.class);

	public static boolean userPasswordIsCorrect(User user, String password) {
		return user.getPassword().equals(password);
	}

	@SuppressWarnings("unchecked")
	public static Section getSectionById(Integer id, Iterable<Section> it) {
		if (id == null || it == null) {
			return null;
		}
		Iterator<Section> iterator = it.iterator();
		List<Section> sections = iterator != null ? IteratorUtils.toList(iterator) : new ArrayList<>();
				
		for (Section sec : sections) {
			if (sec.getId() == id) {
				return sec;
			}
		}
		return null;
	}
	/*
	 * This is the Get actionattribute method
	 */
	@SuppressWarnings("unchecked")
	public static ActionAttribute getActionAttributeById(Integer id, Iterable<ActionAttribute> it) {
		if (id == null || it == null) {
			return null;
		}
		Iterator<ActionAttribute> iterator = it.iterator();
		
		List<ActionAttribute> attributes = iterator != null ? IteratorUtils.toList(iterator) : new ArrayList<>();
		
		for (ActionAttribute att : attributes) {
			if (att.getId() == id) {
				return att;
			}
		}


		return null;
	}
	/*
	 * This is the Get actionattribute method
	 */
	@SuppressWarnings("unchecked")
	public static Act getActById(Integer id, Iterable<Act> it) {
		if (id == null || it == null) {
			return null;
		}
		Iterator<Act> iterator = it.iterator();
		
		List<Act> acts = iterator != null ? IteratorUtils.toList(iterator) : new ArrayList<>();
		
		for (Act ac : acts) {
			if (ac.getId() == id) {
				return ac;
			}
		}


		return null;
	}
	/**
	 * Gets {@link Action} by id.
	 * 
	 * @param id
	 * @param it
	 * @return action
	 */
	@SuppressWarnings("unchecked")
	public static Action getActionById(Integer id, Iterable<Action> it) {
		if (id == null || it == null) {
			return null;
		}
		Iterator<Action> iterator = it.iterator();
		List<Action> actions = iterator != null ? IteratorUtils.toList(iterator) : new ArrayList<>();
		
		for (Action action : actions) {
			if (action.getId() == id) {
				return action;
			}
		}
		return null;
	}
	
	public static Procedure getProcedureById(Integer id, Iterable<Procedure> it) {
		if (id == null || it == null) {
			return null;
		}
		Iterator<Procedure> iterator = it.iterator();
		List<Procedure> procedures = iterator != null ? IteratorUtils.toList(iterator) : new ArrayList<>();
		
		for (Procedure procedure : procedures) {
			if (procedure.getId() == id) {
				return procedure;
			}
		}
		return null;
	}
	
	public static List<ActionAttribute> parseAttributeString(String val, ActionAttributeRepository attributeRepo) {
		List<ActionAttribute> ret = new ArrayList<>();
		// Return ASAP
		if (StringUtils.isBlank(val)) {
			return ret;
		}
		for(String name : parseCommaDelimitedStringToStringList(val)) {
			ActionAttribute att = attributeRepo.findByName(name);
			if (att != null) {
				ret.add(att);
			} else {
				log.warn("Couldn't find Attribute with name :", name);
			}
		}

		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Article> parseArticleString(String val, ArticleRepository articleRepo) {
		List<Article> ret = new ArrayList<>();
		List<Article> existingArticles = careFullyCastIterableToList(articleRepo.findAll());
		for(Integer num : parseStringToIntegerList(val)) {
			for (Article candidate : existingArticles) {
				if (candidate.getArticleNumber() == num) {
					ret.add(candidate);
					break;
				}
				
			}
		}

		return ret;	
	}

	@SuppressWarnings("unchecked")
	public static  List<Section> parseSectionString(String val, SectionRepository sectionRepo) {
		List<Section> ret = new ArrayList<>();
		if (val == null || sectionRepo == null) {
			return ret;
		}
		if (val.isEmpty()) {
			return ret;
		}
		List<Section> existingSections = careFullyCastIterableToList(sectionRepo.findAll());
		String [] names = val.split(",");
		for(String name : names) {
			for (Section candidate : existingSections) {
				if (candidate.getName().equalsIgnoreCase(name)) {
					ret.add(candidate);
					break;
				}
				
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static List<Act> parseActString(String val, ActRepository actRepo) {
		List<Act> ret = new ArrayList<>();
		// Return ASAP
		if (StringUtils.isBlank(val)) {
			return ret;
		}
		List<Act> existingActs = careFullyCastIterableToList(actRepo.findAll());
		for(String name : parseCommaDelimitedStringToStringList(val)) {
			for (Act candidate : existingActs) {
				if (candidate.getName().equalsIgnoreCase(name)) {
					ret.add(candidate);
					break;
				}
				
			}
		}

		return ret;	
	}
	
	@SuppressWarnings("unchecked")
	public static List<Procedure> parseProcedureString(String val, ProcedureRepository procedureRepo) {
		List<Procedure> ret = new ArrayList<>();
		// Return ASAP
		if (StringUtils.isBlank(val)) {
			return ret;
		}
		for(String name : parseCommaDelimitedStringToStringList(val)) {
			Procedure procedure = procedureRepo.findByName(name);
			if (procedure != null) {
				ret.add(procedure);
			} else {
				log.warn("Couldn't find Procedure with name :", name);
			}
		}

		return ret;	
	}
	
	public static List<Integer> parseStringToIntegerList(String val) {
		List<Integer> ints = new ArrayList<>();
		String[] stringIds;
		try {
			stringIds = val.split(",");
		} catch (PatternSyntaxException ex) {
			throw new IllegalArgumentException("Wrong mapping for child of list entities. Properly seperate Items in the list with commas");
		}
		for (String id : stringIds) {
			try {
				ints.add(Integer.parseInt(id));
			} catch(NumberFormatException ex) {
				log.error("Error parsing ID :", ex);
				// Just continue
				continue;
			}
		}
		return ints;
	}
	
	public static List<String> parseCommaDelimitedStringToStringList(String val) {
		List<String> strings = new ArrayList<>();
		String[] rawStrings;
		try {
			rawStrings = val.split(",");
		} catch (PatternSyntaxException ex) {
			throw new IllegalArgumentException("Wrong mapping for child of list entities. Properly seperate Items in the list with commas");
		}
		for (String string : rawStrings) {
			if (StringUtils.isBlank(string)) {
				continue;
			}
			string.trim();
			strings.add(string);
		}
		return strings;
	}
	
	public static List careFullyCastIterableToList(Iterable iterable) {
		if (iterable == null || iterable == null) {
			return null;
		}
		Iterator iterator = iterable.iterator();
		return iterator != null ? IteratorUtils.toList(iterator) : new ArrayList<>();
		
	}
	
}
