package com.cipitech.tools.converters.exchange.client.impl.offline;

import com.cipitech.tools.converters.exchange.client.api.ConversionFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("offline")
public class OfflineConversionFetcher implements ConversionFetcher {

    private static final Logger log = LoggerFactory.getLogger(OfflineConversionFetcher.class);

    public OfflineConversionFetcher() {
    }

    @Override
    public Double getConversionBetweenCurrencies(String fromCurrencyCode, String toCurrencyCode, Double amount) {
        return null;
    }

    @Override
    public List<Double> getConversionBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes, Double amount) {
        return null;
    }
}
