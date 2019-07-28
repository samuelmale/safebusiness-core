package org.safebusiness.api;

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
}
