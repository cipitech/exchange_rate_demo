package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rate")
public class ExchangeRateController
{
    private static final Logger log = LoggerFactory.getLogger(ExchangeRateController.class);

    private final ExchangeRateFetcher rateFetcher;
    private final Config config;

    public ExchangeRateController(ExchangeRateFetcher rateFetcher, Config config) {
        this.rateFetcher = rateFetcher;
        this.config = config;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping()
    {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    @GetMapping("/between/single/{fromCurrencyCode}/{toCurrencyCode}")
    public ResponseEntity<String> getBetweenSingle(@PathVariable String fromCurrencyCode,
                                                          @PathVariable String toCurrencyCode)
    {
        log.info("getBetweenSingle started...");

        Double rate = null;

        try
        {
            rate = rateFetcher.getExchangeRateBetweenCurrencies(fromCurrencyCode, toCurrencyCode);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ResponseEntity<>(String.format("The exchange rate from %s to %s is: %f", fromCurrencyCode, toCurrencyCode, rate), HttpStatus.OK);
    }
}
