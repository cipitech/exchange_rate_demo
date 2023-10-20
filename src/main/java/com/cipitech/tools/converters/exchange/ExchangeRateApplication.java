package com.cipitech.tools.converters.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author acipi
 */

@SpringBootApplication
@EntityScan("com.cipitech.tools.converters.exchange.model")
@EnableJpaRepositories("com.cipitech.tools.converters.exchange.repository")
@EnableTransactionManagement
public class ExchangeRateApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateApplication.class, args);
    }
}
