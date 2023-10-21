package com.cipitech.tools.converters.exchange.utils;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ConversionUtils
{
	public static BigDecimal convertAmount(@NonNull Double amount, @NonNull Double exchangeRate)
	{
		return new BigDecimal(amount * exchangeRate).setScale(2, RoundingMode.HALF_UP);
	}
}
