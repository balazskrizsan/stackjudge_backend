package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services;

import com.kbalazsworks.simple_oidc.exceptions.OidcApiException;
import com.kbalazsworks.simple_oidc.services.ICommunicationService;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.builders.RestTemplateFactory;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.exceptions.OpenSdkResponseException;
import com.kbalazsworks.stackjudge_aws_sdk.common.exceptions.ResponseException;
import com.kbalazsworks.stackjudge_aws_sdk.common.interfaces.IOpenSdkPostable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.kbalazsworks.stackjudge.common.enums.OidcGrantNamesEnum.SJ__AWS;
import static com.kbalazsworks.stackjudge.common.enums.OidcGrantNamesEnum.XC__SJ__AWS;

@Service
@Log4j2
@RequiredArgsConstructor
public class AwsOpenSdkService
{
    private final ApplicationProperties applicationProperties;
    private final RestTemplateFactory   restTemplateFactory;
    private final ICommunicationService communicationService;

    public ResponseEntity<String> post(IOpenSdkPostable postData, String apiUri)
    throws ResponseException
    {
        try
        {
            log.info("Aws post: {}{}", applicationProperties.getStuckJudgeAwsSdkHost(), apiUri);

            String xcAccessToken = communicationService.callTokenEndpoint(XC__SJ__AWS.getValue()).getAccessToken();
            String accessToken = communicationService.callTokenEndpoint(
                SJ__AWS.getValue(),
                new HashMap<>()
                {{
                    put("token", xcAccessToken);
                    put("exchange_from", XC__SJ__AWS.getValue());
                }}
            ).getAccessToken();

            HttpHeaders headers = new HttpHeaders()
            {{
                setContentType(MediaType.MULTIPART_FORM_DATA);
                set(AUTHORIZATION, accessToken);
            }};

            return restTemplateFactory.build().postForEntity(
                applicationProperties.getStuckJudgeAwsSdkHost() + apiUri,
                new HttpEntity<>(postData.toOpenSdkPost(), headers),
                String.class
            );
        }
        catch (OidcApiException e)
        {
            log.error("AWS post OIDC error: " + e.getMessage(), e);

            throw new ResponseException("AWS post OIDC error");
        }
        catch (OpenSdkResponseException e)
        {
            log.error("AWS post error: " + e.getMessage(), e);

            throw new ResponseException("AWS post error");
        }
    }
}
