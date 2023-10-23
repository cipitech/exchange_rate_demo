package com.cipitech.tools.converters.exchange.client.api;

import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractExchangeRateFetcher implements ExchangeRateFetcher
{
	public void processAllCurrencies(Map<String, Double> ratesMap, @NonNull String fromCurrencyCode, List<ExchangeRateDTO> ratesList)
	{
		log.trace("No target currency was provided. Going to fetch all the combinations.");

		ratesMap.forEach((key, value) -> ratesList.add(ExchangeRateDTO.builder()
				.fromCurrency(CurrencyDTO.builder().code(fromCurrencyCode).build())
				// The key of the exchange rates map is of the type "<SOURCE_CURRENCY_CODE><TARGET_CURRENCY_CODE>"
				// For example "EURUSD" for source EUR and target USD.
				// So if we want to acquire the target currency code we must remove from the key the source currency code
				// which we already know as it is given by the params.
				.toCurrency(CurrencyDTO.builder().code(key.replaceFirst(fromCurrencyCode, StringUtils.EMPTY)).build())
				// The value of the exchange rates map is the actual exchange rate numeric value.
				.rate(value).build()));
	}

	public void processSingleCurrency(Map<String, Double> ratesMap, @NonNull String fromCurrencyCode, @NonNull String toCurrencyCode, List<ExchangeRateDTO> ratesList)
	{
		log.trace("Processing currency {}", toCurrencyCode);

		// Get the exchange rate entry from the map by giving the key "<SOURCE_CURRENCY_CODE><TARGET_CURRENCY_CODE>"
		Double rateValue = ratesMap.get(fromCurrencyCode.toUpperCase() + toCurrencyCode.toUpperCase());

		// If the user asked to get an exchange rate for the same currency
		// then that combination does not exist in the ratesMap.
		// So the rateValue will be null.
		// But it is safe to assume that the exchange rate from one currency
		// to the same currency is 1.
		if (toCurrencyCode.equalsIgnoreCase(fromCurrencyCode))
		{
			rateValue = 1D;
		}

		// If no entry was found in the ratesMap that means that the user
		// requested a source currency that does not exist so an error must be returned.
		if (rateValue == null)
		{
			log.error("Currency {} does not exist.", toCurrencyCode);

			throw new RecordNotFoundException(String.format("Exchange rate from currency %s to currency %s does not exist. Please try another currency code.", fromCurrencyCode.toUpperCase(), toCurrencyCode.toUpperCase()));
		}

		ratesList.add(ExchangeRateDTO.builder()
				.fromCurrency(CurrencyDTO.builder().code(fromCurrencyCode.toUpperCase()).build())
				.toCurrency(CurrencyDTO.builder().code(toCurrencyCode.toUpperCase()).build())
				.rate(rateValue).build());
	}
}
