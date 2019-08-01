package org.safebusiness.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.Section;
import org.safebusiness.Action;
import org.safebusiness.ActionAttribute;
import org.safebusiness.Article;
import org.safebusiness.User;
import org.safebusiness.api.repo.ArticleRepository;
import org.safebusiness.api.repo.SectionRepository;

/**
 * Defines API Utility methods
 * @author samuel
 *
 */
public class APIUtils {
	
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
	
	@SuppressWarnings("unchecked")
	public static List<Article> parseArticleString(String val, ArticleRepository articleRepo) {
		return careFullyCastIterableToList(articleRepo.findAllById(parseStringToIntegerList(val)));	
	}

	@SuppressWarnings("unchecked")
	public static  List<Section> parseSectionString(String val, SectionRepository sectionRepo) {
		return careFullyCastIterableToList(sectionRepo.findAllById(parseStringToIntegerList(val)));
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
				// Just continue
				continue;
			}
		}
		return ints;
	}
	
	public static List careFullyCastIterableToList(Iterable iterable) {
		if (iterable == null || iterable == null) {
			return null;
		}
		Iterator iterator = iterable.iterator();
		return iterator != null ? IteratorUtils.toList(iterator) : new ArrayList<>();
		
	}
	
}
