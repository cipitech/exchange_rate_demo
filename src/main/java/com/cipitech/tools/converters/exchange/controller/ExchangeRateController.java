package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.service.ExchangeRateService;
import com.cipitech.tools.converters.exchange.utils.DateUtils;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(Globals.Endpoints.ExchangeRate.CONTROLLER)
public class ExchangeRateController extends AbstractController
{
	private final ExchangeRateFetcher rateFetcher;
	private final ExchangeRateService exchangeRateService;

	public ExchangeRateController(ExchangeRateFetcher rateFetcher, AppConfig config, ExchangeRateService exchangeRateService)
	{
		super(config);
		this.rateFetcher = rateFetcher;
		this.exchangeRateService = exchangeRateService;
	}

	@GetMapping(Globals.Endpoints.PING)
	public ResponseEntity<String> ping()
	{
		return pong();
	}

	@GetMapping(Globals.Endpoints.ExchangeRate.value)
	public ResponseEntity<List<ExchangeRateDTO>> getValue(
			@RequestParam(value = Globals.Parameters.ExchangeRate.from) String fromCurrencyCode,
			@RequestParam(value = Globals.Parameters.ExchangeRate.to, required = false) String toCurrencyCode,
			@RequestParam(value = Globals.Parameters.ExchangeRate.delay, required = false) Long delay)
	{
		log.info("getValue started...");

		List<ExchangeRateDTO> rates = new ArrayList<>();

		try
		{
			if (delay == null)
			{
				delay = getConfig().getDelayNewRequestSeconds();
			}

			List<String> toCurrencyCodes = toCurrencyCode != null ? Arrays.asList(toCurrencyCode.split(",")) : null;

			List<String> notFoundCodes = new ArrayList<>();

			if (delay.equals(0L))
			{
				rates.addAll(getFetcher().getExchangeRateBetweenCurrencies(fromCurrencyCode, toCurrencyCodes));
			}
			else
			{
				if (CollectionUtils.isEmpty(toCurrencyCodes))
				{

				}
				else
				{
					for (String toCode : toCurrencyCodes)
					{
						ExchangeRateDTO exchangeRateDTO = exchangeRateService.findFirstRateDTOAfterTime(fromCurrencyCode, toCode, DateUtils.currentTimeInMillisMinusSeconds(delay));

						if (exchangeRateDTO != null)
						{
							rates.add(exchangeRateDTO);
						}
						else
						{
							notFoundCodes.add(toCode);
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ResponseEntity<>(rates, HttpStatus.OK);
	}

	@Override
	protected ExchangeRateFetcher getFetcher()
	{
		return rateFetcher;
	}
}
