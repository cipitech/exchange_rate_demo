package com.cipitech.tools.converters.exchange.client.impl.thirdparty;

import com.cipitech.tools.converters.exchange.client.api.ExchangeRateFetcher;
import com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto.ThirdPartyResponseDTO;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.dto.ExchangeRateDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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
	public List<ExchangeRateDTO> getExchangeRateBetweenCurrencies(String fromCurrencyCode, List<String> toCurrencyCodes)
	{
		ThirdPartyResponseDTO response = thirdPartyWebClient.callRateEndpoint(fromCurrencyCode, toCurrencyCodes);

		List<ExchangeRateDTO> ratesList = new ArrayList<>();

		if (response.getSuccess())
		{
			if (CollectionUtils.isEmpty(toCurrencyCodes))
			{
				response.getQuotes().forEach((key, value) -> ratesList.add(ExchangeRateDTO.builder()
						.fromCurrency(CurrencyDTO.builder().code(response.getSource()).build())
						.toCurrency(CurrencyDTO.builder().code(key.replaceFirst(response.getSource(), StringUtils.EMPTY)).build())
						.rate(value).build()));
			}
			else
			{
				toCurrencyCodes.forEach(toCurrencyCode ->
				{
					Double rateValue = response.getQuotes().get(fromCurrencyCode.toUpperCase() + toCurrencyCode.toUpperCase());

					if(toCurrencyCode.equalsIgnoreCase(fromCurrencyCode))
					{
						rateValue = 1D;
					}

					if(rateValue == null)
					{
						throw new RecordNotFoundException(String.format("Exchange rate from currency %s to currency %s does not exist. Please try another currency code.", fromCurrencyCode.toUpperCase(), toCurrencyCode.toUpperCase()));
					}

					ratesList.add(ExchangeRateDTO.builder()
							.fromCurrency(CurrencyDTO.builder().code(fromCurrencyCode.toUpperCase()).build())
							.toCurrency(CurrencyDTO.builder().code(toCurrencyCode.toUpperCase()).build())
							.rate(rateValue).build());
				});
			}
		}
		else
		{
			StringBuffer sb = new StringBuffer();

			sb.append("The call to the third party API was not successful. ");
			if (response.getError() != null)
			{
				sb.append(String.format("Error Code [%s]: %s", response.getError().getCode(), response.getError().getInfo()));
			}

			log.error(sb.toString());
			throw new RecordNotFoundException(sb.toString());
		}

		return ratesList;
	}
}
