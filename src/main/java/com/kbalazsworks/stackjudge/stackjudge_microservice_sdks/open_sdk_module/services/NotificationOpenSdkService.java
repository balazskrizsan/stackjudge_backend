package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services;

import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.builders.RestTemplateFactory;
import com.kbalazsworks.stackjudge_notification_sdk.common.interfaces.IOpenSdkPostable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class NotificationOpenSdkService
{
    private final ApplicationProperties applicationProperties;
    private final RestTemplateFactory   restTemplateFactory;

    public ResponseEntity<String> post(IOpenSdkPostable postData, String apiUri, HttpHeaders headers)
    {
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        try
        {
            return restTemplateFactory
                .build()
                .postForEntity(
                    applicationProperties.getStuckJudgeNotificationSdkHost()
                        + ":"
                        + applicationProperties.getStuckJudgeNotificationSdkPort()
                        + apiUri,
                    new HttpEntity<>(postData.toOpenSdkPost(), headers),
                    String.class
                );
        }
        catch (Exception e)
        {
//            e.printStackTrace();
            // @todo: return with named exception
        }
        return null;
    }
}
