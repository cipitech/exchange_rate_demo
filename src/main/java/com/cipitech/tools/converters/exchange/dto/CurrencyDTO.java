package com.cipitech.tools.converters.exchange.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CurrencyDTO implements Serializable {

    private String code;
    private String description;

    public CurrencyDTO(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
