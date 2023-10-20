package com.cipitech.tools.converters.exchange.utils;

import lombok.NonNull;

public class ConversionUtils
{
	public static double convertAmount(@NonNull Double amount, @NonNull Double exchangeRate)
	{
		return amount * exchangeRate;
	}
}
