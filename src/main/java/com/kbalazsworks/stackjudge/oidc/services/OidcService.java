package com.kbalazsworks.stackjudge.oidc.services;

import com.kbalazsworks.stackjudge.oidc.entities.AccessTokenRawResponse;
import com.kbalazsworks.stackjudge.oidc.entities.OidcConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

@RequiredArgsConstructor
public class OidcService
{
    private final OidcConfig            oidcConfig;
    private final OidcHttpClientService oidcHttpClient;

    public AccessTokenRawResponse callTokenEndpoint(String clientId, String clientSecret, String scope, String grantType)
    {
        return oidcHttpClient.postWithMap(
            oidcConfig.getTokenEndpoint(),
            new LinkedMultiValueMap<>()
            {{
                addAll("client_id", List.of(clientId));
                addAll("client_secret", List.of(clientSecret));
                addAll("scope", List.of(scope));
                addAll("grant_type", List.of(grantType));
            }},
            AccessTokenRawResponse.class
        );
    }
}
