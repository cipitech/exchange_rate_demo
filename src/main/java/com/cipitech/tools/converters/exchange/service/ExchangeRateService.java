package com.cipitech.tools.converters.exchange.service;

import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.model.ExchangeRate;
import com.cipitech.tools.converters.exchange.repository.ExchangeRateRepository;
import com.cipitech.tools.converters.exchange.service.base.BaseService;

import java.util.List;

public interface ExchangeRateService extends BaseService<ExchangeRate, ExchangeRateRepository>
{
	ExchangeRateDTO findFirstRateDTOAfterTime(String fromCurrencyCode, String toCurrencyCode, long timeMillis);

	boolean existForCurrencyAfterTime(String fromCurrencyCode, long timeMillis);

	void addNewRates(List<ExchangeRateDTO> rateList);
}
