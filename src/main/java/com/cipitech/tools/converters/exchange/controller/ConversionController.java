package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.ConversionFetcher;
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
@RequestMapping("/convert")
public class ConversionController extends AbstractController
{
	private final ConversionFetcher conversionFetcher;

	public ConversionController(ConversionFetcher conversionFetcher, Config config)
	{
		super(config);
		this.conversionFetcher = conversionFetcher;
	}

	@GetMapping("/ping")
	public ResponseEntity<String> ping()
	{
		return pong();
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
			rate = getFetcher().getConversionBetweenCurrencies(fromCurrencyCode, toCurrencyCode, amount);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ResponseEntity<>(String.format("%f %s = %f %s", amount, fromCurrencyCode, rate, toCurrencyCode), HttpStatus.OK);
	}

	@Override
	protected ConversionFetcher getFetcher()
	{
		return conversionFetcher;
	}
}
