package com.cipitech.tools.converters.exchange.client.impl.thirdparty.dto;

import java.io.Serializable;
import java.util.Map;

public class ThirdPartyResponseDTO implements Serializable {

    private Map<String, Double> quotes;
    private String source;
    private Long timestamp;
    private Boolean success;

    public Map<String, Double> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, Double> quotes) {
        this.quotes = quotes;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
