package com.cipitech.tools.converters.exchange.repository;

import com.cipitech.tools.converters.exchange.model.Currency;
import com.cipitech.tools.converters.exchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>
{
	ExchangeRate findFirstByFromCurrencyAndToCurrencyOrderByInsertedAtDesc(Currency fromCurrency, Currency toCurrency);
}
