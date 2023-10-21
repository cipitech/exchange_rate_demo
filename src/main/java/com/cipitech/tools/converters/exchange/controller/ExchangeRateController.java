package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.service.CurrencyService;
import com.cipitech.tools.converters.exchange.service.ExchangeRateService;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(Globals.Endpoints.ExchangeRate.CONTROLLER)
public class ExchangeRateController extends AbstractRateController
{
	protected ExchangeRateController(AppConfig config, ExchangeRateFetcher rateFetcher, CurrencyFetcher currencyFetcher, ExchangeRateService exchangeRateService, CurrencyService currencyService)
	{
		super(config, rateFetcher, currencyFetcher, exchangeRateService, currencyService);
	}

	@GetMapping(Globals.Endpoints.PING)
	public ResponseEntity<String> ping()
	{
		return pong();
	}

	@GetMapping(Globals.Endpoints.ExchangeRate.value)
	public ResponseEntity<List<ExchangeRateDTO>> getValue(
			@RequestParam(value = Globals.Parameters.ExchangeRate.from, required = false) String fromCurrencyCode,
			@RequestParam(value = Globals.Parameters.ExchangeRate.to, required = false) String toCurrencyCode,
			@RequestParam(value = Globals.Parameters.ExchangeRate.delay, required = false) Long delay)
	{
		log.info("getValue started...");

		return new ResponseEntity<>(getExchangeRatesList(fromCurrencyCode, toCurrencyCode, delay), HttpStatus.OK);
	}
}
