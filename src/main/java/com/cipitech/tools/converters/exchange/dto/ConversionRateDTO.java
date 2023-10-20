package com.cipitech.tools.converters.exchange.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRateDTO implements Serializable
{
	private ExchangeRateDTO exchangeRate;
	private Double          fromAmount;
	private Double          toAmount;
	private String          description;
}
