package com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class ThirdPartyResponseDTO implements Serializable
{
	private Map<String, String> currencies;
	private Map<String, Double> quotes;
	private String              source;
	private Long                timestamp;
	private Boolean             success;
	private String              terms;
	private String              privacy;
	private Error               error;

	@Getter
	@Setter
	public static class Error
	{
		private int    code;
		private String type;
		private String info;
	}
}
