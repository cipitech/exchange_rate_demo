package com.cipitech.tools.converters.exchange.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig
{
	private Long   delayNewRequestSeconds; // The seconds to wait before calling the third party exchange rate API
	private String defaultFromCurrency; // The default currency code to put as the source if the used does not specify one.
	private String ignoreCurrencies; //Some currencies produce an error in the third api website, so we must ignore them in order for our application to run without problems.
}
