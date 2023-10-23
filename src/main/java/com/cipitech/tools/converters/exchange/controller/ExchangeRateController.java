package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.dto.SuccessResponseDTO;
import com.cipitech.tools.converters.exchange.service.CurrencyService;
import com.cipitech.tools.converters.exchange.service.ExchangeRateService;
import com.cipitech.tools.converters.exchange.utils.Globals;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The controller that provides the exchange rates endpoints.
 */

@Slf4j
@RestController
@RequestMapping(value = Globals.Endpoints.ExchangeRate.CONTROLLER, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Exchange Rates API", description = "Get exchange rates for any currency")
public class ExchangeRateController extends AbstractRateController
{
	protected ExchangeRateController(AppConfig config, ExchangeRateFetcher rateFetcher, CurrencyFetcher currencyFetcher, ExchangeRateService exchangeRateService, CurrencyService currencyService)
	{
		super(config, rateFetcher, currencyFetcher, exchangeRateService, currencyService);
	}

	@GetMapping(Globals.Endpoints.PING)
	@Operation(summary = "Check if this API is healthy")
	public ResponseEntity<SuccessResponseDTO> ping()
	{
		return pong();
	}

	@GetMapping(Globals.Endpoints.ExchangeRate.value)
	@Operation(summary = "Get exchange rate information between currencies",
			description = "Note: If you want to retrieve live exchange rates without using the caching mechanism set the delay parameter to 0.")
	public ResponseEntity<List<ExchangeRateDTO>> getValue(
			@Parameter(description = "The source currency code", example = "USD")
			@RequestParam(value = Globals.Parameters.ExchangeRate.from, required = false) String fromCurrencyCode,
			@Parameter(description = "The list of target currency codes. Use a comma to separate the codes.", example = "EUR,GBP")
			@RequestParam(value = Globals.Parameters.ExchangeRate.to, required = false) String toCurrencyCode,
			@Parameter(description = "Allow a few seconds before calling for real-time data.", example = "60")
			@RequestParam(value = Globals.Parameters.ExchangeRate.delay, required = false) Long delay)
	{
		log.info("getValue started...");

		return ResponseEntity.ok(getExchangeRatesList(fromCurrencyCode, toCurrencyCode, delay));
	}
}
