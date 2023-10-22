package com.cipitech.tools.converters.exchange.client.impl.thirdparty;

import com.cipitech.tools.converters.exchange.client.api.AbstractCurrencyFetcher;
import com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto.ThirdPartyResponseDTO;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The CurrencyFetcher implementation for the third party (Rest API) datasource
 */

@Slf4j
@Primary
@Service
@Profile(Globals.Profiles.THIRD_PARTY)
public class ThirdPartyCurrencyFetcher extends AbstractCurrencyFetcher
{
	private final ThirdPartyWebClient thirdPartyWebClient;

	public ThirdPartyCurrencyFetcher(AppConfig appConfig, ThirdPartyWebClient thirdPartyWebClient)
	{
		super(appConfig);
		this.thirdPartyWebClient = thirdPartyWebClient;
	}

	@Override
	public List<CurrencyDTO> getAllCurrencies()
	{
		log.trace("Third-Party: getAllCurrencies");

		// Call the endpoint that returns the currencies
		ThirdPartyResponseDTO response = thirdPartyWebClient.callCurrencyEndpoint();

		// If the call was successful then the "success" property is set to true
		if (response.getSuccess())
		{
			return convertMapToDTO(response.getCurrencies());
		}
		// If the call was not successful the "success" property is set to false.
		else
		{
			log.error("Currency endpoint call was not successful.");

			throw new RecordNotFoundException(response.getErrorMessageInfo());
		}
	}
}
