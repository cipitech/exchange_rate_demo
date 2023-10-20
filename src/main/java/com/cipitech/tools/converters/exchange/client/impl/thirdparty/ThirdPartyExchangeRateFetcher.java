package com.cipitech.tools.converters.exchange.client.impl.thirdparty;

import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto.ThirdPartyResponseDTO;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Profile(Globals.Profiles.THIRD_PARTY)
public class ThirdPartyExchangeRateFetcher implements ExchangeRateFetcher
{
	private final ThirdPartyWebClient thirdPartyWebClient;

	public ThirdPartyExchangeRateFetcher(ThirdPartyWebClient thirdPartyWebClient)
	{
		this.thirdPartyWebClient = thirdPartyWebClient;
	}

	@Override
	public Double getExchangeRateBetweenCurrencies(String fromCurrencyCode, String toCurrencyCode)
	{
		ThirdPartyResponseDTO response = thirdPartyWebClient.callRateEndpoint(fromCurrencyCode, List.of(toCurrencyCode));

		if (response.getSuccess())
		{
			Optional<Map.Entry<String, Double>> firstEntryOpt = response.getQuotes().entrySet().stream().findFirst();
			if (firstEntryOpt.isPresent())
			{
				Map.Entry<String, Double> firstEntry = firstEntryOpt.get();

				String toCurrencyResponse = firstEntry.getKey().replaceFirst(response.getSource(), "");
				if (toCurrencyCode.equals(toCurrencyResponse))
				{
					return firstEntry.getValue();
				}
				else
				{
					log.error("Wrong currency exchange rate was returned");
				}
			}
		}
		else
		{
			log.error("The call to the third party API was not successful");
			if (response.getError() != null)
			{
				log.error("Error Code [{}]: {}", response.getError().getCode(), response.getError().getInfo());
			}
		}

		return 0D;
	}

	@Override
	public List<Double> getExchangeRateBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes)
	{
		return null;
	}

	@Override
	public List<Double> getAllExchangeRatesForCurrency(String fromCurrencyCode)
	{
		return null;
	}
}
