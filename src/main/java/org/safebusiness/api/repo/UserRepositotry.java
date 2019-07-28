package org.safebusiness.api.repo;

import org.safebusiness.User;
import org.springframework.data.repository.CrudRepository;

/**
 * User CRUD methods
 * @author Kinene
 */
public interface UserRepositotry extends CrudRepository<User, Integer> {

}
