package com.cipitech.tools.converters.exchange.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseRecordDTO implements Serializable
{
	@Hidden
	private Long id;
	@Hidden
	private Long versionNumber;
	@Hidden
	private Long insertedAt;
	@Hidden
	private Long updatedAt;
}
