package com.cipitech.tools.converters.exchange.client.impl.thirdparty;

import com.cipitech.tools.converters.exchange.client.api.ConversionFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("third-party")
public class ThirdPartyConversionFetcher implements ConversionFetcher {

    private static final Logger log = LoggerFactory.getLogger(ThirdPartyConversionFetcher.class);

    private final ThirdPartyExchangeRateFetcher exchangeRateFetcher;

    public ThirdPartyConversionFetcher(ThirdPartyExchangeRateFetcher exchangeRateFetcher) {
        this.exchangeRateFetcher = exchangeRateFetcher;
    }

    @Override
    public Double getConversionBetweenCurrencies(String fromCurrencyCode, String toCurrencyCode, Double amount) {
        Double exchangeRate = exchangeRateFetcher.getExchangeRateBetweenCurrencies(fromCurrencyCode, toCurrencyCode);

        return amount * exchangeRate;
    }

    @Override
    public List<Double> getConversionBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes, Double amount) {
        return null;
    }
}
