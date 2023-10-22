package com.cipitech.tools.converters.exchange.client.impl.thirdparty;

import com.cipitech.tools.converters.exchange.config.ThirdPartyConfig;
import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Here we create the web client bean using webflux
 * that will make all the calls to the third party API.
 */

@Slf4j
@Component
@Profile(Globals.Profiles.THIRD_PARTY)
public class ThirdPartyWebClientConfig
{
	private final ThirdPartyConfig thirdPartyConfig;

	public ThirdPartyWebClientConfig(ThirdPartyConfig thirdPartyConfig)
	{
		this.thirdPartyConfig = thirdPartyConfig;
	}

	@Bean
	public WebClient webClient()
	{
		log.info("Creating webflux WebClient bean...");

		return WebClient.builder()
				.baseUrl(this.thirdPartyConfig.getUrl())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}
