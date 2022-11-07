package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.builders;

import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.exceptions.OpenSdkResponseException;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.OpenSdkSslService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Service
@RequiredArgsConstructor
@Log4j2
public class RestTemplateFactory
{
    private final OpenSdkSslService openSdkSslService;

    public RestTemplate build() throws OpenSdkResponseException
    {
        SSLContext sslContext;
        try
        {
            sslContext = openSdkSslService.getSslContext();
        }
        catch (Exception e)
        {
            log.error("SSL context load error: " + e.getMessage(), e);

            throw new OpenSdkResponseException("SSL context load error");
        }

        try
        {
            CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).build();

            return new RestTemplateBuilder()
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();
        }
        catch (Exception e)
        {
            log.error("OpenSdk API call error: " + e.getMessage(), e);

            throw new OpenSdkResponseException("OpenSdk API call error");
        }
    }
}
