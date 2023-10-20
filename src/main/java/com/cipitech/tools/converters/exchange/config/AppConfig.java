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
	private Long delayNewRequestSeconds; // The seconds to wait before calling the third party exchange rate API
}
