package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.config.Config;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/currency")
public class CurrencyController {
    private static final Logger log = LoggerFactory.getLogger(CurrencyController.class);

    private final CurrencyFetcher currencyFetcher;
    private final Config config;

    public CurrencyController(CurrencyFetcher currencyFetcher, Config config) {
        this.currencyFetcher = currencyFetcher;
        this.config = config;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CurrencyDTO>> getAll() {
        log.info("CurrencyController getAll started...");

        return new ResponseEntity<>(currencyFetcher.getAllCurrencies(), HttpStatus.OK);
    }
}
