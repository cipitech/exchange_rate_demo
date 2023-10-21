package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.ConversionRateDTO;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.service.CurrencyService;
import com.cipitech.tools.converters.exchange.service.ExchangeRateService;
import com.cipitech.tools.converters.exchange.utils.ConversionUtils;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(Globals.Endpoints.Conversion.CONTROLLER)
public class ConversionController extends AbstractRateController
{
	protected ConversionController(AppConfig config, ExchangeRateFetcher rateFetcher, CurrencyFetcher currencyFetcher, ExchangeRateService exchangeRateService, CurrencyService currencyService)
	{
		super(config, rateFetcher, currencyFetcher, exchangeRateService, currencyService);
	}

	@GetMapping(Globals.Endpoints.PING)
	public ResponseEntity<String> ping()
	{
		return pong();
	}

	@GetMapping(Globals.Endpoints.Conversion.value)
	public ResponseEntity<List<ConversionRateDTO>> getValue(
			@RequestParam(value = Globals.Parameters.Conversion.amount) Double amount,
			@RequestParam(value = Globals.Parameters.Conversion.from, required = false) String fromCurrencyCode,
			@RequestParam(value = Globals.Parameters.Conversion.to, required = false) String toCurrencyCode,
			@RequestParam(value = Globals.Parameters.Conversion.delay, required = false) Long delay)
	{
		log.info("getValue started...");

		if (amount == null || amount < 0)
		{
			amount = 1D;
		}

		List<ExchangeRateDTO> exchangeRates = getExchangeRatesList(fromCurrencyCode, toCurrencyCode, delay);

		Double initialAmount = amount;

		List<ConversionRateDTO> resultList = exchangeRates.stream()
				.filter(exchangeRate -> exchangeRate != null && exchangeRate.getRate() != null
										&& exchangeRate.getFromCurrency() != null && exchangeRate.getFromCurrency().getCode() != null
										&& exchangeRate.getToCurrency() != null && exchangeRate.getToCurrency().getCode() != null)
				.map(exchangeRate ->
				{
					BigDecimal convertedAmount = ConversionUtils.convertAmount(initialAmount, exchangeRate.getRate());

					return ConversionRateDTO.builder()
							.exchangeRate(exchangeRate)
							.fromAmount(initialAmount)
							.toAmount(convertedAmount)
							.description(String.format("%.2f %s = %.2f %s", initialAmount, exchangeRate.getFromCurrency().getCode(), convertedAmount, exchangeRate.getToCurrency().getCode()))
							.build();
				}).toList();

		return new ResponseEntity<>(resultList, HttpStatus.OK);
	}
}
