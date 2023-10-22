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

/**
 * This service utilizes the webflux webclient and calls the two different API
 * endpoints that the third party website provides.
 * The first endpoint returns all the available currencies
 * and the second endpoints returns the exchange rate information.
 */

@Slf4j
@Service
@Profile(Globals.Profiles.THIRD_PARTY)
public class ThirdPartyWebClient
{
	private final ThirdPartyConfig thirdPartyConfig;
	private final WebClient        defaultWebClient;

	public ThirdPartyWebClient(ThirdPartyConfig thirdPartyConfig, WebClient defaultWebClient)
	{
		this.thirdPartyConfig = thirdPartyConfig;
		this.defaultWebClient = defaultWebClient;
	}

	/**
	 * Calls the third party endpoint that provides the exchange rates information
	 *
	 * @param fromCurrencyCode The code of the currency that we want to get the exchange rate from
	 * @param toCurrencyCodes  The list of codes of the currencies that we want to get the exchange rate to
	 * @return the website response as a dto
	 */
	public ThirdPartyResponseDTO callRateEndpoint(String fromCurrencyCode, List<String> toCurrencyCodes)
	{
		log.trace("Third party exchange rates endpoint is called.");
		log.trace("fromCurrencyCode [{}]", fromCurrencyCode);

		// The API needs the source currency codes as a comma separated string
		Optional<String> currCodesOpt = Optional.ofNullable(CollectionUtils.isEmpty(toCurrencyCodes) ? null : String.join(",", toCurrencyCodes));
		log.trace("toCurrencyCodes [{}]", currCodesOpt);

		Mono<ThirdPartyResponseDTO> responseMono = defaultWebClient
				.get()
				.uri(uriBuilder -> uriBuilder.path("/" + thirdPartyConfig.getRateEndpoint())
						// An access key must be provided for all calls. Get your access key by registering to the website
						.queryParam(thirdPartyConfig.getRequestCodes().getKey(), thirdPartyConfig.getAccessKey())
						// It is not required to provide a source currency.
						// The website has a default one that will be used instead.
						.queryParamIfPresent(thirdPartyConfig.getRequestCodes().getFrom(), Optional.ofNullable(fromCurrencyCode))
						// It is not required to provide target currencies.
						// If not provided the website assumes that you want all the available currencies
						.queryParamIfPresent(thirdPartyConfig.getRequestCodes().getTo(), currCodesOpt)
						.build())
				.retrieve()
				// The response from the website is automatically converted to a dto
				.bodyToMono(ThirdPartyResponseDTO.class);

		return responseMono.block();
	}

	/**
	 * Calls the third party endpoint that provides the information about the available currencies
	 *
	 * @return the website response as a dto
	 */
	public ThirdPartyResponseDTO callCurrencyEndpoint()
	{
		log.trace("Third party currency endpoint is called.");

		Mono<ThirdPartyResponseDTO> responseMono = defaultWebClient
				.get()
				.uri(uriBuilder -> uriBuilder.path("/" + thirdPartyConfig.getCurrencyEndpoint())
						// An access key must be provided for all calls. Get your access key by registering to the website
						.queryParam(thirdPartyConfig.getRequestCodes().getKey(), thirdPartyConfig.getAccessKey())
						.build())
				.retrieve()
				// The response from the website is automatically converted to a dto
				.bodyToMono(ThirdPartyResponseDTO.class);

		return responseMono.block();
	}
}
