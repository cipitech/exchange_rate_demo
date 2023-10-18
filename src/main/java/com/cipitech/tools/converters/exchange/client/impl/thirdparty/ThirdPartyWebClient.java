package com.cipitech.tools.converters.exchange.client.impl.thirdparty;

import com.cipitech.tools.converters.exchange.config.Config;
import com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto.ThirdPartyResponseDTO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@Profile("third-party")
public class ThirdPartyWebClient {

    private final Config config;
    private final WebClient defaultWebClient;

    public ThirdPartyWebClient(Config config, WebClient defaultWebClient) {
        this.config = config;
        this.defaultWebClient = defaultWebClient;
    }

    public ThirdPartyResponseDTO callRateEndpoint(String fromCurrencyCode, List<String> toCurrencyCodes) {

        Optional<String> currCodesOpt = Optional.ofNullable(CollectionUtils.isEmpty(toCurrencyCodes) ? null : String.join(",", toCurrencyCodes));

        Mono<ThirdPartyResponseDTO> responseMono = defaultWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/" + config.getThirdParty().getRateEndpoint())
                        .queryParam(config.getThirdParty().getRequestCodes().getKey(), config.getThirdParty().getAccessKey())
                        .queryParamIfPresent(config.getThirdParty().getRequestCodes().getFrom(), Optional.ofNullable(fromCurrencyCode))
                        .queryParamIfPresent(config.getThirdParty().getRequestCodes().getTo(), currCodesOpt)
                        .build())
                .retrieve()
                .bodyToMono(ThirdPartyResponseDTO.class);

        return responseMono.block();
    }

    public ThirdPartyResponseDTO callCurrencyEndpoint() {

        Mono<ThirdPartyResponseDTO> responseMono = defaultWebClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/" + config.getThirdParty().getCurrencyEndpoint())
                        .queryParam(config.getThirdParty().getRequestCodes().getKey(), config.getThirdParty().getAccessKey())
                        .build())
                .retrieve()
                .bodyToMono(ThirdPartyResponseDTO.class);

        return responseMono.block();
    }
}
