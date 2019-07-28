package org.safebusiness.api.repo;

import org.safebusiness.Action;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring will auto implement this Service into a bean {@code actionRepository} that will
 * handle the CRUD operations on the {@link Action} domain object ie.
 * <code>
 * 		@Autowired
 * 		ActionRepository actionRepository; // Enough to use this service
 * </code>
 * @author samuel
 *
 */

public interface ActionRepository extends CrudRepository<Action, Integer> {

}
