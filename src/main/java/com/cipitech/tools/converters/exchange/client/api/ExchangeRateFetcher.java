package com.cipitech.tools.converters.exchange.client.api;

import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;

import java.util.List;

/**
 * Fetches information about exchange rates.
 */
public interface ExchangeRateFetcher extends AbstractFetcher
{
	/**
	 * Fetches a list of the exchange rates that exist in the datasource
	 * for all the combinations between
	 * a specific currency as the source
	 * and a list of currencies as the target
	 *
	 * @param fromCurrencyCode The code of the currency that we want to get the exchange rate from
	 * @param toCurrencyCodes  The list of codes of the currencies that we want to get the exchange rate to
	 * @return the list of exchange rate DTOs
	 */
	List<ExchangeRateDTO> getExchangeRateBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes);
}
