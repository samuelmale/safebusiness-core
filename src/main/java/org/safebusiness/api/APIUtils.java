package org.safebusiness.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
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
}
