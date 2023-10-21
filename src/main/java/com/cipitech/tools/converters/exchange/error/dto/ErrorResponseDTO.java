package com.cipitech.tools.converters.exchange.error.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "The JSON Response Object for when a server side error occurs")
public class ErrorResponseDTO
{
	@Schema(description = "The HTTP status code", example = "404", implementation = Integer.class)
	private int    statusCode;
	@Schema(description = "The HTTP status brief description", example = "Bad Request", implementation = String.class)
	private String message;
	@Schema(description = "A simple explanation of the error", example = "No currency found for this code. Please try something else.", implementation = String.class)
	private String details;
}
