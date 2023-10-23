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

/**
 * A DTO that is the deserialization of the response that the third party API returns.
 */

@Getter
@Setter
public class ThirdPartyResponseDTO implements Serializable
{
	private Map<String, String> currencies; // key: Currency code, Value: currency Description => {"USD": "United States Dollar"}
	private Map<String, Double> quotes; // key: <SOURCE_CURRENCY_CODE><TARGET_CURRENCY_CODE>, Value: exchange rate => {"EURUSD": 1.068437}
	private String              source; //the from currency code => "EUR"
	private Long                timestamp;
	private Boolean             success;
	private String              terms; // a link to the TOC page
	private String              privacy; //a link to the privacy page
	private Error               error; //the error information

	/**
	 * When you call the third API with the same currency code as 'from' and 'to' (e.g. EUR) then the quotes field is an empty array,
	 * not a map as it does in a normal call.
	 * So we have to implement a custom deserializer that checks if the quotes field is an array and returns an empty map.
	 *
	 * @param node the JSON node for the quotes field
	 */
	@JsonProperty("quotes")
	public void setQuotes(JsonNode node)
	{
		if (node.isArray())
		{
			this.quotes = new HashMap<>();
		}
		else
		{
			this.quotes = new ObjectMapper().convertValue(node, new TypeReference<>()
			{
			});
		}
	}

	public String getErrorMessageInfo()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("The call to the third party API was not successful. ");
		if (getError() != null)
		{
			sb.append(String.format("Error Code [%s]: %s", getError().getCode(), getError().getInfo()));
		}

		return sb.toString();
	}

	@Getter
	@Setter
	public static class Error
	{
		private int    code; // custom error code. For more info visit https://exchangerate.host/documentation
		private String type;
		private String info;
	}
}
