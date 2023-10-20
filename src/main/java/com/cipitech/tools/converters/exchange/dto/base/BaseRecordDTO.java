package com.cipitech.tools.converters.exchange.dto.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseRecordDTO implements Serializable
{
	private Long id;
	private Long versionNumber;
	private Long insertedAt;
	private Long updatedAt;
}
