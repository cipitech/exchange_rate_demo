package com.cipitech.tools.converters.exchange.client.impl.offline;

import com.cipitech.tools.converters.exchange.client.api.AbstractExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.OfflineConfig;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.error.exceptions.ServerErrorException;
import com.cipitech.tools.converters.exchange.utils.FileUtils;
import com.cipitech.tools.converters.exchange.utils.Globals;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The ExchangeRateFetcher implementation for the offline (file-system) datasource
 */

@Slf4j
@Service
@Profile(Globals.Profiles.OFFLINE)
public class OfflineExchangeRateFetcher extends AbstractExchangeRateFetcher
{
	private final OfflineConfig offlineConfig;

	public OfflineExchangeRateFetcher(OfflineConfig offlineConfig)
	{
		this.offlineConfig = offlineConfig;
	}

	@Override
	public List<ExchangeRateDTO> getExchangeRateBetweenCurrencies(@NonNull String fromCurrencyCode, List<String> toCurrencyCodes)
	{
		log.trace("Getting exchange rate info for currency {}", fromCurrencyCode.toUpperCase());

		Map<String, Double> ratesMap;

		// The filename must be of the type "<3-LETTER CURRENCY CODE>.json", e.g. EUR.json
		String filename = offlineConfig.getRatesFolder() + fromCurrencyCode + offlineConfig.getRatesFileSuffix();

		// Read the JSON file that contains a Map of all the available exchanges rate between the from-currency and all the other available currencies
		try
		{
			// Convert json string to map
			ratesMap = new ObjectMapper().readValue(FileUtils.getFileBytes(filename), Map.class);
		}
		catch (NoSuchFileException e)
		{
			log.error("Could not find file {}", filename, e);

			throw new RecordNotFoundException(String.format("Currency with code %s does not exist in our datasource. Please ask us to generate the necessary data.", fromCurrencyCode));
		}
		catch (Exception e)
		{
			log.error("Error while reading exchange rates file {}", filename, e);

			throw new ServerErrorException("Could not read the JSON file that contains the exchange rates information.");
		}

		// If the exchange rates file was not empty
		if (!CollectionUtils.isEmpty(ratesMap))
		{
			log.debug("Found {} new rates in {}", ratesMap.size(), filename);

			List<ExchangeRateDTO> ratesList = new ArrayList<>();

			// If no target currencies were provided then all the available combinations
			// for the source currency and every other available currency must be provided.
			if (CollectionUtils.isEmpty(toCurrencyCodes))
			{
				processAllCurrencies(ratesMap, fromCurrencyCode.toUpperCase(), ratesList);
			}
			else
			{
				toCurrencyCodes.forEach(toCurrencyCode ->
						processSingleCurrency(ratesMap, fromCurrencyCode, toCurrencyCode, ratesList));
			}

			return ratesList;
		}
		else
		{
			log.error("No data was found in {}", filename);

			throw new RecordNotFoundException("Could not find any information in the exchange rates JSON file.");
		}
	}
}
