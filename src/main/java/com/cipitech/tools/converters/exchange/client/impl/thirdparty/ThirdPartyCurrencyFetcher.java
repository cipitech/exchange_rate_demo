package com.cipitech.tools.converters.exchange.client.impl.thirdparty;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto.ThirdPartyResponseDTO;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import com.cipitech.tools.converters.exchange.error.exceptions.RecordNotFoundException;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Profile(Globals.Profiles.THIRD_PARTY)
public class ThirdPartyCurrencyFetcher implements CurrencyFetcher
{
	private final ThirdPartyWebClient thirdPartyWebClient;

	public ThirdPartyCurrencyFetcher(ThirdPartyWebClient thirdPartyWebClient)
	{
		this.thirdPartyWebClient = thirdPartyWebClient;
	}

	@Override
	public List<CurrencyDTO> getAllCurrencies()
	{
		ThirdPartyResponseDTO response = thirdPartyWebClient.callCurrencyEndpoint();

		if (response.getSuccess())
		{
			return response.getCurrencies().entrySet().stream().map(mapEntry -> CurrencyDTO.builder().code(mapEntry.getKey()).description(mapEntry.getValue()).build()).toList();
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
	}
}
