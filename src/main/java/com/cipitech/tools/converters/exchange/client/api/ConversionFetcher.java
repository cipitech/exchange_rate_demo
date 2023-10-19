package com.cipitech.tools.converters.exchange.client.api;

import java.util.List;

public interface ConversionFetcher extends AbstractFetcher
{
	Double getConversionBetweenCurrencies(String fromCurrencyCode, String toCurrencyCode, Double amount);

	List<Double> getConversionBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes, Double amount);
}
