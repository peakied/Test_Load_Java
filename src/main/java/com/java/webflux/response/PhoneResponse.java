package com.java.webflux.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PhoneResponse {
    @JsonProperty("error")
    private String error;

    @JsonProperty("name")
    private String name;
}