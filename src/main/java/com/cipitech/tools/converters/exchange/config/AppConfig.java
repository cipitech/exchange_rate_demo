package com.cipitech.tools.converters.exchange.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Spring boot properties that have a general purpose.
 */

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig
{
	// The time (in seconds) to wait before calling the exchange rate fetcher for retrieving the live data.
	// If we always want to call the fetcher then we must set delay to 0.
	private Long   delayNewRequestSeconds;
	private String defaultFromCurrency; // The default currency code to put as the source if the user does not specify one.
	private String ignoreCurrencies; // Some currencies produce an error in the third api website, so we must ignore them in order for our application to run without problems.

	public AppConfig()
	{
		log.debug("AppConfig Loaded...");
	}
}
