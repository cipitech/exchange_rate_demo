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

@Slf4j
@Service
public class CurrencyServiceImpl extends BaseServiceImpl<Currency, CurrencyRepository> implements CurrencyService
{
	private final CurrencyRepository  currencyRepository;
	private final ExchangeRateService exchangeRateService;

	public CurrencyServiceImpl(CurrencyRepository currencyRepository, ExchangeRateService exchangeRateService)
	{
		this.currencyRepository = currencyRepository;
		this.exchangeRateService = exchangeRateService;
	}

	@Override
	public List<String> getAllCurrencyCodes()
	{
		return getAllCurrencies().stream().map(Currency::getCode).filter(Objects::nonNull).toList();
	}

	@Override
	public List<CurrencyDTO> getAllCurrencyDTOs()
	{
		return getAllCurrencies().stream().map(this::toDTO).toList();
	}

	@Override
	public List<Currency> getAllCurrencies()
	{
		return getRepository().findAll(Sort.by(Sort.Order.asc(Currency.CODE)));
	}

	@Override
	public int refreshCurrencies(List<CurrencyDTO> currList)
	{
		removeAll();

		if (!CollectionUtils.isEmpty(currList))
		{
			List<Currency> currencies = currList.stream().map(this::toEntity).toList();

			saveAll(currencies);

			return currencies.size();
		}

		return 0;
	}

	@Override
	public CurrencyDTO getCurrencyDTO(String code)
	{
		return toDTO(getRepository().findByCode(code));
	}

	@Override
	public void removeAll()
	{
		exchangeRateService.removeAll();

		super.removeAll();
	}

	@Override
	public CurrencyRepository getRepository()
	{
		return currencyRepository;
	}

	private CurrencyDTO toDTO(Currency currency)
	{
		if (currency == null)
		{
			return null;
		}

		return CurrencyDTO.builder().code(currency.getCode()).description(currency.getDescription()).build();
	}

	private Currency toEntity(CurrencyDTO currencyDTO)
	{
		if (currencyDTO == null)
		{
			return null;
		}

		return Currency.builder().code(currencyDTO.getCode()).description(currencyDTO.getDescription()).build();
	}
}
