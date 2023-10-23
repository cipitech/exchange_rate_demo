package com.cipitech.tools.converters.exchange.utils;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility functions regarding any kind of numeric Conversions.
 */
public class ConversionUtils
{
	/**
	 * Convert an amount into another amount using an exchange rate value
	 *
	 * @param amount       the amount to be converted
	 * @param exchangeRate the exchange rate value
	 * @return the converted amount
	 */
	public static BigDecimal convertAmount(@NonNull Double amount, @NonNull Double exchangeRate)
	{
		return new BigDecimal(amount * exchangeRate).setScale(2, RoundingMode.HALF_UP);
	}
}
