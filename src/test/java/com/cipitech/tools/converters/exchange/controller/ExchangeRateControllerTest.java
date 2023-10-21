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
public class ExchangeRateControllerTest extends TestCase
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
						MockMvcRequestBuilders.get(Globals.Endpoints.ExchangeRate.CONTROLLER + Globals.Endpoints.PING)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetValueWithNoToCurrencyAndDelay0_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.ExchangeRate.CONTROLLER + Globals.Endpoints.ExchangeRate.value)
								.queryParam(Globals.Parameters.ExchangeRate.from, "EUR")
								.queryParam(Globals.Parameters.ExchangeRate.delay, "0")
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
						MockMvcRequestBuilders.get(Globals.Endpoints.ExchangeRate.CONTROLLER + Globals.Endpoints.ExchangeRate.value)
								.queryParam(Globals.Parameters.ExchangeRate.from, "EUR")
								.queryParam(Globals.Parameters.ExchangeRate.to, "USD")
								.queryParam(Globals.Parameters.ExchangeRate.delay, "0")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].fromCurrency.code", Matchers.is("EUR")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].toCurrency.code", Matchers.is("USD")))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].rate", Matchers.is(Matchers.notNullValue())))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetValueWithNoToCurrencyAndNoFromCurrencyAndDelay0_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.ExchangeRate.CONTROLLER + Globals.Endpoints.ExchangeRate.value)
								.queryParam(Globals.Parameters.ExchangeRate.delay, "0")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].fromCurrency.code", Matchers.is("EUR")))
				.andReturn().getResponse().getContentAsString());
	}
}
