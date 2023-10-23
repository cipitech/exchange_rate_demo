package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.dto.SuccessResponseDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.service.CurrencyService;
import com.cipitech.tools.converters.exchange.utils.Globals;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller that provides the currency endpoints.
 */

@Slf4j
@RestController
@RequestMapping(value = Globals.Endpoints.Currency.CONTROLLER, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Currency API", description = "Everything about currencies")
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
	@Operation(summary = "Check if this API is healthy")
	public ResponseEntity<SuccessResponseDTO> ping()
	{
		return pong();
	}

	@GetMapping(Globals.Endpoints.Currency.all)
	@Operation(summary = "Get a list of all the currencies that exist in the system")
	public ResponseEntity<List<?>> getAll(
			@Parameter(description = "Display the currency's full name", example = "false")
			@RequestParam(value = Globals.Parameters.Currency.showDescription, defaultValue = "false", required = false) Boolean showDescription)
	{
		log.info("getAll called");
		log.debug("showDescription [{}]", showDescription);

		// Refresh currencies if none exist in the database
		if (!currencyService.exist())
		{
			refresh();
		}

		return ResponseEntity.ok(showDescription ? currencyService.getAllCurrencyDTOs() : currencyService.getAllCurrencyCodes());
	}

	@DeleteMapping(Globals.Endpoints.Currency.all)
	@Operation(summary = "Delete all the currencies that exist in the system.", description = "Note: Exchange Rate records will be deleted as well.")
	public ResponseEntity<SuccessResponseDTO> deleteAll()
	{
		log.info("deleteAll called");

		currencyService.removeAll();

		return ResponseEntity.ok(SuccessResponseDTO.builder().message("All currencies were removed from the database.").build());
	}

	@GetMapping(Globals.Endpoints.Currency.refresh)
	@Operation(summary = "Delete any currencies from the system and reimport them from the remote datasource.", description = "Note: Exchange Rate records will be deleted as well.")
	public ResponseEntity<SuccessResponseDTO> refreshCurrencies()
	{
		log.info("refreshCurrencies called");

		refresh();

		return ResponseEntity.ok(SuccessResponseDTO.builder().message("Currencies were fetched from source and added to the database.").build());
	}

	@GetMapping("/{" + Globals.Parameters.Currency.code + "}")
	@Operation(summary = "Check if a currency with the provided code exists in the system and get its information.")
	public ResponseEntity<CurrencyDTO> getByCode(
			@Parameter(description = "The 3-letter code of the currency to be checked.", example = "EUR")
			@PathVariable String code)
	{
		log.info("getByCode called");
		log.debug("code [{}]", code);

		// Sanitize the user input parameter
		if (code != null)
		{
			code = Jsoup.clean(code, Safelist.basic());
		}

		// Refresh currencies if none exist in the database
		if (!currencyService.exist())
		{
			refresh();
		}

		CurrencyDTO currencyDTO = currencyService.getCurrencyDTO(code);
		if (currencyDTO != null)
		{
			log.trace("Currency found [{}]", currencyDTO);
			return ResponseEntity.ok(currencyDTO);
		}

		throw new RecordNotFoundException(String.format("The currency with code %s was not found. Please try something else.", code));
	}

	@Override
	protected CurrencyFetcher getFetcher()
	{
		return currencyFetcher;
	}

	/**
	 * call the currency fetcher to retrieve a list of currencies from the
	 * external datasource and save those currencies in our database.
	 */
	private void refresh()
	{
		log.trace("Going to refresh the currencies.");

		currencyService.refreshCurrencies(getFetcher().getAllCurrencies());
	}
}
