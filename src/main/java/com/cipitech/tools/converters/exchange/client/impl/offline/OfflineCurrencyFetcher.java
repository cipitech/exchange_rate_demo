package com.cipitech.tools.converters.exchange.client.impl.offline;

import com.cipitech.tools.converters.exchange.client.api.AbstractCurrencyFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.config.OfflineConfig;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.error.exceptions.ServerErrorException;
import com.cipitech.tools.converters.exchange.utils.FileUtils;
import com.cipitech.tools.converters.exchange.utils.Globals;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * The CurrencyFetcher implementation for the offline (file-system) datasource
 */

@Slf4j
@Service
@Profile(Globals.Profiles.OFFLINE)
public class OfflineCurrencyFetcher extends AbstractCurrencyFetcher
{
	private final OfflineConfig offlineConfig;

	public OfflineCurrencyFetcher(AppConfig appConfig, OfflineConfig offlineConfig)
	{
		super(appConfig);
		this.offlineConfig = offlineConfig;
	}

	@Override
	public List<CurrencyDTO> getAllCurrencies()
	{
		log.trace("Offline: getAllCurrencies");

		Map<String, String> currencyMap;

		// Read the JSON file that contains a Map of all the available currencies
		try
		{
			// Convert json string to map
			currencyMap = new ObjectMapper().readValue(FileUtils.getFileBytes(offlineConfig.getCurrenciesFile()), Map.class);
		}
		catch (Exception e)
		{
			log.error("Error while reading currencies file.", e);

			throw new ServerErrorException("Could not read the JSON file that contains the currency information.");
		}

		// If the currencies file was not empty
		if (!CollectionUtils.isEmpty(currencyMap))
		{
			log.debug("Found {} new currencies in {}", currencyMap.size(), offlineConfig.getCurrenciesFile());

			return convertMapToDTO(currencyMap);
		}
		else
		{
			log.error("No data was found in {}", offlineConfig.getCurrenciesFile());

			throw new RecordNotFoundException("Could not find any information in the currencies JSON file.");
		}
	}
}
