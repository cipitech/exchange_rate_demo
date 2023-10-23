package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.service.CurrencyService;
import com.cipitech.tools.converters.exchange.service.ExchangeRateService;
import com.cipitech.tools.converters.exchange.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The controllers that will handle exchange rate data need to extend this class.
 */

@Slf4j
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

	/**
	 * Get the list of exchange currencies either by calling the fetcher or by looking in our database.
	 *
	 * @param fromCurrencyCode the source currency code
	 * @param toCurrencyCode   the target currency codes
	 * @param delay            seconds to allow stale data
	 * @return a list of dtos representing the exchange rates
	 */
	public List<ExchangeRateDTO> getExchangeRatesList(String fromCurrencyCode, String toCurrencyCode, Long delay)
	{
		log.debug("fromCurrencyCode [{}]", fromCurrencyCode);
		log.debug("toCurrencyCode [{}]", toCurrencyCode);
		log.debug("delay [{}]", delay);

		// The result of the controller
		List<ExchangeRateDTO> rates = new ArrayList<>();

		// Sanitize input strings only if provided
		if (fromCurrencyCode != null)
		{
			fromCurrencyCode = Jsoup.clean(fromCurrencyCode, Safelist.basic());
		}
		if (toCurrencyCode != null)
		{
			toCurrencyCode = Jsoup.clean(toCurrencyCode, Safelist.basic());
		}

		// In case the user doesn't give a delay or gives a negative delay we must set the delay to the default value.
		if (delay == null || delay < 0)
		{
			delay = getConfig().getDelayNewRequestSeconds();
		}

		// In case the user doesn't provide a source currency then the default currency (e.g. EUR) will be used.
		if (StringUtils.isBlank(fromCurrencyCode))
		{
			fromCurrencyCode = getConfig().getDefaultFromCurrency();
		}

		// If the user gives a coma separated list of target currencies then we must transform it to a list of currency codes and eliminate any whitespace characters
		List<String> toCurrencyCodes = StringUtils.isBlank(toCurrencyCode) ? null : Arrays.stream(toCurrencyCode.split(",")).map(String::trim).toList();

		// If delay is 0 that means that we must always call the exchange rate fetcher and NOT use our database.
		if (delay.equals(0L))
		{
			rates.addAll(getDirectlyFromFetcher(fromCurrencyCode, toCurrencyCodes));
		}
		else
		{
			// The minimum value that the column insertedAt must have
			long minTimeThreshold = DateUtils.currentTimeInMillisMinusSeconds(delay);

			// First we must check whether any exchange rate records exist in our database
			// because if no records exist then there is no point in looking anymore in the database
			// we must call the fetcher.
			if (exchangeRateService.existForCurrencyAfterTime(fromCurrencyCode, minTimeThreshold))
			{
				log.trace("Some records exist in the database.");

				// If the user did not specify any target currencies then we assume that
				// all the currency combinations with the source currency must be provided.
				// So we must obtain them from our database.
				if (CollectionUtils.isEmpty(toCurrencyCodes))
				{
					// First we refresh the currencies just to be sure.
					refreshCurrenciesIfNeeded();

					// We must exclude the source currency from the results because
					// the exchange rate for the same currency is always 1.
					toCurrencyCodes = currencyService.getAllCurrencyCodesExcept(fromCurrencyCode);
				}

				// Now we are going to search in our database for every combination between the source
				// currency and every target currency in the list.
				// If a record is not found for a specific combination then we must put the target
				// currency of the combination to a separate list so that we can call the exchange rate fetcher
				// only for the target currencies that were not found.
				List<String> notFoundCodes = new ArrayList<>();

				for (String toCode : toCurrencyCodes)
				{
					log.trace("Processing target currency [{}]", toCode);

					ExchangeRateDTO exchangeRateDTO = exchangeRateService.findFirstRateDTOAfterTime(fromCurrencyCode, toCode, minTimeThreshold);

					// If found in our database
					if (exchangeRateDTO != null)
					{
						log.trace("Found in the database");

						rates.add(exchangeRateDTO);
					}
					// If not found in the database then add to the not found list
					else
					{
						log.trace("NOT Found in the database");

						notFoundCodes.add(toCode);
					}
				}

				// Call the exchange rate fetcher if the not found list is not empty
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

	/**
	 * Call the exchange rates fetcher and retrieve a list of exchange rates
	 *
	 * @param fromCurrencyCode the source currency code
	 * @param toCurrencyCodes  the target currency codes
	 * @return a list of exchange rate information in a dto format
	 */
	public List<ExchangeRateDTO> getDirectlyFromFetcher(String fromCurrencyCode, List<String> toCurrencyCodes)
	{
		log.trace("Going to retrieve data directly from the fetcher");
		log.trace("fromCurrencyCode [{}]", fromCurrencyCode);
		log.trace("toCurrencyCodes [{}]", toCurrencyCodes);

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

	/**
	 * If no currencies exist in our database then call the currency fetcher
	 * to retrieve a list of currencies from the external datasource
	 * and save those currencies in our database.
	 */
	private void refreshCurrenciesIfNeeded()
	{
		if (!currencyService.exist())
		{
			log.trace("No currencies found. Going to refresh them.");

			currencyService.refreshCurrencies(currencyFetcher.getAllCurrencies());
		}
	}

	@Override
	protected ExchangeRateFetcher getFetcher()
	{
		return rateFetcher;
	}
}
