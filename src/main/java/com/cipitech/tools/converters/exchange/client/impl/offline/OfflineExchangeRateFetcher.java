package com.cipitech.tools.converters.exchange.client.impl.offline;

import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.OfflineConfig;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.error.exceptions.ServerErrorException;
import com.cipitech.tools.converters.exchange.utils.FileUtils;
import com.cipitech.tools.converters.exchange.utils.Globals;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Profile(Globals.Profiles.OFFLINE)
public class OfflineExchangeRateFetcher implements ExchangeRateFetcher
{
	private final OfflineConfig config;

	public OfflineExchangeRateFetcher(OfflineConfig config)
	{
		this.config = config;
	}

	@Override
	public List<ExchangeRateDTO> getExchangeRateBetweenCurrencies(@NonNull String fromCurrencyCode, List<String> toCurrencyCodes)
	{
		Map<String, Double> ratesMap;

		String filename = config.getRatesFolder() + fromCurrencyCode + config.getRatesFileSuffix();

		try
		{
			//convert json string to object
			ratesMap = new ObjectMapper().readValue(FileUtils.getFileBytes(filename), Map.class);
		}
		catch (Exception e)
		{
			log.error("Error while reading exchange rates file {}", filename, e);

			throw new ServerErrorException("Could not read the JSON file that contains the exchange rates information.");
		}

		if (!CollectionUtils.isEmpty(ratesMap))
		{
			log.debug("Found {} new rates in {}", ratesMap.size(), filename);

			List<ExchangeRateDTO> ratesList = new ArrayList<>();

			if (CollectionUtils.isEmpty(toCurrencyCodes))
			{
				ratesMap.forEach((key, value) -> ratesList.add(ExchangeRateDTO.builder()
						.fromCurrency(CurrencyDTO.builder().code(fromCurrencyCode.toUpperCase()).build())
						.toCurrency(CurrencyDTO.builder().code(key.replaceFirst(fromCurrencyCode.toUpperCase(), StringUtils.EMPTY)).build())
						.rate(value).build()));
			}
			else
			{
				toCurrencyCodes.forEach(toCurrencyCode ->
				{
					Double rateValue = ratesMap.get(fromCurrencyCode.toUpperCase() + toCurrencyCode.toUpperCase());

					ratesList.add(ExchangeRateDTO.builder()
							.fromCurrency(CurrencyDTO.builder().code(fromCurrencyCode.toUpperCase()).build())
							.toCurrency(CurrencyDTO.builder().code(toCurrencyCode.toUpperCase()).build())
							.rate(rateValue != null ? rateValue : -1D).build());
				});
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
