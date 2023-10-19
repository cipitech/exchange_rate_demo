package com.cipitech.tools.converters.exchange.service;

import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.model.Currency;
import com.cipitech.tools.converters.exchange.repository.CurrencyRepository;
import com.cipitech.tools.converters.exchange.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CurrencyServiceImpl extends BaseServiceImpl<Currency, CurrencyRepository> implements CurrencyService
{
	private final CurrencyRepository currencyRepository;

	public CurrencyServiceImpl(CurrencyRepository currencyRepository)
	{
		this.currencyRepository = currencyRepository;
	}

	@Override
	public List<String> getAllCurrencyCodes()
	{
		List<Currency> currencies = getRepository().findAll(Sort.by(Sort.Order.asc(Currency.CODE)));
		return currencies.stream().map(Currency::getCode).filter(Objects::nonNull).collect(Collectors.toList());
	}

	@Override
	public int refreshCurrencies(List<CurrencyDTO> currList)
	{
		removeAll();

		if(!CollectionUtils.isEmpty(currList))
		{
			List<Currency> currencies = currList.stream().map(currencyDTO -> Currency.builder().code(currencyDTO.getCode()).description(currencyDTO.getDescription()).build()).toList();

			saveAll(currencies);

			return currencies.size();
		}

		return 0;
	}

	@Override
	public CurrencyRepository getRepository()
	{
		return currencyRepository;
	}
}
