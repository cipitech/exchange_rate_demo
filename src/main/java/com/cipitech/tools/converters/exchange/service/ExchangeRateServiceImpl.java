package com.cipitech.tools.converters.exchange.service;

import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.model.ExchangeRate;
import com.cipitech.tools.converters.exchange.repository.ExchangeRateRepository;
import com.cipitech.tools.converters.exchange.service.base.BaseServiceImpl;
import com.cipitech.tools.converters.exchange.service.mappers.ExchangeRateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class ExchangeRateServiceImpl extends BaseServiceImpl<ExchangeRate, ExchangeRateRepository> implements ExchangeRateService
{
	private final ExchangeRateRepository exchangeRateRepository;
	private final ExchangeRateMapper     exchangeRateMapper;

	public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository, ExchangeRateMapper exchangeRateMapper)
	{
		this.exchangeRateRepository = exchangeRateRepository;
		this.exchangeRateMapper = exchangeRateMapper;
	}

	@Override
	public ExchangeRateRepository getRepository()
	{
		return exchangeRateRepository;
	}

	@Override
	public ExchangeRateDTO findFirstRateDTOAfterTime(String fromCurrencyCode, String toCurrencyCode, long timeMillis)
	{
		return exchangeRateMapper.toDTO(getRepository().findFirstRateAfterTime(fromCurrencyCode, toCurrencyCode, timeMillis));
	}

	@Override
	public boolean existForCurrencyAfterTime(String fromCurrencyCode, long timeMillis)
	{
		return getRepository().countByFromCurrencyAfterTime(fromCurrencyCode, timeMillis) > 0;
	}

	@Override
	public void addNewRates(List<ExchangeRateDTO> rateList)
	{
		if (!CollectionUtils.isEmpty(rateList))
		{
			saveAll(rateList.stream().map(exchangeRateMapper::toEntity).toList());
		}
	}
}
