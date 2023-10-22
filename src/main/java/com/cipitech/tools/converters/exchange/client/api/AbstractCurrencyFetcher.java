package com.cipitech.tools.converters.exchange.client.api;

import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
public abstract class AbstractCurrencyFetcher implements CurrencyFetcher
{
	private final AppConfig appConfig;

	protected AbstractCurrencyFetcher(AppConfig appConfig)
	{
		this.appConfig = appConfig;
	}

	public List<CurrencyDTO> convertMapToDTO(Map<String, String> currencyMap)
	{
		return currencyMap.entrySet().stream()
				// Ignore any currencies that might cause a problem like SVC
				.filter(mapEntry -> {
					boolean allow = mapEntry.getKey() != null && !this.appConfig.getIgnoreCurrencies().toUpperCase().contains(mapEntry.getKey().toUpperCase());

					if(!allow)
					{
						log.trace("Found forbidden currency {}....going to skip it.", mapEntry.getKey());
					}

					return allow;
				})
				.map(mapEntry -> CurrencyDTO.builder().code(mapEntry.getKey()).description(mapEntry.getValue()).build()).toList();
	}
}
