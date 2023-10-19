package com.cipitech.tools.converters.exchange.service.base;

import com.cipitech.tools.converters.exchange.model.base.BaseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseService<T extends BaseRecord, R extends JpaRepository<T, Long>>
{
	R getRepository();

	T save(T record);

	List<T> saveAll(List<T> records);

	void removeAll();

	boolean exist();
}
