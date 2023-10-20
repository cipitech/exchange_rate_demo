package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.service.CurrencyService;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(Globals.Endpoints.Currency.CONTROLLER)
public class CurrencyController extends AbstractController
{
	private final CurrencyFetcher currencyFetcher;
	private final CurrencyService currencyService;

	public CurrencyController(CurrencyFetcher currencyFetcher, CurrencyService currencyService, AppConfig config)
	{
		super(config);
		this.currencyFetcher = currencyFetcher;
		this.currencyService = currencyService;
	}

	@GetMapping(Globals.Endpoints.PING)
	public ResponseEntity<String> ping()
	{
		return pong();
	}

	@GetMapping(Globals.Endpoints.Currency.all)
	public ResponseEntity<List<?>> getAll(@RequestParam(value = Globals.Parameters.Currency.showDescription, defaultValue = "false", required = false) Boolean showDescription)
	{
		log.info("getAll started...");
		log.debug("showDescription [{}]", showDescription);

		if (!currencyService.exist())
		{
			refresh();
		}

		List<?> result = showDescription ? currencyService.getAllCurrencyDTOs() : currencyService.getAllCurrencyCodes();

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(Globals.Endpoints.Currency.refresh)
	public ResponseEntity<String> refreshCurrencies()
	{
		log.info("refreshCurrencies started...");

		refresh();

		return new ResponseEntity<>("Currencies were fetched from source and added to the database.", HttpStatus.OK);
	}

	@GetMapping("/{" + Globals.Parameters.Currency.code + "}")
	public ResponseEntity<?> getByCode(@PathVariable String code)
	{
		log.info("getByCode started...");
		log.debug("code [{}]", code);

		CurrencyDTO currencyDTO = currencyService.getCurrencyDTO(code);
		if (currencyDTO != null)
		{
			return new ResponseEntity<>(currencyDTO, HttpStatus.OK);
		}

		return new ResponseEntity<>(String.format("The currency with code %s was not found. Please try something else.", code), HttpStatus.NOT_FOUND);
	}

	@Override
	protected CurrencyFetcher getFetcher()
	{
		return currencyFetcher;
	}

	private void refresh()
	{
		currencyService.refreshCurrencies(getFetcher().getAllCurrencies());
	}
}
