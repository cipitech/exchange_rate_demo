package com.cipitech.tools.converters.exchange.service.mappers;

import com.cipitech.tools.converters.exchange.dto.base.BaseRecordDTO;
import com.cipitech.tools.converters.exchange.model.base.BaseRecord;

/**
 * A service that converts hibernate records to serializable DTOs
 * and vice versa
 *
 * @param <T> the hibernate record
 * @param <R> the DTO record
 */
public interface MappingService<T extends BaseRecord, R extends BaseRecordDTO>
{
	/**
	 * Convert a record from hibernate to DTO
	 *
	 * @param entity the hibernate record
	 * @return the dto record
	 */
	R toDTO(T entity);

	/**
	 * Convert a record from hibernate to DTO
	 * and include the BaseRecord info (id, insertedAt, updatedAt etc.)
	 *
	 * @param entity the hibernate record
	 * @return the dto record
	 */
	R toBaseDTO(T entity);

	/**
	 * Convert a record from DTO to hibernate
	 *
	 * @param dto the dto record
	 * @return the hibernate record
	 */
	T toEntity(R dto);
}
