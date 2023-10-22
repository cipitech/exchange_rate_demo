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
public class CurrencyControllerTest extends TestCase
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
						MockMvcRequestBuilders.get(Globals.Endpoints.Currency.CONTROLLER + Globals.Endpoints.PING)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetRefresh_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Currency.CONTROLLER + Globals.Endpoints.Currency.refresh)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetAll_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Currency.CONTROLLER + Globals.Endpoints.Currency.all)
								.queryParam(Globals.Parameters.Currency.showDescription, "true")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());

		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Currency.CONTROLLER + Globals.Endpoints.Currency.all)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenDeleteAll_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.delete(Globals.Endpoints.Currency.CONTROLLER + Globals.Endpoints.Currency.all)
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(Matchers.notNullValue())))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetByCode_thenReturnOK() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Currency.CONTROLLER + "/USD")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is("USD")))
				.andReturn().getResponse().getContentAsString());
	}

	@Test
	public void whenGetByWrongCode_thenReturnNotFound() throws Exception
	{
		log.info(mockMvc.perform(
						MockMvcRequestBuilders.get(Globals.Endpoints.Currency.CONTROLLER + "/USDT")
								.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(404)))
				.andReturn().getResponse().getContentAsString());
	}
}
