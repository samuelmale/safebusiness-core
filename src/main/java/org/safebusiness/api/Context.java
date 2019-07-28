package org.safebusiness.api;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
import org.safebusiness.User;
import org.safebusiness.api.repo.UserRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Represents a SafeBusiness {@code Context} which maybe used to authenticate a {@link User} and 
 * also obtain services or any Spring bean in-order to interact with the API
 * 
 * @author samuel
 *
 */
@Component
public class Context implements ApplicationContextAware {
	
	/**
	 * Spring's ApplicationContext
	 */
	private ApplicationContext applicationContext;
	
	/**
	 * User object containing details about the authenticated user
	 */
	private User currentUser;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * Authenticates a {@link User} into application context.
	 * 
	 * @param login the {@code userName} or {@code phoneNumber}
	 * @param password 
	 * @return true if successful 
	 * @return false on failure
	 * @should authenticate User with userName or phoneNumber
	 */
	public boolean authenticate(String login, String password) {
		// TODO : Expand on the User service, we should be able to query users by name, phone numbers etc...
		Iterator<User> iterator = userRepo.findAll() != null ? userRepo.findAll().iterator() : null;
		if (iterator == null) {
			throw new RuntimeException("No Users Available!");
		}
		List<User> users = IteratorUtils.toList(iterator);
		for (User candidate : users) {
			if (candidate.getUsername().equals(login)) {
				if (APIUtils.userPasswordIsCorrect(candidate, password)) {
					this.currentUser = candidate;
					return true;
				}
			} else if (candidate.getPhoneNumber().equals(login)) {
				if (APIUtils.userPasswordIsCorrect(candidate, password)) {
					this.currentUser = candidate;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Removes a User from the {@code applicationContext}.
	 */
	public void logout(User user) {
		// tentative equality check
		if (isAuthenticatedContext()) {
			if (user.getId() == currentUser.getId()) {
				currentUser = null;
			}
		}
	}
	
	/**
	 * @return true if we have an authenticated {@code User} in Context; else false
	 */
	public boolean isAuthenticatedContext() {
		return currentUser != null;
	}
}
