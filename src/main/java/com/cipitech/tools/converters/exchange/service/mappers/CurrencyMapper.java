package com.cipitech.tools.converters.exchange.service.mappers;

import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.model.Currency;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CurrencyMapper implements MappingService<Currency, CurrencyDTO>
{
	@Override
	public CurrencyDTO toDTO(Currency currency)
	{
		if(currency == null)
		{
			return null;
		}

		return CurrencyDTO.builder().code(currency.getCode()).description(currency.getDescription()).build();
	}

	@Override
	public Currency toEntity(CurrencyDTO currencyDTO)
	{
		if(currencyDTO == null)
		{
			return null;
		}

		return Currency.builder().code(currencyDTO.getCode()).description(currencyDTO.getDescription()).build();
	}
}
