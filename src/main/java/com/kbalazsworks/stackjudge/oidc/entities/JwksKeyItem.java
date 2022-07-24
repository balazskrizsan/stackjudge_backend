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
public class JwksKeyItem
{
    @JsonProperty("kty")
    private final String kty;

    @JsonProperty("use")
    private final String use;

    @JsonProperty("kid")
    private final String kid;

    @JsonProperty("e")
    private final String e;

    @JsonProperty("n")
    private final String n;

    @JsonProperty("alg")
    private final String alg;
}
