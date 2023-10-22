package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.ExchangeRateApplicationTest;
import com.cipitech.tools.converters.exchange.utils.Globals;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExchangeRateApplicationTest.class)
@Slf4j
public class ConversionControllerTest extends TestCase
{
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp()
	{
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}

	@Test
	public void whenGetPing_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Conversion.CONTROLLER + Globals.Endpoints.PING)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetValueWithNoToCurrencyAndDelay0_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Conversion.CONTROLLER + Globals.Endpoints.Conversion.value)
								.queryParam(Globals.Parameters.Conversion.amount, "20")
								.queryParam(Globals.Parameters.Conversion.from, "EUR")
								.queryParam(Globals.Parameters.Conversion.delay, "0")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetValueWithDelay0_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Conversion.CONTROLLER + Globals.Endpoints.Conversion.value)
								.queryParam(Globals.Parameters.Conversion.amount, "20")
								.queryParam(Globals.Parameters.Conversion.from, "EUR")
								.queryParam(Globals.Parameters.Conversion.to, "USD")
								.queryParam(Globals.Parameters.Conversion.delay, "0")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].exchangeRate.fromCurrency.code", Matchers.is("EUR")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].exchangeRate.toCurrency.code", Matchers.is("USD")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].exchangeRate.rate", Matchers.is(Matchers.notNullValue())))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetValueWithTheSameFromAndTo_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Conversion.CONTROLLER + Globals.Endpoints.Conversion.value)
								.queryParam(Globals.Parameters.Conversion.amount, "20")
								.queryParam(Globals.Parameters.Conversion.from, "USD")
								.queryParam(Globals.Parameters.Conversion.to, "USD")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].exchangeRate.fromCurrency.code", Matchers.is("USD")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].exchangeRate.toCurrency.code", Matchers.is("USD")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].exchangeRate.rate", Matchers.is(1D)))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetValueWithWrongFrom_thenReturnNotFound() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Conversion.CONTROLLER + Globals.Endpoints.Conversion.value)
								.queryParam(Globals.Parameters.Conversion.amount, "20")
								.queryParam(Globals.Parameters.Conversion.from, "EURT")
								.queryParam(Globals.Parameters.Conversion.delay, "0")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(404)))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetValueWithWrongTo_thenReturnNotFound() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Conversion.CONTROLLER + Globals.Endpoints.Conversion.value)
								.queryParam(Globals.Parameters.Conversion.amount, "20")
								.queryParam(Globals.Parameters.Conversion.from, "EUR")
								.queryParam(Globals.Parameters.Conversion.to, "USDT")
								.queryParam(Globals.Parameters.Conversion.delay, "0")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(404)))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetValueWithDelay0MultipleToCurrency_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Conversion.CONTROLLER + Globals.Endpoints.Conversion.value)
								.queryParam(Globals.Parameters.Conversion.amount, "20")
								.queryParam(Globals.Parameters.Conversion.from, "EUR")
								.queryParam(Globals.Parameters.Conversion.to, "USD,GBP,  JPY")
								.queryParam(Globals.Parameters.Conversion.delay, "0")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].exchangeRate.fromCurrency.code", Matchers.is("EUR")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].exchangeRate.toCurrency.code", Matchers.is("USD")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].exchangeRate.rate", Matchers.is(Matchers.notNullValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetValueWithNoParameters_thenReturnBadRequest() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Conversion.CONTROLLER + Globals.Endpoints.Conversion.value)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetValueWithNoToCurrencyAndNoFromCurrencyAndDelay0_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Conversion.CONTROLLER + Globals.Endpoints.Conversion.value)
								.queryParam(Globals.Parameters.Conversion.amount, "20")
								.queryParam(Globals.Parameters.Conversion.delay, "0")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].exchangeRate.fromCurrency.code", Matchers.is("EUR")))
				.andReturn().getResponse().getContentAsString());
	}
}
