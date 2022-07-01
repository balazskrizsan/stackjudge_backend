package com.kbalazsworks.stackjudge.oidc.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.builders.RestTemplateFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

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
        try
        {
            ResponseEntity<String> rawResponse = restTemplateFactory
                .build()
                .postForEntity(url, postData, String.class);

            return objectMapper.readValue(rawResponse.getBody(), mapperClass);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
