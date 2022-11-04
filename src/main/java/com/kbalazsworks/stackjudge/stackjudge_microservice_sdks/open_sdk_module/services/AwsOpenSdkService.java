package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services;

import com.kbalazsworks.simple_oidc.exceptions.GrantStoreException;
import com.kbalazsworks.simple_oidc.exceptions.OidcApiException;
import com.kbalazsworks.simple_oidc.services.IOidcService;
import com.kbalazsworks.simple_oidc.services.OidcService;
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

import static com.kbalazsworks.stackjudge.common.enums.OidcGrantNamesEnum.SJ__AWS__EC2;

@Service
@Log4j2
@RequiredArgsConstructor
public class AwsOpenSdkService
{
    private final ApplicationProperties applicationProperties;
    private final RestTemplateFactory restTemplateFactory;
    private final IOidcService        oidcService;

    public ResponseEntity<String> post(IOpenSdkPostable postData, String apiUri)
    throws ResponseException
    {
        try
        {
            log.info("IDS post: {}{}", applicationProperties.getStuckJudgeAwsSdkHost(), apiUri);

            String accessToken = oidcService.callTokenEndpoint(SJ__AWS__EC2.getValue()).getAccessToken();

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
        catch (GrantStoreException | OidcApiException e)
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
