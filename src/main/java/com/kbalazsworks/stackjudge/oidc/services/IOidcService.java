package com.kbalazsworks.stackjudge.oidc.services;

import com.kbalazsworks.stackjudge.oidc.entities.AccessTokenRawResponse;
import com.kbalazsworks.stackjudge.oidc.entities.BasicAuth;
import com.kbalazsworks.stackjudge.oidc.entities.IntrospectRawResponse;

public interface IOidcService
{
    AccessTokenRawResponse callTokenEndpoint(String clientId, String clientSecret, String scope, String grantType);
    IntrospectRawResponse callIntrospectEndpoint(String accessToken, BasicAuth basicAuth);
}
