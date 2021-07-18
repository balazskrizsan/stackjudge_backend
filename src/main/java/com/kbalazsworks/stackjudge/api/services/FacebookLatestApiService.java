package com.kbalazsworks.stackjudge.api.services;

import com.github.scribejava.apis.facebook.FacebookAccessTokenJsonExtractor;
import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth2.clientauthentication.ClientAuthentication;
import com.github.scribejava.core.oauth2.clientauthentication.RequestBodyAuthenticationScheme;
import com.kbalazsworks.stackjudge.api.services.facebook_service.FacebookLatestApiSignService;

import java.io.OutputStream;

public class FacebookLatestApiService extends DefaultApi20
{
    private final        String                   latestVersion = "10.0";
    private static final FacebookLatestApiService INSTANCE      = new FacebookLatestApiService();

    public static FacebookLatestApiService instance()
    {
        return INSTANCE;
    }

    @Override
    public Verb getAccessTokenVerb()
    {
        return Verb.GET;
    }

    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://graph.facebook.com/v" + latestVersion + "/oauth/access_token";
    }

    @Override
    public String getRefreshTokenEndpoint()
    {
        throw new UnsupportedOperationException("Facebook doesn't support refreshing tokens");
    }

    @Override
    protected String getAuthorizationBaseUrl()
    {
        return "https://www.facebook.com/v" + latestVersion + "/dialog/oauth";
    }

    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor()
    {
        return FacebookAccessTokenJsonExtractor.instance();
    }

    @Override
    public ClientAuthentication getClientAuthentication()
    {
        return RequestBodyAuthenticationScheme.instance();
    }

    @Override
    public FacebookLatestApiSignService createService(
        String apiKey,
        String apiSecret,
        String callback,
        String defaultScope,
        String responseType,
        OutputStream debugStream,
        String userAgent,
        HttpClientConfig httpClientConfig,
        HttpClient httpClient
    )
    {
        return new FacebookLatestApiSignService(
            this,
            apiKey,
            apiSecret,
            callback,
            defaultScope,
            responseType,
            debugStream,
            userAgent,
            httpClientConfig,
            httpClient
        );
    }
}
