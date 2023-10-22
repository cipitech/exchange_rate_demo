package com.cipitech.tools.converters.exchange;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author acipi
 */

@Slf4j
@SpringBootApplication
@EntityScan("com.cipitech.tools.converters.exchange.model")
@EnableJpaRepositories("com.cipitech.tools.converters.exchange.repository")
@EnableTransactionManagement
public class ExchangeRateApplication
{
	public static void main(String[] args)
	{
		log.info("Initializing ExchangeRateApplication...");

		SpringApplication.run(ExchangeRateApplication.class, args);
	}
}
