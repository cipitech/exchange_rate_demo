package com.cipitech.tools.converters.exchange.config;

import com.cipitech.tools.converters.exchange.utils.Globals;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = Globals.Profiles.THIRD_PARTY)
@Profile(Globals.Profiles.THIRD_PARTY)
public class ThirdPartyConfig
{
	private String       url; //The base URL of the third-party API
	private String       rateEndpoint; //The endpoint that will provide the necessary exchange rate info
	private String       currencyEndpoint; //The endpoint that returns the full list of all the available currencies
	private String       accessKey;
	private RequestCodes requestCodes;

	public ThirdPartyConfig()
	{
		log.info("ThirdPartyConfig Loaded...");
	}

	@Getter
	@Setter
	public static class RequestCodes
	{
		private String key;
		private String from;
		private String to;
	}
}
