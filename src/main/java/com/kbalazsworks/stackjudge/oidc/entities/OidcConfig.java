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
public class OidcConfig
{
    @JsonProperty("issuer")
    private final String issuer;
    @JsonProperty("jwks_uri")
    private final String jwksUri;
    @JsonProperty("authorization_endpoint")
    private final String authorizationEndpoint;
    @JsonProperty("token_endpoint")
    private final String tokenEndpoint;
    @JsonProperty("userinfo_endpoint")
    private final String userinfoEndpoint;
    @JsonProperty("end_session_endpoint")
    private final String endSessionEndpoint;
    @JsonProperty("check_session_iframe")
    private final String checkSessionIframe;
    @JsonProperty("revocation_endpoint")
    private final String revocationEndpoint;
    @JsonProperty("introspection_endpoint")
    private final String introspectionEndpoint;
    @JsonProperty("device_authorization_endpoint")
    private final String deviceAuthorizationEndpoint;
    @JsonProperty("backchannel_authentication_endpoint")
    private final String backchannelAuthenticationEndpoint;
}
