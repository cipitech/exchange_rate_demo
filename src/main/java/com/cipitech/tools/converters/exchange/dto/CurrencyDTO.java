package com.cipitech.tools.converters.exchange.dto;

import java.io.Serializable;

public class CurrencyDTO implements Serializable {

    private String code;
    private String description;

    public CurrencyDTO(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
