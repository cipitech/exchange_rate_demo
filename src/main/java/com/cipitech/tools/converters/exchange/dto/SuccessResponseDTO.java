package com.cipitech.tools.converters.exchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "The JSON Response Object for when a success message needs to be returned to the user.")
public class SuccessResponseDTO
{
	@Schema(description = "The success message returned to the user", example = "Your call was successful", implementation = String.class)
	private String message;
}
