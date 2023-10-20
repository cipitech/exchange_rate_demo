package com.cipitech.tools.converters.exchange.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtils
{
	public static long currentTimeInMillis()
	{
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	public static long currentTimeInMillisMinusSeconds(int seconds)
	{
		return currentTimeInMillis() - (seconds * 1000L);
	}
}
