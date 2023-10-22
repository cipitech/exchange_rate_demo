package com.cipitech.tools.converters.exchange.client.impl.thirdparty;

import com.cipitech.tools.converters.exchange.client.api.AbstractExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto.ThirdPartyResponseDTO;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The ExchangeRateFetcher implementation for the third party (Rest API) datasource
 */

@Slf4j
@Primary
@Service
@Profile(Globals.Profiles.THIRD_PARTY)
public class ThirdPartyExchangeRateFetcher extends AbstractExchangeRateFetcher
{
	private final ThirdPartyWebClient thirdPartyWebClient;

	public ThirdPartyExchangeRateFetcher(ThirdPartyWebClient thirdPartyWebClient)
	{
		this.thirdPartyWebClient = thirdPartyWebClient;
	}

	@Override
	public List<ExchangeRateDTO> getExchangeRateBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes)
	{
		log.trace("Getting exchange rate info for currency {}", fromCurrencyCode.toUpperCase());

		// Call the endpoint that returns the exchange rates
		ThirdPartyResponseDTO response = thirdPartyWebClient.callRateEndpoint(fromCurrencyCode, toCurrencyCodes);

		List<ExchangeRateDTO> ratesList = new ArrayList<>();

		// If the call was successful then the "success" property is set to true
		if (response.getSuccess())
		{
			// If no target currencies were provided then all the available combinations
			// for the source currency and every other available currency must be provided.
			if (CollectionUtils.isEmpty(toCurrencyCodes))
			{
				processAllCurrencies(response.getQuotes(), response.getSource(), ratesList);
			}
			else
			{
				toCurrencyCodes.forEach(toCurrencyCode ->
						processSingleCurrency(response.getQuotes(), fromCurrencyCode, toCurrencyCode, ratesList));
			}
		}
		// If the call was not successful the "success" property is set to false.
		else
		{
			log.error("Exchange rates endpoint call was not successful");

			throw new RecordNotFoundException(response.getErrorMessageInfo());
		}

		return ratesList;
	}
}
