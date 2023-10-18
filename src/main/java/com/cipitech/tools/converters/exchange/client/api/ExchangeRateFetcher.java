package com.cipitech.tools.converters.exchange.client.api;

import java.util.List;

public interface ExchangeRateFetcher {
    /**
     *
     * @param fromCurrencyCode The code of the currency that we want to get the exchange rate from
     * @param toCurrencyCode The code of the currency that we want to get the exchange rate to
     * @return the exchange rate value
     */
    Double getExchangeRateBetweenCurrencies(String fromCurrencyCode, String toCurrencyCode);
    List<Double> getExchangeRateBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes);

    List<Double> getAllExchangeRatesForCurrency(String fromCurrencyCode);

    Double getConversionBetweenCurrencies(String fromCurrencyCode, String toCurrencyCode, Double amount);
    List<Double> getConversionBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes, Double amount);
}
