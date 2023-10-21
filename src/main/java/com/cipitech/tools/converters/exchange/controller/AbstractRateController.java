package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.service.CurrencyService;
import com.cipitech.tools.converters.exchange.service.ExchangeRateService;
import com.cipitech.tools.converters.exchange.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractRateController extends AbstractController
{
	private final ExchangeRateFetcher rateFetcher;
	private final CurrencyFetcher     currencyFetcher;
	private final ExchangeRateService exchangeRateService;
	private final CurrencyService     currencyService;

	protected AbstractRateController(AppConfig config, ExchangeRateFetcher rateFetcher, CurrencyFetcher currencyFetcher, ExchangeRateService exchangeRateService, CurrencyService currencyService)
	{
		super(config);
		this.rateFetcher = rateFetcher;
		this.currencyFetcher = currencyFetcher;
		this.exchangeRateService = exchangeRateService;
		this.currencyService = currencyService;
	}

	public List<ExchangeRateDTO> getExchangeRatesList(String fromCurrencyCode, String toCurrencyCode, Long delay)
	{
		List<ExchangeRateDTO> rates = new ArrayList<>();

		if (delay == null || delay < 0)
		{
			delay = getConfig().getDelayNewRequestSeconds();
		}

		if (StringUtils.isBlank(fromCurrencyCode))
		{
			fromCurrencyCode = getConfig().getDefaultFromCurrency();
		}

		List<String> toCurrencyCodes = StringUtils.isBlank(toCurrencyCode) ? null : Arrays.asList(toCurrencyCode.split(","));

		if (delay.equals(0L))
		{
			rates.addAll(getDirectlyFromFetcher(fromCurrencyCode, toCurrencyCodes));
		}
		else
		{
			long minTimeThreshold = DateUtils.currentTimeInMillisMinusSeconds(delay);

			if (exchangeRateService.existForCurrencyAfterTime(fromCurrencyCode, minTimeThreshold))
			{
				if (CollectionUtils.isEmpty(toCurrencyCodes))
				{
					refreshCurrenciesIfNeeded();

					toCurrencyCodes = currencyService.getAllCurrencyCodes();
				}

				List<String> notFoundCodes = new ArrayList<>();

				for (String toCode : toCurrencyCodes)
				{
					ExchangeRateDTO exchangeRateDTO = exchangeRateService.findFirstRateDTOAfterTime(fromCurrencyCode, toCode, minTimeThreshold);

					if (exchangeRateDTO != null)
					{
						rates.add(exchangeRateDTO);
					}
					else
					{
						notFoundCodes.add(toCode);
					}
				}

				if (!CollectionUtils.isEmpty(notFoundCodes))
				{
					rates.addAll(getDirectlyFromFetcher(fromCurrencyCode, notFoundCodes));
				}
			}
			else
			{
				rates.addAll(getDirectlyFromFetcher(fromCurrencyCode, toCurrencyCodes));
			}
		}

		return rates;
	}

	public List<ExchangeRateDTO> getDirectlyFromFetcher(String fromCurrencyCode, List<String> toCurrencyCodes)
	{
		// Fetch the fresh exchange rates using the fetcher
		List<ExchangeRateDTO> fetchedRates = getFetcher().getExchangeRateBetweenCurrencies(fromCurrencyCode, toCurrencyCodes);

		// We must refresh currencies in database otherwise the incoming exchange rates
		// might not find their currencies (from, to)
		// so the foreign key connection will not be made in the database
		// and the columns will be null
		refreshCurrenciesIfNeeded();

		// Everytime we fetch exchange rates we must also save them to the database
		// so that subsequent calls to our API can retrieve the data directly from the database and not use the fetcher.
		// Now all the currencies exist in the database
		// so, we can safely save the exchange rate records in the database
		exchangeRateService.addNewRates(fetchedRates);

		return fetchedRates;
	}

	private void refreshCurrenciesIfNeeded()
	{
		if (!currencyService.exist())
		{
			currencyService.refreshCurrencies(currencyFetcher.getAllCurrencies());
		}
	}

	@Override
	protected ExchangeRateFetcher getFetcher()
	{
		return rateFetcher;
	}
}
