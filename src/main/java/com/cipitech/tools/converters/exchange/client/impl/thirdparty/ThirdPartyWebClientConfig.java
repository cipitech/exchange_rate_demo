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

@Slf4j
@Component
@Profile(Globals.Profiles.THIRD_PARTY)
public class ThirdPartyWebClientConfig
{
	private final ThirdPartyConfig config;

	public ThirdPartyWebClientConfig(ThirdPartyConfig config)
	{
		this.config = config;
	}

	@Bean
	public WebClient webClient()
	{
		return WebClient.builder()
				.baseUrl(this.config.getUrl())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}
