package org.safebusiness.api.repo;

import org.springframework.data.repository.CrudRepository;
import org.safebusiness.Process;

/**
 * Spring will auto implement this Service into a bean {@code processRepository} that will
 * handle the CRUD operations on the {@link Process} domain object ie.
 * <code>
 * 		@Autowired
 * 		ProcessRepository processRepository; // Enough to use this service
 * </code>
 * @author samuel
 *
 */

public interface ProcessRepository extends CrudRepository<Process, Integer> {

}
