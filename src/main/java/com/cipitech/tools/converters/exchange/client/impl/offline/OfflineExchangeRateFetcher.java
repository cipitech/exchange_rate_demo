package com.cipitech.tools.converters.exchange.client.impl.offline;

import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("offline")
public class OfflineExchangeRateFetcher implements ExchangeRateFetcher {

    private static final Logger log = LoggerFactory.getLogger(OfflineExchangeRateFetcher.class);

    public OfflineExchangeRateFetcher() {
    }

    @Override
    public Double getExchangeRateBetweenCurrencies(String fromCurrencyCode, String toCurrencyCode) {
        return null;
    }

    @Override
    public List<Double> getExchangeRateBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes) {
        return null;
    }

    @Override
    public List<Double> getAllExchangeRatesForCurrency(String fromCurrencyCode) {
        return null;
    }
}
