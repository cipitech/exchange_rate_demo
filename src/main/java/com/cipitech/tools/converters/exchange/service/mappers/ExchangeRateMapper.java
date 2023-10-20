package com.cipitech.tools.converters.exchange.service.mappers;

import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.model.ExchangeRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExchangeRateMapper implements MappingService<ExchangeRate, ExchangeRateDTO>
{
	private final CurrencyMapper currencyMapper;

	public ExchangeRateMapper(CurrencyMapper currencyMapper)
	{
		this.currencyMapper = currencyMapper;
	}

	@Override
	public ExchangeRateDTO toDTO(ExchangeRate rate)
	{
		if (rate == null)
		{
			return null;
		}

		return ExchangeRateDTO.builder()
				.fromCurrency(currencyMapper.toDTO(rate.getFromCurrency()))
				.toCurrency(currencyMapper.toDTO(rate.getToCurrency()))
				.rate(rate.getRate()).build();
	}

	@Override
	public ExchangeRate toEntity(ExchangeRateDTO rateDTO)
	{
		if (rateDTO == null)
		{
			return null;
		}

		return ExchangeRate.builder()
				.fromCurrency(currencyMapper.toEntity(rateDTO.getFromCurrency()))
				.toCurrency(currencyMapper.toEntity(rateDTO.getToCurrency()))
				.rate(rateDTO.getRate()).build();
	}
}
