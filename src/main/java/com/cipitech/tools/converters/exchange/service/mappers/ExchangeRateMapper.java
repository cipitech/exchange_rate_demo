package com.cipitech.tools.converters.exchange.service.mappers;

import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.model.ExchangeRate;
import com.cipitech.tools.converters.exchange.repository.CurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Mapping service from ExchangeRate to ExchangeRateDTO and vice versa
 */

@Slf4j
@Service
public class ExchangeRateMapper implements MappingService<ExchangeRate, ExchangeRateDTO>
{
	private final CurrencyMapper     currencyMapper;
	private final CurrencyRepository currencyRepository;

	public ExchangeRateMapper(CurrencyMapper currencyMapper, CurrencyRepository currencyRepository)
	{
		this.currencyMapper = currencyMapper;
		this.currencyRepository = currencyRepository;
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
				.rate(rate.getRate())
				.build();
	}

	@Override
	public ExchangeRateDTO toBaseDTO(ExchangeRate rate)
	{
		if (rate == null)
		{
			return null;
		}

		ExchangeRateDTO rateDTO = ExchangeRateDTO.builder()
				.fromCurrency(currencyMapper.toBaseDTO(rate.getFromCurrency()))
				.toCurrency(currencyMapper.toBaseDTO(rate.getToCurrency()))
				.rate(rate.getRate()).build();
		rateDTO.setId(rate.getId());
		rateDTO.setInsertedAt(rate.getInsertedAt());
		rateDTO.setUpdatedAt(rate.getUpdatedAt());

		return rateDTO;
	}

	@Override
	public ExchangeRate toEntity(ExchangeRateDTO rateDTO)
	{
		if (rateDTO == null)
		{
			return null;
		}

		ExchangeRate rate = ExchangeRate.builder()
				// We must look in the database in order to make the connection because the currencyDTO doear not have the ID field
				// so hibernate will not set the foreign key and the currency column will be null
				.fromCurrency(rateDTO.getFromCurrency() != null ? currencyRepository.findByCodeIgnoreCase(rateDTO.getFromCurrency().getCode()) : null)
				.toCurrency(rateDTO.getToCurrency() != null ? currencyRepository.findByCodeIgnoreCase(rateDTO.getToCurrency().getCode()) : null)
				.rate(rateDTO.getRate()).build();
		rate.setId(rateDTO.getId());

		return rate;
	}
}
