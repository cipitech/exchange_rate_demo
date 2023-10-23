package com.cipitech.tools.converters.exchange.dto;

import com.cipitech.tools.converters.exchange.dto.base.BaseRecordDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "The JSON Response Object for the Exchange Rates API")
public class ExchangeRateDTO extends BaseRecordDTO
{
	@Schema(description = "The information of the currency the exchange rate is from", implementation = CurrencyDTO.class)
	private CurrencyDTO fromCurrency;
	@Schema(description = "The information of the currency the exchange rate is to", implementation = CurrencyDTO.class)
	private CurrencyDTO toCurrency;
	@Schema(description = "The exchange rate value", example = "1.060164", implementation = Double.class)
	private Double      rate;
}
