package com.cipitech.tools.converters.exchange.service.base;

import com.cipitech.tools.converters.exchange.model.base.BaseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * A service that is shared among all hibernate entities
 *
 * @param <T> the hibernate entity
 * @param <R> the repository of the hibernate entity
 */

public interface BaseService<T extends BaseRecord, R extends JpaRepository<T, Long>>
{
	/**
	 * Get the repository of the hibernate entity
	 *
	 * @return a JPARepository
	 */
	R getRepository();

	/**
	 * Save an entity to the database.
	 *
	 * @param record the entity to save
	 * @return the saved entity
	 */
	T save(T record);

	/**
	 * Save a list of entities to the database all at once
	 *
	 * @param records the entities to save
	 * @return the saved entities
	 */
	List<T> saveAll(List<T> records);

	/**
	 * Remove all saved entities from the database table
	 */
	void removeAll();

	/**
	 * Check if a database table is empty or not.
	 *
	 * @return true if the table is not empty, otherwise false
	 */
	boolean exist();
}
