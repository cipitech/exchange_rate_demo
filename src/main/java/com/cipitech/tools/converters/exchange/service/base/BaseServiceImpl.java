package com.cipitech.tools.converters.exchange.service.base;

import com.cipitech.tools.converters.exchange.model.base.BaseRecord;
import com.cipitech.tools.converters.exchange.utils.DateUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseServiceImpl<T extends BaseRecord, R extends JpaRepository<T, Long>> implements BaseService<T, R>
{
	@Override
	public abstract R getRepository();

	@Override
	public T save(T record)
	{
		updateBaseInfo(record);

		return getRepository().save(record);
	}

	@Override
	public List<T> saveAll(List<T> records)
	{
		records.forEach(this::updateBaseInfo);

		return getRepository().saveAll(records);
	}

	@Override
	public void removeAll()
	{
		getRepository().deleteAll();
	}

	@Override
	public boolean exist()
	{
		return getRepository().count() > 0;
	}

	/**
	 * Before saving an entity to the database we must set some core attributes.
	 *
	 * @param record the entity to update
	 */
	private void updateBaseInfo(T record)
	{
		long timeNowInMillis = DateUtils.currentTimeInMillis();

		// If record is new
		if (record.getId() == null)
		{
			record.setVersionNumber(1L);
			record.setInsertedAt(timeNowInMillis);
		}
		// If record already exists in the database
		else
		{
			record.setUpdatedAt(timeNowInMillis);
		}
	}
}
