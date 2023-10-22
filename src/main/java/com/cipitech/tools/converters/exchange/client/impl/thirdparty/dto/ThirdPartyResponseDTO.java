package com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
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

	/**
	 * When you call the third API with the same currency code as 'from' and 'to' (e.g. EUR) then the quotes field is an empty array,
	 * not a map as it does in a normal call.
	 * So we have to implement a custom deserializer that checks if the quotes field is an array and returns an empty map.
	 * @param node the JSON node for the quotes field
	 */
	@JsonProperty("quotes")
	public void setQuotes(JsonNode node)
	{
		if(node.isArray())
		{
			this.quotes = new HashMap<>();
		}
		else
		{
			this.quotes = new ObjectMapper().convertValue(node, new TypeReference<>(){});
		}
	}

	@Getter
	@Setter
	public static class Error
	{
		private int    code;
		private String type;
		private String info;
	}
}
