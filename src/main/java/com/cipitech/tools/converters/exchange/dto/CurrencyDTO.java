package com.cipitech.tools.converters.exchange.dto;

import com.cipitech.tools.converters.exchange.dto.base.BaseRecordDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "The JSON Response Object for the Currency API")
public class CurrencyDTO extends BaseRecordDTO
{
	@Schema(description = "The 3-letter code of the currency (uppercase)", example = "USD", implementation = String.class)
	private String code;
	@Schema(description = "The full description / official name of the currency", example = "United States Dollar", implementation = String.class)
	private String description;
}
