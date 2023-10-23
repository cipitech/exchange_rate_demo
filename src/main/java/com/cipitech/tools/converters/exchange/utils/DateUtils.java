package com.cipitech.tools.converters.exchange.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Utility functions regarding Dates.
 */
public class DateUtils
{
	/**
	 * Get the current epoch time in milliseconds.
	 *
	 * @return milliseconds
	 */
	public static long currentTimeInMillis()
	{
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}

	/**
	 * Get the current epoch time in milliseconds after an amount of seconds has been subtracted.
	 *
	 * @param seconds the amount of seconds to subtract from the current time.
	 * @return milliseconds
	 */
	public static long currentTimeInMillisMinusSeconds(long seconds)
	{
		return currentTimeInMillis() - (seconds * 1000);
	}
}
