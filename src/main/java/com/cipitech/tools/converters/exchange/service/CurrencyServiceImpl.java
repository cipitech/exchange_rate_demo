package com.cipitech.tools.converters.exchange.service;

import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.model.Currency;
import com.cipitech.tools.converters.exchange.repository.CurrencyRepository;
import com.cipitech.tools.converters.exchange.service.base.BaseServiceImpl;
import com.cipitech.tools.converters.exchange.service.mappers.CurrencyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CurrencyServiceImpl extends BaseServiceImpl<Currency, CurrencyRepository> implements CurrencyService
{
	private final CurrencyRepository  currencyRepository;
	private final ExchangeRateService exchangeRateService;
	private final CurrencyMapper currencyMapper;

	public CurrencyServiceImpl(CurrencyRepository currencyRepository, ExchangeRateService exchangeRateService, CurrencyMapper currencyMapper)
	{
		this.currencyRepository = currencyRepository;
		this.exchangeRateService = exchangeRateService;
		this.currencyMapper = currencyMapper;
	}

	@Override
	public List<String> getAllCurrencyCodes()
	{
		return getAllCurrencies().stream().map(Currency::getCode).filter(Objects::nonNull).toList();
	}

	@Override
	public List<CurrencyDTO> getAllCurrencyDTOs()
	{
		return getAllCurrencies().stream().map(currencyMapper::toDTO).toList();
	}

	@Override
	public List<Currency> getAllCurrencies()
	{
		return getRepository().findAll(Sort.by(Sort.Order.asc(Currency.CODE)));
	}

	@Override
	public void refreshCurrencies(List<CurrencyDTO> currList)
	{
		this.removeAll();

		if (!CollectionUtils.isEmpty(currList))
		{
			saveAll(currList.stream().map(currencyMapper::toEntity).toList());
		}
	}

	@Override
	public CurrencyDTO getCurrencyDTO(String code)
	{
		return currencyMapper.toDTO(getRepository().findByCodeIgnoreCase(code));
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
}
