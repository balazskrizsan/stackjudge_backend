package com.kbalazsworks.stackjudge.api.services.facebook_services;

import com.github.scribejava.core.httpclient.HttpClient;
import com.github.scribejava.core.httpclient.HttpClientConfig;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.services.facebook_services.ScribeJavaFacebookLatestApiService;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class ScribeJavaFacebookLatestApiSignService extends OAuth20Service
{
    public ScribeJavaFacebookLatestApiSignService(
        ScribeJavaFacebookLatestApiService api,
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
        super(
            api,
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

    @Override
    public void signRequest(String accessToken, OAuthRequest request)
    {
        super.signRequest(accessToken, request);

        final Mac mac;
        try
        {
            mac = Mac.getInstance("HmacSHA256");
            final SecretKeySpec secretKey = new SecretKeySpec(getApiSecret().getBytes(), "HmacSHA256");
            mac.init(secretKey);

            final Formatter appsecretProof = new Formatter();

            for (byte b : mac.doFinal(accessToken.getBytes()))
            {
                appsecretProof.format("%02x", b);
            }

            request.addParameter("appsecret_proof", appsecretProof.toString());
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e)
        {
            throw new IllegalStateException("There is a problem while generating Facebook appsecret_proof.", e);
        }
    }
}

