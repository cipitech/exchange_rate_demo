package com.cipitech.tools.converters.exchange.utils;

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
		}

		public static class ExchangeRate
		{
			public static final String CONTROLLER = "/rate";
		}
	}
}
