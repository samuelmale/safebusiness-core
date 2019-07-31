package org.safebusiness.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.Section;
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
}
