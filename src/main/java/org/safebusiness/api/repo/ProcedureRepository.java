package org.safebusiness.api.repo;

import org.safebusiness.Procedure;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring will auto implement this Service into a bean {@code procedureRepository} that will
 * handle the CRUD operations on the {@link Procedure} domain object ie.
 * <code>
 * 		@Autowired
 * 		ProcedureRepository procedureRepository; // Enough to use this service
 * </code>
 * @author samuel
 *
 */
public interface ProcedureRepository extends CrudRepository<Procedure, Integer> {

}
