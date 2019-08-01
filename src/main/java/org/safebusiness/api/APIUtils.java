package org.safebusiness.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.Section;
import org.safebusiness.Action;
import org.safebusiness.ActionAttribute;
import org.safebusiness.Article;
import org.safebusiness.User;

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
	
}
