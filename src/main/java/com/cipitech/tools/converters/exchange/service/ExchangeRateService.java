package com.cipitech.tools.converters.exchange.service;

import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.model.ExchangeRate;
import com.cipitech.tools.converters.exchange.repository.ExchangeRateRepository;
import com.cipitech.tools.converters.exchange.service.base.BaseService;

import java.util.List;

/**
 * The service of the ExchangeRate entity.
 */

public interface ExchangeRateService extends BaseService<ExchangeRate, ExchangeRateRepository>
{
	/**
	 * Find the latest exchange rate entity that was inserted to the database after the specified timestamp
	 * for the specified currency codes.
	 *
	 * @param fromCurrencyCode the source currency code
	 * @param toCurrencyCode   the target currency code
	 * @param timeMillis       the timestamp in milliseconds
	 * @return the exchange rate entity as a DTO
	 */
	ExchangeRateDTO findFirstRateDTOAfterTime(String fromCurrencyCode, String toCurrencyCode, long timeMillis);

	/**
	 * Check whether exchange rate entities were inserted in the database after
	 * the specified timestamp for the specified currency code as the source.
	 *
	 * @param fromCurrencyCode the source currency code
	 * @param timeMillis       a timestamp in milliseconds
	 * @return true if they exist, otherwise false
	 */
	boolean existForCurrencyAfterTime(String fromCurrencyCode, long timeMillis);

	/**
	 * Save new exchange rates to the database
	 *
	 * @param rateList the exchange rates list to be saved to the database
	 */
	void addNewRates(List<ExchangeRateDTO> rateList);
}
