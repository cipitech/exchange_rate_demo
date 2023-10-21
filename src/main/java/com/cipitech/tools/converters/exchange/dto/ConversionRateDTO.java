package com.cipitech.tools.converters.exchange.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRateDTO implements Serializable
{
	private ExchangeRateDTO exchangeRate;
	private Double          fromAmount;
	private BigDecimal      toAmount;
	private String          description;
}
