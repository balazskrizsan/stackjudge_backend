package com.kbalazsworks.stackjudge.stackjudge_aws_sdk.services;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.builders.RestTemplateFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class StackJudgeAwsSdkService
{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final ApplicationProperties applicationProperties;
    private final RestTemplateFactory   restTemplateFactory;

    public void post(Object postData, String apiUri)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        Map<String, String> postDataMap = objectMapper.convertValue(postData, Map.class);

        MultiValueMap<String, String> postDataMultiValueMap = new LinkedMultiValueMap<>();
        for (var entry : postDataMap.entrySet())
        {
            postDataMultiValueMap.addAll(entry.getKey(), List.of(entry.getValue()));
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(postDataMultiValueMap, headers);

        try
        {
            restTemplateFactory
                .build()
                .postForEntity(
                    applicationProperties.getStuckJudgeAwsSdkHost()
                        + ":"
                        + applicationProperties.getStuckJudgeAwsSdkPort()
                        + apiUri,
                    request,
                    String.class
                );
        }
        catch (Exception e)
        {
            // @todo: return with named exception
        }
    }
}
