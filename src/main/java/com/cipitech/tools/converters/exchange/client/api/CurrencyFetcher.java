package com.cipitech.tools.converters.exchange.client.api;

import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;

import java.util.List;

public interface CurrencyFetcher {
    List<CurrencyDTO> getAllCurrencies();
}
