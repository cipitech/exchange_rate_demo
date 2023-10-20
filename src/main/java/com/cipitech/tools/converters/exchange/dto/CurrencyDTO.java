package com.cipitech.tools.converters.exchange.dto;

import com.cipitech.tools.converters.exchange.dto.base.BaseRecordDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO extends BaseRecordDTO
{
	private String code;
	private String description;
}
