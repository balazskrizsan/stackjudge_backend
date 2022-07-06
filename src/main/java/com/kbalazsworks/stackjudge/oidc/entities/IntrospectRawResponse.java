package com.kbalazsworks.stackjudge.oidc.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@ToString
@Getter
public class IntrospectRawResponse
{
    @JsonProperty("active")
    private final boolean      active;
    @JsonProperty("iss")
    private final String       iss;
    @JsonProperty("nbf")
    private final Integer      nbf;
    @JsonProperty("iat")
    private final Integer      iat;
    @JsonProperty("exp")
    private final Integer      exp;
    @JsonProperty("aud")
    private final List<String> aud;
    @JsonProperty("client_id")
    private final String       clientId;
    @JsonProperty("jti")
    private final String       jti;
    @JsonProperty("active")
    private final String       scope;
}
