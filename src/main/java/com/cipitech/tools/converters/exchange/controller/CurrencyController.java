package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.dto.SuccessResponseDTO;
import com.cipitech.tools.converters.exchange.error.dto.ErrorResponseDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.service.CurrencyService;
import com.cipitech.tools.converters.exchange.utils.Globals;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public ResponseEntity<SuccessResponseDTO> ping()
	{
		return pong();
	}

	@GetMapping(Globals.Endpoints.Currency.all)
	@Operation(summary = "Get a list of all the currencies that exist in the system")
	public ResponseEntity<List<?>> getAll(
			@Parameter(description = "Display the currency's full name", example = "true")
			@RequestParam(value = Globals.Parameters.Currency.showDescription, defaultValue = "false", required = false) Boolean showDescription)
	{
		log.info("getAll started...");
		log.debug("showDescription [{}]", showDescription);

		if (!currencyService.exist())
		{
			refresh();
		}

		List<?> result = showDescription ? currencyService.getAllCurrencyDTOs() : currencyService.getAllCurrencyCodes();

		return ResponseEntity.ok(result);
	}

	@DeleteMapping(Globals.Endpoints.Currency.all)
	@Operation(summary = "Delete all the currencies that exist in the system.", description = "Note: Exchange Rate records will be deleted as well.")
	public ResponseEntity<SuccessResponseDTO> deleteAll()
	{
		log.info("deleteAll started...");

		currencyService.removeAll();

		return ResponseEntity.ok(SuccessResponseDTO.builder().message("All currencies were removed from the database.").build());
	}

	@GetMapping(Globals.Endpoints.Currency.refresh)
	@Operation(summary = "Delete any currencies from the system and reimport them from the remote datasource.")
	public ResponseEntity<SuccessResponseDTO> refreshCurrencies()
	{
		log.info("refreshCurrencies started...");

		refresh();

		return ResponseEntity.ok(SuccessResponseDTO.builder().message("Currencies were fetched from source and added to the database.").build());
	}

	@GetMapping("/{" + Globals.Parameters.Currency.code + "}")
	@Operation(summary = "Check if a currency with the provided code exists in the system.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					content = @Content(
							schema = @Schema(implementation = CurrencyDTO.class))),
			@ApiResponse(responseCode = "404",
					content = @Content(
							schema = @Schema(implementation = ErrorResponseDTO.class)))})
	public ResponseEntity<CurrencyDTO> getByCode(
			@Parameter(description = "The 3-letter code of the currency to be checked.", example = "EUR")
			@PathVariable String code)
	{
		log.info("getByCode started...");
		log.debug("code [{}]", code);

		if(code != null)
		{
			code = Jsoup.clean(code, Safelist.basic());
		}

		if (!currencyService.exist())
		{
			refresh();
		}

		CurrencyDTO currencyDTO = currencyService.getCurrencyDTO(code);
		if (currencyDTO != null)
		{
			return ResponseEntity.ok(currencyDTO);
		}

		throw new RecordNotFoundException(String.format("The currency with code %s was not found. Please try something else.", code));
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
