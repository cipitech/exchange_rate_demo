package com.cipitech.tools.converters.exchange.service;

import com.cipitech.tools.converters.exchange.model.ExchangeRate;
import com.cipitech.tools.converters.exchange.repository.ExchangeRateRepository;
import com.cipitech.tools.converters.exchange.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExchangeRateServiceImpl extends BaseServiceImpl<ExchangeRate, ExchangeRateRepository> implements ExchangeRateService
{
	private final ExchangeRateRepository exchangeRateRepository;

	public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository)
	{
		this.exchangeRateRepository = exchangeRateRepository;
	}

	@Override
	public ExchangeRateRepository getRepository()
	{
		return exchangeRateRepository;
	}
}
