package com.cipitech.tools.converters.exchange.client.impl.offline;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
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

@Slf4j
@Service
@Profile(Globals.Profiles.OFFLINE)
public class OfflineCurrencyFetcher implements CurrencyFetcher
{
	private final OfflineConfig config;

	public OfflineCurrencyFetcher(OfflineConfig config)
	{
		this.config = config;
	}

	@Override
	public List<CurrencyDTO> getAllCurrencies()
	{
		Map<String, String> currencyMap;

		try
		{
			//convert json string to object
			currencyMap = new ObjectMapper().readValue(FileUtils.getFileBytes(config.getCurrenciesFile()), Map.class);
		}
		catch (Exception e)
		{
			log.error("Error while reading currencies file.", e);

			throw new ServerErrorException("Could not read the JSON file that contains the currency information.");
		}

		if (!CollectionUtils.isEmpty(currencyMap))
		{
			log.debug("Found {} new currencies in {}", currencyMap.size(), config.getCurrenciesFile());

			return currencyMap.entrySet().stream().map(mapEntry -> CurrencyDTO.builder().code(mapEntry.getKey()).description(mapEntry.getValue()).build()).toList();
		}
		else
		{
			log.error("No data was found in {}", config.getCurrenciesFile());

			throw new RecordNotFoundException("Could not find any information in the currencies JSON file.");
		}
	}
}
