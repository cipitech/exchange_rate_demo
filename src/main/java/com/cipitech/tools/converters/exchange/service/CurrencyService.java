package com.cipitech.tools.converters.exchange.service;

import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.model.Currency;
import com.cipitech.tools.converters.exchange.repository.CurrencyRepository;
import com.cipitech.tools.converters.exchange.service.base.BaseService;

import java.util.List;

/**
 * The service of the Currency entity.
 */

public interface CurrencyService extends BaseService<Currency, CurrencyRepository>
{
	/**
	 * Get all currency codes
	 *
	 * @return the list of codes
	 */
	List<String> getAllCurrencyCodes();

	/**
	 * Get all currency codes except from the specified code.
	 *
	 * @param exceptCode the currency code to exclude form the results
	 * @return the list of codes
	 */
	List<String> getAllCurrencyCodesExcept(String exceptCode);

	/**
	 * Get all currencies that exist in the database
	 * but converted to a DTO.
	 *
	 * @return the list of currency DTOs
	 */
	List<CurrencyDTO> getAllCurrencyDTOs();

	/**
	 * Get all currencies that exist in the database sorted by their code in ascending order
	 *
	 * @return the list of currency entities
	 */
	List<Currency> getAllCurrencies();

	/**
	 * Save a list of currency DTOs in the database
	 * after you delete any previous records.
	 *
	 * @param currList the list of DTOs to be saved
	 */
	void refreshCurrencies(List<CurrencyDTO> currList);

	/**
	 * Find a currency that has the specified code
	 * but converted to a DTO
	 *
	 * @param code the currency code
	 * @return the currency DTO
	 */
	CurrencyDTO getCurrencyDTO(String code);
}
