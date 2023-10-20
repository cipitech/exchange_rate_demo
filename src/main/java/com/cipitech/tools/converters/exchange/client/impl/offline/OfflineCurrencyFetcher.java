package com.cipitech.tools.converters.exchange.client.impl.offline;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.config.OfflineConfig;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

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
		return null;
	}
}
