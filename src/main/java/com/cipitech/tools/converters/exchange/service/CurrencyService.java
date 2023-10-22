package com.cipitech.tools.converters.exchange.service;

import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.model.Currency;
import com.cipitech.tools.converters.exchange.repository.CurrencyRepository;
import com.cipitech.tools.converters.exchange.service.base.BaseService;

import java.util.List;

public interface CurrencyService extends BaseService<Currency, CurrencyRepository>
{
	List<String> getAllCurrencyCodes();

	List<String> getAllCurrencyCodesExcept(String exceptCode);

	List<CurrencyDTO> getAllCurrencyDTOs();

	List<Currency> getAllCurrencies();

	void refreshCurrencies(List<CurrencyDTO> currList);

	CurrencyDTO getCurrencyDTO(String code);
}
