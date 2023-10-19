package com.cipitech.tools.converters.exchange.client.impl.thirdparty;

import com.cipitech.tools.converters.exchange.client.api.CurrencyFetcher;
import com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto.ThirdPartyResponseDTO;
import com.cipitech.tools.converters.exchange.dto.CurrencyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("third-party")
public class ThirdPartyCurrencyFetcher implements CurrencyFetcher {

    private static final Logger log = LoggerFactory.getLogger(ThirdPartyCurrencyFetcher.class);

    private final ThirdPartyWebClient thirdPartyWebClient;

    public ThirdPartyCurrencyFetcher(ThirdPartyWebClient thirdPartyWebClient) {
        this.thirdPartyWebClient = thirdPartyWebClient;
    }

    @Override
    public List<CurrencyDTO> getAllCurrencies() {
        ThirdPartyResponseDTO response = thirdPartyWebClient.callCurrencyEndpoint();

        if (response.getSuccess()) {
            return response.getCurrencies().entrySet().stream().map(mapEntry -> new CurrencyDTO(mapEntry.getKey(), mapEntry.getValue())).toList();
        }
        else{
            log.error("The call to the third party API was not successful");
            if(response.getError() != null){
                log.error("Error Code [{}]: {}", response.getError().getCode(), response.getError().getInfo());
            }
        }

        return new ArrayList<>();
    }
}
