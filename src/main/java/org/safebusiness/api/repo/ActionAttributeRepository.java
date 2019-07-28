package org.safebusiness.api.repo;

import org.safebusiness.ActionAttribute;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring will auto implement this Service into a bean {@code actionAttributeRepository} that will
 * handle the CRUD operations on the {@link ActionAttribute} domain object ie.
 * <code>
 * 		@Autowired
 * 		ActionAttributeRepository actionAttributeRepository; // Enough to use this service
 * </code>
 * @author samuel
 *
 */
public interface ActionAttributeRepository extends CrudRepository<ActionAttribute, Integer> {

}
