package com.cipitech.tools.converters.exchange.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class Config {

    private Long delayNewRequestSeconds; // The seconds to wait before calling the third party exchange rate API
    private ThirdParty thirdParty;

    public Long getDelayNewRequestSeconds() {
        return delayNewRequestSeconds;
    }

    public void setDelayNewRequestSeconds(Long delayNewRequestSeconds) {
        this.delayNewRequestSeconds = delayNewRequestSeconds;
    }

    public ThirdParty getThirdParty() {
        return thirdParty;
    }

    public void setThirdParty(ThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    public static class ThirdParty {
        private String url; //The base URL of the third-party API
        private String context; //The endpoint that will provide the necessary info
        private String accessKey;
        private RequestCodes requestCodes;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public RequestCodes getRequestCodes() {
            return requestCodes;
        }

        public void setRequestCodes(RequestCodes requestCodes) {
            this.requestCodes = requestCodes;
        }

        public static class RequestCodes {
            private String key;
            private String from;
            private String to;

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }
        }
    }
}
