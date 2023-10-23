package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.ConversionRateDTO;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.dto.SuccessResponseDTO;
import com.cipitech.tools.converters.exchange.service.CurrencyService;
import com.cipitech.tools.converters.exchange.service.ExchangeRateService;
import com.cipitech.tools.converters.exchange.utils.ConversionUtils;
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

import java.math.BigDecimal;
import java.util.List;

/**
 * The controller that provides the amount conversion endpoints.
 */

@Slf4j
@RestController
@RequestMapping(value = Globals.Endpoints.Conversion.CONTROLLER, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Conversion Rates API", description = "Convert an amount to other currencies")
public class ConversionController extends AbstractRateController
{
	protected ConversionController(AppConfig config, ExchangeRateFetcher rateFetcher, CurrencyFetcher currencyFetcher, ExchangeRateService exchangeRateService, CurrencyService currencyService)
	{
		super(config, rateFetcher, currencyFetcher, exchangeRateService, currencyService);
	}

	@GetMapping(Globals.Endpoints.PING)
	@Operation(summary = "Check if this API is healthy")
	public ResponseEntity<SuccessResponseDTO> ping()
	{
		return pong();
	}

	@GetMapping(Globals.Endpoints.Conversion.value)
	@Operation(summary = "Get an amount conversion between currencies using exchange rates.",
			description = "Note: If you want to retrieve live exchange rates without using the caching mechanism set the delay parameter to 0.")
	public ResponseEntity<List<ConversionRateDTO>> getValue(
			@Parameter(description = "The amount to be converted to other currencies.", example = "25")
			@RequestParam(value = Globals.Parameters.Conversion.amount) Double amount,
			@Parameter(description = "The source currency code", example = "USD")
			@RequestParam(value = Globals.Parameters.Conversion.from, required = false) String fromCurrencyCode,
			@Parameter(description = "The list of target currency codes. Use a comma to separate the codes.", example = "EUR,GBP")
			@RequestParam(value = Globals.Parameters.Conversion.to, required = false) String toCurrencyCode,
			@Parameter(description = "Allow a few seconds before calling for real-time data.", example = "60")
			@RequestParam(value = Globals.Parameters.Conversion.delay, required = false) Long delay)
	{
		log.info("getValue called");
		log.debug("amount [{}]", amount);

		// If no amount or negative value was provided set default value to 1.
		if (amount == null || amount < 0)
		{
			amount = 1D;
		}

		// Get the list of exchange rates
		List<ExchangeRateDTO> exchangeRates = getExchangeRatesList(fromCurrencyCode, toCurrencyCode, delay);

		Double initialAmount = amount;

		// For every exchange rate use it to convert the given amount to the new amount.
		List<ConversionRateDTO> resultList = exchangeRates.stream()
				// First check that every information for the exchange rate is given and is not missing.
				.filter(exchangeRate -> exchangeRate != null && exchangeRate.getRate() != null
										&& exchangeRate.getFromCurrency() != null && exchangeRate.getFromCurrency().getCode() != null
										&& exchangeRate.getToCurrency() != null && exchangeRate.getToCurrency().getCode() != null)
				.map(exchangeRate ->
				{
					// Make the actual conversion and get the converted amount
					BigDecimal convertedAmount = ConversionUtils.convertAmount(initialAmount, exchangeRate.getRate());

					return ConversionRateDTO.builder()
							.exchangeRate(exchangeRate)
							.fromAmount(initialAmount)
							.toAmount(convertedAmount)
							.description(String.format("%.2f %s = %.2f %s", initialAmount, exchangeRate.getFromCurrency().getCode(), convertedAmount, exchangeRate.getToCurrency().getCode()))
							.build();
				}).toList();

		return ResponseEntity.ok(resultList);
	}
}
