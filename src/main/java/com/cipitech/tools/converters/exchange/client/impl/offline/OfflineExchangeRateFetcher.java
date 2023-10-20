package com.cipitech.tools.converters.exchange.client.impl.offline;

import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.config.OfflineConfig;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public List<ExchangeRateDTO> getExchangeRateBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes)
	{
		return null;
	}
}
