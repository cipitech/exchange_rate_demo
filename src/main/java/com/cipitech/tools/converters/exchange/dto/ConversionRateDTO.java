package com.cipitech.tools.converters.exchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "The JSON Response Object for the Conversion Rates API")
public class ConversionRateDTO implements Serializable
{
	@Schema(description = "The information of the exchange rate this conversion uses")
	private ExchangeRateDTO exchangeRate;
	@Schema(description = "The amount that will be converted", example = "20.5", implementation = Double.class)
	private Double          fromAmount;
	@Schema(description = "The amount after the conversion to the new currency", example = "23.6", implementation = Double.class)
	private BigDecimal      toAmount;
	@Schema(description = "A brief and user-friendly description of the amount conversion", example = "20.00 EUR = 21.20 USD", implementation = String.class)
	private String          description;

	public static final String exampleOK = "[{\"exchangeRate\": {\"fromCurrency\": {\"code\": \"USD\"},\"toCurrency\": {\"code\": \"EUR\"},\"rate\": 0.939095},\"fromAmount\": 25,\"toAmount\": 23.48,\"description\": \"25.00 USD = 23.48 EUR\"}]";
}
