package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rate")
public class ExchangeRateController extends AbstractController
{
	private final ExchangeRateFetcher rateFetcher;

	public ExchangeRateController(ExchangeRateFetcher rateFetcher, Config config)
	{
		super(config);
		this.rateFetcher = rateFetcher;
	}

	@GetMapping("/ping")
	public ResponseEntity<String> ping()
	{
		return pong();
	}

	@GetMapping("/between/single/{fromCurrencyCode}/{toCurrencyCode}")
	public ResponseEntity<String> getBetweenSingle(@PathVariable String fromCurrencyCode,
												   @PathVariable String toCurrencyCode)
	{
		log.info("ExchangeRateController getBetweenSingle started...");

		Double rate = null;

		try
		{
			rate = getFetcher().getExchangeRateBetweenCurrencies(fromCurrencyCode, toCurrencyCode);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ResponseEntity<>(String.format("The exchange rate from %s to %s is: %f", fromCurrencyCode, toCurrencyCode, rate), HttpStatus.OK);
	}

	@Override
	protected ExchangeRateFetcher getFetcher()
	{
		return rateFetcher;
	}
}
