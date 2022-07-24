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
public class JwksKeys
{
    @JsonProperty("keys")
    private final List<JwksKeyItem> keys;
}
