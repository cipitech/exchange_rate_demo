package com.cipitech.tools.converters.exchange.client.api;

import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;

import java.util.List;

/**
 * Fetches information about currencies.
 */
public interface CurrencyFetcher extends AbstractFetcher
{
	/**
	 * Fetches a list of ALL available currencies in the datasource along with their information (code, description)
	 *
	 * @return the list
	 */
	List<CurrencyDTO> getAllCurrencies();
}
