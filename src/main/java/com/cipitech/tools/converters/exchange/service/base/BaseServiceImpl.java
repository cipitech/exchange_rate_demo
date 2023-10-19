package com.cipitech.tools.converters.exchange.service.base;

import com.cipitech.tools.converters.exchange.model.base.BaseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

	private void updateBaseInfo(T record)
	{
		long timeNowInMillis = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

		if (record.getId() == null)
		{
			record.setVersionNumber(1L);
			record.setInsertedAt(timeNowInMillis);
		}
		else
		{
			record.setUpdatedAt(timeNowInMillis);
		}
	}
}
