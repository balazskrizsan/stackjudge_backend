package com.kbalazsworks.stackjudge.oidc.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.oidc.entities.BasicAuth;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.builders.RestTemplateFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Log4j2
public class OidcHttpClientService
{
    private final RestTemplateFactory restTemplateFactory;

    private final HttpHeaders headers = new HttpHeaders()
    {{
        setContentType(MediaType.MULTIPART_FORM_DATA);
    }};

    private final ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public <T> T getWithMap(String url, Class<T> mapperClass)
    {
        try
        {
            ResponseEntity<String> rawResponse = restTemplateFactory
                .build()
                .getForEntity(url, String.class);

            return objectMapper.readValue(rawResponse.getBody(), mapperClass);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public <T> T postWithMap(String url, LinkedMultiValueMap<String, Object> postData, Class<T> mapperClass)
    {
        return postWithMap(url, postData, mapperClass, null);
    }

    public <T> T postWithMap(
        String url,
        LinkedMultiValueMap<String, Object> postData,
        Class<T> mapperClass,
        BasicAuth basicAuth
    )
    {
        try
        {
            RestTemplate restTemplate = restTemplateFactory.build();
            HttpHeaders  headers      = new HttpHeaders();

            if (null != basicAuth)
            {
                headers.setBasicAuth(basicAuth.getUserName(), basicAuth.getPassword());
            }

            HttpEntity<Object>     request     = new HttpEntity<>(postData, headers);
            ResponseEntity<String> rawResponse = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            return objectMapper.readValue(rawResponse.getBody(), mapperClass);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
