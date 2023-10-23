package com.cipitech.tools.converters.exchange.controller;

import com.cipitech.tools.converters.exchange.client.api.AbstractFetcher;
import com.cipitech.tools.converters.exchange.config.AppConfig;
import com.cipitech.tools.converters.exchange.dto.SuccessResponseDTO;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

/**
 * Every Controller must extend this class.
 */

@Getter
public abstract class AbstractController
{
	private final AppConfig config;

	protected AbstractController(AppConfig config)
	{
		this.config = config;
	}

	/**
	 * Every Controller will eventually need to obtain live data from the external datasource.
	 * In order to do that it must have access to the appropriate fetcher.
	 *
	 * @return the fetcher
	 */
	protected abstract AbstractFetcher getFetcher();

	/**
	 * Every controller provides a "ping" endpoint for health checking functionality.
	 *
	 * @return success message "pong"
	 */
	protected ResponseEntity<SuccessResponseDTO> pong()
	{
		return ResponseEntity.ok(SuccessResponseDTO.builder().message("pong").build());
	}
}
