package com.kbalazsworks.stackjudge.stackjudge_aws_sdk.builders;

import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.services.SdkSslService;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Service
@RequiredArgsConstructor
public class RestTemplateFactory
{
    private final SdkSslService sdkSslService;

    public RestTemplate build() throws Exception
    {
        try
        {
            SSLContext sslContext = sdkSslService.getSslContext();

            HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();

            return new RestTemplateBuilder()
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();
        }
        catch (Exception e)
        {
            // @todo: add error message
            // @todo: throw exception with: unknown error
            throw new Exception();
        }
    }
}
