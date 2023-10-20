package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.utils.ConversionUtils;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(Globals.Endpoints.Conversion.CONTROLLER)
public class ConversionController extends AbstractController
{
	private final ExchangeRateFetcher exchangeRateFetcher;

	public ConversionController(ExchangeRateFetcher exchangeRateFetcher, AppConfig config)
	{
		super(config);
		this.exchangeRateFetcher = exchangeRateFetcher;
	}

	@GetMapping(Globals.Endpoints.PING)
	public ResponseEntity<String> ping()
	{
		return pong();
	}

	@GetMapping("/between/single/{fromCurrencyCode}/{toCurrencyCode}/{amount}")
	public ResponseEntity<String> getBetweenSingle(@PathVariable String fromCurrencyCode,
												   @PathVariable String toCurrencyCode,
												   @PathVariable Double amount)
	{
		log.info("getBetweenSingle started...");

		Double rate = null;

		try
		{
			rate = ConversionUtils.convertAmount(amount, getFetcher().getExchangeRateBetweenCurrencies(fromCurrencyCode, toCurrencyCode));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ResponseEntity<>(String.format("%f %s = %f %s", amount, fromCurrencyCode, rate, toCurrencyCode), HttpStatus.OK);
	}

	@Override
	protected ExchangeRateFetcher getFetcher()
	{
		return exchangeRateFetcher;
	}
}
