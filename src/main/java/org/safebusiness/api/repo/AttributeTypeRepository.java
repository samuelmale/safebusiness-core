package org.safebusiness.api.repo;

import org.safebusiness.AttributeType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AttributeTypeRepository extends CrudRepository<AttributeType, Integer> {

	@Query(
			value = "SELECT * FROM attribute_type a WHERE a.name = ?1", 
			nativeQuery = true)
	public AttributeType findByName(String name);
}
