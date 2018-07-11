package com.baimurzin.gs.config.stateless;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {

    @JsonProperty
    private String token;

    public TokenResponse() {
    }

    public TokenResponse(String token) {
        this.token = token;
    }
}
