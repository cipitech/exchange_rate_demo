package com.cipitech.tools.converters.exchange.repository;

import com.cipitech.tools.converters.exchange.model.Currency;
import com.cipitech.tools.converters.exchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>
{
	ExchangeRate findFirstByFromCurrencyAndToCurrencyOrderByInsertedAtDesc(Currency fromCurrency, Currency toCurrency);

	@Query("SELECT r FROM ExchangeRate r " +
		   "WHERE UPPER(r.fromCurrency.code) = UPPER(:fromCurrCode) " +
		   "AND UPPER(r.toCurrency.code) = UPPER(:toCurrCode) " +
		   "AND r.insertedAt >= :timeMillis " +
		   "ORDER BY r.insertedAt DESC " +
		   "LIMIT 1 ")
	ExchangeRate findFirstRateAfterTime(
			@Param("fromCurrCode") String fromCurrencyCode,
			@Param("toCurrCode") String toCurrencyCode,
			@Param("timeMillis") Long timeMillis);
}
