package com.cipitech.tools.converters.exchange.client.api;

import java.util.List;

public interface ExchangeRateFetcher {
    /**
     *
     * @param fromCurrencyCode
     * @param toCurrencyCode
     * @return
     */
    public abstract Double getExchangeRateBetweenCurrencies(String fromCurrencyCode, String toCurrencyCode);
    public abstract List<Double> getExchangeRateBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes);

    public abstract List<Double> getAllExchangeRatesForCurrency(String fromCurrencyCode);

    public abstract Double getConversionBetweenCurrencies(String fromCurrencyCode, String toCurrencyCode, Double amount);
    public abstract List<Double> getConversionBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes, Double amount);
}
