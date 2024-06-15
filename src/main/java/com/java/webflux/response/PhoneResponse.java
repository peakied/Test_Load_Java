package com.java.webflux.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneResponse {
    @JsonProperty("error")
    private String error;

    @JsonProperty("name")
    private String name;
}