package com.cipitech.tools.converters.exchange.dto.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseRecordDTO implements Serializable
{
	private Long id;
	private Long versionNumber;
	private Long insertedAt;
	private Long updatedAt;
}
