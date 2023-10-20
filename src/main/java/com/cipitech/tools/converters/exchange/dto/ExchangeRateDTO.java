package com.cipitech.tools.converters.exchange.dto;

import com.cipitech.tools.converters.exchange.dto.base.BaseRecordDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDTO extends BaseRecordDTO
{
	private CurrencyDTO fromCurrency;
	private CurrencyDTO toCurrency;
	private Double      rate;
}
