package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.AbstractFetcher;
import com.cipitech.tools.converters.exchange.config.Config;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public abstract class AbstractController
{
	private final Config config;

	protected AbstractController(Config config)
	{
		this.config = config;
	}

	protected abstract AbstractFetcher getFetcher();

	protected ResponseEntity<String> pong()
	{
		return new ResponseEntity<>("pong", HttpStatus.OK);
	}
}
