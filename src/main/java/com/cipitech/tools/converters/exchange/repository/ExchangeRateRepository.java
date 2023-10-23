package com.cipitech.tools.converters.exchange.repository;

import com.cipitech.tools.converters.exchange.model.Currency;
import com.cipitech.tools.converters.exchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>
{
	/**
	 * Find the latest exchange rate entity that was inserted to the database for the specified currencies.
	 *
	 * @param fromCurrency the source currency
	 * @param toCurrency   the target currency
	 * @return the exchange rate entity
	 */
	ExchangeRate findFirstByFromCurrencyAndToCurrencyOrderByInsertedAtDesc(Currency fromCurrency, Currency toCurrency);

	/**
	 * Find the latest exchange rate entity that was inserted to the database after the specified timestamp
	 * for the specified currency codes.
	 *
	 * @param fromCurrencyCode the source currency code
	 * @param toCurrencyCode   the target currency code
	 * @param timeMillis       the timestamp in milliseconds
	 * @return the exchange rate entity
	 */
	@Query("SELECT r FROM ExchangeRate r " +
		   "WHERE UPPER(r.fromCurrency.code) = UPPER(:fromCurrCode) " +
		   "AND UPPER(r.toCurrency.code) = UPPER(:toCurrCode) " +
		   "AND r.insertedAt >= :timeMillis " +
		   "ORDER BY r.insertedAt DESC " +
		   "LIMIT 1 ")
	ExchangeRate findFirstRateAfterTime(
			@Param("fromCurrCode") String fromCurrencyCode,
			@Param("toCurrCode") String toCurrencyCode,
			@Param("timeMillis") long timeMillis);

	/**
	 * Count how many exchange rate entities were inserted in the database after
	 * the specified timestamp for the specified currency code as the source.
	 *
	 * @param fromCurrencyCode the source currency code
	 * @param timeMillis       the timestamp in milliseconds
	 * @return the count value
	 */
	@Query("SELECT COUNT(r.id) FROM ExchangeRate r " +
		   "WHERE UPPER(r.fromCurrency.code) = UPPER(:fromCurrCode) " +
		   "AND r.insertedAt >= :timeMillis ")
	Long countByFromCurrencyAfterTime(@Param("fromCurrCode") String fromCurrencyCode, @Param("timeMillis") long timeMillis);
}
