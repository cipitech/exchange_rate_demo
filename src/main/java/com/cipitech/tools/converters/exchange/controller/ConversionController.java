package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.ConversionFetcher;
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
@RequestMapping("/convert")
public class ConversionController
{
    private static final Logger log = LoggerFactory.getLogger(ConversionController.class);

    private final ConversionFetcher conversionFetcher;
    private final Config config;

    public ConversionController(ConversionFetcher conversionFetcher, Config config) {
        this.conversionFetcher = conversionFetcher;
        this.config = config;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping()
    {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    @GetMapping("/between/single/{fromCurrencyCode}/{toCurrencyCode}/{amount}")
    public ResponseEntity<String> getBetweenSingle(@PathVariable String fromCurrencyCode,
                                                          @PathVariable String toCurrencyCode,
                                                   @PathVariable Double amount)
    {
        log.info("ConversionController getBetweenSingle started...");

        Double rate = null;

        try
        {
            rate = conversionFetcher.getConversionBetweenCurrencies(fromCurrencyCode, toCurrencyCode, amount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new ResponseEntity<>(String.format("%f %s = %f %s", amount, fromCurrencyCode, rate, toCurrencyCode), HttpStatus.OK);
    }
}
