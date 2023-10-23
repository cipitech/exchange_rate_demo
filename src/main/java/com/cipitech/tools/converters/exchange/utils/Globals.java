package com.cipitech.tools.converters.exchange.utils;

/**
 * Global String variables
 */
public class Globals
{
	public static class Profiles
	{
		public static final String OFFLINE     = "offline";
		public static final String THIRD_PARTY = "third-party";
	}

	public static class Parameters
	{
		public static class Currency
		{
			public static final String showDescription = "show_description";
			public static final String code            = "code";
		}

		public static class ExchangeRate
		{
			public static final String from  = "from";
			public static final String to    = "to";
			public static final String delay = "delay";
		}

		public static class Conversion
		{
			public static final String from   = "from";
			public static final String to     = "to";
			public static final String delay  = "delay";
			public static final String amount = "amount";
		}
	}

	public static class Endpoints
	{
		public static final String PING = "/ping";

		public static class Currency
		{
			public static final String CONTROLLER = "/currency";

			public static final String all     = "/all";
			public static final String refresh = "/refresh";
		}

		public static class Conversion
		{
			public static final String CONTROLLER = "/convert";

			public static final String value = "/value";
		}

		public static class ExchangeRate
		{
			public static final String CONTROLLER = "/rate";

			public static final String value = "/value";
		}
	}
}
