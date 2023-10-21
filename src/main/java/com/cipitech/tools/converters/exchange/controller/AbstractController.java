package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.AbstractFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.SuccessResponseDTO;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public abstract class AbstractController
{
	private final AppConfig config;

	protected AbstractController(AppConfig config)
	{
		this.config = config;
	}

	protected abstract AbstractFetcher getFetcher();

	protected ResponseEntity<SuccessResponseDTO> pong()
	{
		return ResponseEntity.ok(SuccessResponseDTO.builder().message("pong").build());
	}
}
