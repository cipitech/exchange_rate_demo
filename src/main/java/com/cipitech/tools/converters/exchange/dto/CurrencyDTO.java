package com.cipitech.tools.converters.exchange.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDTO implements Serializable
{
	private String code;
	private String description;
}
