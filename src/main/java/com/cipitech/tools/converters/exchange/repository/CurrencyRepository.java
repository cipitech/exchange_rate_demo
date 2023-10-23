package com.cipitech.tools.converters.exchange.repository;

import com.cipitech.tools.converters.exchange.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long>
{
	/**
	 * Find a currency that has the specified code.
	 *
	 * @param code the currency code
	 * @return a currency entity
	 */
	Currency findByCodeIgnoreCase(String code);

	/**
	 * Get all the currencies that have in their description the specified text.
	 *
	 * @param description currency full name
	 * @return a list of currencies
	 */
	List<Currency> findAllByDescriptionContainsIgnoreCase(String description);
}
