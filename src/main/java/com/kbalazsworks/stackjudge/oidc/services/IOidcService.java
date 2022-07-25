package com.kbalazsworks.stackjudge.oidc.services;

import com.kbalazsworks.stackjudge.oidc.entities.AccessTokenRawResponse;
import com.kbalazsworks.stackjudge.oidc.entities.BasicAuth;
import com.kbalazsworks.stackjudge.oidc.entities.IntrospectRawResponse;
import com.kbalazsworks.stackjudge.oidc.exceptions.OidcApiException;

public interface IOidcService
{
    AccessTokenRawResponse callTokenEndpoint(String clientId, String clientSecret, String scope, String grantType)
    throws OidcApiException;

    IntrospectRawResponse callIntrospectEndpoint(String accessToken, BasicAuth basicAuth) throws OidcApiException;
}
