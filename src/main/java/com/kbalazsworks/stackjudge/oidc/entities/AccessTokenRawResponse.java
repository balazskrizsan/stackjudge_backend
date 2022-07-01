package com.kbalazsworks.stackjudge.oidc.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@ToString
@Getter
public class AccessTokenRawResponse
{
    @JsonProperty("access_token")
    private final String accessToken;
    @JsonProperty("expires_in")
    private final Integer expires_in;
    @JsonProperty("token_type")
    private final String tokenType;
    @JsonProperty("scope")
    private final String scope;
}
