package com.kbalazsworks.stackjudge.stackjudge_aws_sdk.services;

import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.stackjudge_aws_sdk.builders.RestTemplateFactory;
import com.kbalazsworks.stackjudge_aws_sdk.common.interfaces.IOpenSdkPostable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class AwsOpenSdkService
{
    private final ApplicationProperties applicationProperties;
    private final RestTemplateFactory   restTemplateFactory;

    public void post(IOpenSdkPostable postData, String apiUri)
    {
        HttpHeaders headers = new HttpHeaders()
        {{
            setContentType(MediaType.MULTIPART_FORM_DATA);
        }};

        try
        {
            restTemplateFactory
                .build()
                .postForEntity(
                    applicationProperties.getStuckJudgeAwsSdkHost()
                        + ":"
                        + applicationProperties.getStuckJudgeAwsSdkPort()
                        + apiUri,
                    new HttpEntity<>(postData.toOpenSdkPost(), headers),
                    String.class
                );
        }
        catch (Exception e)
        {
            // @todo: return with named exception
        }
    }
}
