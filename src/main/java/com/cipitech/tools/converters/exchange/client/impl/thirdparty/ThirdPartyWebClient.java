package com.cipitech.tools.converters.exchange.client.impl.thirdparty;

import com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto.ThirdPartyResponseDTO;
import com.cipitech.tools.converters.exchange.config.ThirdPartyConfig;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Profile(Globals.Profiles.THIRD_PARTY)
public class ThirdPartyWebClient
{
	private final ThirdPartyConfig config;
	private final WebClient        defaultWebClient;

	public ThirdPartyWebClient(ThirdPartyConfig config, WebClient defaultWebClient)
	{
		this.config = config;
		this.defaultWebClient = defaultWebClient;
	}

	public ThirdPartyResponseDTO callRateEndpoint(String fromCurrencyCode, List<String> toCurrencyCodes)
	{
		Optional<String> currCodesOpt = Optional.ofNullable(CollectionUtils.isEmpty(toCurrencyCodes) ? null : String.join(",", toCurrencyCodes));

		Mono<ThirdPartyResponseDTO> responseMono = defaultWebClient
				.get()
				.uri(uriBuilder -> uriBuilder.path("/" + config.getRateEndpoint())
						.queryParam(config.getRequestCodes().getKey(), config.getAccessKey())
						.queryParamIfPresent(config.getRequestCodes().getFrom(), Optional.ofNullable(fromCurrencyCode))
						.queryParamIfPresent(config.getRequestCodes().getTo(), currCodesOpt)
						.build())
				.retrieve()
				.bodyToMono(ThirdPartyResponseDTO.class);

		return responseMono.block();
	}

	public ThirdPartyResponseDTO callCurrencyEndpoint()
	{
		Mono<ThirdPartyResponseDTO> responseMono = defaultWebClient
				.get()
				.uri(uriBuilder -> uriBuilder.path("/" + config.getCurrencyEndpoint())
						.queryParam(config.getRequestCodes().getKey(), config.getAccessKey())
						.build())
				.retrieve()
				.bodyToMono(ThirdPartyResponseDTO.class);

		return responseMono.block();
	}
}
