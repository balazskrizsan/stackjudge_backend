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
import com.kbalazsworks.stackjudge.api.services.facebook_service.FacebookApi10SignService;

import java.io.OutputStream;

public class FacebookApi10Service extends DefaultApi20
{
    private final String version;

    protected FacebookApi10Service()
    {
        this("10.0");
    }

    protected FacebookApi10Service(String version)
    {
        this.version = version;
    }

    private static class InstanceHolder
    {

        private static final FacebookApi10Service INSTANCE = new FacebookApi10Service();
    }

    public static FacebookApi10Service instance()
    {
        return FacebookApi10Service.InstanceHolder.INSTANCE;
    }

    public static FacebookApi10Service customVersion(String version)
    {
        return new FacebookApi10Service(version);
    }

    @Override
    public Verb getAccessTokenVerb()
    {
        return Verb.GET;
    }

    @Override
    public String getAccessTokenEndpoint()
    {
        return "https://graph.facebook.com/v" + version + "/oauth/access_token";
    }

    @Override
    public String getRefreshTokenEndpoint()
    {
        throw new UnsupportedOperationException("Facebook doesn't support refreshing tokens");
    }

    @Override
    protected String getAuthorizationBaseUrl()
    {
        return "https://www.facebook.com/v" + version + "/dialog/oauth";
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
    public FacebookApi10SignService createService(
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
        return new FacebookApi10SignService(
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
