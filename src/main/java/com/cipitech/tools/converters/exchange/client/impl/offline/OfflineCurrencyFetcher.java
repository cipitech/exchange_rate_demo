package com.cipitech.tools.converters.exchange.client.impl.offline;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("offline")
public class OfflineCurrencyFetcher implements CurrencyFetcher {

    private static final Logger log = LoggerFactory.getLogger(OfflineCurrencyFetcher.class);

    public OfflineCurrencyFetcher() {
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies() {
        return null;
    }
}
