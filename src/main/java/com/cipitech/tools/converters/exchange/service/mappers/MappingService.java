package com.cipitech.tools.converters.exchange.service.mappers;

import com.cipitech.tools.converters.exchange.dto.base.BaseRecordDTO;
import com.cipitech.tools.converters.exchange.model.base.BaseRecord;

public interface MappingService<T extends BaseRecord, R extends BaseRecordDTO>
{
	R toDTO(T entity);

	T toEntity(R dto);
}
