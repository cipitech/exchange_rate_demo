package com.cipitech.tools.converters.exchange.service.mappers;

import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.model.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Mapping service from Currency to CurrencyDTO and vice versa
 */

@Slf4j
@Service
public class CurrencyMapper implements MappingService<Currency, CurrencyDTO>
{
	@Override
	public CurrencyDTO toDTO(Currency currency)
	{
		if (currency == null)
		{
			return null;
		}

		return CurrencyDTO.builder().code(currency.getCode()).description(currency.getDescription()).build();
	}

	@Override
	public CurrencyDTO toBaseDTO(Currency currency)
	{
		if (currency == null)
		{
			return null;
		}

		CurrencyDTO currencyDTO = toDTO(currency);
		currencyDTO.setId(currency.getId());
		currencyDTO.setInsertedAt(currency.getInsertedAt());
		currencyDTO.setUpdatedAt(currency.getUpdatedAt());

		return currencyDTO;
	}

	@Override
	public Currency toEntity(CurrencyDTO currencyDTO)
	{
		if (currencyDTO == null)
		{
			return null;
		}

		Currency currency = Currency.builder().code(currencyDTO.getCode()).description(currencyDTO.getDescription()).build();
		currency.setId(currencyDTO.getId());

		return currency;
	}
}
